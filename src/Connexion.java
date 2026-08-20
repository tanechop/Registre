import javax.swing.*;
import java.awt.*;

public class Connexion extends JFrame{
    private JTextField champ1;
    private JPasswordField champ2;
    private JButton bouton;
    private JButton bouton2;
    private JButton bouton3;



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

        //cree le panel pu bloc qui vas contenir les champs et les boutons
        JPanel centre = new JPanel(new GridBagLayout());//PERMET DE MODIFIER LES
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 5, 5, 20);


        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        centre.add(new JLabel(" NOM:"),gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        champ1 = new JTextField();
        centre.add(champ1,gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        centre.add(new JLabel("PASSWORD :"),gbc);// CRRER un label
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        champ2 = new JPasswordField();//cree un interface de champ qui vas contenir le mot de passe saisir et le cripter
        centre.add(champ2,gbc);//ajouter le champ au bloc ou panel (centre)

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 5, 10, 20);
        centre.add(new JLabel(""),gbc);
        JLabel Forgot = new JLabel("Mot de passe oublié ?");
        Forgot.setForeground(Color.BLUE);
        Forgot.setCursor(new Cursor(Cursor.HAND_CURSOR));//Permet de pionter par un curseur le mot toucher
        centre.add(Forgot,gbc);

        //ajouter le stilkel juste,la croit,et retour a coter du bouton ok aver le code \u2713,\u2716,u21BB
        bouton = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>\u2713</span> OK</html>\"");
        bouton2= new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span> Annuler</htmL");
        bouton3= new JButton("<html><span style='color: #4b5563; font-size: 10px;'>\u21BB</span> Reset</html");

        //CREER un bloc nomme panelbouton qui vas contenir tout les boutons puis les ranges en ordre
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));// PERMET DE RANGER LES BOUTONS DU COTE DROIT
        panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Ajouter les boutons au grands bloc
        panelBoutons.add(bouton3);
        panelBoutons.add(bouton2);
        panelBoutons.add(bouton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // Étendu sur les 2 colonnes
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

