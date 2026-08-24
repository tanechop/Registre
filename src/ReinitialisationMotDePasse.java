import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class ReinitialisationMotDePasse {

    public String recupererQuestion(String username) throws SQLException {
        String question = null;
        String requete = "SELECT question_securite FROM utilisateur WHERE nom_d_utilisateur = ?";
        try(Connection connexion = ConnexionBD.getConnection();
        PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, username);
            ResultSet resultat = statement.executeQuery();
            if (resultat.next()) {
                question = resultat.getString("question_securite");
            }

        }
        return question;
    }

    public boolean verifierReponse(String username, String reponse) throws SQLException {
        boolean reponseCorrecte = false;
        String requete = "SELECT reponse_securite FROM  utilisateur WHERE nom_d_utilisateur = ?";
        try(Connection connexion = ConnexionBD.getConnection();
        PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, username);
            ResultSet resultat = statement.executeQuery();
            if (resultat.next()) {
                String hashStocke =  resultat.getString("reponse_securite");
                reponseCorrecte = BCrypt.checkpw(reponse, hashStocke);
            }
        }
        return reponseCorrecte;
    }

    public void changerMotDePasse(String username, String nouveauMotDePasse) throws SQLException {
        String hashNouveauMotDePasse = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
        String requete = "UPDATE utilisateur SET mot_de_passe = ? WHERE nom_d_utilisateur = ?";
        try(Connection connexion = ConnexionBD.getConnection();
        PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, hashNouveauMotDePasse);
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }
}
