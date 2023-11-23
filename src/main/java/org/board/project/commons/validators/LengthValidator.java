package org.board.project.commons.validators;

public interface LengthValidator {
    default void lengthCheck(String str, int min, int max, RuntimeException e) {
        if (str == null || (min > 0 && str.length() < min) || (max > 0 && str.length() > max)) {
            throw e;
        }
    }

    default void lengthCheck(String str, int min, RuntimeException e) {
        lengthCheck(str, min, 0, e);
    }

    default void lengthCheck(long num, int min, int max, RuntimeException e) {
        if (num < min || num > max) {
            throw e;
        }
    }

    default void lengthCheck(long num, int min, RuntimeException e) {
        if (num < min) {
            throw e;
        }
    }
}
