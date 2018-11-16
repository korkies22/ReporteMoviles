// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import java.util.Map;
import java.util.List;

public class TeamTournament extends Tournament
{
    private int _gamesPerMatch;
    private List<TournamentTeam> _standings;
    private Map<TournamentRoundInfo, List<TournamentTeamPairing>> _teamPairingsPerRound;
    
    public TeamTournament(final String s, final String s2) {
        super(s, s2, BroadcastTournamentType.TEAMROUNDROBIN);
        this._standings = new ArrayList<TournamentTeam>();
        this._teamPairingsPerRound = new HashMap<TournamentRoundInfo, List<TournamentTeamPairing>>();
        this._gamesPerMatch = 4;
    }
    
    public void addTeamPairingForRound(final TournamentRoundInfo tournamentRoundInfo, final TournamentTeamPairing tournamentTeamPairing) {
        List<TournamentTeamPairing> list;
        if ((list = this._teamPairingsPerRound.get(tournamentRoundInfo)) == null) {
            list = new LinkedList<TournamentTeamPairing>();
            this._teamPairingsPerRound.put(tournamentRoundInfo, list);
        }
        list.add(tournamentTeamPairing);
    }
    
    @Override
    public int getGamesPerMatch() {
        return this._gamesPerMatch;
    }
    
    @Override
    public List<TournamentTeamPairing> getPairingsForTeam(final TournamentTeam tournamentTeam) {
        final List<TournamentRoundInfo> rounds = this.getRounds();
        final ArrayList list = new ArrayList<TournamentTeamPairing>(rounds.size());
        final Iterator<TournamentRoundInfo> iterator = rounds.iterator();
        while (iterator.hasNext()) {
            for (final TournamentTeamPairing tournamentTeamPairing : this.getTeamPairingsForRound(iterator.next())) {
                if (tournamentTeam.equals(tournamentTeamPairing.getTeam1()) || tournamentTeam.equals(tournamentTeamPairing.getTeam2())) {
                    list.add(tournamentTeamPairing);
                    break;
                }
            }
        }
        return (List<TournamentTeamPairing>)list;
    }
    
    @Override
    public List<TournamentTeamPairing> getTeamPairingsForRound(final TournamentRoundInfo tournamentRoundInfo) {
        return this._teamPairingsPerRound.get(tournamentRoundInfo);
    }
    
    @Override
    public List<TournamentTeam> getTeamStandings() {
        return this._standings;
    }
    
    @Override
    public boolean hasTeams() {
        return true;
    }
    
    @Override
    public boolean isStandingsEnabled() {
        return true;
    }
    
    public void setGamesPerMatch(final int gamesPerMatch) {
        this._gamesPerMatch = gamesPerMatch;
    }
    
    public void setStandings(final List<TournamentTeam> standings) {
        this._standings = standings;
    }
}
