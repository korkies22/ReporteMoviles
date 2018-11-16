// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.Piece;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.position.Position;
import android.support.v4.app.FragmentManager;
import android.content.Context;

public abstract class AbstractBoardViewFactory<T extends BoardView>
{
    private T createBoardViewInstance(final Context context, final FragmentManager fragmentManager, final Position position) {
        if (position != null) {
            return this.createBoardView(context, fragmentManager, position);
        }
        return this.createBoardView(context, fragmentManager);
    }
    
    protected abstract T createBoardView(final Context p0, final FragmentManager p1);
    
    protected abstract T createBoardView(final Context p0, final FragmentManager p1, final Position p2);
    
    public T createBoardView(final Context context, final FragmentManager fragmentManager, final Position position, final boolean b, final boolean b2, final boolean b3) {
        final BoardView boardViewInstance = this.createBoardViewInstance(context, fragmentManager, position);
        boardViewInstance.setBoardViewSettings((BoardView.BoardViewSettings)new MyBoardViewSettings(b, b2, b3));
        boardViewInstance.rereadSettings();
        ServiceProvider.getInstance().getSettingsService().addBoardObserver((SettingsService.BoardSettingObserver)boardViewInstance);
        return (T)boardViewInstance;
    }
    
    public T createBoardView(final Context context, final FragmentManager fragmentManager, final boolean b, final boolean b2, final boolean b3) {
        return this.createBoardView(context, fragmentManager, null, b, b2, b3);
    }
    
    private static class MyBoardViewSettings implements BoardViewSettings
    {
        private boolean _autoQueenEnabled;
        private boolean _autoQueenFromSettings;
        private int _moveTime;
        private boolean _premoveEnabled;
        private boolean _premoveFromSettings;
        private boolean _themesFromSettings;
        
        MyBoardViewSettings(final boolean themesFromSettings, final boolean premoveFromSettings, final boolean autoQueenFromSettings) {
            this._premoveEnabled = false;
            this._autoQueenEnabled = false;
            this._moveTime = 300;
            this._themesFromSettings = themesFromSettings;
            this._premoveFromSettings = premoveFromSettings;
            this._autoQueenFromSettings = autoQueenFromSettings;
        }
        
        @Override
        public int getDrawableIdForPiece(final Piece piece) {
            final boolean themesFromSettings = this._themesFromSettings;
            return ServiceProvider.getInstance().getSettingsService().getDrawableIdForPiece(piece);
        }
        
        @Override
        public int getDrawableIdForSquare(final Square square) {
            final boolean themesFromSettings = this._themesFromSettings;
            return ServiceProvider.getInstance().getSettingsService().getDrawableIdForSquare(square);
        }
        
        @Override
        public int getMoveTimeInMills() {
            return ServiceProvider.getInstance().getSettingsService().getMoveTimeInMills();
        }
        
        @Override
        public boolean isArrowOfLastMoveEnabled() {
            return ServiceProvider.getInstance().getSettingsService().isArrowLastMoveEnabled();
        }
        
        @Override
        public boolean isAutoQueenEnabled() {
            if (this._autoQueenFromSettings) {
                return ServiceProvider.getInstance().getSettingsService().getAutoQueen();
            }
            return this._autoQueenEnabled;
        }
        
        @Override
        public boolean isMarkMoveSquaresModeEnabled() {
            return ServiceProvider.getInstance().getSettingsService().isMarkSquareModeForBoardEnabled();
        }
        
        @Override
        public boolean isPlayingMoveSounds() {
            return ServiceProvider.getInstance().getSettingsService().playMoveSounds();
        }
        
        @Override
        public boolean isPremoveEnabled() {
            if (this._premoveFromSettings) {
                return ServiceProvider.getInstance().getSettingsService().premovesAllowed();
            }
            return this._premoveEnabled;
        }
        
        @Override
        public void setAutoQueenEnabled(final boolean autoQueenEnabled) {
            this._autoQueenEnabled = autoQueenEnabled;
            this._autoQueenFromSettings = false;
        }
        
        @Override
        public void setMoveTimeInMills(final int moveTime) {
            this._moveTime = moveTime;
        }
        
        @Override
        public void setPremoveEnabled(final boolean premoveEnabled) {
            this._premoveEnabled = premoveEnabled;
            this._themesFromSettings = false;
        }
    }
}
