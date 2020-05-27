package com.michael.project.library;

import lombok.Data;

@Data
public class Teacher extends Staff {
	
	private LibraryManager libraryManager;

	@Override
	public boolean borrowBook(Book book) {
		return libraryManager.borrowBook(book);
	}

	@Override
	public boolean returnBook(Book book) {
		return libraryManager.returnBook(book);
	}

}
