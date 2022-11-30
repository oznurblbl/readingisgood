package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "customers", catalog = "readingisgood")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String cusId;
	
	private String cusEmail;
	
	private String cusPass;
	
	private String firstName;
	
	private String lastName;
	
	private String phone;
	
	private String address;
	
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	

}
