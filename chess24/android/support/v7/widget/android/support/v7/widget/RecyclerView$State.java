/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static class RecyclerView.State {
    static final int STEP_ANIMATIONS = 4;
    static final int STEP_LAYOUT = 2;
    static final int STEP_START = 1;
    private SparseArray<Object> mData;
    int mDeletedInvisibleItemCountSincePreviousLayout = 0;
    long mFocusedItemId;
    int mFocusedItemPosition;
    int mFocusedSubChildId;
    boolean mInPreLayout = false;
    boolean mIsMeasuring = false;
    int mItemCount = 0;
    int mLayoutStep = 1;
    int mPreviousLayoutItemCount = 0;
    int mRemainingScrollHorizontal;
    int mRemainingScrollVertical;
    boolean mRunPredictiveAnimations = false;
    boolean mRunSimpleAnimations = false;
    boolean mStructureChanged = false;
    private int mTargetPosition = -1;
    boolean mTrackOldChangeHolders = false;

    static /* synthetic */ int access$1302(RecyclerView.State state, int n) {
        state.mTargetPosition = n;
        return n;
    }

    void assertLayoutStep(int n) {
        if ((this.mLayoutStep & n) == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Layout state should be one of ");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(" but it is ");
            stringBuilder.append(Integer.toBinaryString(this.mLayoutStep));
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public boolean didStructureChange() {
        return this.mStructureChanged;
    }

    public <T> T get(int n) {
        if (this.mData == null) {
            return null;
        }
        return (T)this.mData.get(n);
    }

    public int getItemCount() {
        if (this.mInPreLayout) {
            return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
        }
        return this.mItemCount;
    }

    public int getRemainingScrollHorizontal() {
        return this.mRemainingScrollHorizontal;
    }

    public int getRemainingScrollVertical() {
        return this.mRemainingScrollVertical;
    }

    public int getTargetScrollPosition() {
        return this.mTargetPosition;
    }

    public boolean hasTargetScrollPosition() {
        if (this.mTargetPosition != -1) {
            return true;
        }
        return false;
    }

    public boolean isMeasuring() {
        return this.mIsMeasuring;
    }

    public boolean isPreLayout() {
        return this.mInPreLayout;
    }

    void prepareForNestedPrefetch(RecyclerView.Adapter adapter) {
        this.mLayoutStep = 1;
        this.mItemCount = adapter.getItemCount();
        this.mInPreLayout = false;
        this.mTrackOldChangeHolders = false;
        this.mIsMeasuring = false;
    }

    public void put(int n, Object object) {
        if (this.mData == null) {
            this.mData = new SparseArray();
        }
        this.mData.put(n, object);
    }

    public void remove(int n) {
        if (this.mData == null) {
            return;
        }
        this.mData.remove(n);
    }

    RecyclerView.State reset() {
        this.mTargetPosition = -1;
        if (this.mData != null) {
            this.mData.clear();
        }
        this.mItemCount = 0;
        this.mStructureChanged = false;
        this.mIsMeasuring = false;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("State{mTargetPosition=");
        stringBuilder.append(this.mTargetPosition);
        stringBuilder.append(", mData=");
        stringBuilder.append(this.mData);
        stringBuilder.append(", mItemCount=");
        stringBuilder.append(this.mItemCount);
        stringBuilder.append(", mIsMeasuring=");
        stringBuilder.append(this.mIsMeasuring);
        stringBuilder.append(", mPreviousLayoutItemCount=");
        stringBuilder.append(this.mPreviousLayoutItemCount);
        stringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
        stringBuilder.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
        stringBuilder.append(", mStructureChanged=");
        stringBuilder.append(this.mStructureChanged);
        stringBuilder.append(", mInPreLayout=");
        stringBuilder.append(this.mInPreLayout);
        stringBuilder.append(", mRunSimpleAnimations=");
        stringBuilder.append(this.mRunSimpleAnimations);
        stringBuilder.append(", mRunPredictiveAnimations=");
        stringBuilder.append(this.mRunPredictiveAnimations);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public boolean willRunPredictiveAnimations() {
        return this.mRunPredictiveAnimations;
    }

    public boolean willRunSimpleAnimations() {
        return this.mRunSimpleAnimations;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface LayoutState {
    }

}
