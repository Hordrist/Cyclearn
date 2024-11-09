import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Cours implements Serializable {
    private String nom;
    private String texte;
    private String[] liens;
    public Date date;
    public UUID id;


    public Cours(String nom, String texte, String[] liens) {
        this.nom = nom;
        this.texte = texte;
        this.liens = liens;
        this.date = new Date();
        this.id = UUID.randomUUID();
    }

    //region Attributes setters and getters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getLiens() {
        return liens;
    }

    public void setLiens(String[] liens) {
        this.liens = liens;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(){
        //this.date = LocalDate.now();
        this.date = new Date();
    }
    //endregion

    public String getJsonFromCours(){
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return gson.toJson(this);
    }
}
