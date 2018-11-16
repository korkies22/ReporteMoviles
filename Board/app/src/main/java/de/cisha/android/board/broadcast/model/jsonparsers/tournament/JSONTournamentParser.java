// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.chess.model.Country;
import android.net.Uri;
import de.cisha.android.board.broadcast.video.TournamentVideoInformation;
import de.cisha.chess.util.HTMLStripper;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.HashMap;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.broadcast.model.PlayerStanding;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import android.util.SparseArray;
import org.json.JSONArray;
import java.util.Iterator;
import java.util.LinkedList;
import de.cisha.chess.model.GameEndReason;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.chess.model.GameResult;
import org.json.JSONException;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameStatus;
import org.json.JSONObject;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import java.net.MalformedURLException;
import de.cisha.chess.util.Logger;
import java.net.URL;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONTournamentParser extends JSONAPIResultParser<Tournament>
{
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
    private String _language;
    private String _playerTemplate;
    private long _serverTimeOffset;
    
    public JSONTournamentParser(final long serverTimeOffset, final String language) {
        this._serverTimeOffset = 0L;
        this._language = "en";
        if (language != null) {
            this._language = language;
        }
        this.setServerTimeOffset(serverTimeOffset);
    }
    
    private String checkForRelativeURL(final String s) {
        String string = s;
        if (s != null) {
            string = s;
            if (s.startsWith("/")) {
                final StringBuilder sb = new StringBuilder();
                sb.append("https://chess24.com/");
                sb.append(s);
                string = sb.toString();
            }
        }
        return string;
    }
    
    private Tournament createTournamentFromModel(final TournamentModel tournamentModel) {
        final BroadcastTournamentType fromKey = BroadcastTournamentType.fromKey(tournamentModel.getType());
        final AbstractBroadcastTournamentModelMapping tournamentModelConverter = fromKey.getTournamentModelConverter();
        final Tournament mapFromModel = tournamentModelConverter.mapFromModel(tournamentModel);
        mapFromModel.setVideos(tournamentModel.getVideos());
        mapFromModel.setShareLinkTemplate(tournamentModel.getShareLinkTemplate());
        final String logoUrlString = tournamentModel.getLogoUrlString();
        mapFromModel.setPlayerLinkTemplate(tournamentModel.getPlayerImageTemplate());
        if (logoUrlString != null) {
            try {
                mapFromModel.setLogoUrl(new URL(logoUrlString));
            }
            catch (MalformedURLException ex) {
                Logger.getInstance().debug(JSONTournamentParser.class.getName(), MalformedURLException.class.getName(), ex);
            }
        }
        final List<TournamentRoundInfo> rounds = mapFromModel.getRounds();
        final TournamentRoundInfo currentRoundFromModel = tournamentModelConverter.getCurrentRoundFromModel(tournamentModel);
        if (rounds != null && rounds.contains(currentRoundFromModel)) {
            mapFromModel.setCurrentRound(currentRoundFromModel);
        }
        else if (rounds != null && rounds.size() > 0) {
            mapFromModel.setCurrentRound(rounds.get(rounds.size() - 1));
        }
        mapFromModel.setStandingsEnabled(fromKey.showStandings());
        return mapFromModel;
    }
    
    public static long parseClock(final boolean b, final JSONObject jsonObject) {
        final JSONObject optJSONObject = jsonObject.optJSONObject("clock");
        long optLong = -1L;
        if (optJSONObject != null) {
            String s;
            if (b) {
                s = "white";
            }
            else {
                s = "black";
            }
            optLong = optJSONObject.optLong(s, -1L);
        }
        return optLong;
    }
    
    public static long parseClock(final boolean b, final boolean b2, final JSONObject jsonObject, final GameStatus gameStatus, final long n) {
        final JSONObject optJSONObject = jsonObject.optJSONObject("clock");
        final long clock = parseClock(b, jsonObject);
        if (optJSONObject != null) {
            final long optLong = optJSONObject.optLong("now");
            final long currentTimeMillis = System.currentTimeMillis();
            if (GameStatus.GAME_RUNNING.equals(gameStatus) && b2) {
                return clock - (currentTimeMillis - (optLong - n));
            }
        }
        return clock;
    }
    
    public static FEN parseCurrentFEN(final JSONObject jsonObject) {
        final String optString = jsonObject.optString("currentFen", "");
        if (optString.isEmpty()) {
            return FEN.STARTING_POSITION;
        }
        return new FEN(optString);
    }
    
    public static FEN parseEarlierFEN(final JSONObject jsonObject) {
        final String optString = jsonObject.optString("earlierFen", "");
        FEN starting_POSITION = FEN.STARTING_POSITION;
        if (!optString.isEmpty()) {
            starting_POSITION = new FEN(optString);
        }
        return starting_POSITION;
    }
    
    public static EngineEvaluation parseEngineScore(JSONObject optJSONObject) {
        if (optJSONObject.has("engineScore")) {
            return new EngineEvaluation(optJSONObject.optString("engineScore", "0"));
        }
        optJSONObject = optJSONObject.optJSONObject("engine");
        if (optJSONObject == null) {
            return new EngineEvaluation(0.0f);
        }
        if (!optJSONObject.has("mate")) {
            return new EngineEvaluation(optJSONObject.optInt("score", 0) / 100.0f);
        }
        final int optInt = optJSONObject.optInt("mate");
        if (optInt >= 0) {
            return new EngineEvaluation(optInt, true);
        }
        return new EngineEvaluation(optInt, false);
    }
    
    private GameModel parseGame(final JSONObject jsonObject) throws JSONException {
        final String optString = jsonObject.optString("fullGameRKey");
        final JSONObject optJSONObject = jsonObject.optJSONObject("player");
        final String optString2 = optJSONObject.optString("white");
        final String optString3 = optJSONObject.optString("black");
        final FEN currentFEN = parseCurrentFEN(jsonObject);
        final String[] lastMovesString = parseLastMovesString(jsonObject);
        final FEN earlierFEN = parseEarlierFEN(jsonObject);
        final BroadcastGameStatus gameStatus = parseGameStatus(jsonObject);
        boolean activeColorGuess;
        boolean b;
        if (gameStatus != null && gameStatus.isOngoing() && currentFEN != null) {
            activeColorGuess = currentFEN.getActiveColorGuess();
            b = (activeColorGuess ^ true);
        }
        else {
            activeColorGuess = (b = false);
        }
        final long clock = parseClock(true, activeColorGuess, jsonObject, gameStatus, this.getServerTimeOffset());
        final long clock2 = parseClock(false, b, jsonObject, gameStatus, this.getServerTimeOffset());
        final EngineEvaluation engineScore = parseEngineScore(jsonObject);
        final GameModel gameModel = new GameModel(optString, optString2, optString3);
        gameModel.setCurrentFen(currentFEN);
        gameModel.setLastMovesEANAndEarlierFEN(lastMovesString, earlierFEN);
        gameModel.setStatus(gameStatus);
        gameModel.setRemainingTimeWhite(clock);
        gameModel.setRemainingTimeBlack(clock2);
        gameModel.setEngineEvaluation(engineScore);
        return gameModel;
    }
    
    private static GameResult parseGameResult(final JSONObject jsonObject) {
        final double optDouble = jsonObject.optDouble("white", -1.0);
        if (optDouble < 0.0) {
            return GameResult.NO_RESULT;
        }
        final int compare = Double.compare(optDouble, 0.5);
        boolean b = false;
        final boolean b2 = compare > 0;
        if (Double.compare(optDouble, 0.5) == 0) {
            b = true;
        }
        if (b2) {
            return GameResult.WHITE_WINS;
        }
        if (b) {
            return GameResult.DRAW;
        }
        return GameResult.BLACK_WINS;
    }
    
    public static BroadcastGameStatus parseGameStatus(final JSONObject jsonObject) {
        final JSONObject optJSONObject = jsonObject.optJSONObject("gameStatus");
        if (optJSONObject != null) {
            final String optString = optJSONObject.optString("status", "");
            TournamentState tournamentState = TournamentState.ONGOING;
            if (optString.equals("upcoming")) {
                tournamentState = TournamentState.UPCOMING;
            }
            else if (optString.equals("finished")) {
                tournamentState = TournamentState.FINISHED;
            }
            GameResult gameResult = GameResult.NO_RESULT;
            GameEndReason gameEndReason = GameEndReason.NO_REASON;
            final JSONObject optJSONObject2 = optJSONObject.optJSONObject("result");
            if (optJSONObject2 != null) {
                gameResult = parseGameResult(optJSONObject2);
                gameEndReason = GameEndReason.getReasonForKey(optJSONObject.optString("endedBy", ""));
            }
            return new BroadcastGameStatus(gameResult, gameEndReason, tournamentState);
        }
        return new BroadcastGameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON, TournamentState.ONGOING);
    }
    
    private List<GameModel> parseGames(final JSONObject jsonObject) throws JSONException {
        final LinkedList<GameModel> list = new LinkedList<GameModel>();
        final Iterator keys = jsonObject.keys();
        Object game;
        int int1;
        Logger instance;
        StringBuilder sb;
        Label_0094_Outer:Label_0050_Outer:
        while (true) {
            if (keys.hasNext()) {
                game = keys.next();
                int1 = 0;
                while (true) {
                    try {
                        int1 = Integer.parseInt((String)game);
                        while (true) {
                            game = this.parseGame(jsonObject.getJSONObject((String)game));
                            ((GameModel)game).setGameNumber(int1);
                            list.add((GameModel)game);
                            continue Label_0094_Outer;
                            instance = Logger.getInstance();
                            sb = new StringBuilder();
                            sb.append("error parsing game:");
                            sb.append((String)game);
                            instance.error("JSONTOurnamentParser", sb.toString());
                            continue Label_0050_Outer;
                        }
                    }
                    catch (NumberFormatException ex) {}
                    continue;
                }
            }
            return list;
        }
    }
    
    public static String[] parseLastMovesString(final JSONObject jsonObject) {
        final JSONArray optJSONArray = jsonObject.optJSONArray("lastMoves");
        if (optJSONArray != null) {
            final String[] array = new String[optJSONArray.length()];
            for (int i = 0; i < optJSONArray.length(); ++i) {
                array[i] = optJSONArray.optString(i, "");
            }
            return array;
        }
        return null;
    }
    
    private MatchModel parseMatch(final JSONObject jsonObject) throws JSONException {
        final MatchModel matchModel = new MatchModel(this.parseGames(jsonObject.getJSONObject("games")));
        final JSONObject optJSONObject = jsonObject.optJSONObject("teams");
        if (optJSONObject != null) {
            final String optString = optJSONObject.optString("1", (String)null);
            final String optString2 = optJSONObject.optString("2", (String)null);
            matchModel.setTeam1Key(optString);
            matchModel.setTeam2Key(optString2);
        }
        return matchModel;
    }
    
    private List<MatchModel> parseMatches(final JSONObject jsonObject) throws JSONException {
        final LinkedList<MatchModel> list = new LinkedList<MatchModel>();
        final Iterator keys = jsonObject.keys();
        Object match;
        JSONObject jsonObject2;
        int int1;
        Logger instance;
        StringBuilder sb;
        Label_0102_Outer:Label_0058_Outer:
        while (true) {
            if (keys.hasNext()) {
                match = keys.next();
                jsonObject2 = jsonObject.getJSONObject((String)match);
                int1 = 0;
                while (true) {
                    try {
                        int1 = Integer.parseInt((String)match);
                        while (true) {
                            match = this.parseMatch(jsonObject2);
                            ((MatchModel)match).setMatchNumber(int1);
                            list.add((MatchModel)match);
                            continue Label_0102_Outer;
                            instance = Logger.getInstance();
                            sb = new StringBuilder();
                            sb.append("error parsing matchkey:");
                            sb.append((String)match);
                            instance.error("JSONTOurnamentParser", sb.toString());
                            continue Label_0058_Outer;
                        }
                    }
                    catch (NumberFormatException ex) {}
                    continue;
                }
            }
            return list;
        }
    }
    
    private SparseArray<List<Integer>> parseMatchsGroups(final JSONObject jsonObject) {
        final SparseArray sparseArray = new SparseArray();
        final Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            final String s = keys.next();
            final JSONArray optJSONArray = jsonObject.optJSONArray(s);
            if (optJSONArray != null) {
                final LinkedList<Integer> list = new LinkedList<Integer>();
                for (int i = 0; i < optJSONArray.length(); ++i) {
                    list.add(optJSONArray.optInt(i));
                }
                sparseArray.append(Integer.parseInt(s), (Object)list);
            }
        }
        return (SparseArray<List<Integer>>)sparseArray;
    }
    
    private TournamentPlayer parsePlayer(JSONObject optJSONObject) {
        final String optString = optJSONObject.optString("name");
        final int optInt = optJSONObject.optInt("fideId");
        final int optInt2 = optJSONObject.optInt("elo");
        final String optString2 = optJSONObject.optString("country");
        final String optString3 = optJSONObject.optString("title", "");
        final CountryCode byCode = CountryCode.getByCode(optString2);
        final TournamentPlayer tournamentPlayer = new TournamentPlayer(optString, optInt);
        tournamentPlayer.setTitle(optString3);
        tournamentPlayer.setCountry(byCode);
        tournamentPlayer.setElo(optInt2);
        optJSONObject = optJSONObject.optJSONObject("results");
        if (optJSONObject != null) {
            final JSONPlayerResultParser jsonPlayerResultParser = new JSONPlayerResultParser("live");
            PlayerStanding standing = null;
            Label_0158: {
                try {
                    standing = jsonPlayerResultParser.parseResult(optJSONObject);
                    break Label_0158;
                }
                catch (JSONException ex) {
                    Logger.getInstance().debug(JSONTournamentParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
                }
                catch (InvalidJsonForObjectException ex2) {
                    Logger.getInstance().debug(JSONTournamentParser.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
                }
                standing = null;
            }
            tournamentPlayer.setStanding(standing);
        }
        return tournamentPlayer;
    }
    
    private Map<String, TournamentPlayer> parsePlayers(final JSONObject jsonObject) {
        final HashMap<String, TournamentPlayer> hashMap = new HashMap<String, TournamentPlayer>(jsonObject.length());
        final Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            final String s = keys.next();
            hashMap.put(s, this.parsePlayer(jsonObject.optJSONObject(s)));
        }
        return hashMap;
    }
    
    private RoundModel parseRound(final JSONObject jsonObject) throws JSONException {
        return new RoundModel(jsonObject.optString("name"), jsonObject.optLong("timestamp"), this.parseTimeControls(jsonObject.getJSONObject("timeControl")), this.parseMatches(jsonObject.getJSONObject("matches")));
    }
    
    private List<RoundModel> parseRounds(final JSONObject jsonObject) throws JSONException {
        final LinkedList<RoundModel> list = new LinkedList<RoundModel>();
        final Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            final String s = keys.next();
            final int int1 = Integer.parseInt(s);
            final RoundModel round = this.parseRound(jsonObject.getJSONObject(s));
            round.setRoundNumber(int1);
            list.add(round);
        }
        return list;
    }
    
    private TimeControl parseTimeControl(final JSONObject jsonObject) {
        return new TimeControl(jsonObject.optInt("base", 0) / 60000, jsonObject.optInt("incrementPerMove", 0) / 1000);
    }
    
    private SparseArray<TimeControl> parseTimeControls(final JSONObject jsonObject) {
        final SparseArray sparseArray = new SparseArray(jsonObject.length());
        final Iterator keys = jsonObject.keys();
        if (jsonObject.has("base")) {
            sparseArray.put(1, (Object)this.parseTimeControl(jsonObject));
            return (SparseArray<TimeControl>)sparseArray;
        }
        while (keys.hasNext()) {
            final String s = keys.next();
            try {
                sparseArray.put(Integer.parseInt(s), (Object)this.parseTimeControl(jsonObject.optJSONObject(s)));
            }
            catch (NumberFormatException ex) {
                Logger.getInstance().debug(this.getClass().getName(), "Exception parsing timecontrols", ex);
            }
        }
        return (SparseArray<TimeControl>)sparseArray;
    }
    
    private TournamentModel parseTournamentModel(final JSONObject jsonObject) throws JSONException {
        final TournamentModel tournamentModel = new TournamentModel();
        tournamentModel.setRounds(this.parseRounds(jsonObject.getJSONObject("rounds")));
        final JSONObject optJSONObject = jsonObject.optJSONObject("matchGroups");
        SparseArray matchsGroups = new SparseArray();
        if (optJSONObject != null) {
            matchsGroups = this.parseMatchsGroups(optJSONObject);
        }
        tournamentModel.setMatchGroups((SparseArray<List<Integer>>)matchsGroups);
        final Map<String, TournamentPlayer> players = this.parsePlayers(jsonObject.getJSONObject("players"));
        tournamentModel.setPlayers(players);
        tournamentModel.setCurrentRound(jsonObject.optInt("currentRound"));
        final JSONObject optJSONObject2 = jsonObject.optJSONObject("teams");
        if (optJSONObject2 != null) {
            try {
                tournamentModel.setTeams(new JSONTournamentTeamsParser(players, "live").parseResult(optJSONObject2));
            }
            catch (InvalidJsonForObjectException ex) {
                Logger.getInstance().debug(JSONTournamentParser.class.getName(), InvalidJsonForObjectException.class.getName(), ex);
            }
        }
        final JSONObject optJSONObject3 = jsonObject.optJSONObject("standings");
        if (optJSONObject3 != null) {
            final JSONTournamentStandingsParser jsonTournamentStandingsParser = new JSONTournamentStandingsParser("live");
            try {
                tournamentModel.setStandings(jsonTournamentStandingsParser.parseResult(optJSONObject3));
            }
            catch (InvalidJsonForObjectException ex2) {
                final Logger instance = Logger.getInstance();
                final String name = JSONTournamentParser.class.getName();
                final StringBuilder sb = new StringBuilder();
                sb.append("Invalid Standings JSON: ");
                sb.append(optJSONObject3);
                instance.debug(name, sb.toString(), ex2);
            }
        }
        final String optString = jsonObject.optString("rkey");
        final JSONObject optJSONObject4 = jsonObject.optJSONObject("descriptions");
        final JSONObject optJSONObject5 = jsonObject.optJSONObject("titles");
        final String s = null;
        String optString2;
        if (optJSONObject5 != null) {
            optString2 = optJSONObject5.optString(this._language, (String)null);
        }
        else {
            optString2 = null;
        }
        String optString3 = optString2;
        if (optString2 == null) {
            optString3 = jsonObject.optString("title");
        }
        String optString4;
        if (optJSONObject4 != null && this._language != null) {
            optString4 = optJSONObject4.optString(this._language, (String)null);
        }
        else {
            optString4 = null;
        }
        String optString5 = optString4;
        if (optString4 == null) {
            optString5 = jsonObject.optString("description");
        }
        String strip;
        if ((strip = optString5) != null) {
            strip = HTMLStripper.strip(optString5);
        }
        final int optInt = jsonObject.optInt("gamesPerMatch", 4);
        final String checkForRelativeURL = this.checkForRelativeURL(jsonObject.optString("playerImageTemplate", "https://chess24.com/images/player/{{fideId}}.jpg"));
        String optString6 = jsonObject.optString("logo");
        final String checkForRelativeURL2 = this.checkForRelativeURL(jsonObject.optString("gameLinkTemplate"));
        tournamentModel.setRKey(optString);
        tournamentModel.setTitle(optString3);
        tournamentModel.setDescription(strip);
        tournamentModel.setGamesPerMatch(optInt);
        tournamentModel.setPlayerLinkTemplate(checkForRelativeURL);
        tournamentModel.setShareLinkTemplate(checkForRelativeURL2);
        if ("false".equals(optString6)) {
            optString6 = s;
        }
        tournamentModel.setLogoUrlString(this.checkForRelativeURL(optString6));
        tournamentModel.setVideos(this.parseVideos(jsonObject));
        jsonObject.optString("commentaryLanguage");
        tournamentModel.setType(jsonObject.optString("eventType"));
        tournamentModel.setCurrentGame(jsonObject.optInt("currentGame", 1));
        return tournamentModel;
    }
    
    private List<TournamentVideoInformation> parseVideos(final JSONObject jsonObject) {
        final LinkedList<TournamentVideoInformation> list = new LinkedList<TournamentVideoInformation>();
        final JSONArray optJSONArray = jsonObject.optJSONArray("video");
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); ++i) {
                final JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    final String optString = optJSONObject.optString("language", "GB");
                    final String optString2 = optJSONObject.optString("rtsp", (String)null);
                    if (optString2 != null) {
                        final Uri parse = Uri.parse(optString2);
                        CountryCode countryCode;
                        if ((countryCode = CountryCode.getByCode(optString)) == null) {
                            countryCode = CountryCode.GB;
                        }
                        list.add(new TournamentVideoInformation(countryCode, parse));
                    }
                }
            }
        }
        return list;
    }
    
    public long getServerTimeOffset() {
        return this._serverTimeOffset;
    }
    
    @Override
    public Tournament parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return this.createTournamentFromModel(this.parseTournamentModel(jsonObject));
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(JSONTournamentParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("Error while parsing Tournament", (Throwable)ex);
        }
    }
    
    public void setServerTimeOffset(final long serverTimeOffset) {
        this._serverTimeOffset = serverTimeOffset;
    }
}
