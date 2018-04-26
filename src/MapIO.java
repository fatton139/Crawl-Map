import java.util.ArrayList;
import java.io.*;

public class MapIO {
    public static Thing decodeThing(String encoded, Room root) {
        if (encoded == null || root == null)
            return null;
        switch(encoded.substring(0, 2)) {
            case("$;"):
                return Treasure.decode(encoded);
            case("C;"):
                return Critter.decode(encoded);
            case("B;"):
                return Builder.decode(encoded, root);
            case("E;"):
                return Explorer.decode(encoded);
            default:
                return null;
        }
    }

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

    public static Object[] loadMap(String filename) {
        FileReader file;
        BufferedReader BR;
        Object[] returns = new Object[2];
        try {
            file = new FileReader(filename);
            BR = new BufferedReader(file);
            String line;
            int counter = 0;
            while ((line = BR.readLine()) != null) {
                if (counter == 2)
                    returns[0] = new Room(line);
                if (line.startsWith("E;") || line.startsWith("B;")) {
                    String[] args = line.split(";");
                    if ((line.startsWith("E;") && args.length != 4) ||
                            (line.startsWith("B;") && args.length != 3))
                        return null;
                    if (returns[1] == null) {
                        returns[1] = line.startsWith("E;") ?
                                new Explorer(args[2], args[3],
                                        Integer.parseInt(args[1])) :
                                new Builder(args[1], args[2],
                                        (Room) returns[0]);
                    }
                    else
                        return null;
                }
                counter++;
            }
        } catch (IOException e) {
            return null;
        }

        try {
            BR.close();
        } catch (IOException e) {
            return null;
        }

        return returns;
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
