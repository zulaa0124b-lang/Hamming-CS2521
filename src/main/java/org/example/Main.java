package org.example;

import static org.example.HammingCodeTool.decode;
import static org.example.HammingCodeTool.encode;

public class Main {
    public static void main(String[] args) {
        String originalData = "10100101101";

        System.out.println("Hamming Code");
        System.out.println("Original data (11): " + originalData);

        int[] encodedArr = encode(originalData);
        StringBuilder encodedStr = new StringBuilder();
        for (int i = 1; i <= 15; i++) {
            encodedStr.append(encodedArr[i]);
        }
        System.out.println("Encoded codeword (15): " + encodedStr.toString());

        StringBuilder receivedStr = new StringBuilder(encodedStr.toString());

        char bitToFlip = receivedStr.charAt(4);
        receivedStr.setCharAt(4, (bitToFlip == '0') ? '1' : '0');

        System.out.println("Received (15):         " + receivedStr.toString());

        String decodedData = decode(receivedStr.toString());
        System.out.println("Decoded data (11):     " + decodedData);
    }
}
