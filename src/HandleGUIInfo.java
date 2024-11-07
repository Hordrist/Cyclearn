import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class HandleGUIInfo {
    public static String getValueFromInput(String fieldName,
                                           JPanel frame,
                                           boolean isInGroupingPannel)
    {
        Optional<Component> stream_res = Arrays.stream(frame.getComponents())
                .filter(s->s.getName().equals(fieldName)).findFirst();
        Component component = stream_res.get();
        JComponent jcomp = (JComponent) component;
        if(!isInGroupingPannel){
            return ((JTextComponent)jcomp).getText();
        }
        else {
            try {
                return ((JTextComponent) (jcomp.getComponent(1))).getText();
            }
            catch (ClassCastException e) {
                return ((JTextComponent) (((JScrollPane)jcomp.getComponent(1)).getViewport().getComponent(0))).getText();
            }
        }
    }



    public static void setTextToInput(String text,
                                            String fieldName,
                                            JFrame frame,
                                           boolean isInGroupingPannel)
    {
        Optional<Component> stream_res = Arrays.stream(frame.getContentPane().getComponents())
                .filter(s->s.getName().equals(fieldName)).findFirst();
        Component component = stream_res.get();
        JComponent jcomp = (JComponent) component;
        if(!isInGroupingPannel){
            ((JTextComponent)jcomp).setText(text);
        }
        else {
            ((JTextComponent)(jcomp.getComponent(1))).setText(text);
        }
    }



}
