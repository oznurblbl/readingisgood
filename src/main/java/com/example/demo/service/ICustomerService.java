package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.request.CustomerAddRequest;
import com.example.demo.controller.payload.response.CustomerDto;
import com.example.demo.controller.payload.response.OrderDto;

@Service
public interface ICustomerService {

	CustomerDto createNewCustomer(CustomerAddRequest customerAddRequest);
	
	List<OrderDto> getCustomerAllOrders(String customerId, Pageable pageable);
	
	

}
