/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.view;

import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.android.board.analyze.view.OpeningTreeView;
import de.cisha.chess.model.position.Position;

public static interface OpeningTreeView.OpeningTreeViewSourceAdapter {
    public OpeningPositionInformation getInformation(Position var1);
}
