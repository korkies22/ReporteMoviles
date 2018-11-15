/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package android.support.v7.widget;

import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public static abstract class RecyclerView.ViewHolder {
    static final int FLAG_ADAPTER_FULLUPDATE = 1024;
    static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
    static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
    static final int FLAG_BOUND = 1;
    static final int FLAG_IGNORE = 128;
    static final int FLAG_INVALID = 4;
    static final int FLAG_MOVED = 2048;
    static final int FLAG_NOT_RECYCLABLE = 16;
    static final int FLAG_REMOVED = 8;
    static final int FLAG_RETURNED_FROM_SCRAP = 32;
    static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
    static final int FLAG_TMP_DETACHED = 256;
    static final int FLAG_UPDATE = 2;
    private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
    static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
    public final View itemView;
    private int mFlags;
    private boolean mInChangeScrap = false;
    private int mIsRecyclableCount = 0;
    long mItemId = -1L;
    int mItemViewType = -1;
    WeakReference<RecyclerView> mNestedRecyclerView;
    int mOldPosition = -1;
    RecyclerView mOwnerRecyclerView;
    List<Object> mPayloads = null;
    @VisibleForTesting
    int mPendingAccessibilityState = -1;
    int mPosition = -1;
    int mPreLayoutPosition = -1;
    private RecyclerView.Recycler mScrapContainer = null;
    RecyclerView.ViewHolder mShadowedHolder = null;
    RecyclerView.ViewHolder mShadowingHolder = null;
    List<Object> mUnmodifiedPayloads = null;
    private int mWasImportantForAccessibilityBeforeHidden = 0;

    public RecyclerView.ViewHolder(View view) {
        if (view == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = view;
    }

    static /* synthetic */ RecyclerView.Recycler access$1002(RecyclerView.ViewHolder viewHolder, RecyclerView.Recycler recycler) {
        viewHolder.mScrapContainer = recycler;
        return recycler;
    }

    static /* synthetic */ boolean access$1100(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.mInChangeScrap;
    }

    static /* synthetic */ boolean access$1102(RecyclerView.ViewHolder viewHolder, boolean bl) {
        viewHolder.mInChangeScrap = bl;
        return bl;
    }

    static /* synthetic */ boolean access$1500(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.shouldBeKeptAsChild();
    }

    static /* synthetic */ int access$1600(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.mFlags;
    }

    static /* synthetic */ void access$200(RecyclerView.ViewHolder viewHolder, RecyclerView recyclerView) {
        viewHolder.onEnteredHiddenState(recyclerView);
    }

    static /* synthetic */ void access$300(RecyclerView.ViewHolder viewHolder, RecyclerView recyclerView) {
        viewHolder.onLeftHiddenState(recyclerView);
    }

    static /* synthetic */ boolean access$900(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.doesTransientStatePreventRecycling();
    }

    private void createPayloadsIfNeeded() {
        if (this.mPayloads == null) {
            this.mPayloads = new ArrayList<Object>();
            this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
        }
    }

    private boolean doesTransientStatePreventRecycling() {
        if ((this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView)) {
            return true;
        }
        return false;
    }

    private void onEnteredHiddenState(RecyclerView recyclerView) {
        this.mWasImportantForAccessibilityBeforeHidden = this.mPendingAccessibilityState != -1 ? this.mPendingAccessibilityState : ViewCompat.getImportantForAccessibility(this.itemView);
        recyclerView.setChildImportantForAccessibilityInternal(this, 4);
    }

    private void onLeftHiddenState(RecyclerView recyclerView) {
        recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
        this.mWasImportantForAccessibilityBeforeHidden = 0;
    }

    private boolean shouldBeKeptAsChild() {
        if ((this.mFlags & 16) != 0) {
            return true;
        }
        return false;
    }

    void addChangePayload(Object object) {
        if (object == null) {
            this.addFlags(1024);
            return;
        }
        if ((1024 & this.mFlags) == 0) {
            this.createPayloadsIfNeeded();
            this.mPayloads.add(object);
        }
    }

    void addFlags(int n) {
        this.mFlags = n | this.mFlags;
    }

    void clearOldPosition() {
        this.mOldPosition = -1;
        this.mPreLayoutPosition = -1;
    }

    void clearPayload() {
        if (this.mPayloads != null) {
            this.mPayloads.clear();
        }
        this.mFlags &= -1025;
    }

    void clearReturnedFromScrapFlag() {
        this.mFlags &= -33;
    }

    void clearTmpDetachFlag() {
        this.mFlags &= -257;
    }

    void flagRemovedAndOffsetPosition(int n, int n2, boolean bl) {
        this.addFlags(8);
        this.offsetPosition(n2, bl);
        this.mPosition = n;
    }

    public final int getAdapterPosition() {
        if (this.mOwnerRecyclerView == null) {
            return -1;
        }
        return this.mOwnerRecyclerView.getAdapterPositionFor(this);
    }

    public final long getItemId() {
        return this.mItemId;
    }

    public final int getItemViewType() {
        return this.mItemViewType;
    }

    public final int getLayoutPosition() {
        if (this.mPreLayoutPosition == -1) {
            return this.mPosition;
        }
        return this.mPreLayoutPosition;
    }

    public final int getOldPosition() {
        return this.mOldPosition;
    }

    @Deprecated
    public final int getPosition() {
        if (this.mPreLayoutPosition == -1) {
            return this.mPosition;
        }
        return this.mPreLayoutPosition;
    }

    List<Object> getUnmodifiedPayloads() {
        if ((this.mFlags & 1024) == 0) {
            if (this.mPayloads != null && this.mPayloads.size() != 0) {
                return this.mUnmodifiedPayloads;
            }
            return FULLUPDATE_PAYLOADS;
        }
        return FULLUPDATE_PAYLOADS;
    }

    boolean hasAnyOfTheFlags(int n) {
        if ((n & this.mFlags) != 0) {
            return true;
        }
        return false;
    }

    boolean isAdapterPositionUnknown() {
        if ((this.mFlags & 512) == 0 && !this.isInvalid()) {
            return false;
        }
        return true;
    }

    boolean isBound() {
        if ((this.mFlags & 1) != 0) {
            return true;
        }
        return false;
    }

    boolean isInvalid() {
        if ((this.mFlags & 4) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isRecyclable() {
        if ((this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView)) {
            return true;
        }
        return false;
    }

    boolean isRemoved() {
        if ((this.mFlags & 8) != 0) {
            return true;
        }
        return false;
    }

    boolean isScrap() {
        if (this.mScrapContainer != null) {
            return true;
        }
        return false;
    }

    boolean isTmpDetached() {
        if ((this.mFlags & 256) != 0) {
            return true;
        }
        return false;
    }

    boolean isUpdated() {
        if ((this.mFlags & 2) != 0) {
            return true;
        }
        return false;
    }

    boolean needsUpdate() {
        if ((this.mFlags & 2) != 0) {
            return true;
        }
        return false;
    }

    void offsetPosition(int n, boolean bl) {
        if (this.mOldPosition == -1) {
            this.mOldPosition = this.mPosition;
        }
        if (this.mPreLayoutPosition == -1) {
            this.mPreLayoutPosition = this.mPosition;
        }
        if (bl) {
            this.mPreLayoutPosition += n;
        }
        this.mPosition += n;
        if (this.itemView.getLayoutParams() != null) {
            ((RecyclerView.LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
        }
    }

    void resetInternal() {
        this.mFlags = 0;
        this.mPosition = -1;
        this.mOldPosition = -1;
        this.mItemId = -1L;
        this.mPreLayoutPosition = -1;
        this.mIsRecyclableCount = 0;
        this.mShadowedHolder = null;
        this.mShadowingHolder = null;
        this.clearPayload();
        this.mWasImportantForAccessibilityBeforeHidden = 0;
        this.mPendingAccessibilityState = -1;
        RecyclerView.clearNestedRecyclerViewIfNotNested(this);
    }

    void saveOldPosition() {
        if (this.mOldPosition == -1) {
            this.mOldPosition = this.mPosition;
        }
    }

    void setFlags(int n, int n2) {
        this.mFlags = n & n2 | this.mFlags & ~ n2;
    }

    public final void setIsRecyclable(boolean bl) {
        int n = bl ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
        this.mIsRecyclableCount = n;
        if (this.mIsRecyclableCount < 0) {
            this.mIsRecyclableCount = 0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
            stringBuilder.append(this);
            Log.e((String)"View", (String)stringBuilder.toString());
            return;
        }
        if (!bl && this.mIsRecyclableCount == 1) {
            this.mFlags |= 16;
            return;
        }
        if (bl && this.mIsRecyclableCount == 0) {
            this.mFlags &= -17;
        }
    }

    void setScrapContainer(RecyclerView.Recycler recycler, boolean bl) {
        this.mScrapContainer = recycler;
        this.mInChangeScrap = bl;
    }

    boolean shouldIgnore() {
        if ((this.mFlags & 128) != 0) {
            return true;
        }
        return false;
    }

    void stopIgnoring() {
        this.mFlags &= -129;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("ViewHolder{");
        charSequence.append(Integer.toHexString(this.hashCode()));
        charSequence.append(" position=");
        charSequence.append(this.mPosition);
        charSequence.append(" id=");
        charSequence.append(this.mItemId);
        charSequence.append(", oldPos=");
        charSequence.append(this.mOldPosition);
        charSequence.append(", pLpos:");
        charSequence.append(this.mPreLayoutPosition);
        StringBuilder stringBuilder = new StringBuilder(((StringBuilder)charSequence).toString());
        if (this.isScrap()) {
            stringBuilder.append(" scrap ");
            charSequence = this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]";
            stringBuilder.append((String)charSequence);
        }
        if (this.isInvalid()) {
            stringBuilder.append(" invalid");
        }
        if (!this.isBound()) {
            stringBuilder.append(" unbound");
        }
        if (this.needsUpdate()) {
            stringBuilder.append(" update");
        }
        if (this.isRemoved()) {
            stringBuilder.append(" removed");
        }
        if (this.shouldIgnore()) {
            stringBuilder.append(" ignored");
        }
        if (this.isTmpDetached()) {
            stringBuilder.append(" tmpDetached");
        }
        if (!this.isRecyclable()) {
            charSequence = new StringBuilder();
            charSequence.append(" not recyclable(");
            charSequence.append(this.mIsRecyclableCount);
            charSequence.append(")");
            stringBuilder.append(((StringBuilder)charSequence).toString());
        }
        if (this.isAdapterPositionUnknown()) {
            stringBuilder.append(" undefined adapter position");
        }
        if (this.itemView.getParent() == null) {
            stringBuilder.append(" no parent");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    void unScrap() {
        this.mScrapContainer.unscrapView(this);
    }

    boolean wasReturnedFromScrap() {
        if ((this.mFlags & 32) != 0) {
            return true;
        }
        return false;
    }
}
