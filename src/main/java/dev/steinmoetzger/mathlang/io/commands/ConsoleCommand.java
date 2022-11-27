/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.io.commands;

import dev.steinmoetzger.mathlang.Main;
import dev.steinmoetzger.mathlang.exceptions.MLCommandError;

public class ConsoleCommand implements Command {
    @Override
    public void execute(String[] args, boolean accessedInConsole) throws MLCommandError {
        Main.instance.getRepl().toggleInputState();
    }

    @Override
    public String description() {
        return "Toggles console-mode.";
    }
}
