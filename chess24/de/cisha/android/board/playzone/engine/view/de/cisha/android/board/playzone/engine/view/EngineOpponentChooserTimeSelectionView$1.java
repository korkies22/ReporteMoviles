/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.widget.SeekBar;
import android.widget.TextView;

class EngineOpponentChooserTimeSelectionView
implements SeekBar.OnSeekBarChangeListener {
    EngineOpponentChooserTimeSelectionView() {
    }

    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
        seekBar = EngineOpponentChooserTimeSelectionView.this._eloView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n + 500);
        seekBar.setText((CharSequence)stringBuilder.toString());
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
