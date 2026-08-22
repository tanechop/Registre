package model;

public class Visiteur {
    private int id_visiteur;
    private String nom;
    private String prenom;
    private int contact;
    private String num_cni;

    public Visiteur() {
    }

    public Visiteur(String nom, String prenom, int contact, String num_cni) {
        this.nom = nom;
        this.prenom = prenom;
        this.contact = contact;
        this.num_cni = num_cni;
    }

    public int getIdVisiteur() {
        return id_visiteur;
    }

    public void setIdVisiteur(int id_visiteur) {
        this.id_visiteur = id_visiteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getNumCni() {
        return num_cni;
    }

    public void setNumCni(String num_cni) {
        this.num_cni = num_cni;
    }
}