import org.junit.Test;

import static org.junit.Assert.*;

public class BoundsMapperTest {

    /**
     * Tests rooms which are not connected.
     */
    @Test
    public void testUnlinkedRooms() {
        Room room1 = new Room("1");
        Room room2 = new Room("2");
        BoundsMapper bm = new BoundsMapper(room1);
        bm.walk();
        assertEquals(bm.coords.size(), 1);
        assertEquals(bm.xMax, 0);
        assertEquals(bm.yMax, 0);
        assertEquals(bm.xMin, 0);
        assertEquals(bm.yMin, 0);
    }

    /**
     * Test a 3 by 3 grid of rooms connected in a spiral.
     */
    @Test
    public void testBounds3() {
        Room room1 = new Room("1");
        Room room2 = new Room("2");
        Room room3 = new Room("3");
        Room room4 = new Room("4");
        Room room5 = new Room("5");
        Room room6 = new Room("6");
        Room room7 = new Room("7");
        Room room8 = new Room("8");
        Room room9 = new Room("9");
        try {
            Room.makeExitPair(room1, room2, "East", "West");
            Room.makeExitPair(room2, room3, "East", "West");
            Room.makeExitPair(room3, room4, "South", "North");
            Room.makeExitPair(room4, room5, "South", "North");
            Room.makeExitPair(room5, room6, "West", "East");
            Room.makeExitPair(room6, room7, "West", "East");
            Room.makeExitPair(room7, room8, "North", "South");
            Room.makeExitPair(room8, room9, "East", "West");
        }
        catch(ExitExistsException e) {
            fail();
        }
        catch(NullRoomException e) {
            fail();
        }
        // Starting room at upper left corner
        BoundsMapper bm1 = new BoundsMapper(room1);
        bm1.walk();
        assertEquals(bm1.coords.size(), 9);
        assertEquals(bm1.xMax, 2);
        assertEquals(bm1.yMax, 0);
        assertEquals(bm1.xMin, 0);
        assertEquals(bm1.yMin, -2);
        // Starting room at center
        BoundsMapper bm2 = new BoundsMapper(room9);
        bm2.walk();
        assertEquals(bm2.coords.size(), 9);
        assertEquals(bm2.xMax, 1);
        assertEquals(bm2.yMax, 1);
        assertEquals(bm2.xMin, -1);
        assertEquals(bm2.yMin, -1);
    }

    /**
     * Tests one way exits and unreachable rooms.
     */
    @Test
    public void testOneWayExits() {
        Room room1 = new Room("1");
        Room room2 = new Room("2");
        Room room3 = new Room("One way");
        Room room4 = new Room("Unconnected room");
        try {
            Room.makeExitPair(room1, room2, "East", "West");
            // One way exit to room1.
            room3.addExit("North", room1);
        }
        catch(ExitExistsException e) {
            fail();
        }
        catch(NullRoomException e) {
            fail();
        }
        BoundsMapper bm = new BoundsMapper(room1);
        bm.walk();
        assertEquals(bm.coords.size(), 2);
        assertEquals(bm.xMax, 1);
        assertEquals(bm.yMax, 0);
        assertEquals(bm.xMin, 0);
        assertEquals(bm.yMin, 0);
    }

    /**
     * Tests the reset functionality.
     */
    @Test
    public void testReset() {
        Room room1 = new Room("1");
        Room room2 = new Room("2");
        Room room3 = new Room("3");
        Room room4 = new Room("4");
        Room room5 = new Room("5");
        Room room6 = new Room("6");
        try {
            Room.makeExitPair(room1, room2, "North", "South");
            Room.makeExitPair(room1, room3, "South", "North");
            Room.makeExitPair(room1, room4, "East", "West");
            Room.makeExitPair(room1, room5, "West", "East");
            Room.makeExitPair(room2, room6, "West", "East");
        }
        catch(ExitExistsException e) {
            fail();
        }
        catch(NullRoomException e) {
            fail();
        }
        BoundsMapper bm = new BoundsMapper(room1);
        bm.walk();
        bm.reset();
        assertEquals(bm.coords.size(), 0);
        assertEquals(bm.xMax, 0);
        assertEquals(bm.yMax, 0);
        assertEquals(bm.xMin, 0);
        assertEquals(bm.yMin, 0);
    }
}