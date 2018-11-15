/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

public abstract class TournamentRoundInfo
implements Comparable<TournamentRoundInfo> {
    public TournamentRoundInfo getMainRound() {
        return this;
    }

    public abstract String getRoundString();
}
