/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonmapping;

import de.cisha.android.board.service.jsonmapping.JSONAPIMapping;
import de.cisha.android.board.service.jsonmapping.JSONMappingOpponent;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.position.Position;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMappingGame
implements JSONAPIMapping<Game> {
    private static JSONMappingOpponent _mappingOpponent = new JSONMappingOpponent();
    private int _id;
    private boolean _mapFENs = true;

    private JSONObject createClock(ClockSetting clockSetting, boolean bl) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("base", clockSetting.getBase(bl));
        jSONObject.put("incrementPerMove", clockSetting.getIncrementPerMove(bl));
        return jSONObject;
    }

    private JSONObject createInitialClocks(ClockSetting clockSetting) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("white", (Object)this.createClock(clockSetting, true));
        jSONObject.put("black", (Object)this.createClock(clockSetting, false));
        return jSONObject;
    }

    private JSONObject createMeta(Game game) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("Event", (Object)game.getEvent());
        jSONObject.put("Site", (Object)game.getSite());
        jSONObject.put("Comment", (Object)game.getComment());
        jSONObject.put("Board", game.getBoard());
        jSONObject.put("Title", (Object)game.getTitle());
        if (game.getType() != null) {
            jSONObject.put("Type", game.getType().getNumericRepresentation());
        }
        if (game.getOriginalGameType() != null) {
            jSONObject.put("OriginalGameType", game.getOriginalGameType().getNumericRepresentation());
        }
        String string = "*";
        GameResult gameResult = game.getResult().getResult();
        if (gameResult != null) {
            string = gameResult.getShortDescription();
        }
        if (gameResult == GameResult.WHITE_WINS) {
            string = "1-0";
        } else if (gameResult == GameResult.BLACK_WINS) {
            string = "0-1";
        } else if (gameResult == GameResult.DRAW) {
            string = "0.5-0.5";
        }
        jSONObject.put("Result", (Object)string);
        jSONObject.put("Date", (Object)new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(game.getStartDate()));
        jSONObject.put("Time", (Object)new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(game.getStartDate()));
        jSONObject.put("Round", game.getRound());
        jSONObject.put("White", (Object)this.createOpponent(game.getWhitePlayer()));
        jSONObject.put("Black", (Object)this.createOpponent(game.getBlackPlayer()));
        return jSONObject;
    }

    private void createMove(Move move, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONArray.put(this._id, (Object)jSONObject);
        jSONObject.put("move", (Object)move.getEAN());
        jSONObject.put("timeUsage", move.getMoveTimeInMills());
        if (this._mapFENs) {
            jSONObject.put("fen", (Object)move.getResultingPosition().getFEN().getFENString());
        }
        jSONObject.put("ium", move.isUserMove());
        Object object = move.getChildren();
        move = new JSONArray();
        object = object.iterator();
        while (object.hasNext()) {
            int n;
            Move move2 = (Move)object.next();
            this._id = n = this._id + 1;
            move.put(n);
            this.createMove(move2, jSONArray);
        }
        jSONObject.put("children", (Object)move);
    }

    private JSONArray createMoves(Game object) throws JSONException {
        this._id = 0;
        JSONArray jSONArray = new JSONArray();
        JSONObject object22 = new JSONObject();
        object22.put("move", (Object)"start");
        object22.put("timeUsage", object.getMoveTimeInMills());
        object22.put("fen", (Object)object.getStartingPosition().getFEN().getFENString());
        JSONArray jSONArray2 = new JSONArray();
        object22.put("children", (Object)jSONArray2);
        jSONArray.put(this._id, (Object)object22);
        for (Move move : object.getChildren()) {
            int n;
            this._id = n = this._id + 1;
            jSONArray2.put(n);
            this.createMove(move, jSONArray);
        }
        return jSONArray;
    }

    private JSONObject createOpponent(Opponent opponent) throws JSONException {
        return _mappingOpponent.mapToJSON(opponent);
    }

    @Override
    public JSONObject mapToJSON(Game game) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("meta", (Object)this.createMeta(game));
        jSONObject.put("moves", (Object)this.createMoves(game));
        jSONObject.put("timeGoneSinceLastMove", game.getClockSetting().getTimeGoneSinceLastMove());
        jSONObject.put("initialClocks", (Object)this.createInitialClocks(game.getClockSetting()));
        return jSONObject;
    }

    public void setMapFens(boolean bl) {
        this._mapFENs = bl;
    }
}
