// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import java.util.Map;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.NullMove;
import de.cisha.chess.model.MoveContainer;
import android.util.SparseArray;
import java.util.HashMap;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.FEN;
import org.json.JSONArray;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.android.board.model.BoardMarks;
import org.json.JSONObject;

public class JSONVideoGameParser extends JSONAPIResultParser<GameAndHighlights>
{
    private static final String MOVES_KEY = "moves";
    private static final String MOVE_EAN_KEY = "m";
    private static final String MOVE_ID_KEY = "id";
    private static final String MOVE_PARENT_KEY = "pm";
    private static final String STARTING_POSITION_FEN_KEY = "start_position_fen";
    
    public BoardMarks parseHighlights(final JSONObject jsonObject) {
        final JSONArray optJSONArray = jsonObject.optJSONArray("highlights");
        BoardMarks boardMarks2;
        if (optJSONArray != null) {
            final BoardMarks boardMarks = new BoardMarks();
            int n = 0;
            while (true) {
                boardMarks2 = boardMarks;
                if (n >= optJSONArray.length()) {
                    break;
                }
                try {
                    final JSONObject jsonObject2 = optJSONArray.getJSONObject(n);
                    final String string = jsonObject2.getString("type");
                    final int colorForString = JSONVideoCommandParser.getColorForString(jsonObject2.getString("color"));
                    if ("square".equals(string)) {
                        boardMarks.getSquareMarkings().addSquareMark(Square.instanceForRowAndColumn(jsonObject2.getInt("y"), jsonObject2.getInt("x")), colorForString);
                    }
                    else if ("arrow".equals(string)) {
                        final String string2 = jsonObject2.getString("from");
                        final String string3 = jsonObject2.getString("to");
                        final StringBuilder sb = new StringBuilder();
                        sb.append(string2);
                        sb.append(string3);
                        boardMarks.getArrowContainer().addArrow(new Arrow(new SEP(sb.toString()), ArrowCategory.USER_MARKS, colorForString));
                    }
                }
                catch (JSONException ex) {
                    Logger.getInstance().error(JSONVideoGameParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
                }
                ++n;
            }
        }
        else {
            boardMarks2 = null;
        }
        return boardMarks2;
    }
    
    @Override
    public GameAndHighlights parseResult(JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final String optString = jsonObject.optString("start_position_fen", FEN.STARTING_POSITION.getFENString());
        final FEN fen = new FEN(optString);
        if (!fen.isValid()) {
            final StringBuilder sb = new StringBuilder();
            sb.append("Starting fen is invalid:");
            sb.append(optString);
            throw new InvalidJsonForObjectException(sb.toString());
        }
        final Game game = new Game();
        game.setStartingPosition(new Position(fen));
        final JSONArray optJSONArray = jsonObject.optJSONArray("moves");
        final HashMap<Game, BoardMarks> highlights = new HashMap<Game, BoardMarks>();
        if (optJSONArray != null) {
            final SparseArray sparseArray = new SparseArray(100);
            if (optJSONArray.length() > 0) {
                jsonObject = optJSONArray.getJSONObject(0);
                sparseArray.put(jsonObject.optInt("id", 0), (Object)game);
                final BoardMarks highlights2 = this.parseHighlights(jsonObject);
                if (highlights2 != null) {
                    highlights.put(game, highlights2);
                }
            }
            for (int i = 1; i < optJSONArray.length(); ++i) {
                final JSONObject jsonObject2 = optJSONArray.getJSONObject(i);
                try {
                    final int int1 = jsonObject2.getInt("id");
                    final int int2 = jsonObject2.getInt("pm");
                    final String string = jsonObject2.getString("m");
                    final MoveContainer moveContainer = (MoveContainer)sparseArray.get(int2);
                    if (moveContainer == null) {
                        final Logger instance = Logger.getInstance();
                        final String name = this.getClass().getName();
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append("incorrect move - parent is missing:");
                        sb2.append(jsonObject2);
                        instance.error(name, sb2.toString());
                    }
                    else {
                        final SEP sep = new SEP(string);
                        Move move;
                        if (sep.isNullMove()) {
                            move = new NullMove(moveContainer.getResultingPosition());
                        }
                        else {
                            move = moveContainer.getResultingPosition().createMoveFrom(sep);
                        }
                        if (move == null) {
                            final Logger instance2 = Logger.getInstance();
                            final String name2 = this.getClass().getName();
                            final StringBuilder sb3 = new StringBuilder();
                            sb3.append("can't create move");
                            sb3.append(jsonObject2);
                            instance2.error(name2, sb3.toString());
                        }
                        else {
                            move.setMoveId(int1);
                            moveContainer.addMove(move);
                            sparseArray.put(int1, (Object)move);
                            final BoardMarks highlights3 = this.parseHighlights(jsonObject2);
                            if (highlights3 != null) {
                                highlights.put((Game)move, highlights3);
                            }
                        }
                    }
                }
                catch (JSONException ex) {
                    final Logger instance3 = Logger.getInstance();
                    final String name3 = this.getClass().getName();
                    final StringBuilder sb4 = new StringBuilder();
                    sb4.append("incorrect move - field is missing:");
                    sb4.append(jsonObject2);
                    instance3.error(name3, sb4.toString(), (Throwable)ex);
                }
            }
        }
        final GameAndHighlights gameAndHighlights = new GameAndHighlights();
        gameAndHighlights.game = game;
        gameAndHighlights.highlights = (Map<MoveContainer, BoardMarks>)highlights;
        return gameAndHighlights;
    }
}
