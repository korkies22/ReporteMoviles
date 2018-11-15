/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.pgn;

import de.cisha.android.board.util.FileHelper;

class PGNListFragment
implements FileHelper.FileReaderDelegate {
    PGNListFragment() {
    }

    @Override
    public void fileRead(String string) {
        PGNListFragment.this.readPGNString(string);
    }
}
