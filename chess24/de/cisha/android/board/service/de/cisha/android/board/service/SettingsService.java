/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.util.Log
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import de.cisha.android.board.R;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class SettingsService
implements ILoginService.LoginObserver {
    private static final String ANALYSE_AUTO_REPLAY_SPEED = "SETTINGS_BOARD_AUTO_REPLAY_SPEED";
    private static final String AUTOQUEEN_SHAREDPREF_STRING = "SETTINGS_AUTOQUEEN";
    private static final String BOARD_MOVE_TIME_SHAREDPREF_STRING = "SETTINGS_BOARD_MOVE_TIME";
    private static final String BOARD_PLAY_MOVE_SOUNDS = "SETTINGS_BOARD_PLAY_MOVE_SOUNDS";
    private static final String BOARD_SHOW_ARROW_LAST_MOVE = "SETTINGS_BOARD_SHOW_LAST_MOVE_ARROW";
    private static final String BOARD_SHOW_POSSIBLE_SQUARES = "SEETINGS_BOARD_SHOW_POSSIBLE_SQUARES";
    private static final String BOARD_THEME_SHAREDPREF_STRING = "SETTINGS_PIECE_THEME";
    private static final String CONFIRM_MOVE_SHAREDPREF_STRING = "SETTINGS_CONFIRM_MOVE";
    public static final String LANGUAGE_KEEP_DEFAULT = "default";
    private static final String PIECES_THEME_SHAREDPREF_STRING = "SETTINGS_PIECES_THEME";
    private static final String PREMOVE_SHAREDPREF_STRING = "SETTINGS_PREMOVE";
    private static final String SETTINGS_PREFENERENCES = "Settings";
    private static final String TACTICS_STOP_MODE_SHAREDPREF_STRING = "SETTINGS_TACTICS_STOP";
    private static SettingsService _instance;
    private boolean _allowPremoves = false;
    private int _autoReplaySpeedMillis = 1000;
    private boolean _autoqueen = true;
    private BoardTheme _boardName = BoardTheme.SILVER1;
    private Set<BoardSettingObserver> _boardObservers;
    private boolean _boardShowSquares;
    private boolean _confirmMove = false;
    private Context _context;
    private boolean _ebookRunWithBoard = true;
    private int _engineThinkingTime = 1000;
    private boolean _isArrowLastMoveEnabled;
    private String _language = "default";
    private float _moveAudioVolume = 1.0f;
    private int _moveTimeInMills = 300;
    private BoardTheme _piecesName = BoardTheme.SILVER1;
    private boolean _playMoveSounds = false;
    private String _preferencesKey;
    private Set<TacticsSettingObserver> _tacticsObservers;
    private TacticsStopMode _tacticsStopMode;

    private SettingsService(Context context) {
        this._context = context;
        this.initSettings();
        ServiceProvider.getInstance().getLoginService().addLoginListener(this);
    }

    public static SettingsService getInstance(Context object) {
        synchronized (SettingsService.class) {
            if (_instance == null) {
                _instance = new SettingsService((Context)object);
            }
            object = _instance;
            return object;
        }
    }

    private void initSettings() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceProvider.getInstance().getLoginService().getUserPrefix());
        stringBuilder.append(SETTINGS_PREFENERENCES);
        this._preferencesKey = stringBuilder.toString();
        stringBuilder = this._context.getSharedPreferences(this._preferencesKey, 0);
        this._autoqueen = stringBuilder.getBoolean(AUTOQUEEN_SHAREDPREF_STRING, false);
        this._allowPremoves = stringBuilder.getBoolean(PREMOVE_SHAREDPREF_STRING, true);
        this._confirmMove = stringBuilder.getBoolean(CONFIRM_MOVE_SHAREDPREF_STRING, false);
        this._tacticsStopMode = TacticsStopMode.valueOf(stringBuilder.getString(TACTICS_STOP_MODE_SHAREDPREF_STRING, TacticsStopMode.STOP_ON_FAILURE.name()));
        this._boardObservers = Collections.newSetFromMap(new WeakHashMap());
        this._tacticsObservers = Collections.newSetFromMap(new WeakHashMap());
        this._piecesName = BoardTheme.valueOf(stringBuilder.getString(PIECES_THEME_SHAREDPREF_STRING, BoardTheme.SILVER1.name()));
        this._boardName = BoardTheme.valueOf(stringBuilder.getString(BOARD_THEME_SHAREDPREF_STRING, BoardTheme.WOOD1.name()));
        this._boardShowSquares = stringBuilder.getBoolean(BOARD_SHOW_POSSIBLE_SQUARES, true);
        this._isArrowLastMoveEnabled = stringBuilder.getBoolean(BOARD_SHOW_ARROW_LAST_MOVE, true);
        this._moveTimeInMills = stringBuilder.getInt(BOARD_MOVE_TIME_SHAREDPREF_STRING, 200);
        this._playMoveSounds = stringBuilder.getBoolean(BOARD_PLAY_MOVE_SOUNDS, false);
        this._autoReplaySpeedMillis = stringBuilder.getInt(ANALYSE_AUTO_REPLAY_SPEED, 1000);
    }

    private void notifiyBoardSettingObservers() {
        Iterator<BoardSettingObserver> iterator = this._boardObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().boardSettingsChanged();
        }
    }

    private void notifiyTacticsSettingObservers() {
        Iterator<TacticsSettingObserver> iterator = this._tacticsObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().tacticsSettingsChanged();
        }
    }

    private void storeBooleanToSettingsPref(String string, boolean bl) {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        editor.putBoolean(string, bl);
        editor.commit();
    }

    private void storeStringSetting(String string, String string2) {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        editor.putString(string, string2);
        editor.commit();
    }

    public void addBoardObserver(BoardSettingObserver boardSettingObserver) {
        this._boardObservers.add(boardSettingObserver);
    }

    public void addTacticsObserver(TacticsSettingObserver tacticsSettingObserver) {
        this._tacticsObservers.add(tacticsSettingObserver);
    }

    public boolean confirmMove() {
        return this._confirmMove;
    }

    public boolean getAutoQueen() {
        return this._autoqueen;
    }

    public int getAutoReplaySpeedMillis() {
        return this._autoReplaySpeedMillis;
    }

    public BoardTheme getBoardType() {
        return this._boardName;
    }

    public int getDrawableIdForPiece(Piece piece) {
        try {
            int n = this.getDrawableIdForPiece(piece, this.getPiecesType());
            return n;
        }
        catch (NoSuchThemeException noSuchThemeException) {
            String string = SettingsService.class.getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error: no piece ");
            stringBuilder.append(piece);
            stringBuilder.append("found for theme - wrong theme set:");
            stringBuilder.append((Object)this.getPiecesType());
            Log.e((String)string, (String)stringBuilder.toString(), (Throwable)noSuchThemeException);
            return -1;
        }
    }

    public int getDrawableIdForPiece(Piece piece, BoardTheme boardTheme) throws NoSuchThemeException {
        String string = "";
        int n = piece.getFENChar();
        if (n != 66) {
            if (n != 75) {
                if (n != 78) {
                    if (n != 98) {
                        if (n != 107) {
                            if (n != 110) {
                                block1 : switch (n) {
                                    default: {
                                        switch (n) {
                                            default: {
                                                break block1;
                                            }
                                            case 114: {
                                                string = "black_rook";
                                                break block1;
                                            }
                                            case 113: {
                                                string = "black_queen";
                                                break block1;
                                            }
                                            case 112: 
                                        }
                                        string = "black_pawn";
                                        break;
                                    }
                                    case 82: {
                                        string = "white_rook";
                                        break;
                                    }
                                    case 81: {
                                        string = "white_queen";
                                        break;
                                    }
                                    case 80: {
                                        string = "white_pawn";
                                        break;
                                    }
                                }
                            } else {
                                string = "black_knight";
                            }
                        } else {
                            string = "black_king";
                        }
                    } else {
                        string = "black_bishop";
                    }
                } else {
                    string = "white_knight";
                }
            } else {
                string = "white_king";
            }
        } else {
            string = "white_bishop";
        }
        CharSequence charSequence = new StringBuilder();
        charSequence.append(boardTheme.getPrefix());
        charSequence.append("_");
        charSequence = ((StringBuilder)charSequence).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(string);
        string = stringBuilder.toString();
        try {
            n = R.drawable.class.getDeclaredField(string).getInt(new R.drawable());
            return n;
        }
        catch (IllegalAccessException illegalAccessException) {
            charSequence = new StringBuilder();
            charSequence.append("No image found for piece ");
            charSequence.append(piece);
            charSequence.append(" style:");
            charSequence.append((Object)boardTheme);
            throw new NoSuchThemeException(((StringBuilder)charSequence).toString(), illegalAccessException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            charSequence = new StringBuilder();
            charSequence.append("No image found for piece ");
            charSequence.append(piece);
            charSequence.append(" style:");
            charSequence.append((Object)boardTheme);
            throw new NoSuchThemeException(((StringBuilder)charSequence).toString(), illegalArgumentException);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            charSequence = new StringBuilder();
            charSequence.append("No image found for piece ");
            charSequence.append(piece);
            charSequence.append(" style:");
            charSequence.append((Object)boardTheme);
            throw new NoSuchThemeException(((StringBuilder)charSequence).toString(), noSuchFieldException);
        }
    }

    public int getDrawableIdForSquare(Square square) {
        try {
            int n = this.getDrawableIdForSquare(square, this.getBoardType());
            return n;
        }
        catch (NoSuchThemeException noSuchThemeException) {
            int n = square.isWhite() ? 2131231911 : 2131230882;
            String string = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Square ");
            stringBuilder.append(square);
            stringBuilder.append(" requested for unknown theme - wrongBoardTypeName is set: ");
            stringBuilder.append((Object)this.getBoardType());
            Log.e((String)string, (String)stringBuilder.toString(), (Throwable)noSuchThemeException);
            return n;
        }
    }

    public int getDrawableIdForSquare(Square square, BoardTheme boardTheme) throws NoSuchThemeException {
        try {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(boardTheme.getPrefix());
            charSequence.append("_");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(square.getName());
            int n = R.drawable.class.getDeclaredField(stringBuilder.toString()).getInt(new R.drawable());
            return n;
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No image found for square ");
            stringBuilder.append(square);
            stringBuilder.append(" style:");
            stringBuilder.append((Object)boardTheme);
            throw new NoSuchThemeException(stringBuilder.toString(), illegalAccessException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No image found for square ");
            stringBuilder.append(square);
            stringBuilder.append(" style:");
            stringBuilder.append((Object)boardTheme);
            throw new NoSuchThemeException(stringBuilder.toString(), illegalArgumentException);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No image found for square ");
            stringBuilder.append(square);
            stringBuilder.append(" style:");
            stringBuilder.append((Object)boardTheme);
            throw new NoSuchThemeException(stringBuilder.toString(), noSuchFieldException);
        }
    }

    public int getEngineThinkingTime() {
        return this._engineThinkingTime;
    }

    public String getLanguage() {
        return this._language;
    }

    public float getMoveAudioVolume() {
        return this._moveAudioVolume;
    }

    public int getMoveTimeInMills() {
        return this._moveTimeInMills;
    }

    public BoardTheme getPiecesType() {
        return this._piecesName;
    }

    public TacticsStopMode getTacticsStopMode() {
        return this._tacticsStopMode;
    }

    public boolean isArrowLastMoveEnabled() {
        return this._isArrowLastMoveEnabled;
    }

    public boolean isEbookRunningWithBoard() {
        return this._ebookRunWithBoard;
    }

    public boolean isMarkSquareModeForBoardEnabled() {
        return this._boardShowSquares;
    }

    @Override
    public void loginStateChanged(boolean bl) {
        this.initSettings();
    }

    public boolean playMoveSounds() {
        return this._playMoveSounds;
    }

    public boolean premovesAllowed() {
        return this._allowPremoves;
    }

    public void removeBoardObserver(BoardSettingObserver boardSettingObserver) {
        this._boardObservers.remove(boardSettingObserver);
    }

    public void removeTacticsObserver(TacticsSettingObserver tacticsSettingObserver) {
        this._tacticsObservers.remove(tacticsSettingObserver);
    }

    public void setArrowLastMoveEnabled(boolean bl) {
        this._isArrowLastMoveEnabled = bl;
        this.storeBooleanToSettingsPref(BOARD_SHOW_ARROW_LAST_MOVE, bl);
    }

    public void setAutoQueen(boolean bl) {
        this._autoqueen = bl;
        this.storeBooleanToSettingsPref(AUTOQUEEN_SHAREDPREF_STRING, bl);
    }

    public void setAutoReplaySpeedMillis(int n) {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        editor.putInt(ANALYSE_AUTO_REPLAY_SPEED, n);
        editor.commit();
        this._autoReplaySpeedMillis = n;
    }

    public void setBoardType(BoardTheme boardTheme) {
        this._boardName = boardTheme;
        this.storeStringSetting(BOARD_THEME_SHAREDPREF_STRING, boardTheme.name());
        this.notifiyBoardSettingObservers();
    }

    public void setConfirmMove(boolean bl) {
        this._confirmMove = bl;
        this.storeBooleanToSettingsPref(CONFIRM_MOVE_SHAREDPREF_STRING, bl);
    }

    public void setEbookRunnningWithBoard(boolean bl) {
        this._ebookRunWithBoard = bl;
    }

    public void setEngineThinkingTime(int n) {
        this._engineThinkingTime = n;
    }

    public void setLanguage(String string) {
        this._language = string;
    }

    public void setMarkSquareModeForBoardEnabled(boolean bl) {
        this._boardShowSquares = bl;
        this.storeBooleanToSettingsPref(BOARD_SHOW_POSSIBLE_SQUARES, bl);
    }

    public void setMoveAudioVolume(float f) {
        this._moveAudioVolume = f;
    }

    public void setMoveTime(int n) {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        editor.putInt(BOARD_MOVE_TIME_SHAREDPREF_STRING, n);
        editor.commit();
        this._moveTimeInMills = n;
    }

    public void setPiecesTypeName(BoardTheme boardTheme) {
        this._piecesName = boardTheme;
        this.storeStringSetting(PIECES_THEME_SHAREDPREF_STRING, boardTheme.name());
        this.notifiyBoardSettingObservers();
    }

    public void setPlayMoveSounds(boolean bl) {
        this._playMoveSounds = bl;
        this.storeBooleanToSettingsPref(BOARD_PLAY_MOVE_SOUNDS, bl);
        this.notifiyBoardSettingObservers();
    }

    public void setPremovesAllowed(boolean bl) {
        this._allowPremoves = bl;
        this.storeBooleanToSettingsPref(PREMOVE_SHAREDPREF_STRING, bl);
    }

    public void setTacticsStopMode(TacticsStopMode tacticsStopMode) {
        this._tacticsStopMode = tacticsStopMode;
        SharedPreferences.Editor editor = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        editor.putString(TACTICS_STOP_MODE_SHAREDPREF_STRING, tacticsStopMode.name());
        editor.commit();
        this.notifiyTacticsSettingObservers();
    }

    public static interface BoardSettingObserver {
        public void boardSettingsChanged();
    }

    public static enum BoardTheme {
        WOOD1("wood"),
        SILVER1("silver"),
        BLUE1("blue");
        
        private final String _prefix;

        private BoardTheme(String string2) {
            this._prefix = string2;
        }

        public String getPrefix() {
            return this._prefix;
        }
    }

    public static class NoSuchThemeException
    extends Exception {
        private static final long serialVersionUID = 6251522641164455740L;

        public NoSuchThemeException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    public static interface TacticsSettingObserver {
        public void tacticsSettingsChanged();
    }

    public static enum TacticsStopMode {
        STOP_AFTER_EVERY(2131690361, 2131690362),
        STOP_ON_FAILURE(2131690365, 2131690366),
        STOP_NEVER(2131690363, 2131690364);
        
        private int _descriptionResId;
        private int _titleResId;

        private TacticsStopMode(int n2, int n3) {
            this._titleResId = n2;
            this._descriptionResId = n3;
        }

        public int getDescriptionResId() {
            return this._descriptionResId;
        }

        public int getTitleResId() {
            return this._titleResId;
        }
    }

}
