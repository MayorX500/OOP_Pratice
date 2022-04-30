package Auxiliar;

import MVC_House_Sync.Model;

import java.io.*;

public class FileHandler {
    public static void exportModelToFile(Model model, String filename) throws IOException {
        File obj = new File(filename);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(obj));
        oos.writeObject(model);
        oos.close();
    }

    public static Model importModelFromFile(String filename) throws IOException, ClassNotFoundException {
        File obj = new File(filename);
        ObjectInputStream oos = new ObjectInputStream(new FileInputStream(obj));
        Model m = new Model((Model)oos.readObject());
        oos.close();
        return m;
    }
}
