import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StringValue implements Value<String> {
  String value;
  LocalDateTime ttl;

  public StringValue(String value) {
    this.value = value;
  }

  public StringValue(String value, int seconds) {
    this.value = value;
    this.ttl = LocalDateTime.now().plus(seconds, ChronoUnit.SECONDS);
  }

  @Override
  public String getValue() {
    return this.value;
  }

  public String toString() {
    return value;
  }

  @Override
  public long getTTL() {
    if (ttl == null)
      return -1;

    LocalDateTime now = LocalDateTime.now();

    if (now.compareTo(ttl) > 0)
      return -1;

    Duration d = Duration.between(now, ttl);

    return d.getSeconds();
  }

  public boolean isExpired() {
    if (ttl == null)
      return false;

    LocalDateTime now = LocalDateTime.now();

    return now.isEqual(ttl) || now.isAfter(ttl);
  }

  public void setExp(long seconds) {
    this.ttl = LocalDateTime.now().plus(seconds, ChronoUnit.SECONDS);
  }

}
