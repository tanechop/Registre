package dao;

import database.DatabaseConnection;
import model.Visite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class VisiteDAO {

    public boolean enregistrerVisite(Visite visite) {

        String sql = """
            INSERT INTO Visites
            (motif, date_visite, heure_de_depart, heure_d_arrivee, service,
             Visiteurs_id_Visiteurs, Utilisateur_id_Utilisateur)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, visite.getMotif());
            statement.setTimestamp(2, Timestamp.valueOf(visite.getDateVisite()));
            statement.setTimestamp(3, Timestamp.valueOf(visite.getHeureDeDepart()));
            statement.setTimestamp(4, Timestamp.valueOf(visite.getHeureDArrivee()));
            statement.setString(5, visite.getService());
            statement.setInt(6, visite.getVisiteurId());
            statement.setInt(7, visite.getUtilisateurId());

            int resultat = statement.executeUpdate();
            return resultat > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }public boolean modifierVisite(Visite visite) {
        String sql = """
        UPDATE Visites
        SET motif=?, heure_d_arrivee=?, heure_de_depart=?, service=?
        WHERE id_Visites=?
        """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, visite.getMotif());
            statement.setTimestamp(2, Timestamp.valueOf(visite.getHeureDArrivee()));
            statement.setTimestamp(3, Timestamp.valueOf(visite.getHeureDeDepart()));
            statement.setString(4, visite.getService());
            statement.setInt(5, visite.getIdVisite());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}