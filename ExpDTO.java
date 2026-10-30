public record ExpDTO(boolean isPresent, boolean isValid) {

  public static ExpDTO invalid() {
    return new ExpDTO(false, false);
  }

  public static ExpDTO notFound() {
    return new ExpDTO(false, true);
  }

}
