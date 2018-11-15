/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.DiffUtil;

static class DiffUtil.Range {
    int newListEnd;
    int newListStart;
    int oldListEnd;
    int oldListStart;

    public DiffUtil.Range() {
    }

    public DiffUtil.Range(int n, int n2, int n3, int n4) {
        this.oldListStart = n;
        this.oldListEnd = n2;
        this.newListStart = n3;
        this.newListEnd = n4;
    }
}
