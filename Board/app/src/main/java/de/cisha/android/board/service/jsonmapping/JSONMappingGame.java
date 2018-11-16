// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonmapping;

import de.cisha.chess.model.Opponent;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import de.cisha.chess.model.Move;
import java.text.SimpleDateFormat;
import java.util.Locale;
import de.cisha.chess.model.GameResult;
import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;

public class JSONMappingGame implements JSONAPIMapping<Game>
{
    private static JSONMappingOpponent _mappingOpponent;
    private int _id;
    private boolean _mapFENs;
    
    static {
        JSONMappingGame._mappingOpponent = new JSONMappingOpponent();
    }
    
    public JSONMappingGame() {
        this._mapFENs = true;
    }
    
    private JSONObject createClock(final ClockSetting clockSetting, final boolean b) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("base", clockSetting.getBase(b));
        jsonObject.put("incrementPerMove", clockSetting.getIncrementPerMove(b));
        return jsonObject;
    }
    
    private JSONObject createInitialClocks(final ClockSetting clockSetting) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("white", (Object)this.createClock(clockSetting, true));
        jsonObject.put("black", (Object)this.createClock(clockSetting, false));
        return jsonObject;
    }
    
    private JSONObject createMeta(final Game game) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Event", (Object)game.getEvent());
        jsonObject.put("Site", (Object)game.getSite());
        jsonObject.put("Comment", (Object)game.getComment());
        jsonObject.put("Board", game.getBoard());
        jsonObject.put("Title", (Object)game.getTitle());
        if (game.getType() != null) {
            jsonObject.put("Type", game.getType().getNumericRepresentation());
        }
        if (game.getOriginalGameType() != null) {
            jsonObject.put("OriginalGameType", game.getOriginalGameType().getNumericRepresentation());
        }
        String shortDescription = "*";
        final GameResult result = game.getResult().getResult();
        if (result != null) {
            shortDescription = result.getShortDescription();
        }
        if (result == GameResult.WHITE_WINS) {
            shortDescription = "1-0";
        }
        else if (result == GameResult.BLACK_WINS) {
            shortDescription = "0-1";
        }
        else if (result == GameResult.DRAW) {
            shortDescription = "0.5-0.5";
        }
        jsonObject.put("Result", (Object)shortDescription);
        jsonObject.put("Date", (Object)new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(game.getStartDate()));
        jsonObject.put("Time", (Object)new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(game.getStartDate()));
        jsonObject.put("Round", game.getRound());
        jsonObject.put("White", (Object)this.createOpponent(game.getWhitePlayer()));
        jsonObject.put("Black", (Object)this.createOpponent(game.getBlackPlayer()));
        return jsonObject;
    }
    
    private void createMove(final Move move, final JSONArray jsonArray) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonArray.put(this._id, (Object)jsonObject);
        jsonObject.put("move", (Object)move.getEAN());
        jsonObject.put("timeUsage", move.getMoveTimeInMills());
        if (this._mapFENs) {
            jsonObject.put("fen", (Object)move.getResultingPosition().getFEN().getFENString());
        }
        jsonObject.put("ium", move.isUserMove());
        final List<Move> children = move.getChildren();
        final JSONArray jsonArray2 = new JSONArray();
        for (final Move move2 : children) {
            jsonArray2.put(++this._id);
            this.createMove(move2, jsonArray);
        }
        jsonObject.put("children", (Object)jsonArray2);
    }
    
    private JSONArray createMoves(final Game game) throws JSONException {
        this._id = 0;
        final JSONArray jsonArray = new JSONArray();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("move", (Object)"start");
        jsonObject.put("timeUsage", game.getMoveTimeInMills());
        jsonObject.put("fen", (Object)game.getStartingPosition().getFEN().getFENString());
        final JSONArray jsonArray2 = new JSONArray();
        jsonObject.put("children", (Object)jsonArray2);
        jsonArray.put(this._id, (Object)jsonObject);
        for (final Move move : game.getChildren()) {
            jsonArray2.put(++this._id);
            this.createMove(move, jsonArray);
        }
        return jsonArray;
    }
    
    private JSONObject createOpponent(final Opponent opponent) throws JSONException {
        return JSONMappingGame._mappingOpponent.mapToJSON(opponent);
    }
    
    @Override
    public JSONObject mapToJSON(final Game game) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("meta", (Object)this.createMeta(game));
        jsonObject.put("moves", (Object)this.createMoves(game));
        jsonObject.put("timeGoneSinceLastMove", game.getClockSetting().getTimeGoneSinceLastMove());
        jsonObject.put("initialClocks", (Object)this.createInitialClocks(game.getClockSetting()));
        return jsonObject;
    }
    
    public void setMapFens(final boolean mapFENs) {
        this._mapFENs = mapFENs;
    }
}
