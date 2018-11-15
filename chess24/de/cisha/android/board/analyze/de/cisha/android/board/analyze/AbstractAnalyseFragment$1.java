/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package de.cisha.android.board.analyze;

import android.view.MotionEvent;
import android.view.View;
import de.cisha.android.board.view.FieldView;

class AbstractAnalyseFragment
implements View.OnTouchListener {
    private float _startDownY;

    AbstractAnalyseFragment() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            AbstractAnalyseFragment.this._startBoardScaleWidth = AbstractAnalyseFragment.this._fieldView.getWidth();
            this._startDownY = motionEvent.getRawY();
        } else if (motionEvent.getAction() == 2) {
            AbstractAnalyseFragment.this.setFieldSize((int)((float)AbstractAnalyseFragment.this._startBoardScaleWidth + motionEvent.getRawY() - this._startDownY));
            AbstractAnalyseFragment.this._fieldView.requestLayout();
        }
        return true;
    }
}
