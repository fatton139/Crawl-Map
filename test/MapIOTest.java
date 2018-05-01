import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MapIOTest {

    /**
     * Tests the decoding of a Treasure.
     */
    @Test
    public void decodeTreasure() {
        Treasure treasure = new Treasure("box", 14.5);
        Thing t = MapIO.decodeThing(treasure.repr(), null);
        treasure = (Treasure) t;
        assertTrue(treasure.getShort().equals("box"));
        assertTrue(treasure.getValue() == 14.5);
    }

    /**
     * Tests the decoding of a Critter.
     */
    @Test
    public void decodeCritter() {
        Critter critter = new Critter("cat", "a cat", 14.5, 2);
        Thing c = MapIO.decodeThing(critter.repr(), null);
        critter = (Critter) c;
        assertTrue(critter.getShort().equals("cat"));
        assertTrue(critter.getLong().equals("a cat"));
        assertTrue(critter.getValue() == 0);
        assertEquals(critter.getHealth(), 2);
        critter.setAlive(false);
        assertTrue(critter.getValue() == 14.5);
    }

    /**
     * Test decoding of a Builder.
     */
    @Test
    public void decodeBuilder() {
        Room r = new Room("Test room");
        Builder builder = new Builder("robert", "There were \n\r chars but " +
                "they " + "were replaced", r);
        Thing b = MapIO.decodeThing(builder.repr(), r);
        builder = (Builder) b;
        assertTrue(builder.getShort().equals("robert"));
        assertTrue(builder.getLong().equals("There were ** chars but they " +
                "were replaced"));
    }

    /**
     * Tests decoding of a Explorer.
     */
    @Test
    public void decodeExplorer() {
        Explorer explorer = new Explorer("doris", "There were \n\r chars but " +
                "they were replaced", 2);
        Thing e = MapIO.decodeThing(explorer.repr(), null);
        explorer = (Explorer) e;
        assertTrue(explorer.getShort().equals("doris"));
        assertTrue(explorer.getLong().equals("There were ** chars but they " +
                "were replaced"));
        assertEquals(explorer.getHealth(), 2);
    }

    /**
     * Tests decoding on faulty encoded strings.
     */
    @Test
    public void decodeBadString() {
        Thing decoded = MapIO.decodeThing("M;bad string;2", null);
        assertEquals(decoded, null);
        Thing builder = MapIO.decodeThing("B;builder;builder", null);
        assertEquals(builder, null);
        Thing explorer = MapIO.decodeThing("E;explorer;explorer;2;" +
                "more string", null);
        assertEquals(explorer, null);
        Thing treasure = MapIO.decodeThing("$;two;box", null);
        assertEquals(treasure, null);
    }

    /**
     * Test serialization of a map containing 3 rooms.
     */
    @Test
    public void serializeMap() {
        Room room1 = new Room("Starting");
        Room room2 = new Room("Left of Starting");
        Room room3 = new Room("Right of Starting");
        room1.enter(new Critter("cat", "a cat", 14.5, 2));
        room2.enter(new Treasure("box", 14.5));
        room3.enter(new Explorer("bob", "a bob"));
        try {
            Room.makeExitPair(room1, room2, "West", "East");
            Room.makeExitPair(room1, room3, "East", "West");
        }
        catch(ExitExistsException e) {
            fail();
        }
        catch(NullRoomException e) {
            fail();
        }
        assertEquals(MapIO.serializeMap(room1, "testSerializeMap"), true);
    }

    /**
     * Test deserialization of a map containing 3 rooms.
     */
    @Test
    public void deserializeMap() {
        Room r = MapIO.deserializeMap("testSerializeMap");
        assertTrue(r.getDescription().equals("Starting"));
        assertTrue(r.getContents().get(0) instanceof Critter);
        assertTrue(r.getContents().get(0).getShort().equals("cat"));
        assertEquals(r.getExits().size(), 2);
        for (String exit:r.getExits().keySet()) {
            Room room = r.getExits().get(exit);
            if (exit.equals("West")) {
                assertTrue(room.getDescription().equals("Left of Starting"));
                assertTrue(room.getContents().get(0) instanceof Treasure);
                assertTrue(room.getContents().get(0).getShort().equals("box"));
            }
            if (exit.equals("East")) {
                assertTrue(room.getDescription().equals("Right of Starting"));
                assertTrue(room.getContents().get(0) instanceof Explorer);
                assertTrue(room.getContents().get(0).getShort().equals("bob"));
            }
        }
    }

    /**
     * Test saving of a map in string encoded form containing 3 rooms.
     */
    @Test
    public void saveMap() {
        Room room1 = new Room("Starting");
        Room room2 = new Room("Left of Starting");
        Room room3 = new Room("Right of Starting");
        room1.enter(new Critter("cat", "a cat", 14.5, 2));
        room2.enter(new Treasure("box", 14.5));
        room3.enter(new Explorer("bob", "a bob"));
        try {
            Room.makeExitPair(room1, room2, "West", "East");
            Room.makeExitPair(room1, room3, "East", "West");
        }
        catch(ExitExistsException e) {
            fail();
        }
        catch(NullRoomException e) {
            fail();
        }
        assertEquals(MapIO.saveMap(room1, "testSaveMap.txt"), true);
    }

    /**
     * Test loading of a string encoded map containing 3 rooms.
     */
    @Test
    public void loadMap() {
        Object o[] = MapIO.loadMap("testSaveMap.txt");
        Player p = (Player) o[0];
        assertTrue(p instanceof Explorer);
        Room r = (Room) o[1];
        assertTrue(r.getDescription().equals("Starting"));
        assertTrue(r.getContents().get(0).getShort().equals("cat"));
        assertEquals(r.getExits().size(), 2);
        for (String exit:r.getExits().keySet()) {
            Room room = r.getExits().get(exit);
            if (exit.equals("West")) {
                assertTrue(room.getDescription().equals("Left of Starting"));
                assertTrue(room.getContents().get(0) instanceof Treasure);
                assertTrue(room.getContents().get(0).getShort().equals("box"));
            }
            if (exit.equals("East")) {
                assertTrue(room.getDescription().equals("Right of Starting"));
            }
        }
    }

}