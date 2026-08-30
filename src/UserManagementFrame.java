
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
public class UserManagementFrame extends JFrame {

    private DefaultTableModel tableModel;
    private JButton boutonhistorique ;
    private JButton buttonenregistrer;
    private JButton buttondeconnection;
    private JTable table;
    private JLabel titre;

    private GestionCompte gestionCompte = new GestionCompte();

    public UserManagementFrame(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setTitle("Gestion des comptes");
        setSize(1000,500);

        titre= new JLabel("Gestion des comptes", SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 25));
        titre.setForeground(StyleUI.MARINE); // Optionnel : couleur du texte


// 2. Ajouter une marge sous le titre (20px en bas)
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
// 3. L'ajouter en HAUT de votre panel principal

        JPanel panelGauche = new JPanel();
        panelGauche.setLayout(new GridBagLayout());
        panelGauche.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        boutonhistorique = new JButton("Liste des comptes");
        boutonhistorique.setFont(StyleUI.POLICE_BOUTON);
        boutonhistorique.addActionListener(e -> {
            tableModel.setRowCount(0);
            try {
                List<Object[]> comptes = gestionCompte.listerComptes();
                for (Object[] compte:comptes){
                    tableModel.addRow(new Object[]{compte[0],compte[1], ""});
                }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement des comptes : "+ex.getMessage());

            }
        });

        buttonenregistrer= new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'></span> Nouveau compte</html>\\");
        buttonenregistrer.setFont(StyleUI.POLICE_BOUTON);
        buttonenregistrer.addActionListener(e -> {
            new RgistreUtilisateur().setVisible(true);
        });

        buttondeconnection=new JButton("\u21BB Retour");
        buttondeconnection.setFont(StyleUI.POLICE_BOUTON);
        buttondeconnection.addActionListener(e -> {
            new GroupWare().setVisible(true);
            dispose();
        });
        buttondeconnection.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonenregistrer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonhistorique.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 0;
        panelGauche.add(buttonenregistrer, gbc);

        gbc.gridy = 1;
        panelGauche.add(boutonhistorique, gbc);
        gbc.gridy = 2;
        panelGauche.add(buttondeconnection, gbc);
        add(panelGauche);
        panelGauche.setBackground(StyleUI.MARINE);
        add(panelGauche, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new String[]{"Nom d'utilisateur", "Role", "Actions"}, 0);
        JPanel panelCentre = new JPanel(new BorderLayout()) {
            private final Image logo = new ImageIcon(getClass().getResource("/images/logo global 2.0.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
                g2.drawImage(logo, 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };
        panelCentre.add(titre,BorderLayout.NORTH);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(2).setCellRenderer(new BoutonsRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new BoutonsEditor());
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        panelCentre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentre.add(scrollPane, BorderLayout.CENTER);

        add(panelCentre, BorderLayout.CENTER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        setLocationRelativeTo(null);
    }


    public static class BoutonsRenderer extends JLabel implements TableCellRenderer {
        private JButton boutonModifier;
        private JButton boutonSupprimer;

        public BoutonsRenderer(){
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            boutonModifier = new JButton("✏ Modifier");
            boutonSupprimer = new JButton("🗑 Supprimer");
            add(boutonModifier);
            add(boutonSupprimer);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class BoutonsEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton boutonModifier;
        private JButton boutonSupprimer;
        private int currentRow;

        public BoutonsEditor(){
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            boutonModifier = new JButton("✏ Modifier");
            boutonSupprimer = new JButton("🗑 Supprimer");

            boutonModifier.addActionListener(e -> {
                fireEditingStopped();
                String ancienNom = (String) table.getValueAt(currentRow, 0);
                String roleactuel = (String) table.getValueAt(currentRow, 1);
                if("administrateur".equals(roleactuel)){
                    JOptionPane.showMessageDialog(UserManagementFrame.this, "Vous ne pouvez pas modifier le compte d'un administrateur");
                    return;
                }
                JTextField champNom = new JTextField(ancienNom);
                String[] roles = {"utilisateur standard", "administrateur"};
                JComboBox<String> champRole = new JComboBox<>(roles);
                champRole.setSelectedItem(roleactuel);

                JPanel formulaire = new JPanel(new GridLayout(2, 2, 5, 5));
                formulaire.add(new JLabel("Nom d'utilisateur:"));
                formulaire.add(champNom);
                formulaire.add(new JLabel("Role :"));
                formulaire.add(champRole);

                int resultat = JOptionPane.showConfirmDialog(
                        UserManagementFrame.this,
                        formulaire,
                        "Modifier le compte",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (resultat == JOptionPane.OK_OPTION) {
                    String nouveauNom = champNom.getText();
                    String nouveauRole = (String) champRole.getSelectedItem();
                    try{
                        gestionCompte.modifierCompte(ancienNom, nouveauNom, nouveauRole);
                        tableModel.setValueAt(nouveauNom, currentRow, 0);
                        tableModel.setValueAt(nouveauRole, currentRow, 1);
                    }catch (SQLException ex){
                        JOptionPane.showMessageDialog(UserManagementFrame.this, "Erreur lors de la modification : "+ex.getMessage());
                    }
                }
            });

            boutonSupprimer.addActionListener(e -> {
                fireEditingStopped();
                String nom = (String) tableModel.getValueAt(currentRow, 0);
                String role = (String) tableModel.getValueAt(currentRow, 1);

                if("administrateur".equals(role)){
                    JOptionPane.showMessageDialog(UserManagementFrame.this, "Impossible de supprimer le compte d'un administrateur");
                    return;
                }

                int confirmation = JOptionPane.showConfirmDialog(
                        UserManagementFrame.this,
                        "Supprimer le compte de "+nom+"?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if(confirmation == JOptionPane.YES_OPTION){
                    try {
                        gestionCompte.supprimerCompte(nom);
                        tableModel.removeRow(currentRow);
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(UserManagementFrame.this,"Erreur lors de la suppression : "+ex.getMessage());
                    }
                }
            });

            panel.add(boutonModifier);
            panel.add(boutonSupprimer);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }
        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
    public static void main (String[] args){
        SwingUtilities.invokeLater(() -> new UserManagementFrame().setVisible(true));
    }
}
