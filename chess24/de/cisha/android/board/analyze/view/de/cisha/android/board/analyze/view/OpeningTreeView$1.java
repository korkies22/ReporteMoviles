/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze.view;

import android.view.View;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;

class OpeningTreeView
implements View.OnClickListener {
    OpeningTreeView() {
    }

    public void onClick(View view) {
        if (OpeningTreeView.this._contentPresenter != null) {
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "premium screen shown", "opening tree");
            OpeningTreeView.this._contentPresenter.showConversionDialog(null, ConversionContext.ANALYZE_TREE);
        }
    }
}
