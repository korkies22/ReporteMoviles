// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public class MultiGameKnockOutTournamentRoundInfo extends TournamentRoundInfo
{
    private int _gameNumber;
    private TournamentRoundInfo _mainRound;
    
    public MultiGameKnockOutTournamentRoundInfo(final MainKnockoutRoundInfo mainRound, final int gameNumber) {
        this._mainRound = mainRound;
        this._gameNumber = gameNumber;
    }
    
    @Override
    public int compareTo(final TournamentRoundInfo tournamentRoundInfo) {
        if (!(tournamentRoundInfo instanceof MultiGameKnockOutTournamentRoundInfo)) {
            return tournamentRoundInfo.getClass().getName().compareTo(this.getClass().getName());
        }
        final MultiGameKnockOutTournamentRoundInfo multiGameKnockOutTournamentRoundInfo = (MultiGameKnockOutTournamentRoundInfo)tournamentRoundInfo;
        if (this._mainRound.equals(multiGameKnockOutTournamentRoundInfo.getMainRound())) {
            return this._gameNumber - multiGameKnockOutTournamentRoundInfo._gameNumber;
        }
        return this._mainRound.compareTo(multiGameKnockOutTournamentRoundInfo.getMainRound());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MultiGameKnockOutTournamentRoundInfo)) {
            return false;
        }
        final MultiGameKnockOutTournamentRoundInfo multiGameKnockOutTournamentRoundInfo = (MultiGameKnockOutTournamentRoundInfo)o;
        return multiGameKnockOutTournamentRoundInfo._gameNumber == this._gameNumber && this._mainRound.equals(multiGameKnockOutTournamentRoundInfo.getMainRound());
    }
    
    @Override
    public TournamentRoundInfo getMainRound() {
        return this._mainRound;
    }
    
    @Override
    public String getRoundString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this._mainRound.getRoundString());
        sb.append(".");
        sb.append(this._gameNumber);
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        final int n = 17 + this._gameNumber * 17;
        return n + this._mainRound.hashCode() * n;
    }
}
