// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.TreeMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map;
import de.cisha.chess.model.Country;

public class TournamentTeam
{
    private Country _country;
    private String _key;
    private String _name;
    private Map<String, TournamentPlayer> _playersByBoard;
    private TeamStanding _standings;
    
    public TournamentTeam(final String key, final String name, final Map<String, TournamentPlayer> playersByBoard) {
        this._playersByBoard = playersByBoard;
        this._name = name;
        this._key = key;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TournamentTeam)) {
            return false;
        }
        final TournamentTeam tournamentTeam = (TournamentTeam)o;
        return this._key != null && this._key.equals(tournamentTeam.getKey());
    }
    
    public float getAverageRatingOfTopPlayers(final int n) {
        final SortedMap<String, TournamentPlayer> playersByBoard = this.getPlayersByBoard();
        final Iterator<String> iterator = playersByBoard.keySet().iterator();
        float n2 = 0.0f;
        int n3 = 0;
        float n4 = 0.0f;
        int n5 = 0;
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final int n6 = n3 + 1;
            if (n6 > n) {
                break;
            }
            final TournamentPlayer tournamentPlayer = playersByBoard.get(s);
            n3 = n6;
            if (tournamentPlayer.getElo() == 0) {
                continue;
            }
            ++n5;
            n4 += tournamentPlayer.getElo();
            n3 = n6;
        }
        if (n5 != 0) {
            n2 = n4 / n5;
        }
        return n2;
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
        final TreeMap<Object, Object> treeMap = (TreeMap<Object, Object>)new TreeMap<String, TournamentPlayer>(new Comparator<String>() {
            @Override
            public int compare(final String s, final String s2) {
                try {
                    return Integer.valueOf(Integer.parseInt(s)).compareTo(Integer.valueOf(Integer.parseInt(s2)));
                }
                catch (NumberFormatException ex) {
                    return s.compareTo(s2);
                }
            }
        });
        treeMap.putAll(this._playersByBoard);
        return (SortedMap<String, TournamentPlayer>)treeMap;
    }
    
    public boolean hasPlayer(final TournamentPlayer tournamentPlayer) {
        return tournamentPlayer != null && this._playersByBoard.containsValue(tournamentPlayer);
    }
    
    @Override
    public int hashCode() {
        if (this._key != null) {
            return this._key.hashCode();
        }
        return 17;
    }
    
    public void setCountry(final Country country) {
        this._country = country;
    }
    
    public void setCurrentStandings(final TeamStanding standings) {
        this._standings = standings;
    }
}
