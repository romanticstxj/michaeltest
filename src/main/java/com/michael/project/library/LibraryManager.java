package com.michael.project.library;

import lombok.Data;

@Data
public class LibraryManager {
	
	private Library library;
	
	public boolean borrowBook(Book book) {
		return library.borrowBook(book);
	}
	
	public boolean returnBook(Book book) {
		return library.returnBook(book);
	}

}
