/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import android.util.SparseArray;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.RoundModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.TournamentModel;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import java.util.Map;

public abstract class AbstractBroadcastTournamentModelMapping {
    protected static TournamentGameInfo createGameInfoFromModel(TournamentModel object, RoundModel roundModel, MatchModel matchModel, GameModel gameModel) {
        TournamentGameInfo tournamentGameInfo = new TournamentGameInfo(gameModel.getFullRKey(), object.getPlayers().get(gameModel.getPlayerWhiteId()), object.getPlayers().get(gameModel.getPlayerBlackId()));
        tournamentGameInfo.setCurrentFen(gameModel.getCurrentFen());
        tournamentGameInfo.setLastMovesEANsAndEarlierFEN(gameModel.getLastMoveEANs(), gameModel.getEarlierFEN());
        tournamentGameInfo.setStatus(gameModel.getGameStatus());
        tournamentGameInfo.setRemainingTimeWhite(gameModel.getRemainingTimeWhite());
        tournamentGameInfo.setRemainingTimeBlack(gameModel.getRemainingTimeBlack());
        tournamentGameInfo.setEngineEvaluation(gameModel.getEngineEvaluation());
        FEN fEN = gameModel.getCurrentFen();
        object = fEN;
        if (fEN == null) {
            object = FEN.STARTING_POSITION;
        }
        tournamentGameInfo.setWhitePlayerActive(object.getActiveColorGuess());
        tournamentGameInfo.setTimeControl((TimeControl)roundModel.getTimeControls().get(gameModel.getGameNumber()));
        tournamentGameInfo.setMatchNumber(matchModel.getMatchNumber());
        tournamentGameInfo.setGameNumber(gameModel.getGameNumber());
        tournamentGameInfo.setRoundNumber(roundModel.getRoundNumber());
        return tournamentGameInfo;
    }

    public abstract TournamentRoundInfo getCurrentRoundFromModel(TournamentModel var1);

    public abstract Tournament mapFromModel(TournamentModel var1);
}
