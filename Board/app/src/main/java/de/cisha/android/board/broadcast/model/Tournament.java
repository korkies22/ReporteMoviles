// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.chess.util.EloHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import java.util.Iterator;
import de.cisha.chess.model.GameResult;
import java.util.LinkedList;
import java.util.HashMap;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import java.net.URL;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import java.util.List;
import java.util.Map;

public class Tournament
{
    private TournamentRoundInfo _currentRound;
    private String _description;
    private Map<String, TournamentGameInfo> _mapIdGame;
    private Map<TournamentRoundInfo, List<Integer>> _mapPotentiallyNumbersOfGamesPerMatchToRound;
    private Map<TournamentRoundInfo, List<TournamentGameInfo>> _mapRoundGames;
    private Map<TournamentStructureKey, TournamentGameInfo> _mapStructureKeyGame;
    private String _name;
    private String _playerTemplate;
    private String _shareLinkTemplate;
    private boolean _showStandings;
    private BroadcastTournamentType _type;
    private URL _url;
    private List<TournamentVideoInformation> _videos;
    
    public Tournament(final String name, final String description, final BroadcastTournamentType type) {
        this._name = name;
        this._description = description;
        this._mapRoundGames = new HashMap<TournamentRoundInfo, List<TournamentGameInfo>>();
        this._mapIdGame = new HashMap<String, TournamentGameInfo>();
        this._mapStructureKeyGame = new HashMap<TournamentStructureKey, TournamentGameInfo>();
        this._mapPotentiallyNumbersOfGamesPerMatchToRound = new HashMap<TournamentRoundInfo, List<Integer>>();
        this._type = type;
    }
    
    public void addGame(final TournamentStructureKey tournamentStructureKey, final TournamentRoundInfo tournamentRoundInfo, final TournamentGameInfo tournamentGameInfo) {
        synchronized (this) {
            List<TournamentGameInfo> list2;
            if (!this._mapRoundGames.containsKey(tournamentRoundInfo)) {
                final LinkedList<TournamentGameInfo> list = new LinkedList<TournamentGameInfo>();
                this._mapRoundGames.put(tournamentRoundInfo, list);
                list2 = list;
            }
            else {
                list2 = this._mapRoundGames.get(tournamentRoundInfo);
            }
            list2.add(tournamentGameInfo);
            this._mapIdGame.put(tournamentGameInfo.getGameID().getUuid(), tournamentGameInfo);
            this._mapStructureKeyGame.put(tournamentStructureKey, tournamentGameInfo);
        }
    }
    
    public String createLinkToShareForGame(String s) {
        final TournamentGameInfo gameForId = this.getGameForId(s);
        final int matchNumber = gameForId.getMatchNumber();
        final int gameNumber = gameForId.getGameNumber();
        final int roundNumber = gameForId.getRoundNumber();
        if (this._shareLinkTemplate != null && !this._shareLinkTemplate.isEmpty()) {
            s = this._shareLinkTemplate;
            final StringBuilder sb = new StringBuilder();
            sb.append(roundNumber);
            sb.append("");
            s = s.replace("{{roundId}}", sb.toString());
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(matchNumber);
            sb2.append("");
            s = s.replace("{{matchId}}", sb2.toString());
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(gameNumber);
            sb3.append("");
            return s.replace("{{gameId}}", sb3.toString());
        }
        return "https://chess24.com/en/watch/live-tournaments";
    }
    
    public List<TournamentGameInfo> getAllFinishedGames() {
        final LinkedList<TournamentGameInfo> list = new LinkedList<TournamentGameInfo>();
        for (final TournamentGameInfo tournamentGameInfo : this._mapIdGame.values()) {
            if (GameResult.NO_RESULT != tournamentGameInfo.getStatus().getResult()) {
                list.add(tournamentGameInfo);
            }
        }
        return list;
    }
    
    public List<TournamentGameInfo> getAllRunningGames() {
        final LinkedList<TournamentGameInfo> list = new LinkedList<TournamentGameInfo>();
        for (final TournamentGameInfo tournamentGameInfo : this._mapIdGame.values()) {
            if (tournamentGameInfo.isOngoing()) {
                list.add(tournamentGameInfo);
            }
        }
        return list;
    }
    
    public TournamentRoundInfo getCurrentRound() {
        return this._currentRound;
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public TournamentGameInfo getGameFor(final String s, final String s2, final String s3) {
        return this._mapStructureKeyGame.get(new TournamentStructureKey(s, s2, s3));
    }
    
    public TournamentGameInfo getGameForId(final String s) {
        return this._mapIdGame.get(s);
    }
    
    public List<TournamentGameInfo> getGamesForPlayer(final TournamentPlayer tournamentPlayer) {
        final LinkedList<TournamentGameInfo> list = new LinkedList<TournamentGameInfo>();
        for (final TournamentGameInfo tournamentGameInfo : this._mapIdGame.values()) {
            if (tournamentGameInfo.hasPlayer(tournamentPlayer)) {
                list.add(tournamentGameInfo);
            }
        }
        return list;
    }
    
    public List<TournamentGameInfo> getGamesForRound(final TournamentRoundInfo tournamentRoundInfo) {
        LinkedList<Object> list;
        if (this._mapRoundGames.containsKey(tournamentRoundInfo)) {
            list = new LinkedList<Object>(this._mapRoundGames.get(tournamentRoundInfo));
        }
        else {
            list = new LinkedList<Object>();
        }
        Collections.sort(list, (Comparator<? super Object>)new Comparator<TournamentGameInfo>() {
            @Override
            public int compare(final TournamentGameInfo tournamentGameInfo, final TournamentGameInfo tournamentGameInfo2) {
                return tournamentGameInfo.getMatchNumber() - tournamentGameInfo2.getMatchNumber();
            }
        });
        return (List<TournamentGameInfo>)list;
    }
    
    public int getGamesPerMatch() {
        return 1;
    }
    
    public URL getLogoUrl() {
        return this._url;
    }
    
    public List<TournamentRoundInfo> getMainRounds() {
        final HashSet<TournamentRoundInfo> set = new HashSet<TournamentRoundInfo>();
        final Iterator<TournamentRoundInfo> iterator = this._mapRoundGames.keySet().iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next().getMainRound());
        }
        final LinkedList list = new LinkedList<Comparable>(set);
        Collections.sort((List<Comparable>)list);
        return (List<TournamentRoundInfo>)list;
    }
    
    public String getName() {
        return this._name;
    }
    
    public int getNumberOfGames() {
        return this._mapIdGame.size();
    }
    
    public int getNumberOfRounds() {
        return this._mapRoundGames.size();
    }
    
    public List<TournamentTeamPairing> getPairingsForTeam(final TournamentTeam tournamentTeam) {
        return new ArrayList<TournamentTeamPairing>(0);
    }
    
    public int getPerformanceForPlayer(final TournamentPlayer tournamentPlayer) {
        final Iterator<TournamentGameInfo> iterator = this.getGamesForPlayer(tournamentPlayer).iterator();
        int n = 0;
        float n2 = 0.0f;
        int n3 = 0;
        while (iterator.hasNext()) {
            final TournamentGameInfo tournamentGameInfo = iterator.next();
            if (tournamentGameInfo.getStatus().isFinished()) {
                TournamentPlayer tournamentPlayer2;
                float n4;
                if (tournamentGameInfo.getPlayerWhite() == tournamentPlayer) {
                    tournamentPlayer2 = tournamentGameInfo.getPlayerBlack();
                    n4 = tournamentGameInfo.getStatus().getResult().getScoreWhite();
                }
                else {
                    tournamentPlayer2 = tournamentGameInfo.getPlayerWhite();
                    n4 = tournamentGameInfo.getStatus().getResult().getScoreBlack();
                }
                if (tournamentPlayer2 == null || tournamentPlayer2.getElo() == 0) {
                    continue;
                }
                n2 += n4;
                ++n;
                n3 += tournamentPlayer2.getElo();
            }
        }
        return (int)EloHelper.getPerformanceFIDE(n, n2, n3 / n);
    }
    
    public String getPlayerImageUrl(final TournamentPlayer tournamentPlayer) {
        if (tournamentPlayer != null && this._playerTemplate != null) {
            final String playerTemplate = this._playerTemplate;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(tournamentPlayer.getFideId());
            return playerTemplate.replace("{{fideId}}", sb.toString());
        }
        return null;
    }
    
    public List<TournamentPlayer> getPlayerStandings() {
        return new ArrayList<TournamentPlayer>();
    }
    
    public List<Integer> getPotentiallyNumbersOfGamesInMatchForMainround(final TournamentRoundInfo tournamentRoundInfo) {
        return this._mapPotentiallyNumbersOfGamesPerMatchToRound.get(tournamentRoundInfo.getMainRound());
    }
    
    public float getRatingChangeForPlayer(final TournamentPlayer tournamentPlayer) {
        float n2;
        if (tournamentPlayer.getElo() != 0) {
            final Iterator<TournamentGameInfo> iterator = this.getGamesForPlayer(tournamentPlayer).iterator();
            float n = 0.0f;
            while (true) {
                n2 = n;
                if (!iterator.hasNext()) {
                    break;
                }
                final TournamentGameInfo tournamentGameInfo = iterator.next();
                if (!tournamentGameInfo.getStatus().isFinished()) {
                    continue;
                }
                final TournamentPlayer opponentOfPlayer = tournamentGameInfo.getOpponentOfPlayer(tournamentPlayer);
                final float scoreForPlayer = tournamentGameInfo.getScoreForPlayer(tournamentPlayer);
                if (opponentOfPlayer == null || opponentOfPlayer.getElo() == 0) {
                    continue;
                }
                n += 10.0f * (scoreForPlayer - EloHelper.getExpectedPoints(tournamentPlayer.getElo(), opponentOfPlayer.getElo()));
            }
        }
        else {
            n2 = this.getPerformanceForPlayer(tournamentPlayer);
        }
        return n2;
    }
    
    public List<TournamentRoundInfo> getRounds() {
        final LinkedList<Comparable> list = new LinkedList<Comparable>(this._mapRoundGames.keySet());
        Collections.sort(list);
        return (List<TournamentRoundInfo>)list;
    }
    
    public List<TournamentTeamPairing> getTeamPairingsForRound(final TournamentRoundInfo tournamentRoundInfo) {
        return null;
    }
    
    public List<TournamentTeam> getTeamStandings() {
        return new ArrayList<TournamentTeam>();
    }
    
    public BroadcastTournamentType getType() {
        return this._type;
    }
    
    public List<TournamentVideoInformation> getVideos() {
        return this._videos;
    }
    
    public boolean hasTeams() {
        return false;
    }
    
    public boolean hasVideos() {
        return this._videos != null && this._videos.size() > 0;
    }
    
    public boolean isStandingsEnabled() {
        return this._showStandings;
    }
    
    public void removeGame(final TournamentRoundInfo tournamentRoundInfo, final TournamentGameInfo tournamentGameInfo) {
        synchronized (this) {
            if (this._mapRoundGames.containsKey(tournamentRoundInfo) && this._mapRoundGames.get(tournamentRoundInfo).remove(tournamentGameInfo)) {
                this._mapIdGame.remove(tournamentGameInfo.getGameID());
            }
        }
    }
    
    public void setCurrentRound(final TournamentRoundInfo currentRound) {
        this._currentRound = currentRound;
    }
    
    public void setLogoUrl(final URL url) {
        this._url = url;
    }
    
    public void setPlayerLinkTemplate(final String playerTemplate) {
        this._playerTemplate = playerTemplate;
    }
    
    public void setPotentiallyNumbersOfGamesPerMatchForMainround(final TournamentRoundInfo tournamentRoundInfo, final List<Integer> list) {
        this._mapPotentiallyNumbersOfGamesPerMatchToRound.put(tournamentRoundInfo, list);
    }
    
    public void setShareLinkTemplate(final String shareLinkTemplate) {
        this._shareLinkTemplate = shareLinkTemplate;
    }
    
    public void setStandingsEnabled(final boolean showStandings) {
        this._showStandings = showStandings;
    }
    
    public void setVideos(final List<TournamentVideoInformation> videos) {
        this._videos = videos;
    }
    
    public static class TournamentStructureKey
    {
        private String _game;
        private String _match;
        private String _round;
        
        public TournamentStructureKey(final String round, final String match, final String game) {
            this._round = round;
            this._match = match;
            this._game = game;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof TournamentStructureKey)) {
                return false;
            }
            final TournamentStructureKey tournamentStructureKey = (TournamentStructureKey)o;
            return this._round.equals(tournamentStructureKey._round) && this._match.equals(tournamentStructureKey._match) && this._game.equals(tournamentStructureKey._game);
        }
        
        @Override
        public int hashCode() {
            return this._round.hashCode() + this._match.hashCode() + this._game.hashCode();
        }
    }
}
