/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import java.util.Map;

public interface TwoSidedMap<K, V>
extends Map<K, V> {
    public K getKeyForValue(V var1);

    public K removeValue(V var1);
}
