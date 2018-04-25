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
            System.out.println(e);
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                room_in.close();
            } catch (IOException e) {
                return null;
            }
        }
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
        } finally {
            try {
                room_out.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
