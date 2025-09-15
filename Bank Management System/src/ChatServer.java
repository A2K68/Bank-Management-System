import java.io.*;
import java.net.*;

public class ChatServer {
    public static void startChat() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Waiting for a customer to connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Customer connected!");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            Thread receiveThread = new Thread(() -> {
                try {
                    String clientMessage;
                    while ((clientMessage = input.readLine()) != null) {
                        if (clientMessage.equalsIgnoreCase("exit")) {
                            System.out.println("Customer left the chat.");
                            break;
                        }
                        System.out.println("Customer: " + clientMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Customer disconnected.");
                }
            });
            receiveThread.start();

            String serverMessage;
            while ((serverMessage = keyboard.readLine()) != null) {
                output.println(serverMessage);
                if (serverMessage.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();
            System.out.println("Chat ended.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(Object o) {
    }
}
