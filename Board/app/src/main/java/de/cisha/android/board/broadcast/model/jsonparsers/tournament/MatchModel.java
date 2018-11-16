// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class MatchModel
{
    private List<GameModel> _games;
    private int _matchNumber;
    private String _team1Key;
    private String _team2Key;
    
    public MatchModel(List<GameModel> games) {
        if (games == null) {
            games = new ArrayList<GameModel>();
        }
        this._games = games;
    }
    
    public List<GameModel> getGames() {
        final ArrayList<Object> list = new ArrayList<Object>(this._games);
        Collections.sort(list, (Comparator<? super Object>)new Comparator<GameModel>() {
            @Override
            public int compare(final GameModel gameModel, final GameModel gameModel2) {
                if (gameModel != null && gameModel2 != null) {
                    return gameModel.getGameNumber() - gameModel2.getGameNumber();
                }
                return 0;
            }
        });
        return (List<GameModel>)list;
    }
    
    public int getMatchNumber() {
        return this._matchNumber;
    }
    
    public String getTeam1Key() {
        return this._team1Key;
    }
    
    public String getTeam2Key() {
        return this._team2Key;
    }
    
    public void setMatchNumber(final int matchNumber) {
        this._matchNumber = matchNumber;
    }
    
    public void setTeam1Key(final String team1Key) {
        this._team1Key = team1Key;
    }
    
    public void setTeam2Key(final String team2Key) {
        this._team2Key = team2Key;
    }
}
