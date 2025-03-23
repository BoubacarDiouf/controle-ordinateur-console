Logiciel de Contrôle à Distance en Java
Ce projet est une implémentation d'un outil de contrôle à distance d'ordinateur en Java, fonctionnant de manière similaire à SSH. Il permet à un client d'envoyer des commandes à un serveur distant, qui les exécute et renvoie les résultats.
Fonctionnalités
•Communication client-serveur via TCP/IP
•Exécution de commandes système à distance
•Prise en charge multi-plateformes (Windows/Linux/Mac)
•Gestion de plusieurs clients simultanés via un système multi-thread
•Système d'authentification basique
•Interface console simple et efficace
Prérequis
•Java JDK 8 ou supérieur
•Connexion réseau entre les machines client et serveur
Structure du projet
•ServeurConsole.java : Programme serveur qui accepte les connexions et exécute les commandes
•ClientConsole.java : Programme client qui se connecte au serveur et envoie des commandes
Comment utiliser
Démarrer le serveur
1.	Compilez le fichier ServeurConsole.java
2.	javac ServeurConsole.java
3.	Lancez le serveur
4.	java ServeurConsole
Le serveur démarrera et écoutera les connexions sur le port 5000.
Utiliser le client
1.	Compilez le fichier ClientConsole.java
2.	javac ClientConsole.java
3.	Lancez le client
4.	java ClientConsole
5.	Suivez les instructions à l'écran:
o	Entrez l'adresse IP du serveur
o	Entrez le port du serveur (5000 par défaut)
o	Entrez les identifiants (nom d'utilisateur: "admin", mot de passe: "passer")
o	Commencez à envoyer des commandes
Identifiants par défaut
•	Nom d'utilisateur : admin
•	Mot de passe : passer
Commandes spéciales
•quit (côté client) : Ferme la connexion client
•shutdown (côté serveur) : Arrête le serveur
Sécurité
Ce projet est une démonstration éducative et n'est pas destiné à être utilisé en production sans modifications. Notamment:
•Les identifiants sont codés en dur dans le programme
•Les communications ne sont pas chiffrées
•Aucune restriction n'est imposée sur les commandes exécutables
Projet réalisé dans le cadre du module Java Avancé
École Supérieure Polytechnique de Dakar
Master 1 GLSI
Enseignant: Dr Mouhamed DIOP
Auteurs
•Boubacar Diouf
•Assane Gueye
•Japhet Mokombou
Licence
Ce projet est destiné à des fins éducatives uniquement.

