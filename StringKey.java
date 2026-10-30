import java.time.LocalDateTime;

public class StringKey implements Key<String> {

  private String key;

  public StringKey(String key) {
    this.key = key;
  }

  public StringKey(String key, LocalDateTime TTL, boolean hasExpired) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }
}
