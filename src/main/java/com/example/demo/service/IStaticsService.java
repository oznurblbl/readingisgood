package com.example.demo.service;

import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.response.CustomerMonthlyStaticsDto;
import com.example.demo.controller.payload.response.OrderDto;

@Service
public interface IStaticsService {

	List<CustomerMonthlyStaticsDto> getCustomerMonthlyStatics(String customerId);
	
	
}
