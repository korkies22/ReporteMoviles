/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.ObjectCache;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReferenceObjectCache
implements ObjectCache {
    private final ConcurrentHashMap<Class<?>, Map<Object, Reference<Object>>> classMaps = new ConcurrentHashMap();
    private final boolean useWeak;

    public ReferenceObjectCache(boolean bl) {
        this.useWeak = bl;
    }

    private void cleanMap(Map<Object, Reference<Object>> object) {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            if (((Reference)((Map.Entry)object.next()).getValue()).get() != null) continue;
            object.remove();
        }
    }

    private Map<Object, Reference<Object>> getMapForClass(Class<?> object) {
        if ((object = this.classMaps.get(object)) == null) {
            return null;
        }
        return object;
    }

    public static ReferenceObjectCache makeSoftCache() {
        return new ReferenceObjectCache(false);
    }

    public static ReferenceObjectCache makeWeakCache() {
        return new ReferenceObjectCache(true);
    }

    public <T> void cleanNullReferences(Class<T> object) {
        if ((object = this.getMapForClass((Class<?>)object)) != null) {
            this.cleanMap((Map<Object, Reference<Object>>)object);
        }
    }

    public <T> void cleanNullReferencesAll() {
        Iterator<Map<Object, Reference<Object>>> iterator = this.classMaps.values().iterator();
        while (iterator.hasNext()) {
            this.cleanMap(iterator.next());
        }
    }

    @Override
    public <T> void clear(Class<T> object) {
        if ((object = this.getMapForClass((Class<?>)object)) != null) {
            object.clear();
        }
    }

    @Override
    public void clearAll() {
        Iterator<Map<Object, Reference<Object>>> iterator = this.classMaps.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
    }

    @Override
    public <T, ID> T get(Class<T> object, ID ID) {
        if ((object = this.getMapForClass((Class<?>)object)) == null) {
            return null;
        }
        Reference reference = (Reference)object.get(ID);
        if (reference == null) {
            return null;
        }
        if ((reference = reference.get()) == null) {
            object.remove(ID);
            return null;
        }
        return (T)reference;
    }

    @Override
    public <T, ID> void put(Class<T> object, ID ID, T t) {
        if ((object = this.getMapForClass((Class<?>)object)) != null) {
            if (this.useWeak) {
                object.put(ID, new WeakReference<T>(t));
                return;
            }
            object.put(ID, new SoftReference<T>(t));
        }
    }

    @Override
    public <T> void registerClass(Class<T> class_) {
        synchronized (this) {
            if (this.classMaps.get(class_) == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                this.classMaps.put(class_, concurrentHashMap);
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
        Iterator<Map<Object, Reference<Object>>> iterator = this.classMaps.values().iterator();
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
        if ((object2 = (Reference)object.remove(object2)) == null) {
            return null;
        }
        object.put(ID, object2);
        return object2.get();
    }
}
