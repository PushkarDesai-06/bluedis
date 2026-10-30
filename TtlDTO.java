public record TtlDTO(boolean isFound, long seconds) {
  public static TtlDTO notFound() {
    return new TtlDTO(false, -1);
  }
}
