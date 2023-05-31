/*
Brief Description of this Class:
GameServer is the class responsible for handling the networking. Whether through aws or localhost, this class is able to write data such as 
player IDs, seeds for the random number generator, and the scores of the other player. This class also recieves scores from the player. 
*/
/**
CSCI22 Final Project - Animated Scene
@author Ronald Francis Bautista & Ian Roque Ferol
@version May 15, 2023
**/
/*
We have not discussed the Java language code in our program 
with anyone other than our instructor/s or the teaching assistants 
assigned to this course.
We have not used Java language code obtained from another student, 
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program 
was obtained from another source, such as a textbook or website, 
that has been clearly noted with a proper citation in the comments 
of our program.
*/
/*
Certificate of Authorship:
We hereby certify that the submission described in this document abides by 
the principles stipulated in the DISCS Academic Integrity Policy document.
We further certify that we are the authors of this submission and that any assistance 
We received in its preparation is fully acknowledged and disclosed in the documentation.
*/

import java.io.*;
import java.net.*;
import java.util.Random;  //generate random numbers


public class GameServer <ReadFromClient, WriteToClient> {

    private int numPlayers, maxPlayers, enemies, p1Score, p2Score;
    private String s1, s2;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerSocket ss;
    private Socket s, p1Socket, p2Socket; 
    private ReadFromClient p1ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p2WriteRunnable;
    private Random random;
    private long seed;
    private double enemiesx, p1x, p1y, p2x, p2y;
    private boolean p1Running, p2Running;

    public GameServer () {
        System.out.println("===== GAME SERVER =====");
        numPlayers = 0;
        maxPlayers = 2;
        random = new Random();
        seed = random.nextLong(); //binibigyan ng random seed/number


        try{
            ss = new ServerSocket(45371);
        }catch(IOException ex) {
            System.out.println("IOException from GameServer constructor");
        }
    }
    public void acceptConnections(){  //encapsulate accepting connections
        try{
            System.out.println("Waiting for connections...");
            
            while (numPlayers < maxPlayers ) {
                s = ss.accept(); //server to begin accepting connections
                DataInputStream in = new DataInputStream(s.getInputStream());  //this allows you to read data from an input source or the network connection
                DataOutputStream out = new DataOutputStream(s.getOutputStream()); // allows you to send different types of data over a network connection 

                numPlayers++;   //incrementing players
                out.writeInt(numPlayers); //out is a data output stream. writeInt allows to send an integer to the client
                out.writeLong(seed);
                System.out.println ("Player # " + numPlayers + " has joined."); 

                ReadFromClient rfc = new ReadFromClient(numPlayers, in); 
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1){
                    p1Socket = s;
                    p1ReadRunnable = rfc; 
                    p1WriteRunnable = wtc;
                }
                else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();
                    Thread readThread1 = new Thread (p1ReadRunnable);
                    Thread readThread2 = new Thread (p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    Thread writeThread1 = new Thread (p1WriteRunnable);
                    Thread writeThread2 = new Thread (p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }

            System.out.println("Maximum # of players reached. No longer accepting connections");

        } 
        catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    private class ReadFromClient implements Runnable {

        private int playerId;
        private DataInputStream dataIn;

        public ReadFromClient (int pid, DataInputStream in) {
            playerId = pid;
            dataIn = in;
            System.out.println("RFC Runable for Player " + playerId + " Created");
        }
        public void run () {
            while (true) {
                try {
                    if (playerId == 1) {
                        p1Score = dataIn.readInt();
                        System.out.println("p1 Score: " + p1Score);                    
                    }
                    else {
                        p2Score = dataIn.readInt();
                        System.out.println("p2 Score: " + p2Score);
                    }
                }
                catch(Exception ex) {
                    System.out.println("Exception from RFC run()");
                    try {
                        s.close();
                    }
                    catch (Exception e) {
                        System.out.println("Exception from socket close");
                    }
                }
            }
        }
    }
    
    private class WriteToClient implements Runnable{
        private int playerId;   
        private DataOutputStream dataOut;
        
        public WriteToClient (int pid, DataOutputStream out){
            playerId = pid;
            dataOut = out;
            System.out.println("WTC Runnable for Player " + playerId + " Created");
        }        
        public void run () {
            while (true) {
                try {
                    if (playerId == 1) {
                        dataOut.writeInt(p2Score);    
                        dataOut.flush();
                    }
                    else {
                        dataOut.writeInt(p1Score);
                        dataOut.flush();
                    }
                    Thread.sleep (1000);
                }
                catch (Exception e) {
                    System.out.println ("Exception from WTC run()");
                    try {
                        s.close();
                    }
                    catch (Exception ex) {
                        System.out.println("Exception from socket close");
                    }
                }
            }
        }
        public void sendStartMsg () {
            try {
                dataOut.writeUTF("Both players have started the game. Go!");
            }
            catch(IOException ex) {
                System.out.println("IOException from sendStartMsg()");

            }
        }

    }
        
        
    public static void main(String[] args){
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}

