package com.aldenocain.chatroomapp;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Messages {
    private static SQLiteCRUD sqliteCRUD;

    public Messages(SQLiteCRUD sqliteCRUD) {
        System.out.println("Messages constructor");
    }

    public boolean sendMessage(int senderId, String messageText) {
        sqliteCRUD.storeMessage(senderId, messageText);
        return true;
    }

    public void getMessages() {
        sqliteCRUD.printChatMessages();
    }

    public String getIndividualMsg(int messageID) {
        return sqliteCRUD.getMessageContent(messageID);
    }

    public boolean pinMessage(int messageID) {
        sqliteCRUD.pinMessage(messageID);
        return true;
    }

    public boolean unpinMessage(int messageID) {
        sqliteCRUD.unpinMessage(messageID);
        return true;
    }

    public void getPinnedMessages() {
        sqliteCRUD.printPinnedMessages();
    }

    public static List<String> getBannedWords() {
        sqliteCRUD = new SQLiteCRUD();
        return sqliteCRUD.getBannedWords();
    }

    public static boolean checkMsgForBannedWords(String message) {
        List<String> bannedWords = getBannedWords();
        for (String word : bannedWords) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String sanitizeMessage(String message) {
        List<String> bannedWords = getBannedWords();
        boolean endsWithNewline = message.endsWith("\n");

        if (endsWithNewline) {
            message = message.substring(0, message.length() - 1); // Remove the newline
        }

        for (String word : bannedWords) {
            String pattern = "(?i)\\b" + Pattern.quote(word) + "\\b";
            Matcher matcher = Pattern.compile(pattern).matcher(message);
            StringBuilder sanitized = new StringBuilder();
            int lastEnd = 0;
            while (matcher.find()) {
                sanitized.append(message, lastEnd, matcher.start());
                sanitized.append(generateAsterisks(matcher.group().length()));
                lastEnd = matcher.end();
            }
            sanitized.append(message, lastEnd, message.length());

            message = sanitized.toString();
        }

        if (endsWithNewline) {
            message += "\n"; // Add the newline back
        }
        message += " <SANITIZED>";

        return message;
    }

    public static String generateAsterisks(int count) {
        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < count; i++) {
            asterisks.append("*");
        }
        return asterisks.toString();
    }

//    public static String sanitizeMessage(String message) {
//        List<String> bannedWords = getBannedWords();
//        String[] words = message.split("\\s+");
//        for (int i = 0; i < words.length; i++) {
//            String word = words[i].toLowerCase();
//            if (bannedWords.contains(word)) {
//                StringBuilder asterisks = new StringBuilder();
//                for (int j = 0; j < word.length(); j++) {
//                    asterisks.append("*");
//                }
//                words[i] = asterisks.toString();
//            }
//        }
//        StringBuilder sanitizedMessage = new StringBuilder();
//        for (String word : words) {
//            sanitizedMessage.append(word).append(" ");
//        }
//        return sanitizedMessage.toString().trim();
//    }

}
