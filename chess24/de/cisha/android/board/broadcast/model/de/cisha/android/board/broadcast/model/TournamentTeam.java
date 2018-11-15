/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.chess.model.Country;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class TournamentTeam {
    private Country _country;
    private String _key;
    private String _name;
    private Map<String, TournamentPlayer> _playersByBoard;
    private TeamStanding _standings;

    public TournamentTeam(String string, String string2, Map<String, TournamentPlayer> map) {
        this._playersByBoard = map;
        this._name = string2;
        this._key = string;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TournamentTeam)) {
            return false;
        }
        object = (TournamentTeam)object;
        if (this._key != null && this._key.equals(object.getKey())) {
            return true;
        }
        return false;
    }

    public float getAverageRatingOfTopPlayers(int n) {
        SortedMap<String, TournamentPlayer> sortedMap = this.getPlayersByBoard();
        Iterator<String> iterator = sortedMap.keySet().iterator();
        float f = 0.0f;
        int n2 = 0;
        float f2 = 0.0f;
        int n3 = 0;
        while (iterator.hasNext()) {
            Object object = iterator.next();
            int n4 = n2 + 1;
            if (n4 > n) break;
            object = (TournamentPlayer)sortedMap.get(object);
            n2 = n4;
            if (object.getElo() == 0) continue;
            ++n3;
            f2 += (float)object.getElo();
            n2 = n4;
        }
        if (n3 != 0) {
            f = f2 / (float)n3;
        }
        return f;
    }

    public Country getCountry() {
        return this._country;
    }

    public TeamStanding getCurrentStandings() {
        return this._standings;
    }

    public String getKey() {
        return this._key;
    }

    public String getName() {
        return this._name;
    }

    public SortedMap<String, TournamentPlayer> getPlayersByBoard() {
        TreeMap<String, TournamentPlayer> treeMap = new TreeMap<String, TournamentPlayer>(new Comparator<String>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public int compare(String string, String string2) {
                try {
                    return Integer.valueOf(Integer.parseInt(string)).compareTo(Integer.parseInt(string2));
                }
                catch (NumberFormatException numberFormatException) {
                    return string.compareTo(string2);
                }
            }
        });
        treeMap.putAll(this._playersByBoard);
        return treeMap;
    }

    public boolean hasPlayer(TournamentPlayer tournamentPlayer) {
        if (tournamentPlayer != null && this._playersByBoard.containsValue(tournamentPlayer)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this._key != null) {
            return this._key.hashCode();
        }
        return 17;
    }

    public void setCountry(Country country) {
        this._country = country;
    }

    public void setCurrentStandings(TeamStanding teamStanding) {
        this._standings = teamStanding;
    }

}
