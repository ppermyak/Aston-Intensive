package ru.permyakov;

import java.util.ArrayList;
import java.util.Objects;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int capacity;
    private final float loadFactor;
    private Node<K, V>[] table;
    private int size = 0;

    public MyHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        initTable();
    }

    public MyHashMap(float loadFactor) {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = loadFactor;
        initTable();
    }

    public MyHashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        initTable();
    }

    public V get(Object key) {
        int index = hash(key);
        Node<K, V> firstNode = table[index];
        if (firstNode == null) {
            return null;
        } else {
            if (firstNode.key.equals(key)) {
                return firstNode.value;
            } else {
                Node<K, V> currentNode = firstNode;
                while (currentNode.next != null) {
                    currentNode = currentNode.next;
                    if (currentNode.key.equals(key)) {
                        return currentNode.value;
                    }
                }
            }
        }
        return null;
    }

    public void put(K key, V value) {
        Node<K, V>[] currentTable = table;
        int index = hash(key);
        Node<K, V> firstNode = currentTable[index];
        Node<K, V> newNode = new Node<>(index, key, value, null);

        if (firstNode == null) {
            currentTable[index] = newNode;
            size++;
            checkSize(size, currentTable);
        } else {
            if (firstNode.key.equals(newNode.key)) {
                firstNode.value = newNode.value;
            } else {
                Node<K, V> currentNode = firstNode;
                boolean isNewKey = true;
                while (currentNode.next != null) {
                    currentNode = currentNode.next;
                    if (currentNode.key.equals(newNode.key)) {
                        currentNode.value = newNode.value;
                        isNewKey = false;
                        break;
                    }
                }
                if (isNewKey) {
                    currentNode.next = newNode;
                    size++;
                    checkSize(size, currentTable);
                }
            }
        }
    }

    public void remove(Object key) {
        int index = hash(key);
        Node<K, V> firstNode = table[index];
        if (firstNode != null) {
                     if (firstNode.key.equals(key)) {
                table[index] = firstNode.next;
                size--;
            } else {
                Node<K, V> currentNode = firstNode;
                while (currentNode.next != null) {
                    Node<K, V> previousNode = currentNode;
                    currentNode = currentNode.next;
                    if (currentNode.key.equals(key)) {
                        previousNode.next = currentNode.next;
                        size--;
                        return;
                    }
                }
            }
        }
    }

    private void initTable() {
        table = (Node<K, V>[]) new Node[capacity];
    }

    private int hash(Object key) {
        return (key == null) ? 0 : key.hashCode() & (capacity - 1);
    }

    private void checkSize(int size, Node<K, V>[] currentTable) {
        if (size >= capacity * loadFactor)
            resize(currentTable);
    }

    private void resize(Node<K, V>[] currentTable) {
        capacity *= 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[capacity];
        ArrayList<Node<K, V>> allNodes = new ArrayList<>();

        for (Node<K, V> firstNode : currentTable) {
            Node<K, V> currentNode = firstNode;
            while (currentNode != null) {
                allNodes.add(currentNode);
                currentNode = currentNode.next;
            }
        }

        for (Node<K, V> node : allNodes) {
            node.next = null;
            node.hash = hash(node.key);
            int index = node.hash;
            Node<K, V> firstNode = newTable[index];
            if (firstNode == null) {
                newTable[index] = node;
            } else {
                if (firstNode.equals(node)) {
                    firstNode.value = node.value;
                } else {
                    Node<K, V> currentNode = firstNode;
                    boolean isNewKey = true;
                    while (currentNode.next != null) {
                        currentNode = currentNode.next;
                        if (currentNode.equals(node)) {
                            currentNode.value = node.value;
                            isNewKey = false;
                            break;
                        }
                    }
                    if (isNewKey) {
                        currentNode.next = node;
                    }
                }
            }
        }

        table = newTable;
    }

    private static class Node<K, V> {
        int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node<?, ?> node)) return false;
            return Objects.equals(hash, node.hash) && Objects.equals(key, node.key);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
    }

}

























