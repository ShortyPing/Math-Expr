/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.parser.ast;

import java.awt.geom.Dimension2D;

public class ImmediateNode extends Node {

    private double value;

    public ImmediateNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String print(int pad) {
        return "\t".repeat(pad) + "Immediate Value (" + value + ")\n";
    }
}
