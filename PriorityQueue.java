package com.algo.Huffman;

public class PriorityQueue<T extends Comparable<T>> {
	QNode<T> root;

	public PriorityQueue() {
		root = null;
	}

	private QNode<T> add(QNode<T> root, T data, int priority) {
		if (root == null) {
			return new QNode<>(data, priority);
		}
		if (priority <= root.getPriority()) 
			root.setLeft(add(root.getLeft(), data, priority));
		else 
			root.setRight(add(root.getRight(), data, priority));
		return root;
	}

	public void enqueue(T data, int priority) {
		root = add(root, data, priority);
	}

	private QNode<T> findMin(QNode<T> node) {
		QNode<T> current = node;
		while(current.getLeft() != null) 
			current = current.getLeft();
		
		return current;
	}
}
