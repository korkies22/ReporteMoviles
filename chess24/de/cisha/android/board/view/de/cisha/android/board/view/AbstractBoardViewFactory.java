/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;

public abstract class AbstractBoardViewFactory<T extends BoardView> {
    private T createBoardViewInstance(Context context, FragmentManager fragmentManager, Position position) {
        if (position != null) {
            return this.createBoardView(context, fragmentManager, position);
        }
        return this.createBoardView(context, fragmentManager);
    }

    protected abstract T createBoardView(Context var1, FragmentManager var2);

    protected abstract T createBoardView(Context var1, FragmentManager var2, Position var3);

    public T createBoardView(Context object, FragmentManager fragmentManager, Position position, boolean bl, boolean bl2, boolean bl3) {
        object = this.createBoardViewInstance((Context)object, fragmentManager, position);
        object.setBoardViewSettings(new MyBoardViewSettings(bl, bl2, bl3));
        object.rereadSettings();
        ServiceProvider.getInstance().getSettingsService().addBoardObserver((SettingsService.BoardSettingObserver)object);
        return (T)object;
    }

    public T createBoardView(Context context, FragmentManager fragmentManager, boolean bl, boolean bl2, boolean bl3) {
        return this.createBoardView(context, fragmentManager, null, bl, bl2, bl3);
    }

    private static class MyBoardViewSettings
    implements BoardView.BoardViewSettings {
        private boolean _autoQueenEnabled = false;
        private boolean _autoQueenFromSettings;
        private int _moveTime = 300;
        private boolean _premoveEnabled = false;
        private boolean _premoveFromSettings;
        private boolean _themesFromSettings;

        MyBoardViewSettings(boolean bl, boolean bl2, boolean bl3) {
            this._themesFromSettings = bl;
            this._premoveFromSettings = bl2;
            this._autoQueenFromSettings = bl3;
        }

        @Override
        public int getDrawableIdForPiece(Piece piece) {
            boolean bl = this._themesFromSettings;
            return ServiceProvider.getInstance().getSettingsService().getDrawableIdForPiece(piece);
        }

        @Override
        public int getDrawableIdForSquare(Square square) {
            boolean bl = this._themesFromSettings;
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
        public void setAutoQueenEnabled(boolean bl) {
            this._autoQueenEnabled = bl;
            this._autoQueenFromSettings = false;
        }

        @Override
        public void setMoveTimeInMills(int n) {
            this._moveTime = n;
        }

        @Override
        public void setPremoveEnabled(boolean bl) {
            this._premoveEnabled = bl;
            this._themesFromSettings = false;
        }
    }

}
