/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public class MainKnockoutRoundInfo
extends TournamentRoundInfo {
    private int _roundNumber;

    public MainKnockoutRoundInfo(int n) {
        this._roundNumber = n;
    }

    @Override
    public int compareTo(TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo instanceof MainKnockoutRoundInfo) {
            tournamentRoundInfo = (MainKnockoutRoundInfo)tournamentRoundInfo;
            return this._roundNumber - tournamentRoundInfo._roundNumber;
        }
        return tournamentRoundInfo.getClass().getName().compareTo(this.getClass().getName());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof MainKnockoutRoundInfo)) {
            return false;
        }
        if (this._roundNumber == ((MainKnockoutRoundInfo)object)._roundNumber) {
            return true;
        }
        return false;
    }

    @Override
    public String getRoundString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this._roundNumber);
        return stringBuilder.toString();
    }

    public int hashCode() {
        return this._roundNumber;
    }
}
