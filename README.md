# Projet : Application de Gestion de Robots en Java

Ce projet est une application Java qui simule la gestion de différents types de robots, tels que des robots connectés et des robots de livraison. L'objectif est de fournir une architecture modulaire et extensible pour gérer les fonctionnalités des robots.

## Fonctionnalités principales
- **Gestion de l'énergie** : Vérification et consommation de l'énergie des robots.
- **Maintenance** : Détection des besoins de maintenance après un certain nombre d'heures d'utilisation.
- **Connexion réseau** : Connexion et déconnexion des robots à des réseaux spécifiques.
- **Livraison de colis** : Simulation de la livraison de colis avec suivi de destination.
- **Historique des actions** : Enregistrement des actions effectuées par les robots pour un suivi détaillé.

## Structure du projet
Le projet est organisé en plusieurs classes et interfaces pour une meilleure modularité :

- **`Robot.java`** : Classe abstraite définissant les fonctionnalités de base des robots.
- **`RobotConnecte.java`** : Classe abstraite pour les robots connectés, héritant de `Robot`.
- **`RobotLivraison.java`** : Classe concrète pour les robots spécialisés dans la livraison, héritant de `RobotConnecte`.
- **`EnergieInsuffisanteException.java`** : Exception personnalisée pour gérer les cas d'énergie insuffisante.
- **`MaintenanceRequiseException.java`** : Exception personnalisée pour gérer les besoins de maintenance.
- **`Connectable.java`** : Interface définissant les méthodes de connexion réseau.
- **`RobotException.java`** : Classe de base pour toutes les exceptions liées aux robots.
- **`Main.java`** : Classe principale pour tester l'application.

