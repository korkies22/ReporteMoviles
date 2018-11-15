/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.annotation.TargetApi
 *  android.content.ClipData
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 *  android.view.Display
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.View$DragShadowBuilder
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.WindowManager
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.v4.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

static class ViewCompat.ViewCompatBaseImpl {
    static boolean sAccessibilityDelegateCheckFailed = false;
    static Field sAccessibilityDelegateField;
    private static Method sChildrenDrawingOrderMethod;
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;
    private static Field sMinWidthField;
    private static boolean sMinWidthFieldFetched;
    private static final AtomicInteger sNextGeneratedId;
    private static WeakHashMap<View, String> sTransitionNameMap;
    private Method mDispatchFinishTemporaryDetach;
    private Method mDispatchStartTemporaryDetach;
    private boolean mTempDetachBound;
    WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

    static {
        sNextGeneratedId = new AtomicInteger(1);
    }

    ViewCompat.ViewCompatBaseImpl() {
    }

    private void bindTempDetach() {
        try {
            this.mDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
            this.mDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)ViewCompat.TAG, (String)"Couldn't find method", (Throwable)noSuchMethodException);
        }
        this.mTempDetachBound = true;
    }

    private static void tickleInvalidationFlag(View view) {
        float f = view.getTranslationY();
        view.setTranslationY(1.0f + f);
        view.setTranslationY(f);
    }

    public void addKeyboardNavigationClusters(@NonNull View view, @NonNull Collection<View> collection, int n) {
    }

    public ViewPropertyAnimatorCompat animate(View view) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        if (this.mViewPropertyAnimatorCompatMap == null) {
            this.mViewPropertyAnimatorCompatMap = new WeakHashMap();
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat = this.mViewPropertyAnimatorCompatMap.get((Object)view);
        if (viewPropertyAnimatorCompat == null) {
            viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
            this.mViewPropertyAnimatorCompatMap.put(view, viewPropertyAnimatorCompat2);
        }
        return viewPropertyAnimatorCompat2;
    }

    public void cancelDragAndDrop(View view) {
    }

    public WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return windowInsetsCompat;
    }

    public void dispatchFinishTemporaryDetach(View view) {
        if (!this.mTempDetachBound) {
            this.bindTempDetach();
        }
        if (this.mDispatchFinishTemporaryDetach != null) {
            try {
                this.mDispatchFinishTemporaryDetach.invoke((Object)view, new Object[0]);
                return;
            }
            catch (Exception exception) {
                Log.d((String)ViewCompat.TAG, (String)"Error calling dispatchFinishTemporaryDetach", (Throwable)exception);
                return;
            }
        }
        view.onFinishTemporaryDetach();
    }

    public boolean dispatchNestedFling(View view, float f, float f2, boolean bl) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).dispatchNestedFling(f, f2, bl);
        }
        return false;
    }

    public boolean dispatchNestedPreFling(View view, float f, float f2) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).dispatchNestedPreFling(f, f2);
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(View view, int n, int n2, int[] arrn, int[] arrn2) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).dispatchNestedPreScroll(n, n2, arrn, arrn2);
        }
        return false;
    }

    public boolean dispatchNestedScroll(View view, int n, int n2, int n3, int n4, int[] arrn) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).dispatchNestedScroll(n, n2, n3, n4, arrn);
        }
        return false;
    }

    public void dispatchStartTemporaryDetach(View view) {
        if (!this.mTempDetachBound) {
            this.bindTempDetach();
        }
        if (this.mDispatchStartTemporaryDetach != null) {
            try {
                this.mDispatchStartTemporaryDetach.invoke((Object)view, new Object[0]);
                return;
            }
            catch (Exception exception) {
                Log.d((String)ViewCompat.TAG, (String)"Error calling dispatchStartTemporaryDetach", (Throwable)exception);
                return;
            }
        }
        view.onStartTemporaryDetach();
    }

    public int generateViewId() {
        int n;
        int n2;
        do {
            int n3;
            n2 = sNextGeneratedId.get();
            n = n3 = n2 + 1;
            if (n3 <= 16777215) continue;
            n = 1;
        } while (!sNextGeneratedId.compareAndSet(n2, n));
        return n2;
    }

    public int getAccessibilityLiveRegion(View view) {
        return 0;
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return null;
    }

    public ColorStateList getBackgroundTintList(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView)view).getSupportBackgroundTintList();
        }
        return null;
    }

    public PorterDuff.Mode getBackgroundTintMode(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView)view).getSupportBackgroundTintMode();
        }
        return null;
    }

    public Rect getClipBounds(View view) {
        return null;
    }

    public Display getDisplay(View view) {
        if (this.isAttachedToWindow(view)) {
            return ((WindowManager)view.getContext().getSystemService("window")).getDefaultDisplay();
        }
        return null;
    }

    public float getElevation(View view) {
        return 0.0f;
    }

    public boolean getFitsSystemWindows(View view) {
        return false;
    }

    long getFrameTime() {
        return ValueAnimator.getFrameDelay();
    }

    public int getImportantForAccessibility(View view) {
        return 0;
    }

    @TargetApi(value=26)
    public int getImportantForAutofill(@NonNull View view) {
        return 0;
    }

    public int getLabelFor(View view) {
        return 0;
    }

    public int getLayoutDirection(View view) {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getMinimumHeight(View view) {
        if (!sMinHeightFieldFetched) {
            try {
                sMinHeightField = View.class.getDeclaredField("mMinHeight");
                sMinHeightField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            sMinHeightFieldFetched = true;
        }
        if (sMinHeightField == null) return 0;
        try {
            return (Integer)sMinHeightField.get((Object)view);
        }
        catch (Exception exception) {
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getMinimumWidth(View view) {
        if (!sMinWidthFieldFetched) {
            try {
                sMinWidthField = View.class.getDeclaredField("mMinWidth");
                sMinWidthField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            sMinWidthFieldFetched = true;
        }
        if (sMinWidthField == null) return 0;
        try {
            return (Integer)sMinWidthField.get((Object)view);
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public int getNextClusterForwardId(@NonNull View view) {
        return -1;
    }

    public int getPaddingEnd(View view) {
        return view.getPaddingRight();
    }

    public int getPaddingStart(View view) {
        return view.getPaddingLeft();
    }

    public ViewParent getParentForAccessibility(View view) {
        return view.getParent();
    }

    public int getScrollIndicators(View view) {
        return 0;
    }

    public String getTransitionName(View view) {
        if (sTransitionNameMap == null) {
            return null;
        }
        return sTransitionNameMap.get((Object)view);
    }

    public float getTranslationZ(View view) {
        return 0.0f;
    }

    public int getWindowSystemUiVisibility(View view) {
        return 0;
    }

    public float getZ(View view) {
        return this.getTranslationZ(view) + this.getElevation(view);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean hasAccessibilityDelegate(View object) {
        boolean bl = sAccessibilityDelegateCheckFailed;
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (sAccessibilityDelegateField == null) {
            sAccessibilityDelegateField = View.class.getDeclaredField("mAccessibilityDelegate");
            sAccessibilityDelegateField.setAccessible(true);
        }
        try {
            Object object2 = sAccessibilityDelegateField.get(object);
            if (object2 == null) return bl2;
            return true;
        }
        catch (Throwable throwable) {}
        catch (Throwable throwable) {}
        sAccessibilityDelegateCheckFailed = true;
        return false;
        sAccessibilityDelegateCheckFailed = true;
        return false;
    }

    public boolean hasExplicitFocusable(@NonNull View view) {
        return view.hasFocusable();
    }

    public boolean hasNestedScrollingParent(View view) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).hasNestedScrollingParent();
        }
        return false;
    }

    public boolean hasOnClickListeners(View view) {
        return false;
    }

    public boolean hasOverlappingRendering(View view) {
        return true;
    }

    public boolean hasTransientState(View view) {
        return false;
    }

    public boolean isAttachedToWindow(View view) {
        if (view.getWindowToken() != null) {
            return true;
        }
        return false;
    }

    public boolean isFocusedByDefault(@NonNull View view) {
        return false;
    }

    public boolean isImportantForAccessibility(View view) {
        return true;
    }

    public boolean isImportantForAutofill(@NonNull View view) {
        return true;
    }

    public boolean isInLayout(View view) {
        return false;
    }

    public boolean isKeyboardNavigationCluster(@NonNull View view) {
        return false;
    }

    public boolean isLaidOut(View view) {
        if (view.getWidth() > 0 && view.getHeight() > 0) {
            return true;
        }
        return false;
    }

    public boolean isLayoutDirectionResolved(View view) {
        return false;
    }

    public boolean isNestedScrollingEnabled(View view) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).isNestedScrollingEnabled();
        }
        return false;
    }

    public boolean isPaddingRelative(View view) {
        return false;
    }

    public View keyboardNavigationClusterSearch(@NonNull View view, View view2, int n) {
        return null;
    }

    public void offsetLeftAndRight(View view, int n) {
        view.offsetLeftAndRight(n);
        if (view.getVisibility() == 0) {
            ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(view);
            view = view.getParent();
            if (view instanceof View) {
                ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(view);
            }
        }
    }

    public void offsetTopAndBottom(View view, int n) {
        view.offsetTopAndBottom(n);
        if (view.getVisibility() == 0) {
            ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(view);
            view = view.getParent();
            if (view instanceof View) {
                ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(view);
            }
        }
    }

    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return windowInsetsCompat;
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat.unwrap());
    }

    public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
        return false;
    }

    public void postInvalidateOnAnimation(View view) {
        view.postInvalidate();
    }

    public void postInvalidateOnAnimation(View view, int n, int n2, int n3, int n4) {
        view.postInvalidate(n, n2, n3, n4);
    }

    public void postOnAnimation(View view, Runnable runnable) {
        view.postDelayed(runnable, this.getFrameTime());
    }

    public void postOnAnimationDelayed(View view, Runnable runnable, long l) {
        view.postDelayed(runnable, this.getFrameTime() + l);
    }

    public void requestApplyInsets(View view) {
    }

    public boolean restoreDefaultFocus(@NonNull View view) {
        return view.requestFocus();
    }

    public void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) {
        accessibilityDelegateCompat = accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.getBridge();
        view.setAccessibilityDelegate((View.AccessibilityDelegate)accessibilityDelegateCompat);
    }

    public void setAccessibilityLiveRegion(View view, int n) {
    }

    public /* varargs */ void setAutofillHints(@NonNull View view, @Nullable String ... arrstring) {
    }

    public void setBackground(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

    public void setBackgroundTintList(View view, ColorStateList colorStateList) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView)view).setSupportBackgroundTintList(colorStateList);
        }
    }

    public void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView)view).setSupportBackgroundTintMode(mode);
        }
    }

    public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean bl) {
        if (sChildrenDrawingOrderMethod == null) {
            try {
                sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)ViewCompat.TAG, (String)"Unable to find childrenDrawingOrderEnabled", (Throwable)noSuchMethodException);
            }
            sChildrenDrawingOrderMethod.setAccessible(true);
        }
        try {
            sChildrenDrawingOrderMethod.invoke((Object)viewGroup, bl);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)ViewCompat.TAG, (String)"Unable to invoke childrenDrawingOrderEnabled", (Throwable)invocationTargetException);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e((String)ViewCompat.TAG, (String)"Unable to invoke childrenDrawingOrderEnabled", (Throwable)illegalArgumentException);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)ViewCompat.TAG, (String)"Unable to invoke childrenDrawingOrderEnabled", (Throwable)illegalAccessException);
            return;
        }
    }

    public void setClipBounds(View view, Rect rect) {
    }

    public void setElevation(View view, float f) {
    }

    public void setFocusedByDefault(@NonNull View view, boolean bl) {
    }

    public void setHasTransientState(View view, boolean bl) {
    }

    public void setImportantForAccessibility(View view, int n) {
    }

    public void setImportantForAutofill(@NonNull View view, int n) {
    }

    public void setKeyboardNavigationCluster(@NonNull View view, boolean bl) {
    }

    public void setLabelFor(View view, int n) {
    }

    public void setLayerPaint(View view, Paint paint) {
        view.setLayerType(view.getLayerType(), paint);
        view.invalidate();
    }

    public void setLayoutDirection(View view, int n) {
    }

    public void setNestedScrollingEnabled(View view, boolean bl) {
        if (view instanceof NestedScrollingChild) {
            ((NestedScrollingChild)view).setNestedScrollingEnabled(bl);
        }
    }

    public void setNextClusterForwardId(@NonNull View view, int n) {
    }

    public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
    }

    public void setPaddingRelative(View view, int n, int n2, int n3, int n4) {
        view.setPadding(n, n2, n3, n4);
    }

    public void setPointerIcon(View view, PointerIconCompat pointerIconCompat) {
    }

    public void setScrollIndicators(View view, int n) {
    }

    public void setScrollIndicators(View view, int n, int n2) {
    }

    public void setTooltipText(View view, CharSequence charSequence) {
    }

    public void setTransitionName(View view, String string) {
        if (sTransitionNameMap == null) {
            sTransitionNameMap = new WeakHashMap();
        }
        sTransitionNameMap.put(view, string);
    }

    public void setTranslationZ(View view, float f) {
    }

    public void setZ(View view, float f) {
    }

    public boolean startDragAndDrop(View view, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object object, int n) {
        return view.startDrag(clipData, dragShadowBuilder, object, n);
    }

    public boolean startNestedScroll(View view, int n) {
        if (view instanceof NestedScrollingChild) {
            return ((NestedScrollingChild)view).startNestedScroll(n);
        }
        return false;
    }

    public void stopNestedScroll(View view) {
        if (view instanceof NestedScrollingChild) {
            ((NestedScrollingChild)view).stopNestedScroll();
        }
    }

    public void updateDragShadow(View view, View.DragShadowBuilder dragShadowBuilder) {
    }
}
