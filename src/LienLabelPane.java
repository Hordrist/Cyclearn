import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

public class LienLabelPane extends JPanel implements Serializable {
    private LienLabel labelLien;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonValider;
    private JTextField champLien;
    private String texte;

    public LienLabelPane() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        asEditingLink();
    }

    public void asEditingLink() {
        asEditingLink("");
    }

    public void asEditingLink(String s){
        removeAll();
        updateUI();
        champLien = new JTextField();
        champLien.setText(s);
        champLien.setColumns(50);
        boutonValider = new JButton("Valider");
        boutonValider.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                texte = champLien.getText();
                asShowingLink();
            }
        });
        add(champLien);
        add(boutonValider);
    }

    public void asShowingLink(){
        removeAll();
        updateUI();

        boutonModifier = new JButton("Modifier");
        boutonModifier.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                asEditingLink(texte);
            }
        });
        add(boutonModifier);

        labelLien = new LienLabel(texte);
        add(labelLien);

        boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                Container parent = getParent();
                parent.remove(LienLabelPane.this);
                parent.revalidate();
                parent.repaint();
                removeAll();
                texte = null;
            }
        });
        add(boutonSupprimer);

    }

    public void removeAll(){
        super.removeAll();
        champLien = null;
        boutonValider = null;
        boutonModifier = null;
        boutonSupprimer = null;
        labelLien = null;
    }
}

class LienLabel extends JLabel implements Serializable{


    public LienLabel(String text) {
        super(text);
        init();
    }

    public void init(){
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setName("Lien");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(getText()));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}

