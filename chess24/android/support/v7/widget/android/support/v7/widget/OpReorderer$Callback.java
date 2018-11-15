/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.OpReorderer;

static interface OpReorderer.Callback {
    public AdapterHelper.UpdateOp obtainUpdateOp(int var1, int var2, int var3, Object var4);

    public void recycleUpdateOp(AdapterHelper.UpdateOp var1);
}
