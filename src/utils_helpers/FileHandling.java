package utils_helpers;

import business_objects.Cours;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileHandling {

    public static String DEFAULT_FILE_PATH = "C:\\Users\\matou\\Desktop\\cours.json";

    public static File createFile(String filepath) throws IOException {
        File new_file = new File(filepath);
        if (new_file.createNewFile()) {
            System.out.println("File created : " + new_file.getAbsolutePath());
        }
        else{
            System.out.println("File already existed");
        }

        return new_file;
    }

    public static boolean checkFileExists(String filepath) {
        File file = new File(filepath);
        return file.exists();
    }

    public static void writeFile(String filepath, String content){
        try {
            if(!checkFileExists(filepath)){
                createFile(filepath);
            }
            FileWriter fileWriter = new FileWriter(filepath);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println("File written successfully");
        }
        catch(IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    public static String readFile(String filepath){
        String content = "";
        try{
            File file = new File(filepath);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                content += line;
            }
            reader.close();
        }
        catch(IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
            return null;
        }
        return content;
    }

    public static void deleteFile(String filepath){
        File file = new File(filepath);
        if(file.delete()){
            System.out.println("File deleted successfully");
        }
        else{
            System.out.println("Failed to delete file");
        }
    }

    public static Cours[] getListeCoursFromJsonFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        String json = "";
        if(checkFileExists(DEFAULT_FILE_PATH)){
            json = readFile(DEFAULT_FILE_PATH);
        }
        else{
            createFile(DEFAULT_FILE_PATH);
        }

        List<Cours> saved_cours = gson.fromJson(json,
                new TypeToken<List<Cours>>(){}.getType());
        return saved_cours.toArray(new Cours[saved_cours.size()]);
    }

    public static void writeCoursToJsonFile(Cours cours) throws IOException {
        String stringed_cours = cours.toString();
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        Cours[] saved_cours = getListeCoursFromJsonFile();
        saved_cours = Arrays.copyOf(saved_cours, saved_cours.length + 1);
        saved_cours[saved_cours.length - 1] = cours;
        writeFile(DEFAULT_FILE_PATH, gson.toJson(saved_cours));
    }

    public static void writeAllCoursToJsonFile(Cours[] cours) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        String stringed_courses = gson.toJson(cours);
        writeFile(DEFAULT_FILE_PATH, stringed_courses);
    }
    public static void deleteCoursFromJsonFile(Cours cours) throws IOException {
        List<Cours> liste_cours = new ArrayList<>(List.of(getListeCoursFromJsonFile()));
        liste_cours.removeIf(c->c.id.equals(cours.id));
        writeAllCoursToJsonFile(liste_cours.toArray(new Cours[liste_cours.size()]));
    }
}