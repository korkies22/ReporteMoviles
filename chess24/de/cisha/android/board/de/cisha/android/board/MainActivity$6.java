/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.IErrorPresenter;

class MainActivity
implements IErrorPresenter.ICancelAction {
    MainActivity() {
    }

    @Override
    public void cancelPressed() {
        MainActivity.this.popCurrentFragment();
    }
}
