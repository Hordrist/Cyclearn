package view_related;

import utils_helpers.Utils;
import utils_helpers.serialization.SerializationUtils;

import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Arrays;


public class GUIWindow extends JFrame implements Serializable {
    private CycPagePanel current_panel;

    private byte[][] history;

    public GUIWindow() {
        super("Cyc'Learn");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int decision = JOptionPane.showConfirmDialog(getContentPane(),
                        "Cliquez sur \"Yes\" pour quitter l'application, \"No\" pour qu'elle continue à " +
                                "tourner en arrière plan.");
                if (decision == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                if(decision == JOptionPane.NO_OPTION) {
                    e.getWindow().dispose();
                }
            }
        });

        setSize(Utils.WIDTH, Utils.HEIGHT);

        history = new byte[0][];
    }

    public void initContent(){
        current_panel = new CycPagePanel(this);
        setContentPane(current_panel);
        current_panel.asMenuPanel();
    }

    public CycPagePanel getPrecedentPanel(CycPagePanel current_panel){
        return (CycPagePanel) SerializationUtils.deserialize(history[history.length-1]);
    }

    public void returnToPrecedentPanel(CycPagePanel currentPanel){
        CycPagePanel precpanel = getPrecedentPanel(currentPanel);
        setContentPane(precpanel);

        history = Arrays.copyOf(history, history.length - 1);

        // If history.length=0 => we can't return => we are on a menu panel (if this were coded correctly)
        boolean precpanel_is_menu = history.length==0;
        if(precpanel_is_menu){
            addPanelToHistory(precpanel);
        }
    }

    public void addPanelToHistory(CycPagePanel panel){
        history = Arrays.copyOf(history, history.length+1);
        history[history.length-1] = SerializationUtils.serialize(panel);
    }

    public void showWindow(){
        setVisible(true);
    }

    public void hideWindow(){
        setVisible(false);
    }
}
