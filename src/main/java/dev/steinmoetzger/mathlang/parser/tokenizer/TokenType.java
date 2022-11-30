/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.parser.tokenizer;

public enum TokenType {

    IDENTIFIER,
    STRING_LITERAL,
    NUMBER,

    L_PAR,
    R_PAR,

    EQUALS,
    FUNC_CALL,
    COMMA,

    ADD,
    SUB,
    MULT,
    DIV,
    POW,
    NOT_DEFINED,

    KEYWORD_UNDEF

}
