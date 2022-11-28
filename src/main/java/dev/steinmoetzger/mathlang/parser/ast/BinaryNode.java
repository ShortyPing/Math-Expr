/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.parser.ast;

public class BinaryNode extends Node implements PrintableNode {

    private Node left;
    private Node right;
    private BinaryOperation operation;

    public BinaryNode(Node left, Node right, BinaryOperation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public BinaryOperation getOperation() {
        return operation;
    }

    public void setOperation(BinaryOperation operation) {
        this.operation = operation;
    }

    @Override
    public String print(int pad) {
        String string = "\t".repeat(pad) + operation.toString() + "<BinaryNode> -> " + "\n";
        pad++;
        string += "\t".repeat(pad) + "Left -> \n";
        pad++;
        string += left.print(pad);
        pad--;
        string += "\t".repeat(pad) + "Right -> \n";
        pad++;
        string += right.print(pad);




        return string;
    }
}
