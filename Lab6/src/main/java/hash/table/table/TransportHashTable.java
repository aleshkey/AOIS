package hash.table.table;


import hash.table.model.Transport;

public class TransportHashTable<K, V> {
    private int capacity;    // емкость таблицы
    private Transport<K, V>[] table;   // массив элементов таблицы

    public TransportHashTable(int capacity) {
        this.capacity = capacity;
        table = new Transport[capacity];
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = hash % capacity;
        Transport<K, V> transport = new Transport<K, V>(key, value);

        if (table[index] == null) {
            table[index] = transport;
        } else {
            // решение коллизии методом цепочек
            Transport<K, V> current = table[index];
            while (current != null) {
                if (current.getKey().equals(key)) {
                    current.setValue(value);
                    return;
                }
                current = current.getNext();
            }
            transport.setNext(table[index]);
            table[index] = transport;
        }
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = hash % capacity;
        Transport<K, V> current = table[index];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    public void remove(K key) {
        int hash = key.hashCode();
        int index = hash % capacity;

        if (table[index] == null) {
            return;
        } else {
            Transport<K, V> current = table[index];
            Transport<K, V> prev = null;

            while (current != null) {
                if (current.getKey().equals(key)) {
                    if (prev == null) {
                        table[index] = current.getNext();
                    } else {
                        prev.setNext(current.getNext());
                    }
                    return;
                }
                prev = current;
                current = current.getNext();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i =0; i< table.length; i++){
            if (table[i] != null)
                res.append(i).append(" ").append(table[i].toString()).append("\n");
        }
        return res.toString();
    }
}
