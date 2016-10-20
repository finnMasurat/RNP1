import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket welcomeSocket = new ServerSocket(6432);
            Socket connectionSocket = new Socket();
            connectionSocket = welcomeSocket.accept();

            System.out.println("Connected..");

            // Nachricht vom Client an den Server
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            // Nachricht vom Server an den Client
            DataOutputStream clientOutput = new DataOutputStream(connectionSocket.getOutputStream());

            String inputLine;

            System.out.println("Read from Client: ");
            while((inputLine = serverInput.readLine()) != null ) {
                System.out.println(inputLine);

                String[] inputArray = inputLine.split(" ");

                switch (inputArray[0]) {
                    case "BYE":
                        System.out.println("OK BYE");
                        connectionSocket.close();
                        break;

                }

            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
