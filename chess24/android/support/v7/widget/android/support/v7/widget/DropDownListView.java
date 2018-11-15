/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.appcompat.R;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

class DropDownListView
extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private Field mIsChildViewEnabled;
    private boolean mListSelectionHidden;
    private int mMotionPosition;
    private ResolveHoverRunnable mResolveHoverRunnable;
    private ListViewAutoScrollHelper mScrollHelper;
    private int mSelectionBottomPadding = 0;
    private int mSelectionLeftPadding = 0;
    private int mSelectionRightPadding = 0;
    private int mSelectionTopPadding = 0;
    private GateKeeperDrawable mSelector;
    private final Rect mSelectorRect = new Rect();

    DropDownListView(Context context, boolean bl) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.mHijackFocus = bl;
        this.setCacheColorHint(0);
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled.setAccessible(true);
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
            return;
        }
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        this.setPressed(false);
        this.drawableStateChanged();
        View view = this.getChildAt(this.mMotionPosition - this.getFirstVisiblePosition());
        if (view != null) {
            view.setPressed(false);
        }
        if (this.mClickAnimation != null) {
            this.mClickAnimation.cancel();
            this.mClickAnimation = null;
        }
    }

    private void clickPressedItem(View view, int n) {
        this.performItemClick(view, n, this.getItemIdAtPosition(n));
    }

    private void drawSelectorCompat(Canvas canvas) {
        Drawable drawable;
        if (!this.mSelectorRect.isEmpty() && (drawable = this.getSelector()) != null) {
            drawable.setBounds(this.mSelectorRect);
            drawable.draw(canvas);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void positionSelectorCompat(int n, View view) {
        Rect rect = this.mSelectorRect;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        boolean bl = this.mIsChildViewEnabled.getBoolean((Object)this);
        if (view.isEnabled() == bl) return;
        this.mIsChildViewEnabled.set((Object)this, bl ^ true);
        if (n == -1) return;
        try {
            this.refreshDrawableState();
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    private void positionSelectorLikeFocusCompat(int n, View view) {
        Drawable drawable = this.getSelector();
        boolean bl = true;
        boolean bl2 = drawable != null && n != -1;
        if (bl2) {
            drawable.setVisible(false, false);
        }
        this.positionSelectorCompat(n, view);
        if (bl2) {
            view = this.mSelectorRect;
            float f = view.exactCenterX();
            float f2 = view.exactCenterY();
            if (this.getVisibility() != 0) {
                bl = false;
            }
            drawable.setVisible(bl, false);
            DrawableCompat.setHotspot(drawable, f, f2);
        }
    }

    private void positionSelectorLikeTouchCompat(int n, View view, float f, float f2) {
        this.positionSelectorLikeFocusCompat(n, view);
        view = this.getSelector();
        if (view != null && n != -1) {
            DrawableCompat.setHotspot((Drawable)view, f, f2);
        }
    }

    private void setPressedItem(View view, int n, float f, float f2) {
        View view2;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
            this.drawableHotspotChanged(f, f2);
        }
        if (!this.isPressed()) {
            this.setPressed(true);
        }
        this.layoutChildren();
        if (this.mMotionPosition != -1 && (view2 = this.getChildAt(this.mMotionPosition - this.getFirstVisiblePosition())) != null && view2 != view && view2.isPressed()) {
            view2.setPressed(false);
        }
        this.mMotionPosition = n;
        float f3 = view.getLeft();
        float f4 = view.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(f - f3, f2 - f4);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        this.positionSelectorLikeTouchCompat(n, view, f, f2);
        this.setSelectorEnabled(false);
        this.refreshDrawableState();
    }

    private void setSelectorEnabled(boolean bl) {
        if (this.mSelector != null) {
            this.mSelector.setEnabled(bl);
        }
    }

    private boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState;
    }

    private void updateSelectorStateCompat() {
        Drawable drawable = this.getSelector();
        if (drawable != null && this.touchModeDrawsInPressedStateCompat() && this.isPressed()) {
            drawable.setState(this.getDrawableState());
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        this.drawSelectorCompat(canvas);
        super.dispatchDraw(canvas);
    }

    protected void drawableStateChanged() {
        if (this.mResolveHoverRunnable != null) {
            return;
        }
        super.drawableStateChanged();
        this.setSelectorEnabled(true);
        this.updateSelectorStateCompat();
    }

    public boolean hasFocus() {
        if (!this.mHijackFocus && !super.hasFocus()) {
            return false;
        }
        return true;
    }

    public boolean hasWindowFocus() {
        if (!this.mHijackFocus && !super.hasWindowFocus()) {
            return false;
        }
        return true;
    }

    public boolean isFocused() {
        if (!this.mHijackFocus && !super.isFocused()) {
            return false;
        }
        return true;
    }

    public boolean isInTouchMode() {
        if (this.mHijackFocus && this.mListSelectionHidden || super.isInTouchMode()) {
            return true;
        }
        return false;
    }

    public int lookForSelectablePosition(int n, boolean bl) {
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter != null) {
            if (this.isInTouchMode()) {
                return -1;
            }
            int n2 = listAdapter.getCount();
            if (!this.getAdapter().areAllItemsEnabled()) {
                int n3;
                if (bl) {
                    n = Math.max(0, n);
                    do {
                        n3 = n;
                        if (n < n2) {
                            n3 = n;
                            if (!listAdapter.isEnabled(n)) {
                                ++n;
                                continue;
                            }
                        }
                        break;
                    } while (true);
                } else {
                    n = Math.min(n, n2 - 1);
                    do {
                        n3 = n;
                        if (n < 0) break;
                        n3 = n;
                        if (listAdapter.isEnabled(n)) break;
                        --n;
                    } while (true);
                }
                if (n3 >= 0) {
                    if (n3 >= n2) {
                        return -1;
                    }
                    return n3;
                }
                return -1;
            }
            if (n >= 0) {
                if (n >= n2) {
                    return -1;
                }
                return n;
            }
            return -1;
        }
        return -1;
    }

    public int measureHeightOfChildrenCompat(int n, int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        n2 = this.getListPaddingTop();
        n3 = this.getListPaddingBottom();
        this.getListPaddingLeft();
        this.getListPaddingRight();
        int n8 = this.getDividerHeight();
        Drawable drawable = this.getDivider();
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) {
            return n2 + n3;
        }
        if (n8 <= 0 || drawable == null) {
            n8 = 0;
        }
        int n9 = listAdapter.getCount();
        n3 = n2 + n3;
        n2 = n6 = (n7 = 0);
        drawable = null;
        int n10 = n6;
        while (n7 < n9) {
            int n11 = listAdapter.getItemViewType(n7);
            n6 = n10;
            if (n11 != n10) {
                drawable = null;
                n6 = n11;
            }
            View view = listAdapter.getView(n7, (View)drawable, (ViewGroup)this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            drawable = layoutParams;
            if (layoutParams == null) {
                drawable = this.generateDefaultLayoutParams();
                view.setLayoutParams((ViewGroup.LayoutParams)drawable);
            }
            n10 = drawable.height > 0 ? View.MeasureSpec.makeMeasureSpec((int)drawable.height, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            view.measure(n, n10);
            view.forceLayout();
            n10 = n3;
            if (n7 > 0) {
                n10 = n3 + n8;
            }
            if ((n3 = n10 + view.getMeasuredHeight()) >= n4) {
                n = n4;
                if (n5 >= 0) {
                    n = n4;
                    if (n7 > n5) {
                        n = n4;
                        if (n2 > 0) {
                            n = n4;
                            if (n3 != n4) {
                                n = n2;
                            }
                        }
                    }
                }
                return n;
            }
            n11 = n2;
            if (n5 >= 0) {
                n11 = n2;
                if (n7 >= n5) {
                    n11 = n3;
                }
            }
            ++n7;
            n10 = n6;
            drawable = view;
            n2 = n11;
        }
        return n3;
    }

    protected void onDetachedFromWindow() {
        this.mResolveHoverRunnable = null;
        super.onDetachedFromWindow();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onForwardedEvent(MotionEvent var1_1, int var2_2) {
        block12 : {
            block11 : {
                var3_3 = var1_1.getActionMasked();
                switch (var3_3) {
                    default: {
                        ** GOTO lbl26
                    }
                    case 3: {
                        break;
                    }
                    case 2: {
                        var6_4 = true;
                        ** break;
                    }
                    case 1: {
                        var6_4 = false;
lbl12: // 2 sources:
                        var4_6 = var1_1.findPointerIndex(var2_2);
                        if (var4_6 >= 0) break block11;
                    }
                }
                var6_4 = var7_5 = false;
                break block12;
            }
            var2_2 = (int)var1_1.getX(var4_6);
            var5_7 = this.pointToPosition(var2_2, var4_6 = (int)var1_1.getY(var4_6));
            if (var5_7 == -1) {
                var7_5 = true;
            } else {
                var8_8 = this.getChildAt(var5_7 - this.getFirstVisiblePosition());
                this.setPressedItem(var8_8, var5_7, var2_2, var4_6);
                if (var3_3 == 1) {
                    this.clickPressedItem(var8_8, var5_7);
                }
lbl26: // 4 sources:
                var7_5 = false;
                var6_4 = true;
            }
        }
        if (!var6_4 || var7_5) {
            this.clearPressedItem();
        }
        if (!var6_4) {
            if (this.mScrollHelper == null) return var6_4;
            this.mScrollHelper.setEnabled(false);
            return var6_4;
        }
        if (this.mScrollHelper == null) {
            this.mScrollHelper = new ListViewAutoScrollHelper(this);
        }
        this.mScrollHelper.setEnabled(true);
        this.mScrollHelper.onTouch((View)this, var1_1);
        return var6_4;
    }

    public boolean onHoverEvent(@NonNull MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(motionEvent);
        }
        int n = motionEvent.getActionMasked();
        if (n == 10 && this.mResolveHoverRunnable == null) {
            this.mResolveHoverRunnable = new ResolveHoverRunnable();
            this.mResolveHoverRunnable.post();
        }
        boolean bl = super.onHoverEvent(motionEvent);
        if (n != 9 && n != 7) {
            this.setSelection(-1);
            return bl;
        }
        n = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        if (n != -1 && n != this.getSelectedItemPosition()) {
            motionEvent = this.getChildAt(n - this.getFirstVisiblePosition());
            if (motionEvent.isEnabled()) {
                this.setSelectionFromTop(n, motionEvent.getTop() - this.getTop());
            }
            this.updateSelectorStateCompat();
        }
        return bl;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mMotionPosition = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        }
        if (this.mResolveHoverRunnable != null) {
            this.mResolveHoverRunnable.cancel();
        }
        return super.onTouchEvent(motionEvent);
    }

    void setListSelectionHidden(boolean bl) {
        this.mListSelectionHidden = bl;
    }

    public void setSelector(Drawable drawable) {
        GateKeeperDrawable gateKeeperDrawable = drawable != null ? new GateKeeperDrawable(drawable) : null;
        this.mSelector = gateKeeperDrawable;
        super.setSelector((Drawable)this.mSelector);
        gateKeeperDrawable = new Rect();
        if (drawable != null) {
            drawable.getPadding((Rect)gateKeeperDrawable);
        }
        this.mSelectionLeftPadding = gateKeeperDrawable.left;
        this.mSelectionTopPadding = gateKeeperDrawable.top;
        this.mSelectionRightPadding = gateKeeperDrawable.right;
        this.mSelectionBottomPadding = gateKeeperDrawable.bottom;
    }

    private static class GateKeeperDrawable
    extends DrawableWrapper {
        private boolean mEnabled = true;

        GateKeeperDrawable(Drawable drawable) {
            super(drawable);
        }

        @Override
        public void draw(Canvas canvas) {
            if (this.mEnabled) {
                super.draw(canvas);
            }
        }

        void setEnabled(boolean bl) {
            this.mEnabled = bl;
        }

        @Override
        public void setHotspot(float f, float f2) {
            if (this.mEnabled) {
                super.setHotspot(f, f2);
            }
        }

        @Override
        public void setHotspotBounds(int n, int n2, int n3, int n4) {
            if (this.mEnabled) {
                super.setHotspotBounds(n, n2, n3, n4);
            }
        }

        @Override
        public boolean setState(int[] arrn) {
            if (this.mEnabled) {
                return super.setState(arrn);
            }
            return false;
        }

        @Override
        public boolean setVisible(boolean bl, boolean bl2) {
            if (this.mEnabled) {
                return super.setVisible(bl, bl2);
            }
            return false;
        }
    }

    private class ResolveHoverRunnable
    implements Runnable {
        private ResolveHoverRunnable() {
        }

        public void cancel() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.removeCallbacks((Runnable)this);
        }

        public void post() {
            DropDownListView.this.post((Runnable)this);
        }

        @Override
        public void run() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.drawableStateChanged();
        }
    }

}
