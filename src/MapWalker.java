import java.util.ArrayList;

/**
 * Iterator over all reachable rooms.
 */
public class MapWalker {
    private ArrayList<Room> visitedRooms = new ArrayList<>();
    private ArrayList<Room> roomsToWalk = new ArrayList<>();
    private Room startRoom;

    /**
     * Constructor
     * @param start Room to begin exploring from
     */
    public MapWalker(Room start) {
        this.startRoom = start;
    }

    /**
     * Clears any state from previous walks
     */
    protected void reset() {
        this.visitedRooms = new ArrayList<>();
        this.roomsToWalk = new ArrayList<>();
    }

    /**
     * Visits all reachable rooms and calls visit()
     */
    public void walk() {
        this.reset();
        this.roomsToWalk.add(this.startRoom);
        while (!roomsToWalk.isEmpty()) {
            Room cursor = this.roomsToWalk.get(0);
            this.roomsToWalk.remove(0);
            if (!hasVisited(cursor)) {
                for (String exitName: cursor.getExits().keySet()) {
                    if (!this.hasVisited(cursor.getExits().get(exitName)))
                        this.roomsToWalk.add(cursor.getExits().get(exitName));
                }
                this.visit(cursor);
            }
        }
    }

    /**
     * Check if a room has been visited already
     * @param room The room to check
     * @return true if room has been processed
     */
    public boolean hasVisited(Room room) {
        return this.visitedRooms.contains(room);
    }

    /**
     * Visits/Processes a room
     * @param room
     */
    protected void visit(Room room) {
        this.visitedRooms.add(room);
    }
}
