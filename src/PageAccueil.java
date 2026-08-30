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
        setTitle("Page Accueil - Connecté : " + user );
        setSize(1400, 800);
        setLocationRelativeTo(null);

        JPanel principale = new JPanel(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());

        /*JLabel titre = new JLabel("Accueil", SwingConstants.CENTER);

        titre.setFont(new java.awt.Font("Cooper Black", java.awt.Font.BOLD, 48));
        titre.setForeground(StyleUI.MARINE);
        titre.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.add(titre, BorderLayout.CENTER);*/

        int largeurImage = 90;
        int hauteurImage = 90;

        ImageIcon iconOriginal = new ImageIcon(
                getClass().getResource("/images/logo global 2.0.jpg"));
        java.awt.Image imageRedim = iconOriginal.getImage()
                .getScaledInstance(largeurImage, hauteurImage,
                        java.awt.Image.SCALE_SMOOTH);
        ImageIcon iconRedim = new ImageIcon(imageRedim);
        JLabel labelImage = new JLabel(iconRedim);

        JLabel titre = new JLabel(" GLOBAL SERVICES S.A");
        titre.setFont(new java.awt.Font("Cooper Black", java.awt.Font.BOLD, 48));
        titre.setForeground(StyleUI.MARINE);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        titrePanel.setOpaque(false);
        titrePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titrePanel.add(labelImage);
        titrePanel.add(titre);

        topPanel.add(titrePanel, BorderLayout.CENTER);

        recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        champNom = new JTextField(10);
        champDate = new JTextField(10);
        buttonRechercher = new JButton("Rechercher");
        buttonRechercher.setFont(StyleUI.POLICE_BOUTON);
        buttonRechercher.setBackground(Color.WHITE);
        buttonRechercher.setForeground(StyleUI.MARINE);
        buttonRechercher.setBorder(BorderFactory.createLineBorder(StyleUI.GRIS_BORDURE, 1));
        buttonRechercher.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonRechercher.addActionListener(e -> rechercherParNomEtDate());
        JLabel labelNom = new JLabel("Nom:");
        labelNom.setFont(StyleUI.POLICE_LABEL);
        recherchePanel.add(labelNom);
        recherchePanel.add(champNom);
        JLabel labelDate = new JLabel("Date (JJ-MM-AAAA):");
        labelDate.setFont(StyleUI.POLICE_LABEL);
        recherchePanel.add(labelDate);
        recherchePanel.add(champDate);
        recherchePanel.add(buttonRechercher);
        recherchePanel.setVisible(false);
        topPanel.add(recherchePanel, BorderLayout.WEST);

        exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonExporter = new JButton("Exporter");
        buttonExporter.setFont(StyleUI.POLICE_BOUTON);
        buttonExporter.setBackground(Color.WHITE);
        buttonExporter.setForeground(StyleUI.MARINE);
        buttonExporter.setBorder(BorderFactory.createLineBorder(StyleUI.GRIS_BORDURE, 1));
        buttonExporter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonExporter.addActionListener(e -> exporterPDF());

        JButton buttonModifier = new JButton("Modifier");
        buttonModifier.setFont(StyleUI.POLICE_BOUTON);
        buttonModifier.setBackground(Color.WHITE);
        buttonModifier.setForeground(StyleUI.MARINE);
        buttonModifier.setBorder(BorderFactory.createLineBorder(StyleUI.GRIS_BORDURE, 1));
        buttonModifier.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonModifier.addActionListener(e -> modifierLigneSelectionnee());

        exportPanel.add(buttonModifier);
        exportPanel.add(buttonExporter);
        exportPanel.setVisible(false);
        topPanel.add(exportPanel, BorderLayout.EAST);

        principale.add(topPanel, BorderLayout.NORTH);

        JPanel panelGauche = new JPanel(new GridBagLayout());
        panelGauche.setPreferredSize(new Dimension(200, 0));
        panelGauche.setBackground(StyleUI.MARINE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton buttonHistorique = new JButton("Consulter l'historique");
        buttonHistorique.setFont(StyleUI.POLICE_BOUTON);
        buttonHistorique.setForeground(Color.WHITE);
        buttonHistorique.setBackground(StyleUI.MARINE);
        buttonHistorique.addActionListener(e -> {
            recherchePanel.setVisible(true);
            exportPanel.setVisible(true);
            afficherHistorique();
        });

        JButton buttonEnregistrer = new JButton("+ Nouvelle Visite");
        buttonEnregistrer.setFont(StyleUI.POLICE_BOUTON);
        buttonEnregistrer.setForeground(Color.WHITE);
        buttonEnregistrer.setBackground(StyleUI.MARINE);
        buttonEnregistrer.addActionListener(e -> {
            recherchePanel.setVisible(false);
            exportPanel.setVisible(false);
            new Main();
        });

        JButton buttonDeconnexion = new JButton("Déconnexion");
        buttonDeconnexion.setFont(StyleUI.POLICE_BOUTON);
        buttonDeconnexion.setForeground(Color.WHITE);
        buttonDeconnexion.setBackground(StyleUI.MARINE);
        buttonDeconnexion.addActionListener(e -> {
            new Connexion().setVisible(true);
            dispose();
        });

        gbc.gridy = 0; panelGauche.add(buttonHistorique, gbc);
        gbc.gridy = 1; panelGauche.add(buttonEnregistrer, gbc);
        gbc.gridy = 2; panelGauche.add(buttonDeconnexion, gbc);

        principale.add(panelGauche, BorderLayout.WEST);

        center = new JPanel(new BorderLayout()) {
            private final java.awt.Image logo = new ImageIcon(getClass().getResource("/images/logo global 2.0.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
                g2.drawImage(logo, 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };
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
                new Object[]{"id_Visites", "Nom", "Prénom", "Contact", "Num CNI", "Motif", "Date de visite",
                        "Heure d'arrivee", "Heure de depart",
                        "Service","visiteur_id" , "utilisateur_id"}, 0
        ));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        appliquerStyleTableau(table); // ← AJOUTE ICI


        /*table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(220, 240, 220));
        header.setForeground(new Color(34, 85, 50));
        header.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));*/



        JScrollPane scrollPane = new JScrollPane(table);

        chargerVisiteur(table, null, null);
        ajusterLargeursColonnes(table);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(11));
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(10));
        //table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
        center.removeAll();
        center.add(scrollPane, BorderLayout.CENTER);
        center.revalidate();
        center.repaint();
    }

    private void ajusterLargeursColonnes(JTable table) {
        // Boucle sur chaque colonne du tableau
        for (int col = 0; col < table.getColumnCount(); col++) {

            // Variable qui stocke la largeur maximale trouvée
            int largeurMax = 0;

            // Récupère l'objet colonne (contient titre, largeur...)
            javax.swing.table.TableColumn column =
                    table.getColumnModel().getColumn(col);

            // Mesure la largeur du TITRE de la colonne (header)
            Component headerComp = table.getTableHeader()
                    .getDefaultRenderer()
                    .getTableCellRendererComponent(
                            table,
                            column.getHeaderValue(),
                            false, false, 0, col);
            largeurMax = Math.max(largeurMax,
                    headerComp.getPreferredSize().width);

            // Mesure la largeur de chaque CELLULE de cette colonne
            for (int row = 0; row < table.getRowCount(); row++) {
                Component cellComp = table.prepareRenderer(
                        table.getCellRenderer(row, col), row, col);
                largeurMax = Math.max(largeurMax,
                        cellComp.getPreferredSize().width);
            }

            // Applique la largeur max + 15px de marge
            column.setPreferredWidth(largeurMax + 15);
        }
    }

    public void rechercherParNomEtDate() {
        String nom = champNom.getText().trim();
        String date = champDate.getText().trim();

        table = new JTable(new DefaultTableModel(
                new Object[]{"id_Visites", "Nom", "Prénom", "Contact", "Num CNI", "Motif", "Date de visite",
                        "Heure d'arrivee", "Heure de depart",
                        "Service","visiteur_id" , "utilisateur_id"}, 0

        )
        );
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);



        //table.setShowGrid(true);
        //table.setGridColor(Color.GRAY);


        JScrollPane scrollPane = new JScrollPane(table);
        appliquerStyleTableau(table); // ← AJOUTE ICI


        chargerVisiteur(table, nom.isEmpty() ? null : nom, date.isEmpty() ? null : date);
        // 2. Ajuster ensuite
        ajusterLargeursColonnes(table);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(11));
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(10));
        //table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
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
                "FROM visiteurs v INNER JOIN visites vi ON v.id_visiteurs = vi.Visiteurs_id_Visiteurs";

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
                            rs.getObject("Visiteurs_id_Visiteurs"),    // ✅ AJOUTÉ
                            rs.getObject("Utilisateur_id_Utilisateur") // ✅ AJOUTÉ

                    });
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }
    // ... fin de chargerVisiteur() ...

    private void appliquerStyleTableau(JTable table) {


        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(180, 190, 220));

        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);        // ← interdit la sélection de cellule
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// ← une seule ligne à la fois
        table.setFocusable(false); // ← supprime la bordure de focus sur la cellule



        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(22, 73, 227));
        header.setForeground(Color.WHITE);
        header.setFont(new java.awt.Font("BOOK ANTIQUA", java.awt.Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 40));

        table.setSelectionBackground(new Color(178, 239, 172));
        table.setSelectionForeground(Color.BLACK);
        table.setDefaultEditor(Object.class, null);

        table.setDefaultRenderer(Object.class,
                new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable t, Object value, boolean isSelected,
                            boolean hasFocus, int row, int col) {

                        Component c = super.getTableCellRendererComponent(
                                t, value, isSelected, hasFocus, row, col);

                        if (!isSelected) {
                            if (row % 2 == 0) {
                                c.setBackground(Color.WHITE);
                            } else {
                                c.setBackground(new Color(214, 220, 240));
                            }
                        }

                        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                        return c;
                    }
                }
        );
    }

// ... suite de ta classe ...

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
       /* try {
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
        }*/

        try {
            // ✅ 1 — Nom du fichier avec date et heure pour être unique
            String dateHeure = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new java.util.Date());
            String nomFichier = "export_visiteurs_" + dateHeure + ".pdf";

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document,
                    new FileOutputStream(nomFichier));
            document.open();

            // ✅ 2 — Titre avant le tableau
            com.itextpdf.text.Font fontTitre = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA,
                    18,
                    com.itextpdf.text.Font.BOLD,
                    new BaseColor(22, 73, 227) // bleu comme ton header
            );
            Paragraph titre = new Paragraph(
                    "Liste des Visiteurs — " +
                            new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()),
                    fontTitre
            );
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(20); // espace entre le titre et le tableau
            document.add(titre);

            // ✅ 3 — Tableau des données
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // ✅ 4 — Header du tableau en bleu
            for (int col = 0; col < table.getColumnCount(); col++) {
                PdfPCell cellHeader = new PdfPCell(
                        new Phrase(table.getColumnName(col),
                                new com.itextpdf.text.Font(
                                        com.itextpdf.text.Font.FontFamily.HELVETICA,
                                        11,
                                        com.itextpdf.text.Font.BOLD,
                                        BaseColor.WHITE
                                )
                        )
                );
                cellHeader.setBackgroundColor(new BaseColor(22, 73, 227));
                cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellHeader.setPadding(8);
                pdfTable.addCell(cellHeader);
            }

            // ✅ 5 — Lignes avec alternance de couleur
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    Object value = table.getValueAt(rows, cols);
                    PdfPCell cell = new PdfPCell(
                            new Phrase(value == null ? "" : value.toString()));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(6);
                    cell.setBorderWidth(0.5f);

                    // Alternance blanc / bleu clair
                    if (rows % 2 != 0) {
                        cell.setBackgroundColor(new BaseColor(214, 220, 240));
                    }
                    pdfTable.addCell(cell);
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(this,
                    "Export PDF réussi : " + nomFichier);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur export : " + e.getMessage());
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