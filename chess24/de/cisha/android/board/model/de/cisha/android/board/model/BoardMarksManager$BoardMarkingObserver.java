/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.model;

import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.model.BoardMarksManager;

public static interface BoardMarksManager.BoardMarkingObserver {
    public void boardMarkingChanged(BoardMarks var1);
}
