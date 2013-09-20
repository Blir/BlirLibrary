package blir.util;

import java.util.ArrayList;
import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;
import static java.lang.String.valueOf;
import static java.util.Arrays.copyOf;

/**
 *
 * @author Blir
 * @deprecated
 */
public class Digits {

    private long num;
    private int[] digits;

    public Digits(long num) {
        this.num = num;
        digits = toDigits(num);
    }

    public Digits(int num) {
        this.num = num;
        digits = toDigits(num);
    }

    public Digits(int[] digits) {
        this.digits = copyOf(digits, digits.length);
        num = toLong(this.digits);
    }

    private Digits(long num, int[] digits) {
        this.num = num;
        this.digits = copyOf(digits, digits.length);
    }

    public int[] asDigits() {
        return digits;
    }

    public int[] asOrganizedDigits(Order order) {
        return organize(digits, order);
    }

    public long asLong() {
        return num;
    }

    public int asInteger() {
        return (int) num;
    }

    public Digits toOrganization(Order order) {
        return new Digits(organize(digits, order));
    }

    public void reOrganize(Order order) {
        digits = organize(digits, order);
        num = toLong(digits);
    }

    @Override
    public Digits clone() {
        return new Digits(num, digits);
    }

    public static enum Order {

        GREATEST_TO_LEAST, LEAST_TO_GREATEST, REVERSE
    }

    public static int[] toDigits(long number) {
        String numberAsString = valueOf(number);
        int[] digits = new int[numberAsString.length()];
        for (int idx = 0; idx < digits.length; idx++) {
            digits[idx] = parseInt(valueOf(numberAsString.charAt(idx)));
        }
        return digits;
    }

    public static int[] toDigits(int number) {
        String numberAsString = valueOf(number);
        int[] digits = new int[numberAsString.length()];
        for (int idx = 0; idx < digits.length; idx++) {
            digits[idx] = parseInt(valueOf(numberAsString.charAt(idx)));
        }
        return digits;
    }

    public static int[] organize(int[] digits, Order order) {
        int[] value = new int[digits.length];
        switch (order) {
            case REVERSE:
                for (int idx = 0, idx2 = digits.length - 1; idx < digits.length; idx++, idx2--) {
                    value[idx] = digits[idx2];
                }
                break;
            case GREATEST_TO_LEAST:
                ArrayList<Integer> used = new ArrayList<>();
                for (int idx = 0; idx < digits.length; idx++) {
                    int dig = -1;
                    value[idx] = 0;
                    for (int idx2 = 0; idx2 < digits.length; idx2++) {
                        if (value[idx] < digits[idx2] && !used.contains(idx2)) {
                            value[idx] = digits[idx2];
                            dig = idx2;
                        }
                    }
                    used.add(dig);
                }
                break;
            case LEAST_TO_GREATEST:
                used = new ArrayList<>();
                for (int idx = 0; idx < digits.length; idx++) {
                    int dig = -1;
                    value[idx] = 9;
                    for (int idx2 = 0; idx2 < digits.length; idx2++) {
                        if (value[idx] > digits[idx2] && !used.contains(idx2)) {
                            value[idx] = digits[idx2];
                            dig = idx2;
                        }
                    }
                    used.add(dig);
                }
                break;
        }
        return value;
    }

    public static long toLong(int[] digits) {
        long num = 0;
        for (int idx = 0; idx < digits.length; idx++) {
            num += digits[idx] * pow(10, digits.length - idx - 1);
        }
        return num;
    }

    public static int toInteger(int[] digits) {
        int num = 0;
        for (int idx = 0; idx < digits.length; idx++) {
            num += digits[idx] * pow(10, digits.length - idx - 1);
        }
        return num;
    }
}
