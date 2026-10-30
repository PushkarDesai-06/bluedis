import java.util.concurrent.ConcurrentMap;

public class KeyValueStore<KeyType, ValueType> {

  ConcurrentMap<KeyType, Value<ValueType>> map;

  public KeyValueStore(ConcurrentMap<KeyType, Value<ValueType>> map) {
    this.map = map;
  }

  public GetDTO<KeyType, ValueType> get(Key<KeyType> key) {
    Value<ValueType> value = map.getOrDefault(key.getKey(), null);

    if (value == null)
      return GetDTO.notFound();
    return new GetDTO<>(true, value);
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
}
