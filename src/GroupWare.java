import javax.swing.*;
import java.awt.*;

public class GroupWare extends JFrame{

    private static ImageIcon chrgerIcone(String path, int largeur, int hauteur) {
        java.net.URL imgURL = GroupWare.class.getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            // Redimensionnement dynamique ici
            Image img = icon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            System.err.println("Image introuvable : " + path);
            return null;
        }
    }

    public GroupWare() {
        setTitle("Groupware");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titre = new JLabel("Groupware", SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 24));
        titre.setForeground(new Color(22, 73, 227, 255));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        JPanel menu = new JPanel(new GridLayout(3, 1, 1, 5));
        menu.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        ImageIcon iconPower = chrgerIcone("/images/images.png", 50, 50);
        ImageIcon iconUser = chrgerIcone("/images/9187604.png", 50, 50);
        ImageIcon iconExit = chrgerIcone("/images/quitter7.png", 50, 50);

        JButton btnLancer = new JButton("Lancer l'application", iconPower);
        JButton btnGestion = new JButton("Gérer les comptes", iconUser);
        JButton btnQuitter = new JButton("Fermer l'application", iconExit);

        btnLancer.setHorizontalAlignment(SwingConstants.LEFT);
        btnGestion.setHorizontalAlignment(SwingConstants.LEFT);
        btnQuitter.setHorizontalAlignment(SwingConstants.LEFT);

        Font policeTexte = new Font("BOOK ANTIQUE", Font.BOLD, 20);

        JButton[] boutons = {btnLancer, btnGestion, btnQuitter};
        for (JButton btn : boutons) {
            btn.setFont(policeTexte);
            btn.setIconTextGap(20);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color (220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            btn.setBackground(Color.WHITE);
            btn.setOpaque(true);
            menu.add(btn);
        }

        btnLancer.addActionListener(e -> {
            new PageAccueil("UtilisateurTest").setVisible(true);
            dispose();

        });

        btnGestion.addActionListener(e -> {
            new UserManagementFrame().setVisible(true);
            dispose();
        });

        btnQuitter.addActionListener(e -> {
            System.exit(0);
        });

        add(titre, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
        setResizable(false);
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new GroupWare().setVisible(true));
}

}
