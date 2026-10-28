/**
 * Main
 */

public class Main {

  public static void main(String[] args) {
    int PORT = 9000;
    Bluedis<String, String> db = new Bluedis<>(PORT);
    db.listen();
  }

}
