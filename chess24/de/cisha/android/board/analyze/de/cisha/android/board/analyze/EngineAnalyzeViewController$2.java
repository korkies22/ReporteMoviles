/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze;

import android.view.View;
import de.cisha.android.board.engine.EngineController;

class EngineAnalyzeViewController
implements View.OnClickListener {
    EngineAnalyzeViewController() {
    }

    public void onClick(View view) {
        if (EngineAnalyzeViewController.this._engineController.getVariationCount() > 1) {
            EngineAnalyzeViewController.this._engineController.removeEngineVariation();
            if (EngineAnalyzeViewController.this._engineRunning) {
                EngineAnalyzeViewController.this._engineController.startCalculation();
            }
            EngineAnalyzeViewController.this.updateMoreAndLessVariationsButtons();
        }
    }
}
