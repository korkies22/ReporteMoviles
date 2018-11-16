// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.List;

public class KnockoutMatch
{
    private int _desiredNumberOfGames;
    private List<TournamentGameInfo> _games;
    private boolean _isPlayerLeftWinner;
    private boolean _isPlayerRightWinner;
    private MultiGameKnockoutPairing _knockoutPairing;
    private int _matchNumber;
    private List<Integer> _potetiallyNumbersOfMaximumPlayedGames;
    
    public KnockoutMatch(final TournamentPlayer tournamentPlayer, final TournamentPlayer tournamentPlayer2, final List<Integer> list) {
        this(new MultiGameKnockoutPairing(tournamentPlayer, tournamentPlayer2), list);
    }
    
    public KnockoutMatch(final MultiGameKnockoutPairing knockoutPairing, final List<Integer> potetiallyNumbersOfMaximumPlayedGames) {
        this._isPlayerLeftWinner = false;
        this._isPlayerRightWinner = false;
        this._games = new LinkedList<TournamentGameInfo>();
        this._knockoutPairing = knockoutPairing;
        this._potetiallyNumbersOfMaximumPlayedGames = potetiallyNumbersOfMaximumPlayedGames;
        if (this._potetiallyNumbersOfMaximumPlayedGames == null || this._potetiallyNumbersOfMaximumPlayedGames.size() == 0) {
            (this._potetiallyNumbersOfMaximumPlayedGames = new LinkedList<Integer>()).add(1);
        }
        this._desiredNumberOfGames = this.getNextDesiredNumberOfGames(0);
    }
    
    private int getNextDesiredNumberOfGames(final int n) {
        for (final Integer n2 : this._potetiallyNumbersOfMaximumPlayedGames) {
            if (n2 > n) {
                return n2;
            }
        }
        return -1;
    }
    
    private float getPoints(final TournamentPlayer tournamentPlayer) {
        final Iterator<TournamentGameInfo> iterator = this._games.iterator();
        float n = 0.0f;
        while (iterator.hasNext()) {
            final TournamentGameInfo tournamentGameInfo = iterator.next();
            final boolean equals = tournamentPlayer.equals(tournamentGameInfo.getPlayerWhite());
            final boolean equals2 = tournamentPlayer.equals(tournamentGameInfo.getPlayerBlack());
            switch (KnockoutMatch.2..SwitchMap.de.cisha.chess.model.GameResult[tournamentGameInfo.getStatus().getResult().ordinal()]) {
                case 4: {}
                default: {
                    continue;
                }
                case 3: {
                    if (equals || equals2) {
                        n += 0.5;
                        continue;
                    }
                    continue;
                }
                case 2: {
                    if (equals2) {
                        ++n;
                        continue;
                    }
                    continue;
                }
                case 1: {
                    if (equals) {
                        ++n;
                        continue;
                    }
                    continue;
                }
            }
        }
        return n;
    }
    
    public boolean addGame(final TournamentGameInfo tournamentGameInfo) {
        final MultiGameKnockoutPairing multiGameKnockoutPairing = new MultiGameKnockoutPairing(tournamentGameInfo.getPlayerLeft(), tournamentGameInfo.getPlayerRight());
        this._matchNumber = tournamentGameInfo.getMatchNumber();
        final boolean equals = multiGameKnockoutPairing.equals(this._knockoutPairing);
        final boolean b = false;
        if (equals) {
            this._games.add(tournamentGameInfo);
            final int size = this._games.size();
            if (this.getPlayerLeftPoints() == this.getPlayerRightPoints() && size == this._desiredNumberOfGames) {
                final int nextDesiredNumberOfGames = this.getNextDesiredNumberOfGames(size);
                if (nextDesiredNumberOfGames > this._desiredNumberOfGames) {
                    this._desiredNumberOfGames = nextDesiredNumberOfGames;
                }
            }
            if (this._desiredNumberOfGames == size) {
                this._isPlayerLeftWinner = (this.getPlayerLeftPoints() > this.getPlayerRightPoints());
                boolean isPlayerRightWinner = b;
                if (this.getPlayerRightPoints() > this.getPlayerLeftPoints()) {
                    isPlayerRightWinner = true;
                }
                this._isPlayerRightWinner = isPlayerRightWinner;
                if (!this._isPlayerLeftWinner && !this._isPlayerRightWinner) {
                    this._isPlayerLeftWinner = tournamentGameInfo.getPlayerBlack().equals(this.getPlayerLeft());
                    this._isPlayerRightWinner = tournamentGameInfo.getPlayerBlack().equals(this.getPlayerRight());
                }
            }
            return true;
        }
        return false;
    }
    
    public int getActualDesiredNumberOfGamesInMatch() {
        return Math.max(this._desiredNumberOfGames, this._games.size());
    }
    
    public List<TournamentGameInfo> getGames() {
        Collections.sort(this._games, new Comparator<TournamentGameInfo>() {
            @Override
            public int compare(final TournamentGameInfo tournamentGameInfo, final TournamentGameInfo tournamentGameInfo2) {
                return tournamentGameInfo.getGameNumber() - tournamentGameInfo2.getGameNumber();
            }
        });
        return this._games;
    }
    
    public int getMatchNumber() {
        return this._matchNumber;
    }
    
    public TournamentPlayer getPlayerLeft() {
        return this._knockoutPairing.getOpponentLeft();
    }
    
    public float getPlayerLeftPoints() {
        return this.getPoints(this.getPlayerLeft());
    }
    
    public TournamentPlayer getPlayerRight() {
        return this._knockoutPairing.getOpponentRight();
    }
    
    public float getPlayerRightPoints() {
        return this.getPoints(this.getPlayerRight());
    }
    
    public boolean isPlayerLeftTheWinner() {
        return this._isPlayerLeftWinner;
    }
    
    public boolean isPlayerRightTheWinner() {
        return this._isPlayerRightWinner;
    }
}
