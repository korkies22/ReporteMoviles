/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.setup.view;

import android.widget.CompoundButton;
import de.cisha.android.board.setup.view.EnpassantView;
import de.cisha.chess.model.Square;

class EnpassantView
implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ Square val$square;

    EnpassantView(Square square) {
        this.val$square = square;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        if (EnpassantView.this._listener != null && bl) {
            EnpassantView.this._listener.enpassantChanged(this.val$square);
        }
    }
}
