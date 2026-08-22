package model;

import java.time.LocalDateTime;

public class Visite {
    private int id_visite;
    private String motif;
    private LocalDateTime date_visite;
    private LocalDateTime heure_de_depart;
    private LocalDateTime heure_d_arrivee;
    private String service;
    private String enregistre_par;
    private int visiteurId;
    private int utilisateurId;

    public Visite() {
    }

    public Visite(String motif, LocalDateTime heure_d_arrivee, LocalDateTime heure_de_depart,
                  String service, String enregistre_par, int visiteurId, int utilisateurId) {
        this.motif = motif;
        this.date_visite = LocalDateTime.now();
        this.heure_d_arrivee = heure_d_arrivee;
        this.heure_de_depart = heure_de_depart;
        this.service = service;
        this.enregistre_par = enregistre_par;
        this.visiteurId = visiteurId;
        this.utilisateurId = utilisateurId;
    }

    public int getIdVisite() {
        return id_visite;
    }

    public void setIdVisite(int id_visite) {
        this.id_visite = id_visite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public LocalDateTime getDateVisite() {
        return date_visite;
    }

    public void setDateVisite(LocalDateTime date_visite) {
        this.date_visite = date_visite;
    }

    public LocalDateTime getHeureDeDepart() {
        return heure_de_depart;
    }

    public void setHeureDeDepart(LocalDateTime heure_de_depart) {
        this.heure_de_depart = heure_de_depart;
    }

    public LocalDateTime getHeureDArrivee() {
        return heure_d_arrivee;
    }

    public void setHeureDArrivee(LocalDateTime heure_d_arrivee) {
        this.heure_d_arrivee = heure_d_arrivee;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEnregistrePar() {
        return enregistre_par;
    }

    public void setEnregistrePar(String enregistre_par) {
        this.enregistre_par = enregistre_par;
    }

    public int getVisiteurId() {
        return visiteurId;
    }

    public void setVisiteurId(int visiteurId) {
        this.visiteurId = visiteurId;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}