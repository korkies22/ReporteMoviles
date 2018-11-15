/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.SparseArray
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import android.net.Uri;
import android.util.SparseArray;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.PlayerStanding;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.AbstractBroadcastTournamentModelMapping;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONPlayerResultParser;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTournamentStandingsParser;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTournamentTeamsParser;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.RoundModel;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.TournamentModel;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.util.HTMLStripper;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTournamentParser
extends JSONAPIResultParser<Tournament> {
    private static final String JSON_CLOCKS_KEY = "clock";
    private static final String JSON_CLOCKS_NOW_KEY = "now";
    private static final String JSON_COUNTRY_KEY = "country";
    private static final String JSON_EARLIER_FEN_KEY = "earlierFen";
    private static final String JSON_ELO_KEY = "elo";
    private static final String JSON_ENDED_BY_KEY = "endedBy";
    private static final String JSON_ENGINE_KEY = "engine";
    private static final String JSON_ENGINE_SCORE_KEY = "engineScore";
    private static final String JSON_FIDE_ID_KEY = "fideId";
    private static final String JSON_GAME_STATUS_KEY = "gameStatus";
    private static final String JSON_LAST_MOVES_KEY = "lastMoves";
    private static final String JSON_MATE_KEY = "mate";
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_RESULTS_KEY = "results";
    private static final String JSON_SCORE_KEY = "score";
    private static final String JSON_STATUS_KEY = "status";
    private static final String JSON_TIMECONTROL_BASE_KEY = "base";
    private static final String JSON_TIMECONTROL_INCREMENT_KEY = "incrementPerMove";
    public static final String JSON_TITLES_OBJECT_KEY = "titles";
    public static final String JSON_TITLE_KEY = "title";
    private static final String JSON_TOURNAMENT_DESCRIPTIONS_OBJECT_KEY = "descriptions";
    private static final String JSON_TOURNAMENT_DESCRIPTION_KEY = "description";
    private static final String JSON_TOURNAMENT_TITLES_OBJECT_KEY;
    public static final String STANDING_TYPE = "live";
    public static final String STANDING_TYPE_LIVE = "live";
    public static final String STANDING_TYPE_ROUND = "round";
    private static final String TOURNAMENT_FINISHED = "finished";
    private static final String TOURNAMENT_UPCOMING = "upcoming";
    private static final String WHITE = "white";
    private String _language = "en";
    private String _playerTemplate;
    private long _serverTimeOffset = 0L;

    public JSONTournamentParser(long l, String string) {
        if (string != null) {
            this._language = string;
        }
        this.setServerTimeOffset(l);
    }

    private String checkForRelativeURL(String string) {
        CharSequence charSequence = string;
        if (string != null) {
            charSequence = string;
            if (string.startsWith("/")) {
                charSequence = new StringBuilder();
                charSequence.append("https://chess24.com/");
                charSequence.append(string);
                charSequence = charSequence.toString();
            }
        }
        return charSequence;
    }

    private Tournament createTournamentFromModel(TournamentModel object) {
        BroadcastTournamentType broadcastTournamentType = BroadcastTournamentType.fromKey(object.getType());
        AbstractBroadcastTournamentModelMapping abstractBroadcastTournamentModelMapping = broadcastTournamentType.getTournamentModelConverter();
        Tournament tournament = abstractBroadcastTournamentModelMapping.mapFromModel((TournamentModel)object);
        tournament.setVideos(object.getVideos());
        tournament.setShareLinkTemplate(object.getShareLinkTemplate());
        Object object2 = object.getLogoUrlString();
        tournament.setPlayerLinkTemplate(object.getPlayerImageTemplate());
        if (object2 != null) {
            try {
                tournament.setLogoUrl(new URL((String)object2));
            }
            catch (MalformedURLException malformedURLException) {
                Logger.getInstance().debug(JSONTournamentParser.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            }
        }
        object2 = tournament.getRounds();
        object = abstractBroadcastTournamentModelMapping.getCurrentRoundFromModel((TournamentModel)object);
        if (object2 != null && object2.contains(object)) {
            tournament.setCurrentRound((TournamentRoundInfo)object);
        } else if (object2 != null && object2.size() > 0) {
            tournament.setCurrentRound((TournamentRoundInfo)object2.get(object2.size() - 1));
        }
        tournament.setStandingsEnabled(broadcastTournamentType.showStandings());
        return tournament;
    }

    public static long parseClock(boolean bl, JSONObject object) {
        JSONObject jSONObject = object.optJSONObject(JSON_CLOCKS_KEY);
        long l = -1L;
        if (jSONObject != null) {
            object = bl ? WHITE : "black";
            l = jSONObject.optLong((String)object, -1L);
        }
        return l;
    }

    public static long parseClock(boolean bl, boolean bl2, JSONObject jSONObject, GameStatus gameStatus, long l) {
        JSONObject jSONObject2 = jSONObject.optJSONObject(JSON_CLOCKS_KEY);
        long l2 = JSONTournamentParser.parseClock(bl, jSONObject);
        if (jSONObject2 != null) {
            long l3 = jSONObject2.optLong(JSON_CLOCKS_NOW_KEY);
            long l4 = System.currentTimeMillis();
            if (GameStatus.GAME_RUNNING.equals(gameStatus) && bl2) {
                return l2 - (l4 - (l3 - l));
            }
        }
        return l2;
    }

    public static FEN parseCurrentFEN(JSONObject object) {
        if ((object = object.optString("currentFen", "")).isEmpty()) {
            return FEN.STARTING_POSITION;
        }
        return new FEN((String)object);
    }

    public static FEN parseEarlierFEN(JSONObject object) {
        String string = object.optString(JSON_EARLIER_FEN_KEY, "");
        object = FEN.STARTING_POSITION;
        if (!string.isEmpty()) {
            object = new FEN(string);
        }
        return object;
    }

    public static EngineEvaluation parseEngineScore(JSONObject jSONObject) {
        if (jSONObject.has(JSON_ENGINE_SCORE_KEY)) {
            return new EngineEvaluation(jSONObject.optString(JSON_ENGINE_SCORE_KEY, "0"));
        }
        if ((jSONObject = jSONObject.optJSONObject(JSON_ENGINE_KEY)) != null) {
            if (jSONObject.has(JSON_MATE_KEY)) {
                int n = jSONObject.optInt(JSON_MATE_KEY);
                if (n >= 0) {
                    return new EngineEvaluation(n, true);
                }
                return new EngineEvaluation(n, false);
            }
            return new EngineEvaluation((float)jSONObject.optInt(JSON_SCORE_KEY, 0) / 100.0f);
        }
        return new EngineEvaluation(0.0f);
    }

    private GameModel parseGame(JSONObject object) throws JSONException {
        boolean bl;
        boolean bl2;
        Object object2 = object.optString("fullGameRKey");
        Object object3 = object.optJSONObject("player");
        String string = object3.optString(WHITE);
        String string2 = object3.optString("black");
        object3 = JSONTournamentParser.parseCurrentFEN(object);
        String[] arrstring = JSONTournamentParser.parseLastMovesString(object);
        FEN fEN = JSONTournamentParser.parseEarlierFEN(object);
        BroadcastGameStatus broadcastGameStatus = JSONTournamentParser.parseGameStatus(object);
        if (broadcastGameStatus != null && broadcastGameStatus.isOngoing() && object3 != null) {
            bl2 = object3.getActiveColorGuess();
            bl = bl2 ^ true;
        } else {
            bl = bl2 = false;
        }
        long l = JSONTournamentParser.parseClock(true, bl2, object, broadcastGameStatus, this.getServerTimeOffset());
        long l2 = JSONTournamentParser.parseClock(false, bl, object, broadcastGameStatus, this.getServerTimeOffset());
        object = JSONTournamentParser.parseEngineScore(object);
        object2 = new GameModel((String)object2, string, string2);
        object2.setCurrentFen((FEN)object3);
        object2.setLastMovesEANAndEarlierFEN(arrstring, fEN);
        object2.setStatus(broadcastGameStatus);
        object2.setRemainingTimeWhite(l);
        object2.setRemainingTimeBlack(l2);
        object2.setEngineEvaluation((EngineEvaluation)object);
        return object2;
    }

    private static GameResult parseGameResult(JSONObject jSONObject) {
        double d = jSONObject.optDouble(WHITE, -1.0);
        if (d < 0.0) {
            return GameResult.NO_RESULT;
        }
        int n = Double.compare(d, 0.5);
        boolean bl = false;
        n = n > 0 ? 1 : 0;
        if (Double.compare(d, 0.5) == 0) {
            bl = true;
        }
        if (n != 0) {
            return GameResult.WHITE_WINS;
        }
        if (bl) {
            return GameResult.DRAW;
        }
        return GameResult.BLACK_WINS;
    }

    public static BroadcastGameStatus parseGameStatus(JSONObject object) {
        JSONObject jSONObject = object.optJSONObject(JSON_GAME_STATUS_KEY);
        if (jSONObject != null) {
            Object object2 = jSONObject.optString(JSON_STATUS_KEY, "");
            object = TournamentState.ONGOING;
            if (object2.equals(TOURNAMENT_UPCOMING)) {
                object = TournamentState.UPCOMING;
            } else if (object2.equals(TOURNAMENT_FINISHED)) {
                object = TournamentState.FINISHED;
            }
            object2 = GameResult.NO_RESULT;
            GameEndReason gameEndReason = GameEndReason.NO_REASON;
            JSONObject jSONObject2 = jSONObject.optJSONObject("result");
            if (jSONObject2 != null) {
                object2 = JSONTournamentParser.parseGameResult(jSONObject2);
                gameEndReason = GameEndReason.getReasonForKey(jSONObject.optString(JSON_ENDED_BY_KEY, ""));
            }
            return new BroadcastGameStatus((GameResult)((Object)object2), gameEndReason, (TournamentState)((Object)object));
        }
        return new BroadcastGameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON, TournamentState.ONGOING);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<GameModel> parseGames(JSONObject jSONObject) throws JSONException {
        LinkedList<GameModel> linkedList = new LinkedList<GameModel>();
        Iterator iterator = jSONObject.keys();
        do {
            int n;
            Object object;
            block4 : {
                if (!iterator.hasNext()) {
                    return linkedList;
                }
                object = (String)iterator.next();
                n = 0;
                try {
                    int n2;
                    n = n2 = Integer.parseInt((String)object);
                    break block4;
                }
                catch (NumberFormatException numberFormatException) {}
                Logger logger = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error parsing game:");
                stringBuilder.append((String)object);
                logger.error("JSONTOurnamentParser", stringBuilder.toString());
            }
            object = this.parseGame(jSONObject.getJSONObject((String)object));
            object.setGameNumber(n);
            linkedList.add((GameModel)object);
        } while (true);
    }

    public static String[] parseLastMovesString(JSONObject jSONObject) {
        if ((jSONObject = jSONObject.optJSONArray(JSON_LAST_MOVES_KEY)) != null) {
            String[] arrstring = new String[jSONObject.length()];
            for (int i = 0; i < jSONObject.length(); ++i) {
                arrstring[i] = jSONObject.optString(i, "");
            }
            return arrstring;
        }
        return null;
    }

    private MatchModel parseMatch(JSONObject object) throws JSONException {
        MatchModel matchModel = new MatchModel(this.parseGames(object.getJSONObject("games")));
        Object object2 = object.optJSONObject("teams");
        if (object2 != null) {
            object = object2.optString("1", null);
            object2 = object2.optString("2", null);
            matchModel.setTeam1Key((String)object);
            matchModel.setTeam2Key((String)object2);
        }
        return matchModel;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<MatchModel> parseMatches(JSONObject jSONObject) throws JSONException {
        LinkedList<MatchModel> linkedList = new LinkedList<MatchModel>();
        Iterator iterator = jSONObject.keys();
        do {
            int n;
            Object object;
            JSONObject jSONObject2;
            block4 : {
                if (!iterator.hasNext()) {
                    return linkedList;
                }
                object = (String)iterator.next();
                jSONObject2 = jSONObject.getJSONObject((String)object);
                n = 0;
                try {
                    int n2;
                    n = n2 = Integer.parseInt((String)object);
                    break block4;
                }
                catch (NumberFormatException numberFormatException) {}
                Logger logger = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error parsing matchkey:");
                stringBuilder.append((String)object);
                logger.error("JSONTOurnamentParser", stringBuilder.toString());
            }
            object = this.parseMatch(jSONObject2);
            object.setMatchNumber(n);
            linkedList.add((MatchModel)object);
        } while (true);
    }

    private SparseArray<List<Integer>> parseMatchsGroups(JSONObject jSONObject) {
        SparseArray sparseArray = new SparseArray();
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            JSONArray jSONArray = jSONObject.optJSONArray(string);
            if (jSONArray == null) continue;
            LinkedList<Integer> linkedList = new LinkedList<Integer>();
            for (int i = 0; i < jSONArray.length(); ++i) {
                linkedList.add(jSONArray.optInt(i));
            }
            sparseArray.append(Integer.parseInt(string), linkedList);
        }
        return sparseArray;
    }

    private TournamentPlayer parsePlayer(JSONObject object) {
        Object object2 = object.optString(JSON_NAME_KEY);
        int n = object.optInt(JSON_FIDE_ID_KEY);
        int n2 = object.optInt(JSON_ELO_KEY);
        Object object3 = object.optString(JSON_COUNTRY_KEY);
        Object object4 = object.optString(JSON_TITLE_KEY, "");
        object3 = CountryCode.getByCode((String)object3);
        object2 = new TournamentPlayer((String)object2, n);
        object2.setTitle((String)object4);
        object2.setCountry((CountryCode)object3);
        object2.setElo(n2);
        object = object.optJSONObject(JSON_RESULTS_KEY);
        if (object != null) {
            block4 : {
                object4 = new JSONPlayerResultParser("live");
                try {
                    object = (PlayerStanding)object4.parseResult((JSONObject)object);
                    break block4;
                }
                catch (JSONException jSONException) {
                    Logger.getInstance().debug(JSONTournamentParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                }
                catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                    Logger.getInstance().debug(JSONTournamentParser.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                }
                object = null;
            }
            object2.setStanding((PlayerStanding)object);
        }
        return object2;
    }

    private Map<String, TournamentPlayer> parsePlayers(JSONObject jSONObject) {
        HashMap<String, TournamentPlayer> hashMap = new HashMap<String, TournamentPlayer>(jSONObject.length());
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            hashMap.put(string, this.parsePlayer(jSONObject.optJSONObject(string)));
        }
        return hashMap;
    }

    private RoundModel parseRound(JSONObject jSONObject) throws JSONException {
        return new RoundModel(jSONObject.optString(JSON_NAME_KEY), jSONObject.optLong("timestamp"), this.parseTimeControls(jSONObject.getJSONObject("timeControl")), this.parseMatches(jSONObject.getJSONObject("matches")));
    }

    private List<RoundModel> parseRounds(JSONObject jSONObject) throws JSONException {
        LinkedList<RoundModel> linkedList = new LinkedList<RoundModel>();
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            Object object = (String)iterator.next();
            int n = Integer.parseInt((String)object);
            object = this.parseRound(jSONObject.getJSONObject((String)object));
            object.setRoundNumber(n);
            linkedList.add((RoundModel)object);
        }
        return linkedList;
    }

    private TimeControl parseTimeControl(JSONObject jSONObject) {
        int n = jSONObject.optInt(JSON_TIMECONTROL_BASE_KEY, 0);
        int n2 = jSONObject.optInt(JSON_TIMECONTROL_INCREMENT_KEY, 0);
        return new TimeControl(n / 60000, n2 / 1000);
    }

    private SparseArray<TimeControl> parseTimeControls(JSONObject jSONObject) {
        SparseArray sparseArray = new SparseArray(jSONObject.length());
        Iterator iterator = jSONObject.keys();
        if (jSONObject.has(JSON_TIMECONTROL_BASE_KEY)) {
            sparseArray.put(1, (Object)this.parseTimeControl(jSONObject));
            return sparseArray;
        }
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            try {
                sparseArray.put(Integer.parseInt(string), (Object)this.parseTimeControl(jSONObject.optJSONObject(string)));
            }
            catch (NumberFormatException numberFormatException) {
                Logger.getInstance().debug(this.getClass().getName(), "Exception parsing timecontrols", numberFormatException);
            }
        }
        return sparseArray;
    }

    private TournamentModel parseTournamentModel(JSONObject jSONObject) throws JSONException {
        Object object;
        String string;
        CharSequence charSequence;
        TournamentModel tournamentModel = new TournamentModel();
        tournamentModel.setRounds(this.parseRounds(jSONObject.getJSONObject("rounds")));
        Object object2 = jSONObject.optJSONObject("matchGroups");
        Object object3 = new SparseArray<List<Integer>>();
        if (object2 != null) {
            object3 = this.parseMatchsGroups((JSONObject)object2);
        }
        tournamentModel.setMatchGroups((SparseArray<List<Integer>>)object3);
        object3 = this.parsePlayers(jSONObject.getJSONObject("players"));
        tournamentModel.setPlayers((Map<String, TournamentPlayer>)object3);
        tournamentModel.setCurrentRound(jSONObject.optInt("currentRound"));
        object2 = jSONObject.optJSONObject("teams");
        if (object2 != null) {
            try {
                tournamentModel.setTeams((Map<String, TournamentTeam>)new JSONTournamentTeamsParser((Map<String, TournamentPlayer>)object3, "live").parseResult((JSONObject)object2));
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(JSONTournamentParser.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            }
        }
        if ((object3 = jSONObject.optJSONObject("standings")) != null) {
            object2 = new JSONTournamentStandingsParser("live");
            try {
                tournamentModel.setStandings((List<String>)object2.parseResult((JSONObject)object3));
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                object = Logger.getInstance();
                string = JSONTournamentParser.class.getName();
                charSequence = new StringBuilder();
                charSequence.append("Invalid Standings JSON: ");
                charSequence.append(object3);
                object.debug(string, charSequence.toString(), invalidJsonForObjectException);
            }
        }
        charSequence = jSONObject.optString("rkey");
        object2 = jSONObject.optJSONObject(JSON_TOURNAMENT_DESCRIPTIONS_OBJECT_KEY);
        object3 = jSONObject.optJSONObject(JSON_TITLES_OBJECT_KEY);
        string = null;
        object3 = object3 != null ? object3.optString(this._language, null) : null;
        object = object3;
        if (object3 == null) {
            object = jSONObject.optString(JSON_TITLE_KEY);
        }
        object2 = object2 != null && this._language != null ? object2.optString(this._language, null) : null;
        object3 = object2;
        if (object2 == null) {
            object3 = jSONObject.optString(JSON_TOURNAMENT_DESCRIPTION_KEY);
        }
        object2 = object3;
        if (object3 != null) {
            object2 = HTMLStripper.strip((String)object3);
        }
        int n = jSONObject.optInt("gamesPerMatch", 4);
        String string2 = this.checkForRelativeURL(jSONObject.optString("playerImageTemplate", "https://chess24.com/images/player/{{fideId}}.jpg"));
        object3 = jSONObject.optString("logo");
        String string3 = this.checkForRelativeURL(jSONObject.optString("gameLinkTemplate"));
        tournamentModel.setRKey((String)charSequence);
        tournamentModel.setTitle((String)object);
        tournamentModel.setDescription((String)object2);
        tournamentModel.setGamesPerMatch(n);
        tournamentModel.setPlayerLinkTemplate(string2);
        tournamentModel.setShareLinkTemplate(string3);
        if ("false".equals(object3)) {
            object3 = string;
        }
        tournamentModel.setLogoUrlString(this.checkForRelativeURL((String)object3));
        tournamentModel.setVideos(this.parseVideos(jSONObject));
        jSONObject.optString("commentaryLanguage");
        tournamentModel.setType(jSONObject.optString("eventType"));
        tournamentModel.setCurrentGame(jSONObject.optInt("currentGame", 1));
        return tournamentModel;
    }

    private List<TournamentVideoInformation> parseVideos(JSONObject object) {
        LinkedList<TournamentVideoInformation> linkedList = new LinkedList<TournamentVideoInformation>();
        JSONArray jSONArray = object.optJSONArray("video");
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); ++i) {
                Object object2 = jSONArray.optJSONObject(i);
                if (object2 == null) continue;
                object = object2.optString("language", "GB");
                if ((object2 = object2.optString("rtsp", null)) == null) continue;
                Uri uri = Uri.parse((String)object2);
                object = object2 = CountryCode.getByCode((String)object);
                if (object2 == null) {
                    object = CountryCode.GB;
                }
                linkedList.add(new TournamentVideoInformation((Country)object, uri));
            }
        }
        return linkedList;
    }

    public long getServerTimeOffset() {
        return this._serverTimeOffset;
    }

    @Override
    public Tournament parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            object = this.createTournamentFromModel(this.parseTournamentModel((JSONObject)object));
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(JSONTournamentParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("Error while parsing Tournament", (Throwable)jSONException);
        }
    }

    public void setServerTimeOffset(long l) {
        this._serverTimeOffset = l;
    }
}
