// Ce code ne sert que pour tester la connexion avec la base de données

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnexion {

    public static void main(String[] args) {
        try(Connection connexion = ConnexionBD.getConnection()){
            if(connexion != null){
                System.out.println("Connexion établie");
            }
        }catch (SQLException e){
            System.out.println("Erreur de connexion : "+e.getMessage());
        }
    }
}