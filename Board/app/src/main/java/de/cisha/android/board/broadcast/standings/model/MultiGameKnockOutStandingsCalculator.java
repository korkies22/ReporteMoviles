// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import java.util.List;

public class MultiGameKnockOutStandingsCalculator implements IStandingsCalculator
{
    private List<TournamentGameInfo> getAllGamesForRounds(final List<TournamentRoundInfo> list, final Tournament tournament) {
        final LinkedList<Object> list2 = (LinkedList<Object>)new LinkedList<TournamentGameInfo>();
        final Iterator<TournamentRoundInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.addAll(tournament.getGamesForRound(iterator.next()));
        }
        return (List<TournamentGameInfo>)list2;
    }
    
    private List<TournamentRoundInfo> getRoundsInMainRound(final TournamentRoundInfo tournamentRoundInfo, final Tournament tournament) {
        final LinkedList<TournamentRoundInfo> list = new LinkedList<TournamentRoundInfo>();
        for (final TournamentRoundInfo tournamentRoundInfo2 : tournament.getRounds()) {
            if (tournamentRoundInfo2.getMainRound().equals(tournamentRoundInfo)) {
                list.add(tournamentRoundInfo2);
            }
        }
        return list;
    }
    
    private Map<MultiGameKnockoutPairing, KnockoutMatch> identifyAndCreateMatchesForGames(final List<TournamentGameInfo> list, final List<Integer> list2) {
        final HashMap<MultiGameKnockoutPairing, KnockoutMatch> hashMap = new HashMap<MultiGameKnockoutPairing, KnockoutMatch>();
        for (final TournamentGameInfo tournamentGameInfo : list) {
            final MultiGameKnockoutPairing multiGameKnockoutPairing = new MultiGameKnockoutPairing(tournamentGameInfo.getPlayerLeft(), tournamentGameInfo.getPlayerRight());
            if (!hashMap.containsKey(multiGameKnockoutPairing)) {
                hashMap.put(multiGameKnockoutPairing, new KnockoutMatch(multiGameKnockoutPairing, list2));
            }
        }
        return hashMap;
    }
    
    @Override
    public List<KnockoutMatch> getMatchesForMainRound(final TournamentRoundInfo tournamentRoundInfo, final Tournament tournament) {
        final List<TournamentGameInfo> allGamesForRounds = this.getAllGamesForRounds(this.getRoundsInMainRound(tournamentRoundInfo.getMainRound(), tournament), tournament);
        final Map<MultiGameKnockoutPairing, KnockoutMatch> identifyAndCreateMatchesForGames = this.identifyAndCreateMatchesForGames(allGamesForRounds, tournament.getPotentiallyNumbersOfGamesInMatchForMainround(tournamentRoundInfo));
        for (final TournamentGameInfo tournamentGameInfo : allGamesForRounds) {
            identifyAndCreateMatchesForGames.get(new MultiGameKnockoutPairing(tournamentGameInfo.getPlayerLeft(), tournamentGameInfo.getPlayerRight())).addGame(tournamentGameInfo);
        }
        final ArrayList list = new ArrayList<Object>((Collection<? extends T>)identifyAndCreateMatchesForGames.values());
        Collections.sort((List<E>)list, (Comparator<? super E>)new Comparator<KnockoutMatch>() {
            @Override
            public int compare(final KnockoutMatch knockoutMatch, final KnockoutMatch knockoutMatch2) {
                return knockoutMatch.getMatchNumber() - knockoutMatch2.getMatchNumber();
            }
        });
        return (List<KnockoutMatch>)list;
    }
}
