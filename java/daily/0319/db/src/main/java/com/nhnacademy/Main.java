package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        // Game game = new Game();
        // game.init();
        // game.start();

        // Scanner scanner = new Scanner(System.in);

        Options options = new Options();

        Option add = Option.builder("a")
                .longOpt("add")
                .hasArg()
                .desc("Add Data")
                .build();
        options.addOption(add);

        Option type = Option.builder("t")
                .longOpt("type")
                .hasArg()
                .desc("Specify the type of data")
                .build();
        options.addOption(type);

        Option id = Option.builder("i")
                .longOpt("id")
                .hasArg()
                .build();
        options.addOption(id);

        Option name = Option.builder("n")
                .longOpt("name")
                .hasArg()
                .build();
        options.addOption(name);

        Option list = Option.builder("l")
                .longOpt("list")
                .desc("Show list of <type> objects")
                .build();
        options.addOption(list);

        Option count = Option.builder("c")
                .longOpt("count")
                .desc("Count")
                .build();
        options.addOption(count);

        Option win = Option.builder("W")
                .longOpt("Win")
                .desc("Win count")
                .build();
        options.addOption(win);

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Help")
                .build();
        options.addOption(help);

        Option energy = Option.builder("e")
                .longOpt("energy")
                .desc("Energy")
                .build();
        options.addOption(energy);

        Option attack = Option.builder("a")
                .longOpt("attack")
                .desc("Attack")
                .build();
        options.addOption(attack);

        Option remove = Option.builder("r")
                .longOpt("remove")
                .hasArg()
                .build();
        options.addOption(remove);

        Option defence = Option.builder("d")
                .longOpt("defence")
                .hasArg()
                .build();
        options.addOption(defence);

        Option movingSpeed = Option.builder("m")
                .longOpt("moving-speed")
                .hasArg()
                .build();
        options.addOption(movingSpeed);

        Option attackSpeed = Option.builder("A")
                .longOpt("attack-speed")
                .hasArg()
                .build();
        options.addOption(attackSpeed);

        Option history = Option.builder("L")
                .longOpt("history")
                .hasArg()
                .build();
        options.addOption(history);

        Option dbFile = Option.builder("f")
                .longOpt("db-file")
                .hasArg()
                .build();
        options.addOption(dbFile);

        // System.out.print("command : ");
        // String commandLine = scanner.nextLine();
        // CommandLine cmd = parser.parse(options, commandLine.split(" "));
        // String[] input = cmd.getArgs();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(("h"))) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("db", options);
            System.exit(0);
        }
        if (cmd.hasOption(list.getOpt())) {
            // showUsers();
        }
        if (cmd.hasOption(add.getOpt())) {
            // addUserCommand(input);
        }
        if (cmd.hasOption(remove.getOpt())) {
            // removeUserCommand(input);
        }
    }
}