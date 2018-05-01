/**
 * Lootable object which doesn't fight.
 *
 * @author JF
 * @serial exclude
 */
public class Treasure extends Thing implements Lootable {
    // Worth of this treasure.
    private double value;

    /**
     * Make a treasure.
     * Note: Character replacement rules from Thing apply
     * @param shortDescription short name for this item. (This will also be
     *                         used as the long description)
     * @param value worth of this item
     */
    public Treasure(String shortDescription, double value) {
        super(shortDescription, shortDescription);
        this.value = value;
    }

    /**
     * Gets the worth of the item.
     * @return the worth of this item
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Can the looter loot me.
     * @return true if looter is an instance of Player; else false
     * @inheritDoc
     */
    @Override
    public boolean canLoot(Thing looter) {
        return looter instanceof Player;
    }

    /**
     * Gets the encoded representation.
     * @return an encoded string.
     */
    public String repr() {
        return "$;" + Double.toString(this.value) + ";" + this.getShort() ;
    }

    /**
     * Factory to create Treasure from a String
     * @param encoded repr() form of the object
     * @return decoded Object or null for failure
     */
    public static Treasure decode(String encoded) {
        if (encoded == null || !encoded.startsWith("$;"))
            return null;
        String[] args = encoded.split(";");
        if (args.length != 3)
            return null;
        try {
            return new Treasure(args[2], Double.parseDouble(args[1]));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
