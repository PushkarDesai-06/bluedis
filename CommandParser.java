public class CommandParser {

  public Pair<Actions, String[]> parse(String command) {
    if (command == null || command.equals("")) {
      return nonePair();
    }

    if (command.startsWith("GET")) {
      String key = command.substring(4).trim();

      if (key.length() == 0) {
        return nonePair();
      }

      return new Pair<Actions, String[]>(Actions.GET, new String[] { key });
    }

    if (command.startsWith("PUT")) {
      String[] parts = command.substring(4).trim().split("[,\\s\\.]+");
      String key = parts[0].trim();
      String value = parts[1].trim();

      if (key == "" || value == "")
        return nonePair();

      return new Pair<Actions, String[]>(Actions.PUT, new String[] { key, value });
    }

    if (command.startsWith("DEL")) {
      String key = command.substring(4).trim();

      if (key.length() == 0) {
        return nonePair();
      }

      return new Pair<Actions, String[]>(Actions.DELETE, new String[] { key });
    }

    return nonePair();

  }

  public Pair<Actions, String[]> nonePair() {

    return new Pair<Actions, String[]>(Actions.NONE, null);
  }

}
