package com.michael.project.library;

import lombok.Data;

@Data
public class Book {

	private String name;
	
	private String author;
	
	private BookType bookType;
}
