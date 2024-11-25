package com.example.facebook.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class FacebookLoginServer {

    private static final int PORT = 8082;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Facebook Login Server is running on port " + PORT);

            while (true) {
                // Acepta conexiones entrantes
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                // Delegar el manejo del cliente a ClientHandler
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
