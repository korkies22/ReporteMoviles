/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.DiffUtil;

private static class DiffUtil.PostponedUpdate {
    int currentPos;
    int posInOwnerList;
    boolean removal;

    public DiffUtil.PostponedUpdate(int n, int n2, boolean bl) {
        this.posInOwnerList = n;
        this.currentPos = n2;
        this.removal = bl;
    }
}
