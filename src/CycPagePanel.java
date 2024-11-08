import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CycPagePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    private JPanel retour;
    private JPanel content;
    private GUIWindow parentFrame;

    public CycPagePanel(GUIWindow window) {
        super();
        setSize(Utils.WIDTH, Utils.HEIGHT);
        setLayout(new BorderLayout(0,20));


        retour = createRetourButton();
        add(retour, BorderLayout.NORTH);

        content = new JPanel();
        add(content, BorderLayout.CENTER);

        parentFrame = window;


    }


    //region Object methods
    public JPanel createRetourButton() {
        JPanel grouping = new JPanel();
        grouping.setLayout(new BoxLayout(grouping, BoxLayout.X_AXIS));
        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.returnToPrecedentPanel();

            }
        });
        grouping.add(retour);
        return grouping;
    }

    public CycPagePanel asMenuPanel(){
        retour.setVisible(false);

        content.removeAll();
        content.setLayout(new FlowLayout(FlowLayout.CENTER, Utils.WIDTH, 10));

        JButton btn_ajout_cours = new JButton("Ajouter un cours");
        btn_ajout_cours.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.addPanelToHistory(new CycPagePanel(parentFrame).asMenuPanel());
                CycPagePanel.this.asFormPannel();
            }
        });
        content.add(btn_ajout_cours);

        JButton btn_liste_cours = new JButton("Visualiser et modifier les cours");
        btn_liste_cours.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.addPanelToHistory(new CycPagePanel(parentFrame).asMenuPanel());
                CycPagePanel.this.asListeCoursPanel();
            }
        });
        content.add(btn_liste_cours);
        return this;
    }

    public CycPagePanel asFormPannel(){
        retour.setVisible(true);

        content.removeAll();
        content.setLayout(new FlowLayout(FlowLayout.CENTER, Utils.WIDTH, 10));

        JPanel NomTextField = CycTextPanel.createFormTextField("Nom", content);
        content.add(NomTextField);

        JPanel TexteTextArea = CycTextPanel.createFormTextPane("Texte", content);
        content.add(TexteTextArea);

        JButton boutonAjoutLien = new JButton("Ajouter un lien");
        boutonAjoutLien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indexBoutonAjout = Utils.getComponentIndex(boutonAjoutLien);
                content.add(new LienLabelPane(), indexBoutonAjout);
                updateUI();
            }
        });
        content.add(boutonAjoutLien);

        JButton boutonEnvoi = new JButton("Envoi");
        boutonEnvoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //addPanelToHistory(new CycPagePanel().asFormPannel());
                Cours cours = getCurrentCours();

                try {
                    FileHandling.writeCoursToJsonFile(cours);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        content.add(boutonEnvoi);

        return this;
    }

    public CycPagePanel asVisuPannel(Cours cours){
        retour.setVisible(true);

        content.removeAll();
        content.updateUI(); //Nécessaire juste ici, à voir pourquoi...
        content.setLayout(new FlowLayout(FlowLayout.CENTER, Utils.WIDTH, 10));

        CycTextPanel nomFieldPannel = CycTextPanel.createFormTextField("Nom", content, cours.getNom());
        nomFieldPannel.getTextComponent().setEditable(false);
        content.add(nomFieldPannel);

        CycTextPanel texteFieldPannel = CycTextPanel.createFormTextPane("Texte", content, cours.getTexte());
        texteFieldPannel.getTextComponent().setEditable(false);

        for(String lien : cours.getLiens()){
            LienLabel lienLabel = new LienLabel(lien);
            lienLabel.setName("Lien");
            content.add(lienLabel);
        }

        return this;
    }

    public CycPagePanel asListeCoursPanel() {
        try {
            return asListeCoursPanel(FileHandling.getListeCoursFromJsonFile());
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public CycPagePanel asListeCoursPanel(Cours[] liste_cours){
        retour.setVisible(true);

        content.removeAll();
        content.setLayout(new BorderLayout(0, 10));

        JPanel listecours_panel = new JPanel();
        listecours_panel.setLayout(new BoxLayout(listecours_panel, BoxLayout.Y_AXIS));
        for (Cours cours : liste_cours) {
            JButton boutonCours = new JButton(cours.getNom());
            boutonCours.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    parentFrame.addPanelToHistory(new CycPagePanel(parentFrame).asListeCoursPanel(liste_cours));
                    CycPagePanel.this.asVisuPannel(cours);
                }
            });
            listecours_panel.add(boutonCours);
        }
        content.add(listecours_panel, BorderLayout.CENTER);
        return this;
    }
    //endregion

    //Todo : Refactor and think about construction of Cours. Utiliser des Optionals ?
    // Aussi, Renommer la région OU bouger la méthode
    //region Methods to interface JFrame and Cours objects
    public Cours getCurrentCours(){
        String nom = ((JTextComponent)Utils.getComponentByName(content, "Nom")).getText();
        String texte = ((JTextComponent)Utils.getComponentByName(content, "Texte")).getText();
        ArrayList<JComponent> arraylist_liens= Utils.getMultiComponentByName(content, "Lien");
        //LocalDate date = LocalDate.now();
        Date date = new Date();
        try {
            String dateLabel = ((JTextComponent)Utils.getComponentByName(content, "Date")).getText();
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            //date = (LocalDate) dtf.parse(dateLabel);
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateLabel);
        }
        catch (NullPointerException e){}
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JComponent[] array_liens = arraylist_liens.toArray(new JComponent[arraylist_liens.size()]);
        String[] liens = new String[array_liens.length];
        for(int i = 0; i < array_liens.length; i++){
            liens[i] = ((LienLabel)(array_liens[i])).getText();
        }
        String idLabel="";
        try {
            idLabel = ((JTextComponent) Utils.getComponentByName(content, "Id")).getText();
        }

        catch (NullPointerException e){}
        Cours cours = new Cours(nom, texte, liens, date, idLabel!=""?UUID.fromString(idLabel):UUID.randomUUID());
        return cours;
    }


    //endregion
}
