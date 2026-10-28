
import java.time.LocalDateTime;

public interface Key<T> {

  public T getKey();

  public LocalDateTime getTTL();

  public boolean getExp();

}
