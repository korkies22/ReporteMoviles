// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import de.cisha.chess.model.ClockSetting;
import org.json.JSONArray;
import java.util.LinkedList;
import de.cisha.chess.model.FEN;
import org.json.JSONException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameType;
import org.json.JSONObject;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import java.util.Iterator;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.MoveContainer;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import java.util.List;
import android.util.SparseArray;
import de.cisha.chess.model.Game;

public class JSONGameParser extends JSONAPIResultParser<Game>
{
    public static final String IS_USERMOVE = "ium";
    private long _serverTimeOffset;
    
    public JSONGameParser() {
        this._serverTimeOffset = 0L;
    }
    
    public JSONGameParser(final long serverTimeOffset) {
        this._serverTimeOffset = serverTimeOffset;
    }
    
    private static void buildMoveTreeNonRecursive(final Game game, final SparseArray<List<Integer>> sparseArray, final SparseArray<String> sparseArray2, final SparseIntArray sparseIntArray, final SparseBooleanArray sparseBooleanArray) {
        final SparseIntArray sparseIntArray2 = new SparseIntArray();
        for (int i = 0; i < sparseArray.size(); ++i) {
            final int key = sparseArray.keyAt(i);
            final Iterator iterator = ((List)sparseArray.get(key)).iterator();
            while (iterator.hasNext()) {
                sparseIntArray2.put((int)iterator.next(), key);
            }
        }
        final SparseArray sparseArray3 = new SparseArray();
        for (int j = 0; j < sparseArray2.size(); ++j) {
            final int key2 = sparseArray2.keyAt(j);
            final String s = (String)sparseArray2.valueAt(j);
            final Integer value = sparseIntArray2.get(key2);
            MoveContainer moveContainer;
            if (value != null && value != 0) {
                moveContainer = (MoveContainer)sparseArray3.get((int)value);
            }
            else {
                moveContainer = game;
            }
            if (moveContainer != null) {
                final Position resultingPosition = moveContainer.getResultingPosition();
                final Move moveFromCAN = resultingPosition.createMoveFromCAN(s);
                if (moveFromCAN != null) {
                    moveFromCAN.setMoveId(key2);
                    moveFromCAN.setMoveTimeInMills(sparseIntArray.get(key2));
                    moveFromCAN.setUserGenerated(sparseBooleanArray.get(key2, false));
                    moveContainer.addMove(moveFromCAN);
                    sparseArray3.put(key2, (Object)moveFromCAN);
                }
                else {
                    final Logger instance = Logger.getInstance();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("invalid can: ");
                    sb.append(s);
                    sb.append(" for id: ");
                    sb.append(key2);
                    sb.append("in Poisition ");
                    sb.append(resultingPosition.getFEN().getFENString());
                    instance.error("JSONGameParser", sb.toString());
                }
            }
            else {
                final Logger instance2 = Logger.getInstance();
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("error finding parent for move with id: ");
                sb2.append(key2);
                sb2.append(" can: ");
                sb2.append(s);
                instance2.error("JSONGameParser", sb2.toString());
            }
        }
    }
    
    private GameResult parseGameResult(final String s) {
        return GameResult.fromString(s);
    }
    
    @Override
    public Game parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        while (true) {
            final Game game = new Game();
            while (true) {
                int n2;
                try {
                    final JSONObject jsonObject2 = jsonObject.getJSONObject("meta");
                    game.setEvent(jsonObject2.optString("Event", ""));
                    game.setSite(jsonObject2.optString("Site", ""));
                    game.setComment(jsonObject2.optString("Comment", ""));
                    game.setTitle(jsonObject2.optString("Title", ""));
                    game.setType(GameType.forNumericRepresentation(jsonObject2.optInt("Type", GameType.UNDEFINED.getNumericRepresentation())));
                    game.setOriginalGameType(GameType.forNumericRepresentation(jsonObject2.optInt("OriginalGameType", GameType.UNDEFINED.getNumericRepresentation())));
                    game.setBoard(jsonObject2.optInt("Board", 0));
                    game.setResult(new GameStatus(this.parseGameResult(jsonObject2.optString("Result", "*")), GameEndReason.NO_REASON));
                    final String optString = jsonObject2.optString("Date", "1970.01.01");
                    final String optString2 = jsonObject2.optString("Time", "00:00:00");
                    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
                    try {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(optString);
                        sb.append(" ");
                        sb.append(optString2);
                        game.setStartDate(simpleDateFormat.parse(sb.toString()));
                    }
                    catch (ParseException ex) {
                        Logger.getInstance().debug(JSONGameParser.class.getName(), "wrong format/time", ex);
                    }
                    game.setRound(jsonObject2.optInt("Round", 0));
                    final JSONPlayzoneOpponentParser jsonPlayzoneOpponentParser = new JSONPlayzoneOpponentParser();
                    try {
                        final JSONObject optJSONObject = jsonObject2.optJSONObject("Black");
                        if (optJSONObject != null) {
                            game.setBlackPlayer(jsonPlayzoneOpponentParser.parseResult(optJSONObject));
                        }
                        final JSONObject optJSONObject2 = jsonObject2.optJSONObject("White");
                        if (optJSONObject2 != null) {
                            game.setWhitePlayer(jsonPlayzoneOpponentParser.parseResult(optJSONObject2));
                        }
                    }
                    catch (InvalidJsonForObjectException ex2) {
                        Logger.getInstance().error(JSONGameParser.class.getName(), "wrong Format of Opponent in Game object", ex2);
                    }
                    final JSONArray jsonArray = jsonObject.getJSONArray("moves");
                    final ClockSetting clockSetting = game.getClockSetting();
                    final int optInt = jsonObject.optInt("timeGoneSinceLastMove", -1);
                    if (optInt >= 0) {
                        clockSetting.setTimeGoneSinceLastMove(optInt);
                    }
                    else {
                        final long optLong = jsonObject.optLong("endOfMainlineMoveMadeAt", -1L);
                        if (optLong >= 0L) {
                            final long n = System.currentTimeMillis() + this._serverTimeOffset - optLong;
                            if (n <= 2147483647L) {
                                clockSetting.setTimeGoneSinceLastMove((int)n);
                            }
                        }
                    }
                    final JSONObject optJSONObject3 = jsonObject.optJSONObject("initialClocks");
                    if (optJSONObject3 != null) {
                        try {
                            final JSONObject jsonObject3 = optJSONObject3.getJSONObject("white");
                            clockSetting.setBase(true, jsonObject3.getInt("base"));
                            clockSetting.setIncrementPerMove(true, jsonObject3.optInt("incrementPerMove", 0));
                            final JSONObject jsonObject4 = optJSONObject3.getJSONObject("black");
                            clockSetting.setBase(false, jsonObject4.getInt("base"));
                            clockSetting.setIncrementPerMove(false, jsonObject4.optInt("incrementPerMove", 0));
                        }
                        catch (JSONException ex3) {
                            final Logger instance = Logger.getInstance();
                            final String name = this.getClass().getName();
                            final StringBuilder sb2 = new StringBuilder();
                            sb2.append("Error parsing JSON CLock Object: ");
                            sb2.append(optJSONObject3);
                            instance.error(name, sb2.toString(), (Throwable)ex3);
                        }
                    }
                    final String optString3 = jsonArray.getJSONObject(0).optString("fen", (String)null);
                    FEN starting_POSITION;
                    if (optString3 != null) {
                        starting_POSITION = new FEN(optString3);
                    }
                    else {
                        starting_POSITION = FEN.STARTING_POSITION;
                    }
                    game.setStartingPosition(new Position(starting_POSITION));
                    final SparseArray sparseArray = new SparseArray();
                    final SparseIntArray sparseIntArray = new SparseIntArray();
                    final SparseArray sparseArray2 = new SparseArray();
                    final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                    n2 = 0;
                    if (n2 >= jsonArray.length()) {
                        buildMoveTreeNonRecursive(game, (SparseArray<List<Integer>>)sparseArray2, (SparseArray<String>)sparseArray, sparseIntArray, sparseBooleanArray);
                        return game;
                    }
                    final JSONObject jsonObject5 = jsonArray.getJSONObject(n2);
                    final String optString4 = jsonObject5.optString("move", "start");
                    if (!optString4.equals("start")) {
                        sparseArray.put(n2, (Object)optString4);
                        sparseIntArray.put(n2, jsonObject5.optInt("timeUsage", 0));
                        sparseBooleanArray.append(n2, jsonObject5.optBoolean("ium", false));
                        final JSONArray optJSONArray = jsonObject5.optJSONArray("children");
                        final LinkedList<Integer> list = new LinkedList<Integer>();
                        if (optJSONArray != null) {
                            for (int i = 0; i < optJSONArray.length(); ++i) {
                                list.add(optJSONArray.getInt(i));
                            }
                        }
                        sparseArray2.put(n2, (Object)list);
                    }
                }
                catch (JSONException ex4) {
                    Logger.getInstance().debug(this.getClass().getName(), JSONException.class.getName(), (Throwable)ex4);
                    final StringBuilder sb3 = new StringBuilder();
                    sb3.append("Invalid Json in GameParser:");
                    sb3.append(jsonObject);
                    throw new InvalidJsonForObjectException(sb3.toString(), (Throwable)ex4);
                }
                ++n2;
                continue;
            }
        }
    }
}
