/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.SimpleTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.SinglePlayerTournament;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.AbstractBroadcastTournamentModelMapping;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.RoundModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.TournamentModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MappingSinglePlayerTournament
extends AbstractBroadcastTournamentModelMapping {
    @Override
    public TournamentRoundInfo getCurrentRoundFromModel(TournamentModel tournamentModel) {
        return new SimpleTournamentRoundInfo(tournamentModel.getCurrentRound());
    }

    @Override
    public Tournament mapFromModel(TournamentModel object) {
        Object object2;
        SinglePlayerTournament singlePlayerTournament = new SinglePlayerTournament(object.getTitle(), object.getDescription());
        for (RoundModel object22 : object.getRounds()) {
            object2 = new SimpleTournamentRoundInfo(object22.getRoundNumber());
            for (MatchModel matchModel : object22.getMatches()) {
                for (GameModel gameModel : matchModel.getGames()) {
                    TournamentGameInfo tournamentGameInfo = MappingSinglePlayerTournament.createGameInfoFromModel(object, object22, matchModel, gameModel);
                    CharSequence charSequence = new StringBuilder();
                    charSequence.append(object22.getRoundNumber());
                    charSequence.append("");
                    charSequence = charSequence.toString();
                    CharSequence charSequence2 = new StringBuilder();
                    charSequence2.append(matchModel.getMatchNumber());
                    charSequence2.append("");
                    charSequence2 = charSequence2.toString();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(gameModel.getGameNumber());
                    stringBuilder.append("");
                    singlePlayerTournament.addGame(new Tournament.TournamentStructureKey((String)charSequence, (String)charSequence2, stringBuilder.toString()), (TournamentRoundInfo)object2, tournamentGameInfo);
                }
            }
        }
        Map<String, TournamentPlayer> map = object.getPlayers();
        List<String> list = object.getStandings();
        if (list != null) {
            object = new ArrayList<TournamentPlayer>(list.size());
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                object2 = (TournamentPlayer)map.get(iterator.next());
                if (object2 == null) continue;
                object.add((TournamentPlayer)object2);
            }
            singlePlayerTournament.setPlayerStandings(object);
        }
        return singlePlayerTournament;
    }
}
