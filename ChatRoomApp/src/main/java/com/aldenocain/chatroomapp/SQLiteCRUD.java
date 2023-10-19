package com.aldenocain.chatroomapp;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLiteCRUD {
    private Connection connection;

    public SQLiteCRUD() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Initialize the SQLite database connection
            connection = DriverManager.getConnection("jdbc:sqlite:ChatRoomAppDB.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//    public void initChatTable() {
//        try {
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, sender_id INTEGER, message_text TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
//            PreparedStatement createTable = connection.prepareStatement(createTableSQL);
//            createTable.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // INITIALIZE TABLES
    public void initializeDatabaseTables() {
        initUsersTable();
        initMessagesTable();
        initPinnedMessagesTable();
        initBannedWordsTable();
    }
    public void initUsersTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (UserID INTEGER PRIMARY KEY, Username TEXT, Password TEXT, Email TEXT)";
            PreparedStatement createTable = connection.prepareStatement(createTableSQL);
            createTable.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void initMessagesTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS messages (MessageID INTEGER PRIMARY KEY, Content TEXT, SenderID INTEGER, ChatroomID INTEGER, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
            PreparedStatement createTable = connection.prepareStatement(createTableSQL);
            createTable.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void initPinnedMessagesTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS pinned_messages (MessageID INTEGER)";
            PreparedStatement createTable = connection.prepareStatement(createTableSQL);
            createTable.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void initBannedWordsTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS banned_words (BannedWordID INTEGER PRIMARY KEY, Word TEXT)";
            PreparedStatement createTable = connection.prepareStatement(createTableSQL);
            createTable.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CLEAR TABLES CONTENTS
    public void clearDatabaseTables() {
        clearUserTable();
        clearMessagesTable();
        clearPinnedMessagesTable();
        clearBannedWordsTable();
    }
    public void clearUserTable() {
        try {
            String clearTableSQL = "DELETE FROM users";
            PreparedStatement clearTableStatement = connection.prepareStatement(clearTableSQL);
            clearTableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void clearMessagesTable() {
        try {
            String clearTableSQL = "DELETE FROM messages";
            PreparedStatement clearTableStatement = connection.prepareStatement(clearTableSQL);
            clearTableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearPinnedMessagesTable() {
        try {
            String clearTableSQL = "DELETE FROM pinned_messages";
            PreparedStatement clearTableStatement = connection.prepareStatement(clearTableSQL);
            clearTableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearBannedWordsTable() {
        try {
            String clearTableSQL = "DELETE FROM banned_words";
            PreparedStatement clearTableStatement = connection.prepareStatement(clearTableSQL);
            clearTableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // USER RELATED METHODS
    public void createUser(String username, String password, String email) {
        try {
            String insertSQL = "INSERT INTO users (Username, Password, Email, Role) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setString(3, email);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUsername(String username) {
        try {
            // SQL query to check if the username exists
            String selectSQL = "SELECT COUNT(*) FROM users WHERE Username = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Username exists if count > 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // In case of an error or if the username doesn't exist
    }

    public boolean authenticateUser(String username, String password) {
        try {
            // SQL query to authenticate a username and password
            String selectSQL = "SELECT COUNT(*) FROM users WHERE Username = ? AND Password = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setString(1, username);
            selectStatement.setString(2, password);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Authentication successful if count > 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // In case of an error or if authentication fails
    }

    // MESSAGE RELATED METHODS

    public void storeMessage(int senderId, String messageText) {
        try {
            String insertSQL = "INSERT INTO messages (sender_id, message_text) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setInt(1, senderId);
            insertStatement.setString(2, messageText);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printChatMessages() {
        try {
            String selectSQL = "SELECT id, sender_id, message_text, timestamp FROM messages";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int senderId = resultSet.getInt("sender_id");
                String messageText = resultSet.getString("message_text");
                String timestamp = resultSet.getString("timestamp");

                // Process the retrieved data as needed
                System.out.println("ID: " + id + ", Sender ID: " + senderId + ", Message: " + messageText + ", Timestamp: " + timestamp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Msg> getChatMessages() {
        List<Msg> chatMessages = new ArrayList<>();
        try {
            String selectSQL = "SELECT id, sender_id, message_text, timestamp FROM messages";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int senderId = resultSet.getInt("sender_id");
                String messageText = resultSet.getString("message_text");
                String timestamp = resultSet.getString("timestamp");

                Msg msg = new Msg(id, senderId, messageText, timestamp);
                chatMessages.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatMessages;
    }
    public String getMessageContent(int messageId) {
        String messageText = null;
        try {
            String selectSQL = "SELECT message_text FROM messages WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setInt(1, messageId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                messageText = resultSet.getString("message_text");
                System.out.println(messageText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageText;
    }


    // MESSAGE PINNING RELATED METHODS
    public void pinMessage(int messageID) {
        try {
            String insertSQL = "INSERT INTO pinned_messages (MessageID) VALUES (?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setInt(1, messageID);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printPinnedMessages() {
        try {
            String selectSQL = "SELECT pm.MessageID, m.message_text " +
                    "FROM pinned_messages AS pm " +
                    "INNER JOIN messages AS m ON pm.MessageID = m.id";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int messageID = resultSet.getInt("MessageID");
                String messageText = resultSet.getString("message_text");
                System.out.println("Message ID: " + messageID + ", Message Content: " + messageText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Integer> getPinnedMessageIDs() {
        List<Integer> pinnedMessageIDs = new ArrayList<>();
        try {
            String selectSQL = "SELECT MessageID FROM pinned_messages";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int messageID = resultSet.getInt("MessageID");
                pinnedMessageIDs.add(messageID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pinnedMessageIDs;
    }
    public void unpinMessage(int messageID) {
        try {
            String deleteSQL = "DELETE FROM pinned_messages WHERE MessageID = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setInt(1, messageID);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // BANNED WORDS RELATED METHODS
    public void addBannedWord(String word) {
        try {
            String insertSQL = "INSERT INTO banned_words (Word) VALUES (?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setString(1, word);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printBannedWords() {
        try {
            String selectSQL = "SELECT Word FROM banned_words";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("Word");
                System.out.println(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getBannedWords() {
        List<String> bannedWords = new ArrayList<>();
        try {
            String selectSQL = "SELECT Word FROM banned_words";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("Word");
                bannedWords.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bannedWords;
    }


    public void removeBannedWord(String word) {
        try {
            String deleteSQL = "DELETE FROM banned_words WHERE Word = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setString(1, word);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

//
//
//public class SQLiteCRUD {
//    private static final String DATABASE_NAME = "mydatabase.db";
//    private static final int DATABASE_VERSION = 1;
//
//    private SQLiteDatabase db;
//
//    public SQLiteCRUD(Context context) {
//        DBHelper dbHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
//    }
//
//    public void initTable() {
//        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)");
//    }
//
//    public void clearTable() {
//        try {
//            db.execSQL("DROP TABLE IF EXISTS users");
//            initTable();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void create(int id, String name) {
//        ContentValues values = new ContentValues();
//        values.put("id", id);
//        values.put("name", name);
//        db.insert("users", null, values);
//    }
//
//    public String[] read(int id) {
//        String[] projection = {"name"};
//        String selection = "id=?";
//        String[] selectionArgs = {String.valueOf(id)};
//
//        Cursor cursor = null;
//        try {
//            cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                String name = cursor.getString(cursor.getColumnIndex("name"));
//                return new String[]{String.valueOf(id), name};
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        // If no data found for the given ID, return null or an empty array
//        return null;
//    }
//
//    public void readAll() {
//        String[] projection = {"id", "name"};
//        Cursor cursor = db.query("users", projection, null, null, null, null, null);
//
//        if (cursor != null) {
//            try {
//                System.out.println("User Data:");
//                while (cursor.moveToNext()) {
//                    int id = cursor.getInt(cursor.getColumnIndex("id"));
//                    String name = cursor.getString(cursor.getColumnIndex("name"));
//                    System.out.println("ID: " + id + ", Name: " + name);
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//    }
//
//    public void update(int id, String newName) {
//        ContentValues values = new ContentValues();
//        values.put("name", newName);
//        String selection = "id=?";
//        String[] selectionArgs = {String.valueOf(id)};
//        db.update("users", values, selection, selectionArgs);
//    }
//
//    public void delete(int id) {
//        String selection = "id=?";
//        String[] selectionArgs = {String.valueOf(id)};
//        db.delete("users", selection, selectionArgs);
//    }
//
//    public int getNextAvailableID() {
//        int nextID = 1; // Default starting ID
//
//        Cursor cursor = null;
//        try {
//            // Query the database to find the maximum ID currently in use
//            cursor = db.rawQuery("SELECT MAX(id) FROM users", null);
//            if (cursor.moveToFirst()) {
//                nextID = cursor.getInt(0) + 1;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return nextID;
//    }
//
//    private static class DBHelper extends SQLiteOpenHelper {
//        DBHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)");
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // Handle database schema upgrades here if needed.
//        }
//    }
//}
