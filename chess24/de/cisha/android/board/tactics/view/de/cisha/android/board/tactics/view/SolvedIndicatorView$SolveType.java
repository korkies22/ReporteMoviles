/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics.view;

import de.cisha.android.board.tactics.view.SolvedIndicatorView;

public static enum SolvedIndicatorView.SolveType {
    CORRECT(1),
    INCORRECT(2),
    RUNNING(0),
    INVISIBLE(3);
    
    private int _level;

    private SolvedIndicatorView.SolveType(int n2) {
        this._level = n2;
    }

    static /* synthetic */ int access$000(SolvedIndicatorView.SolveType solveType) {
        return solveType._level;
    }
}
