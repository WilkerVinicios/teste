package com.br.teste.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheLRUTest {

    private CacheLRU<Integer, String> cache;

    @BeforeEach
    void setUp() {
        cache = new CacheLRU<>(3);
    }

    @Test
    void testPutAndGet() {
        cache.put(1, "One");
        cache.put(2, "Two");

        assertEquals("One", cache.get(1));
        assertEquals("Two", cache.get(2));
        assertNull(cache.get(3));
    }

    @Test
    void testSize() {
        assertEquals(0, cache.size());
        cache.put(1, "One");
        cache.put(2, "Two");
        assertEquals(2, cache.size());
    }

    @Test
    void testRemove() {
        cache.put(1, "One");
        cache.put(2, "Two");

        cache.remove(1);
        assertNull(cache.get(1));
        assertEquals(1, cache.size());
    }

    @Test
    void testLRUEviction() {
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        cache.get(1);
        cache.put(4, "Four");

        assertNull(cache.get(2));
        assertEquals("One", cache.get(1));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
        assertEquals(3, cache.size());
    }
}