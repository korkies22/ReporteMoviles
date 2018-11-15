/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  android.view.TouchDelegate
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;

private static class SearchView.UpdatableTouchDelegate
extends TouchDelegate {
    private final Rect mActualBounds;
    private boolean mDelegateTargeted;
    private final View mDelegateView;
    private final int mSlop;
    private final Rect mSlopBounds;
    private final Rect mTargetBounds;

    public SearchView.UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
        super(rect, view);
        this.mSlop = ViewConfiguration.get((Context)view.getContext()).getScaledTouchSlop();
        this.mTargetBounds = new Rect();
        this.mSlopBounds = new Rect();
        this.mActualBounds = new Rect();
        this.setBounds(rect, rect2);
        this.mDelegateView = view;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n;
        int n2;
        int n3;
        boolean bl;
        boolean bl2;
        block10 : {
            n2 = (int)motionEvent.getX();
            n = (int)motionEvent.getY();
            n3 = motionEvent.getAction();
            int n4 = 1;
            bl2 = false;
            switch (n3) {
                default: {
                    break;
                }
                case 3: {
                    bl = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                    n3 = n4;
                    break block10;
                }
                case 1: 
                case 2: {
                    boolean bl3;
                    bl = bl3 = this.mDelegateTargeted;
                    n3 = n4;
                    if (bl3) {
                        bl = bl3;
                        n3 = n4;
                        if (!this.mSlopBounds.contains(n2, n)) {
                            n3 = 0;
                            bl = bl3;
                        }
                    }
                    break block10;
                }
                case 0: {
                    if (!this.mTargetBounds.contains(n2, n)) break;
                    this.mDelegateTargeted = true;
                    bl = true;
                    n3 = n4;
                    break block10;
                }
            }
            bl = false;
            n3 = n4;
        }
        if (bl) {
            if (n3 != 0 && !this.mActualBounds.contains(n2, n)) {
                motionEvent.setLocation((float)(this.mDelegateView.getWidth() / 2), (float)(this.mDelegateView.getHeight() / 2));
            } else {
                motionEvent.setLocation((float)(n2 - this.mActualBounds.left), (float)(n - this.mActualBounds.top));
            }
            bl2 = this.mDelegateView.dispatchTouchEvent(motionEvent);
        }
        return bl2;
    }

    public void setBounds(Rect rect, Rect rect2) {
        this.mTargetBounds.set(rect);
        this.mSlopBounds.set(rect);
        this.mSlopBounds.inset(- this.mSlop, - this.mSlop);
        this.mActualBounds.set(rect2);
    }
}
