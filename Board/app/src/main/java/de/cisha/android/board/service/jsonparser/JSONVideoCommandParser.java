// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.command.MoveCommand;
import de.cisha.android.board.video.command.EmptyCommand;
import de.cisha.android.board.video.command.UnMarkAllCommand;
import de.cisha.android.board.video.command.UnMarkSquareCommand;
import de.cisha.android.board.video.command.MarkSquareCommand;
import de.cisha.chess.model.Square;
import de.cisha.android.board.video.command.UnArrowAllCommand;
import de.cisha.android.board.video.command.UnArrowCommand;
import de.cisha.android.board.video.command.ArrowCommand;
import de.cisha.chess.model.SEP;
import de.cisha.android.board.video.command.SelectGameCommand;
import de.cisha.android.board.video.command.GotoCommand;
import org.json.JSONObject;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.video.command.VideoCommand;

public class JSONVideoCommandParser extends JSONAPIResultParser<VideoCommand>
{
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
    
    public static int getColorForString(final String s) {
        if ("green".equalsIgnoreCase(s)) {
            return -16711936;
        }
        if ("red".equalsIgnoreCase(s)) {
            return -65536;
        }
        if ("blue".equalsIgnoreCase(s)) {
            return -16776961;
        }
        if ("yellow".equalsIgnoreCase(s)) {
            return -256;
        }
        final Logger instance = Logger.getInstance();
        final String name = JSONVideoCommandParser.class.getName();
        final StringBuilder sb = new StringBuilder();
        sb.append("Error: unknown color: ");
        sb.append(s);
        instance.error(name, sb.toString());
        return -65281;
    }
    
    @Override
    public VideoCommand parseResult(JSONObject optJSONObject) throws InvalidJsonForObjectException, JSONException {
        final long n = (long)(optJSONObject.getDouble("t") * 1000.0);
        final String string = optJSONObject.getString("n");
        if ((optJSONObject = optJSONObject.optJSONObject("d")) == null) {
            optJSONObject = new JSONObject();
        }
        if ("gotoId".equals(string)) {
            return new GotoCommand(optJSONObject.getInt("id"), n);
        }
        if ("selectGame".equals(string)) {
            return new SelectGameCommand(optJSONObject.getInt("gameIndex"), optJSONObject.optInt("initialMoveId", 0), n);
        }
        TimedCommand timedCommand;
        if ("drawArrow".equals(string)) {
            final int colorForString = getColorForString(optJSONObject.getString("color"));
            final String string2 = optJSONObject.getString("lineAsCan");
            final SEP sep = new SEP(string2);
            if (!sep.isValid()) {
                final StringBuilder sb = new StringBuilder();
                sb.append("Invalid EAN given in arrow: ");
                sb.append(string2);
                throw new InvalidJsonForObjectException(sb.toString());
            }
            timedCommand = new ArrowCommand(sep, colorForString, n);
        }
        else if ("unarrow".equals(string)) {
            final String string3 = optJSONObject.getString("lineAsCan");
            final SEP sep2 = new SEP(string3);
            if (!sep2.isValid()) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid EAN given in arrow: ");
                sb2.append(string3);
                throw new InvalidJsonForObjectException(sb2.toString());
            }
            timedCommand = new UnArrowCommand(sep2, n);
        }
        else {
            if ("unarrowAll".equals(string)) {
                return new UnArrowAllCommand(n);
            }
            if ("highlightSquare".equals(string)) {
                final int colorForString2 = getColorForString(optJSONObject.getString("color"));
                final int int1 = optJSONObject.getInt("x");
                final int int2 = optJSONObject.getInt("y");
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(int2, int1);
                if (!instanceForRowAndColumn.isValid()) {
                    final StringBuilder sb3 = new StringBuilder();
                    sb3.append("Invalid square given in x ");
                    sb3.append(int1);
                    sb3.append(" y: ");
                    sb3.append(int2);
                    throw new InvalidJsonForObjectException(sb3.toString());
                }
                timedCommand = new MarkSquareCommand(instanceForRowAndColumn, colorForString2, n);
            }
            else if ("unmark".equals(string)) {
                final int int3 = optJSONObject.getInt("x");
                final int int4 = optJSONObject.getInt("y");
                final Square instanceForRowAndColumn2 = Square.instanceForRowAndColumn(int4, int3);
                if (instanceForRowAndColumn2.isValid()) {
                    return new UnMarkSquareCommand(instanceForRowAndColumn2, n);
                }
                final StringBuilder sb4 = new StringBuilder();
                sb4.append("Invalid square given in x ");
                sb4.append(int3);
                sb4.append(" y: ");
                sb4.append(int4);
                throw new InvalidJsonForObjectException(sb4.toString());
            }
            else {
                if ("unmarkAll".equals(string)) {
                    return new UnMarkAllCommand(n);
                }
                if ("triggerExerciseGroup".equals(string)) {
                    return new EmptyCommand(n);
                }
                if ("move".equals(string)) {
                    return new MoveCommand(n, new SEP((short)optJSONObject.getInt("move")), optJSONObject.getInt("id"));
                }
                final StringBuilder sb5 = new StringBuilder();
                sb5.append("Invalid Command Type:");
                sb5.append(string);
                throw new InvalidJsonForObjectException(sb5.toString());
            }
        }
        return timedCommand;
    }
}
