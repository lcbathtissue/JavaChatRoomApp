 package com.aldenocain.chatroomapp;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer {
    private static int SERVER_PORT = 3500;
   private static final int MAX_CLIENTS = 5;
   private ServerSocket serverSocket;
   private ExecutorService executorService;
 
   public static void main(String argv[]) throws Exception {
     new AppServer();
   }

   public AppServer() throws Exception {
        serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Server Listening on port " + SERVER_PORT + "....\n");

        executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
        int connectedClients = 0;

        while (true) {
            if (connectedClients < MAX_CLIENTS) {
                Socket clientSocket = serverSocket.accept();
                int clientPort = clientSocket.getPort();
                InetAddress clientAddress = clientSocket.getInetAddress();
                System.out.println("Established connection with " + clientAddress.getHostAddress() + ":" + clientPort);
                executorService.execute(new ClientHandler(clientSocket));
                connectedClients++;
                // System.out.println("\nSERVER HAS NEW CONNECTION - # CONNECTIONS..\nconnectedClient="+connectedClients);
            } else {
                // System.out.println("\nSERVER HAS REACHED THE LIMIT FOR # CONNECTIONS !\nconnectedClient="+connectedClients+"\nMAX_CLIENTS="+MAX_CLIENTS);
                
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientRejector(clientSocket));

            }
        }
    }

    public static String screenMessage(String message){
        if (Messages.checkMsgForBannedWords(message)){
            message = Messages.sanitizeMessage(message);
            return message;
        }
        return message;
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (DataInputStream br = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {
                String username = null;
                while (true) {
                    String process = br.readUTF();
                    if (Objects.equals(process, "process.login")) {
                        username = br.readUTF();
                        String password = br.readUTF();
                        boolean login_result_bool = Users.loginUser(username, password);
                        String login_result;
                        if (login_result_bool) {
                            login_result = "SUCCESS";
                        } else {
                            login_result = "FAILURE";
                        }
                        System.out.println("Login " + login_result + " (" + username + "|" + password + ") ");
                        dos.writeBytes(login_result + "\n");
                    } else if (Objects.equals(process, "process.register")) {
                        username = br.readUTF();
                        String password = br.readUTF();
                        String email = br.readUTF();
                        boolean register_result_bool = Users.createUser(username, password, email);
                        String register_result;
                        if (register_result_bool) {
                            register_result = "SUCCESS";
                        } else {
                            register_result = "FAILURE";
                        }
                        System.out.println("Registration " + register_result + " (" + username + "|" + password + "|" + email + ") ");
                        dos.writeBytes(register_result + "\n");
                    } else if (Objects.equals(process, "process.chat")) {
                        System.out.println("[" + username + "] has connected to the chatroom");
                        while (true) {
                            String message = br.readUTF();
                            message = screenMessage(message);
                            if (!message.isEmpty()) {
                                System.out.println("[" + username + "] " + message);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ClientRejector implements Runnable {
        private Socket clientSocket;

        public ClientRejector(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (DataInputStream br = new DataInputStream(clientSocket.getInputStream());
                 DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {

                dos.writeBytes("Server is busy. Please try again later.\n");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
