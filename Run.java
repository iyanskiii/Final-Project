public class Run {
    public static void main (String[] args) {
        GameFrame frame = new GameFrame();
        frame.connectToServer();
        frame.setUp();
        frame.addKeyBindings();
    }
    
}
