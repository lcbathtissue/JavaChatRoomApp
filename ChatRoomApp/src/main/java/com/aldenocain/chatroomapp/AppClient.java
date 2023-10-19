 package com.aldenocain.chatroomapp;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class AppClient {
  private static int SERVER_PORT = 3500;
  public static void showMainMenu(Scanner scanner, DataInputStream br, DataOutputStream dos) throws IOException {
    System.out.println("ChatApp: ");
    System.out.println("1) Login with an existing user");
    System.out.println("2) Create a new account");
    System.out.println("3) Exit");
    int choice;
    do {
      System.out.print("Enter your choice (1, 2, or 3): ");
      while (!scanner.hasNextInt()) {
        System.out.print("Invalid input. Please enter a valid choice (1, 2, or 3): ");
        scanner.next();
      }
      choice = scanner.nextInt();
    } while (choice < 1 || choice > 3);
    switch (choice) {
      case 1:
        loginUser(scanner, br, dos);
        break;
      case 2:
        registerUser(scanner, br, dos);
        break;
      case 3:
        System.out.println("Exiting ChatApp. Goodbye!");
        break;
    }
  }

  public static void registerUser(Scanner scanner, DataInputStream br, DataOutputStream dos) throws IOException {
    System.out.print("Enter your username: ");
    String username = scanner.next();
    System.out.print("Enter your password: ");
    String password = scanner.next();
    System.out.print("Enter your email: ");
    String email = scanner.next();

    dos.writeUTF("process.register");
    dos.flush();
    dos.writeUTF(username);
    dos.flush();
    dos.writeUTF(password);
    dos.flush();
    dos.writeUTF(email);
    dos.flush();

    String registerStatus = br.readLine();
    System.out.println("Register: " + registerStatus);
    if (registerStatus.equals("SUCCESS")) {
      startChatService(scanner, br, dos, username.trim());
    } else {
      System.out.println("Something went wrong.. closing..");
      System.exit(1);
    }
  }

  public static void loginUser(Scanner scanner, DataInputStream br, DataOutputStream dos) throws IOException {
    System.out.print("Enter your username: ");
    String username = scanner.next();
    System.out.print("Enter your password: ");
    String password = scanner.next();

    dos.writeUTF("process.login");
    dos.flush();
    dos.writeUTF(username);
    dos.flush();
    dos.writeUTF(password);
    dos.flush();

    String loginStatus = br.readLine();
    System.out.println("Login: " + loginStatus);
    if (loginStatus.equals("SUCCESS")) {
      startChatService(scanner, br, dos, username.trim());
    } else {
      System.out.println("Something went wrong.. closing..");
      System.exit(1);
    }
  }
  public static void startChatService(Scanner scanner, DataInputStream br, DataOutputStream dos, String username) throws IOException {
    System.out.println("\nEntering the chatroom..");
    dos.writeUTF("process.chat");
    dos.flush();
    String message = "";
    System.out.print("[" + username.trim() + "]: ");
    while (true) {
      if (!message.isEmpty()) {
        System.out.print("[" + username.trim() + "]: ");
      }
      message = scanner.nextLine();
      screenMessage(message);
      dos.writeUTF(message);
      dos.flush();
    }
  }

  public static void screenMessage(String message){
    if (Messages.checkMsgForBannedWords(message)){
      System.out.println("[WARNING]: Please watch what you say! Repeated use of foul language may result in a ban from the chatroom!");
      // return Messages.sanitizeMessage(message);
    }
    // return message;
  }

  public static Scanner flushScanner(Scanner scanner){
    if (scanner.hasNextLine()) {
      scanner.nextLine(); // Consume the newline character if it's there
    }
    return scanner;
  }

   public static void main(String argv[]) throws Exception {
     Socket echo = new Socket("localhost", SERVER_PORT);
     DataInputStream br = new DataInputStream(echo.getInputStream());
     DataOutputStream dos = new DataOutputStream(echo.getOutputStream());
     Scanner scanner = new Scanner(System.in);
     showMainMenu(scanner, br, dos);
   }
}

//public class AppClient {
// public static void main(String argv[]) throws Exception {
//   Socket echo;
//   DataInputStream br;
//   DataOutputStream dos;
//
//   echo = new Socket("localhost", 3500);
//   br = new DataInputStream(echo.getInputStream());
//   dos = new DataOutputStream(echo.getOutputStream());
//   int n = 7, k = 3;
//   dos.writeInt(n);
//   dos.flush();
//   dos.writeInt(k);
//   dos.flush();
//   String str = br.readLine();
//   System.out.println("I got: "+str);
// }
//}
