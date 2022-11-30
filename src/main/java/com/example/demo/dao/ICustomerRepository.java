package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Customer;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, String>{

	public Optional<Customer> findByEmail(String email);

}
