package hash.table.table;

import hash.table.model.Entry;

import java.util.LinkedList;

public class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int size;
    private LinkedList<Entry<K, V>>[] table;

    public HashTable() {
        table = new LinkedList[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public void put(K key, V value) {
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;
    }

    public V remove(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                bucket.remove(entry);
                size--;
                return entry.getValue();
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % table.length;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }

        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                int newIndex = (entry.getKey().hashCode() & 0x7fffffff) % newCapacity;
                newTable[newIndex].add(entry);
            }
        }

        table = newTable;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        int counter = 0;
        for(int i =0; i< table.length; i++) {
            if (!table[i].isEmpty()) {
                for (var elem : table[i]) {
                    counter++;
                    res.append(String.format("%5s",counter)).append(" ").append(String.format("%5s",i)).append(" ").append(elem.toString()).append("\n");
                }
            }
        }
        return res.toString();
    }
}