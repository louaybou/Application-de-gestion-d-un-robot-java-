interface Connectable {
    void connecter ( String reseau ) throws RobotException ;
    void deconnecter () throws RobotException ;
    void envoyerDonnees( String donnees) throws RobotException ;
}