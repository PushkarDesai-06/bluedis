public class PutDTO<K, V> {
  boolean alreadyPresent;
  boolean isOk;
  Value<V> value;

  public PutDTO(boolean alreadyPresent, boolean isOk, Value value) {
    this.alreadyPresent = alreadyPresent;
    this.isOk = isOk;
    this.value = value;
  }

}