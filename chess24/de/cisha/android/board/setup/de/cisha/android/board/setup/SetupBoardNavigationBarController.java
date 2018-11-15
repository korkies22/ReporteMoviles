/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 */
package de.cisha.android.board.setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.android.board.setup.view.EnpassantView;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import java.util.Map;
import java.util.TreeMap;

public class SetupBoardNavigationBarController
implements MenuBar.MenuBarObserver,
PositionObserver {
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

    public SetupBoardNavigationBarController(MenuBar menuBar, PositionHolder positionHolder) {
        positionHolder.addPositionObserver(this);
        this._menuBar = menuBar;
        this._menuBar.setObserver(this);
        this._menuBar.setPopupViewGroup((ViewGroup)menuBar.getRootView().findViewById(2131296991));
        this._positionHolder = positionHolder;
        this._mapItemAction = new TreeMap<Integer, Runnable>();
        this._mapItemAction.put(2131296988, new Runnable(){

            @Override
            public void run() {
                SetupBoardNavigationBarController.this._positionHolder.clearPosition();
            }
        });
        this._submenuView = new ArrowBottomContainerView(this._menuBar.getContext());
        this._submenuView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        this._radioGroupActiveColor = (RadioGroup)LayoutInflater.from((Context)this._menuBar.getContext()).inflate(2131427541, null);
        this._radioGroupActiveColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            public void onCheckedChanged(RadioGroup object, int n) {
                boolean bl = n == 2131296983;
                object = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                object.setActiveColor(bl);
                object.setEnPassantColumn(-1);
                SetupBoardNavigationBarController.this._positionHolder.setPosition(object.getSafePosition());
            }
        });
        this._castlingView = LayoutInflater.from((Context)this._menuBar.getContext()).inflate(2131427545, null);
        this._checkCastlingWhite000 = (CheckBox)this._castlingView.findViewById(2131296981);
        this._checkCastlingWhite00 = (CheckBox)this._castlingView.findViewById(2131296980);
        this._checkCastlingBlack000 = (CheckBox)this._castlingView.findViewById(2131296979);
        this._checkCastlingBlack00 = (CheckBox)this._castlingView.findViewById(2131296978);
        menuBar = new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                switch (compoundButton.getId()) {
                    default: {
                        break;
                    }
                    case 2131296981: {
                        modifyablePosition.setWhiteQueenSideCastling(bl);
                        break;
                    }
                    case 2131296980: {
                        modifyablePosition.setWhiteKingSideCastling(bl);
                        break;
                    }
                    case 2131296979: {
                        modifyablePosition.setBlackQueenSideCastling(bl);
                        break;
                    }
                    case 2131296978: {
                        modifyablePosition.setBlackKingSideCastling(bl);
                    }
                }
                SetupBoardNavigationBarController.this._doNotClosePopup = true;
                SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
            }
        };
        this._checkCastlingWhite000.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)menuBar);
        this._checkCastlingWhite00.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)menuBar);
        this._checkCastlingBlack000.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)menuBar);
        this._checkCastlingBlack00.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)menuBar);
        this._enpassantView = new EnpassantView(this._menuBar.getContext());
        this._enpassantView.setEnpassantListener(new EnpassantView.EnpassantListener(){

            @Override
            public void enpassantChanged(Square square) {
                ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                int n = square != null ? square.getColumn() : -1;
                modifyablePosition.setEnPassantColumn(n);
                SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
            }
        });
        this._mapItemAction.put(2131296990, new Runnable(){

            @Override
            public void run() {
                SetupBoardNavigationBarController.this._positionHolder.setPosition(new Position(FEN.STARTING_POSITION));
            }
        });
        this._mapItemAction.put(2131296986, new Runnable(){

            @Override
            public void run() {
                SetupBoardNavigationBarController.this.showSubmenu((View)SetupBoardNavigationBarController.this._radioGroupActiveColor);
                RadioGroup radioGroup = SetupBoardNavigationBarController.this._radioGroupActiveColor;
                int n = SetupBoardNavigationBarController.this._positionHolder.getPosition().getActiveColor() ? 2131296983 : 2131296982;
                radioGroup.check(n);
            }
        });
        this._mapItemAction.put(2131296987, new Runnable(){

            @Override
            public void run() {
                SetupBoardNavigationBarController.this.updateCastlingView();
                SetupBoardNavigationBarController.this.showSubmenu(SetupBoardNavigationBarController.this._castlingView);
            }
        });
        this._mapItemAction.put(2131296989, new Runnable(){

            @Override
            public void run() {
                SetupBoardNavigationBarController.this.updateEnpassantView(SetupBoardNavigationBarController.this._positionHolder.getPosition());
                SetupBoardNavigationBarController.this.showSubmenu((View)SetupBoardNavigationBarController.this._enpassantView);
            }
        });
    }

    private void checkForCastling(boolean bl, Piece piece, Piece piece2, Piece piece3, CheckBox checkBox, CheckBox checkBox2, ModifyablePosition modifyablePosition) {
        boolean bl2 = King.instanceForColor(bl).equals(piece2);
        boolean bl3 = false;
        bl2 = bl2 && Rook.instanceForColor(bl).equals(piece3);
        boolean bl4 = King.instanceForColor(bl).equals(piece2) && Rook.instanceForColor(bl).equals(piece);
        boolean bl5 = bl2 && modifyablePosition.isCastlingPossible(bl, true);
        boolean bl6 = bl3;
        if (bl4) {
            bl6 = bl3;
            if (modifyablePosition.isCastlingPossible(bl, false)) {
                bl6 = true;
            }
        }
        checkBox.setChecked(bl6);
        checkBox.setEnabled(bl4);
        checkBox2.setChecked(bl5);
        checkBox2.setEnabled(bl2);
        if (bl) {
            modifyablePosition.setWhiteKingSideCastling(bl5);
            modifyablePosition.setWhiteQueenSideCastling(bl6);
            return;
        }
        modifyablePosition.setBlackKingSideCastling(bl5);
        modifyablePosition.setBlackQueenSideCastling(bl6);
    }

    private void showSubmenu(View view) {
        if (view.equals((Object)this._subMenuActive)) {
            this.hidePopups();
            return;
        }
        this._subMenuActive = view;
        this._submenuView.removeAllViews();
        this._submenuView.addView(view);
        this._menuBar.showPopup(this._submenuView);
    }

    private void updateCastlingView() {
        Position position = this._positionHolder.getPosition();
        Piece piece = position.getPieceFor(Square.SQUARE_A1);
        Piece piece2 = position.getPieceFor(Square.SQUARE_E1);
        Piece piece3 = position.getPieceFor(Square.SQUARE_H1);
        Piece piece4 = position.getPieceFor(Square.SQUARE_A8);
        Piece piece5 = position.getPieceFor(Square.SQUARE_E8);
        Piece piece6 = position.getPieceFor(Square.SQUARE_H8);
        ModifyablePosition modifyablePosition = new ModifyablePosition(position);
        this.checkForCastling(true, piece, piece2, piece3, this._checkCastlingWhite000, this._checkCastlingWhite00, modifyablePosition);
        this.checkForCastling(false, piece4, piece5, piece6, this._checkCastlingBlack000, this._checkCastlingBlack00, modifyablePosition);
        if (!modifyablePosition.equals(position)) {
            this._positionHolder.setPosition(modifyablePosition.getSafePosition());
        }
    }

    private void updateEnpassantView(Position position) {
        int n;
        this._enpassantView.removeAllViews();
        Square square = position.getEnPassantSquare();
        boolean bl = position.getActiveColor();
        Pawn pawn = Pawn.instanceForColor(bl ^ true);
        int n2 = bl ? 4 : 3;
        int n3 = bl ? 5 : 2;
        int n4 = n = 0;
        do {
            int n5 = 1;
            if (n >= 8) break;
            int n6 = n4;
            if (pawn.equals(position.getPieceFor(Square.instanceForRowAndColumn(n2, n)))) {
                Square square2 = Square.instanceForRowAndColumn(n3, n);
                bl = square2.equals(square);
                this._enpassantView.addEnpassant(square2, bl);
                n6 = n5;
                if (n4 == 0) {
                    n6 = bl ? n5 : 0;
                }
            }
            ++n;
            n4 = n6;
        } while (true);
        this._enpassantView.addNoEnpassantOption((boolean)(n4 ^ 1));
    }

    public void hidePopups() {
        this._menuBar.hidePopup();
        this._subMenuActive = null;
    }

    @Override
    public void menuItemClicked(MenuBarItem object) {
        if ((object = this._mapItemAction.get(object.getId())) != null) {
            object.run();
        }
    }

    @Override
    public void menuItemLongClicked(MenuBarItem menuBarItem) {
    }

    @Override
    public void positionChanged(Position position, Move move) {
        if (!this._doNotClosePopup) {
            this.hidePopups();
        }
        this._doNotClosePopup = false;
        this.updateCastlingView();
    }

}
