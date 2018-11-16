// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class TwoSidedHashMap<K, V> implements TwoSidedMap<K, V>
{
    private HashMap<K, V> _mapKeyValue;
    private HashMap<V, K> _mapValueKey;
    
    public TwoSidedHashMap() {
        this._mapKeyValue = new HashMap<K, V>();
        this._mapValueKey = new HashMap<V, K>();
    }
    
    public TwoSidedHashMap(final int n) {
        this._mapKeyValue = new HashMap<K, V>(n);
        this._mapValueKey = new HashMap<V, K>(n);
    }
    
    @Override
    public void clear() {
        this._mapKeyValue.clear();
        this._mapValueKey.clear();
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this._mapKeyValue.containsKey(o);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this._mapValueKey.containsKey(o);
    }
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return this._mapKeyValue.entrySet();
    }
    
    @Override
    public V get(final Object o) {
        return this._mapKeyValue.get(o);
    }
    
    @Override
    public K getKeyForValue(final V v) {
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
    public V put(final K k, final V v) {
        final boolean containsKey = this._mapKeyValue.containsKey(k);
        final boolean containsKey2 = this._mapValueKey.containsKey(v);
        final V value = this._mapKeyValue.get(k);
        final K value2 = this._mapValueKey.get(v);
        if (containsKey) {
            this._mapValueKey.remove(value);
        }
        if (containsKey2) {
            this._mapKeyValue.remove(value2);
        }
        this._mapValueKey.put(v, k);
        return this._mapKeyValue.put(k, v);
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public V remove(Object remove) {
        remove = this._mapKeyValue.remove(remove);
        this._mapValueKey.remove(remove);
        return (V)remove;
    }
    
    @Override
    public K removeValue(final V v) {
        final K remove = this._mapValueKey.remove(v);
        this._mapKeyValue.remove(remove);
        return remove;
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
