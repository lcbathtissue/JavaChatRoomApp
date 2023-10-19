package com.aldenocain.chatroomapp;

public class Users {
    private static SQLiteCRUD sqliteCRUD;

    public Users(SQLiteCRUD sqliteCRUD) {
        System.out.println("Users constructor");
    }

    public static boolean checkUsername(String username) {
        return sqliteCRUD.checkUsername(username);
    }

    public static boolean authenticateUsername(String username, String password) {
        sqliteCRUD = new SQLiteCRUD();
        return sqliteCRUD.authenticateUser(username, password);
    }

    public static boolean createUser(String username, String password, String email) {
        sqliteCRUD = new SQLiteCRUD();
        if (!checkUsername(username)) {
            sqliteCRUD.createUser(username, password, email);
            return true;
        } else {
            return false;
        }
    }

    public static boolean loginUser(String username, String password) {
        return authenticateUsername(username, password);
    }
}

