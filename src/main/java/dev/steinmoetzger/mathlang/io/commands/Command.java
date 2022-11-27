/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.io.commands;

import dev.steinmoetzger.mathlang.exceptions.MLCommandError;

public interface Command {

    void execute(String[] args, boolean accessedInConsole) throws MLCommandError;
    String description();
}
