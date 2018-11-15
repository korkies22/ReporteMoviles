/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.DummyTournamentService;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import java.util.Random;
import java.util.TimerTask;

class DummyTournamentService
extends TimerTask {
    final /* synthetic */ long val$startTime;

    DummyTournamentService(long l) {
        this.val$startTime = l;
    }

    @Override
    public void run() {
        if (1.this._tournament.getNumberOfGames() > 0) {
            int n = 1.this._random.nextInt(1.this._tournament.getNumberOfGames());
            Object object = 1.this._tournament;
            Object object2 = new StringBuilder();
            object2.append(n);
            object2.append("");
            object2 = object.getGameForId(object2.toString());
            if (1.this.val$listener != null && object2.getStatus().getResult() == GameResult.NO_RESULT) {
                object = new Random().nextBoolean() ? _dummyMoves : _dummyMoves2;
                object2.setLastMovesEANsAndEarlierFEN((String[])object, null);
                object2.setEngineEvaluation(new EngineEvaluation(14.0f * 1.this._random.nextFloat() - 7.0f));
                long l = System.currentTimeMillis();
                object2.setLastMoveTimeStamp(l);
                l = 120000L - (l - this.val$startTime);
                if (l <= 0L) {
                    object = 1.this._random.nextFloat() > 0.5f ? GameResult.WHITE_WINS : GameResult.BLACK_WINS;
                    object2.setStatus(new BroadcastGameStatus((GameResult)((Object)object), GameEndReason.CLOCK_FLAG, TournamentState.FINISHED));
                }
                object2.setRemainingTimeWhite(l);
                object2.setRemainingTimeBlack(l);
                object2.setWhitePlayerActive(object2.isWhitePlayerActive() ^ true);
                if (1.this.val$changeListener != null) {
                    1.this.val$changeListener.tournamentGameChanged((TournamentGameInfo)object2);
                }
            }
        }
    }
}
