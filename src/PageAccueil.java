import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PageAccueil extends JFrame {

    private JTable table;
    private JPanel center;
    private JPanel topPanel;
    private JTextField champNom;
    private JTextField champDate;
    private JButton buttonRechercher;
    private JButton buttonExporter;

    private String currentUser;

    public PageAccueil(String user) {
        this.currentUser = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Page Accueil - Connecté : " + user);
        setSize(1400, 800); // fenêtre plus grande
        setLocationRelativeTo(null);

        JPanel principale = new JPanel(new BorderLayout());

        // --- Panneau supérieur ---
        topPanel = new JPanel(new BorderLayout());

        JLabel titre = new JLabel("Accueil", SwingConstants.CENTER);
        titre.setFont(new java.awt.Font("Cooper Black", java.awt.Font.BOLD, 48));
        titre.setForeground(new Color(66, 72, 90));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
        topPanel.add(titre, BorderLayout.CENTER);

        // Recherche (cachée au départ)
        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        champNom = new JTextField(10);
        champDate = new JTextField(10);
        buttonRechercher = new JButton("Rechercher");
        buttonRechercher.addActionListener(e -> rechercherParNomEtDate());
        recherchePanel.add(new JLabel("Nom:"));
        recherchePanel.add(champNom);
        recherchePanel.add(new JLabel("Date (AAAA-MM-JJ):"));
        recherchePanel.add(champDate);
        recherchePanel.add(buttonRechercher);
        recherchePanel.setVisible(false);
        topPanel.add(recherchePanel, BorderLayout.WEST);

        // Export (caché au départ)
        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonExporter = new JButton("Exporter");
        buttonExporter.addActionListener(e -> exporterPDF());
        exportPanel.add(buttonExporter);
        exportPanel.setVisible(false);
        topPanel.add(exportPanel, BorderLayout.EAST);

        principale.add(topPanel, BorderLayout.NORTH);

        // --- Barre latérale gauche ---
        JPanel panelGauche = new JPanel(new GridBagLayout());
        panelGauche.setPreferredSize(new Dimension(200, 0));
        panelGauche.setBackground(new Color(38, 124, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton buttonHistorique = new JButton("Historiques");
        buttonHistorique.addActionListener(e -> {
            recherchePanel.setVisible(true);
            exportPanel.setVisible(true);
            afficherHistorique();
        });

        JButton buttonEnregistrer = new JButton("Enregistrer");
        buttonEnregistrer.addActionListener(e -> new Main());
        JButton buttonDeconnexion = new JButton("Déconnexion");
        buttonDeconnexion.addActionListener(e -> dispose());

        gbc.gridy = 0; panelGauche.add(buttonHistorique, gbc);
        gbc.gridy = 1; panelGauche.add(buttonEnregistrer, gbc);
        gbc.gridy = 2; panelGauche.add(buttonDeconnexion, gbc);

        principale.add(panelGauche, BorderLayout.WEST);

        // --- Conteneur central ---
        center = new JPanel(new BorderLayout());
        principale.add(center, BorderLayout.CENTER);

        setContentPane(principale);
    }

    // Vérification utilisateur dans la table
    public static boolean verifierUtilisateur(String username, String password) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/visitors_db", "root", "")) {

            String sql = "SELECT * FROM utilisateur WHERE nom_d_utilisateur=? AND mot_de_passe=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si trouvé

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur connexion utilisateur : " + e.getMessage());
            return false;
        }
    }

    // Afficher l’historique
    public void afficherHistorique() {
        table = new JTable(new DefaultTableModel(
                new Object[]{"Nom", "Prénom", "Contact", "Num_CNI", "Motif",
                        "Date_visite", "Heure_arrivee", "Heure_depart",
                        "Service", "Visiteurs_id", "Utilisateur_id"}, 0
        ));

        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(220, 240, 220));
        header.setForeground(new Color(34, 85, 50));
        header.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        chargerVisiteur(table, null, null);

        center.removeAll();
        center.add(scrollPane, BorderLayout.CENTER);
        center.revalidate();
        center.repaint();
    }

    // Recherche par nom/date
    public void rechercherParNomEtDate() {
        String nom = champNom.getText().trim();
        String date = champDate.getText().trim();

        table = new JTable(new DefaultTableModel(
                new Object[]{"Nom", "Prénom", "Contact", "Num_CNI", "Motif",
                        "Date_visite", "Heure_arrivee", "Heure_depart",
                        "Service", "Visiteurs_id", "Utilisateur_id"}, 0
        ));

        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(table);

        chargerVisiteur(table, nom.isEmpty() ? null : nom, date.isEmpty() ? null : date);

        center.removeAll();
        center.add(scrollPane, BorderLayout.CENTER);
        center.revalidate();
        center.repaint();
    }

    // Charger visiteurs
    private void chargerVisiteur(JTable table, String nom, String date) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/visitors_db", "root", "")) {

            String sql = "SELECT v.nom, v.prenom, v.contact, v.num_CNI, " +
                    "vi.motif, vi.date_visite, vi.heure_de_depart, vi.heure_d_arrivee, " +
                    "vi.service, vi.Visiteurs_id_Visiteurs, vi.Utilisateur_id_Utilisateur " +
                    "FROM visiteurs v LEFT JOIN visites vi ON v.id_visiteurs = vi.Visiteurs_id_Visiteurs";

            if (nom != null && date != null) {
                sql += " WHERE v.nom LIKE ? AND vi.date_visite = ?";
            } else if (nom != null) {
                sql += " WHERE v.nom LIKE ?";
            } else if (date != null) {
                sql += " WHERE DATE(vi.date_visite) = ?";
            }


            PreparedStatement ps = conn.prepareStatement(sql);

            if (nom != null && date != null) {
                ps.setString(1, "%" + nom + "%");
                ps.setString(2, date);
            } else if (nom != null) {
                ps.setString(1, "%" + nom + "%");
            } else if (date != null) {
                ps.setDate(1, java.sql.Date.valueOf(date));

            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("contact"),
                        rs.getInt("num_CNI"),
                        rs.getString("motif"),
                        rs.getDate("date_visite"),
                        rs.getTime("heure_d_arrivee"),
                        rs.getTime("heure_de_depart"),
                        rs.getString("service"),
                        rs.getInt("Visiteurs_id_Visiteurs"),
                        rs.getInt("Utilisateur_id_Utilisateur"),
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    // Export PDF
    private void exporterPDF() {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream("export_visiteurs.pdf"));
            document.open();

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Ajouter les lignes
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    Object value = table.getValueAt(rows, cols);
                    PdfPCell cell = new PdfPCell(new Phrase(value == null ? "" : value.toString()));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidth(0.5f); // bordure fine
                    pdfTable.addCell(cell);
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(this, "Export PDF réussi : export_visiteurs.pdf");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur export : " + e.getMessage());
        }
    }

    // --- Point d'entrée principal ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/visitors_db", "root", "")) {

                // Vérifier si au moins un utilisateur existe
                String sql = "SELECT nom_d_utilisateur FROM utilisateur LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String user = rs.getString("nom_d_utilisateur");
                    new PageAccueil(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Aucun utilisateur trouvé dans la table utilisateur",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Erreur de connexion à la base : " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}


