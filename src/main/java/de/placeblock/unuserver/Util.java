package de.placeblock.unuserver;

public class Util {
    public static int modulo(int dividend, int divisor) {
        return (((dividend % divisor) + divisor) % divisor);
    }
}
