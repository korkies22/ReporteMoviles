/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 */
package de.cisha.android.board.setup;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.mainmenu.AbstractMainMenuActivity;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.setup.SetupBoardNavigationBarController;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.android.board.setup.view.DragBoardView;
import de.cisha.android.board.setup.view.DragBoardViewFactory;
import de.cisha.android.board.setup.view.PieceBar;
import de.cisha.android.board.setup.view.PieceView;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.DragView;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.chess.model.position.validator.ExistingKingsValidator;
import de.cisha.chess.model.position.validator.IPositionRuleValidator;
import de.cisha.chess.model.position.validator.IPositionValidator;
import de.cisha.chess.model.position.validator.InactiveKingNotCheckedValidator;
import de.cisha.chess.model.position.validator.PawnInFirstOrLastRowValidator;
import de.cisha.chess.model.position.validator.PositionValidator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SetupBoardActivity
extends AbstractMainMenuActivity
implements DragBoardView.DragDelegate,
PositionObserver {
    public static final String CURRENT_BOARD_FEN_STRING = "de.cisha.android.board.setup.FEN_STRING";
    private static final String SETUP_BOARD_INSTANCE_STATE_FEN_KEY = "SETUP_BOARD_POSITION_KEY";
    private Piece _actualPiece;
    private DragBoardView _boardView;
    private boolean _deleteMode = false;
    private Piece _dragPiece;
    private DragView _dragView;
    private boolean _isValidPosition;
    private View _landscapeDeleteButton;
    private SetupBoardNavigationBarController _navigationBarController;
    private PieceBar _pieceBarBlack;
    private PieceBar _pieceBarWhite;
    private Position _position;
    private PositionHolder _positionHolder;
    private IPositionValidator _positionValidator;

    private void enableDeleteMode(boolean bl) {
        this._deleteMode = bl;
        this._pieceBarBlack.enableDeleteMode(bl);
        this._pieceBarWhite.enableDeleteMode(bl);
        if (this._landscapeDeleteButton != null) {
            this._landscapeDeleteButton.setSelected(bl);
        }
        this._actualPiece = null;
    }

    private void setActualPiece(Piece piece) {
        this.enableDeleteMode(false);
        this._pieceBarBlack.selectPiece(piece);
        this._pieceBarWhite.selectPiece(piece);
        this._actualPiece = piece;
    }

    private void updateSaveButton() {
        this._isValidPosition = this._positionValidator.verify(this._position, (Context)this);
        AbstractMainMenuActivity.ButtonType buttonType = this._isValidPosition ? AbstractMainMenuActivity.ButtonType.SAVE_VALID : AbstractMainMenuActivity.ButtonType.SAVE_INVALID;
        this.setTopButton(buttonType, false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this._dragView.handleTouchEvent(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    protected MenuItem getHiglightedMenuItem() {
        return null;
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2131427366);
        this.setHeaderText(this.getString(2131690292));
        this.setTopButton(AbstractMainMenuActivity.ButtonType.CLOSE, true);
        this.setTopButtonClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SetupBoardActivity.this.finish();
            }
        }, true);
        this.setTopButtonClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (SetupBoardActivity.this._isValidPosition) {
                    object = SetupBoardActivity.this._boardView.getPosition().getFEN();
                    Intent intent = new Intent();
                    intent.putExtra(SetupBoardActivity.CURRENT_BOARD_FEN_STRING, object.getFENString());
                    SetupBoardActivity.this.setResult(-1, intent);
                    SetupBoardActivity.this.finish();
                    return;
                }
                SimpleOkDialogFragment simpleOkDialogFragment = new SimpleOkDialogFragment();
                simpleOkDialogFragment.setTitle(SetupBoardActivity.this.getString(2131690291));
                object = "";
                List<String> list = SetupBoardActivity.this._positionValidator.getErrorMessages();
                for (int i = 0; i < list.size(); ++i) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object);
                    stringBuilder.append(list.get(i));
                    object = i < list.size() - 1 ? "\n\n" : "";
                    stringBuilder.append((String)object);
                    object = stringBuilder.toString();
                }
                simpleOkDialogFragment.setText((CharSequence)object);
                simpleOkDialogFragment.show(SetupBoardActivity.this.getSupportFragmentManager(), "");
            }
        }, false);
        this._boardView = (DragBoardView)DragBoardViewFactory.getInstance().createBoardView((Context)this, this.getSupportFragmentManager(), true, false, false);
        ((FieldView)this.findViewById(2131296984)).setBoardView(this._boardView);
        this._pieceBarWhite = (PieceBar)this.findViewById(2131297006);
        this._pieceBarBlack = (PieceBar)this.findViewById(2131296995);
        this._pieceBarBlack.setPieceColor(true, false);
        Object object2 = new View.OnClickListener(){

            public void onClick(View view) {
                SetupBoardActivity.this.enableDeleteMode(SetupBoardActivity.this._deleteMode ^ true);
            }
        };
        this._pieceBarWhite.findViewById(2131296996).setOnClickListener((View.OnClickListener)object2);
        this._pieceBarBlack.findViewById(2131296996).setOnClickListener((View.OnClickListener)object2);
        if (this.getResources().getConfiguration().orientation == 1) {
            int n = this.getResources().getDisplayMetrics().heightPixels;
            int n2 = this.getResources().getDisplayMetrics().widthPixels;
            n2 = (float)n / (float)n2 < 1.6666666f ? 1 : 0;
            float f = this.getResources().getDisplayMetrics().density;
            if (n2 != 0 && n < 500 || (double)f <= 1.0) {
                this._pieceBarBlack.setVisibility(8);
                this._pieceBarWhite.setMultipleColorEnabled(true);
            }
        } else {
            this._pieceBarBlack.hideTrash();
            this._pieceBarWhite.hideTrash();
            this._landscapeDeleteButton = this.findViewById(2131296997);
            this._landscapeDeleteButton.setOnClickListener((View.OnClickListener)object2);
        }
        object2 = new View.OnTouchListener(){

            public boolean onTouch(View object, MotionEvent object2) {
                switch (object2.getAction()) {
                    default: {
                        break;
                    }
                    case 1: {
                        object = SetupBoardActivity.this._boardView.getSquareForRawCoordinates(object2.getRawX(), object2.getRawY());
                        object2 = new ModifyablePosition(SetupBoardActivity.this._position);
                        object2.setPiece(SetupBoardActivity.this._actualPiece, (Square)object);
                        SetupBoardActivity.this._positionHolder.setPosition(object2.getSafePosition());
                        break;
                    }
                    case 0: {
                        SetupBoardActivity.this._dragView.startDrag((View)object);
                        SetupBoardActivity.this.setActualPiece(((PieceView)((Object)object)).getPiece());
                    }
                }
                return true;
            }
        };
        Iterator<View> iterator = this._pieceBarWhite.getAllPieceViews().iterator();
        while (iterator.hasNext()) {
            iterator.next().setOnTouchListener((View.OnTouchListener)object2);
        }
        iterator = this._pieceBarBlack.getAllPieceViews().iterator();
        while (iterator.hasNext()) {
            iterator.next().setOnTouchListener((View.OnTouchListener)object2);
        }
        this._dragView = new DragView(this.getBaseContext());
        ((ViewGroup)this._pieceBarWhite.getRootView()).addView((View)this._dragView, -1, -1);
        this._boardView.setDragDelegate(this);
        this._boardView.setMovable(false);
        object2 = new GameHolder(new Game());
        object2.setNewGame(new Game());
        this._boardView.setMoveExecutor((MoveExecutor)object2);
        this._positionHolder = new PositionHolder();
        this._positionHolder.addPositionObserver(this._boardView);
        this._position = this._positionHolder.getPosition();
        this._boardView.setPosition(this._position);
        this._positionValidator = new PositionValidator();
        this._positionValidator.setRuleSet(ExistingKingsValidator.class, PawnInFirstOrLastRowValidator.class, InactiveKingNotCheckedValidator.class);
        this.updateSaveButton();
        this._positionHolder.addPositionObserver(this);
        this._navigationBarController = new SetupBoardNavigationBarController((MenuBar)this.findViewById(2131296985), this._positionHolder);
        if (object != null && object.getCharSequence(SETUP_BOARD_INSTANCE_STATE_FEN_KEY) != null) {
            object = object.getCharSequence(SETUP_BOARD_INSTANCE_STATE_FEN_KEY).toString();
            this._positionHolder.setPosition(new Position(new FEN((String)object)));
        } else if (this.getIntent().hasExtra(CURRENT_BOARD_FEN_STRING)) {
            object = this.getIntent().getStringExtra(CURRENT_BOARD_FEN_STRING);
            this._positionHolder.setPosition(new Position(new FEN((String)object)));
        }
        this._actualPiece = null;
    }

    @Override
    protected void onDestroy() {
        this._boardView.clearCachedBitmaps();
        this._dragView.destroy();
        super.onDestroy();
    }

    @Override
    public void onDragStart() {
        if (this._dragPiece != null) {
            this._pieceBarWhite.showDragDeleteHint(true);
            this._pieceBarBlack.showDragDeleteHint(true);
        }
    }

    @Override
    public void onPieceDown(Piece serializable, Square square, View view) {
        this._dragPiece = serializable;
        this._dragView.startDrag(view);
        serializable = new ModifyablePosition(this._position);
        serializable.setPiece(null, square);
        this._positionHolder.setPosition(serializable.getSafePosition());
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        if (this._position != null) {
            bundle.putCharSequence(SETUP_BOARD_INSTANCE_STATE_FEN_KEY, (CharSequence)this._position.getFEN().getFENString());
        }
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onSquareUp(Square square, boolean bl) {
        if (!this._deleteMode || bl) {
            ModifyablePosition modifyablePosition = new ModifyablePosition(this._position);
            if (this._deleteMode && !bl) {
                modifyablePosition.setPiece(null, square);
            } else if (!bl) {
                Piece piece = this._actualPiece != null ? this._actualPiece : this._dragPiece;
                modifyablePosition.setPiece(piece, square);
            } else if (this._dragPiece != null) {
                modifyablePosition.setPiece(this._dragPiece, square);
            }
            if (!this._position.equals(modifyablePosition) || Build.VERSION.SDK_INT < 11) {
                this._positionHolder.setPosition(modifyablePosition.getSafePosition());
            }
        }
        this._dragPiece = null;
        this._navigationBarController.hidePopups();
        this._pieceBarWhite.showDragDeleteHint(false);
        this._pieceBarBlack.showDragDeleteHint(false);
    }

    @Override
    protected void onStop() {
        this._boardView.clearCachedBitmaps();
        super.onStop();
    }

    @Override
    public void positionChanged(Position position, Move move) {
        this._position = position;
        this.updateSaveButton();
    }

    @Override
    protected boolean showMenu() {
        return false;
    }

}
