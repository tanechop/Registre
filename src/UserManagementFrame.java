
    import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

    public class UserManagementFrame extends JFrame {
        private JTextField txtNom, txtEmail;
        private DefaultTableModel tableModel;
        private JTable table;

        public UserManagementFrame() {
            setTitle("Gestion des utilisateurs");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Formulaire
            JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
            panelForm.add(new JLabel("Nom :"));
            txtNom = new JTextField();
            panelForm.add(txtNom);

            panelForm.add(new JLabel("Email :"));
            txtEmail = new JTextField();
            panelForm.add(txtEmail);

            JButton btnAjouter = new JButton("Ajouter");
            panelForm.add(btnAjouter);

            // Tableau
            tableModel = new DefaultTableModel(new String[]{"Nom", "Email"}, 0);
            table = new JTable(tableModel);

            // Action du bouton
            btnAjouter.addActionListener(e -> {
                if (!txtNom.getText().isEmpty() && !txtEmail.getText().isEmpty()) {
                    tableModel.addRow(new Object[]{txtNom.getText(), txtEmail.getText()});
                    txtNom.setText("");
                    txtEmail.setText("");
                }
            });

            // Organisation globale
            setLayout(new BorderLayout(10, 10));
            add(panelForm, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new UserManagementFrame().setVisible(true));
        }
    }


