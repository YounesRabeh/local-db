package tests;

import java.util.*;

public class IndexedSet<T, K> {
    private Map<K, Integer> keyMap;
    private Map<T, Integer> elementMap;
    private List<T> list;

    // Constructor to initialize maps and list
    public IndexedSet() {
        keyMap = new HashMap<>();
        elementMap = new HashMap<>();
        list = new ArrayList<>();
    }

    // Method to add an element to the set with a corresponding key
    public boolean add(K key, T element) {
        // Check if element or key is null, or if the key already exists
        if (element == null || key == null || keyMap.containsKey(key)) {
            return false; // Element already exists or key is null
        }
        // Check if the element already exists
        if (elementMap.containsKey(element)) {
            return false; // Element already exists
        }
        // Add the key and element to the maps
        keyMap.put(key, list.size());
        elementMap.put(element, list.size());
        list.add(element);
        return true;
    }

    // Method to remove an element from the set using its key
    public boolean remove(K key) {
        // Check if the key is null or doesn't exist
        if (key == null || !keyMap.containsKey(key)) {
            return false; // Key doesn't exist or is null
        }
        // Get the index of the element to remove
        int indexToRemove = keyMap.get(key);
        // Get the element to remove
        T elementToRemove = list.get(indexToRemove);
        // Get the last element
        T lastElement = list.get(list.size() - 1);
        // Replace the element to remove with the last element
        list.set(indexToRemove, lastElement);
        // Update the index of the last element
        keyMap.put((K) elementToRemove, indexToRemove);
        // Remove the last element
        list.remove(list.size() - 1);
        // Remove the key from the key map
        keyMap.remove(key);
        // Remove the element from the element map
        elementMap.remove(elementToRemove);
        return true;
    }

    // Method to get an element by its index
    public T get(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return list.get(index);
    }

    // Method to get an element by its key
    public T getByKey(K key) {
        if (!keyMap.containsKey(key)) {
            throw new NoSuchElementException("Key not found");
        }
        return list.get(keyMap.get(key));
    }

    // Method to get the size of the set
    public int size() {
        return list.size();
    }

    // Method to check if the set contains a given element
    public boolean contains(T element) {
        return elementMap.containsKey(element);
    }

    // Method to check if the set contains a given key
    public boolean containsKey(K key) {
        return keyMap.containsKey(key);
    }

    // Method to clear the set
    public void clear() {
        keyMap.clear();
        elementMap.clear();
        list.clear();
    }

    // Method to provide a string representation of the set
    @Override
    public String toString() {
        return list.toString();
    }

    // Main method for testing the class
    public static void main(String[] args) {
        IndexedSet<String, String> indexedSet = new IndexedSet<>();

        indexedSet.add("key1", "A");
        indexedSet.add("key2", "B");
        indexedSet.add("key3", "C");
        indexedSet.add("key2", "D"); // Adding duplicate key, will be ignored
        indexedSet.add("key4", "B"); // Adding duplicate value, will be ignored

        System.out.println("IndexedSet: " + indexedSet); // [A, B, C]

        indexedSet.remove("key2");

        System.out.println("IndexedSet after removing key2: " + indexedSet); // [A, C]

        System.out.println("Element at index 1: " + indexedSet.get(1)); // C
        System.out.println("Element with key 'key1': " + indexedSet.getByKey("key1")); // A

        System.out.println("Size of IndexedSet: " + indexedSet.size()); // 2
    }
}
