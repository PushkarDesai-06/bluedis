import java.util.concurrent.ConcurrentMap;

public class KeyValueStore<KeyType, ValueType> {

  ConcurrentMap<KeyType, Value<ValueType>> map;

  public KeyValueStore(ConcurrentMap<KeyType, Value<ValueType>> map) {
    this.map = map;
  }

  public GetDTO<KeyType, ValueType> get(Key<KeyType> key) {
    try {

      Value<ValueType> value = map.computeIfPresent(key.getKey(), (k, v) -> {
        if (v.isExpired()) {
          return null;
        }

        return v;
      });

      if (value == null)
        return GetDTO.notFound();

      return new GetDTO<>(true, false, value);
    } catch (Exception e) {
      System.out.println(e);
      return GetDTO.notFound();
    }
  }

  public PutDTO<KeyType, ValueType> put(Key<KeyType> key, Value<ValueType> value) {
    Value<ValueType> prev = map.put(key.getKey(), value);

    return new PutDTO<>(prev != null && prev == value, true, value);
  }

  public DeleteDTO<KeyType, ValueType> delete(Key<KeyType> key) {
    Value<ValueType> value = null;

    Value<ValueType> prev = map.remove(key.getKey());

    return new DeleteDTO<>(prev != null, true, value);
  }

  public ExpDTO exp(Key<KeyType> key, long seconds) {
    if (seconds < 0)
      return ExpDTO.invalid();

    Value<ValueType> prev = map.computeIfPresent(key.getKey(), (key1, val1) -> {
      val1.setExp(seconds);
      return val1;
    });

    if (prev == null)
      return ExpDTO.notFound();

    return new ExpDTO(true, true);
  }

  public TtlDTO ttl(Key<KeyType> key) {
    Value<ValueType> value = map.getOrDefault(key.getKey(), null);
    if (value == null)
      return TtlDTO.notFound();

    return new TtlDTO(true, value.getTTL());
  }
}
