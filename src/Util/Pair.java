package Util;

/**
 * A generic class for pairing 2 objects
 * @param <U> First object type
 * @param <V> Second object type
 */
public class Pair<U, V> {
    public final U first;
    public final V second;

    /**
     * Constructor for Pair generic class
     * @param first First object of the pair
     * @param second Second object of the pair
     */
    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }
}
