package com.example.demo.controller;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.payload.request.BookAddRequest;
import com.example.demo.controller.payload.response.BookDto;
import com.example.demo.service.IBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {

	private final IBookService bookService;
	
	@PostMapping("/create")
	public ResponseEntity<BookDto> createBook(@RequestBody BookAddRequest bookAddRequest){
		
		log.info("Add new book request is received");
		
		try {
			BookDto bookDto = bookService.addOrUpdateBook(bookAddRequest);
			
			if(bookDto != null) {
				return new ResponseEntity<BookDto>(bookDto, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (OptimisticLockingFailureException e) {
			log.error("Concurrent update is detected. You need to try again");
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<BookDto> updateBookStock(@PathVariable String bookId, @RequestParam Integer stockCount){
		
		log.info("Update book stock is received");
		
		try {
			BookDto updateBook = bookService.updateBookStock(bookId, stockCount);
			if(updateBook != null) {
				return new ResponseEntity<>(updateBook, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (OptimisticLockingFailureException e) {
			log.error("Concurrent update is detected. You need to try again.");
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
}
