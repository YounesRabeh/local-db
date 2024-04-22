package tests;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class test2 {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        Set<Integer> set = new HashSet<>();

        // Insertion test
        addElement(list, set, 1);
        addElement(list, set, 2);
        addElement(list, set, 3);
        addElement(list, set, 1); // This will not be added because it's a duplicate

        System.out.println("LinkedList after insertion: " + list);
    }

    private static void addElement(LinkedList<Integer> list, Set<Integer> set, Integer element) {
        if (set.add(element)) {
            list.add(element);
        } else {
            System.out.println("Element " + element + " is a duplicate and was not added.");
        }
    }
}