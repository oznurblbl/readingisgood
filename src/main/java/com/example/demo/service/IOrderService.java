package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.request.OrderAddRequest;
import com.example.demo.controller.payload.response.OrderDto;

@Service
public interface IOrderService {
	
	public OrderDto saveOrder(OrderAddRequest orderAddRequest);
	
	public OrderDto getOrdersById(String orderId);
	
	public List<OrderDto> getOrdersByDate(LocalDateTime startDate, LocalDateTime endTime);

	public List<OrderDto> getOrdersByCustomerId(String customerId, Pageable pageable);

}
