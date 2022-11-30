/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.io.commands;

import dev.steinmoetzger.mathlang.Main;
import dev.steinmoetzger.mathlang.exceptions.MLCommandError;
import dev.steinmoetzger.mathlang.memory.Universe;

public class UniverseCommand implements Command {
    @Override
    public void execute(String[] args, boolean accessedInConsole) throws MLCommandError {
        if(args.length == 0)
            throw new MLCommandError("Incomplete Command: \n\t- universe create\n\t- universe list\n\t- universe switch <id>\n\t- universe delete <id>\n\t- universe info [<id>]");

        if(args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "create" -> {
                    Universe universe = Main.instance.getUniverseManager().registerUniverse();
                    System.out.println("Universe created. Switch to it with 'universe switch " + universe.getUuid().toString() + "'.");
                }
                case "list" -> {
                    System.out.println("List of universes (" + Main.instance.getUniverseManager().getRegisteredUniverses().size() + ")");
                    Main.instance.getUniverseManager().getRegisteredUniverses().forEach((id, universe) -> {
                        System.out.println("\t" + id + " - Variables (" + universe.getDefinedVariables().size() + ")");
                    });
                }
                case "info" -> {
                    if(Main.instance.getUniverseManager().getCurrentUniverse() == null)
                        throw new MLCommandError("Currently there is no universe selected.");
                    System.out.println("Universe (" + Main.instance.getUniverseManager().getCurrentUniverse().getUuid().toString() + ")");
                    System.out.println("\t-> Variables (" + Main.instance.getUniverseManager().getCurrentUniverse().getDefinedVariables().size() + ")");
                }
            }
        }
        if(args.length == 2) {
            String id = args[1];
            if(!Main.instance.getUniverseManager().getRegisteredUniverses().containsKey(id))
                throw new MLCommandError("Universe with id " + id + " does not exist.");
            switch (args[0].toLowerCase()) {
                case "switch" -> {
                    Universe universe = Main.instance.getUniverseManager().getCurrentUniverseNoCheck();
                    Main.instance.getUniverseManager().setCurrentUniverse(Main.instance.getUniverseManager().getRegisteredUniverses().get(id));
                    System.out.println("Switched universe (" + (universe!=null?universe.getUuid().toString():"None") + " -> " + Main.instance.getUniverseManager().getCurrentUniverseNoCheck().getUuid().toString() + ")");
                }
                case "delete" -> {
                    Main.instance.getUniverseManager().getRegisteredUniverses().remove(id);
                    System.out.println("Deleted universe (" + id + ")");
                }
                case "info" -> {
                    System.out.println("Universe (" + Main.instance.getUniverseManager().getRegisteredUniverses().get(id).getUuid().toString() + ")");
                    System.out.println("\t-> Variables (" + Main.instance.getUniverseManager().getRegisteredUniverses().get(id).getDefinedVariables().size() + ")");
                }
            }
        }
    }

    @Override
    public String description() {
        return "Helps to manage universes";
    }
}
