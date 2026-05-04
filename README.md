#  Simulation de jeu de Bataille -  Java 21

##  Présentation

Ce projet est une simulation en Java (console) du jeu de bataille.  
Il permet de lancer des parties avec plusieurs joueurs, de simuler automatiquement le jeu et de conserver un historique des parties.

##  Stack technique

Java 21
Maven
Hibernate ORM
MySQL

##  Architecture

Le projet est structuré selon une approche en couches inspirée de la Clean Architecture :

com.marianadev  
- application → orchestration des cas d’usage  
- domain → logique métier  
- infrastructure → persistance et configuration technique  
- presentation → interface utilisateur (console)



---

###  Détail des couches

####  Domain
Contient la logique métier pure :

- `Card`, `Deck`, `Player`, `Game`, `GameOver`, `ResponseHand`
- `Value`, `Symbol` (énumérations)
- Chaque classe contient ses propres comportements, par exemple:
  - preparation et mélange du deck
  - distribution des cartes
  - gestion des tours
  - gestion des files de cartes (Queue)
  - gestion de fin de jeu

---

####  Application

- `GameService` :
  - initialise le jeu
  - distribue les cartes
  - gère les manches
  - mode autoplay
  - détection de fin de partie

- `GameHistory` :
  - gestion de l’historique
  - interaction avec le repository

---

#### Infrastructure

- `GameEntity` : entité persistée en base
- `GameRepository` : accès aux données (Hibernate)
- `SessionFactoryProvider` : configuration Hibernate

---

####  Presentation

- `ConsoleUi` :
  - menu principal
  - menu de jeu
  - affichage des résultats
  - interaction avec l'utilisateur

---

##  Règles du jeu

- Jeu basé sur un deck de 52 cartes (13 valeurs × 4 couleurs)
- Chaque joueur joue une carte par tour
- La carte gagnante est déterminée :
  1. par la valeur (A > K > Q > … > 2)
  2. en cas d’égalité, par la couleur (ordre défini dans le code)
- Le gagnant récupère les cartes
- La partie se termine lorsqu’un joueur possède toutes les cartes

---

##  Fonctionnalités

###  Jeu
- Initialisation avec N joueurs (2 à 4)
- Mélange et distribution automatique
- Mode manuel (tour par tour)
- Mode autoplay (simulation complète)

---

###  Gestion des égalités
- Implémentation d’un système de duel entre joueurs concernés
- Structure prévue pour être étendue (récursivité)

Posibilité de duel infinite et que le joueurs concernnés perdent la totalité de ses cartes:
- Proposition de déterminer la carte gagnante par symbole si égalité par valeur (ordre défini et implementé dans le code)

Posible amelioration: definir un nombre determiné de duels pour eviter duel infinite.

---

###  Gestion des boucles
Le jeu de bataille pouvant générer des parties très longues.
Posible amelioration: une limite de tours peut être mise en place afin d’éviter les boucles infinies.

Propriété pour gerer les nombre d'iteractions lors de chaque main mise en place pour posible amelioration.

---

###  Persistance des parties

Implémentée avec **Hibernate + MySQL** :

- Sauvegarde :
  - date de la partie
  - nombre de joueurs
  - gagnant

- Consultation :
  - historique des parties
  - possibilité de rejouer avec la dernière configuration 

---

##  Choix techniques

- Utilisation de `Queue` pour modéliser le comportement des joueurs (FIFO)
- Séparation stricte des responsabilités (architecture en couches)
- Utilisation d’énumérations avec ranking pour simplifier les comparaisons
- Code orienté lisibilité, extensibilité et testabilité

---

##  Améliorations possibles

- Interface graphique (GUI)
- Tests unitaires et d’intégration
- Optimisation de la gestion des égalités (pile de cartes type “bataille classique”)
- Détection avancée des cycles
- API REST pour exposer le jeu

---

##  Installation

1. Cloner le projet
2. Créer une base MySQL (ex: `card_game_db`)
3. Configurer le fichier `hibernate.cfg.xml` avec les donnée de conection.
4. Lancer l’application via `App.java`

---

##  Auteur

Mariana ALMONTE ALVAREZ
