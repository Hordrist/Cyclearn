import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;


public class GUIWindow extends JFrame {
    private CycPagePanel current_panel;

    //Todo : ButeArrays seem to be less expensive than java objects
    // => Idea is to serialize CycPagePanels (along with buttons actionListeners) and store this as ByteArrays
    private CycPagePanel[] history;

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

        current_panel = new CycPagePanel(this);
        current_panel.asMenuPanel();
        setContentPane(current_panel);

        history = new CycPagePanel[0];
    }

    public void showWindow(){
        setVisible(true);
    }

    public void hideWindow(){
        setVisible(false);
    }

    public CycPagePanel getPrecedentPanel(){
        return history[history.length-1];
    }

    public CycPagePanel returnToPrecedentPanel(){
        CycPagePanel precpanel = getPrecedentPanel();
        setContentPane(precpanel);
        revalidate();
        history = Arrays.copyOf(history, history.length-1);
        return precpanel;
    }

    public CycPagePanel addPanelToHistory(CycPagePanel panel){
        history = Arrays.copyOf(history, history.length+1);
        history[history.length-1] = panel;
        return panel;
    }
}