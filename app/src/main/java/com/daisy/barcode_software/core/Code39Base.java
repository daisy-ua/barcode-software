package com.daisy.barcode_software.core;

public class Code39Base {
    public enum CheckDigit {
        NONE, MOD43
    }

    public CheckDigit checkOption = CheckDigit.MOD43;

    protected char checkDigit = ' ';

    public char getCheckDigit() {
        return checkDigit;
    }

    protected String readable;

    public String getReadable() {
        return readable;
    }
}
