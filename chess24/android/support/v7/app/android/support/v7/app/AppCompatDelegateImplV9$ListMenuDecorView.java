/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 */
package android.support.v7.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ContentFrameLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;

private class AppCompatDelegateImplV9.ListMenuDecorView
extends ContentFrameLayout {
    public AppCompatDelegateImplV9.ListMenuDecorView(Context context) {
        super(context);
    }

    private boolean isOutOfBounds(int n, int n2) {
        if (n >= -5 && n2 >= -5 && n <= this.getWidth() + 5 && n2 <= this.getHeight() + 5) {
            return false;
        }
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!AppCompatDelegateImplV9.this.dispatchKeyEvent(keyEvent) && !super.dispatchKeyEvent(keyEvent)) {
            return false;
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.isOutOfBounds((int)motionEvent.getX(), (int)motionEvent.getY())) {
            AppCompatDelegateImplV9.this.closePanel(0);
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setBackgroundResource(int n) {
        this.setBackgroundDrawable(AppCompatResources.getDrawable(this.getContext(), n));
    }
}
