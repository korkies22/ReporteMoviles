/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss;

public class DataCharacter {
    private final int checksumPortion;
    private final int value;

    public DataCharacter(int n, int n2) {
        this.value = n;
        this.checksumPortion = n2;
    }

    public final boolean equals(Object object) {
        if (!(object instanceof DataCharacter)) {
            return false;
        }
        object = (DataCharacter)object;
        if (this.value == object.value && this.checksumPortion == object.checksumPortion) {
            return true;
        }
        return false;
    }

    public final int getChecksumPortion() {
        return this.checksumPortion;
    }

    public final int getValue() {
        return this.value;
    }

    public final int hashCode() {
        return this.value ^ this.checksumPortion;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.value);
        stringBuilder.append("(");
        stringBuilder.append(this.checksumPortion);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
