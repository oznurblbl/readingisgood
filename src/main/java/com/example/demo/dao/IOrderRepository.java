package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository
public interface IOrderRepository extends CrudRepository<Order, String> {

	Page<Order> findByCustomerId(String customerId, Pageable pageable);

	List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endTime);

}
