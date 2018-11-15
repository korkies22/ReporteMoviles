/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.view.ViewGroup
 */
package de.cisha.android.board.analyze;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.FieldView;

class AbstractAnalyseFragment
implements Runnable {
    AbstractAnalyseFragment() {
    }

    @Override
    public void run() {
        if (!AbstractAnalyseFragment.this.isRemoving() && AbstractAnalyseFragment.this.isAdded()) {
            float f = AbstractAnalyseFragment.this.getResources().getDisplayMetrics().density;
            float f2 = AbstractAnalyseFragment.this._contentView.getHeight();
            if (f2 / f < 60.0f) {
                int n = (int)(f * 60.0f - f2);
                AbstractAnalyseFragment.this.setFieldSize(AbstractAnalyseFragment.this._boardView.getHeight() - n);
                AbstractAnalyseFragment.this._fieldView.requestLayout();
            }
        }
    }
}
