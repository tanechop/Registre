import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class GestionCompte {

    public void creerCompte(String username, String password, String role, String question, String reponse) throws SQLException{
        String hashMotDePasse = BCrypt.hashpw(password, BCrypt.gensalt());
        String hashReponse = BCrypt.hashpw(reponse, BCrypt.gensalt());
        String requete = "INSERT INTO utilisateur(nom_d_utilisateur, mot_de_passe, role, question_securite, reponse_securite) VALUES (?, ?, ?, ?, ?)";
        try(Connection connexion = ConnexionBD.getConnection();
        PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, username);
            statement.setString(2, hashMotDePasse);
            statement.setString(3, role);
            statement.setString(4, question);
            statement.setString(5, hashReponse);
            statement.executeUpdate();
        }
    }

    public boolean supprimerCompte(String username){
        String requete = "DELETE FROM utilisateur WHERE nom_d_utilisateur = ?";

        try(Connection connexion = ConnexionBD.getConnection();
        PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setString(1, username);
            return statement.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
