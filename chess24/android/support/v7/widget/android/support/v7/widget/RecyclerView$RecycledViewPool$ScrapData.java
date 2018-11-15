/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

static class RecyclerView.RecycledViewPool.ScrapData {
    long mBindRunningAverageNs = 0L;
    long mCreateRunningAverageNs = 0L;
    int mMaxScrap = 5;
    final ArrayList<RecyclerView.ViewHolder> mScrapHeap = new ArrayList();

    RecyclerView.RecycledViewPool.ScrapData() {
    }
}
