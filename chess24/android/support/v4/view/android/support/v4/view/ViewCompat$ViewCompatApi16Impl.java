/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package android.support.v4.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeProvider;

@RequiresApi(value=16)
static class ViewCompat.ViewCompatApi16Impl
extends ViewCompat.ViewCompatApi15Impl {
    ViewCompat.ViewCompatApi16Impl() {
    }

    @Override
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if ((view = view.getAccessibilityNodeProvider()) != null) {
            return new AccessibilityNodeProviderCompat((Object)view);
        }
        return null;
    }

    @Override
    public boolean getFitsSystemWindows(View view) {
        return view.getFitsSystemWindows();
    }

    @Override
    public int getImportantForAccessibility(View view) {
        return view.getImportantForAccessibility();
    }

    @Override
    public int getMinimumHeight(View view) {
        return view.getMinimumHeight();
    }

    @Override
    public int getMinimumWidth(View view) {
        return view.getMinimumWidth();
    }

    @Override
    public ViewParent getParentForAccessibility(View view) {
        return view.getParentForAccessibility();
    }

    @Override
    public boolean hasOverlappingRendering(View view) {
        return view.hasOverlappingRendering();
    }

    @Override
    public boolean hasTransientState(View view) {
        return view.hasTransientState();
    }

    @Override
    public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
        return view.performAccessibilityAction(n, bundle);
    }

    @Override
    public void postInvalidateOnAnimation(View view) {
        view.postInvalidateOnAnimation();
    }

    @Override
    public void postInvalidateOnAnimation(View view, int n, int n2, int n3, int n4) {
        view.postInvalidateOnAnimation(n, n2, n3, n4);
    }

    @Override
    public void postOnAnimation(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    @Override
    public void postOnAnimationDelayed(View view, Runnable runnable, long l) {
        view.postOnAnimationDelayed(runnable, l);
    }

    @Override
    public void requestApplyInsets(View view) {
        view.requestFitSystemWindows();
    }

    @Override
    public void setBackground(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    @Override
    public void setHasTransientState(View view, boolean bl) {
        view.setHasTransientState(bl);
    }

    @Override
    public void setImportantForAccessibility(View view, int n) {
        int n2 = n;
        if (n == 4) {
            n2 = 2;
        }
        view.setImportantForAccessibility(n2);
    }
}
