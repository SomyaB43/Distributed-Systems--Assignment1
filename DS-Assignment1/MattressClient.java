import java.io.*;
import java.net.*;

public class MattressClient {

    public static void main(String[] args) {
        try {

            /*A client socket is created to connect to a server on PORT 3500.
            The client socket can read and write data to the server socket. */

            Socket socket = new Socket("localhost", 3500);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); 

   
            System.out.println("Welcome to the Mattress Store!");

            // Read and display available mattresses, once all the mattresses are displayed, allow the user to make their selection. 
            String line;
            while (!(line = dis.readUTF()).equals("DONE")) {
                System.out.println(line);
            }

            String input;
            while (true) {
                // Get user input for mattress type
                System.out.print("Enter the type of mattress you would like to buy (TWIN, QUEEN, KING): ");
                input = consoleReader.readLine();

                // Send user's choice to server
                dos.writeUTF(input);

                // Read and display server response for mattress selection
                String userResponse = dis.readUTF();
                System.out.println(userResponse);

                // Read and display server response for total price
                String price = dis.readUTF();
                System.out.println(price);

                //Create the Receipt for the Mattress Selection made by the user.
                String receiptChoice = "You have selected " + input + ". " + price;
                saveReceipt(receiptChoice);

                //Display the prompt for continuing
                String continuePrompt = dis.readUTF();
                System.out.println(continuePrompt);

                // Get user input for continuing
                String continueChoice = consoleReader.readLine().trim().toUpperCase(); 
                dos.writeUTF(continueChoice);

                // If the user chooses not to continue, break the loop
                if (!continueChoice.equals("Y")) {
                    String goodbyeMessage = dis.readUTF();
                    System.out.println(goodbyeMessage);
                    break; // Exit the loop
                }
            }

            // Close all of the resources
            dos.close();
            dis.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a receipt file in the client's folder
    private static void saveReceipt(String receiptContent) {
        try {
            String fileName = "receipt.txt";
            FileWriter writer = new FileWriter(fileName, true); 
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(receiptContent);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println("Your order receipt has been saved");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}
