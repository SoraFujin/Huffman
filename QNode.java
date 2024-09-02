package com.algo.Huffman;

public class QNode <T extends Comparable<T>> {
	private T data;
	private int priority;
	private QNode<T> left, right;


	public QNode(T data, int priority){
		this.data = data;
		this.priority = priority;
		this.left = this.right = null;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setLeft(QNode<T> left) {
		this.left = left;
	}

	public QNode<T> getLeft() {
		return left;
	}

	public void setRight(QNode<T> right) {
		this.right = right;
	}

	public QNode<T> getRight() {
		return right;
	}

	public String toString() {
		return "data: " + data;
	}
	
}
