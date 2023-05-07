package hash.table.model;

import static java.lang.System.out;

public class Transport<K, V> {
    private K key;
    private V value;

    private Transport<K, V> next;

    public Transport(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Transport<K, V> getNext() {
        return next;
    }

    public void setNext(Transport<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return String.format("%10s -> %-10s", key, value);
    }
}
