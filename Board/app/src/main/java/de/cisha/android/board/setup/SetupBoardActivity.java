// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup;

import de.cisha.chess.model.Move;
import android.os.Build.VERSION;
import java.util.Iterator;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.chess.model.position.validator.IPositionRuleValidator;
import de.cisha.chess.model.position.validator.InactiveKingNotCheckedValidator;
import de.cisha.chess.model.position.validator.PawnInFirstOrLastRowValidator;
import de.cisha.chess.model.position.validator.ExistingKingsValidator;
import de.cisha.chess.model.position.validator.PositionValidator;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Game;
import android.view.ViewGroup;
import de.cisha.chess.model.Square;
import de.cisha.android.board.setup.view.PieceView;
import de.cisha.chess.model.ModifyablePosition;
import android.view.View.OnTouchListener;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.board.setup.view.DragBoardViewFactory;
import java.util.List;
import de.cisha.chess.model.FEN;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.view.MotionEvent;
import android.content.Context;
import de.cisha.chess.model.position.validator.IPositionValidator;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.chess.model.position.Position;
import de.cisha.android.board.setup.view.PieceBar;
import android.view.View;
import de.cisha.android.board.view.DragView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.android.board.setup.view.DragBoardView;
import de.cisha.android.board.mainmenu.AbstractMainMenuActivity;

public class SetupBoardActivity extends AbstractMainMenuActivity implements DragDelegate, PositionObserver
{
    public static final String CURRENT_BOARD_FEN_STRING = "de.cisha.android.board.setup.FEN_STRING";
    private static final String SETUP_BOARD_INSTANCE_STATE_FEN_KEY = "SETUP_BOARD_POSITION_KEY";
    private Piece _actualPiece;
    private DragBoardView _boardView;
    private boolean _deleteMode;
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
    
    public SetupBoardActivity() {
        this._deleteMode = false;
    }
    
    private void enableDeleteMode(final boolean b) {
        this._deleteMode = b;
        this._pieceBarBlack.enableDeleteMode(b);
        this._pieceBarWhite.enableDeleteMode(b);
        if (this._landscapeDeleteButton != null) {
            this._landscapeDeleteButton.setSelected(b);
        }
        this._actualPiece = null;
    }
    
    private void setActualPiece(final Piece actualPiece) {
        this.enableDeleteMode(false);
        this._pieceBarBlack.selectPiece(actualPiece);
        this._pieceBarWhite.selectPiece(actualPiece);
        this._actualPiece = actualPiece;
    }
    
    private void updateSaveButton() {
        this._isValidPosition = this._positionValidator.verify(this._position, (Context)this);
        ButtonType buttonType;
        if (this._isValidPosition) {
            buttonType = ButtonType.SAVE_VALID;
        }
        else {
            buttonType = ButtonType.SAVE_INVALID;
        }
        this.setTopButton(buttonType, false);
    }
    
    @Override
    public boolean dispatchTouchEvent(final MotionEvent motionEvent) {
        this._dragView.handleTouchEvent(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }
    
    @Override
    protected MenuItem getHiglightedMenuItem() {
        return null;
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427366);
        this.setHeaderText(this.getString(2131690292));
        this.setTopButton(ButtonType.CLOSE, true);
        this.setTopButtonClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                SetupBoardActivity.this.finish();
            }
        }, true);
        this.setTopButtonClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (SetupBoardActivity.this._isValidPosition) {
                    final FEN fen = SetupBoardActivity.this._boardView.getPosition().getFEN();
                    final Intent intent = new Intent();
                    intent.putExtra("de.cisha.android.board.setup.FEN_STRING", fen.getFENString());
                    SetupBoardActivity.this.setResult(-1, intent);
                    SetupBoardActivity.this.finish();
                    return;
                }
                final SimpleOkDialogFragment simpleOkDialogFragment = new SimpleOkDialogFragment();
                simpleOkDialogFragment.setTitle(SetupBoardActivity.this.getString(2131690291));
                String string = "";
                final List<String> errorMessages = SetupBoardActivity.this._positionValidator.getErrorMessages();
                for (int i = 0; i < errorMessages.size(); ++i) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(string);
                    sb.append(errorMessages.get(i));
                    String s;
                    if (i < errorMessages.size() - 1) {
                        s = "\n\n";
                    }
                    else {
                        s = "";
                    }
                    sb.append(s);
                    string = sb.toString();
                }
                simpleOkDialogFragment.setText(string);
                simpleOkDialogFragment.show(SetupBoardActivity.this.getSupportFragmentManager(), "");
            }
        }, false);
        this._boardView = DragBoardViewFactory.getInstance().createBoardView((Context)this, this.getSupportFragmentManager(), true, false, false);
        ((FieldView)this.findViewById(2131296984)).setBoardView(this._boardView);
        this._pieceBarWhite = (PieceBar)this.findViewById(2131297006);
        (this._pieceBarBlack = (PieceBar)this.findViewById(2131296995)).setPieceColor(true, false);
        final View.OnClickListener onClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                SetupBoardActivity.this.enableDeleteMode(SetupBoardActivity.this._deleteMode ^ true);
            }
        };
        this._pieceBarWhite.findViewById(2131296996).setOnClickListener((View.OnClickListener)onClickListener);
        this._pieceBarBlack.findViewById(2131296996).setOnClickListener((View.OnClickListener)onClickListener);
        if (this.getResources().getConfiguration().orientation == 1) {
            final int heightPixels = this.getResources().getDisplayMetrics().heightPixels;
            final boolean b = heightPixels / this.getResources().getDisplayMetrics().widthPixels < 1.6666666f;
            final float density = this.getResources().getDisplayMetrics().density;
            if ((b && heightPixels < 500) || density <= 1.0) {
                this._pieceBarBlack.setVisibility(8);
                this._pieceBarWhite.setMultipleColorEnabled(true);
            }
        }
        else {
            this._pieceBarBlack.hideTrash();
            this._pieceBarWhite.hideTrash();
            (this._landscapeDeleteButton = this.findViewById(2131296997)).setOnClickListener((View.OnClickListener)onClickListener);
        }
        final View.OnTouchListener view.OnTouchListener = (View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 1: {
                        final Square squareForRawCoordinates = SetupBoardActivity.this._boardView.getSquareForRawCoordinates(motionEvent.getRawX(), motionEvent.getRawY());
                        final ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardActivity.this._position);
                        modifyablePosition.setPiece(SetupBoardActivity.this._actualPiece, squareForRawCoordinates);
                        SetupBoardActivity.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
                        break;
                    }
                    case 0: {
                        SetupBoardActivity.this._dragView.startDrag(view);
                        SetupBoardActivity.this.setActualPiece(((PieceView)view).getPiece());
                        break;
                    }
                }
                return true;
            }
        };
        final Iterator<View> iterator = this._pieceBarWhite.getAllPieceViews().iterator();
        while (iterator.hasNext()) {
            iterator.next().setOnTouchListener((View.OnTouchListener)view.OnTouchListener);
        }
        final Iterator<View> iterator2 = this._pieceBarBlack.getAllPieceViews().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().setOnTouchListener((View.OnTouchListener)view.OnTouchListener);
        }
        this._dragView = new DragView(this.getBaseContext());
        ((ViewGroup)this._pieceBarWhite.getRootView()).addView((View)this._dragView, -1, -1);
        this._boardView.setDragDelegate((DragBoardView.DragDelegate)this);
        this._boardView.setMovable(false);
        final GameHolder moveExecutor = new GameHolder(new Game());
        moveExecutor.setNewGame(new Game());
        this._boardView.setMoveExecutor(moveExecutor);
        (this._positionHolder = new PositionHolder()).addPositionObserver(this._boardView);
        this._position = this._positionHolder.getPosition();
        this._boardView.setPosition(this._position);
        (this._positionValidator = new PositionValidator()).setRuleSet(ExistingKingsValidator.class, PawnInFirstOrLastRowValidator.class, InactiveKingNotCheckedValidator.class);
        this.updateSaveButton();
        this._positionHolder.addPositionObserver(this);
        this._navigationBarController = new SetupBoardNavigationBarController((MenuBar)this.findViewById(2131296985), this._positionHolder);
        if (bundle != null && bundle.getCharSequence("SETUP_BOARD_POSITION_KEY") != null) {
            this._positionHolder.setPosition(new Position(new FEN(bundle.getCharSequence("SETUP_BOARD_POSITION_KEY").toString())));
        }
        else if (this.getIntent().hasExtra("de.cisha.android.board.setup.FEN_STRING")) {
            this._positionHolder.setPosition(new Position(new FEN(this.getIntent().getStringExtra("de.cisha.android.board.setup.FEN_STRING"))));
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
    public void onPieceDown(final Piece dragPiece, final Square square, final View view) {
        this._dragPiece = dragPiece;
        this._dragView.startDrag(view);
        final ModifyablePosition modifyablePosition = new ModifyablePosition(this._position);
        modifyablePosition.setPiece(null, square);
        this._positionHolder.setPosition(modifyablePosition.getSafePosition());
    }
    
    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        if (this._position != null) {
            bundle.putCharSequence("SETUP_BOARD_POSITION_KEY", (CharSequence)this._position.getFEN().getFENString());
        }
        super.onSaveInstanceState(bundle);
    }
    
    @Override
    public void onSquareUp(final Square square, final boolean b) {
        if (!this._deleteMode || b) {
            final ModifyablePosition modifyablePosition = new ModifyablePosition(this._position);
            if (this._deleteMode && !b) {
                modifyablePosition.setPiece(null, square);
            }
            else if (!b) {
                Piece piece;
                if (this._actualPiece != null) {
                    piece = this._actualPiece;
                }
                else {
                    piece = this._dragPiece;
                }
                modifyablePosition.setPiece(piece, square);
            }
            else if (this._dragPiece != null) {
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
    public void positionChanged(final Position position, final Move move) {
        this._position = position;
        this.updateSaveButton();
    }
    
    @Override
    protected boolean showMenu() {
        return false;
    }
}
