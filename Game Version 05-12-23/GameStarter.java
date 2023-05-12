public class GameStarter {
    public static void main (String[] args) {
        GameFrame frame = new GameFrame();
        frame.setUp();
        frame.setUpTimersAndKeyBindings();
        //frame.connectToServer();
    }
    
}