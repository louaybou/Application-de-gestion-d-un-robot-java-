public class Main {
    public static void main(String[] args) {
        try {
            
            RobotLivraison robot = new RobotLivraison("R1", 0, 0);

            
            robot.demarrer();

            
            robot.chargerColis("Destination A");

            
            robot.FaireLivraison(10, 20);

            
            robot.arreter();

            
            System.out.println("Historique des actions :");
            System.out.println(robot.getHistorique());

           
            System.out.println(robot);

        } catch (RobotException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}