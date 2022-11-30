package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "books", catalog = "readingisgood")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String bookId;
	
	private String bookName;
	
	private String writer;
	
	private Double price;
	
	private Integer publishYear;
	
	private Integer bookStock;
	
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	
	private LocalDateTime updatedAt;
}
