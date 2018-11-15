/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.pgn;

import de.cisha.android.board.util.HTTPHelperAsyn;

class PGNListFragment
implements HTTPHelperAsyn.HTTPHelperDelegate {
    PGNListFragment() {
    }

    @Override
    public void loadingFailed(int n, String string) {
    }

    @Override
    public void urlLoaded(String string) {
        PGNListFragment.this.readPGNString(string);
    }
}
