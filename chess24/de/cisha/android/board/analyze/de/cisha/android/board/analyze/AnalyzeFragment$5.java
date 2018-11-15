/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.ImageView
 */
package de.cisha.android.board.analyze;

import android.widget.ImageView;

class AnalyzeFragment
implements Runnable {
    AnalyzeFragment() {
    }

    @Override
    public void run() {
        AnalyzeFragment.this._saveButton.setSelected(true);
    }
}
