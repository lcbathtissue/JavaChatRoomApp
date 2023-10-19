# Chatroom Application Readme

## Introduction

The Chatroom Application is a simple yet powerful Java-based Client-Server chat service designed for facilitating real-time messaging and user interaction. This application enables users to communicate within a chatroom environment securely, while also providing content filtering capabilities. Below, you'll find an overview of the application's components and how to use it.

## Running the Application

To run the Chatroom Application, follow these steps:

1. **Clone the Repository**:

    You can clone the repository from GitHub using the following command:

    ```shell
    git clone https://github.com/lcbathtissue/JavaChatRoomApp
    ```

2. **Build the Project**:

    Navigate to the root directory of the cloned project using your terminal and use Maven to build the project:

    ```shell
    cd JavaChatRoomApp
    mvn clean install
    ```

3. **Start the Server**:

    Update the `pom.xml` file with the `<mainClass>` for the `AppServer` class:

    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.0.0</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>java</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <mainClass>com.aldenocain.chatroomapp.AppServer</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
    ```

    Once you've updated the `pom.xml`, you can start the server by running:

    ```shell
    mvn exec:java
    ```

4. **Start the Client**:

    Open a new terminal window and navigate to the project directory again. Make sure the server is running.

    Update the `pom.xml` file with the `<mainClass>` for the `AppClient` class:

    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.0.0</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>java</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <mainClass>com.aldenocain.chatroomapp.AppClient</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
    ```

    Start the client by running:

    ```shell
    mvn exec:java
    ```

    You can now use the client terminal to log in, interact with the chatroom, and enjoy real-time messaging.

Please note that this application is designed for demonstration purposes and may require further development for production use.

## Viewing the SQLite Database

This Chatroom Application uses an SQLite database to store user information and chat messages. To conveniently view the contents of the SQLite database, you can use a Python-based database viewer included in this project.

### Prerequisites

Before using the SQLite database viewer, ensure that you have Python installed on your system.

### Using the SQLite Database Viewer

1. **Start the Database Viewer**:

    To view the contents of the SQLite database, you can run the provided `.bat` file, `start_SQLite_DB_viewer.bat`, which will execute the `SQLiteDB_viewer.py` Python script. Simply double click the batch file if you are on windows to open the terminal, or run the script on your own. Pressing the enter button will refresh the data on the screen if you want to view any changes made in the database.

2. **View Database Contents**:

    Once you've executed the `.bat` file, you'll see the database contents displayed in your terminal window. The viewer organizes the tables and their respective columns for easy inspection.

    Example output:
    
    ```
    messages
      - (id, sender_id, message_text, timestamp)
      - (1, 1, 'Hello, SQLite!', '2023-10-12 00:41:26')
      - (2, 4, 'This is a message that contains a banned word.. DurhamCollege', '2023-10-12 00:41:45')
      - (3, 4, 'This is a message that contains a banned word.. DurhamCollege', '2023-10-12 00:49:50')
      - (4, 4, 'This is a message that contains a banned word.. DurhamCollege', '2023-10-12 01:04:41')
      - (5, 4, 'This is a message that contains a banned word.. DurhamCollege', '2023-10-12 01:05:35')
    
    users
      - (UserID, Username, Password, Email, Role)
      - (1, 'user001', '1234', 'john@gmail.com', None)
      - (2, 'user102', '5678', 'david@hotmail.com', None)
      - (3, 'user203', '9012', 'benjamin@yahoo.com', None)
      - (4, 'user304', '3456', 'anything@example.com', None)
      - (5, 'user405', '7890', 'another.one@domain.net', None)
    
    pinned_messages
      - (PinnedMessageID, MessageID, ChatroomID)
    
    banned_words
      - (BannedWordID, Word)
      - (1, 'DurhamCollege')
    ```

    This viewer provides an easy way to inspect the contents of the SQLite database, making it useful for debugging and analyzing data stored by the Chatroom Application.
    
3. **Exit the Viewer**:

    To close the database viewer, you can simply close the terminal window where it's running or type 'exit' to quit.

Please note that this viewer is a useful tool for visualizing the database's structure and data. It allows you to see user information, messages, pinned messages, and banned words stored in the SQLite database.

## Components

### Server - `AppServer.java`

The server component is responsible for handling incoming connections, authenticating users, and managing the chatroom. Here's a breakdown of its key functionalities:

- **Connection Handling**: The server listens on a specified port (default is 3500) for incoming client connections. It allows up to 5 simultaneous client connections.

- **User Authentication**: The server authenticates users through the login process, ensuring that only authorized individuals can participate in the chat. Users can either log in with existing accounts or create new ones.

- **Chatroom Management**: Once authenticated, users are welcomed into the chatroom environment, where they can send and receive messages in real-time. The server handles message filtering and pinning operations.

- **Message Filtering**: Messages sent by users are filtered for banned words. If a message contains prohibited content, it is sanitized before being displayed.

### Client - `AppClient.java`

The client component allows users to connect to the server, interact with the chatroom, and perform the following actions:

- **Main Menu**: The client presents users with a main menu that allows them to choose between logging in with an existing account, creating a new account, or exiting the application.

- **User Authentication**: Users can log in with their existing account by providing their username and password. Successful authentication grants them access to the chatroom. If the login fails, users receive a notification.

- **Account Registration**: Users can create a new account by specifying a unique username, password, and email address. The server verifies the availability of the username. If registration is successful, users are granted access to the chatroom.

- **Chatroom Interaction**: Authenticated users can send messages to the chatroom. Messages are screened for banned words, and if found, the message is sanitized before being displayed. Users can engage in real-time conversations with other connected users.

## Usage

1. **Server Setup**:
    - Run the `AppServer` class to start the server.
    - The server listens on port 3500 by default, but you can configure it by modifying the `SERVER_PORT` variable in the `AppServer` class.

2. **Client Setup**:
    - Run the `AppClient` class to start the client.
    - Follow the prompts to log in with an existing account or create a new one.

3. **Chatroom Interaction**:
    - Upon successful authentication, users can send and receive messages within the chatroom.

4. **Banned Words and Filtering**:
    - Messages containing banned words are sanitized, and a warning is displayed.

5. **User Management**:
    - The application supports user login and registration, and user information is stored in an SQLite database.

## Important Notes

- This application is designed for demonstration purposes and may require further development for production use.

- The application uses SQLite for local data storage, so all data is stored in a local database. 

- Banned words can be configured in the database for moderation.

- The client and server are designed for single-client use but can be extended for multi-client use in a real-world scenario.

## Conclusion

The Chatroom Application provides a foundation for building a simple and secure chatroom service. Users can easily engage in real-time conversations while benefiting from content filtering. Further development and customization can expand its capabilities to meet specific needs.
