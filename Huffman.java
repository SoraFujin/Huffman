package com.algo.Huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    private double fileSize = 0;
    private double compressedFileSize = 0;
    private double headerSize = 0;

    public Huffman(File file) {
        this.file = file;
        this.root = null;
        this.huffmanCodes = new String[MAX_CHAR];
        this.freqData = new Data();  
        this.fileSize = file.length();
    }

    private void readDataFromFile() throws IOException {
        data = new String(Files.readAllBytes(Paths.get(file.getPath())));
    }


    public void calculateFreq() throws IOException {
        freqData.calculateFreq(file);
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
        compressedFileSize = (encodedData.length()) / 8;

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
        headerSize = header.length();
        return header.toString();

    }


    private void generateTreeStructure(Node node, StringBuilder header){
        if(node instanceof Leaf) {
            header.append('1');
            header.append(((Leaf) node).getChar());
        } else {
            header.append('0');
            generateTreeStructure(node.getLeft(), header);
            generateTreeStructure(node.getRight(), header);
        }
    }

    public void writeToFile(File file, String encodedData) throws IOException{
        String header = header(); 
        FileWriter fw = new FileWriter(file);
        fw.write(header);
        fw.write(encodedData);
        fw.close();
    }

    private Node readHeader(BufferedReader br) throws IOException {
        int currentCharacter = br.read(); 
        if (currentCharacter == -1) {
            throw new IOException("Unexpected end of file while reading header");
        }

        if (currentCharacter == '1') {
            char leafChar = (char) br.read();
            return new Leaf(leafChar, 0);
        } else if (currentCharacter == '0') {
            Node leftChild = readHeader(br);
            Node rightChild = readHeader(br);
            return new Node(leftChild, rightChild);
        }

        throw new IOException("Invalid header format");
    }

    public void decodeFromFile(File compressedFile, File outputFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(compressedFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

        Node root = readHeader(br);

        Node currentNode = root;
        int currentCharacter;

        while ((currentCharacter = br.read()) != -1) {
            if (currentCharacter == '0') {
                currentNode = currentNode.getLeft();
            } else if (currentCharacter == '1') {
                currentNode = currentNode.getRight();
            }

            if (currentNode instanceof Leaf) {
                bw.write(((Leaf) currentNode).getChar());
                currentNode = root;  
            }
            compressedFileSize++;
        }

        br.close();
        bw.close();
    }

    public double getFileSize() {
        return fileSize;
    }

    public double getCompressedFileSize() {
        return compressedFileSize;
    }

    public double getHeaderSize() {
        return headerSize;
    }
}
