import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws AWTException, IOException {
        NotifSystem nt = new NotifSystem();
        nt.start();

        GUIWindow window = new GUIWindow();
        window.showWindow();


        /*CycPagePanel cpp = new CycPagePanel();
        CycPagePanel clonebcpp = cpp.copy();
        cpp.asListeCoursPanel();
        CycPagePanel clonemcpp = cpp.copy();
        cpp = new CycPagePanel();
        CycPagePanel cloneacpp = cpp.copy();

        System.out.println("before " + clonebcpp.content.getComponentCount());
        System.out.println("middle " + clonemcpp.content.getComponentCount());
        System.out.println("after " + cloneacpp.content.getComponentCount());
        */





        /*String listecours_string = FileHandling.readFile(FileHandling.DEFAULT_FILE_PATH);
        Gson gson = new Gson();
        Cours[] listecours = gson.fromJson(listecours_string, Cours[].class);
        Notification notification = new Notification("Demo", "Ouvrir le cours 0");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUIWindow window = new GUIWindow();
                window.showWindow();
                ((CycPanel)window.getContentPane()).asVisuPannel(listecours[0]);
            }
        };
        notification.setNotificationActionListener(actionListener);

        notification.show();*/
    }
}