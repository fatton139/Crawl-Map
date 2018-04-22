import java.util.Objects;

public class Pair {
    public int x;
    public int y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() == o.getClass()) {
            Pair pair = (Pair) o;
            return this.x == pair.x && this.y == pair.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
