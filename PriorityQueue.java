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

	private QNode<T> deleteMin(QNode<T> root) {
		if(root.getLeft() == null) 
			return root.getRight();
		root.setLeft(deleteMin(root.getLeft()));
		return root;
	}

	public T dequeue() {
		if (root == null) 
			return null;
		QNode<T> minNode = findMin(root);
		root = deleteMin(root);
		return minNode.getData();
	}

	public boolean isEmpty() {
		return root == null;
	}

	public T peek() {
		if (root == null) 
			return null;
		return findMin(root).getData();
	}

	public int size() {
		return size(root);
	}

	private int size(QNode<T> node) {
		if(node == null) 
			return 0;

		int leftSize = size(node.getLeft());
		int rightSize = size(node.getRight());
		return leftSize + rightSize + 1;
	}

}
