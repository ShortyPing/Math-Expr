/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.io;

import dev.steinmoetzger.mathlang.Main;
import dev.steinmoetzger.mathlang.exceptions.MLCommandError;
import dev.steinmoetzger.mathlang.exceptions.MLException;
import dev.steinmoetzger.mathlang.io.commands.Command;
import dev.steinmoetzger.mathlang.memory.Universe;
import dev.steinmoetzger.mathlang.parser.Parser;
import dev.steinmoetzger.mathlang.parser.ast.BinaryNode;
import dev.steinmoetzger.mathlang.parser.ast.BinaryOperation;
import dev.steinmoetzger.mathlang.parser.solve.Solver;
import dev.steinmoetzger.mathlang.parser.tokenizer.Tokenizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class REPL {

    private InputState inputState;
    private final HashMap<String, Command> registeredCommands;

    public REPL() {
        this.inputState = InputState.COMMAND;
        this.registeredCommands = new HashMap<>();
    }


    public void start() {
        System.out.println("accessing read-eval-print loop...\\");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to MathLang by Michael Steinmötzger\nType 'help' for a list of all commands!");
        int clearNotice = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (clearNotice > 20) {
                System.out.println("Tip: if you want to clear the screen use the 'clear' command! :)");
                clearNotice = 0;
            }
            if (line.isEmpty()) {
                clearNotice++;
                continue;
            }


            if (inputState == InputState.COMMAND) {
                try {
                    this.validateCommand(line);
                } catch (MLException e) {
                    System.out.println("An error occurred while executing input: " + e.getMessage());
                }
            } else if (inputState == InputState.CONSOLE) {
                if (line.startsWith(".")) {
                    try {
                        this.validateCommand(line.substring(1));

                    } catch (MLException e) {
                        System.out.println("An error occurred while executing input: " + e.getMessage());
                    }
                    continue;
                }


                Parser parser = new Parser(line);
                Solver solver = new Solver();
                try {

                    System.out.println("[=] " + solver.solve(parser.parse()));
                } catch (MLException e) {
                    System.out.println("An error occurred while parsing expression: " + e.getMessage());
                }
            }
        }

    }

    public void registerCommand(String name, Command command) {
        this.registeredCommands.put(name.toLowerCase(), command);
    }

    private void validateCommand(String cmd) throws MLException {
        String[] args = cmd.split(" ");
        String command = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        if (!this.registeredCommands.containsKey(command.toLowerCase()))
            throw new MLException("Command '" + command + "' not found... type 'help' for help.");

        Command c = this.registeredCommands.get(command.toLowerCase());

        try {
            c.execute(args, inputState == InputState.CONSOLE);
        } catch (MLCommandError e) {
            throw new MLException("Command '" + command + "' threw error: " + e.getMessage());
        }

    }

    public HashMap<String, Command> getRegisteredCommands() {
        return registeredCommands;
    }

    public void toggleInputState() {
        if (inputState == InputState.COMMAND) {
            inputState = InputState.CONSOLE;
            System.out.println("Input-mode console activated! Any input will be parsed as an mathematical expression.\nIn order to enter a command enter it like this: '.help'\nIn order to exit console mode type '.console'\n\n");
            Main.instance.getUniverseManager().setCurrentUniverse(Main.instance.getUniverseManager().registerUniverse());
            return;
        }

        inputState = InputState.COMMAND;
        System.out.println("Input-mode command activated! Any input will be parsed as a command.\nIn order to enter console mode enter 'console'");
        Main.instance.getUniverseManager().unregisterUniverse(Main.instance.getUniverseManager().getCurrentUniverse());
    }
}
