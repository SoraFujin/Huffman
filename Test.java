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
    }


    public static void encodeData() throws IOException{
       huff.calculateFreq();  
       String encodedData = huff.encode();
       String header = huff.header();
       writeToFile(encodedData, header, compressedFile);
    }

         
    public static void writeToFile(String encodedData, String header, File file) throws IOException {
       FileWriter fw = new FileWriter(file);
       fw.write(header);
       fw.write("00000");
       fw.write(encodedData);
       fw.close();
    }

}
