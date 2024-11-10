package notification;

import java.awt.*;
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


    public void setNotificationActionListener(ActionListener actionListener){
        this.actionListener = actionListener;
        tray.getTrayIcons()[0].addActionListener(actionListener);
    }
}
