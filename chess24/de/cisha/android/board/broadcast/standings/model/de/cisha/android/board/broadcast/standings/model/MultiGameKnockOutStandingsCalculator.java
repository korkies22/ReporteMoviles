/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.standings.model.IStandingsCalculator;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import de.cisha.android.board.broadcast.standings.model.MultiGameKnockoutPairing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MultiGameKnockOutStandingsCalculator
implements IStandingsCalculator {
    private List<TournamentGameInfo> getAllGamesForRounds(List<TournamentRoundInfo> object, Tournament tournament) {
        LinkedList<TournamentGameInfo> linkedList = new LinkedList<TournamentGameInfo>();
        object = object.iterator();
        while (object.hasNext()) {
            linkedList.addAll(tournament.getGamesForRound((TournamentRoundInfo)object.next()));
        }
        return linkedList;
    }

    private List<TournamentRoundInfo> getRoundsInMainRound(TournamentRoundInfo tournamentRoundInfo, Tournament object) {
        LinkedList<TournamentRoundInfo> linkedList = new LinkedList<TournamentRoundInfo>();
        for (TournamentRoundInfo tournamentRoundInfo2 : object.getRounds()) {
            if (!tournamentRoundInfo2.getMainRound().equals(tournamentRoundInfo)) continue;
            linkedList.add(tournamentRoundInfo2);
        }
        return linkedList;
    }

    private Map<MultiGameKnockoutPairing, KnockoutMatch> identifyAndCreateMatchesForGames(List<TournamentGameInfo> object, List<Integer> list) {
        HashMap<MultiGameKnockoutPairing, KnockoutMatch> hashMap = new HashMap<MultiGameKnockoutPairing, KnockoutMatch>();
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = (TournamentGameInfo)object.next();
            if (hashMap.containsKey(object2 = new MultiGameKnockoutPairing(object2.getPlayerLeft(), object2.getPlayerRight()))) continue;
            hashMap.put((MultiGameKnockoutPairing)object2, new KnockoutMatch((MultiGameKnockoutPairing)object2, list));
        }
        return hashMap;
    }

    @Override
    public List<KnockoutMatch> getMatchesForMainRound(TournamentRoundInfo arrayList, Tournament object) {
        Object object2 = this.getAllGamesForRounds(this.getRoundsInMainRound(arrayList.getMainRound(), (Tournament)object), (Tournament)object);
        arrayList = this.identifyAndCreateMatchesForGames((List<TournamentGameInfo>)object2, object.getPotentiallyNumbersOfGamesInMatchForMainround((TournamentRoundInfo)((Object)arrayList)));
        object = object2.iterator();
        while (object.hasNext()) {
            object2 = (TournamentGameInfo)object.next();
            ((KnockoutMatch)arrayList.get(new MultiGameKnockoutPairing(object2.getPlayerLeft(), object2.getPlayerRight()))).addGame((TournamentGameInfo)object2);
        }
        arrayList = new ArrayList<KnockoutMatch>(arrayList.values());
        Collections.sort(arrayList, new Comparator<KnockoutMatch>(){

            @Override
            public int compare(KnockoutMatch knockoutMatch, KnockoutMatch knockoutMatch2) {
                return knockoutMatch.getMatchNumber() - knockoutMatch2.getMatchNumber();
            }
        });
        return arrayList;
    }

}
