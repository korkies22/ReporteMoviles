// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.Date;
import java.net.URL;

public class TournamentInfo
{
    private final TournamentRoundInfo _currentRound;
    private final boolean _hasLiveVideo;
    private final URL _iconImageURL;
    private final int _numberOfFinishedGames;
    private final int _numberOfOngoingGames;
    private final int _numberOfRounds;
    private final Date _startDate;
    private final TournamentState _state;
    private final String _title;
    private final TournamentID _tournamentId;
    
    public TournamentInfo(final TournamentID tournamentId, final String title, final int numberOfRounds, final TournamentRoundInfo currentRound, final int numberOfOngoingGames, final int numberOfFinishedGames, final URL iconImageURL, final boolean hasLiveVideo, final TournamentState state, final Date startDate) {
        this._tournamentId = tournamentId;
        this._title = title;
        this._numberOfRounds = numberOfRounds;
        this._currentRound = currentRound;
        this._numberOfOngoingGames = numberOfOngoingGames;
        this._numberOfFinishedGames = numberOfFinishedGames;
        this._iconImageURL = iconImageURL;
        this._hasLiveVideo = hasLiveVideo;
        this._state = state;
        this._startDate = startDate;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TournamentInfo)) {
            return false;
        }
        final TournamentInfo tournamentInfo = (TournamentInfo)o;
        return this._tournamentId != null && this._tournamentId.equals(tournamentInfo.getTournamentId());
    }
    
    public TournamentRoundInfo getCurrentRound() {
        return this._currentRound;
    }
    
    public URL getIconImageURL() {
        return this._iconImageURL;
    }
    
    public int getNumberOfFinishedGames() {
        return this._numberOfFinishedGames;
    }
    
    public int getNumberOfOngoingGames() {
        return this._numberOfOngoingGames;
    }
    
    public int getNumberOfRounds() {
        return this._numberOfRounds;
    }
    
    public Date getStartDate() {
        return this._startDate;
    }
    
    public TournamentState getState() {
        return this._state;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public TournamentID getTournamentId() {
        return this._tournamentId;
    }
    
    public boolean hasLiveVideo() {
        return this._hasLiveVideo;
    }
    
    @Override
    public int hashCode() {
        if (this._tournamentId != null) {
            return this._tournamentId.hashCode();
        }
        return 17;
    }
}
