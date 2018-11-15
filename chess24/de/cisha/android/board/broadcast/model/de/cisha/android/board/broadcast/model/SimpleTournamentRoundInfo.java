/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public class SimpleTournamentRoundInfo
extends TournamentRoundInfo {
    private int _number;

    public SimpleTournamentRoundInfo(int n) {
        this._number = n;
    }

    @Override
    public int compareTo(TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo instanceof SimpleTournamentRoundInfo) {
            tournamentRoundInfo = (SimpleTournamentRoundInfo)tournamentRoundInfo;
            return this._number - tournamentRoundInfo._number;
        }
        return tournamentRoundInfo.getClass().getName().compareTo(this.getClass().getName());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SimpleTournamentRoundInfo)) {
            return false;
        }
        if (this._number == ((SimpleTournamentRoundInfo)object)._number) {
            return true;
        }
        return false;
    }

    @Override
    public String getRoundString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this._number);
        return stringBuilder.toString();
    }

    public int hashCode() {
        return this._number;
    }
}
