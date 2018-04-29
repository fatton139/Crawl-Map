/**
 * Player type which can dig new rooms and avoid fights.
 * Note: instances of this class are linked to a particular start room.
 *
 * @author JF
 * @serial exclude
 */
public class Builder extends Player {

    // Start room for this map
    private Room root;

    /**
     * Base constructor for Builder.
     *
     * @param shortDescription Short name for this builder
     * @param longDescription  Longer description for this builder
     * @param root      Start room for this map
     */
    public Builder(String shortDescription, String longDescription, Room root) {
        super(shortDescription, longDescription);
        this.root = root;
    }

    /**
    * @return 1000
    * @inheritDoc
    */
    @Override
    public int getDamage() {
        return 1000;
    }

    /**
     * Attempt to damage this Mob. Note: for this type, it will be ignored
     *
     * @param amount amount of damage 
     */
    @Override
    public void takeDamage(int amount) {
    }

    /**
     * Gets the encoded representation.
     * @return an encoded string.
     */
    public String repr() {
        return "B;" + this.getShort() + ";" + this.getLong();
    }

    /**
     * Factory to create Builder from a String
     * @param encoded repr() form of the object
     * @param root Start room for this map
     * @return decoded Object or null for failure
     */
    public static Builder decode(String encoded, Room root) {
        if (encoded == null || root == null || !encoded.startsWith("B;"))
            return null;
        String[] args = encoded.split(";");
        if (args.length != 3)
            return null;
        return new Builder(args[1], args[2], root);
    }
}
