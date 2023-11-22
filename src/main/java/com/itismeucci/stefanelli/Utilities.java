package com.itismeucci.stefanelli;

import java.util.Scanner;

public class Utilities {

    public static void emptyScanner(Scanner scanner) {
        
        scanner.nextLine();
    }

    public static String colorFromString(String color) {

        switch (color.toLowerCase()) {
            
            case "red":
            case "r":
                return red;
            
            case "green":
            case "g":
            case "gr":
                return green;
            
            case "yellow":
            case "gold":
            case "y":
                return yellow;

            case "blue":
            case "b":
            case "bl":
                return blue;
            
            case "purple":
            case "p":
            case "prpl":
            case "pur":
                return purple;

            case "cyan":
            case "c":
            case "cy":
                return cyan;

            case "white":
            case "w":
            case "wh":
            case "default":
            case "def":
            case "d":
                return white;
        
            default:
                return "err";
        }
    }

    public static final String reset = "\u001B[0m";
    public static final String black = "\u001B[30m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String white = "\u001B[37m";
}
