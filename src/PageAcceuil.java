import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PageAcceuil extends JFrame {

    private DefaultTableModel tableModel;
    private JButton boutonhistorique ;
    private JButton buttonenregistrer;
    private JButton buttondeconnection;
    private JButton buttonexpoeter;
    private JTable table;
    private JLabel titre;


    private PageAcceuil(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setTitle("page accueil");
        setSize(900,500);
        //setLayout(new BorderLayout(10, 10));
        JPanel principale=new JPanel();
        //principale.setBackground(new Color(255, 255, 255, 255));

        JPanel paneltitre =new JPanel();
        titre= new JLabel("Accueil", SwingConstants.CENTER);
        titre.setFont(new Font("COOPER BLACK", Font.BOLD, 28));
        titre.setForeground(new Color(66, 72, 90, 244)); // Optionnel : couleur du texte


// 2. Ajouter une marge sous le titre (20px en bas)
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
// 3. L'ajouter en HAUT de votre panel principal
        principale.add(titre, BorderLayout.NORTH);

        JPanel panelGauche = new JPanel();
        panelGauche.setLayout(new GridBagLayout());
        panelGauche.setPreferredSize(new Dimension(200, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

         boutonhistorique = new JButton("<html><span style='color: #10b981; font-size: 10px; font-weight: bold;'></span>  Enregistrer</html>\\");
        buttonenregistrer= new JButton("Historiques");
        buttondeconnection=new JButton("Déconnecxion");
        buttonexpoeter=new JButton("Exporter");
        buttondeconnection.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonenregistrer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonhistorique.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonexpoeter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 0;
        panelGauche.add(buttonenregistrer, gbc);

        gbc.gridy = 1;
        panelGauche.add(boutonhistorique, gbc);
        gbc.gridy = 2;
        panelGauche.add(buttondeconnection, gbc);
        gbc.gridy=3;
        panelGauche.add(buttonexpoeter, gbc);
        add(panelGauche);
        panelGauche.setBackground(new Color(38, 124, 25, 255));
        add(panelGauche, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new String[]{"Nom d'utilisateur", "Role"}, 50);
        table = new JTable(tableModel);
        table.setFont(new Font("COOPER BLACK", Font.BOLD, 16));
        table.getTableHeader().setFont(new Font("COOPER BLACK", Font.BOLD, 14));
        //table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelCentre = new JPanel(new BorderLayout());
        panelCentre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentre.add(scrollPane, BorderLayout.CENTER);

        add(panelCentre, BorderLayout.CENTER);



        // 3. Ajout du panneau gauche à la fenêtre principale
        add(principale);


        //paneltitre.setBackground(new Color(202, 111, 111, 255));
        //paneltitre.setOpaque(true);
        principale.add(paneltitre);
        setLocationRelativeTo(null);
    }
    public static void main (String[] args){
        SwingUtilities.invokeLater(() -> new PageAcceuil().setVisible(true));
    }
}
