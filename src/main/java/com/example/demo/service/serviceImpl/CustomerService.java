package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.request.CustomerAddRequest;
import com.example.demo.controller.payload.response.CustomerDto;
import com.example.demo.controller.payload.response.OrderDto;
import com.example.demo.dao.ICustomerRepository;
import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import com.example.demo.service.IOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{
	
	@Autowired
	private final ICustomerRepository customerRepository;
	
	@Autowired
	private final IOrderService orderService;
	
	
	@Override
	public CustomerDto createNewCustomer(CustomerAddRequest customerAddRequest) {
		
		String email = customerAddRequest.getEmail();
		if(email == null) {
			log.error("Customer email is not valid email={}", email);
		}
		
		Optional<Customer> customerOptional = customerRepository.findByEmail(email);
		
		if(customerOptional.isPresent()) { //if same email customer exists already, return error
			log.error("Customer already exist with email={}", email);
			return null;
		}
		
		Customer customer = Customer.builder()
							.cusEmail(email)
							.firstName(customerAddRequest.getFirstName())
							.lastName(customerAddRequest.getLastName())
							.cusPass(customerAddRequest.getPassword())
							.phone(customerAddRequest.getPhone())
							.address(customerAddRequest.getAddress())
							.build();
		
		customerRepository.save(customer);
		
		log.info("New customer is created with email={}", email);
		return CustomerDto.getCustomer(customer);
	}

	@Override
	public List<OrderDto> getCustomerAllOrders(String customerId, Pageable pageable) {
		
		return orderService.getOrdersByCustomerId(customerId, pageable);
	}

}
