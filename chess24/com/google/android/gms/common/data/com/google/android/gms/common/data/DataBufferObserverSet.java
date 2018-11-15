/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBufferObserver;
import java.util.HashSet;
import java.util.Iterator;

public final class DataBufferObserverSet
implements DataBufferObserver,
DataBufferObserver.Observable {
    private HashSet<DataBufferObserver> zzaCl = new HashSet();

    @Override
    public void addObserver(DataBufferObserver dataBufferObserver) {
        this.zzaCl.add(dataBufferObserver);
    }

    public void clear() {
        this.zzaCl.clear();
    }

    public boolean hasObservers() {
        return this.zzaCl.isEmpty() ^ true;
    }

    @Override
    public void onDataChanged() {
        Iterator<DataBufferObserver> iterator = this.zzaCl.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataChanged();
        }
    }

    @Override
    public void onDataRangeChanged(int n, int n2) {
        Iterator<DataBufferObserver> iterator = this.zzaCl.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeChanged(n, n2);
        }
    }

    @Override
    public void onDataRangeInserted(int n, int n2) {
        Iterator<DataBufferObserver> iterator = this.zzaCl.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeInserted(n, n2);
        }
    }

    @Override
    public void onDataRangeMoved(int n, int n2, int n3) {
        Iterator<DataBufferObserver> iterator = this.zzaCl.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeMoved(n, n2, n3);
        }
    }

    @Override
    public void onDataRangeRemoved(int n, int n2) {
        Iterator<DataBufferObserver> iterator = this.zzaCl.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeRemoved(n, n2);
        }
    }

    @Override
    public void removeObserver(DataBufferObserver dataBufferObserver) {
        this.zzaCl.remove(dataBufferObserver);
    }
}
