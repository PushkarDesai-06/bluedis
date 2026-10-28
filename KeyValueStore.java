import java.util.concurrent.ConcurrentMap;

public class KeyValueStore<KeyType, ValueType> {

  ConcurrentMap<KeyType, Value<ValueType>> map;

  public KeyValueStore(ConcurrentMap<KeyType, Value<ValueType>> map) {
    this.map = map;
  }

  public GetDTO<KeyType, ValueType> get(Key<KeyType> key) {
    if (!map.containsKey(key.getKey())) {
      return GetDTO.notFound();
    }

    return new GetDTO<>(true, map.get(key.getKey()));
  }

  public PutDTO<KeyType, ValueType> put(Key<KeyType> key, Value<ValueType> value) {

    boolean alreadyPresent = map.containsKey(key.getKey());

    map.put(key.getKey(), value);

    return new PutDTO<>(alreadyPresent, true, value);
  }

  public DeleteDTO<KeyType, ValueType> delete(Key<KeyType> key) {
    boolean isPresent = false;
    boolean isRemoved = false;
    Value<ValueType> value = null;

    if (map.containsKey(key.getKey())) {
      isPresent = true;
      map.remove(key.getKey());
      value = map.get(key.getKey());
    }

    isRemoved = true;

    return new DeleteDTO(isPresent, isRemoved, value);
  }

}

class GetDTO<K, V> {
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
    return new GetDTO(false, null);
  }
}

class PutDTO<K, V> {
  boolean alreadyPresent;
  boolean isOk;
  Value<V> value;

  public PutDTO(boolean alreadyPresent, boolean isOk, Value value) {
    this.alreadyPresent = alreadyPresent;
    this.isOk = isOk;
    this.value = value;
  }

}

class DeleteDTO<K, V> {
  boolean isPresent;
  boolean deleted;

  Value<V> deletedValue;

  public DeleteDTO(boolean isPresent, boolean deleted, Value<V> deletedValue) {
    this.isPresent = isPresent;
    this.deleted = deleted;
    this.deletedValue = deletedValue;
  }
}
