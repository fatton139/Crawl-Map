import java.util.ArrayList;
import java.io.*;
import java.util.LinkedHashMap;

public class MapIO {
    public static Thing decodeThing(String encoded, Room root) {
        if (encoded == null || encoded.length() < 2)
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
            ArrayList<Room> roomIndex = new ArrayList<>();
            String line;
            int roomIndexCursor = 0;
            int numRooms = 0;
            int counter = 0;
            while ((line = BR.readLine()) != null) {
                if (counter == 0)
                    numRooms = Integer.parseInt(line);
                else if (counter < numRooms + 1 && counter >= 1) {
                    roomIndex.add(new Room(line));
                } else {
                    if (roomIndexCursor < numRooms) {
                        int i = 0;
                        while (i < Integer.parseInt(line)) {
                            String encoded = BR.readLine();
                            int roomIndexPointer = Integer.parseInt(encoded
                                    .substring(0, encoded.indexOf(" ")));
                            String exitName = encoded.substring(encoded
                                    .indexOf(" ") + 1, encoded.length());
                            try {
                                roomIndex.get(roomIndexCursor).addExit(exitName,
                                        roomIndex.get(roomIndexPointer));
                            } catch(ExitExistsException e) {
                                return null;
                            } catch(NullRoomException e) {
                                return null;
                            }
                            i++;
                        }
                    } else {
                        int k = 0;
                        while (k < Integer.parseInt(line)) {
                            String encoded = BR.readLine();
                            if (encoded.startsWith("E;") ||
                                    encoded.startsWith("B;")) {
                                if (returns[0] == null) {
                                    returns[0] = encoded.startsWith("E;") ?
                                            Explorer.decode(encoded) :
                                            Builder.decode(encoded,
                                                    roomIndex.get(0));
                                } else
                                    return null;

                            } else {
                                roomIndex.get(roomIndexCursor - numRooms)
                                        .enter(MapIO.decodeThing(encoded,
                                                roomIndex.get(0)));
                            }
                            k++;
                        }
                    }
                    roomIndexCursor++;
                }
                counter++;
            }
            returns[1] = roomIndex.get(0);
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
                for (String exit:room.getExits().keySet()) {
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
        ObjectOutputStream room_out;
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
