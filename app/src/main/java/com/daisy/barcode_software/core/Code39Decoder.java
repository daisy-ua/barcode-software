package com.daisy.barcode_software.core;

import static com.daisy.barcode_software.core.Code39Constants.CHARACTER_SET;
import static com.daisy.barcode_software.core.Code39Constants.CODE_39_TABLE;

public class Code39Decoder extends Code39Base {
    private String decodedString;

    public String decode(String binaryData) throws Exception {
        int index = 0;
        int patternLength = CODE_39_TABLE[0].length();
        StringBuilder decodedData = new StringBuilder();

        try {
            while (index < binaryData.length()) {
                String encodedChar = binaryData.substring(index, Math.min(index + patternLength, binaryData.length()));
                index += patternLength + 1;

                int position = getPositionByString(encodedChar);

                char decodedChar = CHARACTER_SET[position];

                decodedData.append(decodedChar);
            }
        } catch (Exception e) {
            throw new Exception("Input data cannot be parsed.");
        }

        if (checkOption == Code39Encoder.CheckDigit.MOD43) {
            int checkDigitIndex = decodedData.length() - 2;

            checkDigit = decodedData.charAt(checkDigitIndex);

            StringBuilder sb = new StringBuilder(decodedData.toString());
            readable = sb.deleteCharAt(checkDigitIndex).toString();

            sb.deleteCharAt(decodedData.length() - 2);
            sb.deleteCharAt(0);
            decodedString = sb.toString();
        }

        return decodedData.toString();
    }

    public String getDecodedString() {
        return decodedString;
    }

    private int getPositionByString(String c) {
        int index = -1;
        for (int i = 0; i < Code39Constants.CODE_39_TABLE.length; ++i) {
            if (Code39Constants.CODE_39_TABLE[i].equals(c)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
