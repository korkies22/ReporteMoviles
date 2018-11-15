/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;

public static abstract class ViewDragHelper.Callback {
    public int clampViewPositionHorizontal(@NonNull View view, int n, int n2) {
        return 0;
    }

    public int clampViewPositionVertical(@NonNull View view, int n, int n2) {
        return 0;
    }

    public int getOrderedChildIndex(int n) {
        return n;
    }

    public int getViewHorizontalDragRange(@NonNull View view) {
        return 0;
    }

    public int getViewVerticalDragRange(@NonNull View view) {
        return 0;
    }

    public void onEdgeDragStarted(int n, int n2) {
    }

    public boolean onEdgeLock(int n) {
        return false;
    }

    public void onEdgeTouched(int n, int n2) {
    }

    public void onViewCaptured(@NonNull View view, int n) {
    }

    public void onViewDragStateChanged(int n) {
    }

    public void onViewPositionChanged(@NonNull View view, int n, int n2, int n3, int n4) {
    }

    public void onViewReleased(@NonNull View view, float f, float f2) {
    }

    public abstract boolean tryCaptureView(@NonNull View var1, int var2);
}
