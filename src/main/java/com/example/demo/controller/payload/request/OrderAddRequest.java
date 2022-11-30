package com.example.demo.controller.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddRequest {

	private String customerId;
	private List<BookOrder> bookOrders;
	
}
