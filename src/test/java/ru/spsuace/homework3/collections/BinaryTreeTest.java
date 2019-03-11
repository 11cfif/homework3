package ru.spsuace.homework3.collections;

import org.junit.Test;

import ru.spsuace.homework3.TestObject;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    @Test
    public void size() {
        BinaryTree<TestObject, Object> tree = new BinaryTree<>();
        assertEquals(0, tree.size());

        TestObject key1 = new TestObject("first");
        tree.put(key1, new Object());
        assertEquals(1, tree.size());

        TestObject key2 = new TestObject("second");
        tree.put(key2, new Object());
        assertEquals(2, tree.size());

        TestObject key3 = new TestObject("second");
        tree.put(key3, new Object());
        assertEquals(2, tree.size());

        tree.remove(key3);
        assertEquals(1, tree.size());

        tree.put(key3, new Object());
        assertEquals(2, tree.size());
    }

    @Test
    public void get() {

    }

    @Test
    public void put() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void rotation() {
    }
}