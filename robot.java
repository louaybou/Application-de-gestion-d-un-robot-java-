import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Robot {
    protected String id;
    protected int x, y;
    protected int energie;
    protected int heuresUtilisation;
    protected boolean enMarche;
    protected List<String> historiqueActions;

    public Robot(String id, int x, int y, int energie, int heuresUtilisation) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.energie = energie;
        this.heuresUtilisation = heuresUtilisation;
        this.enMarche = false;
        this.historiqueActions = new ArrayList<>();
    }
    public void ajouterHistorique(String action) {
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm:ss", Locale.FRENCH);
        String dateHeure = maintenant.format(formatter);
        String entree = dateHeure + " " + action;
        historiqueActions.add(entree);
    }
    public void verifierEnergie(int energieRequise) throws EnergieInsuffisanteException {
        if (this.energie < energieRequise) {
            throw new EnergieInsuffisanteException(
                "Énergie insuffisante : requis = " + energieRequise + ", disponible = " + this.energie
            );
        }
    }
    public void verifierMaintenance() throws MaintenanceRequiseException {
        if (heuresUtilisation >= 100) {
            throw new MaintenanceRequiseException(
                "Maintenance requise : " + heuresUtilisation + " heures d’utilisation."
            );
        }
    }
    public void demarrer() throws RobotException {
        if (enMarche) {
            ajouterHistorique("Tentative de démarrage : déjà allumé.");
            return;
        }
    
        if (energie < 10) {
            String msg = "Démarrage impossible : énergie insuffisante (" + energie + "%).";
            ajouterHistorique(msg);
            throw new RobotException(msg);
        }
    
        enMarche = true;
        ajouterHistorique("Démarrage du robot.");
        System.out.println(id + " est démarré avec " + energie + "% d’énergie.");
    }
    public void arreter()  {
        if (!enMarche) {
            ajouterHistorique("Tentative d'arrêt : déjà éteint.");
            return;
        }
    
        enMarche = false;
        ajouterHistorique("Arrêt du robot.");
        System.out.println(id + " est arrêté.");
    }
    public void consommerEnergie(int quantite){
        energie = Math.max(0, energie - quantite);;
    }
    public void recharger(int quantite) {
        energie = Math.min(100, energie + quantite);
    }
    public abstract void deplacer(int x, int y) throws RobotException;
    public abstract void effectuerTache() throws RobotException;
    public String getHistorique() {
        String historique = "";
        for (String action : historiqueActions) {
            historique += action + "\n"; 
        }
        return historique;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [ID : " + id + 
               ", Position : (" + x + "," + y + "), " +
               "Énergie : " + energie + "%, Heures : " + heuresUtilisation + "]";
    }}
    
    
    

    
   
    
    
    
    
