/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze;

import android.view.View;

class AnalyzeFragment
implements View.OnClickListener {
    AnalyzeFragment() {
    }

    public void onClick(View view) {
        AnalyzeFragment.this.saveGame();
    }
}
