//Ceci sert à convertir le mot de passe (en clair) en vrai hash BCrypt

import org.mindrot.jbcrypt.BCrypt;

public class GenererHash {
    public static void main(String[] args) {
        String motDePasseEnClair = "foresight";
        String hash = BCrypt.hashpw(motDePasseEnClair, BCrypt.gensalt());
        System.out.println(hash);
    }
}
