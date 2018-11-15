/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package android.support.transition;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.transition.ViewOverlayApi14;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

static class ViewOverlayApi14.OverlayViewGroup
extends ViewGroup {
    static Method sInvalidateChildInParentFastMethod;
    ArrayList<Drawable> mDrawables = null;
    ViewGroup mHostView;
    View mRequestingView;
    ViewOverlayApi14 mViewOverlay;

    static {
        try {
            sInvalidateChildInParentFastMethod = ViewGroup.class.getDeclaredMethod("invalidateChildInParentFast", Integer.TYPE, Integer.TYPE, Rect.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {}
    }

    ViewOverlayApi14.OverlayViewGroup(Context context, ViewGroup viewGroup, View view, ViewOverlayApi14 viewOverlayApi14) {
        super(context);
        this.mHostView = viewGroup;
        this.mRequestingView = view;
        this.setRight(viewGroup.getWidth());
        this.setBottom(viewGroup.getHeight());
        viewGroup.addView((View)this);
        this.mViewOverlay = viewOverlayApi14;
    }

    private void getOffset(int[] arrn) {
        int[] arrn2 = new int[2];
        int[] arrn3 = new int[2];
        this.mHostView.getLocationOnScreen(arrn2);
        this.mRequestingView.getLocationOnScreen(arrn3);
        arrn[0] = arrn3[0] - arrn2[0];
        arrn[1] = arrn3[1] - arrn2[1];
    }

    public void add(Drawable drawable) {
        if (this.mDrawables == null) {
            this.mDrawables = new ArrayList();
        }
        if (!this.mDrawables.contains((Object)drawable)) {
            this.mDrawables.add(drawable);
            this.invalidate(drawable.getBounds());
            drawable.setCallback((Drawable.Callback)this);
        }
    }

    public void add(View view) {
        if (view.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view.getParent();
            if (viewGroup != this.mHostView && viewGroup.getParent() != null && ViewCompat.isAttachedToWindow((View)viewGroup)) {
                int[] arrn = new int[2];
                int[] arrn2 = new int[2];
                viewGroup.getLocationOnScreen(arrn);
                this.mHostView.getLocationOnScreen(arrn2);
                ViewCompat.offsetLeftAndRight(view, arrn[0] - arrn2[0]);
                ViewCompat.offsetTopAndBottom(view, arrn[1] - arrn2[1]);
            }
            viewGroup.removeView(view);
            if (view.getParent() != null) {
                viewGroup.removeView(view);
            }
        }
        super.addView(view, this.getChildCount() - 1);
    }

    public void clear() {
        this.removeAllViews();
        if (this.mDrawables != null) {
            this.mDrawables.clear();
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        int[] arrn = new int[2];
        int[] arrn2 = new int[2];
        this.mHostView.getLocationOnScreen(arrn);
        this.mRequestingView.getLocationOnScreen(arrn2);
        canvas.translate((float)(arrn2[0] - arrn[0]), (float)(arrn2[1] - arrn[1]));
        canvas.clipRect(new Rect(0, 0, this.mRequestingView.getWidth(), this.mRequestingView.getHeight()));
        super.dispatchDraw(canvas);
        int n = this.mDrawables == null ? 0 : this.mDrawables.size();
        for (int i = 0; i < n; ++i) {
            this.mDrawables.get(i).draw(canvas);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void invalidateChildFast(View arrn, Rect rect) {
        if (this.mHostView != null) {
            int n = arrn.getLeft();
            int n2 = arrn.getTop();
            arrn = new int[2];
            this.getOffset(arrn);
            rect.offset(n + arrn[0], n2 + arrn[1]);
            this.mHostView.invalidate(rect);
        }
    }

    public ViewParent invalidateChildInParent(int[] arrn, Rect rect) {
        if (this.mHostView != null) {
            rect.offset(arrn[0], arrn[1]);
            if (this.mHostView instanceof ViewGroup) {
                arrn[0] = 0;
                arrn[1] = 0;
                int[] arrn2 = new int[2];
                this.getOffset(arrn2);
                rect.offset(arrn2[0], arrn2[1]);
                return super.invalidateChildInParent(arrn, rect);
            }
            this.invalidate(rect);
        }
        return null;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected ViewParent invalidateChildInParentFast(int n, int n2, Rect rect) {
        if (this.mHostView instanceof ViewGroup && sInvalidateChildInParentFastMethod != null) {
            try {
                this.getOffset(new int[2]);
                sInvalidateChildInParentFastMethod.invoke((Object)this.mHostView, new Object[]{n, n2, rect});
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        return null;
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        this.invalidate(drawable.getBounds());
    }

    boolean isEmpty() {
        if (this.getChildCount() == 0 && (this.mDrawables == null || this.mDrawables.size() == 0)) {
            return true;
        }
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
    }

    public void remove(Drawable drawable) {
        if (this.mDrawables != null) {
            this.mDrawables.remove((Object)drawable);
            this.invalidate(drawable.getBounds());
            drawable.setCallback(null);
        }
    }

    public void remove(View view) {
        super.removeView(view);
        if (this.isEmpty()) {
            this.mHostView.removeView((View)this);
        }
    }

    protected boolean verifyDrawable(@NonNull Drawable drawable) {
        if (!(super.verifyDrawable(drawable) || this.mDrawables != null && this.mDrawables.contains((Object)drawable))) {
            return false;
        }
        return true;
    }

    static class TouchInterceptor
    extends View {
        TouchInterceptor(Context context) {
            super(context);
        }
    }

}
