/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 */
package de.cisha.android.board.tactics;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import de.cisha.android.board.tactics.view.ScrollViewWithSizeListener;
import de.cisha.android.board.view.FieldView;

class TacticsStopFragment
implements ScrollViewWithSizeListener.SizeListener {
    TacticsStopFragment() {
    }

    @Override
    public void onSizeInit(int n) {
        int n2 = (int)(TacticsStopFragment.this.getResources().getDisplayMetrics().density * 100.0f);
        TacticsStopFragment.this.animateFieldViewHeight(Math.min(TacticsStopFragment.this._fieldView.getMeasuredHeight() - (n2 - n), (int)(0.75 * (double)TacticsStopFragment.this._fieldView.getMeasuredHeight())));
    }
}
