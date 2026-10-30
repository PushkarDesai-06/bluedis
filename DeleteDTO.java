public class DeleteDTO<K, V> {
  boolean isPresent;
  boolean deleted;

  Value<V> deletedValue;

  public DeleteDTO(boolean isPresent, boolean deleted, Value<V> deletedValue) {
    this.isPresent = isPresent;
    this.deleted = deleted;
    this.deletedValue = deletedValue;
  }
}
