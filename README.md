# Huffman Coding Project
This project implements Huffman Coding, a popular algorithm for lossless data compression. Huffman Coding is widely used in various applications such as file compression and data encoding. This README provides an overview of Huffman Coding, explains the implementation details of this project, and describes the key classes involved.

## What is Huffman Coding?
Huffman Coding is a compression algorithm used to reduce the size of data. It works by assigning variable-length codes to characters based on their frequencies in the data. More frequent characters are assigned shorter codes, while less frequent characters are assigned longer codes. This method ensures that the overall size of the encoded data is minimized.

### How Huffman Coding Works:
**Frequency Analysis**: Analyze the frequency of each character in the data.
**Build the Huffman Tree**: Construct a binary tree where each leaf node represents a character, and the path to the leaf node represents the Huffman code.
**Generate Codes**: Traverse the Huffman Tree to generate the Huffman codes for each character.
**Encode Data**: Replace each character in the data with its corresponding Huffman code.
**Decode Data**: Use the Huffman Tree to decode the encoded data back to its original form.

## Project Overview
This project includes a graphical user interface (GUI) to encode and decode files using Huffman Coding. The key components of the project are described below:

### Key Classes
#### Huffman
The `Huffman` class contains the core logic for encoding and decoding using Huffman Coding.

**Methods**:
- `calculateFreq()`: Analyzes the frequency of each character in the input file.
- `encode()`: Encodes the input data using Huffman codes.
- `header()`: Generates a header for the encoded data.
- `writeToFile(File file, String data)`: Writes the encoded data to a file.
- `decodeFromFile(File inputFile, File outputFile)`: Decodes the data from a file and writes the result to another file.
- `getFileSize(), getHeaderSize(), getCompressedFileSize()`: Methods to retrieve sizes related to encoding.

`Driver`
The `Driver` class is a Swing-based GUI application for interacting with the Huffman Coding functionality. It provides options to choose files for encoding and decoding, and displays information about file sizes and encoding/decoding status.

**Components**:
- `JFileChooser`: Used to select files for encoding or decoding.
- `JTextArea`: Displays status messages and information about file sizes.
- `JLabels`: Show information about input file size, compressed file size, and size difference.
- `JButtons`: Provide actions for choosing files, encoding, and decoding.

**Actions**:
- `ChooseFileAction`: Handles file selection for encoding or decoding.
- `EncodeAction`: Performs the encoding operation and updates the GUI with information about the compressed file.
- `DecodeAction`: Handles the decoding process and allows users to choose where to save the decompressed file.

#### `PriorityQueue`

The `PriorityQueue` class is a custom implementation of a priority queue used in the Huffman Coding algorithm.

- **Purpose**: Manages nodes of the Huffman Tree based on priority (frequency of characters) to facilitate efficient encoding and decoding.
    
- **Methods**:
    
    - `enqueue(T data, int priority)`: Adds an element with a given priority to the priority queue.
    - `dequeue()`: Removes and returns the element with the highest priority (minimum value).
    - `peek()`: Returns the element with the highest priority without removing it.
    - `isEmpty()`: Checks if the priority queue is empty.
    - `size()`: Returns the number of elements in the priority queue.
- **Implementation Details**:
    
    - **`add(QNode<T> root, T data, int priority)`**: Recursively adds a node to the priority queue, maintaining the order based on priority.
    - **`findMin(QNode<T> node)`**: Finds the node with the minimum priority (the root of the leftmost subtree).
    - **`deleteMin(QNode<T> root)`**: Removes the node with the minimum priority from the priority queue.

#### `QNode`

The `QNode` class represents a node in the custom priority queue.

- **Attributes**:
    
    - `data`: The data stored in the node.
    - `priority`: The priority of the node.
    - `left`, `right`: Child nodes.
- **Methods**:
    
    - Getters and setters for `data`, `priority`, `left`, and `right`.
    - `toString()`: Provides a string representation of the node.

#### `Data`

The `Data` class handles the frequency analysis of characters in the input file.

- **Methods**:
    - `calculateFreq(File file)`: Calculates the frequency of each character in the file and returns an array of frequencies.
    - `printFreq(int[] freq)`: Prints the frequency of each character.
    - `setFreq(int[] freq)`: Sets the frequency array.
    - `getFreq()`: Retrieves the frequency array.
    - `getFileSize()`: Returns the size of the file.
## Usage
1. **Run the Application**: Execute the Driver class to launch the GUI.
2. **Choose File**: Click on "Choose File" to select an input file for encoding or decoding.
3. **Encode**: Click on "Encode" to compress the selected file using Huffman Coding.
4. **Decode**: Click on "Decode" to decompress a selected Huffman-compressed file. You can choose to specify a location for saving the decompressed file or use a default name.

## Example
1. **Encoding**:
- Select an input file (e.g., input.txt).
- Click "Encode" to create a compressed file (e.g., compressed.huff).

2. **Decoding**:
- Select the compressed file (e.g., compressed.huff).
- Choose whether to specify a save location or use the default (decompressed.txt).

## Dependencies
- Java Development Kit (JDK)
- Swing (for GUI)

