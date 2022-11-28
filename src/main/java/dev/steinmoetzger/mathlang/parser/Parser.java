/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.parser;

import dev.steinmoetzger.mathlang.exceptions.MLException;
import dev.steinmoetzger.mathlang.parser.ast.BinaryOperation;
import dev.steinmoetzger.mathlang.parser.ast.Node;
import dev.steinmoetzger.mathlang.parser.tokenizer.TokenType;
import dev.steinmoetzger.mathlang.parser.tokenizer.Tokenizer;

public class Parser {

    private Tokenizer tokenizer;

    public Parser(String s) {
        this.tokenizer = new Tokenizer(s);
    }

    public Node parse() throws MLException {
        Node baseNode;
        System.out.println(tokenizer.current());
        if(tokenizer.current().getType() != TokenType.NUMBER &&
                tokenizer.current().getType() != TokenType.L_PAR &&
                tokenizer.current().getType() != TokenType.R_PAR) {
            throw new MLException("Expected math expression [functions not supported (yet)]");
        }
        baseNode = parseStageOne();
        return baseNode;

    }

    public Node parseStageOne() {
        Node left = parseStageTwo();

        int i = 5;
        

        return null;
    }

    public Node parseStageTwo() {
        return null;
    }


}
