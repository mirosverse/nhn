package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
        public static void main(String[] args) throws ParseException {
                String host = "localhost";
                int port = 12345;

                Options options = new Options();

                Option serverOption = Option.builder("l")
                                .longOpt("list")
                                .desc("server로 동작시 입력 받은 port로 listen")
                                .build();
                options.addOption(serverOption);

                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(options, args);

                port = Integer.parseInt(args[args.length - 1]);

                if (cmd.hasOption("l")) {
                        Thread mode = new Thread(new Server(host, port));
                        mode.start();
                } else {
                        Thread mode = new Thread(new Client(host, port));
                        mode.start();
                }

        }
}