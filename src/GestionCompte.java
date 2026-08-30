import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class GestionCompte {

    public void creerCompte(String username, String password, String role, String question, String reponse) throws SQLException {
        String hashMotDePasse = BCrypt.hashpw(password, BCrypt.gensalt());
        String hashReponse = BCrypt.hashpw(reponse, BCrypt.gensalt());
        String requete = "INSERT INTO utilisateur(nom_d_utilisateur, mot_de_passe, role, question_securite, reponse_securite) VALUES (?, ?, ?, ?, ?)";
        try (Connection connexion = ConnexionBD.getConnection();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, username);
            statement.setString(2, hashMotDePasse);
            statement.setString(3, role);
            statement.setString(4, question);
            statement.setString(5, hashReponse);
            statement.executeUpdate();
        }
    }

    public List<Object[]> listerComptes() throws SQLException {
        List<Object[]> comptes = new ArrayList<>();
        String requete = "SELECT nom_d_utilisateur, role FROM utilisateur";
        try (Connection connexion = ConnexionBD.getConnection();
             PreparedStatement statement = connexion.prepareStatement(requete);
             ResultSet resultat = statement.executeQuery()) {

            while (resultat.next()) {
                String nom = resultat.getString("nom_d_utilisateur");
                String role = resultat.getString("role");
                Object[] ligne = {nom, role};
                comptes.add(ligne);
            }
        }
        return comptes;
    }

    public void supprimerCompte(String username) throws SQLException {
        String requete = "DELETE FROM utilisateur WHERE nom_d_utilisateur = ?";
        try (Connection connexion = ConnexionBD.getConnection();
        PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }

    public void modifierCompte(String ancienNom, String nouveauNom, String nouveauRole) throws SQLException{
        if (!ancienNom.equals(nouveauNom) && existeCompte(nouveauNom)) {
            throw new SQLException("Ce nom d'utilisateur est déjà utilisé.");
        }
        String requete = "UPDATE utilisateur SET nom_d_utilisateur = ?, role = ? WHERE nom_d_utilisateur = ?";
        try(Connection connexion = ConnexionBD.getConnection();
            PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, nouveauNom);
            statement.setString(2, ancienNom);
            statement.setString(3, nouveauRole);
            statement.executeUpdate();
        }
    }

    public boolean existeCompte(String username) throws SQLException {
        String requete = "SELECT 1 FROM utilisateur WHERE nom_d_utilisateur = ?";
        try (Connection connexion = ConnexionBD.getConnection();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, username);
            try (ResultSet resultat = statement.executeQuery()) {
                return resultat.next();
            }
        }
    }
}
