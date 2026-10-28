
import java.util.concurrent.ConcurrentHashMap;

public class Bluedis<KeyType, ValueType> {

  KeyValueStore<String, String> store;
  int PORT;

  public Bluedis(int PORT) {
    this.PORT = PORT;
    this.store = new KeyValueStore<>(new ConcurrentHashMap<>());
  }

  public void listen() {
    ConnectionHandler handler = new ConnectionHandler(-1, PORT, new CommandParser(), store);
    handler.handleConnections();
  }
}
