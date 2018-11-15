/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesObserver;

public interface MoveListView
extends MovesObserver {
    public void setMoveSelector(MoveSelector var1);

    public void setNavigationSelectionEnabled(boolean var1);
}
