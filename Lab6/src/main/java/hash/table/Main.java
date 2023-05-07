package hash.table;

import hash.table.table.TransportHashTable;

public class Main {
    public static void main(String[] args) {
        TransportHashTable<String, String> table = new TransportHashTable<>(20);
        table.put("one", "Один");
        table.put("two", "Два");
        table.put("three", "Три");
        System.out.println(table);

        System.out.println(table.get("one"));
        System.out.println(table.get("two")); // 1
        System.out.println(table.get("four"));   // null

        table.remove("two");
        System.out.println(table.get("two"));    // null
    }
}