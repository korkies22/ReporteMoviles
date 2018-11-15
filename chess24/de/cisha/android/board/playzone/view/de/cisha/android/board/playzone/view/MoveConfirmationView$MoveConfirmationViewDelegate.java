/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.view;

import de.cisha.android.board.playzone.view.MoveConfirmationView;
import de.cisha.chess.model.Move;

public static interface MoveConfirmationView.MoveConfirmationViewDelegate {
    public void cancelMove(Move var1);

    public void confirmMove(Move var1);
}
