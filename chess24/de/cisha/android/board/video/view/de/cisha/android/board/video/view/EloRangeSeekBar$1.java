/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.view;

import de.cisha.android.ui.view.RangeSeekBar;

class EloRangeSeekBar
implements RangeSeekBar.OnRangeSeekBarChangeListener<Integer> {
    EloRangeSeekBar() {
    }

    @Override
    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer n, Integer n2) {
        EloRangeSeekBar.this.setMinMaxEloNumber(n, n2);
    }
}
