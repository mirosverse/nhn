package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class OptionHandler {
	public static void main(String[] args) throws ParseException {
		int port = 8080;

		Options options = new Options();
		options.addOption("p", "port", true, "Port number");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		if (cmd.hasOption("p")) {
			port = Integer.parseInt(cmd.getOptionValue(options.getOption("p")));
		}
		Thread serverThread = new Thread(new Server(port));
		serverThread.start();
	}
}
