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
        Options options = new Options();

        Option classPath = Option.builder("classpath")
                .hasArg()
                .desc("Class Path")
                .build();
        options.addOption(classPath);

        Option module = Option.builder("m")
                .longOpt("module")
                .hasArg()
                .desc("Modules")
                .build();
        options.addOption(module);

        Option version = Option.builder("v")
                .longOpt("version")
                .desc("Version")
                .build();
        options.addOption(version);

        Option group = Option.builder("g")
                .desc("Global")
                .build();
        options.addOption(group);
        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Help")
                .build();
        options.addOption(help);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(("h"))) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ls", options);
            System.exit(0);
        }
        if (cmd.hasOption(classPath.getOpt())) {
            System.out.println("Class Path :" + classPath.getOpt().toString());
        }
        if (cmd.hasOption(module.getOpt())) {
            System.out.println("Module : ");
        }
        if (cmd.hasOption(group.getOpt())) {
            System.out.println("group : ");
        }
        if (cmd.hasOption(version.getOpt())) {
            System.out.println("version : 1.0.0");
            System.exit(0);
        }

        System.out.println(cmd.getArgList());

    }

}
