
    import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

    public class UserManagementFrame extends JFrame {
       // private JTextField txtNom , txtRole;
        private DefaultTableModel tableModel;
        private JTable table;
        private JLabel titre;
        private JButton bouton;
        private JButton bouton2;
        private JButton bouton3;


        public UserManagementFrame() {
            setTitle("Gestion des utilisateurs");
            setSize(700, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);// to center the frame
            // Ftitre de la page
            JPanel panel= new JPanel(new GridLayout(0, 1, 5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(5,10,0,0));
            titre= new JLabel("GESTION DES COMPTES", SwingConstants.CENTER);
            titre.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
            titre.setForeground(new Color(93, 129, 232, 255)); // Optionnel : couleur du texte
           // 2. Ajouter une marge sous le titre (20px en bas)
            titre.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
           // 3. L'ajouter en HAUT de votre panel principal
            panel.add(titre, BorderLayout.NORTH);

            // Formulaire
            /*JPanel panelForm = new JPanel(new GridLayout(0, 2, 0, 0));
             JLabel label=new JLabel("");
            JButton btnAjouter = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>➕</span> Nouveau</html>\"");
            btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnAjouter.setBorderPainted(false);
            //btnAjouter.setContentAreaFilled(false);
             btnAjouter.setFocusPainted(false);
            btnAjouter.setPreferredSize(new Dimension(80,30));
            btnAjouter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));*/

            bouton = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>➕</span> Nouveau</html>\"");
            bouton2= new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span> Supprimer</htmL");
            bouton3= new JButton("<html><span style='color: #4b5563; font-size: 10px;'>&#9999</span> Modifier</html");

            //CREER un bloc nomme panelbouton qui vas contenir tout les boutons puis les ranges en ordre
            JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));// PERMET DE RANGER LES BOUTONS DU COTE DROIT
            panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
            //Ajouter les boutons au grands bloc
            panelBoutons.add(bouton3);
            panelBoutons.add(bouton2);
            panelBoutons.add(bouton);

            panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));



            JPanel panelbouton =new JPanel();
            //panelbouton.add(btnAjouter, BorderLayout.LINE_END);
           // panelForm.add(label);


            // Tableau
            tableModel = new DefaultTableModel(new String[]{"Nom d'utilisateur", "Role"}, 50){{ setFont(new Font("COOPER BLACK", Font.BOLD, 16)); }};
            table = new JTable(tableModel);

            // Action du bouton
            /*btnAjouter.addActionListener(e -> {
                if (!txtNom.getText().isEmpty() && !txtEmail.getText().isEmpty()) {
                    tableModel.addRow(new Object[]{txtNom.getText(), txtEmail.getText()});
                    txtNom.setText("");
                    txtEmail.setText("");
                }else
            });*/

            // Organisation globale
            setLayout(new BorderLayout(10, 10));
            //panel.add(panelForm);;
            panel.add(panelBoutons);
            add(panelbouton,BorderLayout.PAGE_END);
            add(new JScrollPane(table), BorderLayout.CENTER);
            add(panel,BorderLayout.NORTH);
            //add(panelbouton,BorderLayout.PAGE_END);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new UserManagementFrame().setVisible(true));
        }
    }


