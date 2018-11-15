/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.util.EloHelper;
import java.net.URL;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tournament {
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

    public Tournament(String string, String string2, BroadcastTournamentType broadcastTournamentType) {
        this._name = string;
        this._description = string2;
        this._mapRoundGames = new HashMap<TournamentRoundInfo, List<TournamentGameInfo>>();
        this._mapIdGame = new HashMap<String, TournamentGameInfo>();
        this._mapStructureKeyGame = new HashMap<TournamentStructureKey, TournamentGameInfo>();
        this._mapPotentiallyNumbersOfGamesPerMatchToRound = new HashMap<TournamentRoundInfo, List<Integer>>();
        this._type = broadcastTournamentType;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addGame(TournamentStructureKey tournamentStructureKey, TournamentRoundInfo list, TournamentGameInfo tournamentGameInfo) {
        synchronized (this) {
            List<TournamentGameInfo> list2;
            void var3_3;
            if (!this._mapRoundGames.containsKey(list2)) {
                LinkedList<TournamentGameInfo> linkedList = new LinkedList<TournamentGameInfo>();
                this._mapRoundGames.put((TournamentRoundInfo)((Object)list2), (List<TournamentGameInfo>)linkedList);
                list2 = linkedList;
            } else {
                list2 = this._mapRoundGames.get(list2);
            }
            list2.add((TournamentGameInfo)var3_3);
            this._mapIdGame.put(var3_3.getGameID().getUuid(), (TournamentGameInfo)var3_3);
            this._mapStructureKeyGame.put(tournamentStructureKey, (TournamentGameInfo)var3_3);
            return;
        }
    }

    public String createLinkToShareForGame(String object) {
        object = this.getGameForId((String)object);
        int n = object.getMatchNumber();
        int n2 = object.getGameNumber();
        int n3 = object.getRoundNumber();
        if (this._shareLinkTemplate != null && !this._shareLinkTemplate.isEmpty()) {
            object = this._shareLinkTemplate;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n3);
            stringBuilder.append("");
            object = object.replace("{{roundId}}", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append("");
            object = object.replace("{{matchId}}", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(n2);
            stringBuilder.append("");
            return object.replace("{{gameId}}", stringBuilder.toString());
        }
        return "https://chess24.com/en/watch/live-tournaments";
    }

    public List<TournamentGameInfo> getAllFinishedGames() {
        LinkedList<TournamentGameInfo> linkedList = new LinkedList<TournamentGameInfo>();
        for (TournamentGameInfo tournamentGameInfo : this._mapIdGame.values()) {
            if (GameResult.NO_RESULT == tournamentGameInfo.getStatus().getResult()) continue;
            linkedList.add(tournamentGameInfo);
        }
        return linkedList;
    }

    public List<TournamentGameInfo> getAllRunningGames() {
        LinkedList<TournamentGameInfo> linkedList = new LinkedList<TournamentGameInfo>();
        for (TournamentGameInfo tournamentGameInfo : this._mapIdGame.values()) {
            if (!tournamentGameInfo.isOngoing()) continue;
            linkedList.add(tournamentGameInfo);
        }
        return linkedList;
    }

    public TournamentRoundInfo getCurrentRound() {
        return this._currentRound;
    }

    public String getDescription() {
        return this._description;
    }

    public TournamentGameInfo getGameFor(String string, String string2, String string3) {
        return this._mapStructureKeyGame.get(new TournamentStructureKey(string, string2, string3));
    }

    public TournamentGameInfo getGameForId(String string) {
        return this._mapIdGame.get(string);
    }

    public List<TournamentGameInfo> getGamesForPlayer(TournamentPlayer tournamentPlayer) {
        LinkedList<TournamentGameInfo> linkedList = new LinkedList<TournamentGameInfo>();
        for (TournamentGameInfo tournamentGameInfo : this._mapIdGame.values()) {
            if (!tournamentGameInfo.hasPlayer(tournamentPlayer)) continue;
            linkedList.add(tournamentGameInfo);
        }
        return linkedList;
    }

    public List<TournamentGameInfo> getGamesForRound(TournamentRoundInfo linkedList) {
        linkedList = this._mapRoundGames.containsKey(linkedList) ? new LinkedList<TournamentGameInfo>((Collection)this._mapRoundGames.get(linkedList)) : new LinkedList();
        Collections.sort(linkedList, new Comparator<TournamentGameInfo>(){

            @Override
            public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
                return tournamentGameInfo.getMatchNumber() - tournamentGameInfo2.getMatchNumber();
            }
        });
        return linkedList;
    }

    public int getGamesPerMatch() {
        return 1;
    }

    public URL getLogoUrl() {
        return this._url;
    }

    public List<TournamentRoundInfo> getMainRounds() {
        AbstractCollection abstractCollection = new HashSet<TournamentRoundInfo>();
        Iterator<TournamentRoundInfo> iterator = this._mapRoundGames.keySet().iterator();
        while (iterator.hasNext()) {
            abstractCollection.add(iterator.next().getMainRound());
        }
        abstractCollection = new LinkedList(abstractCollection);
        Collections.sort(abstractCollection);
        return abstractCollection;
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

    public List<TournamentTeamPairing> getPairingsForTeam(TournamentTeam tournamentTeam) {
        return new ArrayList<TournamentTeamPairing>(0);
    }

    public int getPerformanceForPlayer(TournamentPlayer tournamentPlayer) {
        Iterator<TournamentGameInfo> iterator = this.getGamesForPlayer(tournamentPlayer).iterator();
        int n = 0;
        float f = 0.0f;
        int n2 = 0;
        while (iterator.hasNext()) {
            float f2;
            TournamentPlayer tournamentPlayer2;
            TournamentGameInfo tournamentGameInfo = iterator.next();
            if (!tournamentGameInfo.getStatus().isFinished()) continue;
            if (tournamentGameInfo.getPlayerWhite() == tournamentPlayer) {
                tournamentPlayer2 = tournamentGameInfo.getPlayerBlack();
                f2 = tournamentGameInfo.getStatus().getResult().getScoreWhite();
            } else {
                tournamentPlayer2 = tournamentGameInfo.getPlayerWhite();
                f2 = tournamentGameInfo.getStatus().getResult().getScoreBlack();
            }
            if (tournamentPlayer2 == null || tournamentPlayer2.getElo() == 0) continue;
            f += f2;
            ++n;
            n2 += tournamentPlayer2.getElo();
        }
        return (int)EloHelper.getPerformanceFIDE(n, f, (float)n2 / (float)n);
    }

    public String getPlayerImageUrl(TournamentPlayer tournamentPlayer) {
        if (tournamentPlayer != null && this._playerTemplate != null) {
            String string = this._playerTemplate;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(tournamentPlayer.getFideId());
            return string.replace("{{fideId}}", stringBuilder.toString());
        }
        return null;
    }

    public List<TournamentPlayer> getPlayerStandings() {
        return new ArrayList<TournamentPlayer>();
    }

    public List<Integer> getPotentiallyNumbersOfGamesInMatchForMainround(TournamentRoundInfo tournamentRoundInfo) {
        return this._mapPotentiallyNumbersOfGamesPerMatchToRound.get(tournamentRoundInfo.getMainRound());
    }

    public float getRatingChangeForPlayer(TournamentPlayer tournamentPlayer) {
        float f;
        if (tournamentPlayer.getElo() != 0) {
            Iterator<TournamentGameInfo> iterator = this.getGamesForPlayer(tournamentPlayer).iterator();
            float f2 = 0.0f;
            do {
                f = f2;
                if (iterator.hasNext()) {
                    TournamentGameInfo tournamentGameInfo = iterator.next();
                    if (!tournamentGameInfo.getStatus().isFinished()) continue;
                    TournamentPlayer tournamentPlayer2 = tournamentGameInfo.getOpponentOfPlayer(tournamentPlayer);
                    f = tournamentGameInfo.getScoreForPlayer(tournamentPlayer);
                    if (tournamentPlayer2 == null || tournamentPlayer2.getElo() == 0) continue;
                    f2 += 10.0f * (f - EloHelper.getExpectedPoints(tournamentPlayer.getElo(), tournamentPlayer2.getElo()));
                    continue;
                }
                break;
            } while (true);
        } else {
            f = this.getPerformanceForPlayer(tournamentPlayer);
        }
        return f;
    }

    public List<TournamentRoundInfo> getRounds() {
        LinkedList<TournamentRoundInfo> linkedList = new LinkedList<TournamentRoundInfo>(this._mapRoundGames.keySet());
        Collections.sort(linkedList);
        return linkedList;
    }

    public List<TournamentTeamPairing> getTeamPairingsForRound(TournamentRoundInfo tournamentRoundInfo) {
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
        if (this._videos != null && this._videos.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isStandingsEnabled() {
        return this._showStandings;
    }

    public void removeGame(TournamentRoundInfo tournamentRoundInfo, TournamentGameInfo tournamentGameInfo) {
        synchronized (this) {
            if (this._mapRoundGames.containsKey(tournamentRoundInfo) && this._mapRoundGames.get(tournamentRoundInfo).remove(tournamentGameInfo)) {
                this._mapIdGame.remove(tournamentGameInfo.getGameID());
            }
            return;
        }
    }

    public void setCurrentRound(TournamentRoundInfo tournamentRoundInfo) {
        this._currentRound = tournamentRoundInfo;
    }

    public void setLogoUrl(URL uRL) {
        this._url = uRL;
    }

    public void setPlayerLinkTemplate(String string) {
        this._playerTemplate = string;
    }

    public void setPotentiallyNumbersOfGamesPerMatchForMainround(TournamentRoundInfo tournamentRoundInfo, List<Integer> list) {
        this._mapPotentiallyNumbersOfGamesPerMatchToRound.put(tournamentRoundInfo, list);
    }

    public void setShareLinkTemplate(String string) {
        this._shareLinkTemplate = string;
    }

    public void setStandingsEnabled(boolean bl) {
        this._showStandings = bl;
    }

    public void setVideos(List<TournamentVideoInformation> list) {
        this._videos = list;
    }

    public static class TournamentStructureKey {
        private String _game;
        private String _match;
        private String _round;

        public TournamentStructureKey(String string, String string2, String string3) {
            this._round = string;
            this._match = string2;
            this._game = string3;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof TournamentStructureKey)) {
                return false;
            }
            object = (TournamentStructureKey)object;
            if (this._round.equals(object._round) && this._match.equals(object._match) && this._game.equals(object._game)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this._round.hashCode() + this._match.hashCode() + this._game.hashCode();
        }
    }

}
