import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MotDePasseOublie extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelPrincipal;
    private JTextField champUsername;
    private ReinitialisationMotDePasse reinitialisation = new  ReinitialisationMotDePasse();
    private String usernameEnCours;
    private JLabel labelQuestion;
    private JTextField champReponse;
    private JPasswordField champNouveauMotDePasse;
    private JPasswordField champConfirmation;

    public MotDePasseOublie() {
        setTitle("Mot de passe oublié ?");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(creerEcranUsername(), "ECRAN_USERNAME");
        panelPrincipal.add(creerEcranQuestion(), "ECRAN_QUESTION");
        panelPrincipal.add(creerEcranNouveauMotDePasse(), "ECRAN_NOUVEAU_MDP");

        setContentPane(panelPrincipal);
    }

    private JPanel creerEcranUsername() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel labelNom = new JLabel("Nom d'utilisateur :");
        labelNom.setFont(StyleUI.POLICE_LABEL);
        panel.add(labelNom, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        champUsername = new JTextField(15);
        panel.add(champUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton boutonSuivant  = new JButton("Suivant");
        boutonSuivant.setFont(StyleUI.POLICE_BOUTON);
        boutonSuivant.setBackground(StyleUI.MARINE);
        boutonSuivant.setForeground(Color.WHITE);
        boutonSuivant.setOpaque(true);
        boutonSuivant.setBorderPainted(false);
        boutonSuivant.setFocusPainted(false);
        boutonSuivant.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(boutonSuivant, gbc);

        boutonSuivant.addActionListener(e -> {
            usernameEnCours = champUsername.getText();
            try {
                String question = reinitialisation.recupererQuestion(usernameEnCours);
                if(question != null){
                    labelQuestion.setText(question);
                    cardLayout.show(panelPrincipal, "ECRAN_QUESTION");
                }else{
                    JOptionPane.showMessageDialog(this, "Utilisateur introuvable.");
                }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données");
            }
        });
        return panel;
    }

    private JPanel creerEcranQuestion() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        labelQuestion = new JLabel("");
        labelQuestion.setFont(StyleUI.POLICE_LABEL);
        panel.add(labelQuestion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel labelReponse = new JLabel("Réponse :");
        labelReponse.setFont(StyleUI.POLICE_LABEL);
        panel.add(labelReponse, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        champReponse = new JTextField(15);
        panel.add(champReponse, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton boutonValider = new JButton("Valider");
        boutonValider.setFont(StyleUI.POLICE_BOUTON);
        boutonValider.setBackground(StyleUI.MARINE);
        boutonValider.setForeground(Color.WHITE);
        boutonValider.setOpaque(true);
        boutonValider.setBorderPainted(false);
        boutonValider.setFocusPainted(false);
        boutonValider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(boutonValider, gbc);

        boutonValider.addActionListener(e -> {
            try {
                boolean correcte = reinitialisation.verifierReponse(usernameEnCours, champReponse.getText());
                if(correcte){
                    cardLayout.show(panelPrincipal, "ECRAN_NOUVEAU_MDP");
                }else{
                    JOptionPane.showMessageDialog(this, "Réponse incorrecte.");
                }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.");
            }
        });
        return panel;
    }

    private JPanel creerEcranNouveauMotDePasse() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel labelNouveau = new JLabel("Nouveau mot de passe :");
        labelNouveau.setFont(StyleUI.POLICE_LABEL);
        panel.add(labelNouveau, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        champNouveauMotDePasse = new JPasswordField(15);
        panel.add(champNouveauMotDePasse, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelConfirmer = new JLabel("Confirmer mot de passe :");
        labelConfirmer.setFont(StyleUI.POLICE_LABEL);
        panel.add(labelConfirmer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        champConfirmation = new JPasswordField(15);
        panel.add(champConfirmation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton boutonEnregistrer = new JButton("Enregistrer");
        boutonEnregistrer.setFont(StyleUI.POLICE_BOUTON);
        boutonEnregistrer.setBackground(StyleUI.MARINE);
        boutonEnregistrer.setForeground(Color.WHITE);
        boutonEnregistrer.setOpaque(true);
        boutonEnregistrer.setBorderPainted(false);
        boutonEnregistrer.setFocusPainted(false);
        boutonEnregistrer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(boutonEnregistrer, gbc);

        boutonEnregistrer.addActionListener(e -> {
            String mdp1 = new String(champNouveauMotDePasse.getPassword());
            String mdp2 = new String(champConfirmation.getPassword());
            if(!mdp1.equals(mdp2)){
                JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas");
                return;
            }
            try{
                reinitialisation.changerMotDePasse(usernameEnCours, mdp1);
                JOptionPane.showMessageDialog(this, "Mot de passe changé avec succès");
                dispose();
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.");
            }
        });
        return panel;

    }
}
