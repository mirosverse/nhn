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
                Game game = new Game();

                Options options = new Options();

                Option add = Option.builder("a")
                                .longOpt("add")
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
                                .hasArg()
                                .desc("Energy")
                                .build();
                options.addOption(energy);

                Option attack = Option.builder("at")
                                .longOpt("attack")
                                .hasArg()
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

                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(options, args);

                if (cmd.hasOption(("h"))) {
                        HelpFormatter formatter = new HelpFormatter();
                        formatter.printHelp("recorder", options);
                        System.exit(0);
                }
                if (cmd.hasOption(("a"))) {
                        String dataType = cmd.getOptionValue(type.getOpt());
                        if (dataType.equals("user")) {
                                game.createUser(cmd.getOptionValue(id.getOpt()),
                                                cmd.getOptionValue(name.getOpt()));
                        } else if (dataType.equals("item")) {
                                game.createItem(
                                                cmd.getOptionValue(id.getOpt()),
                                                cmd.getOptionValue(name.getOpt()),
                                                cmd.getOptionValue(energy.getOpt()),
                                                cmd.getOptionValue(attack.getOpt()),
                                                cmd.getOptionValue(defence.getOpt()),
                                                cmd.getOptionValue(movingSpeed.getOpt()),
                                                cmd.getOptionValue(attackSpeed.getOpt()));
                        }
                }
                if (cmd.hasOption("l")) {
                        String dataType = cmd.getOptionValue(type.getOpt());
                        if (dataType == null) {
                                game.db.printUserDetail();
                        } else if (dataType.equals("user")) {
                                game.db.printUsers();
                        } else if (dataType.equals("item")) {
                                game.db.printItems();
                        }
                }

                if (cmd.hasOption("f")) {
                        System.out.println(game.db.gameDB.toString());
                }

                game.db.save();
        }
}