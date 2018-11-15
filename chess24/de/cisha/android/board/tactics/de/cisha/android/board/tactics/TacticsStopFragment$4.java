/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package de.cisha.android.board.tactics;

import android.view.ViewGroup;
import de.cisha.android.board.view.FieldView;

class TacticsStopFragment
implements Runnable {
    TacticsStopFragment() {
    }

    @Override
    public void run() {
        int n;
        de.cisha.android.board.tactics.TacticsStopFragment.access$600((de.cisha.android.board.tactics.TacticsStopFragment)TacticsStopFragment.this).getLayoutParams().width = n = TacticsStopFragment.this._fieldView.getMeasuredHeight();
        de.cisha.android.board.tactics.TacticsStopFragment.access$600((de.cisha.android.board.tactics.TacticsStopFragment)TacticsStopFragment.this).getLayoutParams().height = n;
    }
}
