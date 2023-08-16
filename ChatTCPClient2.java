import java.io.*;
import java.net.*;

public class ChatTCPClient2 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6666;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            System.out.println("Conectado ao servidor: " + SERVER_IP + ":" + SERVER_PORT);

            Thread readThread = new Thread(new ReadThread(socket));
            Thread writeThread = new Thread(new WriteThread(socket));

            readThread.start();
            writeThread.start();

            readThread.join();
            writeThread.interrupt();
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro: " + e);
        }
    }

    private static class ReadThread implements Runnable {
        private Socket socket;

        public ReadThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Mensagem do servidor: " + message);
                }
            } catch (IOException e) {
                System.err.println("Erro de I/O na leitura: " + e);
            }
        }
    }

    private static class WriteThread implements Runnable {
        private Socket socket;

        public WriteThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

                while (!Thread.currentThread().isInterrupted()) {
                    String message = consoleReader.readLine();
                    if (message == null) {
                        break;
                    }
                    writer.println(message);
                }
            } catch (IOException e) {
                System.err.println("Erro de I/O na escrita: " + e);
            }
        }
    }
}
