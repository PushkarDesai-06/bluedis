public class GetDTO<K, V> {
  boolean isFound;
  Value<V> value;

  public boolean getIsFound() {
    return isFound;
  }

  public Value<V> getValue() {
    return value;
  }

  public GetDTO(boolean isFound, Value<V> value) {
    this.isFound = isFound;
    this.value = value;
  }

  public static GetDTO notFound() {
    return new GetDTO<>(false, null);
  }
}