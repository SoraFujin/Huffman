package com.algo.Huffman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Driver extends JFrame {
    private JFileChooser fileChooser;
    private JLabel inputSizeLabel;
    private JLabel compressedSizeLabel;
    private JLabel percentageLabel;
    private JTextArea statusArea;
    private Huffman huffman;
    private File chosenFile;

    public Driver() {
        setTitle("Huffman Coding");
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10));

        fileChooser = new JFileChooser();
        JButton chooseFileButton = new JButton("Choose File");
        JButton encodeButton = new JButton("Encode");
        JButton decodeButton = new JButton("Decode");
        inputSizeLabel = new JLabel("Input File Size: ");
        compressedSizeLabel = new JLabel("Compressed File Size: ");
        percentageLabel = new JLabel("Size Difference: ");
        statusArea = new JTextArea(5, 30); 
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("File Size Info"), gbc);

        gbc.gridy = 1;
        infoPanel.add(inputSizeLabel, gbc);

        gbc.gridy = 2;
        infoPanel.add(compressedSizeLabel, gbc);

        gbc.gridy = 3;
        infoPanel.add(percentageLabel, gbc);

        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(new JPanel(), gbc); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(chooseFileButton);
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(new JScrollPane(statusArea), BorderLayout.SOUTH);

        chooseFileButton.addActionListener(new ChooseFileAction());
        encodeButton.addActionListener(new EncodeAction());
        decodeButton.addActionListener(new DecodeAction());

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    private class ChooseFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                chosenFile = fileChooser.getSelectedFile();
                long fileSize = chosenFile.length();
                inputSizeLabel.setText("Input File Size: " + fileSize + " bytes");
                huffman = new Huffman(chosenFile);
                statusArea.setText("Selected File: " + chosenFile.getAbsolutePath());
            }
        }
    }

    private class EncodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (huffman == null) {
                statusArea.setText("No file selected for encoding.");
                return;
            }

            File compressedFile = new File(chosenFile.getParent(), "compressed.huff");
            try {
                huffman.calculateFreq();
                String encodedData = huffman.encode();
                String header = huffman.header();
                huffman.writeToFile(compressedFile, encodedData);

                double originalSize = huffman.getFileSize();
                double headerSize = huffman.getHeaderSize();
                double encodedDataSize = huffman.getCompressedFileSize() ;
                double compressedSize = headerSize + encodedDataSize;
                double percentageDifference = 100.0 * (originalSize - compressedSize) / originalSize;

                compressedSizeLabel.setText("Compressed File Size: " + compressedSize + " bytes");
                percentageLabel.setText(String.format("Size Difference: %.2f%%", percentageDifference));
                statusArea.setText("Encoding completed. Compressed file created at: " + compressedFile.getAbsolutePath());

            } catch (IOException ex) {
                statusArea.setText("Encoding failed: " + ex.getMessage());
            }
        }
    }

    private class DecodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File compressedFile = fileChooser.getSelectedFile();

                // Option for user to choose where to save the new file
                int saveOption = JOptionPane.showOptionDialog(
                        null,
                        "Do you want to choose a location for the decompressed file?",
                        "Choose Save Location",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[] {"Yes", "No"},
                        "No"
                        );

                File outputFile;
                if (saveOption == JOptionPane.YES_OPTION) {
                    JFileChooser saveFileChooser = new JFileChooser();
                    saveFileChooser.setDialogTitle("Specify where to save the decompressed file");
                    int saveReturnValue = saveFileChooser.showSaveDialog(null);
                    if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
                        outputFile = saveFileChooser.getSelectedFile();
                    } else {
                        statusArea.setText("Saving file was canceled.");
                        return;
                    }
                } else {
                    outputFile = new File(compressedFile.getParent(), "decompressed.txt");
                }

                try {
                    Huffman huffman = new Huffman(compressedFile); 
                    huffman.decodeFromFile(compressedFile, outputFile);

                    double outputSize = outputFile.length();
                    compressedSizeLabel.setText("File Size: " + outputSize + " bytes");
                    statusArea.setText("Decoding completed. Decompressed file created at: " + outputFile.getAbsolutePath());

                } catch (IOException ex) {
                    statusArea.setText("Decoding failed: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        new Driver();
    }
}
