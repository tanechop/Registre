import javax.swing.*;
import java.awt.*;

public class RgistreUtilisateur extends JFrame {

    private JTextField NOMUTILISATEUR;
    private JPasswordField motdepasse;
    private JLabel titre ;
    private JRadioButton rbStandard;
    private JRadioButton rbSuperviseur;
    private JButton bouton;
    private JButton bouton2;
    private JButton bouton3;



    public RgistreUtilisateur(){

        setTitle("Creér compte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        // panel principale
        JPanel principale =new JPanel(new BorderLayout(0, 1));

        titre= new JLabel("", SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
        titre.setForeground(new Color(22, 73, 227, 255)); // Optionnel : couleur du texte
// 2. Ajouter une marge sous le titre (20px en bas)
        titre.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
// 3. L'ajouter en HAUT de votre panel principal
        principale.add(titre, BorderLayout.NORTH);

        JPanel centre =new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 15, 50);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel nom=new  JLabel("<html>Nom d'utilisateur  <font color='red'>*</font>:</html>",SwingConstants.CENTER);
        nom.setFont(new Font("BOOK Antiqua",Font.BOLD,16));
        centre.add(nom,gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        NOMUTILISATEUR = new JTextField();
        centre.add(NOMUTILISATEUR,gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel passeword=new  JLabel("<html>Mot de passe <font color='red'>*</font>:</html>",SwingConstants.CENTER);// CRRER un label
        passeword.setFont(new Font("BOOK Antiqua",Font.BOLD,16));
        centre.add(passeword,gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        motdepasse = new JPasswordField();//cree un interface de champ qui vas contenir le mot de passe saisir et le cripter
        centre.add(motdepasse,gbc);

        // --- Ligne 2 : Choix du Rôle (Boutons Radio) ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel lblRole = new JLabel("<html>Rôle  <font color='red'>*</font>:</html>", SwingConstants.CENTER);
        lblRole.setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        centre.add(lblRole, gbc);

        // Configuration des boutons radio
        rbStandard = new JRadioButton("Utilisateur Standard", true);
        rbSuperviseur = new JRadioButton("Superviseur");

        rbStandard.setFont(new Font("BOOK Antiqua", Font.PLAIN, 14));
        rbSuperviseur.setFont(new Font("BOOK Antiqua", Font.PLAIN, 14));


        ButtonGroup groupeRole = new ButtonGroup();
        groupeRole.add(rbStandard);
        groupeRole.add(rbSuperviseur);
        JPanel panelRole = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelRole.add(rbStandard);
        panelRole.add(rbSuperviseur);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.7;
        centre.add(panelRole, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weighty = 1.0;
        centre.add(new JLabel(""), gbc);

        add(principale);
        principale.add(centre,BorderLayout.CENTER);

        //gbc.gridx = 0; gbc.gridy = 4; gbc.weighty = 1.0;
        bouton = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>\u2713</span> OK</html>\"");
        bouton2= new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span> Annuler</htmL");
        bouton3= new JButton("<html><span style='color: #4b5563; font-size: 10px;'>\u21BB</span> Réinitialiser</html");

        //CREER un bloc nomme panelbouton qui vas contenir tout les boutons puis les ranges en ordre
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));// PERMET DE RANGER LES BOUTONS DU COTE DROIT
        panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Ajouter les boutons au grands bloc
        panelBoutons.add(bouton3);
        panelBoutons.add(bouton2);
        panelBoutons.add(bouton);
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
     principale.add(panelBoutons,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new RgistreUtilisateur().setVisible(true));

    }
}
