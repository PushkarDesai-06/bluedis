public record GetDTO<K, V>(boolean isFound, boolean isExpired, Value<V> value) {
  public static GetDTO notFound() {
    return new GetDTO<>(false, false, null);
  }

  public static GetDTO expired() {
    return new GetDTO<>(true, true, null);
  }

}