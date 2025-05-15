import java.util.Scanner;


public class RobotLivraison extends RobotConnecte {

    private String colisActuel;
    private String destination;
    private boolean enLivraison;
    static final int ENERGIE_LIVRAISON = 15;
    static final int ENERGIE_CHARGEMENT  = 5;
    private int batterieSolaire = 0;
    private int distanceMatin = 0;
    
    public RobotLivraison(String id, int x, int y) {
        super(id, x, y);
        this.colisActuel = "0";
        this.destination = null;
        this.enLivraison = false;
    }


     public void effectuerTache() throws RobotException {
    if (!enMarche) {
        throw new RobotException("Le robot doit être démarré pour effectuer une tâche");
    }

    Scanner scanner = new Scanner(System.in);
    
    if (enLivraison) {
        System.out.print("Entrez la coordonnée X de destination : ");
        int destX = scanner.nextInt();
        
        System.out.print("Entrez la coordonnée Y de destination : ");
        int destY = scanner.nextInt();
        
        scanner.nextLine(); 
        
        FaireLivraison(destX, destY);
    } else {
        System.out.print("Voulez-vous charger un nouveau colis ? (O/N) : ");
        String reponse = scanner.nextLine();
        
        if (reponse.equalsIgnoreCase("O")) {
            
            System.out.print("Destination : ");
            String destination = scanner.nextLine();
            
            chargerColis(destination);
        } else {
            ajouterHistorique("En attente de colis");
        }
    }
    
    scanner.close();
}
public void FaireLivraison(int Destx, int Desty) throws RobotException {
    deplacer(Destx, Desty) ;
    colisActuel = "0";
    enLivraison = false;
    ajouterHistorique("Livraison terminée à " + destination);
}
@Override
public void deplacer(int x, int y) throws RobotException {
    int heureActuelle = java.time.LocalTime.now().getHour(); 
    double distance = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    if (distance > 100) {
        throw new RobotException("Déplacement impossible : distance trop grande.");
    }
    verifierMaintenance();
    int energieutilisee = (int) Math.ceil(distance * 0.3);
    verifierEnergie(energieutilisee);

    if (heureActuelle >= 10 && heureActuelle < 17) {
        distanceMatin += distance;
    }

    this.x = x;
    this.y = y;
    this.heuresUtilisation += Math.max(1, (int) distance / 10);
    consommerEnergie(energieutilisee);
    ajouterHistorique("Déplacement vers (" + x + ", " + y + "), énergie consommée : " + energieutilisee);
}

public void chargerColis(String destination) throws RobotException {
    verifierEnergie(ENERGIE_CHARGEMENT);
    if (!enLivraison && colisActuel.equals("0")) {
        this.colisActuel = "1";
        this.destination = destination;
        enLivraison = true;
        consommerEnergie(ENERGIE_CHARGEMENT);
        ajouterHistorique("Colis chargé : " + destination);
    } else {
        throw new RobotException("Le robot est déjà en livraison ou a un colis en cours.");
        
    }
}
@Override
public String toString() {
    return String.format(
        "RobotLivraison [ID : %s, Position : (%d,%d), Énergie : %d%%, Heures : %d, Colis : %d, Destination : %s, Connecté : %s]",
        id, x, y, energie, heuresUtilisation,
        colisActuel != null ? 1 : 0,
        destination != null ? destination : "null",
        connecte ? "Oui" : "Non"
    );
}

public int getBatterieSolaire() {
    return batterieSolaire;
}

public void setBatterieSolaire(int valeur) {
    this.batterieSolaire = Math.max(0, valeur);
}

public void rechargerSolaireMatin() {
    batterieSolaire += (int) (distanceMatin * 0.1);
    distanceMatin = 0; 
}
}

