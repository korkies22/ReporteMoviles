/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package de.cisha.android.board.analyze;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

class AbstractAnalyseFragment
implements View.OnTouchListener {
    AbstractAnalyseFragment() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() == 2) {
            AbstractAnalyseFragment.this._scaleGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
        return false;
    }
}
