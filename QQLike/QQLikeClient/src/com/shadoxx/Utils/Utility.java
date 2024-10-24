package com.shadoxx.Utils;

import java.util.Scanner;

public class Utility {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     *A menu option to read keyboard input, with values ranging from 1 to 5
     * @return 1 To 5
     */
    public static char readMenuSelection() {
        char c;
        for (; ; ) {
            //A string containing one character
            String str = readKeyBoard(1, false);
            //Convert a string to a character char type
            c = str.charAt(0);
            if (c != '1' && c != '2' &&
                    c != '3' && c != '4' && c != '5') {
                System.out.print("Select Error, please re-enter:");
            } else {
                break;
            }
        }
        return c;
    }

    /**
     * Read a character entered on the keyboard
     * @return One character
     */
    public static char readChar() {
        //It's just a character
        String str = readKeyBoard(1, false);
        return str.charAt(0);
    }
    /**
     * Reads a character entered by the keyboard, and if you press enter directly, returns the specified default value; Otherwise, the character entered is returned
     * @param defaultValue The default value specified
     * @return Default value or entered character
     */

    public static char readChar(char defaultValue) {
        //Either it's an empty string or a character
        String str = readKeyBoard(1, true);
        return (str.isEmpty()) ? defaultValue : str.charAt(0);
    }

    /**
     * Reads the integer input on the keyboard, which is less than 2 digits in length
     * @return a Integer
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            //An integer with length less than or equal to 10 digits
            String str = readKeyBoard(10, false);
            try {
                //Convert strings to integers
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("The number is entered incorrectly, please re-enter:");
            }
        }
        return n;
    }
    /**
     * Reads the integer or default value entered by the keyboard, returns the default value if you press enter directly, and returns the whole value entered otherwise
     * @param defaultValue The default value specified
     * @return Integer or default
     */
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, true);
            if (str.isEmpty()) {
                return defaultValue;
            }

            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("The number is entered incorrectly, please re-enter:");
            }
        }
        return n;
    }

    /**
     * Reads a string of specified lengths entered by the keyboard
     * @param limit The length of the limit
     * @return A string of specified length
     */

    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * Reads a string or default value of a specified length entered by the keyboard, and returns the default value if you press enter directly, otherwise returns a string
     * @param limit the length of the limit
     * @param defaultValue The default value specified
     * @return A string of specified length
     */

    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.isEmpty() ? defaultValue : str;
    }


    /**
     * Read the confirmation option for keyboard input, Y or N
     * @return Y or N
     */
    public static char readConfirmSelection() {
        char c;
        for (; ; ) {
            //The accepted characters are converted into capital letters
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("If you selected the wrong one, please re-enter:");
            }
        }
        return c;
    }

    public static void waitToInputEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Read a string
     * @param limit The length of the read
     * @param blankReturn If true, it means that the string can be read empty.
     * 					  If it is false, it means that the string cannot be read empty.
     *
     *	If the input is empty, or if the input is longer than the limit, it will prompt you to re-enter.
     */
    private static String readKeyBoard(int limit, boolean blankReturn) {
        String line = "";

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                if (blankReturn) return line;
                else continue;
            }

            if (line.length() > limit) {
                System.out.print("Input length (cannot be greater than " + limit + ") error, please re-enter:");
                continue;
            }
            break;
        }
        return line;
    }

    public static void close() {
        scanner.close();
    }
}