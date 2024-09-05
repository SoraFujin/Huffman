package com.algo.Huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Huffman {
    private Node root;
    private Data freqData;
    private File file;
    private static final int MAX_CHAR = 256;
    private String[] huffmanCodes;
    private String data;

    public Huffman(File file) {
        this.file = file;
        this.root = null;
        this.huffmanCodes = new String[MAX_CHAR];
        this.freqData = new Data();  
    }

    private void readDataFromFile() throws IOException {
        data = new String(Files.readAllBytes(Paths.get(file.getPath())));
    }

    public void calculateFreq() throws IOException {
        freqData.calculateFreq(file);
        // freqData.printFreq(freqData.getFreq());
    }

    public String encode() throws IOException {
        readDataFromFile();  

        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] freq = freqData.getFreq();

        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                pq.enqueue(new Leaf((char) i, freq[i]), freq[i]);
            }
        }

        while (pq.size() > 1) {
            Node left = pq.dequeue();
            Node right = pq.dequeue();
            pq.enqueue(new Node(left, right), left.getFreq() + right.getFreq());
        }

        root = pq.dequeue();

        huffmanCoding(root, "");

        StringBuilder encodedData = new StringBuilder();
        for (char c : data.toCharArray()) {
            String code = huffmanCodes[c];
            if (code != null) {
                encodedData.append(code);
            }
        }

        return encodedData.toString();
    }

    private void huffmanCoding(Node node, String code) {
        if (node instanceof Leaf) {
            Leaf leaf = (Leaf) node;
            huffmanCodes[leaf.getChar()] = code; 
        } else {
            huffmanCoding(node.getLeft(), code + '0');  
            huffmanCoding(node.getRight(), code + '1');
        }
    }

    public String header() {
        return generateHeader(root);
    }


    private String generateHeader(Node root){
        StringBuilder header = new StringBuilder();
        generateTreeStructure(root, header);
        return header.toString();

    }

    private void generateTreeStructure(Node node, StringBuilder header){
        if(node instanceof Leaf) {
            header.append('1');
            String binaryChar = asciiToBinary(((Leaf) node).getChar());
            header.append(binaryChar);
        } else {
            header.append('0');
            generateTreeStructure(node.getLeft(), header);
            generateTreeStructure(node.getRight(), header);
        }
    }

    private String asciiToBinary(char c) {
        int asciiValue = c;
        StringBuilder binary = new StringBuilder();
        for(int i = 7; i >= 0; i--) {
            int bit = (asciiValue >> i) & 1;
            binary.append(bit);
        }
        return binary.toString();
    }

    public void writeToFile(File file, String encodedData) throws IOException{
        String header = header(); 
        FileWriter fw = new FileWriter(file);
        fw.write(header);
        fw.write(encodedData);
        fw.close();
    }
}
