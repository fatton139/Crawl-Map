import java.util.ArrayList;

public class MapWalker {
    private ArrayList<Room> visitedRooms = new ArrayList<>();
    private ArrayList<Room> roomsToWalk = new ArrayList<>();
    private Room startRoom;
    public MapWalker(Room start) {
        this.startRoom = start;
    }

    protected void reset() {}

    public void walk() {
    }

    public boolean hasVisited(Room room) {
        return this.visitedRooms.contains(room);
    }

    protected void visit(Room room) {}
}
