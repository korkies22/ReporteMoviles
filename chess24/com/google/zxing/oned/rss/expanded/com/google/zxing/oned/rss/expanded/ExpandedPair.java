/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class ExpandedPair {
    private final FinderPattern finderPattern;
    private final DataCharacter leftChar;
    private final boolean mayBeLast;
    private final DataCharacter rightChar;

    ExpandedPair(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern, boolean bl) {
        this.leftChar = dataCharacter;
        this.rightChar = dataCharacter2;
        this.finderPattern = finderPattern;
        this.mayBeLast = bl;
    }

    private static boolean equalsOrNull(Object object, Object object2) {
        if (object == null) {
            if (object2 == null) {
                return true;
            }
            return false;
        }
        return object.equals(object2);
    }

    private static int hashNotNull(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof ExpandedPair)) {
            return false;
        }
        object = (ExpandedPair)object;
        if (ExpandedPair.equalsOrNull(this.leftChar, object.leftChar) && ExpandedPair.equalsOrNull(this.rightChar, object.rightChar) && ExpandedPair.equalsOrNull(this.finderPattern, object.finderPattern)) {
            return true;
        }
        return false;
    }

    FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    DataCharacter getLeftChar() {
        return this.leftChar;
    }

    DataCharacter getRightChar() {
        return this.rightChar;
    }

    public int hashCode() {
        return ExpandedPair.hashNotNull(this.leftChar) ^ ExpandedPair.hashNotNull(this.rightChar) ^ ExpandedPair.hashNotNull(this.finderPattern);
    }

    boolean mayBeLast() {
        return this.mayBeLast;
    }

    public boolean mustBeLast() {
        if (this.rightChar == null) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[ ");
        stringBuilder.append(this.leftChar);
        stringBuilder.append(" , ");
        stringBuilder.append(this.rightChar);
        stringBuilder.append(" : ");
        Object object = this.finderPattern == null ? "null" : Integer.valueOf(this.finderPattern.getValue());
        stringBuilder.append(object);
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}
