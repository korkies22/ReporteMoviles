/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.command.ArrowCommand;
import de.cisha.android.board.video.command.EmptyCommand;
import de.cisha.android.board.video.command.GotoCommand;
import de.cisha.android.board.video.command.MarkSquareCommand;
import de.cisha.android.board.video.command.MoveCommand;
import de.cisha.android.board.video.command.SelectGameCommand;
import de.cisha.android.board.video.command.UnArrowAllCommand;
import de.cisha.android.board.video.command.UnArrowCommand;
import de.cisha.android.board.video.command.UnMarkAllCommand;
import de.cisha.android.board.video.command.UnMarkSquareCommand;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONVideoCommandParser
extends JSONAPIResultParser<VideoCommand> {
    private static final String ARROW_COLOR_KEY = "color";
    private static final String COMMAND_TYPE_KEY = "n";
    private static final String DATA_TYPE_KEY = "d";
    private static final String EAN_KEY = "lineAsCan";
    private static final String GAME_ID_KEY = "gameIndex";
    private static final String INITIAL_MOVE_ID_KEY = "initialMoveId";
    private static final String MOVE_ID_KEY = "id";
    private static final String SQUARE_X_KEY = "x";
    private static final String SQUARE_Y_KEY = "y";
    private static final String TIME_KEY = "t";

    public static int getColorForString(String string) {
        if ("green".equalsIgnoreCase(string)) {
            return -16711936;
        }
        if ("red".equalsIgnoreCase(string)) {
            return -65536;
        }
        if ("blue".equalsIgnoreCase(string)) {
            return -16776961;
        }
        if ("yellow".equalsIgnoreCase(string)) {
            return -256;
        }
        Logger logger = Logger.getInstance();
        String string2 = JSONVideoCommandParser.class.getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error: unknown color: ");
        stringBuilder.append(string);
        logger.error(string2, stringBuilder.toString());
        return -65281;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public VideoCommand parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        Object object2;
        long l = (long)(object.getDouble(TIME_KEY) * 1000.0);
        String string = object.getString(COMMAND_TYPE_KEY);
        object = object2 = object.optJSONObject(DATA_TYPE_KEY);
        if (object2 == null) {
            object = new JSONObject();
        }
        if ("gotoId".equals(string)) {
            return new GotoCommand(object.getInt(MOVE_ID_KEY), l);
        }
        if ("selectGame".equals(string)) {
            return new SelectGameCommand(object.getInt(GAME_ID_KEY), object.optInt(INITIAL_MOVE_ID_KEY, 0), l);
        }
        if ("drawArrow".equals(string)) {
            int n = JSONVideoCommandParser.getColorForString(object.getString(ARROW_COLOR_KEY));
            object2 = new SEP((String)(object = object.getString(EAN_KEY)));
            if (object2.isValid()) {
                return new ArrowCommand((SEP)object2, n, l);
            }
            object2 = new StringBuilder();
            object2.append("Invalid EAN given in arrow: ");
            object2.append((String)object);
            throw new InvalidJsonForObjectException(object2.toString());
        }
        if ("unarrow".equals(string)) {
            object2 = new SEP((String)(object = object.getString(EAN_KEY)));
            if (object2.isValid()) {
                return new UnArrowCommand((SEP)object2, l);
            }
            object2 = new StringBuilder();
            object2.append("Invalid EAN given in arrow: ");
            object2.append((String)object);
            throw new InvalidJsonForObjectException(object2.toString());
        }
        if ("unarrowAll".equals(string)) {
            return new UnArrowAllCommand(l);
        }
        if ("highlightSquare".equals(string)) {
            int n = JSONVideoCommandParser.getColorForString(object.getString(ARROW_COLOR_KEY));
            int n2 = object.getInt(SQUARE_X_KEY);
            int n3 = object.getInt(SQUARE_Y_KEY);
            object = Square.instanceForRowAndColumn(n3, n2);
            if (object.isValid()) {
                return new MarkSquareCommand((Square)object, n, l);
            }
            object = new StringBuilder();
            object.append("Invalid square given in x ");
            object.append(n2);
            object.append(" y: ");
            object.append(n3);
            throw new InvalidJsonForObjectException(object.toString());
        }
        if ("unmark".equals(string)) {
            int n = object.getInt(SQUARE_X_KEY);
            int n4 = object.getInt(SQUARE_Y_KEY);
            object = Square.instanceForRowAndColumn(n4, n);
            if (object.isValid()) {
                return new UnMarkSquareCommand((Square)object, l);
            }
            object = new StringBuilder();
            object.append("Invalid square given in x ");
            object.append(n);
            object.append(" y: ");
            object.append(n4);
            throw new InvalidJsonForObjectException(object.toString());
        }
        if ("unmarkAll".equals(string)) {
            return new UnMarkAllCommand(l);
        }
        if ("triggerExerciseGroup".equals(string)) {
            return new EmptyCommand(l);
        }
        if ("move".equals(string)) {
            int n = object.getInt("move");
            int n5 = object.getInt(MOVE_ID_KEY);
            return new MoveCommand(l, new SEP((short)n), n5);
        }
        object = new StringBuilder();
        object.append("Invalid Command Type:");
        object.append(string);
        throw new InvalidJsonForObjectException(object.toString());
    }
}
