package com.example.project2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GenomeServer {
    public static final String USAGE = "USAGE: java server <PORT>";
    public static final String VALID_BASES = "ACGT";

    public static void main(String[] args) {
        // Server parameters
        ServerSocket serverSocket;
        Socket socket;
        PrintWriter out;
        Scanner in;
        int port;
        boolean finished = false;

        // Connection loop: keep reopening socket until finished
        while (!finished) {
            // Connection initialisation
            try {
                // Open socket
                port = Integer.parseInt(args[0]);
                serverSocket = new ServerSocket(port);
                System.out.println("Now listening on " + serverSocket.getLocalSocketAddress());
                socket = serverSocket.accept();
                // Create I/O streams
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new Scanner(socket.getInputStream());
                // Status message
                System.out.printf("Connected %s:%d -> %s:%d\n",
                        socket.getLocalSocketAddress(),
                        socket.getLocalPort(),
                        socket.getRemoteSocketAddress(),
                        socket.getPort()
                );

                // Variables for input and output
                String operation, genome, sequence, response = "";
                int invalidBaseIndex;

                // Client loop: keep processing client requests while connection open
                while (in.hasNextLine()) {
                    operation = in.nextLine();
                    genome = in.nextLine().toUpperCase();
                    sequence = in.nextLine().toUpperCase();
                    System.out.println("request: " + operation + ", " + genome + ", " + sequence );

                    // Validate provided sequences
                    invalidBaseIndex = validateBases(genome);
                    if (invalidBaseIndex != -1) {
                        response = "Invalid base '" +
                                genome.charAt(invalidBaseIndex) +
                                "' in genome at index " +
                                invalidBaseIndex;
                        // Ensure previous operation is not undertaken
                        operation = "error";
                    }
                    invalidBaseIndex = validateBases(sequence);
                    if (invalidBaseIndex != -1) {
                        response = "Invalid base '" +
                                genome.charAt(invalidBaseIndex) +
                                "' in sequence at index " +
                                invalidBaseIndex;
                        // Ensure previous operation is not undertaken
                        operation = "error";
                    }
                    // Perform selected operation
                    switch (operation) {
                        case "occur":
                            int occurrences = countOccurrences(genome, sequence);
                            response = occurrences +
                                    " occurrence" +
                                    (occurrences > 1 ? "s" : "") + // occurrence(s)
                                    " of " +
                                    sequence + "found";
                            break;
                        case "align":
                            response = genome + "\n" + alignSequence(genome, sequence);
                            break;
                        case "error":
                            break;
                        default:
                            response = "Invalid operation specified: \"" + operation + "\"";
                            break;
                    }

                    // Respond back to client
                    System.out.println("response: " + response);
                    out.println(response);
                }

                // Close I/O streams when client loop ends
                out.close();
                in.close();
                socket.close();
            }
            // catch: no port specified
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("server: no port specified");
                System.out.println(USAGE);
                finished = true;
            }
            // catch: port argument is not a number
            catch (NumberFormatException e) {
                System.out.println("server: invalid port specified");
                System.out.println(USAGE);
                finished = true;
            }
            // catch: server errors
            catch (IOException e) {
                System.out.println("server: " + e.getMessage());
            }
            // catch: client disconnect
            catch (NoSuchElementException e) {
                System.out.println("No response from client");
                // Close I/O streams and socket
            }
        } // end connection loop
    }

    /**
     * Checks for unrecognised bases in sequence.
     * @param sequence Sequence of bases to validate.
     * @return index of malformed base, -1 if sequence is valid
     */
    private static int validateBases(String sequence) {
        for (int i = 0; i < sequence.length(); i++)
            // If base at sequence[i] not in valid list, return index of invalid base
            if (VALID_BASES.indexOf(sequence.charAt(i)) == -1)
                return i;
        // Otherwise, return -1
        return -1;
    }

    /**
     * Finds the amount of times a sequence occurs in a genome.
     * @param genome Genome to search in
     * @param sequence Sequence to search for
     * @return count of occurrences
     */
    private static int countOccurrences(String genome, String sequence) {
        // If sequence occurs in genome, count = 1 + occurrences in rest of genome
        int index = genome.indexOf(sequence);
        return index > -1 ?
                1 + countOccurrences(genome.substring(index + sequence.length()), sequence) :
                0;
    }

    /**
     * Returns aligned nucleobase sequence to genome
     * @param genome Genome to align to
     * @param sequence Sequence of bases to align
     * @return Aligned sequence, padded with '-'
     */
    private static String alignSequence(String genome, String sequence) {
        int offset = genome.indexOf(sequence);
        // If sequence not found in genome, return error message
        return offset > -1 ?
                "-".repeat(offset) + sequence + "-".repeat(genome.length() - sequence.length() - offset) :
                "No alignment possible.";
    }
}
