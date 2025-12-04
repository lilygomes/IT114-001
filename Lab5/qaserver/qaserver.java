import java.util.*;
import java.net.*;
import java.io.*;

/////////////////////////////////////////////////////////////////////
//
// A server that provides answers to some questions sent
// from a client (in abbreviated form).  The question/answer
// pairs are stored in the file qa.txt, one per line.
// Each question and answer are separated by a semicolon.
// The questions and answers are loaded into a map.
//
// Note that the desired port number is supplied as the
// first argument on the command-line.
//
// USAGE: java qaserver <PORT>
//
// where <PORT> is the desired port #.
//
// Author: M. Halper
//
/////////////////////////////////////////////////////////////////////

public class qaserver
{

   public static final String USAGE = "USAGE: java qaserver <PORT>";
   public static final String QA_FILE = "qa.txt";

   public static void main(String[] args)
   {

       // Use a map to hold the question/answer pairs 

       Map<String, String> qa_map; 

       ServerSocket serverSocket;
       Socket socket;
       String inputLine;
       String outputLine;
       String answer;
       boolean finished;
       int port;
       String qa_line;
       String[] qa_pair; 

     try
     {
	// use the port indicated on command-line
	
	   port = Integer.parseInt(args[0]);

        // create a server socket

           serverSocket = new ServerSocket(port);

        // Listen for a connection request from a client

           socket = serverSocket.accept();

        // Establish the input and output streams on the socket
       
           PrintWriter out = new
                     PrintWriter(socket.getOutputStream(), true);
           Scanner in = new Scanner(new
	           InputStreamReader(socket.getInputStream())); 
 
        // load the q/a map from the file

              File infile = new File(QA_FILE);
	      Scanner fsc = new Scanner(infile);

	      qa_map = new HashMap<String, String>();

	     while(fsc.hasNext())
	     {

	         qa_line = fsc.nextLine();

	      // extract the current q/a pair

                 qa_pair = qa_line.split(";");

              // put this q/a pair in the map;
	      // trim leading and trailing whitespace.
	      // Also, normalize the key to all lowercase

	         qa_map.put(qa_pair[0].trim().toLowerCase(), qa_pair[1].trim());
	     
	     }

        // Keep answering questions until the client wants to quit

                finished = false;

             while(!finished)
             {
              // get a string from the client

                 inputLine = in.nextLine();

              // convert it to lowercase (note: keys are lowercase)
              
                 inputLine = inputLine.toLowerCase();

              // If it's "quit," we're done
             
                 if(inputLine.equals("quit"))

                               finished = true;
 
                 else
	         {
                  // Produce the appropriate response to the
                  // client's question.  Try to get the answer
                  // from the qa_map.  If it's not there
                  // (i.e., answer comes back null), then
                  // it is an unknown question

		     answer = qa_map.get(inputLine);

		     outputLine = (answer != null)? answer : "Error: unknown question";

                  // send the response to the client

                     out.println(outputLine);

                 }
              }

              out.close();
              in.close();
              socket.close();
              serverSocket.close();

     }
     catch(IOException e)  // socket problems
     {
        System.out.println(e);
     }

     catch(NumberFormatException e)  // port not a number (int)
     {
	System.out.println("First argument must be the port number.");
	System.out.println(USAGE);
     }

     catch(ArrayIndexOutOfBoundsException e)  // no port # given
     {
	System.out.println("Need to supply the port number.");
	System.out.println(USAGE);
     }

   } // end main

} // end qaserver

