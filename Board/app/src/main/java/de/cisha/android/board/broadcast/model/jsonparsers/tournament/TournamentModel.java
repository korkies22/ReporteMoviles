// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import java.util.ArrayList;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import java.util.Map;
import java.util.List;
import android.util.SparseArray;

public class TournamentModel
{
    private int _currentGame;
    private int _currentRound;
    private String _description;
    private int _gamesPerMatch;
    private String _logoUrlString;
    private SparseArray<List<Integer>> _matchGroups;
    private String _playerTemplate;
    private Map<String, TournamentPlayer> _players;
    private String _rKey;
    private List<RoundModel> _rounds;
    private String _shareLinkTemplate;
    private List<String> _standings;
    private Map<String, TournamentTeam> _teams;
    private String _title;
    private String _type;
    private List<TournamentVideoInformation> _videos;
    
    public TournamentModel() {
        this._rounds = new ArrayList<RoundModel>();
    }
    
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
    
    public void setCurrentGame(final int currentGame) {
        this._currentGame = currentGame;
    }
    
    public void setCurrentRound(final int currentRound) {
        this._currentRound = currentRound;
    }
    
    public void setDescription(final String description) {
        this._description = description;
    }
    
    public void setGamesPerMatch(final int gamesPerMatch) {
        this._gamesPerMatch = gamesPerMatch;
    }
    
    public void setLogoUrlString(final String logoUrlString) {
        this._logoUrlString = logoUrlString;
    }
    
    public void setMatchGroups(final SparseArray<List<Integer>> matchGroups) {
        this._matchGroups = matchGroups;
    }
    
    public void setPlayerLinkTemplate(final String playerTemplate) {
        this._playerTemplate = playerTemplate;
    }
    
    public void setPlayers(final Map<String, TournamentPlayer> players) {
        this._players = players;
    }
    
    public void setRKey(final String rKey) {
        this._rKey = rKey;
    }
    
    public void setRounds(final List<RoundModel> rounds) {
        this._rounds = rounds;
    }
    
    public void setShareLinkTemplate(final String shareLinkTemplate) {
        this._shareLinkTemplate = shareLinkTemplate;
    }
    
    public void setStandings(final List<String> standings) {
        this._standings = standings;
    }
    
    public void setTeams(final Map<String, TournamentTeam> teams) {
        this._teams = teams;
    }
    
    public void setTitle(final String title) {
        this._title = title;
    }
    
    public void setType(final String type) {
        this._type = type;
    }
    
    public void setVideos(final List<TournamentVideoInformation> videos) {
        this._videos = videos;
    }
}
