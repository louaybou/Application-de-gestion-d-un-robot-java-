import javax.swing.*;
import java.awt.*;

public class RobotSwingApp extends JFrame {
    private RobotLivraison robot;
    private JPanel canvas;
    private JLabel statusLabel;
    private JProgressBar energyBar;
    private JLabel solarBatteryLabel;
    private int nbRechargesSolaires = 0;

    public RobotSwingApp() {
        robot = new RobotLivraison("R1", 0, 0);
        
        setTitle("Robot Delivery Manager");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(200, 30, 30));
                g2d.fillOval(robot.x, robot.y, 40, 40);
                
                g2d.setColor(Color.BLACK);
                g2d.drawString("R1", robot.x + 15, robot.y + 25);
            }
        };
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(BorderFactory.createLineBorder(new Color(200, 30, 30), 2)); // rouge
        add(canvas, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        controlPanel.setBackground(new Color(230, 240, 250));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel powerPanel = createButtonPanel("Alimentation", 
            createStyledButton("Démarrer", this::startAction),
            createStyledButton("Arrêter", this::stopAction)
        );

        JPanel deliveryPanel = createButtonPanel("Livraison",
            createStyledButton("Charger Colis", this::loadAction),
            createStyledButton("Livrer", this::deliverAction)
        );

        JPanel actionPanel = createButtonPanel("Actions",
            createStyledButton("Déplacer", this::moveAction),
            createStyledButton("Recharger", this::rechargeAction),
            createStyledButton("Stats Éco", this::ecoAction),
            createStyledButton("Solaire", this::solarAction)
        );

        controlPanel.add(powerPanel);
        controlPanel.add(deliveryPanel);
        controlPanel.add(actionPanel);
        add(controlPanel, BorderLayout.SOUTH);

        JPanel statusPanel = new JPanel(new BorderLayout(10, 10));
        statusPanel.setBackground(new Color(240, 240, 240));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        statusLabel = new JLabel("Statut: Prêt");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        energyBar = new JProgressBar(0, 100);
        energyBar.setValue(robot.energie);
        energyBar.setStringPainted(true);
        energyBar.setForeground(new Color(50, 205, 50));
        energyBar.setPreferredSize(new Dimension(200, 25));

        solarBatteryLabel = new JLabel("Solaire: " + robot.getBatterieSolaire() + "%");
        solarBatteryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        solarBatteryLabel.setForeground(new Color(255, 140, 0));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.add(solarBatteryLabel);
        rightPanel.add(energyBar);
        statusPanel.add(rightPanel, BorderLayout.EAST);

        add(statusPanel, BorderLayout.NORTH);
    }

    private JPanel createButtonPanel(String title, JButton... buttons) {
        JPanel panel = new JPanel(new GridLayout(1, buttons.length, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private JButton createStyledButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(null);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void startAction() {
        try {
            robot.demarrer();
            updateStatus("Robot démarré !");
        } catch (RobotException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stopAction() {
        robot.arreter();
        updateStatus("Robot arrêté");
    }

    private void moveAction() {
        try {
            String xInput = JOptionPane.showInputDialog(this, "Coordonnée X:");
            String yInput = JOptionPane.showInputDialog(this, "Coordonnée Y:");
            if (xInput != null && yInput != null) {
                int x = Integer.parseInt(xInput);
                int y = Integer.parseInt(yInput);
                robot.deplacer(x, y);
                canvas.repaint();
                updateStatus("Déplacement vers (" + x + ", " + y + ")");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAction() {
        try {
            String destination = JOptionPane.showInputDialog(this, "Destination:");
            if (destination != null && !destination.isEmpty()) {
                robot.chargerColis(destination);
                updateStatus("Colis chargé pour " + destination );
            }
        } catch (RobotException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Étage invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deliverAction() {
        try {
            String xInput = JOptionPane.showInputDialog(this, "Destination X:");
            String yInput = JOptionPane.showInputDialog(this, "Destination Y:");
            if (xInput != null && yInput != null) {
                int x = Integer.parseInt(xInput);
                int y = Integer.parseInt(yInput);
                robot.FaireLivraison(x, y);
                canvas.repaint();
                updateStatus("Livraison effectuée! CO2: " + String.format("%.2f", robot.getEmpreinteCarbone()) + "g");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechargeAction() {
        try {
            String input = JOptionPane.showInputDialog(this, "Quantité à recharger:");
            if (input != null && !input.isEmpty()) {
                int quantite = Integer.parseInt(input);
                robot.recharger(quantite);
                updateStatus("Énergie rechargée de " + quantite + "%");
                canvas.repaint();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ecoAction() {
        String stats = String.format(
            "Énergie consommée: %d unités\nEmpreinte carbone: %.2f g CO2\nRecharges solaires: %d",
            robot.getEnergieConsommee(), robot.getEmpreinteCarbone(), nbRechargesSolaires
        );
        JOptionPane.showMessageDialog(this, stats, "Statistiques Écologiques", JOptionPane.INFORMATION_MESSAGE);
    }

    private void solarAction() {
        if (robot.energie < 10) {
            if (robot.getBatterieSolaire() > 0) {
                int aRecharger = Math.min(100 - robot.energie, robot.getBatterieSolaire());
                robot.energie += aRecharger;
                robot.setBatterieSolaire(robot.getBatterieSolaire() - aRecharger);
                nbRechargesSolaires++;
                updateStatus("Recharge solaire: +" + aRecharger + "%");
                canvas.repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Batterie solaire vide!\nElle se recharge automatiquement le matin.",
                    "Information", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Énergie suffisante (" + robot.energie + "%).",
                "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateStatus(String message) {
        statusLabel.setText("Statut: " + message);
        energyBar.setValue(robot.energie);
        solarBatteryLabel.setText("Solaire: " + robot.getBatterieSolaire() + "%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RobotSwingApp app = new RobotSwingApp();
            app.setVisible(true);
        });
    }
}