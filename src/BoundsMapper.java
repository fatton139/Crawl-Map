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
    public void reset() {}
}
