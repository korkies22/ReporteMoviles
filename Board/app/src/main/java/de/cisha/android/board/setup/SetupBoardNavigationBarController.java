// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup;

import de.cisha.chess.model.Move;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Square;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.cisha.chess.model.ModifyablePosition;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import java.util.TreeMap;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import android.widget.RadioGroup;
import de.cisha.android.board.setup.model.PositionHolder;
import java.util.Map;
import de.cisha.android.board.setup.view.EnpassantView;
import android.widget.CheckBox;
import android.view.View;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;

public class SetupBoardNavigationBarController implements MenuBarObserver, PositionObserver
{
    private View _castlingView;
    private CheckBox _checkCastlingBlack00;
    private CheckBox _checkCastlingBlack000;
    private CheckBox _checkCastlingWhite00;
    private CheckBox _checkCastlingWhite000;
    private boolean _doNotClosePopup;
    private EnpassantView _enpassantView;
    private Map<Integer, Runnable> _mapItemAction;
    private MenuBar _menuBar;
    private PositionHolder _positionHolder;
    private RadioGroup _radioGroupActiveColor;
    private View _subMenuActive;
    private ArrowBottomContainerView _submenuView;
    
    public SetupBoardNavigationBarController(final MenuBar menuBar, final PositionHolder positionHolder) {
        positionHolder.addPositionObserver(this);
        (this._menuBar = menuBar).setObserver((MenuBar.MenuBarObserver)this);
        this._menuBar.setPopupViewGroup((ViewGroup)menuBar.getRootView().findViewById(2131296991));
        this._positionHolder = positionHolder;
        (this._mapItemAction = new TreeMap<Integer, Runnable>()).put(2131296988, new Runnable() {
            @Override
            public void run() {
                SetupBoardNavigationBarController.this._positionHolder.clearPosition();
            }
        });
        (this._submenuView = new ArrowBottomContainerView(this._menuBar.getContext())).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
            }
        });
        (this._radioGroupActiveColor = (RadioGroup)LayoutInflater.from(this._menuBar.getContext()).inflate(2131427541, (ViewGroup)null)).setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(final RadioGroup radioGroup, final int n) {
                final boolean activeColor = n == 2131296983;
                final ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                modifyablePosition.setActiveColor(activeColor);
                modifyablePosition.setEnPassantColumn(-1);
                SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
            }
        });
        this._castlingView = LayoutInflater.from(this._menuBar.getContext()).inflate(2131427545, (ViewGroup)null);
        this._checkCastlingWhite000 = (CheckBox)this._castlingView.findViewById(2131296981);
        this._checkCastlingWhite00 = (CheckBox)this._castlingView.findViewById(2131296980);
        this._checkCastlingBlack000 = (CheckBox)this._castlingView.findViewById(2131296979);
        this._checkCastlingBlack00 = (CheckBox)this._castlingView.findViewById(2131296978);
        final CompoundButton.OnCheckedChangeListener compoundButton.OnCheckedChangeListener = (CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                final ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                switch (compoundButton.getId()) {
                    case 2131296981: {
                        modifyablePosition.setWhiteQueenSideCastling(b);
                        break;
                    }
                    case 2131296980: {
                        modifyablePosition.setWhiteKingSideCastling(b);
                        break;
                    }
                    case 2131296979: {
                        modifyablePosition.setBlackQueenSideCastling(b);
                        break;
                    }
                    case 2131296978: {
                        modifyablePosition.setBlackKingSideCastling(b);
                        break;
                    }
                }
                SetupBoardNavigationBarController.this._doNotClosePopup = true;
                SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
            }
        };
        this._checkCastlingWhite000.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButton.OnCheckedChangeListener);
        this._checkCastlingWhite00.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButton.OnCheckedChangeListener);
        this._checkCastlingBlack000.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButton.OnCheckedChangeListener);
        this._checkCastlingBlack00.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButton.OnCheckedChangeListener);
        (this._enpassantView = new EnpassantView(this._menuBar.getContext())).setEnpassantListener((EnpassantView.EnpassantListener)new EnpassantView.EnpassantListener() {
            @Override
            public void enpassantChanged(final Square square) {
                final ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                int column;
                if (square != null) {
                    column = square.getColumn();
                }
                else {
                    column = -1;
                }
                modifyablePosition.setEnPassantColumn(column);
                SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
            }
        });
        this._mapItemAction.put(2131296990, new Runnable() {
            @Override
            public void run() {
                SetupBoardNavigationBarController.this._positionHolder.setPosition(new Position(FEN.STARTING_POSITION));
            }
        });
        this._mapItemAction.put(2131296986, new Runnable() {
            @Override
            public void run() {
                SetupBoardNavigationBarController.this.showSubmenu((View)SetupBoardNavigationBarController.this._radioGroupActiveColor);
                final RadioGroup access.200 = SetupBoardNavigationBarController.this._radioGroupActiveColor;
                int n;
                if (SetupBoardNavigationBarController.this._positionHolder.getPosition().getActiveColor()) {
                    n = 2131296983;
                }
                else {
                    n = 2131296982;
                }
                access.200.check(n);
            }
        });
        this._mapItemAction.put(2131296987, new Runnable() {
            @Override
            public void run() {
                SetupBoardNavigationBarController.this.updateCastlingView();
                SetupBoardNavigationBarController.this.showSubmenu(SetupBoardNavigationBarController.this._castlingView);
            }
        });
        this._mapItemAction.put(2131296989, new Runnable() {
            @Override
            public void run() {
                SetupBoardNavigationBarController.this.updateEnpassantView(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                SetupBoardNavigationBarController.this.showSubmenu((View)SetupBoardNavigationBarController.this._enpassantView);
            }
        });
    }
    
    private void checkForCastling(final boolean b, final Piece piece, final Piece piece2, final Piece piece3, final CheckBox checkBox, final CheckBox checkBox2, final ModifyablePosition modifyablePosition) {
        final boolean equals = King.instanceForColor(b).equals(piece2);
        final boolean b2 = false;
        final boolean enabled = equals && Rook.instanceForColor(b).equals(piece3);
        final boolean enabled2 = King.instanceForColor(b).equals(piece2) && Rook.instanceForColor(b).equals(piece);
        final boolean blackKingSideCastling = enabled && modifyablePosition.isCastlingPossible(b, true);
        boolean blackQueenSideCastling = b2;
        if (enabled2) {
            blackQueenSideCastling = b2;
            if (modifyablePosition.isCastlingPossible(b, false)) {
                blackQueenSideCastling = true;
            }
        }
        checkBox.setChecked(blackQueenSideCastling);
        checkBox.setEnabled(enabled2);
        checkBox2.setChecked(blackKingSideCastling);
        checkBox2.setEnabled(enabled);
        if (b) {
            modifyablePosition.setWhiteKingSideCastling(blackKingSideCastling);
            modifyablePosition.setWhiteQueenSideCastling(blackQueenSideCastling);
            return;
        }
        modifyablePosition.setBlackKingSideCastling(blackKingSideCastling);
        modifyablePosition.setBlackQueenSideCastling(blackQueenSideCastling);
    }
    
    private void showSubmenu(final View subMenuActive) {
        if (subMenuActive.equals(this._subMenuActive)) {
            this.hidePopups();
            return;
        }
        this._subMenuActive = subMenuActive;
        this._submenuView.removeAllViews();
        this._submenuView.addView(subMenuActive);
        this._menuBar.showPopup(this._submenuView);
    }
    
    private void updateCastlingView() {
        final Position position = this._positionHolder.getPosition();
        final Piece piece = position.getPieceFor(Square.SQUARE_A1);
        final Piece piece2 = position.getPieceFor(Square.SQUARE_E1);
        final Piece piece3 = position.getPieceFor(Square.SQUARE_H1);
        final Piece piece4 = position.getPieceFor(Square.SQUARE_A8);
        final Piece piece5 = position.getPieceFor(Square.SQUARE_E8);
        final Piece piece6 = position.getPieceFor(Square.SQUARE_H8);
        final ModifyablePosition modifyablePosition = new ModifyablePosition(position);
        this.checkForCastling(true, piece, piece2, piece3, this._checkCastlingWhite000, this._checkCastlingWhite00, modifyablePosition);
        this.checkForCastling(false, piece4, piece5, piece6, this._checkCastlingBlack000, this._checkCastlingBlack00, modifyablePosition);
        if (!modifyablePosition.equals(position)) {
            this._positionHolder.setPosition(modifyablePosition.getSafePosition());
        }
    }
    
    private void updateEnpassantView(final Position position) {
        this._enpassantView.removeAllViews();
        final Square enPassantSquare = position.getEnPassantSquare();
        final boolean activeColor = position.getActiveColor();
        final Pawn instanceForColor = Pawn.instanceForColor(activeColor ^ true);
        int n;
        if (activeColor) {
            n = 4;
        }
        else {
            n = 3;
        }
        int n2;
        if (activeColor) {
            n2 = 5;
        }
        else {
            n2 = 2;
        }
        int n4;
        int n3 = n4 = 0;
        while (true) {
            final boolean b = true;
            if (n3 >= 8) {
                break;
            }
            int n5 = n4;
            if (instanceForColor.equals(position.getPieceFor(Square.instanceForRowAndColumn(n, n3)))) {
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(n2, n3);
                final boolean equals = instanceForRowAndColumn.equals(enPassantSquare);
                this._enpassantView.addEnpassant(instanceForRowAndColumn, equals);
                n5 = (b ? 1 : 0);
                if (n4 == 0) {
                    n5 = ((equals && b) ? 1 : 0);
                }
            }
            ++n3;
            n4 = n5;
        }
        this._enpassantView.addNoEnpassantOption((n4 ^ 0x1) != 0x0);
    }
    
    public void hidePopups() {
        this._menuBar.hidePopup();
        this._subMenuActive = null;
    }
    
    @Override
    public void menuItemClicked(final MenuBarItem menuBarItem) {
        final Runnable runnable = this._mapItemAction.get(menuBarItem.getId());
        if (runnable != null) {
            runnable.run();
        }
    }
    
    @Override
    public void menuItemLongClicked(final MenuBarItem menuBarItem) {
    }
    
    @Override
    public void positionChanged(final Position position, final Move move) {
        if (!this._doNotClosePopup) {
            this.hidePopups();
        }
        this._doNotClosePopup = false;
        this.updateCastlingView();
    }
}
