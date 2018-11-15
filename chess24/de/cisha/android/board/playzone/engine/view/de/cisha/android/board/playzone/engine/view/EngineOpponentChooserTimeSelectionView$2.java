/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.playzone.engine.view;

import android.widget.CompoundButton;
import de.cisha.android.ui.patterns.input.CustomSeekBar;

class EngineOpponentChooserTimeSelectionView
implements CompoundButton.OnCheckedChangeListener {
    EngineOpponentChooserTimeSelectionView() {
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        EngineOpponentChooserTimeSelectionView.this._increment.setEnabled(bl);
        EngineOpponentChooserTimeSelectionView.this._minutes.setEnabled(bl);
    }
}
