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
}
