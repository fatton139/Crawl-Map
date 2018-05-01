import java.util.Objects;

/**
 * Helper class to store two ints
 */
public class Pair {
    public int x;
    public int y;

    /**
     * Constructor
     * @param x x value of pair
     * @param y u value of pair
     */
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Check if two pairs are equal
     * @param o The object to equate the pair to
     * @return true if the two pairs are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this.getClass() == o.getClass()) {
            Pair pair = (Pair) o;
            return this.x == pair.x && this.y == pair.y;
        }
        return false;
    }

    /**
     * Gets the hashcode of the object
     * @return the hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
