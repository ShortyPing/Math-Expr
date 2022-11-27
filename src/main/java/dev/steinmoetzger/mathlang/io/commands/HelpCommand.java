/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.io.commands;

import dev.steinmoetzger.mathlang.Main;
import dev.steinmoetzger.mathlang.exceptions.MLCommandError;

public class HelpCommand implements Command{
    @Override
    public void execute(String[] args, boolean accessedInConsole) throws MLCommandError {
        System.out.println("\nList of all commands:");
        Main.instance.getRepl().getRegisteredCommands().forEach((name, command) -> {
            System.out.println("  " + name + " <-> " + command.description());
        });
        System.out.println("\n");
    }

    @Override
    public String description() {
        return "Shows a list of all commands.";
    }
}
