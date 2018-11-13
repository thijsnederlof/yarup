package nl.thijsnederlof.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiKeyMap<K1, K2, V> {

    private HashMap<K1, V> k1VHashMap = new HashMap<>();

    private HashMap<K2, V> k2VHashMap = new HashMap<>();

    public boolean containsK1(K1 k1) {
        return k1VHashMap.containsKey(k1);
    }

    public boolean containsK2(K2 k2) {
        return k2VHashMap.containsKey(k2);
    }

    public V put(K1 k1, K2 k2, V value) {
        k1VHashMap.put(k1, value);
        k2VHashMap.put(k2, value);

        return value;
    }

    public long size() {
        if(k1VHashMap.size() != k2VHashMap.size()) {
            throw new IllegalStateException("Underlying map sizes of MultiKeyMap are not equal");
        }

        return k1VHashMap.size();
    }

    public V removeK1(K1 k1) {
        V v = k1VHashMap.get(k1);

        for (Map.Entry<K2, V> entry : k2VHashMap.entrySet()) {
            if(v.equals(entry.getValue())) {
                k2VHashMap.remove(entry.getKey());
                k1VHashMap.remove(k1);
            }
        }

        return v;
    }

    public Set<MultiKeyMap.Entry<K1, K2, V>> entrySet() {
        final Set<MultiKeyMap.Entry<K1, K2, V>> entrySet = new HashSet<>();

        for(Map.Entry<K1, V> k1VEntry : k1VHashMap.entrySet()) {
            for(Map.Entry<K2, V> k2VEntry : k2VHashMap.entrySet()) {
                if(k1VEntry.getValue().equals(k2VEntry.getValue())) {
                    entrySet.add(new Entry<>(k1VEntry.getKey(), k2VEntry.getKey(), k1VEntry.getValue()));
                }
            }
        }

        return entrySet;
    }

    public V removeK2(K2 k2) {
        V v = k2VHashMap.get(k2);

        for (Map.Entry<K1, V> entry : k1VHashMap.entrySet()) {
            if(v.equals(entry.getValue())) {
                k1VHashMap.remove(entry.getKey());
                k2VHashMap.remove(k2);
            }
        }

        return v;
    }

    public V getK1(K1 k1) {
        return k1VHashMap.get(k1);
    }

    public V getK2(K2 k2) {
        return k2VHashMap.get(k2);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Entry<K1, K2, V> {

        private final K1 k1;

        private final K2 k2;

        private final V value;
    }
}
