package view_related;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public class CenteredBoxLayout extends BoxLayout {
    public CenteredBoxLayout(Container target, int axis) {
        super(target, axis);
        target.addContainerListener(new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
                try{
                    JComponent child = (JComponent) e.getChild();
                    child.setAlignmentX(Component.CENTER_ALIGNMENT);
                }catch (ClassCastException _){

                }

            }

            @Override
            public void componentRemoved(ContainerEvent e) {

            }
        });
    }

}
