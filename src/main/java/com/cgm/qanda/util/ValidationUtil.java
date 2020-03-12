package com.cgm.qanda.util;

public class ValidationUtil {
    public static boolean validateLength(String input) {
        if (input == null || input.length() > 256) {
            return false;
        }
        return true;
    }

    public static boolean validateAnswerFormat(String input) {
        if (input.contains("\"")) {
            return true;
        }
        return false;
    }
}
