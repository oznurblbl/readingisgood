package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.request.BookAddRequest;
import com.example.demo.controller.payload.response.BookDto;
import com.example.demo.model.Book;

@Service
public interface IBookService {

	public	Book getBookStockOfBooks(String bookId, Integer stock);
	
	public void saveBooks(List<Book> bookList);
    
	public BookDto addOrUpdateBook(BookAddRequest bookAddRequest);
	
	public BookDto updateBookStock(String bookId, Integer stockValue);
}
