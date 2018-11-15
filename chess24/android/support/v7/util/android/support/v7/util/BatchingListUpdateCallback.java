/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.ListUpdateCallback;

public class BatchingListUpdateCallback
implements ListUpdateCallback {
    private static final int TYPE_ADD = 1;
    private static final int TYPE_CHANGE = 3;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_REMOVE = 2;
    int mLastEventCount = -1;
    Object mLastEventPayload = null;
    int mLastEventPosition = -1;
    int mLastEventType = 0;
    final ListUpdateCallback mWrapped;

    public BatchingListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        this.mWrapped = listUpdateCallback;
    }

    public void dispatchLastEvent() {
        if (this.mLastEventType == 0) {
            return;
        }
        switch (this.mLastEventType) {
            default: {
                break;
            }
            case 3: {
                this.mWrapped.onChanged(this.mLastEventPosition, this.mLastEventCount, this.mLastEventPayload);
                break;
            }
            case 2: {
                this.mWrapped.onRemoved(this.mLastEventPosition, this.mLastEventCount);
                break;
            }
            case 1: {
                this.mWrapped.onInserted(this.mLastEventPosition, this.mLastEventCount);
            }
        }
        this.mLastEventPayload = null;
        this.mLastEventType = 0;
    }

    @Override
    public void onChanged(int n, int n2, Object object) {
        int n3;
        if (this.mLastEventType == 3 && n <= this.mLastEventPosition + this.mLastEventCount && (n3 = n + n2) >= this.mLastEventPosition && this.mLastEventPayload == object) {
            n2 = this.mLastEventPosition;
            int n4 = this.mLastEventCount;
            this.mLastEventPosition = Math.min(n, this.mLastEventPosition);
            this.mLastEventCount = Math.max(n2 + n4, n3) - this.mLastEventPosition;
            return;
        }
        this.dispatchLastEvent();
        this.mLastEventPosition = n;
        this.mLastEventCount = n2;
        this.mLastEventPayload = object;
        this.mLastEventType = 3;
    }

    @Override
    public void onInserted(int n, int n2) {
        if (this.mLastEventType == 1 && n >= this.mLastEventPosition && n <= this.mLastEventPosition + this.mLastEventCount) {
            this.mLastEventCount += n2;
            this.mLastEventPosition = Math.min(n, this.mLastEventPosition);
            return;
        }
        this.dispatchLastEvent();
        this.mLastEventPosition = n;
        this.mLastEventCount = n2;
        this.mLastEventType = 1;
    }

    @Override
    public void onMoved(int n, int n2) {
        this.dispatchLastEvent();
        this.mWrapped.onMoved(n, n2);
    }

    @Override
    public void onRemoved(int n, int n2) {
        if (this.mLastEventType == 2 && this.mLastEventPosition >= n && this.mLastEventPosition <= n + n2) {
            this.mLastEventCount += n2;
            this.mLastEventPosition = n;
            return;
        }
        this.dispatchLastEvent();
        this.mLastEventPosition = n;
        this.mLastEventCount = n2;
        this.mLastEventType = 2;
    }
}
