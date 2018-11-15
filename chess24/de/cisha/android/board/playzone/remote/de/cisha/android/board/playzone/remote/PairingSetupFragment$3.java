/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.view.PremiumButtonOverlayLayout;

class PairingSetupFragment
implements Runnable {
    final /* synthetic */ boolean val$enableRegisterEntryPoint;

    PairingSetupFragment(boolean bl) {
        this.val$enableRegisterEntryPoint = bl;
    }

    @Override
    public void run() {
        if (PairingSetupFragment.this._eloRangeOverlay != null) {
            PairingSetupFragment.this._eloRangeOverlay.setPremiumOverlayEnabled(this.val$enableRegisterEntryPoint);
        }
    }
}
