/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze.view;

import android.view.View;

class EngineVariantView
implements View.OnClickListener {
    EngineVariantView() {
    }

    public void onClick(View view) {
        EngineVariantView.this.setToMinimized(EngineVariantView.this._minimized ^ true);
    }
}
