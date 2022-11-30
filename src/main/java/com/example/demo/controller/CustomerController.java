package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.payload.request.CustomerAddRequest;
import com.example.demo.controller.payload.response.CustomerDto;
import com.example.demo.controller.payload.response.OrderDto;
import com.example.demo.service.ICustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CustomerController {
	
	@Autowired
	private final ICustomerService customerService;
	
	@PostMapping("/create")
	public ResponseEntity<CustomerDto> createNewCustomer(@RequestBody CustomerAddRequest customerAddRequest){
		
		log.info("New customer request is received!");
		try {
			CustomerDto customerDto = customerService.createNewCustomer(customerAddRequest);
			if(customerDto != null) {
				return new ResponseEntity<>(customerDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("/{id}/orders")
	public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable String id, @PageableDefault(size = 20) Pageable pageable){
		
		log.info("Customer orders request is received with customerId={}", id);
		
		try {
			List<OrderDto> orderDto = customerService.getCustomerAllOrders(id, pageable);
			
			if(orderDto.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<List<OrderDto>>(orderDto, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
