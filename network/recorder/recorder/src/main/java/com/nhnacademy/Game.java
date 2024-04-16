package com.nhnacademy;

import java.util.Scanner;

public class Game {
    private static Scanner scanner = new Scanner(System.in);
    DB db;

    public Game() {
        db = new DB();
        db.read();
    }

    public static String[] readCommand() {
        System.out.print("command: ");
        return scanner.nextLine().split(" ");
    }

    public void init() {
        String[] command;
        while ((command = readCommand()) != null) {
            // user
            if (command[0].equals("user")) {
                if (command[1].equals("add")) {
                    createUser(command[2], command[3]);
                    continue;
                }
                if (command[1].equals("print")) {
                    db.printUsers();
                }
            }

            // item
            else if (command[0].equals("item")) {
                if (command[1].equals("add")) {
                    createItem(command[2], command[3], command[4], command[5], command[6], command[7], command[8]);
                    continue;
                }
                if (command[1].equals("print")) {
                    db.printItems();
                }

            }

            // save
            else if (command[0].equals("save")) {
                db.save();
            }

            // read
            else if (command[0].equals("read")) {
                db.read();
            }

            else if (command[0].equals("start")) {
                break;
            }
        }
    }

  

    public void createUser(String id, String name) {
        db.addUser(new User(id, name));
    }

    public void createItem(String id, String name, String health, String attack, String defense, String speed,
            String attackSpeed) {
        db.addItem(new Item(id, name, Integer.parseInt(health), Integer.parseInt(attack),
                Integer.parseInt(defense), Integer.parseInt(speed), Integer.parseInt(attackSpeed)));
    }



}
