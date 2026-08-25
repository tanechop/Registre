import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class Authentification {
    public String seConnecter(String username, String password) {
        String role = null;
        String requete = "SELECT * FROM Utilisateur WHERE nom_d_utilisateur = ?";

        try(Connection connexion = ConnexionBD.getConnection();
            PreparedStatement statement = connexion.prepareStatement(requete)){
            statement.setString(1,username);
            ResultSet resultat = statement.executeQuery();

            if (resultat.next()){
                String hashstock =  resultat.getString("mot_de_passe");
                if (BCrypt.checkpw(password,hashstock)){
                    role = resultat.getString("role");
                }
            }
        }catch (SQLException e){
            System.out.println("Erreur de connexion"+e.getMessage());
        }

        return role;
    }
}

