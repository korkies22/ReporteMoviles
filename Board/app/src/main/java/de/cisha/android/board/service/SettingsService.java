// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.Square;
import de.cisha.android.board.R;
import android.util.Log;
import de.cisha.chess.model.Piece;
import android.content.SharedPreferences.Editor;
import java.util.Iterator;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import android.content.Context;
import java.util.Set;

public class SettingsService implements LoginObserver
{
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
    private boolean _allowPremoves;
    private int _autoReplaySpeedMillis;
    private boolean _autoqueen;
    private BoardTheme _boardName;
    private Set<BoardSettingObserver> _boardObservers;
    private boolean _boardShowSquares;
    private boolean _confirmMove;
    private Context _context;
    private boolean _ebookRunWithBoard;
    private int _engineThinkingTime;
    private boolean _isArrowLastMoveEnabled;
    private String _language;
    private float _moveAudioVolume;
    private int _moveTimeInMills;
    private BoardTheme _piecesName;
    private boolean _playMoveSounds;
    private String _preferencesKey;
    private Set<TacticsSettingObserver> _tacticsObservers;
    private TacticsStopMode _tacticsStopMode;
    
    private SettingsService(final Context context) {
        this._moveTimeInMills = 300;
        this._engineThinkingTime = 1000;
        this._autoReplaySpeedMillis = 1000;
        this._playMoveSounds = false;
        this._boardName = BoardTheme.SILVER1;
        this._piecesName = BoardTheme.SILVER1;
        this._language = "default";
        this._ebookRunWithBoard = true;
        this._moveAudioVolume = 1.0f;
        this._autoqueen = true;
        this._allowPremoves = false;
        this._confirmMove = false;
        this._context = context;
        this.initSettings();
        ServiceProvider.getInstance().getLoginService().addLoginListener((ILoginService.LoginObserver)this);
    }
    
    public static SettingsService getInstance(final Context context) {
        synchronized (SettingsService.class) {
            if (SettingsService._instance == null) {
                SettingsService._instance = new SettingsService(context);
            }
            return SettingsService._instance;
        }
    }
    
    private void initSettings() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ServiceProvider.getInstance().getLoginService().getUserPrefix());
        sb.append("Settings");
        this._preferencesKey = sb.toString();
        final SharedPreferences sharedPreferences = this._context.getSharedPreferences(this._preferencesKey, 0);
        this._autoqueen = sharedPreferences.getBoolean("SETTINGS_AUTOQUEEN", false);
        this._allowPremoves = sharedPreferences.getBoolean("SETTINGS_PREMOVE", true);
        this._confirmMove = sharedPreferences.getBoolean("SETTINGS_CONFIRM_MOVE", false);
        this._tacticsStopMode = TacticsStopMode.valueOf(sharedPreferences.getString("SETTINGS_TACTICS_STOP", TacticsStopMode.STOP_ON_FAILURE.name()));
        this._boardObservers = Collections.newSetFromMap(new WeakHashMap<BoardSettingObserver, Boolean>());
        this._tacticsObservers = Collections.newSetFromMap(new WeakHashMap<TacticsSettingObserver, Boolean>());
        this._piecesName = BoardTheme.valueOf(sharedPreferences.getString("SETTINGS_PIECES_THEME", BoardTheme.SILVER1.name()));
        this._boardName = BoardTheme.valueOf(sharedPreferences.getString("SETTINGS_PIECE_THEME", BoardTheme.WOOD1.name()));
        this._boardShowSquares = sharedPreferences.getBoolean("SEETINGS_BOARD_SHOW_POSSIBLE_SQUARES", true);
        this._isArrowLastMoveEnabled = sharedPreferences.getBoolean("SETTINGS_BOARD_SHOW_LAST_MOVE_ARROW", true);
        this._moveTimeInMills = sharedPreferences.getInt("SETTINGS_BOARD_MOVE_TIME", 200);
        this._playMoveSounds = sharedPreferences.getBoolean("SETTINGS_BOARD_PLAY_MOVE_SOUNDS", false);
        this._autoReplaySpeedMillis = sharedPreferences.getInt("SETTINGS_BOARD_AUTO_REPLAY_SPEED", 1000);
    }
    
    private void notifiyBoardSettingObservers() {
        final Iterator<BoardSettingObserver> iterator = this._boardObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().boardSettingsChanged();
        }
    }
    
    private void notifiyTacticsSettingObservers() {
        final Iterator<TacticsSettingObserver> iterator = this._tacticsObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().tacticsSettingsChanged();
        }
    }
    
    private void storeBooleanToSettingsPref(final String s, final boolean b) {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        edit.putBoolean(s, b);
        edit.commit();
    }
    
    private void storeStringSetting(final String s, final String s2) {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        edit.putString(s, s2);
        edit.commit();
    }
    
    public void addBoardObserver(final BoardSettingObserver boardSettingObserver) {
        this._boardObservers.add(boardSettingObserver);
    }
    
    public void addTacticsObserver(final TacticsSettingObserver tacticsSettingObserver) {
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
    
    public int getDrawableIdForPiece(final Piece piece) {
        try {
            return this.getDrawableIdForPiece(piece, this.getPiecesType());
        }
        catch (NoSuchThemeException ex) {
            final String name = SettingsService.class.getName();
            final StringBuilder sb = new StringBuilder();
            sb.append("Error: no piece ");
            sb.append(piece);
            sb.append("found for theme - wrong theme set:");
            sb.append(this.getPiecesType());
            Log.e(name, sb.toString(), (Throwable)ex);
            return -1;
        }
    }
    
    public int getDrawableIdForPiece(final Piece piece, final BoardTheme boardTheme) throws NoSuchThemeException {
        String s = "";
        final char fenChar = piece.getFENChar();
        Label_0196: {
            if (fenChar != 'B') {
                if (fenChar != 'K') {
                    if (fenChar != 'N') {
                        if (fenChar != 'b') {
                            if (fenChar != 'k') {
                                if (fenChar != 'n') {
                                    switch (fenChar) {
                                        default: {
                                            switch (fenChar) {
                                                default: {
                                                    break Label_0196;
                                                }
                                                case 114: {
                                                    s = "black_rook";
                                                    break Label_0196;
                                                }
                                                case 113: {
                                                    s = "black_queen";
                                                    break Label_0196;
                                                }
                                                case 112: {
                                                    s = "black_pawn";
                                                    break Label_0196;
                                                }
                                            }
                                            break;
                                        }
                                        case 82: {
                                            s = "white_rook";
                                            break;
                                        }
                                        case 81: {
                                            s = "white_queen";
                                            break;
                                        }
                                        case 80: {
                                            s = "white_pawn";
                                            break;
                                        }
                                    }
                                }
                                else {
                                    s = "black_knight";
                                }
                            }
                            else {
                                s = "black_king";
                            }
                        }
                        else {
                            s = "black_bishop";
                        }
                    }
                    else {
                        s = "white_knight";
                    }
                }
                else {
                    s = "white_king";
                }
            }
            else {
                s = "white_bishop";
            }
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(boardTheme.getPrefix());
        sb.append("_");
        final String string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append(s);
        final String string2 = sb2.toString();
        try {
            return R.drawable.class.getDeclaredField(string2).getInt(new R.drawable());
        }
        catch (IllegalAccessException ex) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("No image found for piece ");
            sb3.append(piece);
            sb3.append(" style:");
            sb3.append(boardTheme);
            throw new NoSuchThemeException(sb3.toString(), ex);
        }
        catch (IllegalArgumentException ex2) {
            final StringBuilder sb4 = new StringBuilder();
            sb4.append("No image found for piece ");
            sb4.append(piece);
            sb4.append(" style:");
            sb4.append(boardTheme);
            throw new NoSuchThemeException(sb4.toString(), ex2);
        }
        catch (NoSuchFieldException ex3) {
            final StringBuilder sb5 = new StringBuilder();
            sb5.append("No image found for piece ");
            sb5.append(piece);
            sb5.append(" style:");
            sb5.append(boardTheme);
            throw new NoSuchThemeException(sb5.toString(), ex3);
        }
    }
    
    public int getDrawableIdForSquare(final Square square) {
        try {
            return this.getDrawableIdForSquare(square, this.getBoardType());
        }
        catch (NoSuchThemeException ex) {
            int n;
            if (square.isWhite()) {
                n = 2131231911;
            }
            else {
                n = 2131230882;
            }
            final String name = this.getClass().getName();
            final StringBuilder sb = new StringBuilder();
            sb.append("Square ");
            sb.append(square);
            sb.append(" requested for unknown theme - wrongBoardTypeName is set: ");
            sb.append(this.getBoardType());
            Log.e(name, sb.toString(), (Throwable)ex);
            return n;
        }
    }
    
    public int getDrawableIdForSquare(final Square square, final BoardTheme boardTheme) throws NoSuchThemeException {
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append(boardTheme.getPrefix());
            sb.append("_");
            final String string = sb.toString();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(square.getName());
            return R.drawable.class.getDeclaredField(sb2.toString()).getInt(new R.drawable());
        }
        catch (IllegalAccessException ex) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("No image found for square ");
            sb3.append(square);
            sb3.append(" style:");
            sb3.append(boardTheme);
            throw new NoSuchThemeException(sb3.toString(), ex);
        }
        catch (IllegalArgumentException ex2) {
            final StringBuilder sb4 = new StringBuilder();
            sb4.append("No image found for square ");
            sb4.append(square);
            sb4.append(" style:");
            sb4.append(boardTheme);
            throw new NoSuchThemeException(sb4.toString(), ex2);
        }
        catch (NoSuchFieldException ex3) {
            final StringBuilder sb5 = new StringBuilder();
            sb5.append("No image found for square ");
            sb5.append(square);
            sb5.append(" style:");
            sb5.append(boardTheme);
            throw new NoSuchThemeException(sb5.toString(), ex3);
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
    public void loginStateChanged(final boolean b) {
        this.initSettings();
    }
    
    public boolean playMoveSounds() {
        return this._playMoveSounds;
    }
    
    public boolean premovesAllowed() {
        return this._allowPremoves;
    }
    
    public void removeBoardObserver(final BoardSettingObserver boardSettingObserver) {
        this._boardObservers.remove(boardSettingObserver);
    }
    
    public void removeTacticsObserver(final TacticsSettingObserver tacticsSettingObserver) {
        this._tacticsObservers.remove(tacticsSettingObserver);
    }
    
    public void setArrowLastMoveEnabled(final boolean isArrowLastMoveEnabled) {
        this.storeBooleanToSettingsPref("SETTINGS_BOARD_SHOW_LAST_MOVE_ARROW", this._isArrowLastMoveEnabled = isArrowLastMoveEnabled);
    }
    
    public void setAutoQueen(final boolean autoqueen) {
        this.storeBooleanToSettingsPref("SETTINGS_AUTOQUEEN", this._autoqueen = autoqueen);
    }
    
    public void setAutoReplaySpeedMillis(final int autoReplaySpeedMillis) {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        edit.putInt("SETTINGS_BOARD_AUTO_REPLAY_SPEED", autoReplaySpeedMillis);
        edit.commit();
        this._autoReplaySpeedMillis = autoReplaySpeedMillis;
    }
    
    public void setBoardType(final BoardTheme boardName) {
        this._boardName = boardName;
        this.storeStringSetting("SETTINGS_PIECE_THEME", boardName.name());
        this.notifiyBoardSettingObservers();
    }
    
    public void setConfirmMove(final boolean confirmMove) {
        this.storeBooleanToSettingsPref("SETTINGS_CONFIRM_MOVE", this._confirmMove = confirmMove);
    }
    
    public void setEbookRunnningWithBoard(final boolean ebookRunWithBoard) {
        this._ebookRunWithBoard = ebookRunWithBoard;
    }
    
    public void setEngineThinkingTime(final int engineThinkingTime) {
        this._engineThinkingTime = engineThinkingTime;
    }
    
    public void setLanguage(final String language) {
        this._language = language;
    }
    
    public void setMarkSquareModeForBoardEnabled(final boolean boardShowSquares) {
        this.storeBooleanToSettingsPref("SEETINGS_BOARD_SHOW_POSSIBLE_SQUARES", this._boardShowSquares = boardShowSquares);
    }
    
    public void setMoveAudioVolume(final float moveAudioVolume) {
        this._moveAudioVolume = moveAudioVolume;
    }
    
    public void setMoveTime(final int moveTimeInMills) {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        edit.putInt("SETTINGS_BOARD_MOVE_TIME", moveTimeInMills);
        edit.commit();
        this._moveTimeInMills = moveTimeInMills;
    }
    
    public void setPiecesTypeName(final BoardTheme piecesName) {
        this._piecesName = piecesName;
        this.storeStringSetting("SETTINGS_PIECES_THEME", piecesName.name());
        this.notifiyBoardSettingObservers();
    }
    
    public void setPlayMoveSounds(final boolean playMoveSounds) {
        this.storeBooleanToSettingsPref("SETTINGS_BOARD_PLAY_MOVE_SOUNDS", this._playMoveSounds = playMoveSounds);
        this.notifiyBoardSettingObservers();
    }
    
    public void setPremovesAllowed(final boolean allowPremoves) {
        this.storeBooleanToSettingsPref("SETTINGS_PREMOVE", this._allowPremoves = allowPremoves);
    }
    
    public void setTacticsStopMode(final TacticsStopMode tacticsStopMode) {
        this._tacticsStopMode = tacticsStopMode;
        final SharedPreferences.Editor edit = this._context.getSharedPreferences(this._preferencesKey, 0).edit();
        edit.putString("SETTINGS_TACTICS_STOP", tacticsStopMode.name());
        edit.commit();
        this.notifiyTacticsSettingObservers();
    }
    
    public interface BoardSettingObserver
    {
        void boardSettingsChanged();
    }
    
    public enum BoardTheme
    {
        BLUE1("blue"), 
        SILVER1("silver"), 
        WOOD1("wood");
        
        private final String _prefix;
        
        private BoardTheme(final String prefix) {
            this._prefix = prefix;
        }
        
        public String getPrefix() {
            return this._prefix;
        }
    }
    
    public static class NoSuchThemeException extends Exception
    {
        private static final long serialVersionUID = 6251522641164455740L;
        
        public NoSuchThemeException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    public interface TacticsSettingObserver
    {
        void tacticsSettingsChanged();
    }
    
    public enum TacticsStopMode
    {
        STOP_AFTER_EVERY(2131690361, 2131690362), 
        STOP_NEVER(2131690363, 2131690364), 
        STOP_ON_FAILURE(2131690365, 2131690366);
        
        private int _descriptionResId;
        private int _titleResId;
        
        private TacticsStopMode(final int titleResId, final int descriptionResId) {
            this._titleResId = titleResId;
            this._descriptionResId = descriptionResId;
        }
        
        public int getDescriptionResId() {
            return this._descriptionResId;
        }
        
        public int getTitleResId() {
            return this._titleResId;
        }
    }
}
