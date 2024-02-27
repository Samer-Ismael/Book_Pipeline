package com.BookPipeline.BookPipeline.login.filter;


public class PasswordValidator {

    public static String validatePassword(String password) {
        StringBuilder errors = new StringBuilder();

        if (password.length() < 8) {
            errors.append("Password must be at least 8 characters long. ");
        } else {
            if (!containsUppercase(password)) {
                errors.append("Password must contain at least one uppercase letter.\n");
            }

            if (!containsLowercase(password)) {
                errors.append("Password must contain at least one lowercase letter.\n");
            }

            if (!containsDigit(password)) {
                errors.append("Password must contain at least one digit.\n");
            }

            if (!containsSpecialCharacter(password)) {
                errors.append("Password must contain at least one special character.\n");
            }
        }
        return errors.toString().trim();
    }

    private static boolean containsUppercase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsLowercase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsDigit(String password) {
        return password.matches(".*\\d.*");
    }

    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[^a-zA-Z0-9].*");
    }
}
