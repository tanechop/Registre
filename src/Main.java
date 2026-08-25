import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Visiteur;
import model.Visite;
import dao.VisiteurDAO;
import dao.VisiteDAO;

public class Main extends JFrame {

    private JTextField champ1;
    private JTextField champ2;
    private JTextField champ3;
    private JTextField champ4;
    private JTextField champ5;
    private JTextField champ6;
    private JTextArea champ7;
    private JButton bouton1;
    private JButton bouton2;
    private JButton bouton3;
    private JLabel titre;

    public Main() {

        setSize(800, 720);
        setTitle("Enregistrement");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.ORANGE);

        JPanel panelprincipale = new JPanel(new BorderLayout());
        panelprincipale.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
        panelprincipale.setBackground(new Color(121, 142, 246));

        titre = new JLabel(
                "<html><span style='color: #10b981; font-size: 10px; font-weight: bold;background-color:blue'></span>PAGE D'ENREGISTREMENT</html>",
                SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
        titre.setForeground(new Color(122, 255, 101));
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelprincipale.add(titre, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(8, 2, 0, 50));
        panel.setBackground(new Color(121, 142, 246));

        panel.add(new JLabel("<html>Nom <font color='red'>*</font> : </html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ1 = new JTextField();
        panel.add(champ1);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>Prénom <font color='red'>*</font> :</html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ2 = new JTextField();
        panel.add(champ2);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>N°CNI <font color='red'>*</font> :</html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ3 = new JTextField();
        panel.add(champ3);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>N° Téléphone <font color='red'>*</font> :</html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ4 = new JTextField();
        panel.add(champ4);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>Heure d'arriver <font color='red'>*</font> :</html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ5 = new JTextField();
        panel.add(champ5);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>Heure de départ <font color='red'>*</font> :</html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ6 = new JTextField();
        panel.add(champ6);
        panel.add(new JLabel(""));

        panel.add(new JLabel("<html>Motif <font color='red'>*</font> :</html>", SwingConstants.CENTER) {{
            setFont(new Font("BOOK Antiqua", Font.BOLD, 16));
        }});
        champ7 = new JTextArea();
        champ7.setLineWrap(true);
        champ7.setWrapStyleWord(true);
        JScrollPane scrollPane1 = new JScrollPane(champ7);
       //champ7.setPreferredSize(new Dimension(100, 50));
        panel.add(champ7);
        panel.add(new JLabel(""));

        panel.add(new JLabel(""));

        bouton1 = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'>\u2713</span> ok</html>");
        bouton2 = new JButton("<html><span style='color: #dc2626; font-size: 10px;'>\u2716</span>Annuler</html>");
        bouton3 = new JButton("<html><span style='color: #4b5563; font-size: 10px;'>\u21BB</span>Retour</html>");

        bouton1.addActionListener(e -> {
            try {
                String nom = champ1.getText();
                String prenom = champ2.getText();
                String numCni = champ3.getText();
                int contact = Integer.parseInt(champ4.getText().trim());

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
                LocalDate today = LocalDate.now();
                LocalDateTime heureArrivee = LocalTime.parse(champ5.getText().trim(), fmt).atDate(today);
                LocalDateTime heureDepart = LocalTime.parse(champ6.getText().trim(), fmt).atDate(today);
                String motif = champ7.getText();

                Visiteur visiteur = new Visiteur(nom, prenom, contact, numCni);
                VisiteurDAO visiteurDAO = new VisiteurDAO();
                int idVisiteur = visiteurDAO.enregistrerVisiteur(visiteur);

                if (idVisiteur == -1) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du visiteur.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int utilisateurIdPlaceholder = 1; // TODO: remplacer par l'id de l'utilisateur connect+�

                Visite visite = new Visite(motif, heureArrivee, heureDepart,
                        "", "", idVisiteur, utilisateurIdPlaceholder);

                VisiteDAO visiteDAO = new VisiteDAO();
                boolean success = visiteDAO.enregistrerVisite(visite);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Visiteur enregistr+� avec succ+�s !");
                    champ1.setText("");
                    champ2.setText("");
                    champ3.setText("");
                    champ4.setText("");
                    champ5.setText("");
                    champ6.setText("");
                    champ7.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la visite.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Le num+�ro de t+�l+�phone doit +�tre un nombre.",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(this, "Heure invalide. Utilisez le format HH:mm (ex: 14:30).",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        });

        bouton2.addActionListener(e -> {
            champ1.setText("");
            champ2.setText("");
            champ3.setText("");
            champ4.setText("");
            champ5.setText("");
            champ6.setText("");
            champ7.setText("");
        });

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBoutons.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBoutons.add(bouton3);
        panelBoutons.add(bouton2);
        panelBoutons.add(bouton1);
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelBoutons.setBackground(new Color(122, 150, 236));
        panel.add(panelBoutons);

        panelprincipale.add(panel);
        add(panelprincipale);
        setVisible(true);

        getContentPane().setBackground(Color.ORANGE);
    }

    public static void main(String[] args) {
        new Main();
    }
}
