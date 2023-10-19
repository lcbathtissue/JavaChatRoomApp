package com.aldenocain.chatroomapp;

public class JavaDBTest {
    public static void main(String[] args) {
        try {
            // Create an instance of SQLiteCRUD
            SQLiteCRUD sqliteCRUD = new SQLiteCRUD();

            // Initialize the chat tables
            sqliteCRUD.clearDatabaseTables();
            sqliteCRUD.initializeDatabaseTables();

            // Create users
            sqliteCRUD.createUser("user001", "1234", "john@gmail.com");
            sqliteCRUD.createUser("user102", "5678", "david@hotmail.com");
            sqliteCRUD.createUser("user203", "9012", "benjamin@yahoo.com");

            // Create a sample message
            sqliteCRUD.storeMessage(1, "Hello, SQLite!");

            // Retrieve and print chat messages
            System.out.println("\nChat Messages:");
            sqliteCRUD.printChatMessages();

            // Test pinning and unpinning a message
            System.out.println("\nPinned Messages:");
            sqliteCRUD.pinMessage(1);
            sqliteCRUD.printPinnedMessages();
            System.out.println("Pinned Messages:");
            sqliteCRUD.unpinMessage(1);
            sqliteCRUD.printPinnedMessages();

            // Test pinning and unpinning a message
            System.out.println("\nBanned Words:");
            sqliteCRUD.addBannedWord("DurhamCollege");
            sqliteCRUD.printBannedWords();
            // System.out.println("Banned Words:");
            // sqliteCRUD.removeBannedWord("DurhamCollege");
            // sqliteCRUD.printBannedWords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}