package com.daisy.barcode_software.core;

import static com.daisy.barcode_software.core.Code39Constants.CHARACTER_SET;
import static com.daisy.barcode_software.core.Code39Constants.CODE_39_TABLE;

public class Code39Encoder extends Code39Base {
    private final String rawData;
    private String encodedData;

    public Code39Encoder(String rawData) {
        this.rawData = rawData;
    }

    public String encode() throws Exception {
        if (!rawData.matches("[0-9A-Z. \\-$/+%]*")) {
            throw new Exception("Input data contains unsupported characters.");
        }

        String closingBinary = CODE_39_TABLE[CODE_39_TABLE.length - 1];
        char delimiter = '0';

        int patternLength = closingBinary.length() +
                closingBinary.length() +
                (10 * rawData.length()) +
                (checkOption == CheckDigit.MOD43 ? 10 : 0);

        StringBuilder dest = new StringBuilder(patternLength);
        dest.append(closingBinary);
        dest.append(delimiter);

        int counter = 0;

        for (int i = 0; i < rawData.length(); i++) {
            char c = rawData.charAt(i);
            int index = getPositionByChar(c);
            dest.append(CODE_39_TABLE[index]);
            dest.append(delimiter);
            counter += index;
        }

        if (checkOption == CheckDigit.MOD43) {
            counter = counter % 43;
            checkDigit = CHARACTER_SET[counter];
            int index = getPositionByChar(checkDigit);
            dest.append(CODE_39_TABLE[index]);
            dest.append(delimiter);
            if (checkDigit == ' ') {
                // display a space check digit as _, otherwise it looks like an error
                checkDigit = '_';
            }
        }

        dest.append(closingBinary);

        if (checkOption == CheckDigit.MOD43) {
            readable = "*" + rawData + checkDigit + "*";
        } else {
            readable = "*" + rawData + "*";
        }

        encodedData = dest.toString();
        return encodedData;
    }

    public String getEncodedData() {
        return encodedData;
    }

    private int getPositionByChar(char c) {
        int index = -1;
        for (int i = 0; i < Code39Constants.CHARACTER_SET.length; ++i) {
            if (Code39Constants.CHARACTER_SET[i] == c) {
                index = i;
                break;
            }
        }
        return index;
    }
}
