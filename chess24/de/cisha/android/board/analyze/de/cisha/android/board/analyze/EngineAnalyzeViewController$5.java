/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.widget.TextView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

class EngineAnalyzeViewController
implements Runnable {
    EngineAnalyzeViewController() {
    }

    @Override
    public void run() {
        EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689547));
        if (EngineAnalyzeViewController.this._menuItem != null) {
            EngineAnalyzeViewController.this._menuItem.setGlowing(true);
        }
    }
}
