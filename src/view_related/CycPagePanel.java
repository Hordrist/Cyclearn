package view_related;

import business_objects.Cours;
import utils_helpers.Callback;
import utils_helpers.FileHandling;
import utils_helpers.Utils;
import utils_helpers.serialization.SerializableActionListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CycPagePanel extends JPanel implements Serializable{
    private final JPanel retour;
    private final JPanel content;
    private final JPanel footer;

    public CycPagePanel(GUIWindow window) {
        super();
        setSize(Utils.WIDTH, Utils.HEIGHT);
        setLayout(new BorderLayout(0,20));


        retour = createRetourButton();
        add(retour, BorderLayout.NORTH);

        content = new JPanel();
        add(content, BorderLayout.CENTER);

        footer = new JPanel();
        footer.setVisible(false);
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, Utils.WIDTH, 10));
        add(footer, BorderLayout.SOUTH);
    }


    //region Object methods
    public JPanel createRetourButton() {
        JPanel grouping = new JPanel();
        grouping.setLayout(new BoxLayout(grouping, BoxLayout.X_AXIS));
        JButton retour = new JButton("Retour");
        retour.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                getParentFrame().returnToPrecedentPanel(CycPagePanel.this);

            }
        });
        grouping.add(retour);
        return grouping;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CycPagePanel asMenuPanel(){
        retour.setVisible(false);

        content.removeAll();
        content.setLayout(new CenteredBoxLayout(content, BoxLayout.PAGE_AXIS));

        JButton btn_ajout_cours = new JButton("Ajouter un cours");
        btn_ajout_cours.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                CycPagePanel.this.asFormPanel();
            }
        });
        content.add(btn_ajout_cours);

        JButton btn_liste_cours = new JButton("Visualiser et modifier les cours");
        btn_liste_cours.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                CycPagePanel.this.asListeCoursPanel();
            }
        });
        //btn_liste_cours.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(btn_liste_cours);

        //TL;DR Pour ajouter MenuPanel directement dans l'historique "history"
        //Actuellement, on ajoute un panel à l'historique dès qu'on quitte ce panel AUTREMENT qu'en cliquant
        //sur le bouton retour, afin de ne pas risquer de boucles.
        //La particularité d'un MenuPanel, c'est qu'il n'a pas de bouton "retour".
        //Ainsi, la seule manière d'en sortir, c'est en cliquant sur "ajouter" ou "visualiser un cours"
        //Et donc on peut ajouter MenuPanel à l'historique dès qu'on rentre dedans
        getParentFrame().addPanelToHistory(this);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CycPagePanel asFormPanel(){
        retour.setVisible(true);

        content.removeAll();
        content.setLayout(new CenteredBoxLayout(content, BoxLayout.PAGE_AXIS));

        JPanel NomTextField = CycTextPanel.createFormTextField("Nom", content);
        content.add(NomTextField);

        JPanel TexteTextArea = CycTextPanel.createFormTextPane("Texte", content);
        content.add(TexteTextArea);

        JButton boutonAjoutLien = new JButton("Ajouter un lien");
        boutonAjoutLien.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indexBoutonAjout = Utils.getComponentIndex(boutonAjoutLien);
                content.add(new LienLabelPane(), indexBoutonAjout);
                updateUI();
            }
        });
        content.add(boutonAjoutLien);

        JButton boutonEnvoi = new JButton("Envoi");
        boutonEnvoi.addActionListener(new SerializableActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cours cours = createCoursFromForm();
                try {
                    FileHandling.writeCoursToJsonFile(cours);
                    showActionSuccessText(
                            "Le cours a bien été créé.",
                            new Callback(){
                                @Override
                                public void call() {
                                    asListeCoursPanel();
                                    updateUI();
                                }
                            }
                    );
                } catch (IOException ex) {
                    System.err.println("Unable to write cours to json file :\n" + ex.getMessage());
                }
            }
        });
        content.add(boutonEnvoi);

        Dimension dim_to_fill = Utils.getDimensionToFill(content, Utils.ToFill.FILL_HEIGHT);
        content.add(new Box.Filler(dim_to_fill,dim_to_fill,dim_to_fill));
        System.out.println(NomTextField.getPreferredSize());

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CycPagePanel asVisuPanel(Cours cours){
        retour.setVisible(true);

        content.removeAll();
        content.updateUI(); //Nécessaire juste ici, à voir pourquoi...
        content.setLayout(new CenteredBoxLayout(content, BoxLayout.PAGE_AXIS));

        CycTextPanel nomFieldPanel = CycTextPanel.createFormTextField("Nom", content, cours.getNom());
        nomFieldPanel.getTextComponent().setEditable(false);
        content.add(nomFieldPanel);

        CycTextPanel texteFieldPanel = CycTextPanel.createFormTextPane("Texte", content, cours.getTexte());
        texteFieldPanel.getTextComponent().setEditable(false);
        content.add(texteFieldPanel);

        for(String lien : cours.getLiens()){
            LienLabel lienLabel = new LienLabel(lien);
            lienLabel.setName("Lien");
            content.add(lienLabel);
        }

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CycPagePanel asListeCoursPanel() {
        try {
            return asListeCoursPanel(FileHandling.getListeCoursFromJsonFile());
        }
        catch (IOException e1) {
            System.err.println("Error reading courses file :\n" + e1.getMessage());
            return null;
        }
    }

    public CycPagePanel asListeCoursPanel(Cours[] liste_cours){
        retour.setVisible(true);

        content.removeAll();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        for (Cours cours : liste_cours) {
            JPanel cours_panel = createCoursButtonForListe(cours);
            content.add(cours_panel);
        }
        Dimension filler_dim = Utils.getDimensionToFill(content, Utils.ToFill.FILL_HEIGHT);
        content.add(new Box.Filler(filler_dim,filler_dim,filler_dim));
        return this;
    }

    public void showActionSuccessText(String successText, Callback callback){
        new Thread() {
            public void run() {
                JLabel successLabel = new JLabel(successText);
                successLabel.setForeground(Color.GREEN);
                footer.add(successLabel);
                footer.setVisible(true);
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    footer.removeAll();
                    footer.setVisible(false);
                    callback.call();
                }
            }
        }.start();
    }

    public JPanel createCoursButtonForListe(Cours cours){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton boutonCours = new JButton(cours.getNom());
        boutonCours.addActionListener(new SerializableActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getParentFrame().addPanelToHistory(CycPagePanel.this);
                CycPagePanel.this.asVisuPanel(cours);
            }
        });
        panel.add(boutonCours);

        ImageIcon trashicon = new ImageIcon("assets/trash-icon.png");
        JButton supprimerCours = new JButton(trashicon);
        supprimerCours.setPreferredSize(new Dimension(trashicon.getIconWidth()+10,
                trashicon.getIconHeight()+10));
        supprimerCours.addActionListener(new SerializableActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileHandling.deleteCoursFromJsonFile(cours);
                    JPanel parentPanel = (JPanel) panel.getParent();
                    parentPanel.remove(panel);
                    parentPanel.updateUI();
                    System.out.println("business_objects.Cours supprimé");
                } catch (IOException ex) {
                    System.err.println("Erreur lors de la suppression du cours");
                }
            }
        });
        panel.add(supprimerCours);
        return panel;

    }

    //endregion

    //region Methods to interface JFrame and business_objects.Cours objects
    public Cours createCoursFromForm(){
        String nom = ((JTextComponent) Utils.getComponentByName(content, "Nom")).getText();
        String texte = ((JTextComponent) Utils.getComponentByName(content, "Texte")).getText();
        ArrayList<LienLabel> arraylist_liens= Utils.getMultiComponentByName(content, "Lien");
        String[] liens = new String[arraylist_liens.size()];
        for(int i = 0; i < liens.length; i++){
            liens[i] = (arraylist_liens.get(i)).getText();
        }
        return new Cours(nom, texte, liens);
    }

    private GUIWindow getParentFrame(){
        return (GUIWindow) SwingUtilities.getWindowAncestor(this);
    }

    //endregion
}
