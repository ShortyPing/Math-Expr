/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang;

import dev.steinmoetzger.mathlang.io.REPL;
import dev.steinmoetzger.mathlang.io.commands.ClearCommand;
import dev.steinmoetzger.mathlang.io.commands.ConsoleCommand;
import dev.steinmoetzger.mathlang.io.commands.HelpCommand;
import dev.steinmoetzger.mathlang.memory.UniverseManager;

public class Main {

    public static Main instance;
    private REPL repl;
    private UniverseManager universeManager;

    public Main() {
        instance = this;

        this.universeManager = new UniverseManager();
        this.repl = new REPL();
        this.registerCommands();
        this.repl.start();
    }

    private void registerCommands() {
        this.repl.registerCommand("help", new HelpCommand());
        this.repl.registerCommand("console", new ConsoleCommand());
        this.repl.registerCommand("clear", new ClearCommand());
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    public REPL getRepl() {
        return repl;
    }

    public UniverseManager getUniverseManager() {
        return universeManager;
    }


}
