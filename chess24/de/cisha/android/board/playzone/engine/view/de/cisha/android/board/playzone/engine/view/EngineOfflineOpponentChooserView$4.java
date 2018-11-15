/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.engine.view;

import android.view.View;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.model.TimeControl;

class EngineOfflineOpponentChooserView
implements View.OnClickListener {
    final /* synthetic */ int val$elo;
    final /* synthetic */ String val$engineName;
    final /* synthetic */ TimeControl val$timeControl;

    EngineOfflineOpponentChooserView(TimeControl timeControl, int n, String string) {
        this.val$timeControl = timeControl;
        this.val$elo = n;
        this.val$engineName = string;
    }

    public void onClick(View view) {
        if (EngineOfflineOpponentChooserView.this._listener != null) {
            EngineOfflineOpponentChooserView.this._listener.onChoosen(this.val$timeControl, this.val$elo, this.val$engineName, EngineOfflineOpponentChooserView.this.getPieceColor());
        }
    }
}
