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
            DataOutputStream serverOutput = new DataOutputStream(connectionSocket.getOutputStream());

            String inputLine;
            String outputLine = "";

            while(true) {
                inputLine = serverInput.readLine();

                //System.out.println("FROM CLIENT: " + inputLine);

                if (inputLine != null) {
                    String[] inputArray = inputLine.split(" ");

                    switch (inputArray[0]) {
                        case "LOWERCASE":
                        case "UPPERCASE":
                            if (inputArray.length == 1) {
                                System.out.println("ERROR NO EMPTY STRING ALLOWED");
                            }
                            for (int i = 1; i < inputArray.length; i++) {
                                if (inputArray[0].equals("LOWERCASE")) {
                                    outputLine += " " + inputArray[i].toLowerCase();
                                } else {
                                    outputLine += " " + inputArray[i].toUpperCase();
                                }
                            }

                            System.out.println("Send to client:" + outputLine);
                            serverOutput.writeBytes("OK" + outputLine + "\n");

                            break;
                        case "REVERSE":
                            if (inputArray.length == 1) {
                                System.out.println("ERROR NO EMPTY STRING ALLOWED");
                            }
                            for (int i = inputArray.length - 1; i > 0; i --) {
                                outputLine += " " + new StringBuilder(inputArray[i]).reverse().toString();
                            }
                            System.out.println("Send to client: OK" + outputLine);
                            serverOutput.writeBytes(outputLine + "\n");

                            break;
                        case "BYE":
                            System.out.println("OK BYE");
                            connectionSocket.close();
                            break;
                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
