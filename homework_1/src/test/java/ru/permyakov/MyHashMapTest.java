package ru.permyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MyHashMapTest {
    private final MyHashMap<Integer, String> myHashMap = new MyHashMap<>();

    @BeforeEach
    void mapInit() {
        myHashMap.put(1, "Ivan");
        myHashMap.put(2, "Nikolay");
        myHashMap.put(3, "Mikhail");
        myHashMap.put(4, "Sergey");
        myHashMap.put(5, "Roman");
        myHashMap.put(16, "Vladimir");
        myHashMap.put(26, "Dmitriy");
        myHashMap.put(10, "Andrey");
    }

    @Test
    void getByExistKeyShouldReturnCorrectValue() {
        assertEquals("Roman", myHashMap.get(5));
    }

    @Test
    void getByExistKeyWithCollisionShouldReturnCorrectValue() {
        assertEquals("Andrey", myHashMap.get(10));
    }

    @Test
    void getByNotExistKeyShouldReturnNull() {
        assertNull(myHashMap.get(15));
    }

    @Test
    void putNewKeyShouldAddNewElement() {
        myHashMap.put(11, "New key");
        assertEquals("New key", myHashMap.get(11));
    }

    @Test
    void putExistKeyShouldRewriteOldValue() {
        myHashMap.put(2, "New value");
        assertEquals("New value", myHashMap.get(2));
    }

    @Test
    void removeExistKeyShouldDeleteElement() {
        myHashMap.remove(2);
        assertNull(myHashMap.get(2));
    }
}