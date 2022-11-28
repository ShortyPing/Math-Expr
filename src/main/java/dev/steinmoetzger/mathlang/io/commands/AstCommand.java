/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/
package dev.steinmoetzger.mathlang.io.commands;

import dev.steinmoetzger.mathlang.Main;
import dev.steinmoetzger.mathlang.exceptions.MLCommandError;

public class AstCommand implements Command{
    @Override
    public void execute(String[] args, boolean accessedInConsole) throws MLCommandError {
        if(args.length == 0)
            throw new MLCommandError("Incomplete command. Usage: ast <true/false>");

        String b = args[0];

        switch (b.toLowerCase()) {
            case "true":
                Main.instance.setAstDump(true);
                System.out.println("AST-Dump enabled.");
                break;
            case "false":
                Main.instance.setAstDump(false);
                System.out.println("AST-Dump disabled.");
                break;
            default:
                throw new MLCommandError("Argument format incorrect. Usage: ast <true/false>");
        }

    }

    @Override
    public String description() {
        return "ARGS(0: true/false) <-> enables or disables AST dump";
    }
}
