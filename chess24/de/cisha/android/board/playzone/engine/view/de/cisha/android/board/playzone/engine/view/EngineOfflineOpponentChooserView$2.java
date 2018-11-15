/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.engine.view;

import android.content.res.Resources;
import android.view.View;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.engine.view.EngineOpponentChooserTimeSelectionView;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.model.Rating;

class EngineOfflineOpponentChooserView
implements View.OnClickListener {
    final /* synthetic */ EngineOpponentChooserTimeSelectionView val$customOpponentSelectionView;

    EngineOfflineOpponentChooserView(EngineOpponentChooserTimeSelectionView engineOpponentChooserTimeSelectionView) {
        this.val$customOpponentSelectionView = engineOpponentChooserTimeSelectionView;
    }

    public void onClick(View view) {
        if (EngineOfflineOpponentChooserView.this._listener != null) {
            EngineOfflineOpponentChooserView.this._listener.onChoosen(this.val$customOpponentSelectionView.getTimeControlChosen(), this.val$customOpponentSelectionView.getSelectedElo().getRating(), EngineOfflineOpponentChooserView.this.getResources().getString(2131689963), EngineOfflineOpponentChooserView.this.getPieceColor());
        }
    }
}
