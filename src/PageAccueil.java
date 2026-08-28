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
    private JPanel recherchePanel;
    private JPanel exportPanel;

    private String currentUser;

    public PageAccueil(String user) {
        this.currentUser = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Page Accueil - Connecté : " + user);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        JPanel principale = new JPanel(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());

        JLabel titre = new JLabel("Accueil", SwingConstants.CENTER);
        titre.setFont(new java.awt.Font("Cooper Black", java.awt.Font.BOLD, 48));
        titre.setForeground(new Color(66, 72, 90));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
        topPanel.add(titre, BorderLayout.CENTER);

        recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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

        exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonExporter = new JButton("Exporter");
        buttonExporter.addActionListener(e -> exporterPDF());

        JButton buttonModifier = new JButton("Modifier");
        buttonModifier.addActionListener(e -> modifierLigneSelectionnee());

        exportPanel.add(buttonModifier);
        exportPanel.add(buttonExporter);
        exportPanel.setVisible(false);
        topPanel.add(exportPanel, BorderLayout.EAST);

        principale.add(topPanel, BorderLayout.NORTH);

        JPanel panelGauche = new JPanel(new GridBagLayout());
        panelGauche.setPreferredSize(new Dimension(200, 0));
        panelGauche.setBackground(new Color(38, 124, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton buttonHistorique = new JButton("Historique");
        buttonHistorique.addActionListener(e -> {
            recherchePanel.setVisible(true);
            exportPanel.setVisible(true);
            afficherHistorique();
        });

        JButton buttonEnregistrer = new JButton("Nouvel Visite");
        buttonEnregistrer.addActionListener(e -> {
            recherchePanel.setVisible(false);
            exportPanel.setVisible(false);
            new Main();
        });

        JButton buttonDeconnexion = new JButton("Déconnexion");
        buttonDeconnexion.addActionListener(e -> {
            new Connexion().setVisible(true);
            dispose();
        });

        gbc.gridy = 0; panelGauche.add(buttonHistorique, gbc);
        gbc.gridy = 1; panelGauche.add(buttonEnregistrer, gbc);
        gbc.gridy = 2; panelGauche.add(buttonDeconnexion, gbc);

        principale.add(panelGauche, BorderLayout.WEST);

        center = new JPanel(new BorderLayout());
        principale.add(center, BorderLayout.CENTER);

        setContentPane(principale);
    }

    public static boolean verifierUtilisateur(String username, String password) {
        String sql = "SELECT * FROM utilisateur WHERE nom_d_utilisateur=? AND mot_de_passe=?";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/visitors_db", "root", "");
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur connexion utilisateur : " + e.getMessage());
            return false;
        }
    }

    public void afficherHistorique() {
        table = new JTable(new DefaultTableModel(
                new Object[]{"id_Visites", "Nom", "Prénom", "Contact", "Num_CNI", "Motif", "Date_visite",
                        "Heure_arrivee", "Heure_depart",
                        "Service", "Visiteurs_id", "Utilisateur_id"}, 0
        ));

        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(220, 240, 220));
        header.setForeground(new Color(34, 85, 50));
        header.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = new JScrollPane(table);

        chargerVisiteur(table, null, null);

        center.removeAll();
        center.add(scrollPane, BorderLayout.CENTER);
        center.revalidate();
        center.repaint();
    }

    public void rechercherParNomEtDate() {
        String nom = champNom.getText().trim();
        String date = champDate.getText().trim();

        table = new JTable(new DefaultTableModel(
                new Object[]{"id_Visites", "Nom", "Prénom", "Contact", "Num_CNI", "Motif", "Date_visite",
                        "Heure_arrivee", "Heure_depart",
                        "Service", "Visiteurs_id", "Utilisateur_id"}, 0
        ));

        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = new JScrollPane(table);

        chargerVisiteur(table, nom.isEmpty() ? null : nom, date.isEmpty() ? null : date);

        center.removeAll();
        center.add(scrollPane, BorderLayout.CENTER);
        center.revalidate();
        center.repaint();
    }

    private void chargerVisiteur(JTable table, String nom, String date) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String sql = "SELECT vi.id_Visites, v.nom, v.prenom, v.contact, v.num_CNI, " +
                "vi.motif, vi.date_visite, vi.heure_de_depart, vi.heure_d_arrivee, " +
                "vi.service, vi.Visiteurs_id_Visiteurs, vi.Utilisateur_id_Utilisateur " +
                "FROM visiteurs v LEFT JOIN visites vi ON v.id_visiteurs = vi.Visiteurs_id_Visiteurs";

        if (nom != null && date != null) {
            sql += " WHERE v.nom LIKE ? AND DATE(vi.date_visite) = ?";
        } else if (nom != null) {
            sql += " WHERE v.nom LIKE ?";
        } else if (date != null) {
            sql += " WHERE DATE(vi.date_visite) = ?";
        }

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/visitors_db", "root", "");
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            if (nom != null && date != null) {
                ps.setString(1, "%" + nom + "%");
                ps.setString(2, date);
            } else if (nom != null) {
                ps.setString(1, "%" + nom + "%");
            } else if (date != null) {
                ps.setDate(1, java.sql.Date.valueOf(date));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id_Visites"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getInt("contact"),
                            rs.getString("num_CNI"),
                            rs.getString("motif"),
                            rs.getDate("date_visite"),
                            rs.getTime("heure_d_arrivee"),
                            rs.getTime("heure_de_depart"),
                            rs.getString("service"),
                            rs.getInt("Visiteurs_id_Visiteurs"),
                            rs.getInt("Utilisateur_id_Utilisateur"),
                    });
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    private void modifierLigneSelectionnee() {
        if (table == null || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.");
            return;
        }

        int rowVue = table.getSelectedRow();
        int rowModele = table.convertRowIndexToModel(rowVue);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        Object idVisitesObj = model.getValueAt(rowModele, 0);
        if (idVisitesObj == null) {
            JOptionPane.showMessageDialog(this,
                    "Ce visiteur n'a aucune visite enregistrée à modifier.");
            return;
        }
        int idVisites = (int) idVisitesObj;

        String nom = (String) model.getValueAt(rowModele, 1);
        String prenom = (String) model.getValueAt(rowModele, 2);
        int contact = (int) model.getValueAt(rowModele, 3);
        String numCni = (String) model.getValueAt(rowModele, 4);
        String motif = (String) model.getValueAt(rowModele, 5);

        java.sql.Time heureArriveeSql = (java.sql.Time) model.getValueAt(rowModele, 7);
        java.sql.Time heureDepartSql = (java.sql.Time) model.getValueAt(rowModele, 8);

        if (heureArriveeSql == null || heureDepartSql == null) {
            JOptionPane.showMessageDialog(this,
                    "Les heures d'arrivée/départ sont manquantes pour cette visite.");
            return;
        }

        String heureArrivee = heureArriveeSql.toLocalTime().toString().substring(0, 5);
        String heureDepart = heureDepartSql.toLocalTime().toString().substring(0, 5);

        String service = (String) model.getValueAt(rowModele, 9);
        int idVisiteurs = (int) model.getValueAt(rowModele, 10);

        new ModificationEregistrement(idVisiteurs, idVisites, nom, prenom, numCni, contact,
                heureArrivee, heureDepart, motif, service,
                this::afficherHistorique);
    }

    private void exporterPDF() {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream("export_visiteurs.pdf"));
            document.open();

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    Object value = table.getValueAt(rows, cols);
                    PdfPCell cell = new PdfPCell(new Phrase(value == null ? "" : value.toString()));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidth(0.5f);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String sql = "SELECT nom_d_utilisateur FROM utilisateur LIMIT 1";

            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/visitors_db", "root", "");
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()
            ) {
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