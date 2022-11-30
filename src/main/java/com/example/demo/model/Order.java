package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.controller.payload.request.BookOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "orders", catalog = "readingisgood")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String orderId;
	
	private String cusId;
	
	private List<BookOrder> bookOrders;
	
	private Integer totalBooks;
	
	private Double totalPrice;
	
	private String status;
	
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
}
