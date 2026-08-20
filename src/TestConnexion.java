import java.sql.Connection;

public class TestConnexion {

    public static void main(String[] args) {
        Connection connexion = ConnexionBD.getConnection();
        if (connexion != null) {
            System.out.println("La connexion fonctionne !");
        } else {
            System.out.println("La connexion a échoué.");
        }
    }
}