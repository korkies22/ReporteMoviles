// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.model.FEN;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;

public abstract class AbstractBroadcastTournamentModelMapping
{
    protected static TournamentGameInfo createGameInfoFromModel(final TournamentModel tournamentModel, final RoundModel roundModel, final MatchModel matchModel, final GameModel gameModel) {
        final TournamentGameInfo tournamentGameInfo = new TournamentGameInfo(gameModel.getFullRKey(), tournamentModel.getPlayers().get(gameModel.getPlayerWhiteId()), tournamentModel.getPlayers().get(gameModel.getPlayerBlackId()));
        tournamentGameInfo.setCurrentFen(gameModel.getCurrentFen());
        tournamentGameInfo.setLastMovesEANsAndEarlierFEN(gameModel.getLastMoveEANs(), gameModel.getEarlierFEN());
        tournamentGameInfo.setStatus(gameModel.getGameStatus());
        tournamentGameInfo.setRemainingTimeWhite(gameModel.getRemainingTimeWhite());
        tournamentGameInfo.setRemainingTimeBlack(gameModel.getRemainingTimeBlack());
        tournamentGameInfo.setEngineEvaluation(gameModel.getEngineEvaluation());
        FEN fen;
        if ((fen = gameModel.getCurrentFen()) == null) {
            fen = FEN.STARTING_POSITION;
        }
        tournamentGameInfo.setWhitePlayerActive(fen.getActiveColorGuess());
        tournamentGameInfo.setTimeControl((TimeControl)roundModel.getTimeControls().get(gameModel.getGameNumber()));
        tournamentGameInfo.setMatchNumber(matchModel.getMatchNumber());
        tournamentGameInfo.setGameNumber(gameModel.getGameNumber());
        tournamentGameInfo.setRoundNumber(roundModel.getRoundNumber());
        return tournamentGameInfo;
    }
    
    public abstract TournamentRoundInfo getCurrentRoundFromModel(final TournamentModel p0);
    
    public abstract Tournament mapFromModel(final TournamentModel p0);
}
