package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.payload.response.CustomerMonthlyStaticsDto;
import com.example.demo.service.IStaticsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/statics")
@RequiredArgsConstructor
public class StaticsController {
	
	private final IStaticsService staticsService;
	
	  @GetMapping("/{customerId}")
	    public ResponseEntity<List<CustomerMonthlyStaticsDto>> getCustomerMonthlyStatics(@PathVariable String customerId) {

	        log.info("Customer monthly statics request is received with customerId={}", customerId);

	        try {
	            List<CustomerMonthlyStaticsDto> customerMonthlyStatics = staticsService.getCustomerMonthlyStatics(customerId);
	            return new ResponseEntity<>(customerMonthlyStatics, HttpStatus.OK);

	        } catch (Exception ex) {
	            log.error("Exception is occurred during customer monthly statics querying ", ex);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	    }

}
