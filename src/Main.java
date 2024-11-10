import notification.NotifSystem;
import view_related.GUIWindow;

import java.awt.*;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws AWTException, IOException {
        NotifSystem nt = new NotifSystem();
        nt.start();

        GUIWindow window = new GUIWindow();
        window.initContent();
        window.showWindow();
    }
}