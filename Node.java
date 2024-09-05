package com.algo.Huffman;

public class Node implements Comparable<Node>{
	private final int freq;
	private Node left, right;

	public Node(int freq) {
		this.freq = freq;
	}

	public Node(Node left, Node right) {
		this.freq = left.getFreq() + right.getFreq();
		this.left = left;
		this.right = right;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getLeft() {
		return left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getRight() {
		return right;
	}

	public int getFreq() {
		return freq;
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(freq, o.getFreq());
	}

	@Override
	public String toString() {
		return left.toString() + right.toString();
	}

}

