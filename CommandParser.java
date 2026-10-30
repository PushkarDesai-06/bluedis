public class CommandParser {

  public Pair<Actions, String[]> parse(String command) {
    if (command == null || command.equals("")) {
      return nonePair();
    }

    String lower = command.toLowerCase();

    if (lower.startsWith("get ")) {
      String key = command.substring(4).trim();

      if (key.length() == 0) {
        return nonePair();
      }

      return new Pair<>(Actions.GET, new String[] { key });
    }

    if (lower.startsWith("put ")) {
      String[] parts = command.substring(4).trim().split("[,\\s\\.]+");
      String key = parts[0].trim();
      String value = parts[1].trim();

      if ("".equals(key) || "".equals(value))
        return nonePair();

      return new Pair<>(Actions.PUT, new String[] { key, value });
    }

    if (lower.startsWith("del ")) {
      String key = command.substring(4).trim();

      if (key.length() == 0) {
        return nonePair();
      }

      return new Pair<>(Actions.DELETE, new String[] { key });
    }

    if (lower.startsWith("ping")) {
      return new Pair<>(Actions.PING, null);
    }

    return nonePair();

  }

  public Pair<Actions, String[]> nonePair() {

    return new Pair<>(Actions.NONE, null);
  }

}
