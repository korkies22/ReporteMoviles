/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

class AbstractPlayzoneFragment
implements Runnable {
    AbstractPlayzoneFragment() {
    }

    @Override
    public void run() {
        AbstractPlayzoneFragment.this.dismissResignDialog();
        AbstractPlayzoneFragment.this.updateUI();
    }
}
