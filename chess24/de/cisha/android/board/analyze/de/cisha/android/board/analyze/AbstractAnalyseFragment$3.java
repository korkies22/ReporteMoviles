/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze;

import de.cisha.android.board.view.FieldView;

class AbstractAnalyseFragment
implements Runnable {
    AbstractAnalyseFragment() {
    }

    @Override
    public void run() {
        if (AbstractAnalyseFragment.this._fieldView != null) {
            AbstractAnalyseFragment.this._maxSizeBoard = AbstractAnalyseFragment.this._fieldView.getMeasuredHeight();
            AbstractAnalyseFragment.this.setFieldSize(AbstractAnalyseFragment.this._maxSizeBoard);
        }
    }
}
