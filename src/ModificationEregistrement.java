import dao.VisiteDAO;
import dao.VisiteurDAO;
import model.Visite;
import model.Visiteur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ModificationEregistrement extends JFrame {

    private JTextField champ1;
    private JTextField champ2;
    private JTextField champ3;
    private JTextField champ4;
    private JTextField champ5;
    private JTextField champ6;
    private JTextArea champ7;
    private JTextField champ8;
    private JButton bouton1;
    private JButton bouton2;
    private JLabel titre;

    private final int idVisiteur;
    private final int idVisite;
    private final Runnable surModificationReussie;

    public ModificationEregistrement(int idVisiteur, int idVisite,
                                     String nom, String prenom, String numCni, int contact,
                                     String heureArrivee, String heureDepart,
                                     String motif, String service,
                                     Runnable surModificationReussie) {

        this.idVisiteur = idVisiteur;
        this.idVisite = idVisite;
        this.surModificationReussie = surModificationReussie;

        setSize(600, 700);
        setTitle("Modification Enregistrement");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.ORANGE);

        JPanel panelprincipale = new JPanel(new BorderLayout());
        panelprincipale.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelprincipale.setBackground(StyleUI.GRIS_CLAIR);

        titre = new JLabel(
                "<html><span style='color: #10b981; font-size: 10px; font-weight: bold;background-color:blue'></span>Modification</html>",
                SwingConstants.CENTER);
        titre.setFont(new java.awt.Font("COOPER BLACK", java.awt.Font.BOLD, 28));
        titre.setForeground(StyleUI.MARINE);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelprincipale.add(titre, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 2, 0, 15));
        panel.setBackground(StyleUI.GRIS_CLAIR);

        panel.add(new JLabel("<html>Nom <font color='red'>*</font> : </html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ1 = new JTextField(nom);
        panel.add(champ1);

        panel.add(new JLabel("<html>Prénom <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ2 = new JTextField(prenom);
        panel.add(champ2);

        panel.add(new JLabel("<html>N°CNI <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ3 = new JTextField(numCni);
        panel.add(champ3);

        panel.add(new JLabel("<html>N° Téléphone <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ4 = new JTextField(String.valueOf(contact));
        panel.add(champ4);

        panel.add(new JLabel("<html>Heure d'arrivée <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ5 = new JTextField(heureArrivee);
        panel.add(champ5);

        panel.add(new JLabel("<html>Heure de départ <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ6 = new JTextField(heureDepart);
        panel.add(champ6);

        panel.add(new JLabel("<html>Motif <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ7 = new JTextArea(motif);
        champ7.setLineWrap(true);
        champ7.setWrapStyleWord(true);
        panel.add(champ7);

        panel.add(new JLabel("<html>Service sollicité <font color='red'>*</font> :</html>", SwingConstants.LEFT) {{
            setFont(StyleUI.POLICE_LABEL);
        }});
        champ8 = new JTextField(service);
        panel.add(champ8);
        panel.add(new JLabel(""));

        panel.add(new JLabel(""));

        bouton1 = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>\u2713</span> Valider</html>");
        bouton1.setFont(StyleUI.POLICE_BOUTON);
        bouton1.setBackground(StyleUI.MARINE);
        bouton1.setForeground(Color.WHITE);
        bouton1.setOpaque(true);
        bouton1.setBorderPainted(false);
        bouton1.setFocusPainted(false);
        bouton1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bouton2 = new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span> Annuler</html>");
        bouton2.setFont(StyleUI.POLICE_BOUTON);
        bouton2.setBackground(Color.WHITE);
        bouton2.setForeground(StyleUI.MARINE);
        bouton2.setBorder(BorderFactory.createLineBorder(StyleUI.GRIS_BORDURE, 1));
        bouton2.setFocusPainted(false);
        bouton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton1.addActionListener(e -> {
            try {
                String nouveauNom = champ1.getText();
                String nouveauPrenom = champ2.getText();
                String nouveauNumCni = champ3.getText();
                int nouveauContact = Integer.parseInt(champ4.getText().trim());

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
                LocalDate today = LocalDate.now();
                LocalDateTime nouvelleHeureArrivee = LocalTime.parse(champ5.getText().trim(), fmt).atDate(today);
                LocalDateTime nouvelleHeureDepart = LocalTime.parse(champ6.getText().trim(), fmt).atDate(today);
                String nouveauMotif = champ7.getText();
                String nouveauService = champ8.getText();

                Visiteur visiteur = new Visiteur(nouveauNom, nouveauPrenom, nouveauContact, nouveauNumCni);
                visiteur.setIdVisiteur(idVisiteur);
                VisiteurDAO visiteurDAO = new VisiteurDAO();
                boolean visiteurOk = visiteurDAO.modifierVisiteur(visiteur);

                Visite visite = new Visite(nouveauMotif, nouvelleHeureArrivee, nouvelleHeureDepart,
                        nouveauService, "", idVisiteur, 1);
                visite.setIdVisite(idVisite);
                VisiteDAO visiteDAO = new VisiteDAO();
                boolean visiteOk = visiteDAO.modifierVisite(visite);

                if (visiteurOk && visiteOk) {
                    JOptionPane.showMessageDialog(this, "Modification enregistrée avec succès !");
                    if (surModificationReussie != null) {
                        surModificationReussie.run();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit être un nombre.",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(this, "Heure invalide. Utilisez le format HH:mm (ex: 14:30).",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        });

        bouton2.addActionListener(e -> dispose());

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBoutons.add(bouton2, SwingConstants.CENTER);
        panelBoutons.add(bouton1, SwingConstants.CENTER);
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelBoutons.setBackground(StyleUI.GRIS_CLAIR);
        panel.add(panelBoutons);

        panelprincipale.add(panel);
        add(panelprincipale);
        setVisible(true);

        getContentPane().setBackground(Color.ORANGE);
    }
}