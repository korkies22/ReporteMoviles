// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.ArrayList;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TeamTournament;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.SimpleTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public class MappingTeamRoundRobin extends AbstractBroadcastTournamentModelMapping
{
    @Override
    public TournamentRoundInfo getCurrentRoundFromModel(final TournamentModel tournamentModel) {
        return new SimpleTournamentRoundInfo(tournamentModel.getCurrentRound());
    }
    
    @Override
    public Tournament mapFromModel(final TournamentModel tournamentModel) {
        final TeamTournament teamTournament = new TeamTournament(tournamentModel.getTitle(), tournamentModel.getDescription());
        final Map<String, TournamentTeam> teams = tournamentModel.getTeams();
        teamTournament.setGamesPerMatch(tournamentModel.getGamesPerMatch());
        for (final RoundModel roundModel : tournamentModel.getRounds()) {
            final SimpleTournamentRoundInfo simpleTournamentRoundInfo = new SimpleTournamentRoundInfo(roundModel.getRoundNumber());
            for (final MatchModel matchModel : roundModel.getMatches()) {
                final String team1Key = matchModel.getTeam1Key();
                final String team2Key = matchModel.getTeam2Key();
                TournamentTeam tournamentTeam = null;
                TournamentTeam tournamentTeam2;
                if (teams != null) {
                    tournamentTeam = teams.get(team1Key);
                    tournamentTeam2 = teams.get(team2Key);
                }
                else {
                    final Logger instance = Logger.getInstance();
                    final String name = this.getClass().getName();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("No Teams object found on team tournament: ");
                    sb.append(tournamentModel.getrKey());
                    instance.error(name, sb.toString());
                    tournamentTeam2 = null;
                }
                final ArrayList<TournamentGameInfo> list = new ArrayList<TournamentGameInfo>();
                for (final GameModel gameModel : matchModel.getGames()) {
                    final TournamentGameInfo gameInfoFromModel = AbstractBroadcastTournamentModelMapping.createGameInfoFromModel(tournamentModel, roundModel, matchModel, gameModel);
                    gameInfoFromModel.setWhitePlayerLeftSide((tournamentTeam == null && tournamentTeam2 == null) || (tournamentTeam != null && tournamentTeam.hasPlayer(gameInfoFromModel.getPlayerWhite())) || (tournamentTeam2 != null && tournamentTeam2.hasPlayer(gameInfoFromModel.getPlayerBlack())));
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(roundModel.getRoundNumber());
                    sb2.append("");
                    final String string = sb2.toString();
                    final StringBuilder sb3 = new StringBuilder();
                    sb3.append(matchModel.getMatchNumber());
                    sb3.append("");
                    final String string2 = sb3.toString();
                    final StringBuilder sb4 = new StringBuilder();
                    sb4.append(gameModel.getGameNumber());
                    sb4.append("");
                    teamTournament.addGame(new Tournament.TournamentStructureKey(string, string2, sb4.toString()), simpleTournamentRoundInfo, gameInfoFromModel);
                    list.add(gameInfoFromModel);
                }
                teamTournament.addTeamPairingForRound(simpleTournamentRoundInfo, new TournamentTeamPairing(tournamentTeam, tournamentTeam2, list));
            }
        }
        final List<String> standings = tournamentModel.getStandings();
        if (standings != null) {
            final ArrayList standings2 = new ArrayList<TournamentTeam>(standings.size());
            final Iterator<String> iterator4 = standings.iterator();
            while (iterator4.hasNext()) {
                final TournamentTeam tournamentTeam3 = teams.get(iterator4.next());
                if (tournamentTeam3 != null) {
                    standings2.add(tournamentTeam3);
                }
            }
            teamTournament.setStandings((List<TournamentTeam>)standings2);
        }
        return teamTournament;
    }
}
