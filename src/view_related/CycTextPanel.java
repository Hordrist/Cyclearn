package view_related;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.Serializable;

public class CycTextPanel extends JPanel implements Serializable {
    private JTextComponent text_component;
    public CycTextPanel() {
        super();
    }

    public void setTextComponent(JTextComponent comp){
        text_component = comp;
    }

    public JTextComponent getTextComponent(){
        return text_component;
    }

    public static CycTextPanel createFormTextPane(String title, JPanel parent, String texte) {
        CycTextPanel grouping = new CycTextPanel();
        grouping.setLayout(new BoxLayout(grouping, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        grouping.add(label);

        JTextPane textPane = new JTextPane();
        textPane.setVisible(true);
        textPane.setEditable(true);
        textPane.setPreferredSize(new Dimension(parent.getWidth()/2,100));
        textPane.setName(title);
        if (!texte.isEmpty()) {
            textPane.setText(texte);
        }
        grouping.setTextComponent(textPane);

        JScrollPane scrollPane = new JScrollPane(textPane);
        grouping.add(scrollPane);

        grouping.setVisible(true);
        return grouping;
    }

    public static CycTextPanel createFormTextPane(String title, JPanel parent) {
        return createFormTextPane(title, parent, "");
    }

    public static CycTextPanel createFormTextField(String title, JPanel parent, String texte) {
        CycTextPanel grouping = new CycTextPanel();
        grouping.setLayout(new BoxLayout(grouping, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        grouping.add(label);


        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(parent.getWidth()/2,20));
        textField.setName(title);
        if(!texte.isEmpty()){
            textField.setText(texte);
        }
        grouping.add(textField);
        grouping.setTextComponent(textField);

        grouping.setVisible(true);
        return grouping;
    }
    public static CycTextPanel createFormTextField(String title, JPanel parent) {
        return createFormTextField(title, parent, "");
    }


}
