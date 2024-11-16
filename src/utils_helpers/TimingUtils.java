package utils_helpers;

import business_objects.Cours;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimingUtils {
    private static int[] leitnerInvervals = new int[]{1,3,7,30,6*30,12*30,3*12*30};//Les jours d'intervale
    public static Cours[] getCoursForTheDay(){
        try {
            Cours[] liste_all_cours = FileHandling.getListeCoursFromJsonFile();
            ArrayList<Cours> today_cours = new ArrayList<Cours>();
            for(Cours cours : liste_all_cours){
                if(cours.getDate() != null && leintnerIntervalHasPassed(cours.getDate())){
                    today_cours.add(cours);
                }
            }
            return today_cours.toArray(new Cours[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean leintnerIntervalHasPassed(LocalDate start_date){
        LocalDate today_date = LocalDate.now();
        for(int interval : leitnerInvervals){
            if(start_date.plusDays(interval).equals(today_date)){
                return true;
            }
        }
        return false;
    }
}
