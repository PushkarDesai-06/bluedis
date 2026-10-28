public class StringValue implements Value<String> {
  String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  public String toString() {
    return value;
  }

}
