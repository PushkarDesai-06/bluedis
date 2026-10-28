
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main
 */

public class Main {

  public static void main(String[] args) {
    int PORT = 9000;
    ExecutorService threadpool = Executors.newCachedThreadPool();

    try {
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.out.println("Server is up and listening on port : " + PORT);

      while (true) {
        Socket sock = serverSocket.accept();
        threadpool.submit(() -> handleConnection(sock));
        System.out.println("Submitted " + sock.getPort() + " to Threadpool");
      }

    } catch (Exception e) {
      System.out.println(e);
    }

  }

  public static void handleConnection(Socket connection) {
    try {

      InputStream input = connection.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(input));
      while (true) {
        String data = br.readLine();
        if (data == null || data.equals(""))
          continue;

        System.out.println(
            connection.getPort() + ": " +
                data);

        if (data.startsWith("GET")) {
          System.out.println(connection.getPort());
        }
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }

}