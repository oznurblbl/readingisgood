package com.example.demo.controller.payload.response;

import java.time.LocalDateTime;

import com.example.demo.model.Customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDto {

	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private LocalDateTime createdAt;
	
	public static CustomerDto getCustomer(Customer customer) {
		return CustomerDto.builder()
				.email(customer.getCusEmail())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.phone(customer.getPhone())
				.address(customer.getAddress())
				.createdAt(customer.getCreatedAt())
				.build();
	}
}
