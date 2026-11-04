package org.example;

public class HammingCodeTool {

    // Data bit positions (1-indexed)
    private static final int[] DATA_POSITIONS = {3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15};

    public static int[] encode(String data11) {
        int[] codeword = new int[16]; // Index 0 unused, indices 1-15 used

        // place data bits into their correct positions
        for (int i = 0; i < 11; i++) {
            codeword[DATA_POSITIONS[i]] = Character.getNumericValue(data11.charAt(i));
        }

        // calculate parity bits for positions 1, 2, 4, 8
        int count = 0;

        if (codeword[3] == 1) count++;
        if (codeword[5] == 1) count++;
        if (codeword[7] == 1) count++;
        if (codeword[9] == 1) count++;
        if (codeword[11] == 1) count++;
        if (codeword[13] == 1) count++;
        if (codeword[15] == 1) count++;

        if (count % 2 != 0) {
            codeword[1] = 1;
        } else {
            codeword[1] = 0;
        }
        count = 0;
        if (codeword[3] == 1) count++;
        if (codeword[6] == 1) count++;
        if (codeword[7] == 1) count++;
        if (codeword[10] == 1) count++;
        if (codeword[11] == 1) count++;
        if (codeword[14] == 1) count++;
        if (codeword[15] == 1) count++;

        if (count % 2 != 0) {
            codeword[2] = 1;
        } else {
            codeword[2] = 0;
        }

        count = 0;

        if (codeword[5] == 1) count++;
        if (codeword[6] == 1) count++;
        if (codeword[7] == 1) count++;
        if (codeword[12] == 1) count++;
        if (codeword[13] == 1) count++;
        if (codeword[14] == 1) count++;
        if (codeword[15] == 1) count++;

        if (count % 2 != 0) {
            codeword[4] = 1;
        } else {
            codeword[4] = 0;
        }

        count = 0;
        if (codeword[9] == 1) count++;
        if (codeword[10] == 1) count++;
        if (codeword[11] == 1) count++;
        if (codeword[12] == 1) count++;
        if (codeword[13] == 1) count++;
        if (codeword[14] == 1) count++;
        if (codeword[15] == 1) count++;

        if (count % 2 != 0) {
            codeword[8] = 1;
        } else {
            codeword[8] = 0;
        }
        return codeword;

    }
    public static String decode(String received15) {
        int[] received = new int[16];
        for (int i = 0; i < 15; i++) {
            received[i + 1] = Character.getNumericValue(received15.charAt(i));
        }

        // Calculate check bits (C1, C2, C4, C8)
        int c1 = 0, c2 = 0, c4 = 0, c8 = 0;

        // C1 checks: 1, 3, 5, 7, 9, 11, 13, 15
        int count = 0;
        if (received[1] == 1) count++;
        if (received[3] == 1) count++;
        if (received[5] == 1) count++;
        if (received[7] == 1) count++;
        if (received[9] == 1) count++;
        if (received[11] == 1) count++;
        if (received[13] == 1) count++;
        if (received[15] == 1) count++;
        if (count % 2 != 0) c1 = 1;

        // C2 checks: 2, 3, 6, 7, 10, 11, 14, 15
        count = 0;
        if (received[2] == 1) count++;
        if (received[3] == 1) count++;
        if (received[6] == 1) count++;
        if (received[7] == 1) count++;
        if (received[10] == 1) count++;
        if (received[11] == 1) count++;
        if (received[14] == 1) count++;
        if (received[15] == 1) count++;
        if (count % 2 != 0) c2 = 1;

        // C4 checks: 4, 5, 6, 7, 12, 13, 14, 15
        count = 0;
        if (received[4] == 1) count++;
        if (received[5] == 1) count++;
        if (received[6] == 1) count++;
        if (received[7] == 1) count++;
        if (received[12] == 1) count++;
        if (received[13] == 1) count++;
        if (received[14] == 1) count++;
        if (received[15] == 1) count++;
        if (count % 2 != 0) c4 = 1;

        // C8 checks: 8, 9, 10, 11, 12, 13, 14, 15
        count = 0;
        if (received[8] == 1) count++;
        if (received[9] == 1) count++;
        if (received[10] == 1) count++;
        if (received[11] == 1) count++;
        if (received[12] == 1) count++;
        if (received[13] == 1) count++;
        if (received[14] == 1) count++;
        if (received[15] == 1) count++;
        if (count % 2 != 0) c8 = 1;

        // Form the syndrome
        int syndrome = (c8 * 8) + (c4 * 4) + (c2 * 2) + c1;
        System.out.println("Syndrome: " + c8 + c4 + c2 + c1 + " (decimal = " + syndrome + ")");

        // correct the single-bit error if syndrome > 0
        if (syndrome > 0 && syndrome <= 15) {
            received[syndrome] = (received[syndrome] == 0) ? 1 : 0; // flip the bit
        }

        // print corrected codeword
        StringBuilder correctedCodeword = new StringBuilder();
        for (int i = 1; i <= 15; i++) {
            correctedCodeword.append(received[i]);
        }
        System.out.println("Corrected codeword: " + correctedCodeword.toString());

        // extract and return the 11 data bits
        StringBuilder decodedData = new StringBuilder();
        for (int pos : DATA_POSITIONS) {
            decodedData.append(received[pos]);
        }

        return decodedData.toString();
    }
}