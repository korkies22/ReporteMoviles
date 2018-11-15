/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package uk.co.jasonfry.android.tools.ui;

import android.view.MotionEvent;
import android.view.View;
import uk.co.jasonfry.android.tools.ui.PageControl;

class PageControl
implements View.OnTouchListener {
    PageControl() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (PageControl.this.mOnPageControlClickListener != null) {
            if (motionEvent.getAction() != 1) {
                return true;
            }
            if (PageControl.this.getOrientation() == 0) {
                float f = (float)PageControl.this.getWidth() / (float)PageControl.this.getPageCount();
                int n = (int)(motionEvent.getX() / f);
                PageControl.this.mOnPageControlClickListener.goToPage(n);
            } else {
                float f = (float)PageControl.this.getHeight() / (float)PageControl.this.getPageCount();
                int n = (int)(motionEvent.getY() / f);
                PageControl.this.mOnPageControlClickListener.goToPage(n);
            }
            return false;
        }
        return true;
    }
}
