/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.standings.model.MultiGameKnockoutPairing;
import de.cisha.chess.model.GameResult;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class KnockoutMatch {
    private int _desiredNumberOfGames;
    private List<TournamentGameInfo> _games = new LinkedList<TournamentGameInfo>();
    private boolean _isPlayerLeftWinner = false;
    private boolean _isPlayerRightWinner = false;
    private MultiGameKnockoutPairing _knockoutPairing;
    private int _matchNumber;
    private List<Integer> _potetiallyNumbersOfMaximumPlayedGames;

    public KnockoutMatch(TournamentPlayer tournamentPlayer, TournamentPlayer tournamentPlayer2, List<Integer> list) {
        this(new MultiGameKnockoutPairing(tournamentPlayer, tournamentPlayer2), list);
    }

    public KnockoutMatch(MultiGameKnockoutPairing multiGameKnockoutPairing, List<Integer> list) {
        this._knockoutPairing = multiGameKnockoutPairing;
        this._potetiallyNumbersOfMaximumPlayedGames = list;
        if (this._potetiallyNumbersOfMaximumPlayedGames == null || this._potetiallyNumbersOfMaximumPlayedGames.size() == 0) {
            this._potetiallyNumbersOfMaximumPlayedGames = new LinkedList<Integer>();
            this._potetiallyNumbersOfMaximumPlayedGames.add(1);
        }
        this._desiredNumberOfGames = this.getNextDesiredNumberOfGames(0);
    }

    private int getNextDesiredNumberOfGames(int n) {
        for (Integer n2 : this._potetiallyNumbersOfMaximumPlayedGames) {
            if (n2 <= n) continue;
            return n2;
        }
        return -1;
    }

    private float getPoints(TournamentPlayer tournamentPlayer) {
        Iterator<TournamentGameInfo> iterator = this._games.iterator();
        float f = 0.0f;
        block6 : while (iterator.hasNext()) {
            TournamentGameInfo tournamentGameInfo = iterator.next();
            boolean bl = tournamentPlayer.equals(tournamentGameInfo.getPlayerWhite());
            boolean bl2 = tournamentPlayer.equals(tournamentGameInfo.getPlayerBlack());
            switch (.$SwitchMap$de$cisha$chess$model$GameResult[tournamentGameInfo.getStatus().getResult().ordinal()]) {
                case 4: {
                    continue block6;
                }
                default: {
                    continue block6;
                }
                case 3: {
                    if (!bl && !bl2) continue block6;
                    f = (float)((double)f + 0.5);
                    continue block6;
                }
                case 2: {
                    if (!bl2) continue block6;
                    f += 1.0f;
                    continue block6;
                }
                case 1: 
            }
            if (!bl) continue;
            f += 1.0f;
        }
        return f;
    }

    public boolean addGame(TournamentGameInfo tournamentGameInfo) {
        MultiGameKnockoutPairing multiGameKnockoutPairing = new MultiGameKnockoutPairing(tournamentGameInfo.getPlayerLeft(), tournamentGameInfo.getPlayerRight());
        this._matchNumber = tournamentGameInfo.getMatchNumber();
        boolean bl = multiGameKnockoutPairing.equals(this._knockoutPairing);
        boolean bl2 = false;
        if (bl) {
            int n;
            this._games.add(tournamentGameInfo);
            int n2 = this._games.size();
            if (this.getPlayerLeftPoints() == this.getPlayerRightPoints() && n2 == this._desiredNumberOfGames && (n = this.getNextDesiredNumberOfGames(n2)) > this._desiredNumberOfGames) {
                this._desiredNumberOfGames = n;
            }
            if (this._desiredNumberOfGames == n2) {
                bl = this.getPlayerLeftPoints() > this.getPlayerRightPoints();
                this._isPlayerLeftWinner = bl;
                bl = bl2;
                if (this.getPlayerRightPoints() > this.getPlayerLeftPoints()) {
                    bl = true;
                }
                this._isPlayerRightWinner = bl;
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
        Collections.sort(this._games, new Comparator<TournamentGameInfo>(){

            @Override
            public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
                return tournamentGameInfo.getGameNumber() - tournamentGameInfo2.getGameNumber();
            }
        });
        return this._games;
    }

    public int getMatchNumber() {
        return this._matchNumber;
    }

    public TournamentPlayer getPlayerLeft() {
        return (TournamentPlayer)this._knockoutPairing.getOpponentLeft();
    }

    public float getPlayerLeftPoints() {
        return this.getPoints(this.getPlayerLeft());
    }

    public TournamentPlayer getPlayerRight() {
        return (TournamentPlayer)this._knockoutPairing.getOpponentRight();
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
