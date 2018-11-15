/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.UiThread;
import android.support.v7.util.AsyncListUtil;

public static abstract class AsyncListUtil.ViewCallback {
    public static final int HINT_SCROLL_ASC = 2;
    public static final int HINT_SCROLL_DESC = 1;
    public static final int HINT_SCROLL_NONE = 0;

    @UiThread
    public void extendRangeInto(int[] arrn, int[] arrn2, int n) {
        int n2 = arrn[1] - arrn[0] + 1;
        int n3 = n2 / 2;
        int n4 = arrn[0];
        int n5 = n == 1 ? n2 : n3;
        arrn2[0] = n4 - n5;
        n5 = arrn[1];
        if (n != 2) {
            n2 = n3;
        }
        arrn2[1] = n5 + n2;
    }

    @UiThread
    public abstract void getItemRangeInto(int[] var1);

    @UiThread
    public abstract void onDataRefresh();

    @UiThread
    public abstract void onItemLoaded(int var1);
}
