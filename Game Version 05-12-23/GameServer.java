import java.io.*;
import java.net.*;
import java.util.Random;  //generate random numbers


public class GameServer<ReadFromClient, WriteToClient> {
    private ServerSocket ss;
    private int numPlayers, maxPlayers, enemies, p1Score, p2Score;
    private Socket p1Socket, p2Socket; 
    private ReadFromClient p1ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p2WriteRunnable;
    private Random random;
    private long seed;
    private double enemiesx, p1x, p1y, p2x, p2y;
    private boolean p1Running, p2Running;

    public GameServer(){
        System.out.println("===== GAME SERVER =====");
        numPlayers = 0;
        maxPlayers = 2;
        p1Score = 0;
        p2Score = 0;
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
                Socket s = ss.accept(); //server to begin accepting connections
                DataInputStream in = new DataInputStream(s.getInputStream());  //this allows you to read data from an input source or the network connection
                DataOutputStream out = new DataOutputStream(s.getOutputStream()); // allows you to send different types of data over a network connection 

                numPlayers++;   //incrementing players
                out.writeInt(numPlayers); //out is a data output stream. writeInt allows to send an integer to the client
                out.writeLong(seed);
                System.out.println ("Player # " + numPlayers + "has joined."); 

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
                    Thread readThread1 = new Thread (p1WriteRunnable);
                    Thread readThread2 = new Thread (p2WriteRunnable);
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
                    p1Score = dataIn.readInt();
                    p2Score = dataIn.readInt();
                    System.out.println("p1: " + p1Score + "; p2: " + p2Score);
                    Thread.sleep (1000);
                }
                catch(Exception ex) {
                    System.out.println("Exception from RFC run()");
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
                    //dataOut.writeInt (p1Score);
                    //dataOut.writeInt (p2Score);
                    dataOut.flush();
                    Thread.sleep (1000);
                }
                catch (Exception e) {
                    System.out.println ("Exception from WTC run()");
                }
                
            }

        }
        public void sendStartMsg(){
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
