
# Projet JAVA - Application de Gestion de Robots

## 📌 Aperçu
Ce projet est une application Java de gestion de robots. L'application intègre des fonctionnalités de modularité, écologie, et une interface Swing pour gérer des robots connectés spécialisés dans la livraison, avec un focus sur l'empreinte carbone et l'optimisation énergétique.

---

## � Architecture du Projet

### Hiérarchie des Classes
- **`Robot`** (classe abstraite)  
  - Gère l'énergie, la position, et les actions de base (`démarrer`, `arrêter`, `déplacer`, etc.).  
  - Attributs : `ID`, `position (x, y)`, `niveauÉnergie`, `historiqueActions`.  
  - Méthode abstraite : `effectuerTache()`.

- **`RobotConnecté`** (hérite de `Robot`)  
  - Implémente l'interface `Connectable` pour la connexion réseau.  
  - Attributs : `connecté`, `réseauConnecté`.  
  - Méthodes : `envoyerDonnées()`, `seConnecter()`.

- **`RobotLivraison`** (hérite de `RobotConnecté`)  
  - Spécialisé dans la livraison avec gestion écologique.  
  - Attributs : `colisActuel`, `destination`, `batterieSolaire`.  
  - Méthodes : `chargerColis()`, `livrer()`, `rechargerSolaire()`.

---

## ✨ Fonctionnalités Clés

### 1. **Gestion d'Énergie**
- **Consommation dynamique** : 0.3% d'énergie par unité de distance.  
- **Recharge** :  
  - Classique (seuil à 10%).  
  - Solaire (automatique en journée ou manuelle via bouton dédié).  

### 2. **Écologie Intégrée**
- **Batterie Solaire** :  
  - Utilisée si l'énergie principale < 10%.  
  - Réduction de l'empreinte carbone.  
- **Calcul d'empreinte carbone** : Affichée après chaque livraison (ex: `2.50g CO2`).  

### 3. **Interface Graphique (Swing)**
- **Zone centrale** : Visualisation du robot et sa position en temps réel.  
- **Panneaux de contrôle** :  
  - Boutons : `Démarrer/Arrêter`, `Charger Colis`, `Livrer`, `Recharger`, `Stats Eco`.  
  - Barre d'état : Niveau d'énergie et batterie solaire.  

---

## 🏗️ Gestion des Exceptions
- **`EnergieInsuffisanteException`** : Lancée si l'énergie est trop faible pour une action.  
- **`MaintenanceRequiseException`** : Signalée pour les robots nécessitant une maintenance.  

---

## 🌱 Mode Éco-Responsable
- **Optimisation des trajets** : Minimisation du CO2.  
- **Statistiques** :  
  - Comparaison énergie classique vs. solaire.  
  - Historique des émissions de CO2.  

---
