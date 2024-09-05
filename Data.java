package com.algo.Huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data {
    // private final File file = new File("/home/ahmad/Desktop/java/Projects/Algorithms/src/main/java/com/algo/Huffman/input.txt");
    private int[] freq;
    private double fileSize = 0;

    public int[] calculateFreq(File file) throws IOException {
        freq = new int[256];  
        BufferedReader reader = new BufferedReader(new FileReader(file), 1024);  

        int character;
        while ((character = reader.read()) != -1) {
            if (character == '\n') {
                freq['\n']++;
            } else if (character == ' ') {
                freq[' ']++;
            } else if (character == '\t') {
                freq['\t']++;
            } else if (character >= 0 && character < freq.length) {
                freq[character]++;
            }
        }
        reader.close();

        return freq;
    }

    public void printFreq(int[] freq) {
        for(int i = 0; i < freq.length; i++) {
            if(freq[i] > 0)
                System.out.println("Character: " + (char) i + " Frequency: " + freq[i]);
            fileSize++;
        }
    }

    public void setFreq(int[] freq) {
        this.freq = freq;
    }

    public int[] getFreq() {
        return freq;
    }

    public double getFileSize() {
        return fileSize;
    }
}
   
