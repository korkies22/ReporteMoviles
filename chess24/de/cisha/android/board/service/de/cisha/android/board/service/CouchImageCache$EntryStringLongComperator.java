/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.CouchImageCache;
import java.util.Comparator;
import java.util.Map;

private static class CouchImageCache.EntryStringLongComperator
implements Comparator<Map.Entry<String, ?>> {
    private CouchImageCache.EntryStringLongComperator() {
    }

    @Override
    public int compare(Map.Entry<String, ?> entry, Map.Entry<String, ?> entry2) {
        long l;
        long l2 = Long.parseLong(entry.getValue().toString());
        if (l2 < (l = Long.parseLong(entry2.getValue().toString()))) {
            return -1;
        }
        if (l2 > l) {
            return 1;
        }
        return 0;
    }
}
