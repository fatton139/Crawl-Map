/**
 * A Player who doesn't modify the map.
 *
 * @author JF
 * @serial exclude
 */
public class Explorer extends Player {
    /**
     * Copy details (but not inventory) from another Player.
     *
     * @param player Player to copy from
     */
    public Explorer(Player player) {
        super(player.getShort(), player.getLong(), player.getHealth());
    }

    /**
     * Create an Explorer with default health.
     * @param shortDescription A short name or description for the Explorer
     * @param longDescription A more detailed description for the Explorer
     */
    public Explorer(String shortDescription, String longDescription) {
        super(shortDescription, longDescription);
    }

    /**
     * Create an Explorer with a set health.
     * @param shortDescription A short name or description for the Explorer
     * @param longDescription A more detailed description for the Explorer
     * @param health Starting health for the Explorer.
     */
    public Explorer(String shortDescription, String longDescription, 
            int health) {
        super(shortDescription, longDescription, health);
    }

    @Override
    public String getDescription() {
        return getLong() + (isAlive()
                ? " with " + getHealth() + " health" : "(fainted)");
    }

    /**
    * @return 1
    * @inheritDoc
    */
    @Override
    public int getDamage() {
        return 1;
    }

    /**
     * Gets the encoded representation.
     * @return an encoded string.
     */
    public String repr() {
        return "E;" + Integer.toString(this.getHealth()) + ";" + this
                .getShort() + ";" + this.getLong();
    }

    /**
     * Factory to create Explorer from a String
     * @param encoded repr() form of the object
     * @return decoded Object or null for failure
     */
    public static Explorer decode(String encoded) {
        if (encoded == null || !encoded.startsWith("E;"))
            return null;
        String[] args = encoded.split(";");
        if (args.length != 4)
            return null;
        return new Explorer(args[2], args[3], Integer.parseInt(args[1]));
    }
}
