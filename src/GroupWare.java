import javax.swing.*;
import java.awt.*;

public class GroupWare {
    private static ImageIcon chrgerIcone(String path, int largeur, int hauteur) {
        java.net.URL imgURL = GroupWare.class.getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            // Redimensionnement dynamique ici
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            System.err.println("Image introuvable : " + path);
            return null;
        }
    }
public static void main(String[] args){
    JFrame page = new JFrame("Groupware");
    page.setSize(500, 300);
    page.setLocationRelativeTo(null);
    page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Panneau principal divisé en 3 lignes de même taille
    JPanel menu = new JPanel(new GridLayout(3, 1, 5, 5));

    // Chargement des icônes (remplacez par vos chemins d'accès)
    ImageIcon iconPower = chrgerIcone("/images/images.png",50,50);
    ImageIcon iconUser  = chrgerIcone("/images/9187604.png",50,50);
    ImageIcon iconExit  = chrgerIcone("/images/quitter7.png",50,50);

    // Création des 3 grands boutons cliquables
    JButton btnLancer = new JButton("Lancer l'application", iconPower);
    JButton btnGestion = new JButton("Gestion des utilisateurs", iconUser);
    JButton btnQuitter = new JButton("Fermer l'application", iconExit);

    // Alignement du texte et de l'icône à gauche avec un peu de marge
    btnLancer.setHorizontalAlignment(SwingConstants.LEFT);
    btnGestion.setHorizontalAlignment(SwingConstants.LEFT);
    btnQuitter.setHorizontalAlignment(SwingConstants.LEFT);

    // Style pour agrandir le texte et ajouter de l'espace interne (padding)
    Font policeTexte = new Font("BOOK Antique", Font.BOLD, 20);

    JButton[] boutons = {btnLancer, btnGestion, btnQuitter};// FONCTION qui gere les boutons
    for (JButton btn : boutons) {
        btn.setFont(policeTexte);
        btn.setIconTextGap(20); // Espace entre l'icône et le texte
        btn.setFocusPainted(false); // Enlève la bordure de sélection autour du texte
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu.add(btn);
    }

    // Actions au clic sur les boutons
    btnLancer.addActionListener(e -> {
        JOptionPane.showMessageDialog(page, "Lancement de l'application...");
    });

    btnGestion.addActionListener(e -> {
        JOptionPane.showMessageDialog(page, "Ouverture de la gestion des utilisateurs...");
    });

    btnQuitter.addActionListener(e -> {
        System.exit(0);
    });

    page.add(menu);
    page.setResizable(false);
    page.setVisible(true);
}

}
