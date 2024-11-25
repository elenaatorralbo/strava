package com.example.facebook.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final UserService userService;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.userService = UserService.getInstance(); // Singleton para compartir usuarios
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request = in.readLine();
            System.out.println("Request received: " + request);

            // Procesar la solicitud
            String response = processRequest(request);
            out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processRequest(String request) {
        // Formato esperado: "action:email:password"
        String[] parts = request.split(":");
        if (parts.length != 3) {
            return "Error: Invalid request format. Expected 'action:email:password'.";
        }

        String action = parts[0];
        String email = parts[1];
        String password = parts[2];

        if ("register".equalsIgnoreCase(action)) {
            return userService.registerUser(email, password);
        } else if ("verify".equalsIgnoreCase(action)) {
            return userService.verifyCredentials(email, password);
        } else {
            return "Error: Unsupported action. Use 'register' or 'verify'.";
        }


    }
}
