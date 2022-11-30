package com.example.demo.controller.payload.response;

import java.time.LocalDateTime;

import com.example.demo.model.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDto {

	private String name;
	private String writer;
	private Integer publishYear;
	private Integer stock;
	private Double price;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public static BookDto fromBook(Book book) {
		
		return BookDto.builder()
				.name(book.getBookName())
				.writer(book.getWriter())
				.publishYear(book.getPublishYear())
				.stock(book.getBookStock())
				.price(book.getPrice())
				.createdAt(book.getCreatedAt())
				.updatedAt(book.getUpdatedAt())
				.build();
	}
}
