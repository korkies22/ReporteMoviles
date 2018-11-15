/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.view.AbstractBoardViewFactory;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;

private static class AbstractBoardViewFactory.MyBoardViewSettings
implements BoardView.BoardViewSettings {
    private boolean _autoQueenEnabled = false;
    private boolean _autoQueenFromSettings;
    private int _moveTime = 300;
    private boolean _premoveEnabled = false;
    private boolean _premoveFromSettings;
    private boolean _themesFromSettings;

    AbstractBoardViewFactory.MyBoardViewSettings(boolean bl, boolean bl2, boolean bl3) {
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
