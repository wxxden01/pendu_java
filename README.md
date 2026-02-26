DM JAVA:
L’objectif était de mettre en pratique la programmation orientée objet, la manipulation de fichiers, les structures de données et la gestion des interactions utilisateur.


Structure du programme:
- deux classes :

	- Initialisation : prépare les éléments nécessaires au démarrage (lecture du fichier de mots, choix aléatoire du mot, création du joueur, initialisation des variables).

	- Jeu : gère le déroulement de la partie (boucle de jeu, vérifications, analyse des lettres, fin de partie et enregistrement du score).

Cette séparation permet de distinguer la phase de préparation et la logique du jeu.

Fonctionnement du jeu:
- Lecture d’un fichier texte contenant une liste de mots.

- Sélection aléatoire d’un mot.

- Transformation du mot en liste de lettres.

- Création d’un pseudo joueur.

- Initialisation du mot caché avec des “_”.

- Lancement d’une boucle tant que le joueur a des chances restantes.

À chaque tour :

- Le joueur saisit une lettre.

- Le programme vérifie la validité de la saisie.

- Si la lettre est présente dans le mot, elle est révélée, sinon, une chance est retirée.

- Le nombre d’essais est mis à jour.

- La partie se termine soit par une victoire (mot entièrement découvert), soit par une défaite (plus de chances).

Gestion des erreurs, plusieurs contrôles sont mis en place :

- Vérification que l’utilisateur saisit bien une seule lettre grâce à une expression régulière.

- Vérification que la lettre n’a pas déjà été utilisée.

- Gestion des exceptions lors de la lecture du fichier de mots.

- Gestion des exceptions lors de l’écriture du score dans un fichier.

Ces contrôles permettent d’éviter les entrées incorrectes et les erreurs liées aux fichiers.


Gestion du score:

En cas de victoire, un score est calculé en fonction du nombre d’essais et de la longueur du mot.
Le score est ensuite enregistré dans un fichier texte.
