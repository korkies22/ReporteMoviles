/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.model.AbstractGameModel;

class AbstractPlayzoneFragment
implements Runnable {
    AbstractPlayzoneFragment() {
    }

    @Override
    public void run() {
        AbstractPlayzoneFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._gameAdapter != null) {
                    AbstractPlayzoneFragment.this.initBoard();
                    AbstractPlayzoneFragment.this.initListener();
                    AbstractPlayzoneFragment.this.updateUI();
                }
                AbstractPlayzoneFragment.this.updateUIOnce();
            }
        });
    }

}
