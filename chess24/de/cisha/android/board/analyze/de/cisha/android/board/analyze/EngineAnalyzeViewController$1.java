/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze;

import android.view.View;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.engine.EngineController;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;

class EngineAnalyzeViewController
implements View.OnClickListener {
    EngineAnalyzeViewController() {
    }

    public void onClick(View view) {
        int n = EngineAnalyzeViewController.this._engineController.getVariationCount();
        if (n < 4) {
            if (n < EngineAnalyzeViewController.this._maxVariationsAllowedForThisUser) {
                EngineAnalyzeViewController.this._engineController.addEngineVariation();
                if (EngineAnalyzeViewController.this._engineRunning) {
                    EngineAnalyzeViewController.this._engineController.startCalculation();
                }
                EngineAnalyzeViewController.this.updateMoreAndLessVariationsButtons();
                return;
            }
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.ANALYSIS, "premium screen shown", "engine");
            EngineAnalyzeViewController.this._contentPresenter.showConversionDialog(null, ConversionContext.ANALYZE_ENGINE_VARIATION);
        }
    }
}
