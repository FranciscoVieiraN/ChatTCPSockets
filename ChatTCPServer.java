import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatTCPServer {
    private static final int PORT = 6666;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor de chat iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e);
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
                synchronized (clientWriters) {
                    clientWriters.add(writer);
                }

                System.out.println("Cliente conectado: " + socket.getInetAddress() + ":" + socket.getPort());

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Mensagem recebida: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                System.err.println("Erro de I/O: " + e);
            } finally {
                if (writer != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(writer);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static void broadcast(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}
