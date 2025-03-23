import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServeurConsole {
    private static final int PORT = 5000; 
    private static boolean running = true; 
    private static final String utilisateur = "admin"; 
    private static final String mdp = "passer"; 

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool(); 
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré sur le port " + PORT);
            
            while (isRunning()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Nouveau client connecté: " + clientSocket.getInetAddress());
                    executor.execute(new ClientHandler(clientSocket));
                } catch (IOException e) {
                    System.err.println("Erreur de connexion client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    public synchronized static boolean isRunning() { 
        return running;
    }

    public synchronized static void setRunning(boolean value) { 
        running = value;
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                out.println("Entrez votre nom d'utilisateur : ");
                String username = in.readLine();
                out.println("Entrez votre mot de passe : ");
                String password = in.readLine();

                if (!utilisateur.equals(username) || !mdp.equals(password)) {
                    out.println("Authentification échouée. Connexion fermée.");
                    return; 
                }
                out.println("Authentification réussie. Vous pouvez envoyer des commandes.");

                String command;
                while ((command = in.readLine()) != null) {
                    if ("shutdown".equalsIgnoreCase(command)) {
                        setRunning(false); 
                        out.println("Serveur en cours d'arrêt...");
                        break;
                    }
                    System.out.println("Commande reçue: " + command);
                    String result = executeCommand(command);
                    out.println(result);
                    out.println("END_OF_RESPONSE"); 
                }
            } catch (IOException e) {
                System.err.println("Erreur avec le client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Erreur fermeture socket: " + e.getMessage());
                }
            }
        }

        private String executeCommand(String command) {
            try {
                ProcessBuilder pb = new ProcessBuilder();
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    pb.command("cmd.exe", "/c", command);
                } else {
                    pb.command("/bin/sh", "-c", command);
                }

                Process process = pb.start();
                StringBuilder output = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                while ((line = errorReader.readLine()) != null) {
                    output.append("ERROR: ").append(line).append("\n");
                }

                process.waitFor();
                return output.length() > 0 ? output.toString() : "Commande exécutée avec succès";
            } catch (Exception e) {
                return "Erreur d'exécution: " + e.getMessage();
            }
        }
    }
}
