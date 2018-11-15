/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import android.util.SparseArray;
import de.cisha.android.board.broadcast.model.MainKnockoutRoundInfo;
import de.cisha.android.board.broadcast.model.MultiGameKnockOutTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.AbstractBroadcastTournamentModelMapping;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.RoundModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.TournamentModel;
import java.util.List;

public class MappingMultiknockout
extends AbstractBroadcastTournamentModelMapping {
    @Override
    public TournamentRoundInfo getCurrentRoundFromModel(TournamentModel tournamentModel) {
        return new MultiGameKnockOutTournamentRoundInfo(new MainKnockoutRoundInfo(tournamentModel.getCurrentRound()), tournamentModel.getCurrentGame());
    }

    @Override
    public Tournament mapFromModel(TournamentModel tournamentModel) {
        Tournament tournament = new Tournament(tournamentModel.getTitle(), tournamentModel.getDescription(), BroadcastTournamentType.MULTIKNOCKOUT);
        for (RoundModel roundModel : tournamentModel.getRounds()) {
            MainKnockoutRoundInfo mainKnockoutRoundInfo = new MainKnockoutRoundInfo(roundModel.getRoundNumber());
            tournament.setPotentiallyNumbersOfGamesPerMatchForMainround(mainKnockoutRoundInfo, (List)tournamentModel.getMatchGroups().get(roundModel.getRoundNumber()));
            for (MatchModel matchModel : roundModel.getMatches()) {
                for (GameModel gameModel : matchModel.getGames()) {
                    TournamentGameInfo tournamentGameInfo = MappingMultiknockout.createGameInfoFromModel(tournamentModel, roundModel, matchModel, gameModel);
                    MultiGameKnockOutTournamentRoundInfo multiGameKnockOutTournamentRoundInfo = new MultiGameKnockOutTournamentRoundInfo(mainKnockoutRoundInfo, gameModel.getGameNumber());
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
                    tournament.addGame(new Tournament.TournamentStructureKey((String)charSequence, (String)charSequence2, stringBuilder.toString()), multiGameKnockOutTournamentRoundInfo, tournamentGameInfo);
                }
            }
        }
        return tournament;
    }
}
