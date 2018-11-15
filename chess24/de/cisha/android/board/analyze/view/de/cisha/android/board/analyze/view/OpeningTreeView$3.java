/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.view;

import de.cisha.chess.model.position.Position;

class OpeningTreeView
implements Runnable {
    final /* synthetic */ Position val$position;

    OpeningTreeView(Position position) {
        this.val$position = position;
    }

    @Override
    public void run() {
        OpeningTreeView.this.setOpeningTreeInformation(this.val$position);
    }
}
