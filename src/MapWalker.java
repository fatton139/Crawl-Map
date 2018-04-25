import java.util.ArrayList;

public class MapWalker {
    private ArrayList<Room> visitedRooms = new ArrayList<>();
    private ArrayList<Room> roomsToWalk = new ArrayList<>();
    private Room startRoom;
    public MapWalker(Room start) {
        this.startRoom = start;
    }

    protected void reset() {
        this.visitedRooms = new ArrayList<>();
        this.roomsToWalk = new ArrayList<>();
    }

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

    public boolean hasVisited(Room room) {
        return this.visitedRooms.contains(room);
    }

    protected void visit(Room room) {
        this.visitedRooms.add(room);
    }
}
