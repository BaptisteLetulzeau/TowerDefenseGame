**TowerDefenseGame**

    Si vous voulez voir la doc utilisateur, voici le lien: https://github.com/BaptisteLetulzeau/TowerDefenseGame/blob/main/docs/user/README.md

**Table des Matières**

    - Caractéristiques
    - Design Pattern
    - Installation
    - Technologies utilisées

**Caractéristiques**

    Plusieurs entités avec leur propres spécificités (point de vie et vitesse pour les monstres ou encore dégâts et portée pour les tours)
    Les vagues d’ennemis deviennent de plus en plus difficiles à mesure que la partie avance.
    Interface graphique interactive (JavaFX).
    Suivi des scores et sauvegarde des parties.
    Design modulaire et facilement extensible.

**Design Pattern**

    - Factory pattern (créer des tours (Towers) différentes en fonction de la sélection de l'utilisateur sans avoir à instancier directement les classes concrètes (comme ArrowTower))
    - Observer pattern (notifier des éléments du jeu (comme une tour) lorsqu'un ennemi entre dans la portée de la tour (pratique pour les attaques automatiques))
    - Strategy pattern (changer la stratégie d'attaque des tours (par exemple : cibler le premier ennemi, le plus proche, ou le plus faible))

**Installation**

    - Prérequis

        Java Development Kit (JDK) : version 23 ou supérieure.

    Install it: https://openjfx.io/openjfx-docs/#install-java
    Install IDE: https://www.jetbrains.com/fr-fr/idea/download/?section=windows

**Technologies utilisées**

    - Java (+framework JavaFX)
