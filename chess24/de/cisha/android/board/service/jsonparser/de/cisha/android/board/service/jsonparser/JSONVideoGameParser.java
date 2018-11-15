/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import android.util.SparseArray;
import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import de.cisha.android.board.model.ArrowContainer;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.model.SquareMarkings;
import de.cisha.android.board.service.jsonparser.GameAndHighlights;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONVideoCommandParser;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.NullMove;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONVideoGameParser
extends JSONAPIResultParser<GameAndHighlights> {
    private static final String MOVES_KEY = "moves";
    private static final String MOVE_EAN_KEY = "m";
    private static final String MOVE_ID_KEY = "id";
    private static final String MOVE_PARENT_KEY = "pm";
    private static final String STARTING_POSITION_FEN_KEY = "start_position_fen";

    public BoardMarks parseHighlights(JSONObject object) {
        block7 : {
            JSONArray jSONArray = object.optJSONArray("highlights");
            if (jSONArray != null) {
                BoardMarks boardMarks = new BoardMarks();
                int n = 0;
                do {
                    object = boardMarks;
                    if (n >= jSONArray.length()) break block7;
                    try {
                        Object object2 = jSONArray.getJSONObject(n);
                        object = object2.getString("type");
                        int n2 = JSONVideoCommandParser.getColorForString(object2.getString("color"));
                        if ("square".equals(object)) {
                            int n3 = object2.getInt("x");
                            object = Square.instanceForRowAndColumn(object2.getInt("y"), n3);
                            boardMarks.getSquareMarkings().addSquareMark((Square)object, n2);
                        } else if ("arrow".equals(object)) {
                            object = object2.getString("from");
                            object2 = object2.getString("to");
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append((String)object);
                            stringBuilder.append((String)object2);
                            object = new Arrow(new SEP(stringBuilder.toString()), ArrowCategory.USER_MARKS, n2);
                            boardMarks.getArrowContainer().addArrow((Arrow)object);
                        }
                    }
                    catch (JSONException jSONException) {
                        Logger.getInstance().error(JSONVideoGameParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                    }
                    ++n;
                } while (true);
            }
            object = null;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public GameAndHighlights parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        Object object2 = object.optString(STARTING_POSITION_FEN_KEY, FEN.STARTING_POSITION.getFENString());
        Serializable serializable = new FEN((String)object2);
        if (!serializable.isValid()) {
            object = new StringBuilder();
            object.append("Starting fen is invalid:");
            object.append((String)object2);
            throw new InvalidJsonForObjectException(object.toString());
        }
        object2 = new Game();
        object2.setStartingPosition(new Position((FEN)serializable));
        JSONArray jSONArray = object.optJSONArray(MOVES_KEY);
        serializable = new HashMap();
        if (jSONArray != null) {
            SparseArray sparseArray = new SparseArray(100);
            if (jSONArray.length() > 0) {
                object = jSONArray.getJSONObject(0);
                sparseArray.put(object.optInt(MOVE_ID_KEY, 0), object2);
                object = this.parseHighlights((JSONObject)object);
                if (object != null) {
                    serializable.put(object2, object);
                }
            }
            for (int i = 1; i < jSONArray.length(); ++i) {
                Object object3;
                CharSequence charSequence;
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                try {
                    int n = jSONObject.getInt(MOVE_ID_KEY);
                    int n2 = jSONObject.getInt(MOVE_PARENT_KEY);
                    object = jSONObject.getString(MOVE_EAN_KEY);
                    object3 = (MoveContainer)sparseArray.get(n2);
                    if (object3 == null) {
                        object = Logger.getInstance();
                        object3 = this.getClass().getName();
                        charSequence = new StringBuilder();
                        charSequence.append("incorrect move - parent is missing:");
                        charSequence.append((Object)jSONObject);
                        object.error((String)object3, charSequence.toString());
                        continue;
                    }
                    object = (object = new SEP((String)object)).isNullMove() ? new NullMove(object3.getResultingPosition()) : object3.getResultingPosition().createMoveFrom((SEP)object);
                    if (object == null) {
                        object = Logger.getInstance();
                        object3 = this.getClass().getName();
                        charSequence = new StringBuilder();
                        charSequence.append("can't create move");
                        charSequence.append((Object)jSONObject);
                        object.error((String)object3, charSequence.toString());
                        continue;
                    }
                    object.setMoveId(n);
                    object3.addMove((Move)object);
                    sparseArray.put(n, object);
                    object3 = this.parseHighlights(jSONObject);
                    if (object3 == null) continue;
                    serializable.put(object, object3);
                    continue;
                }
                catch (JSONException jSONException) {
                    object3 = Logger.getInstance();
                    charSequence = this.getClass().getName();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("incorrect move - field is missing:");
                    stringBuilder.append((Object)jSONObject);
                    object3.error((String)charSequence, stringBuilder.toString(), (Throwable)jSONException);
                }
            }
        }
        object = new GameAndHighlights();
        object.game = object2;
        object.highlights = serializable;
        return object;
    }
}
