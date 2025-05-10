import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RobotSwingApp extends JFrame {
    private RobotLivraison robot;
    private JPanel canvas;
    private JLabel statusLabel;
    private JProgressBar energyBar;

    public RobotSwingApp() {
        // Initialisation du robot
        robot = new RobotLivraison("R1", 0, 0);

        // Configuration de la fenêtre principale
        setTitle("Simulation de Robot de Livraison");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barre de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Barre d'outils
        JToolBar toolBar = new JToolBar();
        JButton startButton = new JButton("Démarrer");
        JButton loadButton = new JButton("Charger Colis");
        JButton deliverButton = new JButton("Livrer");
        JButton moveButton = new JButton("Déplacer");
        JButton stopButton = new JButton("Arrêter");
        toolBar.add(startButton);
        toolBar.add(loadButton);
        toolBar.add(deliverButton);
        toolBar.add(moveButton);
        toolBar.add(stopButton);
        add(toolBar, BorderLayout.NORTH);

        // Zone de dessin pour afficher le robot
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillOval(robot.x, robot.y, 20, 20); // Dessiner le robot
            }
        };
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(canvas, BorderLayout.CENTER);

        // Barre de statut et barre d'énergie
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Statut : Prêt");
        energyBar = new JProgressBar(0, 100);
        energyBar.setValue(robot.energie);
        energyBar.setStringPainted(true);
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(energyBar, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);

        // Actions des boutons
        startButton.addActionListener(e -> {
            try {
                robot.demarrer();
                updateStatus("Robot démarré !");
            } catch (RobotException ex) {
                updateStatus("Erreur : " + ex.getMessage());
            }
        });

        loadButton.addActionListener(e -> {
            try {
                String destination = JOptionPane.showInputDialog(this, "Entrez la destination :");
                if (destination != null && !destination.isEmpty()) {
                    robot.chargerColis(destination);
                    updateStatus("Colis chargé pour " + destination);
                }
            } catch (RobotException ex) {
                updateStatus("Erreur : " + ex.getMessage());
            }
        });

        deliverButton.addActionListener(e -> {
            try {
                robot.FaireLivraison(robot.x + 50, robot.y + 50); // Exemple de livraison
                canvas.repaint();
                updateStatus("Livraison effectuée !");
            } catch (RobotException ex) {
                updateStatus("Erreur : " + ex.getMessage());
            }
        });

        moveButton.addActionListener(e -> {
            try {
                String xInput = JOptionPane.showInputDialog(this, "Entrez la coordonnée X :");
                String yInput = JOptionPane.showInputDialog(this, "Entrez la coordonnée Y :");
                if (xInput != null && yInput != null) {
                    int x = Integer.parseInt(xInput);
                    int y = Integer.parseInt(yInput);
                    robot.deplacer(x, y);
                    canvas.repaint();
                    updateStatus("Déplacement vers (" + x + ", " + y + ")");
                }
            } catch (Exception ex) {
                updateStatus("Erreur : " + ex.getMessage());
            }
        });

        stopButton.addActionListener(e -> {
            updateStatus("Robot arrêté !");
        });
    }

    // Méthode pour mettre à jour le statut et la barre d'énergie
    private void updateStatus(String message) {
        statusLabel.setText("Statut : " + message);
        energyBar.setValue(robot.energie);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RobotSwingApp app = new RobotSwingApp();
            app.setVisible(true);
        });
    }
}