/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import java.util.Arrays;

class StaggeredGridLayoutManager.AnchorInfo {
    boolean mInvalidateOffsets;
    boolean mLayoutFromEnd;
    int mOffset;
    int mPosition;
    int[] mSpanReferenceLines;
    boolean mValid;

    StaggeredGridLayoutManager.AnchorInfo() {
        this.reset();
    }

    void assignCoordinateFromPadding() {
        int n = this.mLayoutFromEnd ? StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() : StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
        this.mOffset = n;
    }

    void assignCoordinateFromPadding(int n) {
        if (this.mLayoutFromEnd) {
            this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - n;
            return;
        }
        this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + n;
    }

    void reset() {
        this.mPosition = -1;
        this.mOffset = Integer.MIN_VALUE;
        this.mLayoutFromEnd = false;
        this.mInvalidateOffsets = false;
        this.mValid = false;
        if (this.mSpanReferenceLines != null) {
            Arrays.fill(this.mSpanReferenceLines, -1);
        }
    }

    void saveSpanReferenceLines(StaggeredGridLayoutManager.Span[] arrspan) {
        int n = arrspan.length;
        if (this.mSpanReferenceLines == null || this.mSpanReferenceLines.length < n) {
            this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length];
        }
        for (int i = 0; i < n; ++i) {
            this.mSpanReferenceLines[i] = arrspan[i].getStartLine(Integer.MIN_VALUE);
        }
    }
}
