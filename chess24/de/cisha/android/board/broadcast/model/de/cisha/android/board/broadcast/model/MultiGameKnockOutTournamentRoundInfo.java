/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.MainKnockoutRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public class MultiGameKnockOutTournamentRoundInfo
extends TournamentRoundInfo {
    private int _gameNumber;
    private TournamentRoundInfo _mainRound;

    public MultiGameKnockOutTournamentRoundInfo(MainKnockoutRoundInfo mainKnockoutRoundInfo, int n) {
        this._mainRound = mainKnockoutRoundInfo;
        this._gameNumber = n;
    }

    @Override
    public int compareTo(TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo instanceof MultiGameKnockOutTournamentRoundInfo) {
            if (this._mainRound.equals(((MultiGameKnockOutTournamentRoundInfo)(tournamentRoundInfo = (MultiGameKnockOutTournamentRoundInfo)tournamentRoundInfo)).getMainRound())) {
                return this._gameNumber - tournamentRoundInfo._gameNumber;
            }
            return this._mainRound.compareTo(((MultiGameKnockOutTournamentRoundInfo)tournamentRoundInfo).getMainRound());
        }
        return tournamentRoundInfo.getClass().getName().compareTo(this.getClass().getName());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof MultiGameKnockOutTournamentRoundInfo)) {
            return false;
        }
        object = (MultiGameKnockOutTournamentRoundInfo)object;
        if (object._gameNumber == this._gameNumber && this._mainRound.equals(object.getMainRound())) {
            return true;
        }
        return false;
    }

    @Override
    public TournamentRoundInfo getMainRound() {
        return this._mainRound;
    }

    @Override
    public String getRoundString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this._mainRound.getRoundString());
        stringBuilder.append(".");
        stringBuilder.append(this._gameNumber);
        return stringBuilder.toString();
    }

    public int hashCode() {
        int n = 17 + this._gameNumber * 17;
        return n + this._mainRound.hashCode() * n;
    }
}
