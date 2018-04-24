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

}
