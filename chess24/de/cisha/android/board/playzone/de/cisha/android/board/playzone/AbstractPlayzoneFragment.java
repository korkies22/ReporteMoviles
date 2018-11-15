/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.AbstractNetworkContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.PremiumIndicatorManager;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.android.board.playzone.view.MoveConfirmationView;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.board.view.MoveListView;
import de.cisha.android.board.view.TakenPiecesView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.chess.util.Logger;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractPlayzoneFragment
extends AbstractNetworkContentFragment
implements IGameModelDelegate {
    private View _afterGameView;
    private View _beforeGameView;
    private BoardView _boardView;
    private View _buttonDrawOffer;
    private View _buttonDrawOfferDivider;
    private View _buttonDrawOfferShaddow;
    private TextView _buttonDrawOfferText;
    private View _buttonResign;
    private TextView _buttonResignText;
    private ChessClockView _chessClockView;
    private boolean _clockActiveColor = true;
    private boolean _clockRunning = false;
    private PlayzoneState _currentState;
    private TextView _eloBlack;
    private TextView _eloWhite;
    private FieldView _fieldView;
    private ImageView _flagImageBlack;
    private ImageView _flagImageWhite;
    protected ViewGroup _fragmentView;
    private AbstractGameModel _gameAdapter;
    private MoveConfirmationView _moveConfirmationView;
    private MoveDelayingMoveExcutor _moveExecutor;
    private MoveListView _moveListView;
    private TextView _nameBlack;
    private TextView _nameWhite;
    private RookieInfoDialogFragment _rookieDialog;
    private TakenPiecesView _takenPieceViewBlack;
    private TakenPiecesView _takenPieceViewWhite;

    private void dismissResignDialog() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._rookieDialog != null) {
                    AbstractPlayzoneFragment.this._rookieDialog.dismiss();
                    AbstractPlayzoneFragment.this._rookieDialog = null;
                }
            }
        });
    }

    private int getColorFromResources(int n) {
        int n2 = Color.rgb((int)68, (int)68, (int)68);
        if (this.isAdded()) {
            return this.getResources().getColor(n);
        }
        Logger.getInstance().error(this.getClass().getName(), "Error: calling getColor without being attached to activity - returning standard grey");
        return n2;
    }

    private void hideAfterGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._afterGameView != null) {
                    AbstractPlayzoneFragment.this._fragmentView.removeView(AbstractPlayzoneFragment.this._afterGameView);
                }
                AbstractPlayzoneFragment.this._afterGameView = null;
            }
        });
    }

    private void hideBeforeGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._fragmentView != null) {
                    if (AbstractPlayzoneFragment.this._beforeGameView != null) {
                        AbstractPlayzoneFragment.this._fragmentView.removeView(AbstractPlayzoneFragment.this._beforeGameView);
                    }
                    AbstractPlayzoneFragment.this._beforeGameView = null;
                }
            }
        });
    }

    private void hideMoveConfirmationView() {
        this._moveConfirmationView.setVisibility(8);
    }

    private void initBoard() {
        if (this._gameAdapter != null) {
            boolean bl = this._gameAdapter.playerIsWhite();
            this._boardView.flip(bl);
            this._fieldView.flip(bl);
            Position position = this._gameAdapter.getPosition();
            this._boardView.setPosition(position);
        }
    }

    private void initListener() {
        if (this._gameAdapter != null) {
            this._gameAdapter.getPositionObservable().addPositionObserver(this._boardView);
            this._boardView.setMoveExecutor(this._moveExecutor);
            this._moveConfirmationView.setMoveConfirmationDelegate(this._moveExecutor);
            this._moveListView.setMoveSelector(this._gameAdapter.getMoveExecutor());
            this._gameAdapter.getMovesObservable().addMovesObserver(this._moveListView);
            this._gameAdapter.getPositionObservable().addPositionObserver(this._takenPieceViewBlack);
            this._gameAdapter.getPositionObservable().addPositionObserver(this._takenPieceViewWhite);
        }
    }

    private void initViews() {
        this._boardView.postDelayed(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        if (AbstractPlayzoneFragment.this._gameAdapter != null) {
                            AbstractPlayzoneFragment.this.initBoard();
                            AbstractPlayzoneFragment.this.initListener();
                            AbstractPlayzoneFragment.this.updateUI();
                        }
                        AbstractPlayzoneFragment.this.updateUIOnce();
                    }
                });
            }

        }, 10L);
    }

    private void removeListener() {
        if (this._gameAdapter != null) {
            if (this._gameAdapter.getPositionObservable() != null) {
                this._gameAdapter.getPositionObservable().removePositionObserver(this._boardView);
                this._gameAdapter.getMovesObservable().removeMovesObserver(this._moveListView);
                this._gameAdapter.getPositionObservable().removePositionObserver(this._takenPieceViewWhite);
                this._gameAdapter.getPositionObservable().removePositionObserver(this._takenPieceViewBlack);
            }
            this._gameAdapter.removeDelegate();
        }
    }

    private void setClockRunning(final boolean bl) {
        this._clockRunning = bl;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.getChessClockView().setRunning(bl);
            }
        });
    }

    private void setResultForClockView(GameStatus gameStatus) {
        if (gameStatus != null && gameStatus.getResult() != GameResult.NO_RESULT) {
            this.getChessClockView().setGameResult(gameStatus.getResult());
        }
    }

    private void showAfterGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._afterGameView == null) {
                    AbstractPlayzoneFragment.this._afterGameView = AbstractPlayzoneFragment.this.getAfterGameView();
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
                    AbstractPlayzoneFragment.this._fragmentView.addView(AbstractPlayzoneFragment.this._afterGameView, layoutParams);
                }
                AbstractPlayzoneFragment.this._afterGameView.bringToFront();
            }
        });
    }

    private void showBeforeGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._beforeGameView != null) {
                    AbstractPlayzoneFragment.this._fragmentView.removeView(AbstractPlayzoneFragment.this._beforeGameView);
                    AbstractPlayzoneFragment.this._beforeGameView = null;
                }
                if (AbstractPlayzoneFragment.this._beforeGameView == null) {
                    AbstractPlayzoneFragment.this._beforeGameView = AbstractPlayzoneFragment.this.getBeforeGameView();
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
                    AbstractPlayzoneFragment.this._fragmentView.addView(AbstractPlayzoneFragment.this._beforeGameView, layoutParams);
                    AbstractPlayzoneFragment.this._beforeGameView.setVisibility(0);
                }
                AbstractPlayzoneFragment.this._beforeGameView.bringToFront();
            }
        });
    }

    private void showMoveConfirmationView(Move move) {
        this._moveConfirmationView.setVisibility(0);
        this._moveConfirmationView.setMoveToShow(move);
        this._moveConfirmationView.bringToFront();
    }

    private void updateUI() {
        this.getChessClockView().setRunning(this._clockRunning);
        this.getChessClockView().setActiveClockMark(this._clockActiveColor);
        boolean bl = this._gameAdapter.isAbortable() ^ true;
        this._buttonDrawOffer.setClickable(bl);
        View view = this._buttonDrawOffer;
        int n = this._gameAdapter.opponentsDrawOfferPending() ? 2131231563 : 2131231562;
        view.setBackgroundResource(n);
        view = this._buttonDrawOfferDivider;
        n = this._gameAdapter.opponentsDrawOfferPending() ? 2131099807 : 2131099806;
        view.setBackgroundColor(this.getColorFromResources(n));
        view = this._buttonDrawOfferShaddow;
        n = this._gameAdapter.opponentsDrawOfferPending() ? 4 : 0;
        view.setVisibility(n);
        this._buttonDrawOffer.setSelected(this._gameAdapter.playersDrawOfferPending());
        view = this._buttonResignText;
        n = bl ? 2131690267 : 2131689502;
        view.setText(n);
        view = this._buttonDrawOfferText;
        n = this._gameAdapter.opponentsDrawOfferPending() ? 2131689503 : 2131690097;
        view.setText(n);
        n = bl ? 2131099809 : 2131099808;
        view = this._buttonDrawOfferText;
        if (this._gameAdapter.playersDrawOfferPending()) {
            n = 2131099810;
        }
        view.setTextColor(this.getColorFromResources(n));
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            this._buttonDrawOfferText.setTextColor(this.getColorFromResources(2131099811));
        }
        n = bl ? 2131231573 : 2131231574;
        view = this._buttonDrawOfferText;
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            n = 2131231575;
        } else if (this._gameAdapter.playersDrawOfferPending()) {
            n = 2131231572;
        }
        view.setCompoundDrawablesWithIntrinsicBounds(n, 0, 0, 0);
        if (!this._gameAdapter.isGameActive()) {
            this._buttonDrawOfferText.setTextColor(this.getColorFromResources(2131099808));
            this._buttonDrawOffer.setBackgroundResource(2131231562);
            this._buttonDrawOffer.setSelected(false);
            this._buttonDrawOfferDivider.setBackgroundColor(this.getColorFromResources(2131099806));
            this._buttonDrawOfferShaddow.setVisibility(0);
        }
    }

    private void updateUIOnce() {
        Object object;
        int n;
        boolean bl = this._gameAdapter != null && this._gameAdapter.isGameActive();
        this._moveListView.setNavigationSelectionEnabled(bl ^ true);
        if (this._gameAdapter != null) {
            Opponent opponent = this._gameAdapter.getPlayerInfo(true);
            object = opponent.getName();
            this._nameWhite.setText((CharSequence)object);
            Object object2 = this._eloWhite;
            StringBuilder stringBuilder = new StringBuilder();
            if (!opponent.hasTitle()) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append("(");
                object.append(opponent.getTitle());
                object.append(") ");
                object = object.toString();
            }
            stringBuilder.append((String)object);
            object = opponent.hasRating() ? Integer.valueOf(opponent.getRating().getRating()) : "-";
            stringBuilder.append(object);
            object2.setText((CharSequence)stringBuilder.toString());
            object = this._flagImageWhite;
            object2 = opponent.getCountry();
            int n2 = 2131231366;
            n = object2 != null ? opponent.getCountry().getImageId() : 2131231366;
            object.setImageResource(n);
            object2 = this._gameAdapter.getPlayerInfo(false);
            this._nameBlack.setText((CharSequence)object2.getName());
            PremiumIndicatorManager.showPremiumIndicatorBesideTextView(object2.isPremium(), this._nameBlack);
            PremiumIndicatorManager.showPremiumIndicatorBesideTextView(opponent.isPremium(), this._nameWhite);
            opponent = this._eloBlack;
            stringBuilder = new StringBuilder();
            if (!object2.hasTitle()) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append("(");
                object.append(object2.getTitle());
                object.append(") ");
                object = object.toString();
            }
            stringBuilder.append((String)object);
            object = object2.hasRating() ? Integer.valueOf(object2.getRating().getRating()) : "-";
            stringBuilder.append(object);
            opponent.setText((CharSequence)stringBuilder.toString());
            object = this._flagImageBlack;
            n = n2;
            if (object2.getCountry() != null) {
                n = object2.getCountry().getImageId();
            }
            object.setImageResource(n);
            this._moveListView.allMovesChanged(this._gameAdapter.getRootMoveContainer());
            n = this._gameAdapter.getPosition().getHalfMoveNumber() >= 2 ? 1 : 0;
            this._clockActiveColor = this._gameAdapter.getPosition().getActiveColor();
        } else {
            n = 0;
        }
        this._buttonDrawOffer.setClickable(bl);
        n = n != 0 ? 2131231573 : 2131231574;
        object = this._buttonDrawOfferText;
        if (!bl) {
            n = 2131231573;
        }
        object.setCompoundDrawablesWithIntrinsicBounds(n, 0, 0, 0);
        this._boardView.setMovable(bl);
        this._boardView.setPremovable(bl);
        this._buttonResignText.setCompoundDrawablesWithIntrinsicBounds(2131231592, 0, 0, 0);
        this._buttonResign.setClickable(bl);
        n = bl ? 2131099809 : 2131099808;
        this._buttonResignText.setTextColor(this.getColorFromResources(n));
        if (bl) {
            bl = this._gameAdapter.playerIsWhite();
            this._boardView.setWhiteMoveable(bl);
            this._boardView.setBlackMoveable(bl ^ true);
            this._boardView.setWhitePreMoveable(bl);
            this._boardView.setBlackPreMoveable(bl ^ true);
            this._moveExecutor.restoreState();
        }
    }

    public void gameChosen(AbstractGameModel abstractGameModel) {
        if (this._gameAdapter != null && !this._gameAdapter.isGameActive()) {
            this._gameAdapter.destroy();
        }
        this._gameAdapter = abstractGameModel;
        this._gameAdapter.startUp();
        this.initViews();
    }

    protected abstract View getAfterGameView();

    protected abstract View getBeforeGameView();

    protected ChessClockView getChessClockView() {
        return this._chessClockView;
    }

    public AbstractGameModel getGameAdapter() {
        return this._gameAdapter;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        HashSet<SettingsMenuCategory> hashSet = new HashSet<SettingsMenuCategory>();
        hashSet.add(SettingsMenuCategory.PLAYZONE);
        hashSet.add(SettingsMenuCategory.BOARD);
        return hashSet;
    }

    protected boolean hasRunningGame() {
        if (this.getGameAdapter() != null && this.getGameAdapter().isGameActive()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }

    @Override
    public void onClockChanged(final long l, final boolean bl) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.getChessClockView().setTime(bl, l);
            }
        });
    }

    @Override
    public void onClockFlag(boolean bl) {
    }

    @Override
    public void onClockStarted() {
        this.setClockRunning(true);
    }

    @Override
    public void onClockStopped() {
        this.setClockRunning(false);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._moveExecutor = new MoveDelayingMoveExcutor();
        this.addStateHolder(this._moveExecutor);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this._fragmentView = (ViewGroup)layoutInflater.inflate(2131427362, viewGroup, false);
        this._eloBlack = (TextView)this._fragmentView.findViewById(2131296720);
        this._eloWhite = (TextView)this._fragmentView.findViewById(2131296721);
        this._flagImageWhite = (ImageView)this._fragmentView.findViewById(2131296798);
        this._flagImageBlack = (ImageView)this._fragmentView.findViewById(2131296695);
        this._takenPieceViewBlack = (TakenPiecesView)this._fragmentView.findViewById(2131296777);
        this._takenPieceViewBlack.setColor(false);
        this._takenPieceViewWhite = (TakenPiecesView)this._fragmentView.findViewById(2131296778);
        this._moveListView = (MoveListView)this._fragmentView.findViewWithTag((Object)"MoveListView");
        this._moveListView.setNavigationSelectionEnabled(false);
        this._boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getFragmentManager(), true, true, true);
        this._fieldView = (FieldView)this._fragmentView.findViewById(2131296529);
        this._fieldView.setBoardView(this._boardView);
        this._chessClockView = (ChessClockView)this._fragmentView.findViewById(2131296712);
        layoutInflater = Typeface.createFromAsset((AssetManager)this.getActivity().getAssets(), (String)"fonts/TREBUCBD.TTF");
        this._nameWhite = (TextView)this._fragmentView.findViewById(2131296751);
        this._nameWhite.setTypeface((Typeface)layoutInflater);
        this._nameBlack = (TextView)this._fragmentView.findViewById(2131296750);
        this._nameBlack.setTypeface((Typeface)layoutInflater);
        this._buttonResign = this._fragmentView.findViewById(2131296699);
        this._buttonResignText = (TextView)this._fragmentView.findViewById(2131296700);
        this._buttonDrawOffer = this._fragmentView.findViewById(2131296696);
        this._buttonDrawOfferText = (TextView)this._fragmentView.findViewById(2131296697);
        this._moveConfirmationView = (MoveConfirmationView)this._fragmentView.findViewById(2131296748);
        this._buttonDrawOfferShaddow = this._fragmentView.findViewById(2131296773);
        this._buttonDrawOfferDivider = this._fragmentView.findViewById(2131296698);
        this._buttonResign.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (AbstractPlayzoneFragment.this._gameAdapter != null && AbstractPlayzoneFragment.this._gameAdapter.isGameActive()) {
                    if (AbstractPlayzoneFragment.this._gameAdapter.isAbortable()) {
                        AbstractPlayzoneFragment.this._gameAdapter.requestAbort();
                        return;
                    }
                    AbstractPlayzoneFragment.this.showResignConfirmationDialog();
                }
            }
        });
        this._buttonDrawOffer.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (AbstractPlayzoneFragment.this._gameAdapter != null && AbstractPlayzoneFragment.this._gameAdapter.isGameActive()) {
                    AbstractPlayzoneFragment.this._gameAdapter.offerOrAcceptDraw();
                    AbstractPlayzoneFragment.this.updateUI();
                }
            }
        });
        this.getChessClockView().bringToFront();
        return this._fragmentView;
    }

    @Override
    public void onDestroy() {
        if (this._gameAdapter != null && !this._gameAdapter.isGameActive()) {
            this._gameAdapter.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        this._afterGameView = null;
        this._beforeGameView = null;
        this._boardView.clearCachedBitmaps();
        super.onDestroyView();
    }

    @Override
    public void onGameEnd(GameEndInformation gameEndInformation) {
    }

    @Override
    public void onGameSessionEnded(final GameStatus gameStatus) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.dismissResignDialog();
                AbstractPlayzoneFragment.this.updateUI();
                AbstractPlayzoneFragment.this.updateUIOnce();
                AbstractPlayzoneFragment.this.showMenusForPlayzoneState(PlayzoneState.AFTER);
                AbstractPlayzoneFragment.this.setResultForClockView(gameStatus);
            }
        });
    }

    @Override
    public void onGameSessionOver(Game game, GameEndInformation gameEndInformation) {
        this.initViews();
        this.setResultForClockView(gameEndInformation.getStatus());
    }

    @Override
    public void onGameStart() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.showMenusForPlayzoneState(PlayzoneState.PLAYING);
                AbstractPlayzoneFragment.this.initListener();
                AbstractPlayzoneFragment.this.initBoard();
                AbstractPlayzoneFragment.this.updateUIOnce();
                AbstractPlayzoneFragment.this.updateUI();
                AbstractPlayzoneFragment.this.getChessClockView().setGameResult(null);
            }
        });
    }

    @Override
    public void onMove(Move move) {
        this._clockActiveColor = move.getPiece().isWhite() ^ true;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.dismissResignDialog();
                AbstractPlayzoneFragment.this.updateUI();
            }
        });
    }

    @Override
    public void onPause() {
        this.removeListener();
        super.onPause();
        if (this._gameAdapter != null) {
            this._gameAdapter.removeDelegate();
        }
    }

    @Override
    public void onReceiveDrawOffer() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                AbstractPlayzoneFragment.this.updateUI();
            }
        });
    }

    @Override
    public void onResume() {
        if (this._gameAdapter != null) {
            this._gameAdapter.setDelegate(this);
        }
        super.onResume();
        this.initViews();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View object, Bundle bundle) {
        super.onViewCreated((View)object, bundle);
        object = this._currentState != null ? this._currentState : PlayzoneState.CHOOSING_BEFORE;
        this.showMenusForPlayzoneState((PlayzoneState)((Object)object));
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    public void showMenusForPlayzoneState(PlayzoneState playzoneState) {
        this._currentState = playzoneState;
        switch (.$SwitchMap$de$cisha$android$board$playzone$AbstractPlayzoneFragment$PlayzoneState[playzoneState.ordinal()]) {
            default: {
                this.hideAfterGameView();
                this.hideBeforeGameView();
                return;
            }
            case 2: {
                this.hideAfterGameView();
                this.showBeforeGameView();
                return;
            }
            case 1: 
        }
        this.hideBeforeGameView();
        this.showAfterGameView();
    }

    protected void showResignConfirmationDialog() {
        this._rookieDialog = new RookieInfoDialogFragment();
        CustomButton customButton = new CustomButtonNeutral((Context)this.getActivity());
        customButton.setText(2131690268);
        customButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractPlayzoneFragment.this.dismissResignDialog();
            }
        });
        this._rookieDialog.addButton(customButton);
        customButton = new CustomButtonPositive((Context)this.getActivity());
        customButton.setText(2131690271);
        customButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractPlayzoneFragment.this.dismissResignDialog();
                AbstractPlayzoneFragment.this._gameAdapter.resignGame();
            }
        });
        this._rookieDialog.addButton(customButton);
        this._rookieDialog.setRookieType(RookieInfoDialogFragment.RookieType.PROMOTION);
        this._rookieDialog.setTitle(this.getActivity().getString(2131690270));
        this._rookieDialog.setText(this.getActivity().getString(2131690269));
        this._rookieDialog.setCancelable(true);
        this._rookieDialog.show(this.getChildFragmentManager(), "Resign Dialog");
    }

    protected final void startAnalyzeGame(Game object) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("jsonGame", new JSONMappingGame().mapToJSON((Game)object).toString());
            object = new AnalyzeFragment();
            object.setArguments(bundle);
            this.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
            return;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(AbstractPlayzoneFragment.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            return;
        }
    }

    private class MoveDelayingMoveExcutor
    implements MoveExecutor,
    MoveConfirmationView.MoveConfirmationViewDelegate,
    StateHolder {
        private static final String DELAYED_SEP_TO_CONFIRM = "DELAYED_SEP_TO_CONFIRM";
        private SEP _currentlyDelayedSEP;

        private MoveDelayingMoveExcutor() {
        }

        private MoveExecutor getMoveExecutor() {
            return AbstractPlayzoneFragment.this.getGameAdapter().getMoveExecutor();
        }

        private boolean hasDelayedMove() {
            if (this._currentlyDelayedSEP != null) {
                return true;
            }
            return false;
        }

        @Override
        public boolean advanceOneMoveInCurrentLine() {
            if (this.getMoveExecutor() != null) {
                return this.getMoveExecutor().advanceOneMoveInCurrentLine();
            }
            return false;
        }

        @Override
        public void cancelMove(Move move) {
            AbstractPlayzoneFragment.this.hideMoveConfirmationView();
            if (AbstractPlayzoneFragment.this._boardView != null && AbstractPlayzoneFragment.this.getGameAdapter() != null) {
                AbstractPlayzoneFragment.this._boardView.setPosition(AbstractPlayzoneFragment.this.getGameAdapter().getPosition());
                this._currentlyDelayedSEP = null;
            }
        }

        @Override
        public void confirmMove(Move move) {
            AbstractPlayzoneFragment.this.hideMoveConfirmationView();
            if (move != null && this.getMoveExecutor() != null) {
                this.getMoveExecutor().doMoveInCurrentPosition(move.getSEP());
                this._currentlyDelayedSEP = null;
            }
        }

        @Override
        public Move doMoveInCurrentPosition(SEP serializable) {
            if (ServiceProvider.getInstance().getSettingsService().confirmMove()) {
                Position position;
                this._currentlyDelayedSEP = serializable;
                if (AbstractPlayzoneFragment.this.getGameAdapter() != null && (position = AbstractPlayzoneFragment.this.getGameAdapter().getPosition()) != null) {
                    if ((serializable = position.createMoveFrom((SEP)serializable)) != null) {
                        if (AbstractPlayzoneFragment.this._boardView != null) {
                            AbstractPlayzoneFragment.this._boardView.positionChanged(serializable.getResultingPosition(), (Move)serializable);
                        }
                        AbstractPlayzoneFragment.this.showMoveConfirmationView((Move)serializable);
                    }
                    return serializable;
                }
            } else if (this.getMoveExecutor() != null) {
                return this.getMoveExecutor().doMoveInCurrentPosition((SEP)serializable);
            }
            return null;
        }

        @Override
        public boolean goBackOneMove() {
            if (this.getMoveExecutor() != null) {
                return this.getMoveExecutor().goBackOneMove();
            }
            return false;
        }

        @Override
        public void gotoEndingPosition() {
            if (this.getMoveExecutor() != null) {
                this.getMoveExecutor().gotoEndingPosition();
            }
        }

        @Override
        public boolean gotoStartingPosition() {
            if (this.getMoveExecutor() != null) {
                return this.getMoveExecutor().gotoStartingPosition();
            }
            return false;
        }

        @Override
        public void registerPremove(Square square, Square square2) {
            if (this.getMoveExecutor() != null) {
                this.getMoveExecutor().registerPremove(square, square2);
            }
        }

        public void restoreState() {
            if (this.hasDelayedMove()) {
                this.doMoveInCurrentPosition(this._currentlyDelayedSEP);
            }
        }

        @Override
        public void restoreState(Bundle bundle) {
            if (bundle != null && bundle.get(DELAYED_SEP_TO_CONFIRM) != null) {
                this.doMoveInCurrentPosition(new SEP(bundle.getShort(DELAYED_SEP_TO_CONFIRM)));
            }
        }

        @Override
        public void saveState(Bundle bundle) {
            if (this._currentlyDelayedSEP != null) {
                bundle.putShort(DELAYED_SEP_TO_CONFIRM, this._currentlyDelayedSEP.getSEPNumber());
                return;
            }
            bundle.remove(DELAYED_SEP_TO_CONFIRM);
        }

        @Override
        public boolean selectMove(Move move) {
            if (this.getMoveExecutor() != null) {
                return this.getMoveExecutor().selectMove(move);
            }
            return false;
        }
    }

    public static enum PlayzoneState {
        CHOOSING_BEFORE,
        PLAYING,
        AFTER;
        

        private PlayzoneState() {
        }
    }

}
