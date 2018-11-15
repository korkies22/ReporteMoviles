/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.widget.TextView;
import de.cisha.android.board.playzone.model.ChessClock;

class EngineOnlineOpponentChooserView
implements Runnable {
    final /* synthetic */ boolean val$colorWhite;
    final /* synthetic */ long val$timeMillis;

    EngineOnlineOpponentChooserView(long l, boolean bl) {
        this.val$timeMillis = l;
        this.val$colorWhite = bl;
    }

    @Override
    public void run() {
        String string = ChessClock.formatTime(this.val$timeMillis, false);
        if (this.val$colorWhite) {
            EngineOnlineOpponentChooserView.this._textClockWhite = string;
        } else {
            EngineOnlineOpponentChooserView.this._textClockBlack = string;
        }
        if (EngineOnlineOpponentChooserView.this._updateClockTextView != null) {
            string = EngineOnlineOpponentChooserView.this._updateClockTextView;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(EngineOnlineOpponentChooserView.this._textClockWhite);
            stringBuilder.append(" ");
            stringBuilder.append(EngineOnlineOpponentChooserView.this._textClockBlack);
            string.setText((CharSequence)stringBuilder.toString());
        }
    }
}
