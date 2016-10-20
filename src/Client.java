import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("127.0.0.1",6432);

            // Nachricht vom Benutzer an den Client
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Nachricht vom Server an den Client
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Nachricht vom Client an den Server
            DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("Bitte Eingabe tätigen: ");
            String eingabe = userInput.readLine();

            clientOutput.writeBytes(eingabe + '\n');


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
