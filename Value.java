
public interface Value<T> {

  public T getValue();

  @Override
  public String toString();

  public long getTTL();

  public void setExp(long seconds);

  public boolean isExpired();
}
