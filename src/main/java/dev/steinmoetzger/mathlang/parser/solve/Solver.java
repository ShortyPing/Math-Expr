/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.parser.solve;

import dev.steinmoetzger.mathlang.exceptions.MLException;
import dev.steinmoetzger.mathlang.exceptions.MLSolveException;
import dev.steinmoetzger.mathlang.parser.ast.BinaryNode;
import dev.steinmoetzger.mathlang.parser.ast.BinaryOperation;
import dev.steinmoetzger.mathlang.parser.ast.ImmediateNode;
import dev.steinmoetzger.mathlang.parser.ast.Node;

public class Solver {

    public double solve(Node node) throws MLSolveException {
        if(node instanceof ImmediateNode cast)
            return cast.getValue();
        if(node instanceof BinaryNode cast)
            return solveBinary(cast);
        return 0.0;
    }

    private double solveBinary(BinaryNode node) throws MLSolveException {
        double l = solve(node.getLeft());
        double r = solve(node.getRight());
        if(node.getOperation() == BinaryOperation.DIV && r == 0) {
            throw new MLSolveException("Do you think you can divide by zero? Go to school!");
        }

        return switch (node.getOperation()) {
            case ADD -> l + r;
            case MULT -> l * r;
            case DIV -> l / r;
            case SUB -> l - r;
            case POW -> Math.pow(l, r);
            case NOT_DEFINED -> 0.0;
        };
    }
}
