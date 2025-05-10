import javax.swing.*;
import java.awt.*;

public class RobotSwingApp extends JFrame {
    private RobotLivraison robot;
    private JPanel canvas;
    private JLabel statusLabel;

    public RobotSwingApp() {
        
        robot = new RobotLivraison("R1", 0, 0);

    
        setTitle("Simulation de Robot");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillOval(robot.x, robot.y, 20, 20); 
            }
        };
        canvas.setBackground(Color.WHITE);
        add(canvas, BorderLayout.CENTER);

        
        JPanel controlPanel = new JPanel();
        JButton moveButton = new JButton("Déplacer");
        JButton loadButton = new JButton("Charger Colis");
        JButton deliverButton = new JButton("Livrer");
        controlPanel.add(moveButton);
        controlPanel.add(loadButton);
        controlPanel.add(deliverButton);
        add(controlPanel, BorderLayout.SOUTH);

       
        statusLabel = new JLabel("Statut : Prêt | Énergie : " + robot.energie + "%");
        add(statusLabel, BorderLayout.NORTH);

        
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
                robot.FaireLivraison(robot.x + 50, robot.y + 50); 
                canvas.repaint();
                updateStatus("Livraison effectuée !");
            } catch (RobotException ex) {
                updateStatus("Erreur : " + ex.getMessage());
            }
        });
    }

    
    private void updateStatus(String message) {
        statusLabel.setText("Statut : " + message + " | Énergie : " + robot.energie + "%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RobotSwingApp app = new RobotSwingApp();
            app.setVisible(true);
        });
    }
}