/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.engine.view;

import android.view.View;
import de.cisha.android.board.action.BoardAction;

class EngineOnlineOpponentChooserView
implements View.OnClickListener {
    final /* synthetic */ BoardAction val$resumeAction;

    EngineOnlineOpponentChooserView(BoardAction boardAction) {
        this.val$resumeAction = boardAction;
    }

    public void onClick(View view) {
        if (this.val$resumeAction != null) {
            this.val$resumeAction.perform();
        }
    }
}
