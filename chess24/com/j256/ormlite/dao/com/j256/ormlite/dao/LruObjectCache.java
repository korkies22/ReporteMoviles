/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.ObjectCache;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LruObjectCache
implements ObjectCache {
    private final int capacity;
    private final ConcurrentHashMap<Class<?>, Map<Object, Object>> classMaps = new ConcurrentHashMap();

    public LruObjectCache(int n) {
        this.capacity = n;
    }

    private Map<Object, Object> getMapForClass(Class<?> object) {
        if ((object = this.classMaps.get(object)) == null) {
            return null;
        }
        return object;
    }

    @Override
    public <T> void clear(Class<T> object) {
        if ((object = this.getMapForClass((Class<?>)object)) != null) {
            object.clear();
        }
    }

    @Override
    public void clearAll() {
        Iterator<Map<Object, Object>> iterator = this.classMaps.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
    }

    @Override
    public <T, ID> T get(Class<T> object, ID ID) {
        if ((object = this.getMapForClass((Class<?>)object)) == null) {
            return null;
        }
        return (T)object.get(ID);
    }

    @Override
    public <T, ID> void put(Class<T> object, ID ID, T t) {
        if ((object = this.getMapForClass((Class<?>)object)) != null) {
            object.put(ID, t);
        }
    }

    @Override
    public <T> void registerClass(Class<T> class_) {
        synchronized (this) {
            if (this.classMaps.get(class_) == null) {
                Map map = Collections.synchronizedMap(new LimitedLinkedHashMap(this.capacity));
                this.classMaps.put(class_, map);
            }
            return;
        }
    }

    @Override
    public <T, ID> void remove(Class<T> object, ID ID) {
        if ((object = this.getMapForClass((Class<?>)object)) != null) {
            object.remove(ID);
        }
    }

    @Override
    public <T> int size(Class<T> object) {
        if ((object = this.getMapForClass((Class<?>)object)) == null) {
            return 0;
        }
        return object.size();
    }

    @Override
    public int sizeAll() {
        Iterator<Map<Object, Object>> iterator = this.classMaps.values().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            n += iterator.next().size();
        }
        return n;
    }

    @Override
    public <T, ID> T updateId(Class<T> object, ID object2, ID ID) {
        if ((object = this.getMapForClass((Class<?>)object)) == null) {
            return null;
        }
        if ((object2 = object.remove(object2)) == null) {
            return null;
        }
        object.put(ID, object2);
        return (T)object2;
    }

    private static class LimitedLinkedHashMap<K, V>
    extends LinkedHashMap<K, V> {
        private static final long serialVersionUID = -4566528080395573236L;
        private final int capacity;

        public LimitedLinkedHashMap(int n) {
            super(n, 0.75f, true);
            this.capacity = n;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
            if (this.size() > this.capacity) {
                return true;
            }
            return false;
        }
    }

}
