package structures;

import java.lang.reflect.Array;

/**
 * A basic implementation of Associative Arrays with keys of type K and values of type V.
 * Associative Arrays store key/value pairs and permit you to look up values by key.
 *
 * @author Julian Kim
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({"unchecked"})
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs =
        (KVPair<K, V>[]) Array.newInstance((new KVPair<K, V>()).getClass(), DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clonedArr = new AssociativeArray<K, V>();
    for (KVPair<K, V> pair : pairs) {
      clonedArr.set(pair.key, pair.value);
    }
    return clonedArr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String returnStr = "{ " + pairs[0].key + ": " + pairs[0].value;
    for (int i = 1; i < size; i++) {
      returnStr += ", " + pairs[i].key + ": " + pairs[i].value;
    }
    returnStr += " }";
    return returnStr;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to get(key) will return value.
   */
  public void set(K key, V value) {
    int index;

    try {
      index = find(key);
    } catch (KeyNotFoundException e) {
      // expand if too small
      if (size >= pairs.length)
        this.expand();

      index = size++;
    }

    pairs[index] = new KVPair<K, V>(key, value);
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException when the key does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    return pairs[find(key)].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    try {
      find(key);

      return true;
    } catch (KeyNotFoundException e) {
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls to get(key) will throw an
   * exception. If the key does not appear in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      pairs[find(key)] = pairs[size - 1];
      pairs[--size] = null;
    } catch (KeyNotFoundException e) {
      // do nothing
    }
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  @SuppressWarnings("unchecked")
  public K[] getKeys() {
    K[] keys = (K[]) Array.newInstance(pairs[0].key.getClass(), size());

    for (int i = 0; i < size(); i++) {
      keys[i] = pairs[i].key;
    }

    return keys;
  }

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  private void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key. If no such entry is found,
   * throws an exception.
   */
  private int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < size; i++) {
      if (pairs[i].key.equals(key))
        return i;
    }
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray
