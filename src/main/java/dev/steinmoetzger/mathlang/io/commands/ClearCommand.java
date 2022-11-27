/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.io.commands;

import dev.steinmoetzger.mathlang.exceptions.MLCommandError;

public class ClearCommand implements Command{
    @Override
    public void execute(String[] args, boolean accessedInConsole) throws MLCommandError {
        for(int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    @Override
    public String description() {
        return "clears the screen";
    }
}
