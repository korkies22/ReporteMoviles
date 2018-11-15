/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

static class StaggeredGridLayoutManager.LazySpanLookup {
    private static final int MIN_SIZE = 10;
    int[] mData;
    List<FullSpanItem> mFullSpanItems;

    StaggeredGridLayoutManager.LazySpanLookup() {
    }

    private int invalidateFullSpansAfter(int n) {
        FullSpanItem fullSpanItem;
        int n2;
        block5 : {
            if (this.mFullSpanItems == null) {
                return -1;
            }
            fullSpanItem = this.getFullSpanItem(n);
            if (fullSpanItem != null) {
                this.mFullSpanItems.remove(fullSpanItem);
            }
            int n3 = this.mFullSpanItems.size();
            for (n2 = 0; n2 < n3; ++n2) {
                if (this.mFullSpanItems.get((int)n2).mPosition < n) {
                    continue;
                }
                break block5;
            }
            n2 = -1;
        }
        if (n2 != -1) {
            fullSpanItem = this.mFullSpanItems.get(n2);
            this.mFullSpanItems.remove(n2);
            return fullSpanItem.mPosition;
        }
        return -1;
    }

    private void offsetFullSpansForAddition(int n, int n2) {
        if (this.mFullSpanItems == null) {
            return;
        }
        for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
            FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
            if (fullSpanItem.mPosition < n) continue;
            fullSpanItem.mPosition += n2;
        }
    }

    private void offsetFullSpansForRemoval(int n, int n2) {
        if (this.mFullSpanItems == null) {
            return;
        }
        for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
            FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
            if (fullSpanItem.mPosition < n) continue;
            if (fullSpanItem.mPosition < n + n2) {
                this.mFullSpanItems.remove(i);
                continue;
            }
            fullSpanItem.mPosition -= n2;
        }
    }

    public void addFullSpanItem(FullSpanItem fullSpanItem) {
        if (this.mFullSpanItems == null) {
            this.mFullSpanItems = new ArrayList<FullSpanItem>();
        }
        int n = this.mFullSpanItems.size();
        for (int i = 0; i < n; ++i) {
            FullSpanItem fullSpanItem2 = this.mFullSpanItems.get(i);
            if (fullSpanItem2.mPosition == fullSpanItem.mPosition) {
                this.mFullSpanItems.remove(i);
            }
            if (fullSpanItem2.mPosition < fullSpanItem.mPosition) continue;
            this.mFullSpanItems.add(i, fullSpanItem);
            return;
        }
        this.mFullSpanItems.add(fullSpanItem);
    }

    void clear() {
        if (this.mData != null) {
            Arrays.fill(this.mData, -1);
        }
        this.mFullSpanItems = null;
    }

    void ensureSize(int n) {
        if (this.mData == null) {
            this.mData = new int[Math.max(n, 10) + 1];
            Arrays.fill(this.mData, -1);
            return;
        }
        if (n >= this.mData.length) {
            int[] arrn = this.mData;
            this.mData = new int[this.sizeForPosition(n)];
            System.arraycopy(arrn, 0, this.mData, 0, arrn.length);
            Arrays.fill(this.mData, arrn.length, this.mData.length, -1);
        }
    }

    int forceInvalidateAfter(int n) {
        if (this.mFullSpanItems != null) {
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                if (this.mFullSpanItems.get((int)i).mPosition < n) continue;
                this.mFullSpanItems.remove(i);
            }
        }
        return this.invalidateAfter(n);
    }

    public FullSpanItem getFirstFullSpanItemInRange(int n, int n2, int n3, boolean bl) {
        if (this.mFullSpanItems == null) {
            return null;
        }
        int n4 = this.mFullSpanItems.size();
        for (int i = 0; i < n4; ++i) {
            FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
            if (fullSpanItem.mPosition >= n2) {
                return null;
            }
            if (fullSpanItem.mPosition < n || n3 != 0 && fullSpanItem.mGapDir != n3 && (!bl || !fullSpanItem.mHasUnwantedGapAfter)) continue;
            return fullSpanItem;
        }
        return null;
    }

    public FullSpanItem getFullSpanItem(int n) {
        if (this.mFullSpanItems == null) {
            return null;
        }
        for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
            FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
            if (fullSpanItem.mPosition != n) continue;
            return fullSpanItem;
        }
        return null;
    }

    int getSpan(int n) {
        if (this.mData != null && n < this.mData.length) {
            return this.mData[n];
        }
        return -1;
    }

    int invalidateAfter(int n) {
        if (this.mData == null) {
            return -1;
        }
        if (n >= this.mData.length) {
            return -1;
        }
        int n2 = this.invalidateFullSpansAfter(n);
        if (n2 == -1) {
            Arrays.fill(this.mData, n, this.mData.length, -1);
            return this.mData.length;
        }
        int[] arrn = this.mData;
        Arrays.fill(arrn, n, ++n2, -1);
        return n2;
    }

    void offsetForAddition(int n, int n2) {
        if (this.mData != null) {
            if (n >= this.mData.length) {
                return;
            }
            int n3 = n + n2;
            this.ensureSize(n3);
            System.arraycopy(this.mData, n, this.mData, n3, this.mData.length - n - n2);
            Arrays.fill(this.mData, n, n3, -1);
            this.offsetFullSpansForAddition(n, n2);
            return;
        }
    }

    void offsetForRemoval(int n, int n2) {
        if (this.mData != null) {
            if (n >= this.mData.length) {
                return;
            }
            int n3 = n + n2;
            this.ensureSize(n3);
            System.arraycopy(this.mData, n3, this.mData, n, this.mData.length - n - n2);
            Arrays.fill(this.mData, this.mData.length - n2, this.mData.length, -1);
            this.offsetFullSpansForRemoval(n, n2);
            return;
        }
    }

    void setSpan(int n, StaggeredGridLayoutManager.Span span) {
        this.ensureSize(n);
        this.mData[n] = span.mIndex;
    }

    int sizeForPosition(int n) {
        int n2;
        for (n2 = this.mData.length; n2 <= n; n2 *= 2) {
        }
        return n2;
    }

    static class FullSpanItem
    implements Parcelable {
        public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>(){

            public FullSpanItem createFromParcel(Parcel parcel) {
                return new FullSpanItem(parcel);
            }

            public FullSpanItem[] newArray(int n) {
                return new FullSpanItem[n];
            }
        };
        int mGapDir;
        int[] mGapPerSpan;
        boolean mHasUnwantedGapAfter;
        int mPosition;

        FullSpanItem() {
        }

        FullSpanItem(Parcel parcel) {
            this.mPosition = parcel.readInt();
            this.mGapDir = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.mHasUnwantedGapAfter = bl;
            n = parcel.readInt();
            if (n > 0) {
                this.mGapPerSpan = new int[n];
                parcel.readIntArray(this.mGapPerSpan);
            }
        }

        public int describeContents() {
            return 0;
        }

        int getGapForSpan(int n) {
            if (this.mGapPerSpan == null) {
                return 0;
            }
            return this.mGapPerSpan[n];
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FullSpanItem{mPosition=");
            stringBuilder.append(this.mPosition);
            stringBuilder.append(", mGapDir=");
            stringBuilder.append(this.mGapDir);
            stringBuilder.append(", mHasUnwantedGapAfter=");
            stringBuilder.append(this.mHasUnwantedGapAfter);
            stringBuilder.append(", mGapPerSpan=");
            stringBuilder.append(Arrays.toString(this.mGapPerSpan));
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

}
