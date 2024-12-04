package notification;

import utils_helpers.Utils;

import java.awt.*;
import java.awt.event.ActionListener;

public class Notification extends TrayIcon{

    private String nom;
    private String message;
    private ActionListener actionListener;
    public Notification(String nom, String message) throws AWTException {
        super(Toolkit.getDefaultToolkit().createImage("icon.png"), nom);
        if(SystemTray.isSupported()){
            this.nom = nom;
            this.message = message;
            setImageAutoSize(true);
            Utils.getSystemTray().add(this);
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
        Utils.getSystemTray().getTrayIcons()[0].addActionListener(actionListener);
    }
}
