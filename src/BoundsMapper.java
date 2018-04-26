import java.util.LinkedHashMap;
import java.util.Map;

public class BoundsMapper extends MapWalker {
    public Map<Room, Pair> coords = new LinkedHashMap<>();
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
            super.visit(room);
            Map<String, Room> exits = room.getExits();
            for (String exitName:exits.keySet()) {
                if (coords.containsKey(exits.get(exitName))) {
                    Pair position = coords.get(exits.get(exitName));
                    switch (exitName) {
                        case "North":
                            coords.put(room, new Pair(position.x,
                                    position.y - 1));
                            if (position.y - 1 < this.yMin) {
                                this.yMin = position.y - 1;
                            }
                            break;
                        case "South":
                            coords.put(room, new Pair(position.x,
                                    position.y + 1));
                            if (position.y + 1 > this.yMax) {
                                this.yMax = position.y + 1;
                            }
                            break;
                        case "East":
                            coords.put(room, new Pair(position.x - 1,
                                    position.y));
                            if (position.x - 1 < this.xMin) {
                                this.xMin = position.x - 1;
                            }
                            break;
                        case "West":
                            coords.put(room, new Pair(position.x + 1,
                                    position.y));
                            if (position.x + 1 > this.xMax) {
                                this.xMax = position.x + 1;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.coords = new LinkedHashMap<>();
        this.xMax = 0;
        this.yMax = 0;
        this.xMin = 0;
        this.yMin = 0;
    }
}
