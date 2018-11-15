/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.CompoundButton
 */
package de.cisha.android.board.playzone.engine.view;

import android.view.View;
import android.widget.CompoundButton;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.playzone.model.TimeControl;

class EngineOnlineOpponentChooserView
implements View.OnClickListener {
    final /* synthetic */ OnlineEngineOpponent val$opponent;
    final /* synthetic */ TimeControl val$timeControl;

    EngineOnlineOpponentChooserView(OnlineEngineOpponent onlineEngineOpponent, TimeControl timeControl) {
        this.val$opponent = onlineEngineOpponent;
        this.val$timeControl = timeControl;
    }

    public void onClick(View view) {
        if (EngineOnlineOpponentChooserView.this._listener != null) {
            EngineOnlineOpponentChooserView.this._listener.onOnlineEngineChosen(this.val$opponent.getUuidString(), this.val$timeControl, EngineOnlineOpponentChooserView.this._rateGameSwitch.isChecked());
        }
    }
}
