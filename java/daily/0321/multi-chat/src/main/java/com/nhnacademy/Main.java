package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
    public static void main(String[] args) {
        int serverPort = 1234;

        Options options = new Options();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Help")
                .build();
        options.addOption(help);

        Option portOption = Option.builder("p")
                .longOpt("port")
                .hasArg()
                .desc("Service Port")
                .build();
        options.addOption(portOption);

        Option clientListOption = Option.builder("cl")
                .longOpt("client-list")
                .desc("Show client list")
                .build();
        options.addOption(clientListOption);

        Option denyAddOption = Option.builder("da")
                .longOpt("deny-add")
                .hasArg()
                .desc("deny add <client_id>")
                .build();
        options.addOption(denyAddOption);

        Option denyDelOption = Option.builder("dd")
                .longOpt("deny-del")
                .hasArg()
                .desc("deny del <clent_id>")
                .build();
        options.addOption(denyDelOption);

        Option sendOffOption = Option.builder("s")
                .longOpt("send-off")
                .hasArg()
                .desc("send_off <clent_id>")
                .build();
        options.addOption(sendOffOption);

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(("h"))) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("recorder", options);
                System.exit(0);
            }

            if (cmd.hasOption("p")) {
                serverPort = Integer.parseInt(cmd.getOptionValue(portOption.getOpt()));
            }

            Server server = new Server(serverPort); // server 객체 생성
            
            if (cmd.hasOption("da")) {
                server.addDenyClient(cmd.getOptionValue(denyAddOption.getOpt()));
            }
            if (cmd.hasOption("dd")) {
                server.sendOff(cmd.getOptionValue(denyAddOption.getOpt()));
            }

            new Thread(server).start();

        } catch (Exception e) {
            //
        }

    }
}