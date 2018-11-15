/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.modalfragments;

import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;

class RematchDialogFragment
implements Runnable {
    RematchDialogFragment() {
    }

    @Override
    public void run() {
        if (RematchDialogFragment.this._rematchButton != null) {
            RematchDialogFragment.this._rematchButton.setEnabled(false);
        }
    }
}
