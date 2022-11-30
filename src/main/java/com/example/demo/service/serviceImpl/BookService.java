package com.example.demo.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.payload.request.BookAddRequest;
import com.example.demo.controller.payload.response.BookDto;
import com.example.demo.dao.IBookRepository;
import com.example.demo.model.Book;
import com.example.demo.service.IBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService implements IBookService {
	
	@Autowired
	private final IBookRepository bookRepository;
	
	
	@Override
	public Book getBookStockOfBooks(String bookId, Integer stock) {
		
		Optional<Book> bookOpt = bookRepository.findById(bookId);
		if(bookOpt.isEmpty()) {
			log.error("Book is not found with bookId={}", bookId);
			return null;
		} else if(bookOpt.get().getBookStock() < stock) {
			log.error("Book does have enough stock with bookId={}, existingStock={}, requestedStock={}",
					bookId, bookOpt.get().getBookStock(), stock);
			return null;
		}
		return bookOpt.get();
	}

	@Override
	public void saveBooks(List<Book> bookList) {
		for(Book book : bookList) {
			bookRepository.save(book);
		}
		
	}

	@Override
	public BookDto addOrUpdateBook(BookAddRequest bookAddRequest) {
	
		String bookName = bookAddRequest.getName();
		String writer = bookAddRequest.getWriter();
		Integer publishYear = bookAddRequest.getPublishYear();
		Integer stock = bookAddRequest.getStock();
		Double price = bookAddRequest.getPrice();
		
		if(bookName == null || writer == null || publishYear == null) {
			log.error("New Book could'nt be added name={}, writer={}, publishYear={}", bookName, writer, publishYear);
			return null;
		}
		
		//query book with same name, writer, publishYear and price
		Optional<Book> bookOpt = bookRepository.findByNameAndWriterAndPublishYearAndPrice(bookName, writer, publishYear, price);
		
		Book book;
		if(bookOpt.isPresent()) { //if same book exists  already, just update value
			book = bookOpt.get();
			book.setUpdatedAt(LocalDateTime.now());
			book.setBookStock(book.getBookStock() + stock);
		} else { // if not exists, create new book 
			
			book = Book.builder()
					.bookName(bookName)
					.writer(writer)
					.publishYear(publishYear)
					.bookStock(stock)
					.price(price)
					.build();
			
		}
		Book savedBook = bookRepository.save(book);
		
		log.info("New book is added successfully created with name={}, writer={}, publishYear={}, stock={}, price={}", bookName, writer, publishYear, stock, price);
		
		return BookDto.fromBook(savedBook);
	}

	@Override
	public BookDto updateBookStock(String bookId, Integer stockValue) {
		
		//query book by id
		Optional<Book> bookOpt= bookRepository.findById(bookId);
		Book updatedBook = null;
		
		if(bookOpt.isEmpty()) {
			log.error("Book not found with id={}", bookId );
		} else {
			Book book = bookOpt.get();
			book.setBookStock(stockValue);
			updatedBook = bookRepository.save(book);
			
			log.info("Book is updated  successfully  id={}, stockValue={}", bookId, stockValue);
			
			
		}
		return BookDto.fromBook(updatedBook);
	}

}
