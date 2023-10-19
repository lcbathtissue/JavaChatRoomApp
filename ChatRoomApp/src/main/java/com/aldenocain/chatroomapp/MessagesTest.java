package com.aldenocain.chatroomapp;

public class MessagesTest {
    public static void main(String[] args) {
        // Assume you have an SQLiteCRUD instance
        SQLiteCRUD sqliteCRUD = new SQLiteCRUD();

        // Create an instance of Messages with the SQLiteCRUD instance
        Messages messages = new Messages(sqliteCRUD);

        // Send a message
        boolean messageSent = messages.sendMessage(4, "This is a message that contains a banned word.. DurhamCollege");
        // System.out.println("[sendMessage] messageSent=" + messageSent);

        // Get chat messages
        System.out.println("Chat Messages:");
        messages.getMessages();

        // Pin a message
        boolean messagePinned = messages.pinMessage(3);
        // System.out.println("[pinMessage] messagePinned=" + messagePinned);

        // Get pinned messages
        System.out.println("Pinned Messages:");
        messages.getPinnedMessages();

        // Unpin a message
        boolean messageUnpinned = messages.unpinMessage(3);
        // System.out.println("[pinMessage] unpinMessage=" + messagePinned);

        // Get pinned messages
        System.out.println("Pinned Messages:");
        messages.getPinnedMessages();

        // Check for banned words
        System.out.println("Check for banned words:");
        boolean bannedWordsCheck = messages.checkMsgForBannedWords(messages.getIndividualMsg(1));
        System.out.println("[checkMsgForBannedWords, clean message] bannedWordsCheck=" + bannedWordsCheck + " (expected false)");
        bannedWordsCheck = messages.checkMsgForBannedWords(messages.getIndividualMsg(4));
        System.out.println("[checkMsgForBannedWords, dirty message] bannedWordsCheck=" + bannedWordsCheck + " (expected true)");
    }
}
