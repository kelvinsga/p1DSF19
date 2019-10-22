package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarComparator;

public class CircularSortedDoublyLinkedList<E> implements SortedList<E>{
	private int size;
	private Node<E> header = new Node(null, null, null);
	private Comparator<E> cmp;  

	public CircularSortedDoublyLinkedList(){
		size = 0;
		header = new Node<E>(null,null,null);
		header.setNext(header);
		header.setPrev(header);
			}
	//  The constructor to test the Data Structure
	public CircularSortedDoublyLinkedList(Comparator<E> c) {
		this();
		this.cmp= c;
	}
	@Override
	public Iterator<E> iterator() {
		return new CircularSortedDoublyLinkedListIterator();
	}

	// Method that adds the target object to the list
	@Override
	public boolean add(E c) {

		if(c == null) {
			return false;
		}

		Node<E> newNode = new Node<E>(c);


		if(size == 0) {
			header.setNext(newNode);
			header.setPrev(newNode);
			newNode.setNext(header);
			newNode.setPrev(header);
			size++;
			return true;
		}

		Node<E> currentNode = header.getNext();
		while(currentNode != header) {

			if(cmp.compare(c, currentNode.getElement()) <= 0) {
				newNode.setPrev(currentNode.getPrev());
				newNode.setNext(currentNode);
				currentNode.setPrev(newNode);
				newNode.getPrev().setNext(newNode);
				size++;
				return true;
			}

			currentNode = currentNode.getNext();
		}
		newNode.setNext(header);
		newNode.setPrev(header.getPrev());
		header.getPrev().setNext(newNode);
		header.setPrev(newNode);
		size++;

		return true;
	}

	//Returns the size of the target list.
	@Override
	public int size() {
		return this.size;
	}
	// this method remove the target object from the list
	@Override
	public boolean remove(E c) {
		Node<E> currentNode;
		for(currentNode = header.getNext(); currentNode != header; currentNode = currentNode.getNext()) {
			if(currentNode.getElement().equals(c)) {
				currentNode.getNext().setPrev(currentNode.getPrev());
				currentNode.getPrev().setNext(currentNode.getNext());
				currentNode.clear();
				size--;

				return true;
			}
		}
		//if the car is not found it is false
		return false;
	}

	//this method removes the object in the index established
	@Override
	public boolean remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		int i = 0;
		Node<E> currentNode = header.getNext();
		while(i < index) {
			currentNode = currentNode.getNext();
			i++;
		}
		currentNode.getNext().setPrev(currentNode.getPrev());
		currentNode.getPrev().setNext(currentNode.getNext());
		currentNode.clear();
		size--;

		return true;
	}

	//This method removes all elements c in the list and returns
	@Override
	public int removeAll(E c) {
		int count = 0;
		while(this.remove(c)) {
			count++;
		}
		return count;
	}

	//This method returns the first element at the target list.
	@Override
	public E first() {
		if(this.isEmpty()) {
			return null;
		}
		return header.getNext().getElement();
	}

	//This method returns the last element of the target list.
	@Override
	public E last() {
		if(this.isEmpty()) {
			return null;
		}
		return header.getPrev().getElement();
	}

	
	 // This method will return the element in the target
	@Override
	public E get(int index) {
		if(index < 0 || index >= size || this.isEmpty()) {
			return null;
		}
		else {
			int i = 0;
			Node<E> currentNode = header.getNext();
			while(i < index) {
				currentNode = currentNode.getNext();
				i++;
			}
			return currentNode.getElement();
		}
	}

	//erase all the objects in the list
	@Override
	public void clear() {
		while(!this.isEmpty()) {
			this.remove(0);
		}

	}

	// this method checks if the list contains c
	@Override
	public boolean contains(E c) {
		if(this.isEmpty() || c == null) {
			return false;
		}
		Node<E> currentNode;
		for(currentNode = header.getNext(); currentNode != header; currentNode = currentNode.getNext()) {
			if(currentNode.getElement().equals(c)) {
				return true;
			}
		}
		return false;
	}

	//returns true if the list is empty or false otherwise;
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	//returns the first index in the kist after the dummy header
	@Override
	public int firstIndex(E c) {
		int i=0;
		Node<E>currentNode;
		for(currentNode=header.getNext(); currentNode!=header; currentNode=currentNode.getNext(), i++) {
			if(c.equals(currentNode.getElement()))
				return i;
		}
		//Element not found
		return -1;
	}

	//returns the element previous to the header
	@Override
	public int lastIndex(E c) {
		int i = size()-1;
		Node<E>currentNode;
		for(currentNode=header.getPrev(); currentNode!=header; currentNode=currentNode.getPrev(), i--) {
			if(c.equals(currentNode.getElement()))
				return i;
		}
		//Not found
		return -1;

	}

	//converts list to array
	public Car[] toArray(){
		Car [] result = new Car[this.size];
		int i = 0;
		Node<E> temp = this.header.getNext();
		while(temp != this.header) {
			result[i] = (Car) temp.getElement();
			i++;
			temp = temp.getNext();
		}
		return result;
	}
	private static class Node<E>{
		private E element;
		private Node<E> prev, next;

		public Node(){
			super();
		}
		public Node(E e) {
			this.element = e;
		}
		public Node(E element, Node<E> prev, Node<E> next) {
			super();
			this.element = element;
			this.prev = prev;
			this.next = next;
		}

		private E getElement() {
			return element;
		}
		private void setElement(E element) {
			this.element = element;
		}
		private Node<E> getPrev() {
			return prev;
		}
		private void setPrev(Node<E> prev) {
			this.prev = prev;
		}
		private Node<E> getNext() {
			return next;
		}
		private void setNext(Node<E> next) {
			this.next = next;
		}

		public void clear() {
			prev = next = null;
		}

	}
	//Iterator class for the List
		private class CircularSortedDoublyLinkedListIterator implements Iterator<E>{

			private Node<E> nextNode;

			//constructor
			public CircularSortedDoublyLinkedListIterator() {
				this.nextNode = (Node<E>)header.getNext();
			}

			@Override
			public boolean hasNext() {
				return nextNode != header;
			}

			@Override
			public E next() {
				if(this.hasNext()) {
					E result = this.nextNode.getElement();
					this.nextNode = this.nextNode.getNext();
					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}

		}
}
