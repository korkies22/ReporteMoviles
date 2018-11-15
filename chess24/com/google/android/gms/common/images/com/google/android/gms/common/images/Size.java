/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.images;

public final class Size {
    private final int zzrG;
    private final int zzrH;

    public Size(int n, int n2) {
        this.zzrG = n;
        this.zzrH = n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Size parseSize(String string) throws NumberFormatException {
        int n;
        if (string == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int n2 = n = string.indexOf(42);
        if (n < 0) {
            n2 = string.indexOf(120);
        }
        if (n2 < 0) {
            throw Size.zzdm(string);
        }
        try {
            return new Size(Integer.parseInt(string.substring(0, n2)), Integer.parseInt(string.substring(n2 + 1)));
        }
        catch (NumberFormatException numberFormatException) {
            throw Size.zzdm(string);
        }
    }

    private static NumberFormatException zzdm(String string) {
        StringBuilder stringBuilder = new StringBuilder(16 + String.valueOf(string).length());
        stringBuilder.append("Invalid Size: \"");
        stringBuilder.append(string);
        stringBuilder.append("\"");
        throw new NumberFormatException(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        boolean bl2 = bl;
        if (object instanceof Size) {
            object = (Size)object;
            bl2 = bl;
            if (this.zzrG == object.zzrG) {
                bl2 = bl;
                if (this.zzrH == object.zzrH) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public int getHeight() {
        return this.zzrH;
    }

    public int getWidth() {
        return this.zzrG;
    }

    public int hashCode() {
        return this.zzrH ^ (this.zzrG << 16 | this.zzrG >>> 16);
    }

    public String toString() {
        int n = this.zzrG;
        int n2 = this.zzrH;
        StringBuilder stringBuilder = new StringBuilder(23);
        stringBuilder.append(n);
        stringBuilder.append("x");
        stringBuilder.append(n2);
        return stringBuilder.toString();
    }
}
