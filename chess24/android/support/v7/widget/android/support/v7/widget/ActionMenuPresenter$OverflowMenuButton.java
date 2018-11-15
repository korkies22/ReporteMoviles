/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPopup;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.view.View;

private class ActionMenuPresenter.OverflowMenuButton
extends AppCompatImageView
implements ActionMenuView.ActionMenuChildView {
    private final float[] mTempPts;

    public ActionMenuPresenter.OverflowMenuButton(Context context) {
        super(context, null, R.attr.actionOverflowButtonStyle);
        this.mTempPts = new float[2];
        this.setClickable(true);
        this.setFocusable(true);
        this.setVisibility(0);
        this.setEnabled(true);
        TooltipCompat.setTooltipText((View)this, this.getContentDescription());
        this.setOnTouchListener((View.OnTouchListener)new ForwardingListener((View)this, ActionMenuPresenter.this){
            final /* synthetic */ ActionMenuPresenter val$this$0;
            {
                this.val$this$0 = actionMenuPresenter;
                super(view);
            }

            @Override
            public ShowableListMenu getPopup() {
                if (ActionMenuPresenter.this.mOverflowPopup == null) {
                    return null;
                }
                return ActionMenuPresenter.this.mOverflowPopup.getPopup();
            }

            @Override
            public boolean onForwardingStarted() {
                ActionMenuPresenter.this.showOverflowMenu();
                return true;
            }

            @Override
            public boolean onForwardingStopped() {
                if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                    return false;
                }
                ActionMenuPresenter.this.hideOverflowMenu();
                return true;
            }
        });
    }

    @Override
    public boolean needsDividerAfter() {
        return false;
    }

    @Override
    public boolean needsDividerBefore() {
        return false;
    }

    public boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        this.playSoundEffect(0);
        ActionMenuPresenter.this.showOverflowMenu();
        return true;
    }

    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        Drawable drawable = this.getDrawable();
        Drawable drawable2 = this.getBackground();
        if (drawable != null && drawable2 != null) {
            int n5 = this.getWidth();
            n2 = this.getHeight();
            n = Math.max(n5, n2) / 2;
            int n6 = this.getPaddingLeft();
            int n7 = this.getPaddingRight();
            n3 = this.getPaddingTop();
            n4 = this.getPaddingBottom();
            n5 = (n5 + (n6 - n7)) / 2;
            n2 = (n2 + (n3 - n4)) / 2;
            DrawableCompat.setHotspotBounds(drawable2, n5 - n, n2 - n, n5 + n, n2 + n);
        }
        return bl;
    }

}
