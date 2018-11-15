/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.design.widget;

import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.ViewOffsetHelper;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.view.ViewGroup;

private class CollapsingToolbarLayout.OffsetUpdateListener
implements AppBarLayout.OnOffsetChangedListener {
    CollapsingToolbarLayout.OffsetUpdateListener() {
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int n) {
        int n2;
        CollapsingToolbarLayout.this.mCurrentOffset = n;
        int n3 = CollapsingToolbarLayout.this.mLastInsets != null ? CollapsingToolbarLayout.this.mLastInsets.getSystemWindowInsetTop() : 0;
        int n4 = CollapsingToolbarLayout.this.getChildCount();
        block4 : for (n2 = 0; n2 < n4; ++n2) {
            appBarLayout = CollapsingToolbarLayout.this.getChildAt(n2);
            CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams)appBarLayout.getLayoutParams();
            ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper((View)appBarLayout);
            switch (layoutParams.mCollapseMode) {
                default: {
                    continue block4;
                }
                case 2: {
                    viewOffsetHelper.setTopAndBottomOffset(Math.round((float)(- n) * layoutParams.mParallaxMult));
                    continue block4;
                }
                case 1: {
                    viewOffsetHelper.setTopAndBottomOffset(MathUtils.clamp(- n, 0, CollapsingToolbarLayout.this.getMaxOffsetForPinChild((View)appBarLayout)));
                }
            }
        }
        CollapsingToolbarLayout.this.updateScrimVisibility();
        if (CollapsingToolbarLayout.this.mStatusBarScrim != null && n3 > 0) {
            ViewCompat.postInvalidateOnAnimation((View)CollapsingToolbarLayout.this);
        }
        n2 = CollapsingToolbarLayout.this.getHeight();
        n4 = ViewCompat.getMinimumHeight((View)CollapsingToolbarLayout.this);
        CollapsingToolbarLayout.this.mCollapsingTextHelper.setExpansionFraction((float)Math.abs(n) / (float)(n2 - n4 - n3));
    }
}
