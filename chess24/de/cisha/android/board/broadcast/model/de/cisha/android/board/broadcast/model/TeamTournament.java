/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TeamTournament
extends Tournament {
    private int _gamesPerMatch = 4;
    private List<TournamentTeam> _standings = new ArrayList<TournamentTeam>();
    private Map<TournamentRoundInfo, List<TournamentTeamPairing>> _teamPairingsPerRound = new HashMap<TournamentRoundInfo, List<TournamentTeamPairing>>();

    public TeamTournament(String string, String string2) {
        super(string, string2, BroadcastTournamentType.TEAMROUNDROBIN);
    }

    public void addTeamPairingForRound(TournamentRoundInfo tournamentRoundInfo, TournamentTeamPairing tournamentTeamPairing) {
        List<TournamentTeamPairing> list;
        List<TournamentTeamPairing> list2 = list = this._teamPairingsPerRound.get(tournamentRoundInfo);
        if (list == null) {
            list2 = new LinkedList<TournamentTeamPairing>();
            this._teamPairingsPerRound.put(tournamentRoundInfo, list2);
        }
        list2.add(tournamentTeamPairing);
    }

    @Override
    public int getGamesPerMatch() {
        return this._gamesPerMatch;
    }

    @Override
    public List<TournamentTeamPairing> getPairingsForTeam(TournamentTeam tournamentTeam) {
        Object object = this.getRounds();
        ArrayList<TournamentTeamPairing> arrayList = new ArrayList<TournamentTeamPairing>(object.size());
        object = object.iterator();
        block0 : while (object.hasNext()) {
            for (TournamentTeamPairing tournamentTeamPairing : this.getTeamPairingsForRound((TournamentRoundInfo)object.next())) {
                if (!tournamentTeam.equals(tournamentTeamPairing.getTeam1()) && !tournamentTeam.equals(tournamentTeamPairing.getTeam2())) continue;
                arrayList.add(tournamentTeamPairing);
                continue block0;
            }
        }
        return arrayList;
    }

    @Override
    public List<TournamentTeamPairing> getTeamPairingsForRound(TournamentRoundInfo tournamentRoundInfo) {
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

    public void setGamesPerMatch(int n) {
        this._gamesPerMatch = n;
    }

    public void setStandings(List<TournamentTeam> list) {
        this._standings = list;
    }
}
