// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.Iterator;
import java.util.List;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.SimpleTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public class MappingUnknownTournament extends AbstractBroadcastTournamentModelMapping
{
    @Override
    public TournamentRoundInfo getCurrentRoundFromModel(final TournamentModel tournamentModel) {
        return new SimpleTournamentRoundInfo(tournamentModel.getCurrentRound());
    }
    
    @Override
    public Tournament mapFromModel(final TournamentModel tournamentModel) {
        final Tournament tournament = new Tournament(tournamentModel.getTitle(), tournamentModel.getDescription(), BroadcastTournamentType.UNKNOWN);
        for (final RoundModel roundModel : tournamentModel.getRounds()) {
            final SimpleTournamentRoundInfo simpleTournamentRoundInfo = new SimpleTournamentRoundInfo(roundModel.getRoundNumber());
            tournament.setPotentiallyNumbersOfGamesPerMatchForMainround(simpleTournamentRoundInfo, (List<Integer>)tournamentModel.getMatchGroups().get(roundModel.getRoundNumber()));
            for (final MatchModel matchModel : roundModel.getMatches()) {
                for (final GameModel gameModel : matchModel.getGames()) {
                    final TournamentGameInfo gameInfoFromModel = AbstractBroadcastTournamentModelMapping.createGameInfoFromModel(tournamentModel, roundModel, matchModel, gameModel);
                    final StringBuilder sb = new StringBuilder();
                    sb.append(roundModel.getRoundNumber());
                    sb.append("");
                    final String string = sb.toString();
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(matchModel.getMatchNumber());
                    sb2.append("");
                    final String string2 = sb2.toString();
                    final StringBuilder sb3 = new StringBuilder();
                    sb3.append(gameModel.getGameNumber());
                    sb3.append("");
                    tournament.addGame(new Tournament.TournamentStructureKey(string, string2, sb3.toString()), simpleTournamentRoundInfo, gameInfoFromModel);
                }
            }
        }
        return tournament;
    }
}
