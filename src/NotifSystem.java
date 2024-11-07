import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NotifSystem extends Thread {
    private boolean runned = false;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    private LocalTime[] notifTimes = new LocalTime [3];
    private int[] leitnerInvervals = new int[]{1,3,7,30,6*30,12*30,3*12*30};//Les jours d'intervale
    private List<Notification> dayNotifications = new ArrayList<Notification>();
    private Cours[] liste_cours;
    public NotifSystem() throws IOException {
        super();
        liste_cours = FileHandling.getListeCoursFromJsonFile();
        notifTimes[0] = LocalTime.of(9,0,0);
        notifTimes[1] = LocalTime.of(12,45,0);
        notifTimes[2] = LocalTime.of(19,30,0);
    }

    public void setListeCours(Cours[] liste_cours) {
        this.liste_cours = liste_cours;
    }

    public void getNotifForTheDay() throws ParseException, AWTException {
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //LocalDate today_date = LocalDate.parse(LocalDate.now().format(dtf));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today_date = sdf.parse(sdf.format(new Date()));
        ArrayList<Cours> today_cours = new ArrayList<Cours>();
        for(Cours cours : liste_cours){
            if(cours.getDate() != null && cours.getDate().equals(today_date)){
                today_cours.add(cours);
            }
        }
        Notification notif = new Notification("Cyclearn", "Cours du jour");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUIWindow window = new GUIWindow();
                window.showWindow();
                Cours[] atoday_cours = today_cours.toArray(new Cours[today_cours.size()]);
                ((CycPagePanel)window.getContentPane()).asListeCoursPanel(atoday_cours);
            }
        };
        notif.setNotificationActionListener(actionListener);
        notif.show();

    }

    public void run() {
        while (true) {
            LocalTime now = LocalTime.parse(LocalTime.now().format(dtf));
            if(Arrays.stream(notifTimes).anyMatch(t->t.equals(now))) {
                if(!runned){
                    if(now.equals(notifTimes[0])){
                        try {
                            setListeCours(FileHandling.getListeCoursFromJsonFile());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        getNotifForTheDay();
                        runned = true;
                    } catch (ParseException | AWTException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else if(Arrays.stream(notifTimes).anyMatch(t->t.plusSeconds(1).equals(now))) {
                runned = false;
            }
        }
    }

}
