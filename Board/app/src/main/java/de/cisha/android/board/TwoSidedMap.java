// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import java.util.Map;

public interface TwoSidedMap<K, V> extends Map<K, V>
{
    K getKeyForValue(final V p0);
    
    K removeValue(final V p0);
}
