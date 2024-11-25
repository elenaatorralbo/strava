package com.example.facebook.server;


import java.util.concurrent.ConcurrentHashMap;


public class UserService
{
    private static UserService instance;
    private final ConcurrentHashMap<String, String> users;

    private UserService()
    {
        users = new ConcurrentHashMap<>();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public String registerUser(String email, String password) {
        if (users.putIfAbsent(email, password) == null) {
            return "User registered successfully.";
        } else {
            return "Error: User already exists.";
        }
    }
    public String verifyCredentials(String email, String password) {
        String storedPassword = users.get(email);
        if (storedPassword != null && storedPassword.equals(password)) {
            return "Login successful.";
        } else {
            return "Error: Invalid credentials.";
        }
    }
}
