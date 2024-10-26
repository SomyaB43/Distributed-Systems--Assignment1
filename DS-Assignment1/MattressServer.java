import java.io.*;
import java.net.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MattressServer {

//The available sizes and fixed prices of the mattresses are declared as final global variables. 
    private static final String[] SIZE = {"TWIN", "QUEEN", "KING"};
    private static final double[] PRICE = {150.00, 250.00, 400.00};

    public static void main(String[] args) {

        /* A server socket listens for a client connection on PORT 3500. Once a client is connected, the server socket accepts the 
         * connection. An instance of the MattressThread is created to handle the client connection and process the client request.
         * This server can handle multiple clients. 
         */

        try {
            ServerSocket mattressSocket = new ServerSocket(3500);
            System.out.println("The Mattress Server has started.");
            while (true) {
                Socket clientSocket = mattressSocket.accept();
                System.out.println("Client is connected to the Mattress Server");
                MattressThread mattressThread = new MattressThread(clientSocket);
                mattressThread.start(); // This indicates starting a new thread for each client that starts a connection. 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class MattressThread extends Thread {
        private Socket socket;
    
        public MattressThread(Socket socket) {
            this.socket = socket;
        }
    
        public void run() {
            try {
                
                //Input and Output Streams are initialized.
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("Mattresses are available in the following sizes:");

                //Displays the various mattress sizes. 
    
                for (int i = 0; i < SIZE.length; i++) {
                    dos.writeUTF((i + 1) + ": " + SIZE[i] + " - $" + PRICE[i]);
                }
    
                dos.writeUTF("DONE");
    
                //This While loop is responsible for managing the client's selection/responses. 

                while (true) {
                    String userInput;
                    userInput = dis.readUTF();
                    boolean inputFound = false; //This determines if the user has selected a valid input. 
    
                    //Display's the response provided by the user.
                    for (int i = 0; i < SIZE.length; i++) {
                        if (SIZE[i].equalsIgnoreCase(userInput)) {
                            dos.writeUTF("You have selected a " + SIZE[i] + " mattress.");
                            dos.writeUTF("The total price is $" + PRICE[i]);
                            inputFound = true;
                            break;
                        }
                    }
    
                    if (!inputFound) {
                        dos.writeUTF("Invalid choice. Please enter a valid mattress type (TWIN, FULL, QUEEN).");
                    }
    
                    // Ask if the user wants to continue
                    dos.writeUTF("Do you want to make another selection? (Y/N)");
                    userInput = dis.readUTF().trim().toUpperCase();
    
                    if (userInput.equals("N")) {
                        dos.writeUTF("Thank you for visiting the Mattress Store. Goodbye!");
                        break; // Exit the loop and close the connection
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } 
            }
        }
    }

    