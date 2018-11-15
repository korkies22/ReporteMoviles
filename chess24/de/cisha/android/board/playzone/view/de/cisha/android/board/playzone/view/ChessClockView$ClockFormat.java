/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.view;

import de.cisha.android.board.playzone.view.ChessClockView;

private static enum ChessClockView.ClockFormat {
    HH_MM(2131689610, 2131689611),
    MM_SS(2131689611, 2131689612),
    SS_MS(2131689612, 2131689609);
    
    private int _text1;
    private int _text2;

    private ChessClockView.ClockFormat(int n2, int n3) {
        this._text1 = n2;
        this._text2 = n3;
    }

    static /* synthetic */ int access$000(ChessClockView.ClockFormat clockFormat) {
        return clockFormat._text1;
    }

    static /* synthetic */ int access$100(ChessClockView.ClockFormat clockFormat) {
        return clockFormat._text2;
    }
}
