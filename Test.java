package com.algo.Huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Test {
   private static final File inputFile = new File("/home/ahmad/Desktop/java/Projects/Algorithms/src/main/java/com/algo/Huffman/input.txt");
    private static final File compressedFile = new File("/home/ahmad/Desktop/java/Projects/Algorithms/src/main/java/com/algo/Huffman/compressed.txt");
    private static final File outputFile = new File("/home/ahmad/Desktop/java/Projects/Algorithms/src/main/java/com/algo/Huffman/output.txt");
    private static Huffman huff = new Huffman(inputFile);

    public static void main(String[] args) throws IOException {
       encodeData();
       decode();
    }

    public static void encodeData() throws IOException{
       huff.calculateFreq();  
       String encodedData = huff.encode();
       String header = huff.header();
       double fileSize = huff.getFileSize();
       double headerSize = huff.getHeaderSize();
       double encodedDataSize = huff.getCompressedFileSize() ;
       double compressedSize = headerSize + encodedDataSize;
       System.out.println("File size: " + fileSize);
       System.out.println("Header size: " + headerSize);
       System.out.println("encoded size: " + encodedDataSize);
       System.out.println("compressed File size: " + compressedSize);
       writeToFile(encodedData, header, compressedFile);

    }
         
    public static void writeToFile(String encodedData, String header, File file) throws IOException {
       FileWriter fw = new FileWriter(compressedFile);
       fw.write(header);
       fw.write(encodedData);
       fw.close();
    }

    public static void decode() throws IOException {
       huff.decodeFromFile(compressedFile, outputFile);
    }

}
