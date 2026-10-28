/**
 * Main
 */

public class Main {

  public static void main(String[] args) {
    int PORT = 9000;

    ConnectionHandler handler = new ConnectionHandler(-1, PORT, null);
    handler.handleConnections();
  }

}
