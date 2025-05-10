public class Main {
    public static void main(String[] args) {
        try {
            // Création d'un robot de livraison
            RobotLivraison robot = new RobotLivraison("R1", 0, 0);

            // Démarrage du robot
            robot.demarrer();

            // Charger un colis
            robot.chargerColis("Destination A");

            // Déplacer le robot pour effectuer la livraison
            robot.FaireLivraison(10, 20);

            // Arrêter le robot
            robot.arreter();

            // Afficher l'historique des actions
            System.out.println("Historique des actions :");
            System.out.println(robot.getHistorique());

            // Afficher l'état final du robot
            System.out.println(robot);

        } catch (RobotException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}