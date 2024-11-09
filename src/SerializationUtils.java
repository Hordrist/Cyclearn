import java.io.*;

public class SerializationUtils {
    public static byte[] serialize(Object obj){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos  = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            return baos.toByteArray();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deserialize(byte[] ba){
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object copy(Object obj){
        return deserialize(serialize(obj));
    }
}
