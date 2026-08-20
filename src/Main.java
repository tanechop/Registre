import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private JTextField champ1;
    private JTextField champ2;
    private JTextField champ3;
    private JTextField champ4;
    private JTextField champ5;
    private JTextField champ6;
    private JTextField champ7;
    private JButton bouton1;
    private JButton bouton2;
    private JButton bouton3;
    //private JButton bouton4;
    private JLabel titre;

    public Main(){

        // permet de modifier les position avec le GridBagconstraints
        setSize(800,720);
        setTitle("Enregistrement");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.ORANGE);
        //GridBagConstraints gbc=new GridBagConstraints();
        //gbc.fill=GridBagConstraints.HORIZONTAL;
        //gbc.insets=new Insets(20,5,5,20);
        // PANEL PRINCIPALE
        JPanel panelprincipale=new JPanel(new BorderLayout());
        panelprincipale.setBorder(BorderFactory.createEmptyBorder(20,10,0,0));
        panelprincipale.setBackground(new Color(121, 142, 246)); // ou Color.ORANGE

// 2. Rendre le panel du formulaire transparent pour laisser paraître le fond
        titre= new JLabel("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;background-color:blue'></span>PAGE D'ENREGISTREMENT</html>", SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
        titre.setForeground(new Color(122, 255, 101)); // Optionnel : couleur du texte

// 2. Ajouter une marge sous le titre (20px en bas)
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
// 3. L'ajouter en HAUT de votre panel principal
        panelprincipale.add(titre, BorderLayout.NORTH);

        JPanel panel=new JPanel( new GridLayout(8,2,0,50));// INPOSE UNE MEME longueur au champs de texte et defini le nombre de lignes et de colonnes utilise dans le panel
        //gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.setBackground(new Color(121, 142, 246));
        panel.add(new JLabel("<html>Nom <font color='red'>*</font> : </html>",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.6;
        champ1 = new JTextField();
        panel.add(champ1);
        panel.add(new JLabel(""));

        //gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("<html>Prénom <font color='red'>*</font> :</html>:",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.6;
        champ2 = new JTextField();
        panel.add(champ2);
        panel.add(new JLabel(""));

        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("<html>N°CNI <font color='red'>*</font> :</html>:",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.6;
        champ3 = new JTextField();
        panel.add(champ3);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>N°Tèlèphone <font color='red'>*</font> :</html>:",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.6;
        champ4 = new JTextField();
        panel.add(champ4);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>Heure d'arriver <font color='red'>*</font> :</html>",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.6;
        champ5 = new JTextField();
        panel.add(champ5);
        panel.add(new JLabel(""));


        panel.add(new JLabel("<html>Heure de depart <font color='red'>*</font> :</html>",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.6;
        champ6 = new JTextField();
        panel.add(champ6);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>Motif <font color='red'>*</font> :</html>",SwingConstants.CENTER){{ setFont(new Font("BOOK Antiqua", Font.BOLD, 16)); }});
        //gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.6;
        champ7 = new JTextField();
        panel.add(champ7);
        panel.add(new JLabel(""));
        /*JPanel wrapper =new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        champ7.setPreferredSize(new Dimension(500,50));
        wrapper.add(champ7);
        panel.add(wrapper);
        wrapper.setBackground(new Color(121, 142, 246));*/

        //panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        bouton1 = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>\u2713</span> ok</html>");
        bouton2= new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span>Annuler</htmL");
        bouton3= new JButton("<html><span style='color: #4b5563; font-size: 10px;'>\u21BB</span>Retour</html");
        //bouton1.setFont(new Font("ARIAL",Font.BOLD,20));

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));// PERMET DE RANGER LES BOUTONS DU COTE DROIT
        panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Ajouter les boutons au grands bloc
        panelBoutons.add(bouton3);
        panelBoutons.add(bouton2);
        panelBoutons.add(bouton1);
        //panelBoutons.add(bouton4);
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelBoutons.setBackground(new Color(122, 150, 236));
        panel.add(panelBoutons,BorderLayout.NORTH);

        panelprincipale.add(panel);
        add(panelprincipale);//afficher la pager principale
        setVisible(true);

        //panel.setOpaque(false);
        //panel.setBackground(Color.blue);
        getContentPane().setBackground(Color.ORANGE);
    }
    public static void main (String[] args){
        new Main();// Permet de rendre visible la page pageconnexion
    }
}

