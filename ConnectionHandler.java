import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionHandler {
  int threadCount;
  int PORT;
  CommandParser commandParser;
  ExecutorService threadpool;
  KeyValueStore<String, String> store;

  public ConnectionHandler(int threadCount, int PORT, CommandParser commandParser,
      KeyValueStore<String, String> store) {
    this.threadCount = threadCount;
    this.PORT = PORT;
    this.commandParser = commandParser;
    this.store = store;

    initializeThreadpool();
  }

  public final void initializeThreadpool() {

    if (threadCount <= 0) {
      threadpool = Executors.newCachedThreadPool();
      System.out.println("Dynamic Thread Pool initiated");
    } else {
      threadpool = Executors.newFixedThreadPool(threadCount);
      System.out.println("Fixed Thread Pool initiated with count : " + threadCount);
    }
  }

  public void handleConnections() {

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server is up and listening on port : " + PORT);

      while (true) {
        Socket sock = serverSocket.accept();
        threadpool.submit(() -> {
          try {
            handleConnection(sock);
          } catch (IOException ex) {
            System.getLogger(ConnectionHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
          }
        });
        System.out.println("Submitted " + sock.getPort() + " to Threadpool");
      }

    } catch (IOException e) {
      System.getLogger(ConnectionHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void handleConnection(Socket connection) throws IOException {
    try {

      InputStream input = connection.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(input));
      while (true) {
        String data = br.readLine();
        if (data == null || data.isBlank()) {
          try (connection) {
            System.out.println("Closing Connection " + connection.getPort());
          }
          return;
        }

        Pair<Actions, String[]> parsed = commandParser.parse(data);

        System.out.println(parsed.getFirst() + " | " + parsed.getSecond()[0] + " | "
            + (parsed.getSecond().length == 2 ? parsed.getSecond()[1] : ""));

        if (parsed.getFirst() == Actions.NONE)
          continue;

        if (null == parsed.getFirst()) {
          sendRaw(connection, "WRONG_COMMAND");
        } else
          switch (parsed.getFirst()) {
            case GET -> {
              GetDTO<String, String> dto = store.get(new StringKey(parsed.getSecond()[0]));
              if (!dto.isFound) {
                sendNotFound(connection);
              } else {
                sendValue(connection, dto.value);
              }
            }
            case PUT -> {
              StringKey key = new StringKey(parsed.getSecond()[0]);
              StringValue value = new StringValue(parsed.getSecond()[1]);
              System.out.println(value.getValue());

              PutDTO<String, String> dto = store.put(key, value);

              if (!dto.isOk) {
                sendNotFound(connection);
              } else {
                sendValue(connection, dto.value);
              }
            }
            case DELETE -> {
              DeleteDTO<String, String> dto = store.delete(new StringKey(parsed.getSecond()[0]));
              if (!dto.isPresent) {
                sendNotFound(connection);
              } else {
                sendValue(connection, new StringValue(parsed.getSecond()[0]));
              }
            }
            case PING -> sendText(connection, "PONG");
            default -> sendText(connection, "WRONG_COMMAND");
          }
      }

    } catch (IOException e) {
      connection.close();
      System.out.println(e);
    }
  }

  public void sendNotFound(Socket connection) {
    sendRaw(connection, "NOT_FOUND");
  }

  public void sendValue(Socket connection, Value<String> value) {
    sendRaw(connection, value.getValue());
  }

  public void sendText(Socket connection, String text) {
    sendRaw(connection, text);
  }

  public void sendRaw(Socket connection, String s) {
    try {
      PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
      out.println(s);
    } catch (IOException ex) {
      System.getLogger(ConnectionHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }

  }

}
