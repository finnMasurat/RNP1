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

            while (true) {

                Socket connectionSocket = welcomeSocket.accept();

                System.out.println("Connected..");

                // Nachricht vom Client an den Server
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                // Nachricht vom Server an den Client
                DataOutputStream serverOutput = new DataOutputStream(connectionSocket.getOutputStream());

                String inputLine;
                String outputLine = "";

                inputLine = serverInput.readLine();

                //System.out.println("FROM CLIENT: " + inputLine);

                // Auf das Ende der Nachricht prüfen
                if (inputLine != null) {
                    byte[] utf8Bytes = inputLine.getBytes("UTF-8");

                    // Die Vorgegebene UTF-8 Maximallänge des Strings prüfen
                    if (utf8Bytes.length < 255) {

                        String[] inputArray = inputLine.split(" ");

                        switch (inputArray[0]) {
                            case "LOWERCASE":
                            case "UPPERCASE":

                                if (inputArray.length > 1) {
                                    for (int i = 1; i < inputArray.length; i++) {
                                        if (inputArray[0].equals("LOWERCASE")) {
                                            outputLine += " " + inputArray[i].toLowerCase();
                                        } else {
                                            outputLine += " " + inputArray[i].toUpperCase();
                                        }
                                    }
                                    serverOutput.writeBytes("OK" + outputLine + "\n");

                                } else {
                                    serverOutput.writeBytes("ERROR NO EMPTY STRING ALLOWED" + "\n");
                                }
                                break;

                            case "REVERSE":
                                if (inputArray.length > 1) {
                                    for (int i = inputArray.length - 1; i > 0; i--) {
                                        outputLine += " " + new StringBuilder(inputArray[i]).reverse().toString();
                                    }
                                    serverOutput.writeBytes("OK" + outputLine + "\n");
                                } else {
                                    serverOutput.writeBytes("ERROR NO EMPTY STRING ALLOWED" + "\n");
                                }
                                break;

                            case "BYE":
                                serverOutput.writeBytes("OK BYE" + "\n");
                                connectionSocket.close();
                                break;

                            default:
                                serverOutput.writeBytes("ERROR UNKNOWN COMMAND" + "\n");
                        }
                    } else {
                        serverOutput.writeBytes("ERROR STRING TOO LONG");
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
