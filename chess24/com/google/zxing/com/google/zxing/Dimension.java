/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

public final class Dimension {
    private final int height;
    private final int width;

    public Dimension(int n, int n2) {
        if (n >= 0 && n2 >= 0) {
            this.width = n;
            this.height = n2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public boolean equals(Object object) {
        if (object instanceof Dimension) {
            object = (Dimension)object;
            if (this.width == object.width && this.height == object.height) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return this.width * 32713 + this.height;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.width);
        stringBuilder.append("x");
        stringBuilder.append(this.height);
        return stringBuilder.toString();
    }
}
