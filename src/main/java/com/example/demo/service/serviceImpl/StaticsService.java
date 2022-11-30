package com.example.demo.service.serviceImpl;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.response.CustomerMonthlyStaticsDto;
import com.example.demo.controller.payload.response.OrderDto;
import com.example.demo.service.ICustomerService;
import com.example.demo.service.IStaticsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class StaticsService implements IStaticsService{
	
	@Autowired
	private final ICustomerService customerService;

	@Override
	public List<CustomerMonthlyStaticsDto> getCustomerMonthlyStatics(String customerId) {
			//get customer all orders
			List<OrderDto> customerOrders = customerService.getCustomerAllOrders(customerId, Pageable.unpaged());
			
			//group customers orders by month
			Map<Month, List<OrderDto>> result = customerOrders.stream()
					.collect(Collectors.groupingBy(order -> order.getCreatedAt().getMonth()));
			
			return result.entrySet().stream()
					.map(entry -> createCustomerMonthlyStatics(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
			
		}

	// Helper method to calculate total book and total amount for each month orders
	private CustomerMonthlyStaticsDto createCustomerMonthlyStatics(Month month, List<OrderDto> orderList) {
		
		int totalBookCount = 0;
		double totalPurchasedAmount = 0;
		
		for (OrderDto orderDto : orderList) {
			
			totalBookCount += orderDto.getTotalBooks();
			totalPurchasedAmount += orderDto.getTotalPrice();
		}
		
		return new CustomerMonthlyStaticsDto(month.toString(), orderList.size(), totalBookCount, totalPurchasedAmount);
	}
}
