package com.view;


import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ReaderUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static int readInt() {
        String userNum;
        int intValue;
        do {
            try {
                userNum = readString();
                intValue = Integer.parseInt(userNum);
            } catch (NumberFormatException e) {
                log.error("Bad integer input");
                continue;
            }
            break;
        } while (true);
        return intValue;
    }

    public static String readString() {
        return scanner.nextLine();
    }
}
