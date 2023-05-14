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
                    }
                    else {
                        p2Score = dataIn.readInt();
                        System.out.println("Other Score: " + p2Score);
                    }
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
                    if (playerId == 1) {
                        dataOut.writeInt(p1Score);    
                        dataOut.flush();
                    }
                    else {
                       dataOut.writeInt(p2Score);
                       dataOut.flush();
                    }
                    Thread.sleep (1000);
                }
                catch (Exception e) {
                    System.out.println ("Exception from WTC run()");
                }
            }
        }
      
      
