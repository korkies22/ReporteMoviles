/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.expanded.ExpandedPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;
    private final boolean wasReversed;

    ExpandedRow(List<ExpandedPair> list, int n, boolean bl) {
        this.pairs = new ArrayList<ExpandedPair>(list);
        this.rowNumber = n;
        this.wasReversed = bl;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ExpandedRow)) {
            return false;
        }
        if (this.pairs.equals((object = (ExpandedRow)object).getPairs()) && this.wasReversed == object.wasReversed) {
            return true;
        }
        return false;
    }

    List<ExpandedPair> getPairs() {
        return this.pairs;
    }

    int getRowNumber() {
        return this.rowNumber;
    }

    public int hashCode() {
        return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
    }

    boolean isEquivalent(List<ExpandedPair> list) {
        return this.pairs.equals(list);
    }

    boolean isReversed() {
        return this.wasReversed;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{ ");
        stringBuilder.append(this.pairs);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
