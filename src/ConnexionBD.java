import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String url = "jdbc:mysql://localhost:3306/gestion_visiteurs";
    private static final String username = "root";
    private static final String password = "";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connexion réussie");
            return connection;
        }catch (SQLException e){
            System.out.println("Erreur de connexion"+e.getMessage());
            return null;
        }
    }
}


