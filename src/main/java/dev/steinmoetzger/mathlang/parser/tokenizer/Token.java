/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.parser.tokenizer;

public class Token {

    private String value;
    private TokenType type;
    private int idxLength;

    public Token() {
    }


    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public Token(String value, TokenType type, int idxLength) {
        this.value = value;
        this.type = type;
        this.idxLength = idxLength;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public int getIdxLength() {
        return idxLength;
    }

    public void setIdxLength(int idxLength) {
        this.idxLength = idxLength;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
