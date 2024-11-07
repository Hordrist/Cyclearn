import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notification extends TrayIcon{

    private SystemTray tray;
    private String nom;
    private String message;
    private ActionListener actionListener;
    public Notification(String nom, String message) throws AWTException {
        super(Toolkit.getDefaultToolkit().createImage("icon.png"), nom);
        if(SystemTray.isSupported()){
            tray = SystemTray.getSystemTray();
            this.nom = nom;
            this.message = message;
            setImageAutoSize(true);
            tray.add(this);
        }
        else{

            System.err.println("SystemTray not supported");
        }
    }
    public void show(){
        displayMessage(nom, message, MessageType.INFO);
    }

    /*public void showNotification(String name, String text) throws AWTException {
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, name);
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);
        trayIcon.displayMessage(name, text, MessageType.INFO);
    }*/

    public void setNotificationActionListener(ActionListener actionListener){
        this.actionListener = actionListener;
        tray.getTrayIcons()[0].addActionListener(actionListener);
    }




    /*public void showPopupMenu(String name, String text) throws AWTException {
        PopupMenu popup = new PopupMenu();
        MenuItem item = new MenuItem(name);
        ActionListener listener = new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 System.out.println(text);
             }
        };
        item.addActionListener(listener);
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("icon.png"), "Popup", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(listener);
        tray.add(trayIcon);
        trayIcon.displayMessage(name, text, MessageType.INFO);
        GUIWindow window = new GUIWindow();
        popup.show(window, 0, 0);
    }*/
}
