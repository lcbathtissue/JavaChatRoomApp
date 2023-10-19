package com.aldenocain.chatroomapp;

public class UsersTest {
    public static void main(String[] args) {
        // Initialize the SQLiteCRUD instance
        SQLiteCRUD sqliteCRUD = new SQLiteCRUD();

        // Create an instance of Users and pass the SQLiteCRUD instance
        Users users = new Users(sqliteCRUD);

        // Check if a username exists
         boolean usernameExists = users.checkUsername("freeUsername");
         System.out.println("[checkUsername, freeUsername] usernameExists=" + usernameExists + " (expected false)");
         usernameExists = users.checkUsername("user001");
         System.out.println("[checkUsername, user001] usernameExists=" + usernameExists + " (expected true)");

        // Authenticate a username and password
         boolean authenticated = users.authenticateUsername("user001", "password123");
         System.out.println("[authenticateUsername, user001/badpass] authenticated=" + authenticated + " (expected false)");
         authenticated = users.authenticateUsername("user001", "1234");
         System.out.println("[authenticateUsername, user001/1234] authenticated=" + authenticated + " (expected true)");

        System.out.println("'Test' results will not be correct unless data is configured correctly prior to execution..");
        // Test creating a new user
        boolean userCreated = users.createUser("user001", "1234", "john@gmail.com");
        System.out.println("[createUser, user001] userCreated=" + userCreated + " (expected false)");
        userCreated = users.createUser("user304", "3456", "susan@msn.com");
        System.out.println("[createUser, user304] userCreated=" + userCreated + " (expected true)");

        // Test logging into a user
        boolean loggedIn = users.loginUser("user001", "badpass");
        System.out.println("[loginUser, user001/badpass] authenticated=" + loggedIn + " (expected false)");
        loggedIn = users.loginUser("user304", "3456");
        System.out.println("[loginUser, user304/3456] authenticated=" + loggedIn + " (expected true)");
    }
}
