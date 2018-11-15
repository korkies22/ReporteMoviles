/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.DummyTournamentService;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import java.util.Random;
import java.util.TimerTask;

class DummyTournamentService
extends TimerTask {
    DummyTournamentService() {
    }

    @Override
    public void run() {
        TournamentRoundInfo tournamentRoundInfo = new TournamentRoundInfo(){

            @Override
            public int compareTo(TournamentRoundInfo tournamentRoundInfo) {
                return 0;
            }

            @Override
            public TournamentRoundInfo getMainRound() {
                return this;
            }

            @Override
            public String getRoundString() {
                return "R-1";
            }
        };
        1.this._tournament.setCurrentRound(tournamentRoundInfo);
        for (int i = 0; i < 20; ++i) {
            String[] arrstring = new String[]();
            arrstring.append("");
            arrstring.append(i);
            TournamentGameInfo tournamentGameInfo = new TournamentGameInfo(arrstring.toString(), _dummyNames[(int)((float)_dummyNames.length * 1.this._random.nextFloat())], _dummyNames[(int)((float)_dummyNames.length * 1.this._random.nextFloat())]);
            arrstring = new Random().nextBoolean() ? _dummyMoves : _dummyMoves2;
            tournamentGameInfo.setLastMovesEANsAndEarlierFEN(arrstring, FEN.STARTING_POSITION);
            tournamentGameInfo.setRemainingTimeBlack(120000L);
            tournamentGameInfo.setWhitePlayerLeftSide(1.this._random.nextBoolean());
            tournamentGameInfo.setEngineEvaluation(new EngineEvaluation(14.0f * 1.this._random.nextFloat() - 7.0f));
            tournamentGameInfo.setRemainingTimeWhite(120000L);
            float f = 1.this._random.nextFloat();
            double d = f;
            if (d < 0.25) {
                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON, TournamentState.ONGOING));
            } else if (f < 0.5f) {
                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.BLACK_WINS, GameEndReason.MATE, TournamentState.FINISHED));
            } else if (d < 0.75) {
                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_MUTUAL_AGREEMENT, TournamentState.FINISHED));
            } else {
                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.WHITE_WINS, GameEndReason.MATE, TournamentState.FINISHED));
            }
            1.this._tournament.addGame(new Tournament.TournamentStructureKey("0", "0", "0"), tournamentRoundInfo, tournamentGameInfo);
        }
        1.this._tournament.setStandingsEnabled(true);
        if (1.this.val$listener != null) {
            1.this.val$listener.tournamentLoaded(1.this._tournament);
        }
    }

}
