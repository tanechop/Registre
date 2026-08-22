package dao;

import database.DatabaseConnection;
import model.Visiteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VisiteurDAO {

    // Returns the new id_Visiteurs on success, or -1 on failure
    public int enregistrerVisiteur(Visiteur visiteur) {

        String sql = "INSERT INTO Visiteurs (nom, prenom, contact, num_CNI) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, visiteur.getNom());
            statement.setString(2, visiteur.getPrenom());
            statement.setInt(3, visiteur.getContact());
            statement.setString(4, visiteur.getNumCni());

            int resultat = statement.executeUpdate();
            if (resultat == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}