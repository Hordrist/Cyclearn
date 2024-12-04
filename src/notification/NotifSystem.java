package notification;

import business_objects.Cours;
import utils_helpers.FileHandling;
import utils_helpers.TimingUtils;
import utils_helpers.Utils;
import view_related.CycPagePanel;
import view_related.GUIWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class NotifSystem extends Thread {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static LocalTime[] notifTimes = new LocalTime[] {
            LocalTime.of(7,45,0),
            LocalTime.of(9,50,0),
            LocalTime.of(12,45,0),
            LocalTime.of(13,30,0),
            LocalTime.of(15,50,0),
            LocalTime.of(19,0,0),
            LocalTime.of(19,30,0),
            LocalTime.of(20,30,0),
            LocalTime.of(21,30,0),
    };
    private boolean runned = false;
    private Cours[] liste_cours;
    public NotifSystem() throws IOException{
        super();
        liste_cours = FileHandling.getListeCoursFromJsonFile();
    }

    public void setListeCours(Cours[] liste_cours) {
        this.liste_cours = liste_cours;
    }



    public void getNotifForTheDay() throws AWTException {
        removeFormerNotifs();
        Cours[] today_cours = TimingUtils.getCoursForTheDay();
        Notification notif = new Notification("Cyc'learn", "Cours du jour : " + today_cours.length);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUIWindow window = new GUIWindow();
                window.initContent();
                window.showWindow();
                ((CycPagePanel)window.getContentPane()).asListeCoursPanel(today_cours);

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
                    } catch (AWTException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else if(Arrays.stream(notifTimes).anyMatch(t->t.plusSeconds(1).equals(now))) {
                runned = false;
            }
        }
    }

    public void removeFormerNotifs(){
        TrayIcon[] former_notifs_array = Utils.getSystemTray().getTrayIcons();
        Arrays.stream(former_notifs_array).forEach(n->Utils.getSystemTray().remove(n));
    }

}
