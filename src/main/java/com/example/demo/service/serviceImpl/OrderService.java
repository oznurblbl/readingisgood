package com.example.demo.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.request.BookOrder;
import com.example.demo.controller.payload.request.OrderAddRequest;
import com.example.demo.controller.payload.response.OrderDto;
import com.example.demo.dao.IOrderRepository;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.service.IBookService;
import com.example.demo.service.IOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService{
	
	@Autowired
	private final IOrderRepository orderRepository;
	
	@Autowired
	private final IBookService bookService;
	
	@Autowired
	private final ReentrantLock reentrantLock;
	
	@Override
	public List<OrderDto> getOrdersByCustomerId(String customerId, Pageable pageable) {
		Page<Order> pageOrders = orderRepository.findByCustomerId(customerId, pageable);
		List<Order> customerOrders = pageOrders.getContent();
		
		return customerOrders.stream().map(OrderDto::fromOrder)
							.collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public OrderDto saveOrder(OrderAddRequest orderAddRequest) {
		
		try {
			if(reentrantLock.tryLock(10, TimeUnit.SECONDS)) {
				String customerId = orderAddRequest.getCustomerId();
				List<BookOrder> bookOrderList = orderAddRequest.getBookOrders();
				
				// filter invalid book orders which have invalid book count
				final List<BookOrder> invalidBookOrders = bookOrderList.stream()
						.filter(bookOrder -> bookOrder.getCount() <= 0 )
						.collect(Collectors.toList());
				
				if(customerId == null || !invalidBookOrders.isEmpty()) {
					log.error("New order request  failed due to invalid customerId={}, bookOrders={}", customerId, invalidBookOrders);
					return null;
				}
				
				//fetch books by id and control whether or not there is enough stock for each book order
				List<Book> books = bookOrderList.stream()
						.map(bookOrder -> bookService.getBookStockOfBooks(bookOrder.getBookId(), bookOrder.getCount()))
						.filter(Objects::nonNull)
						.collect(Collectors.toList());
				if(books.size() < bookOrderList.size()) {
					log.error("Order is not created due to lack of requested books");
					return null;
				}
				
				Map<String , Integer> collect = bookOrderList.stream()
						.collect(Collectors.toMap(BookOrder::getBookId, BookOrder::getCount));
				
				//update each book stock
				books.forEach(book -> book.setBookStock(book.getBookStock() - collect.get(book.getBookId())));
				bookService.saveBooks(books);
				
				//calculate total order price
				Double totalPrice = books.stream()
						.map(book -> collect.get(book.getBookId()) * book.getPrice())
						.reduce(0.0, Double::sum);
				
				//calculate total book count
				Integer totalBooks =  collect.values().stream()
						.reduce(0, Integer::sum);
				
				//create new order
				Order order = Order.builder()
						.cusId(customerId)
						.bookOrders(bookOrderList)
						.totalBooks(totalBooks)
						.totalPrice(totalPrice)
						.status("Completed")
						.build();
				
				Order savedOrder =  orderRepository.save(order);
				
				log.info("Order is created successfully with customer={}, books={}", customerId, books);
				
				return OrderDto.fromOrder(savedOrder);
			}
		} catch (Exception e) {
			log.error("Order is not created error={}", e.getMessage());
			throw new RuntimeException("Create new order is failed");
		} finally {
			reentrantLock.unlock();
		}
		
		return null;
	}

	@Override
	public OrderDto getOrdersById(String orderId) {
		
		final Optional<Order> orderOptional = orderRepository.findById(orderId);
		if(orderOptional.isEmpty()) {
			log.error("Order is not found with id= {}", orderId);
			return null;
		}
		
		return OrderDto.fromOrder(orderOptional.get());
	}

	@Override
	public List<OrderDto> getOrdersByDate(LocalDateTime startDate, LocalDateTime endTime) {
		List<Order> orders = orderRepository.findByCreatedAtBetween(startDate, endTime);
		
		return orders.stream()
				.map(OrderDto::fromOrder)
				.collect(Collectors.toList());
	}


}
