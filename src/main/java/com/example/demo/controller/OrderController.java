package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.payload.request.OrderAddRequest;
import com.example.demo.controller.payload.response.OrderDto;
import com.example.demo.service.IOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
	
	@Autowired
	 private final IOrderService orderService;


	    @PostMapping("/create")
	    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderAddRequest newOrderRequest) {

	        log.info("New order request is received for customer={}", newOrderRequest.getCustomerId());

	        try {
	            OrderDto newOrderDto = orderService.saveOrder(newOrderRequest);
	            if (newOrderDto != null) {
	                return new ResponseEntity<>(newOrderDto, HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	            }
	        }
	        catch (OptimisticLockingFailureException ex) {
	            log.error("Concurrent update is detected. Please try again");
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        catch (Exception ex) {
	            log.error("Exception is occurred during order creation {}", ex.getMessage());
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<OrderDto> getOrder(@PathVariable String id) {

	        log.info("Order query by id={} is received", id);

	        try {
	            OrderDto orderDto = orderService.getOrdersById(id);
	            if (orderDto == null) {
	                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	            }
	            return new ResponseEntity<>(orderDto, HttpStatus.OK);

	        } catch (Exception ex) {
	            log.error("Exception is occurred during order fetching ", ex);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @GetMapping()
	    public ResponseEntity<List<OrderDto>> getOrdersByDateInterval(@DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate startDate,
	                                                    @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate endDate) {

	        log.info("Orders query by startDate={} endDate={} is received", startDate, endDate);

	        try {
	            List<OrderDto> orders = orderService.getOrdersByDate(startDate.atStartOfDay(), endDate.atStartOfDay());
	            if (orders == null) {
	                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	            }
	            return new ResponseEntity<>(orders, HttpStatus.OK);

	        } catch (Exception ex) {
	            log.error("Exception is occurred during order fetching ", ex);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
