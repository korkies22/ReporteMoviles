/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import android.util.SparseArray;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.RoundModel;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TournamentModel {
    private int _currentGame;
    private int _currentRound;
    private String _description;
    private int _gamesPerMatch;
    private String _logoUrlString;
    private SparseArray<List<Integer>> _matchGroups;
    private String _playerTemplate;
    private Map<String, TournamentPlayer> _players;
    private String _rKey;
    private List<RoundModel> _rounds = new ArrayList<RoundModel>();
    private String _shareLinkTemplate;
    private List<String> _standings;
    private Map<String, TournamentTeam> _teams;
    private String _title;
    private String _type;
    private List<TournamentVideoInformation> _videos;

    public int getCurrentGame() {
        return this._currentGame;
    }

    public int getCurrentRound() {
        return this._currentRound;
    }

    public String getDescription() {
        return this._description;
    }

    public int getGamesPerMatch() {
        return this._gamesPerMatch;
    }

    public String getLogoUrlString() {
        return this._logoUrlString;
    }

    public SparseArray<List<Integer>> getMatchGroups() {
        return this._matchGroups;
    }

    public String getPlayerImageTemplate() {
        return this._playerTemplate;
    }

    public Map<String, TournamentPlayer> getPlayers() {
        return this._players;
    }

    public List<RoundModel> getRounds() {
        return this._rounds;
    }

    public String getShareLinkTemplate() {
        return this._shareLinkTemplate;
    }

    public List<String> getStandings() {
        return this._standings;
    }

    public Map<String, TournamentTeam> getTeams() {
        return this._teams;
    }

    public String getTitle() {
        return this._title;
    }

    public String getType() {
        return this._type;
    }

    public List<TournamentVideoInformation> getVideos() {
        return this._videos;
    }

    public String getrKey() {
        return this._rKey;
    }

    public void setCurrentGame(int n) {
        this._currentGame = n;
    }

    public void setCurrentRound(int n) {
        this._currentRound = n;
    }

    public void setDescription(String string) {
        this._description = string;
    }

    public void setGamesPerMatch(int n) {
        this._gamesPerMatch = n;
    }

    public void setLogoUrlString(String string) {
        this._logoUrlString = string;
    }

    public void setMatchGroups(SparseArray<List<Integer>> sparseArray) {
        this._matchGroups = sparseArray;
    }

    public void setPlayerLinkTemplate(String string) {
        this._playerTemplate = string;
    }

    public void setPlayers(Map<String, TournamentPlayer> map) {
        this._players = map;
    }

    public void setRKey(String string) {
        this._rKey = string;
    }

    public void setRounds(List<RoundModel> list) {
        this._rounds = list;
    }

    public void setShareLinkTemplate(String string) {
        this._shareLinkTemplate = string;
    }

    public void setStandings(List<String> list) {
        this._standings = list;
    }

    public void setTeams(Map<String, TournamentTeam> map) {
        this._teams = map;
    }

    public void setTitle(String string) {
        this._title = string;
    }

    public void setType(String string) {
        this._type = string;
    }

    public void setVideos(List<TournamentVideoInformation> list) {
        this._videos = list;
    }
}
