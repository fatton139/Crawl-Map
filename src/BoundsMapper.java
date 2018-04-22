import java.util.HashMap;
import java.util.Map;

public class BoundsMapper extends MapWalker {
    public Map<Room, Pair> coords = new HashMap<>();
    public int xMin;
    public int xMax;
    public int yMin;
    public int yMax;
    public BoundsMapper(Room root) {
        super(root);
    }

    private boolean hasVisitedNeighbours(Room room) {
        for (String key:room.getExits().keySet()) {
            if (coords.containsKey(room.getExits().get(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void visit(Room room) {
        if (!hasVisitedNeighbours(room))
            coords.put(room, new Pair(0, 0));
        else {
            Map<String, Room> exits = room.getExits();
            for (String exitName:exits.keySet()) {
                if (coords.containsKey(exits.get(exitName))) {
                    Pair position = coords.get(exits.get(exitName));
                    switch (exitName) {
                        case "North":
                            coords.put(room, new Pair(position.x, position.y - 1));
                            break;
                        case "South":
                            coords.put(room, new Pair(position.x, position.y + 1));
                            break;
                        case "East":
                            coords.put(room, new Pair(position.x - 1, position.y));
                            break;
                        case "West":
                            System.out.println(position.y);
                            coords.put(room, new Pair(position.x + 1, position.y));
                            break;
                        default:
                            break;
                    }
                }

            }
        }
    }

    @Override
    public void reset() {}
}
