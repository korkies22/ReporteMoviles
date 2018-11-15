/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.database;

import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;

class AbstractDatabaseListFragment
implements AbstractDialogFragment.OnDialogCloseListener {
    AbstractDatabaseListFragment() {
    }

    @Override
    public void onDialogClosed() {
        2.this.this$0.updateList();
    }
}
