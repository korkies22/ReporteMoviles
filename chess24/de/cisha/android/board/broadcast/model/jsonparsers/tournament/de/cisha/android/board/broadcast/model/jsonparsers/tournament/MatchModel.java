/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MatchModel {
    private List<GameModel> _games;
    private int _matchNumber;
    private String _team1Key;
    private String _team2Key;

    public MatchModel(List<GameModel> list) {
        if (list == null) {
            list = new ArrayList<GameModel>();
        }
        this._games = list;
    }

    public List<GameModel> getGames() {
        ArrayList<GameModel> arrayList = new ArrayList<GameModel>(this._games);
        Collections.sort(arrayList, new Comparator<GameModel>(){

            @Override
            public int compare(GameModel gameModel, GameModel gameModel2) {
                if (gameModel != null && gameModel2 != null) {
                    return gameModel.getGameNumber() - gameModel2.getGameNumber();
                }
                return 0;
            }
        });
        return arrayList;
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

    public void setMatchNumber(int n) {
        this._matchNumber = n;
    }

    public void setTeam1Key(String string) {
        this._team1Key = string;
    }

    public void setTeam2Key(String string) {
        this._team2Key = string;
    }

}
