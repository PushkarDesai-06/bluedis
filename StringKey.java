import java.time.LocalDateTime;

public class StringKey implements Key<String> {

  private String key;
  private LocalDateTime TTL;
  private boolean hasExpired;

  public StringKey(String key, LocalDateTime TTL, boolean hasExpired) {
    this.key = key;
    this.TTL = TTL;
    this.hasExpired = hasExpired;
  }

  public boolean getHasExpired() {
    return this.hasExpired;
  }

  @Override
  public LocalDateTime getTTL() {
    return this.TTL;
  }

  @Override
  public String getKey() {
    return this.key;
  }

  @Override
  public boolean getExp() {
    return LocalDateTime.now().isBefore(TTL);
  }

}
