package com.java.basic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MyLinkedList<E> {
	
	private Node<E> first;
	private Node<E> last;
	private int size;
	
	public void addLast(E e) {
		last.next = new Node<E>(last, e, null);
		size++;
	}
	
	public void printAll() {
		while(last)
	}

	@Data
	@AllArgsConstructor
	private static class Node<E> {
		Node<E> prev;
		E item;
		Node<E> next;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
