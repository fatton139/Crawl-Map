import java.util.ArrayList;
import java.io.*;

public class MapIO {
    public static Room deserializeMap(String filename) {
        ObjectInputStream room_in = null;
        try {
            FileInputStream f_in = new FileInputStream(filename);
            room_in = new ObjectInputStream(f_in);
            return (Room) room_in.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } finally {
            try {
                room_in.close();
            } catch (IOException e) {
                return null;
            }
        }
    }
    public static boolean saveMap(Room root, String filename) {
        FileWriter file;
        BufferedWriter BW;
        BoundsMapper BM = new BoundsMapper(root);
        BM.walk();
        ArrayList<Room> rooms = new ArrayList<>(BM.coords.keySet());
        try {
            file = new FileWriter(filename);
            BW = new BufferedWriter(file);
            //1. The number of rooms in the file
            BW.write(Integer.toString(rooms.size()));
            BW.newLine();
            //2. Room descriptions
            for (Room room:rooms) {
                BW.write(room.getDescription());
                BW.newLine();
            }
            //3. Room exits
            for (Room room:rooms) {
                BW.write(Integer.toString(room.getExits().keySet().size()));
                BW.newLine();
                for (String exit: room.getExits().keySet()) {
                    BW.write(Integer.toString(rooms.indexOf(room.getExits().get
                            (exit))));
                    BW.write(" ");
                    BW.write(exit);
                    BW.newLine();
                }
            }
            //4. Room contents
            for (Room room:rooms) {
                BW.write(Integer.toString(room.getContents().size()));
                BW.newLine();
                for(Thing thing: room.getContents()) {
                    BW.write(thing.repr());
                    BW.newLine();
                }
            }
        } catch (IOException e) {
            return false;
        }

        try {
            BW.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static boolean serializeMap(Room root, String filename) {
        ObjectOutputStream room_out = null;
        try {
            FileOutputStream file_out = new FileOutputStream(filename);
            room_out = new ObjectOutputStream(file_out);
            room_out.writeObject(root);
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        try {
            room_out.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
