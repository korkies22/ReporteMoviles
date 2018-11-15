/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 */
package de.cisha.android.board.playzone.engine.view;

import android.widget.SeekBar;

class EngineOpponentChooserTimeSelectionView
implements SeekBar.OnSeekBarChangeListener {
    EngineOpponentChooserTimeSelectionView() {
    }

    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
        EngineOpponentChooserTimeSelectionView.this.setIncrementText(n);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
