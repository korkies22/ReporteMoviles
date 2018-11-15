/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import java.net.URL;
import java.util.Date;

public class TournamentInfo {
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

    public TournamentInfo(TournamentID tournamentID, String string, int n, TournamentRoundInfo tournamentRoundInfo, int n2, int n3, URL uRL, boolean bl, TournamentState tournamentState, Date date) {
        this._tournamentId = tournamentID;
        this._title = string;
        this._numberOfRounds = n;
        this._currentRound = tournamentRoundInfo;
        this._numberOfOngoingGames = n2;
        this._numberOfFinishedGames = n3;
        this._iconImageURL = uRL;
        this._hasLiveVideo = bl;
        this._state = tournamentState;
        this._startDate = date;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TournamentInfo)) {
            return false;
        }
        object = (TournamentInfo)object;
        if (this._tournamentId != null && this._tournamentId.equals(object.getTournamentId())) {
            return true;
        }
        return false;
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

    public int hashCode() {
        if (this._tournamentId != null) {
            return this._tournamentId.hashCode();
        }
        return 17;
    }
}
