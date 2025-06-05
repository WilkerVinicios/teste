package com.br.teste.cache;

import java.util.LinkedHashMap;

public class CacheLRU<K, V> {
    private final int capacidadeMaxima;
    private final LinkedHashMap<K, V> cache;

    public CacheLRU(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
        this.cache = new LinkedHashMap<K, V>(capacidadeMaxima, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
                return size() > capacidadeMaxima;
            }
        };
    }

    public void put(K chave, V valor) {
        cache.put(chave, valor);
    }
    public V get(K chave) {
        return cache.getOrDefault(chave, null);
    }
    public void remove(K chave) {
        cache.remove(chave);
    }
    public int size() {
        return cache.size();
    }
}
