import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionHandler {
  int threadCount;
  int PORT;
  CommandParser commandParser;
  ExecutorService threadpool;

  public ConnectionHandler(int threadCount, int PORT, CommandParser commandParser) {
    this.threadCount = threadCount;
    this.PORT = PORT;
    this.commandParser = commandParser;

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

    try {
      ServerSocket serverSocket = new ServerSocket(PORT);
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
        String parsed = commandParser.parse(data);
        System.out.println(parsed);
      }

    } catch (Exception e) {
      connection.close();
      System.out.println(e);
    }
  }

}
