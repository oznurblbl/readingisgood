package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

@Repository
public interface IBookRepository extends CrudRepository<Book, String>{

	public Optional<Book> findByNameAndWriterAndPublishYearAndPrice(String name, String writer, Integer publishYear, Double price);
}
