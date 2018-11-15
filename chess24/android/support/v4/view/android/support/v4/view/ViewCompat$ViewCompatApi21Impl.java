/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewParent
 *  android.view.WindowInsets
 */
package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowInsets;

@RequiresApi(value=21)
static class ViewCompat.ViewCompatApi21Impl
extends ViewCompat.ViewCompatApi19Impl {
    private static ThreadLocal<Rect> sThreadLocalRect;

    ViewCompat.ViewCompatApi21Impl() {
    }

    private static Rect getEmptyTempRect() {
        Rect rect;
        if (sThreadLocalRect == null) {
            sThreadLocalRect = new ThreadLocal();
        }
        Rect rect2 = rect = sThreadLocalRect.get();
        if (rect == null) {
            rect2 = new Rect();
            sThreadLocalRect.set(rect2);
        }
        rect2.setEmpty();
        return rect2;
    }

    @Override
    public WindowInsetsCompat dispatchApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
        windowInsetsCompat = (WindowInsets)WindowInsetsCompat.unwrap(windowInsetsCompat);
        WindowInsets windowInsets = object.dispatchApplyWindowInsets((WindowInsets)windowInsetsCompat);
        object = windowInsetsCompat;
        if (windowInsets != windowInsetsCompat) {
            object = new WindowInsets(windowInsets);
        }
        return WindowInsetsCompat.wrap(object);
    }

    @Override
    public boolean dispatchNestedFling(View view, float f, float f2, boolean bl) {
        return view.dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean dispatchNestedPreFling(View view, float f, float f2) {
        return view.dispatchNestedPreFling(f, f2);
    }

    @Override
    public boolean dispatchNestedPreScroll(View view, int n, int n2, int[] arrn, int[] arrn2) {
        return view.dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    @Override
    public boolean dispatchNestedScroll(View view, int n, int n2, int n3, int n4, int[] arrn) {
        return view.dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    @Override
    public ColorStateList getBackgroundTintList(View view) {
        return view.getBackgroundTintList();
    }

    @Override
    public PorterDuff.Mode getBackgroundTintMode(View view) {
        return view.getBackgroundTintMode();
    }

    @Override
    public float getElevation(View view) {
        return view.getElevation();
    }

    @Override
    public String getTransitionName(View view) {
        return view.getTransitionName();
    }

    @Override
    public float getTranslationZ(View view) {
        return view.getTranslationZ();
    }

    @Override
    public float getZ(View view) {
        return view.getZ();
    }

    @Override
    public boolean hasNestedScrollingParent(View view) {
        return view.hasNestedScrollingParent();
    }

    @Override
    public boolean isImportantForAccessibility(View view) {
        return view.isImportantForAccessibility();
    }

    @Override
    public boolean isNestedScrollingEnabled(View view) {
        return view.isNestedScrollingEnabled();
    }

    @Override
    public void offsetLeftAndRight(View view, int n) {
        boolean bl;
        Rect rect = ViewCompat.ViewCompatApi21Impl.getEmptyTempRect();
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof View) {
            View view2 = (View)viewParent;
            rect.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
            bl = rect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()) ^ true;
        } else {
            bl = false;
        }
        super.offsetLeftAndRight(view, n);
        if (bl && rect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
            ((View)viewParent).invalidate(rect);
        }
    }

    @Override
    public void offsetTopAndBottom(View view, int n) {
        boolean bl;
        Rect rect = ViewCompat.ViewCompatApi21Impl.getEmptyTempRect();
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof View) {
            View view2 = (View)viewParent;
            rect.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
            bl = rect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()) ^ true;
        } else {
            bl = false;
        }
        super.offsetTopAndBottom(view, n);
        if (bl && rect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
            ((View)viewParent).invalidate(rect);
        }
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
        windowInsetsCompat = (WindowInsets)WindowInsetsCompat.unwrap(windowInsetsCompat);
        WindowInsets windowInsets = object.onApplyWindowInsets((WindowInsets)windowInsetsCompat);
        object = windowInsetsCompat;
        if (windowInsets != windowInsetsCompat) {
            object = new WindowInsets(windowInsets);
        }
        return WindowInsetsCompat.wrap(object);
    }

    @Override
    public void requestApplyInsets(View view) {
        view.requestApplyInsets();
    }

    @Override
    public void setBackgroundTintList(View view, ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        if (Build.VERSION.SDK_INT == 21) {
            colorStateList = view.getBackground();
            boolean bl = view.getBackgroundTintList() != null || view.getBackgroundTintMode() != null;
            if (colorStateList != null && bl) {
                if (colorStateList.isStateful()) {
                    colorStateList.setState(view.getDrawableState());
                }
                view.setBackground((Drawable)colorStateList);
            }
        }
    }

    @Override
    public void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
        if (Build.VERSION.SDK_INT == 21) {
            mode = view.getBackground();
            boolean bl = view.getBackgroundTintList() != null || view.getBackgroundTintMode() != null;
            if (mode != null && bl) {
                if (mode.isStateful()) {
                    mode.setState(view.getDrawableState());
                }
                view.setBackground((Drawable)mode);
            }
        }
    }

    @Override
    public void setElevation(View view, float f) {
        view.setElevation(f);
    }

    @Override
    public void setNestedScrollingEnabled(View view, boolean bl) {
        view.setNestedScrollingEnabled(bl);
    }

    @Override
    public void setOnApplyWindowInsetsListener(View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        if (onApplyWindowInsetsListener == null) {
            view.setOnApplyWindowInsetsListener(null);
            return;
        }
        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(){

            public WindowInsets onApplyWindowInsets(View view, WindowInsets object) {
                object = WindowInsetsCompat.wrap(object);
                return (WindowInsets)WindowInsetsCompat.unwrap(onApplyWindowInsetsListener.onApplyWindowInsets(view, (WindowInsetsCompat)object));
            }
        });
    }

    @Override
    public void setTransitionName(View view, String string) {
        view.setTransitionName(string);
    }

    @Override
    public void setTranslationZ(View view, float f) {
        view.setTranslationZ(f);
    }

    @Override
    public void setZ(View view, float f) {
        view.setZ(f);
    }

    @Override
    public boolean startNestedScroll(View view, int n) {
        return view.startNestedScroll(n);
    }

    @Override
    public void stopNestedScroll(View view) {
        view.stopNestedScroll();
    }

}
