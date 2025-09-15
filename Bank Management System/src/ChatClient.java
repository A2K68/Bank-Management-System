import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = input.readLine()) != null) {
                        System.out.println("Banker: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from banker.");
                }
            });

            receiveThread.start();

            String clientMessage;
            while ((clientMessage = keyboard.readLine()) != null) {
                output.println(clientMessage);
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting chat...");
                    break;
                }
            }

            socket.close(); // Close connection and return
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
        }
    }
}
