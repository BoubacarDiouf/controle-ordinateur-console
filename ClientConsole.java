import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez l'adresse IP du serveur : ");
        String serverAddress = scanner.nextLine();
        System.out.print("Entrez le port du serveur : ");
        int serverPort;
        try {
            serverPort = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Port invalide. Utilisation du port par défaut 5000.");
            serverPort = 5000;
        }

        try (
            Socket socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connecté au serveur " + serverAddress + ":" + serverPort);

            String serverRequest;
            while ((serverRequest = in.readLine()) != null) {
                System.out.println(serverRequest);
                if (serverRequest.contains("nom d'utilisateur")) {
                    System.out.print("Nom d'utilisateur : ");
                    out.println(scanner.nextLine());
                } else if (serverRequest.contains("mot de passe")) {
                    System.out.print("Mot de passe : ");
                    out.println(scanner.nextLine());
                } else if (serverRequest.contains("Authentification échouée")) {
                    System.out.println("Connexion refusée par le serveur.");
                    return;
                } else if (serverRequest.contains("Authentification réussie")) {
                    break; 
                }
            }

            while (true) {
                System.out.print("Entrez une commande (ou 'quit' pour quitter) : ");
                String command = scanner.nextLine();

                if ("quit".equalsIgnoreCase(command)) {
                    break;
                }

                out.println(command);
                String response;
                while ((response = in.readLine()) != null) {
                    if (response.equals("END_OF_RESPONSE")) break; 
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur client: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
