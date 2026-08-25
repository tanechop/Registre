import javax.swing.*;
import java.awt.*;


public class Connexion extends JFrame {

    private JTextField champ1;
    private JTextField champ2;
    private JButton bouton;
    private JButton bouton2;
    private JButton bouton3;
    private JLabel titre;


    public Connexion() {

        //ESPACE INTERNE ENTTE LES ELEMENTS
        //JPanel principale =new JPanel(new GridLayout());
        JPanel principale = new JPanel(new BorderLayout(0, 10));
        principale.setBackground(Color.blue);
        setTitle("Inscription");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //permet de centralisser la page
        setLocationRelativeTo(null);

// 2. Rendre le panel du formulaire transparent pour laisser para+�tre le fond
        titre= new JLabel("Connexion", SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
        titre.setForeground(new Color(255, 255, 255, 255)); // Optionnel : couleur du texte

// 2. Ajouter une marge sous le titre (20px en bas)
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
// 3. L'ajouter en HAUT de votre panel principal
        principale.add(titre, BorderLayout.NORTH);

        //cree le panel pu bloc qui vas contenir les champs et les boutons
        JPanel centre = new JPanel(new GridBagLayout());//PERMET DE MODIFIER LES ELEMENTS D'un
        //centre.setBackground(new Color(255, 255, 255, 119));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 5, 5, 20);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel nom=new  JLabel(" Nom :",SwingConstants.CENTER);
        nom.setFont(new Font("BOOK Antiqua",Font.BOLD,16));
        centre.add(nom,gbc);
        //.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.6;
        champ1 = new JTextField();
        centre.add(champ1,gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel passeword=new  JLabel("Mot de passe :",SwingConstants.CENTER);// CRRER un label
        passeword.setFont(new Font("BOOK Antiqua",Font.BOLD,16));
        centre.add(passeword,gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        champ2 = new JPasswordField();//cree un interface de champ qui vas contenir le mot de passe saisir et le cripter
        centre.add(champ2,gbc);//ajouter le champ au bloc ou panel (centre)

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 5, 10, 20);
        centre.add(new JLabel(""),gbc);
        JLabel Forgot = new JLabel("Mot de passe oublié ?");
        Forgot.setFont(new Font("BOOK Antiqua",Font.BOLD,14));
        Forgot.setForeground(Color.BLUE);
        Forgot.setCursor(new Cursor(Cursor.HAND_CURSOR));//Permet de pionter par un curseur le mot toucher
        centre.add(Forgot,gbc);

        //ajouter le stilkel juste,la croit,et retour a coter du bouton ok aver le code \u2713,\u2716,u21BB
        bouton = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>\u2713</span> OK</html>\"");
        bouton2= new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span>Annuler</htmL");
        bouton3= new JButton("<html><span style='color: #4b5563; font-size: 10px;'>\u21BB</span>Rénitialiser</html");

        //CREER un bloc nomme panelbouton qui vas contenir tout les boutons puis les ranges en ordre
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));// PERMET DE RANGER LES BOUTONS DU COTE DROIT
        panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Ajouter les boutons au grands bloc
        panelBoutons.add(bouton3);
        panelBoutons.add(bouton2);
        panelBoutons.add(bouton);
        //panelBoutons.setBackground(new Color(121, 142, 246, 255));

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // +�tendu sur les 2 colonnes
        gbc.insets = new Insets(10, 5,0,20);

        centre.add(new JLabel("")); // Espace vide dans la grille
        centre.add(panelBoutons,gbc);//ajouter le bloc qui contient le bloc panelboutons


        setContentPane(principale);//afficher la pager principale
        principale.add(centre, BorderLayout.CENTER);//afficher la page principale et la placerau centre du jframe de la page de connexion

    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new Connexion().setVisible(true);// Permet de rendre visible la page pageconnexion
        });

    }
}

