package com.michael.project.library;

import java.util.List;


public class Library {

	private List<Book> books;
	
	public Library(List<Book> books) {
		setBooks(books);
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public boolean borrowBook(Book book) {
		return books.remove(book);
	}
	
	public boolean returnBook(Book book) {
		return books.add(book);
	}
}
