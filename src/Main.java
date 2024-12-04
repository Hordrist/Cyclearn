import notification.NotifSystem;
import view_related.GUIWindow;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws AWTException, IOException {
        NotifSystem nt = new NotifSystem();
        nt.start();
        System.out.println("Cyc'learn launched");

        if(!Arrays.stream(args).anyMatch(a->a.equals("background") || a.equals("b"))) {
            GUIWindow window = new GUIWindow();
            window.initContent();
            window.showWindow();
        }
        else{
            nt.getNotifForTheDay();
        }

    }
}