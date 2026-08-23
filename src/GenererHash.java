//Ceci sert à convertir le mot de passe (en clair) en vrai hash BCrypt

import org.mindrot.jbcrypt.BCrypt;

public class GenererHash {
    public static void main(String[] args) {
        String motDePasseEnClair = "foresight";
        String texte = "NARUTO";
        String hash = BCrypt.hashpw(motDePasseEnClair, BCrypt.gensalt());
        String hash2 = BCrypt.hashpw(texte,BCrypt.gensalt());
        System.out.println(hash);
        System.out.println(hash2);
    }
}
