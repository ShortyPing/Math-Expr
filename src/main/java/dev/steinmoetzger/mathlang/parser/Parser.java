/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.parser;

import dev.steinmoetzger.mathlang.Main;
import dev.steinmoetzger.mathlang.exceptions.MLException;
import dev.steinmoetzger.mathlang.exceptions.MLSolveException;
import dev.steinmoetzger.mathlang.parser.ast.BinaryNode;
import dev.steinmoetzger.mathlang.parser.ast.BinaryOperation;
import dev.steinmoetzger.mathlang.parser.ast.ImmediateNode;
import dev.steinmoetzger.mathlang.parser.ast.Node;
import dev.steinmoetzger.mathlang.parser.solve.Solver;
import dev.steinmoetzger.mathlang.parser.tokenizer.TokenType;
import dev.steinmoetzger.mathlang.parser.tokenizer.Tokenizer;

public class Parser {

    private Tokenizer tokenizer;

    public Parser(String s) {
        this.tokenizer = new Tokenizer(s);
    }

    public Node parse() throws MLException, MLSolveException {
        Node baseNode;

        if (tokenizer.current().getType() == TokenType.IDENTIFIER && tokenizer.getNext().getType() == TokenType.EQUALS) {
            return parseVariable();
        }

        if (tokenizer.current().getType() == TokenType.IDENTIFIER && tokenizer.getNext().getType() == TokenType.NOT_DEFINED) {
            return parseGetVariable();
        }
        if(tokenizer.current().getType() == TokenType.KEYWORD_UNDEF) {
            return parseUndef();
        }

        baseNode = parseStageOne();

        if (Main.instance.isAstDump())
            System.out.println(baseNode.print(0));

        return baseNode;
    }


    public Node parseUndef() throws MLException {
        this.tokenizer.next();
        String name = this.tokenizer.current().getValue();
        System.out.println(name);
        if(!Main.instance.getUniverseManager().getCurrentUniverse().getDefinedVariables().containsKey(name))
            throw new MLException("Expected defined variable on undef command.");
        Main.instance.getUniverseManager().getCurrentUniverse().getDefinedVariables().remove(name);
        System.out.println("Undefined Variable (" + name + ")");

        return new ImmediateNode(0);
    }

    public Node parseGetVariable() throws MLException {
        String name = this.tokenizer.current().getValue();

        if (!Main.instance.getUniverseManager().getCurrentUniverse().getDefinedVariables().containsKey(name))
            throw new MLException("Invalid expression or undefined variable");

        return new ImmediateNode(Main.instance.getUniverseManager().getCurrentUniverse().getDefinedVariables().get(name));
    }

    public Node parseVariable() throws MLException, MLSolveException {
        String name = this.tokenizer.current().getValue();
        this.tokenizer.next();
        this.tokenizer.next();
        if (this.tokenizer.current().getType() == TokenType.NOT_DEFINED)
            throw new MLException("Value expected");

        Node value = parseStageOne();
        double solved = new Solver().solve(value);
        Main.instance.getUniverseManager().getCurrentUniverse().getDefinedVariables().put(name, solved);
        System.out.println("Variable Definition (" + name + " -> " + solved + ")");
        return new ImmediateNode(solved);
    }

    public Node parseStageOne() throws MLException {
        Node left = parseStageTwo();


        if (tokenizer.getNext().getType() == TokenType.ADD || tokenizer.getNext().getType() == TokenType.SUB)
            this.tokenizer.next();

        while (tokenizer.current().getType() == TokenType.ADD || tokenizer.current().getType() == TokenType.SUB) {
            BinaryOperation operation = BinaryOperation.fromToken(tokenizer.current());

            this.tokenizer.next();

            if (this.tokenizer.current().getType() == TokenType.NOT_DEFINED)
                throw new MLException("Expected valid expression.");

            Node right = parseStageTwo();
            left = new BinaryNode(left, right, operation);

            if (tokenizer.getNext().getType() == TokenType.ADD || tokenizer.getNext().getType() == TokenType.SUB)
                this.tokenizer.next();
        }
        return left;
    }

    public Node parseStageTwo() throws MLException {
        Node left = parseStageThree();


        if (this.tokenizer.getNext().getType() == TokenType.MULT || this.tokenizer.getNext().getType() == TokenType.DIV)
            this.tokenizer.next();

        while (this.tokenizer.current().getType() == TokenType.MULT || this.tokenizer.current().getType() == TokenType.DIV) {
            BinaryOperation operation = BinaryOperation.fromToken(tokenizer.current());

            this.tokenizer.next();

            if (this.tokenizer.current().getType() == TokenType.NOT_DEFINED)
                throw new MLException("Expected valid expression.");

            Node right = parseStageThree();
            left = new BinaryNode(left, right, operation);

            if (this.tokenizer.getNext().getType() == TokenType.MULT || this.tokenizer.getNext().getType() == TokenType.DIV) {
                this.tokenizer.next();
            }


        }
        return left;
    }

    public Node parseStageThree() throws MLException {
        Node left = atomize();


        if (this.tokenizer.getNext().getType() == TokenType.POW)
            this.tokenizer.next();

        while (this.tokenizer.current().getType() == TokenType.POW) {
            BinaryOperation operation = BinaryOperation.fromToken(tokenizer.current());

            this.tokenizer.next();

            if (this.tokenizer.current().getType() == TokenType.NOT_DEFINED)
                throw new MLException("Expected valid expression.");

            Node right = atomize();
            left = new BinaryNode(left, right, operation);

            if (this.tokenizer.getNext().getType() == TokenType.POW) {
                this.tokenizer.next();
            }


        }
        return left;
    }

    private Node atomize() throws MLException {
        if (tokenizer.current().getType() == TokenType.NUMBER)
            return new ImmediateNode(Double.parseDouble(tokenizer.current().getValue()));
        if (tokenizer.current().getType() == TokenType.IDENTIFIER) {
            return parseGetVariable();
        }

        if (tokenizer.current().getType() == TokenType.L_PAR) {
            this.tokenizer.next();
            Node e = parseStageOne();
            this.tokenizer.next();
            if (tokenizer.current().getType() != TokenType.R_PAR)
                throw new MLException("Expected closed parenthese");
            return e;
        }

        throw new MLException("Syntax error " + tokenizer.current());
    }


}
