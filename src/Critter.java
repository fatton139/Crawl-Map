/**
 * A non-player Lootable Mob.
 *
 * @author JF
 * @serial exclude
 */
public class Critter extends Thing implements Lootable, Mob {
    // What is the value of this Mob once looted?
    private double value;
    // Current health of this Mob.
    private int health;
    // Default health (used if alive is set back to true).
    private static final int MAX_HEALTH = 10;

    /**
     * Critter - A non-player Lootable Mob.
     * @param shortDescription Name or short description for this Mob
     * @param longDescription  Longer description for this Mob
     * @param value     Worth of Mob when looted
     * @param health    Starting health of Mob
     */
    public Critter(String shortDescription, String longDescription,
                   double value, int health) {
        super(shortDescription, longDescription);
        this.value = value;
        this.health = health;
    }

    /**
     * Long desctription of Mob.
     *
     * @return Long description, immediately followed by "(fainted)" iff the
     *         critter is not alive.
     */
    @Override
    public String getDescription() {
        return isAlive() ? getLong() : getLong() + "(fainted)";
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
    }

    /**
     * @return 2
     * @inheritDoc
     */
    @Override
    public int getDamage() {
        return 2;
    }

    @Override
    public double getValue() {
        return (health > 0) ? 0 : value;
    }

    /**
     * @return true iff looter is a Player and your health is zero
     * @inheritDoc
     */
    @Override
    public boolean canLoot(Thing looter) {
        return (looter instanceof Player) && (health == 0);
    }

    /**
     * @inheritDoc
     * @see Mob#fight(Mob)
     */
    @Override
    public void fight(Mob mob) {
        while (this.isAlive() && mob.isAlive()) {
            int damage = getDamage();
            mob.takeDamage(damage);
            if (mob.isAlive()) {
                damage = mob.getDamage();
                this.takeDamage(damage);
            }
        }
    }

    /**
     * Returns whether the mob is an Explorer.
     * @return true if mob is an Explorer
     */
    @Override
    public boolean wantsToFight(Mob mob) {
        return mob instanceof Explorer;
    }

    /**
     * Is the current Mob health above zero?
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * If true, set health to MAX_HEALTH.
     * @see Critter#Critter(String, String, double, int)
     */
    @Override
    public void setAlive(boolean alive) {
        health = (alive ? MAX_HEALTH : 0);
    }

    /**
     * Returns the current health.
     * @return current health
     */
    public int getHealth() {
        return health;
    }

    public String repr() {
        return "C;" + Double.toString(this.value) + ";" +
                Integer.toString(this.getHealth()) + ";" +
                this.getShort() + ";" + this.getLong();
    }

    public static Critter decode(String encoded) {
        if (encoded == null || !encoded.startsWith("C;"))
            return null;
        String[] args = encoded.split(";");
        if (args.length != 5)
            return null;
        return new Critter(args[3], args[4], Double.parseDouble("14.5"),
                Integer.parseInt(args[2]));
    }
}
