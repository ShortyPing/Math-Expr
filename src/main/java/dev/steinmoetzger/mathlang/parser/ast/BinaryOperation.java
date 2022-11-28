/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.parser.ast;

import dev.steinmoetzger.mathlang.parser.tokenizer.Token;

public enum BinaryOperation {

    ADD,
    MULT,
    DIV,
    SUB,
    POW,
    NOT_DEFINED;

    public BinaryOperation fromToken(Token token) {
        return switch (token.getType()) {
            case ADD -> ADD;
            case DIV -> DIV;
            case MULT -> MULT;
            case SUB -> SUB;
            case POW -> POW;
            default -> NOT_DEFINED;
        };
    }
}
