/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.SimpleTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TeamTournament;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.AbstractBroadcastTournamentModelMapping;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.RoundModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.TournamentModel;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MappingTeamRoundRobin
extends AbstractBroadcastTournamentModelMapping {
    @Override
    public TournamentRoundInfo getCurrentRoundFromModel(TournamentModel tournamentModel) {
        return new SimpleTournamentRoundInfo(tournamentModel.getCurrentRound());
    }

    @Override
    public Tournament mapFromModel(TournamentModel object) {
        Object object2;
        TeamTournament teamTournament = new TeamTournament(object.getTitle(), object.getDescription());
        Map<String, TournamentTeam> map = object.getTeams();
        teamTournament.setGamesPerMatch(object.getGamesPerMatch());
        for (RoundModel roundModel : object.getRounds()) {
            SimpleTournamentRoundInfo simpleTournamentRoundInfo = new SimpleTournamentRoundInfo(roundModel.getRoundNumber());
            for (MatchModel matchModel : roundModel.getMatches()) {
                Object object3 = matchModel.getTeam1Key();
                object2 = matchModel.getTeam2Key();
                TournamentTeam tournamentTeam = null;
                if (map != null) {
                    tournamentTeam = map.get(object3);
                    object2 = map.get(object2);
                } else {
                    object2 = Logger.getInstance();
                    object3 = this.getClass().getName();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No Teams object found on team tournament: ");
                    stringBuilder.append(object.getrKey());
                    object2.error((String)object3, stringBuilder.toString());
                    object2 = null;
                }
                object3 = new ArrayList();
                for (GameModel gameModel : matchModel.getGames()) {
                    TournamentGameInfo tournamentGameInfo = MappingTeamRoundRobin.createGameInfoFromModel((TournamentModel)object, roundModel, matchModel, gameModel);
                    boolean bl = tournamentTeam == null && object2 == null || tournamentTeam != null && tournamentTeam.hasPlayer(tournamentGameInfo.getPlayerWhite()) || object2 != null && object2.hasPlayer(tournamentGameInfo.getPlayerBlack());
                    tournamentGameInfo.setWhitePlayerLeftSide(bl);
                    CharSequence charSequence = new StringBuilder();
                    charSequence.append(roundModel.getRoundNumber());
                    charSequence.append("");
                    charSequence = charSequence.toString();
                    CharSequence charSequence2 = new StringBuilder();
                    charSequence2.append(matchModel.getMatchNumber());
                    charSequence2.append("");
                    charSequence2 = charSequence2.toString();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(gameModel.getGameNumber());
                    stringBuilder.append("");
                    teamTournament.addGame(new Tournament.TournamentStructureKey((String)charSequence, (String)charSequence2, stringBuilder.toString()), simpleTournamentRoundInfo, tournamentGameInfo);
                    object3.add(tournamentGameInfo);
                }
                teamTournament.addTeamPairingForRound(simpleTournamentRoundInfo, new TournamentTeamPairing(tournamentTeam, (TournamentTeam)object2, (List<TournamentGameInfo>)object3));
            }
        }
        Object object4 = object.getStandings();
        if (object4 != null) {
            object = new ArrayList(object4.size());
            object4 = object4.iterator();
            while (object4.hasNext()) {
                object2 = map.get((String)object4.next());
                if (object2 == null) continue;
                object.add(object2);
            }
            teamTournament.setStandings((List<TournamentTeam>)object);
        }
        return teamTournament;
    }
}
