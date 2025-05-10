public abstract class RobotConnecte extends Robot implements Connectable {

    protected boolean connecte;
    protected String reseauconnecte;

    public RobotConnecte(String id, int x, int y) {
        super(id, x, y, 100, 0); 
        this.connecte = false;
        this.reseauconnecte = null;
    }

    @Override
    public void connecter(String reseau) throws RobotException {
        verifierEnergie(5);
        verifierMaintenance();
        if (this.connecte){
            throw new RobotException("Déjà connecté au réseau : " + this.reseauconnecte);
        }
        this.reseauconnecte = reseau;
        consommerEnergie(5);
        this.connecte = true;
        ajouterHistorique("Connecté au réseau : " + reseau);
    }
    @Override
    public void deconnecter() throws RobotException {
        verifierMaintenance();
        if(connecte) {
            this.connecte = false;
            this.reseauconnecte = null;
            ajouterHistorique("Déconnecté du réseau.");
        }
    }
    @Override
    public void envoyerDonnees(String donnees) throws RobotException {
        verifierMaintenance();
        if (!this.connecte){
            throw new RobotException("Le robot n'est pas connecté au réseau.");
        }
        if(energie <3){
            throw new EnergieInsuffisanteException("Énergie insuffisante pour envoyer des données.");
        }
        consommerEnergie(3);
        ajouterHistorique("Envoi de données sur " + reseauconnecte + ": " + donnees);


    }
    }

