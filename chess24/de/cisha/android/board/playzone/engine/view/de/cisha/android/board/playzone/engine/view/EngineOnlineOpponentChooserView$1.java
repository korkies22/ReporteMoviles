/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.playzone.engine.view;

import android.widget.CompoundButton;
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;
import java.util.Iterator;
import java.util.List;

class EngineOnlineOpponentChooserView
implements CompoundButton.OnCheckedChangeListener {
    EngineOnlineOpponentChooserView() {
    }

    public void onCheckedChanged(CompoundButton object, boolean bl) {
        if (EngineOnlineOpponentChooserView.this._opponentViewsNotRated != null) {
            object = EngineOnlineOpponentChooserView.this._opponentViewsNotRated.iterator();
            while (object.hasNext()) {
                ((EngineOpponentView)((Object)object.next())).setOpponentEnabled(bl ^ true);
            }
        }
    }
}
