package com.example.tdd.utils.helper;

import androidx.annotation.NonNull;
import androidx.core.util.ObjectsCompat;

/**
 * Container to ease passing around a tuple of two objects. This object provides a sensible
 * implementation of equals(), returning true if equals() is true on each of the contained
 * objects.
 */
public class Pair<F, S> {
    public final @NonNull
    F first;
    public final @NonNull
    S second;

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public Pair(@NonNull F first, @NonNull S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     *
     * @param a the first object in the Pair
     * @param b the second object in the pair
     * @return a Pair that is templatized with the types of a and b
     */
    @NonNull
    public static <A, B> Pair<A, B> create(@NonNull A a, @NonNull B b) {
        return new Pair<A, B>(a, b);
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link Pair} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        return ObjectsCompat.equals(p.first, first) && ObjectsCompat.equals(p.second, second);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return "Pair{" + first + " " + second + "}";
    }
}
