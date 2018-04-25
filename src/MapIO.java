import java.util.ArrayList;
import java.io.*;

public class MapIO {
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
