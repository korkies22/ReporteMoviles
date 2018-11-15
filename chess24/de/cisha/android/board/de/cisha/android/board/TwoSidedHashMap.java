/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.TwoSidedMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TwoSidedHashMap<K, V>
implements TwoSidedMap<K, V> {
    private HashMap<K, V> _mapKeyValue;
    private HashMap<V, K> _mapValueKey;

    public TwoSidedHashMap() {
        this._mapKeyValue = new HashMap();
        this._mapValueKey = new HashMap();
    }

    public TwoSidedHashMap(int n) {
        this._mapKeyValue = new HashMap(n);
        this._mapValueKey = new HashMap(n);
    }

    @Override
    public void clear() {
        this._mapKeyValue.clear();
        this._mapValueKey.clear();
    }

    @Override
    public boolean containsKey(Object object) {
        return this._mapKeyValue.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this._mapValueKey.containsKey(object);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this._mapKeyValue.entrySet();
    }

    @Override
    public V get(Object object) {
        return this._mapKeyValue.get(object);
    }

    @Override
    public K getKeyForValue(V v) {
        return this._mapValueKey.get(v);
    }

    @Override
    public boolean isEmpty() {
        return this._mapKeyValue.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return this._mapKeyValue.keySet();
    }

    @Override
    public V put(K k, V v) {
        boolean bl = this._mapKeyValue.containsKey(k);
        boolean bl2 = this._mapValueKey.containsKey(v);
        V v2 = this._mapKeyValue.get(k);
        K k2 = this._mapValueKey.get(v);
        if (bl) {
            this._mapValueKey.remove(v2);
        }
        if (bl2) {
            this._mapKeyValue.remove(k2);
        }
        this._mapValueKey.put(v, k);
        return this._mapKeyValue.put(k, v);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        for (Map.Entry entry : object.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object object) {
        object = this._mapKeyValue.remove(object);
        this._mapValueKey.remove(object);
        return (V)object;
    }

    @Override
    public K removeValue(V object) {
        object = this._mapValueKey.remove(object);
        this._mapKeyValue.remove(object);
        return (K)object;
    }

    @Override
    public int size() {
        return this._mapKeyValue.size();
    }

    @Override
    public Collection<V> values() {
        return this._mapValueKey.keySet();
    }
}
