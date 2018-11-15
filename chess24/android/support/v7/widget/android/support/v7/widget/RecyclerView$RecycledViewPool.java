/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import java.util.ArrayList;

public static class RecyclerView.RecycledViewPool {
    private static final int DEFAULT_MAX_SCRAP = 5;
    private int mAttachCount = 0;
    SparseArray<ScrapData> mScrap = new SparseArray();

    private ScrapData getScrapDataForType(int n) {
        ScrapData scrapData;
        ScrapData scrapData2 = scrapData = (ScrapData)this.mScrap.get(n);
        if (scrapData == null) {
            scrapData2 = new ScrapData();
            this.mScrap.put(n, (Object)scrapData2);
        }
        return scrapData2;
    }

    void attach(RecyclerView.Adapter adapter) {
        ++this.mAttachCount;
    }

    public void clear() {
        for (int i = 0; i < this.mScrap.size(); ++i) {
            ((ScrapData)this.mScrap.valueAt((int)i)).mScrapHeap.clear();
        }
    }

    void detach() {
        --this.mAttachCount;
    }

    void factorInBindTime(int n, long l) {
        ScrapData scrapData = this.getScrapDataForType(n);
        scrapData.mBindRunningAverageNs = this.runningAverage(scrapData.mBindRunningAverageNs, l);
    }

    void factorInCreateTime(int n, long l) {
        ScrapData scrapData = this.getScrapDataForType(n);
        scrapData.mCreateRunningAverageNs = this.runningAverage(scrapData.mCreateRunningAverageNs, l);
    }

    @Nullable
    public RecyclerView.ViewHolder getRecycledView(int n) {
        Object object = (ScrapData)this.mScrap.get(n);
        if (object != null && !object.mScrapHeap.isEmpty()) {
            object = object.mScrapHeap;
            return (RecyclerView.ViewHolder)object.remove(object.size() - 1);
        }
        return null;
    }

    public int getRecycledViewCount(int n) {
        return this.getScrapDataForType((int)n).mScrapHeap.size();
    }

    void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2, boolean bl) {
        if (adapter != null) {
            this.detach();
        }
        if (!bl && this.mAttachCount == 0) {
            this.clear();
        }
        if (adapter2 != null) {
            this.attach(adapter2);
        }
    }

    public void putRecycledView(RecyclerView.ViewHolder viewHolder) {
        int n = viewHolder.getItemViewType();
        ArrayList<RecyclerView.ViewHolder> arrayList = this.getScrapDataForType((int)n).mScrapHeap;
        if (((ScrapData)this.mScrap.get((int)n)).mMaxScrap <= arrayList.size()) {
            return;
        }
        viewHolder.resetInternal();
        arrayList.add(viewHolder);
    }

    long runningAverage(long l, long l2) {
        if (l == 0L) {
            return l2;
        }
        return l / 4L * 3L + l2 / 4L;
    }

    public void setMaxRecycledViews(int n, int n2) {
        Object object = this.getScrapDataForType(n);
        object.mMaxScrap = n2;
        object = object.mScrapHeap;
        while (object.size() > n2) {
            object.remove(object.size() - 1);
        }
    }

    int size() {
        int n = 0;
        for (int i = 0; i < this.mScrap.size(); ++i) {
            ArrayList<RecyclerView.ViewHolder> arrayList = ((ScrapData)this.mScrap.valueAt((int)i)).mScrapHeap;
            int n2 = n;
            if (arrayList != null) {
                n2 = n + arrayList.size();
            }
            n = n2;
        }
        return n;
    }

    boolean willBindInTime(int n, long l, long l2) {
        long l3 = this.getScrapDataForType((int)n).mBindRunningAverageNs;
        if (l3 != 0L && l + l3 >= l2) {
            return false;
        }
        return true;
    }

    boolean willCreateInTime(int n, long l, long l2) {
        long l3 = this.getScrapDataForType((int)n).mCreateRunningAverageNs;
        if (l3 != 0L && l + l3 >= l2) {
            return false;
        }
        return true;
    }

    static class ScrapData {
        long mBindRunningAverageNs = 0L;
        long mCreateRunningAverageNs = 0L;
        int mMaxScrap = 5;
        final ArrayList<RecyclerView.ViewHolder> mScrapHeap = new ArrayList();

        ScrapData() {
        }
    }

}
