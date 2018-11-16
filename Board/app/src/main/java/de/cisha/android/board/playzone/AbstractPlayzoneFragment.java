// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.SEP;
import org.json.JSONException;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.chess.model.Game;
import de.cisha.android.board.playzone.model.GameEndInformation;
import android.view.View.OnClickListener;
import android.graphics.Typeface;
import android.content.Context;
import de.cisha.android.board.view.BoardViewFactory;
import android.view.LayoutInflater;
import de.cisha.android.board.StateHolder;
import android.os.Bundle;
import java.util.HashSet;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.chess.model.Country;
import java.io.Serializable;
import de.cisha.chess.model.Opponent;
import de.cisha.android.board.PremiumIndicatorManager;
import android.view.ViewGroup.LayoutParams;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.chess.util.Logger;
import android.graphics.Color;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.android.board.view.TakenPiecesView;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import de.cisha.android.board.view.MoveListView;
import de.cisha.android.board.playzone.view.MoveConfirmationView;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.board.playzone.view.ChessClockView;
import android.widget.TextView;
import de.cisha.android.board.view.BoardView;
import android.view.View;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.AbstractNetworkContentFragment;

public abstract class AbstractPlayzoneFragment extends AbstractNetworkContentFragment implements IGameModelDelegate
{
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
    private boolean _clockActiveColor;
    private boolean _clockRunning;
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
    
    public AbstractPlayzoneFragment() {
        this._clockActiveColor = true;
        this._clockRunning = false;
    }
    
    private void dismissResignDialog() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._rookieDialog != null) {
                    AbstractPlayzoneFragment.this._rookieDialog.dismiss();
                    AbstractPlayzoneFragment.this._rookieDialog = null;
                }
            }
        });
    }
    
    private int getColorFromResources(final int n) {
        final int rgb = Color.rgb(68, 68, 68);
        if (this.isAdded()) {
            return this.getResources().getColor(n);
        }
        Logger.getInstance().error(this.getClass().getName(), "Error: calling getColor without being attached to activity - returning standard grey");
        return rgb;
    }
    
    private void hideAfterGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
            final boolean playerIsWhite = this._gameAdapter.playerIsWhite();
            this._boardView.flip(playerIsWhite);
            this._fieldView.flip(playerIsWhite);
            this._boardView.setPosition(this._gameAdapter.getPosition());
        }
    }
    
    private void initListener() {
        if (this._gameAdapter != null) {
            this._gameAdapter.getPositionObservable().addPositionObserver(this._boardView);
            this._boardView.setMoveExecutor(this._moveExecutor);
            this._moveConfirmationView.setMoveConfirmationDelegate((MoveConfirmationView.MoveConfirmationViewDelegate)this._moveExecutor);
            this._moveListView.setMoveSelector(this._gameAdapter.getMoveExecutor());
            this._gameAdapter.getMovesObservable().addMovesObserver(this._moveListView);
            this._gameAdapter.getPositionObservable().addPositionObserver(this._takenPieceViewBlack);
            this._gameAdapter.getPositionObservable().addPositionObserver(this._takenPieceViewWhite);
        }
    }
    
    private void initViews() {
        this._boardView.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                AbstractPlayzoneFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    
    private void setClockRunning(final boolean clockRunning) {
        this._clockRunning = clockRunning;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                AbstractPlayzoneFragment.this.getChessClockView().setRunning(clockRunning);
            }
        });
    }
    
    private void setResultForClockView(final GameStatus gameStatus) {
        if (gameStatus != null && gameStatus.getResult() != GameResult.NO_RESULT) {
            this.getChessClockView().setGameResult(gameStatus.getResult());
        }
    }
    
    private void showAfterGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._afterGameView == null) {
                    AbstractPlayzoneFragment.this._afterGameView = AbstractPlayzoneFragment.this.getAfterGameView();
                    AbstractPlayzoneFragment.this._fragmentView.addView(AbstractPlayzoneFragment.this._afterGameView, new ViewGroup.LayoutParams(-1, -1));
                }
                AbstractPlayzoneFragment.this._afterGameView.bringToFront();
            }
        });
    }
    
    private void showBeforeGameView() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (AbstractPlayzoneFragment.this._beforeGameView != null) {
                    AbstractPlayzoneFragment.this._fragmentView.removeView(AbstractPlayzoneFragment.this._beforeGameView);
                    AbstractPlayzoneFragment.this._beforeGameView = null;
                }
                if (AbstractPlayzoneFragment.this._beforeGameView == null) {
                    AbstractPlayzoneFragment.this._beforeGameView = AbstractPlayzoneFragment.this.getBeforeGameView();
                    AbstractPlayzoneFragment.this._fragmentView.addView(AbstractPlayzoneFragment.this._beforeGameView, new ViewGroup.LayoutParams(-1, -1));
                    AbstractPlayzoneFragment.this._beforeGameView.setVisibility(0);
                }
                AbstractPlayzoneFragment.this._beforeGameView.bringToFront();
            }
        });
    }
    
    private void showMoveConfirmationView(final Move moveToShow) {
        this._moveConfirmationView.setVisibility(0);
        this._moveConfirmationView.setMoveToShow(moveToShow);
        this._moveConfirmationView.bringToFront();
    }
    
    private void updateUI() {
        this.getChessClockView().setRunning(this._clockRunning);
        this.getChessClockView().setActiveClockMark(this._clockActiveColor);
        final boolean clickable = this._gameAdapter.isAbortable() ^ true;
        this._buttonDrawOffer.setClickable(clickable);
        final View buttonDrawOffer = this._buttonDrawOffer;
        int backgroundResource;
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            backgroundResource = 2131231563;
        }
        else {
            backgroundResource = 2131231562;
        }
        buttonDrawOffer.setBackgroundResource(backgroundResource);
        final View buttonDrawOfferDivider = this._buttonDrawOfferDivider;
        int n;
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            n = 2131099807;
        }
        else {
            n = 2131099806;
        }
        buttonDrawOfferDivider.setBackgroundColor(this.getColorFromResources(n));
        final View buttonDrawOfferShaddow = this._buttonDrawOfferShaddow;
        int visibility;
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            visibility = 4;
        }
        else {
            visibility = 0;
        }
        buttonDrawOfferShaddow.setVisibility(visibility);
        this._buttonDrawOffer.setSelected(this._gameAdapter.playersDrawOfferPending());
        final TextView buttonResignText = this._buttonResignText;
        int text;
        if (clickable) {
            text = 2131690267;
        }
        else {
            text = 2131689502;
        }
        buttonResignText.setText(text);
        final TextView buttonDrawOfferText = this._buttonDrawOfferText;
        int text2;
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            text2 = 2131689503;
        }
        else {
            text2 = 2131690097;
        }
        buttonDrawOfferText.setText(text2);
        int n2;
        if (clickable) {
            n2 = 2131099809;
        }
        else {
            n2 = 2131099808;
        }
        final TextView buttonDrawOfferText2 = this._buttonDrawOfferText;
        if (this._gameAdapter.playersDrawOfferPending()) {
            n2 = 2131099810;
        }
        buttonDrawOfferText2.setTextColor(this.getColorFromResources(n2));
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            this._buttonDrawOfferText.setTextColor(this.getColorFromResources(2131099811));
        }
        int n3;
        if (clickable) {
            n3 = 2131231573;
        }
        else {
            n3 = 2131231574;
        }
        final TextView buttonDrawOfferText3 = this._buttonDrawOfferText;
        if (this._gameAdapter.opponentsDrawOfferPending()) {
            n3 = 2131231575;
        }
        else if (this._gameAdapter.playersDrawOfferPending()) {
            n3 = 2131231572;
        }
        buttonDrawOfferText3.setCompoundDrawablesWithIntrinsicBounds(n3, 0, 0, 0);
        if (!this._gameAdapter.isGameActive()) {
            this._buttonDrawOfferText.setTextColor(this.getColorFromResources(2131099808));
            this._buttonDrawOffer.setBackgroundResource(2131231562);
            this._buttonDrawOffer.setSelected(false);
            this._buttonDrawOfferDivider.setBackgroundColor(this.getColorFromResources(2131099806));
            this._buttonDrawOfferShaddow.setVisibility(0);
        }
    }
    
    private void updateUIOnce() {
        final boolean b = this._gameAdapter != null && this._gameAdapter.isGameActive();
        this._moveListView.setNavigationSelectionEnabled(b ^ true);
        boolean b2;
        if (this._gameAdapter != null) {
            final Opponent playerInfo = this._gameAdapter.getPlayerInfo(true);
            this._nameWhite.setText((CharSequence)playerInfo.getName());
            final TextView eloWhite = this._eloWhite;
            final StringBuilder sb = new StringBuilder();
            String string;
            if (!playerInfo.hasTitle()) {
                string = "";
            }
            else {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("(");
                sb2.append(playerInfo.getTitle());
                sb2.append(") ");
                string = sb2.toString();
            }
            sb.append(string);
            Serializable value;
            if (playerInfo.hasRating()) {
                value = playerInfo.getRating().getRating();
            }
            else {
                value = "-";
            }
            sb.append(value);
            eloWhite.setText((CharSequence)sb.toString());
            final ImageView flagImageWhite = this._flagImageWhite;
            final Country country = playerInfo.getCountry();
            final int n = 2131231366;
            int imageId;
            if (country != null) {
                imageId = playerInfo.getCountry().getImageId();
            }
            else {
                imageId = 2131231366;
            }
            flagImageWhite.setImageResource(imageId);
            final Opponent playerInfo2 = this._gameAdapter.getPlayerInfo(false);
            this._nameBlack.setText((CharSequence)playerInfo2.getName());
            PremiumIndicatorManager.showPremiumIndicatorBesideTextView(playerInfo2.isPremium(), this._nameBlack);
            PremiumIndicatorManager.showPremiumIndicatorBesideTextView(playerInfo.isPremium(), this._nameWhite);
            final TextView eloBlack = this._eloBlack;
            final StringBuilder sb3 = new StringBuilder();
            String string2;
            if (!playerInfo2.hasTitle()) {
                string2 = "";
            }
            else {
                final StringBuilder sb4 = new StringBuilder();
                sb4.append("(");
                sb4.append(playerInfo2.getTitle());
                sb4.append(") ");
                string2 = sb4.toString();
            }
            sb3.append(string2);
            Serializable value2;
            if (playerInfo2.hasRating()) {
                value2 = playerInfo2.getRating().getRating();
            }
            else {
                value2 = "-";
            }
            sb3.append(value2);
            eloBlack.setText((CharSequence)sb3.toString());
            final ImageView flagImageBlack = this._flagImageBlack;
            int imageId2 = n;
            if (playerInfo2.getCountry() != null) {
                imageId2 = playerInfo2.getCountry().getImageId();
            }
            flagImageBlack.setImageResource(imageId2);
            this._moveListView.allMovesChanged(this._gameAdapter.getRootMoveContainer());
            b2 = (this._gameAdapter.getPosition().getHalfMoveNumber() >= 2);
            this._clockActiveColor = this._gameAdapter.getPosition().getActiveColor();
        }
        else {
            b2 = false;
        }
        this._buttonDrawOffer.setClickable(b);
        int n2;
        if (b2) {
            n2 = 2131231573;
        }
        else {
            n2 = 2131231574;
        }
        final TextView buttonDrawOfferText = this._buttonDrawOfferText;
        if (!b) {
            n2 = 2131231573;
        }
        buttonDrawOfferText.setCompoundDrawablesWithIntrinsicBounds(n2, 0, 0, 0);
        this._boardView.setMovable(b);
        this._boardView.setPremovable(b);
        this._buttonResignText.setCompoundDrawablesWithIntrinsicBounds(2131231592, 0, 0, 0);
        this._buttonResign.setClickable(b);
        int n3;
        if (b) {
            n3 = 2131099809;
        }
        else {
            n3 = 2131099808;
        }
        this._buttonResignText.setTextColor(this.getColorFromResources(n3));
        if (b) {
            final boolean playerIsWhite = this._gameAdapter.playerIsWhite();
            this._boardView.setWhiteMoveable(playerIsWhite);
            this._boardView.setBlackMoveable(playerIsWhite ^ true);
            this._boardView.setWhitePreMoveable(playerIsWhite);
            this._boardView.setBlackPreMoveable(playerIsWhite ^ true);
            this._moveExecutor.restoreState();
        }
    }
    
    public void gameChosen(final AbstractGameModel gameAdapter) {
        if (this._gameAdapter != null && !this._gameAdapter.isGameActive()) {
            this._gameAdapter.destroy();
        }
        (this._gameAdapter = gameAdapter).startUp();
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
        final HashSet<SettingsMenuCategory> set = new HashSet<SettingsMenuCategory>();
        set.add(SettingsMenuCategory.PLAYZONE);
        set.add(SettingsMenuCategory.BOARD);
        return set;
    }
    
    protected boolean hasRunningGame() {
        return this.getGameAdapter() != null && this.getGameAdapter().isGameActive();
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }
    
    @Override
    public void onClockChanged(final long n, final boolean b) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                AbstractPlayzoneFragment.this.getChessClockView().setTime(b, n);
            }
        });
    }
    
    @Override
    public void onClockFlag(final boolean b) {
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
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.addStateHolder(this._moveExecutor = new MoveDelayingMoveExcutor());
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        this._fragmentView = (ViewGroup)layoutInflater.inflate(2131427362, viewGroup, false);
        this._eloBlack = (TextView)this._fragmentView.findViewById(2131296720);
        this._eloWhite = (TextView)this._fragmentView.findViewById(2131296721);
        this._flagImageWhite = (ImageView)this._fragmentView.findViewById(2131296798);
        this._flagImageBlack = (ImageView)this._fragmentView.findViewById(2131296695);
        (this._takenPieceViewBlack = (TakenPiecesView)this._fragmentView.findViewById(2131296777)).setColor(false);
        this._takenPieceViewWhite = (TakenPiecesView)this._fragmentView.findViewById(2131296778);
        (this._moveListView = (MoveListView)this._fragmentView.findViewWithTag((Object)"MoveListView")).setNavigationSelectionEnabled(false);
        this._boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getFragmentManager(), true, true, true);
        (this._fieldView = (FieldView)this._fragmentView.findViewById(2131296529)).setBoardView(this._boardView);
        this._chessClockView = (ChessClockView)this._fragmentView.findViewById(2131296712);
        final Typeface fromAsset = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/TREBUCBD.TTF");
        (this._nameWhite = (TextView)this._fragmentView.findViewById(2131296751)).setTypeface(fromAsset);
        (this._nameBlack = (TextView)this._fragmentView.findViewById(2131296750)).setTypeface(fromAsset);
        this._buttonResign = this._fragmentView.findViewById(2131296699);
        this._buttonResignText = (TextView)this._fragmentView.findViewById(2131296700);
        this._buttonDrawOffer = this._fragmentView.findViewById(2131296696);
        this._buttonDrawOfferText = (TextView)this._fragmentView.findViewById(2131296697);
        this._moveConfirmationView = (MoveConfirmationView)this._fragmentView.findViewById(2131296748);
        this._buttonDrawOfferShaddow = this._fragmentView.findViewById(2131296773);
        this._buttonDrawOfferDivider = this._fragmentView.findViewById(2131296698);
        this._buttonResign.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (AbstractPlayzoneFragment.this._gameAdapter != null && AbstractPlayzoneFragment.this._gameAdapter.isGameActive()) {
                    if (AbstractPlayzoneFragment.this._gameAdapter.isAbortable()) {
                        AbstractPlayzoneFragment.this._gameAdapter.requestAbort();
                        return;
                    }
                    AbstractPlayzoneFragment.this.showResignConfirmationDialog();
                }
            }
        });
        this._buttonDrawOffer.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (AbstractPlayzoneFragment.this._gameAdapter != null && AbstractPlayzoneFragment.this._gameAdapter.isGameActive()) {
                    AbstractPlayzoneFragment.this._gameAdapter.offerOrAcceptDraw();
                    AbstractPlayzoneFragment.this.updateUI();
                }
            }
        });
        this.getChessClockView().bringToFront();
        return (View)this._fragmentView;
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
    public void onGameEnd(final GameEndInformation gameEndInformation) {
    }
    
    @Override
    public void onGameSessionEnded(final GameStatus gameStatus) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public void onGameSessionOver(final Game game, final GameEndInformation gameEndInformation) {
        this.initViews();
        this.setResultForClockView(gameEndInformation.getStatus());
    }
    
    @Override
    public void onGameStart() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public void onMove(final Move move) {
        this._clockActiveColor = (move.getPiece().isWhite() ^ true);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        PlayzoneState playzoneState;
        if (this._currentState != null) {
            playzoneState = this._currentState;
        }
        else {
            playzoneState = PlayzoneState.CHOOSING_BEFORE;
        }
        this.showMenusForPlayzoneState(playzoneState);
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    public void showMenusForPlayzoneState(final PlayzoneState currentState) {
        this._currentState = currentState;
        switch (AbstractPlayzoneFragment.17..SwitchMap.de.cisha.android.board.playzone.AbstractPlayzoneFragment.PlayzoneState[currentState.ordinal()]) {
            default: {
                this.hideAfterGameView();
                this.hideBeforeGameView();
            }
            case 2: {
                this.hideAfterGameView();
                this.showBeforeGameView();
            }
            case 1: {
                this.hideBeforeGameView();
                this.showAfterGameView();
            }
        }
    }
    
    protected void showResignConfirmationDialog() {
        this._rookieDialog = new RookieInfoDialogFragment();
        final CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131690268);
        customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractPlayzoneFragment.this.dismissResignDialog();
            }
        });
        this._rookieDialog.addButton(customButtonNeutral);
        final CustomButtonPositive customButtonPositive = new CustomButtonPositive((Context)this.getActivity());
        customButtonPositive.setText(2131690271);
        customButtonPositive.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractPlayzoneFragment.this.dismissResignDialog();
                AbstractPlayzoneFragment.this._gameAdapter.resignGame();
            }
        });
        this._rookieDialog.addButton(customButtonPositive);
        this._rookieDialog.setRookieType(RookieInfoDialogFragment.RookieType.PROMOTION);
        this._rookieDialog.setTitle(this.getActivity().getString(2131690270));
        this._rookieDialog.setText(this.getActivity().getString(2131690269));
        this._rookieDialog.setCancelable(true);
        this._rookieDialog.show(this.getChildFragmentManager(), "Resign Dialog");
    }
    
    protected final void startAnalyzeGame(final Game game) {
        try {
            final Bundle arguments = new Bundle();
            arguments.putString("jsonGame", new JSONMappingGame().mapToJSON(game).toString());
            final AnalyzeFragment analyzeFragment = new AnalyzeFragment();
            analyzeFragment.setArguments(arguments);
            this.getContentPresenter().showFragment(analyzeFragment, true, true);
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(AbstractPlayzoneFragment.class.getName(), JSONException.class.getName(), (Throwable)ex);
        }
    }
    
    private class MoveDelayingMoveExcutor implements MoveExecutor, MoveConfirmationViewDelegate, StateHolder
    {
        private static final String DELAYED_SEP_TO_CONFIRM = "DELAYED_SEP_TO_CONFIRM";
        private SEP _currentlyDelayedSEP;
        
        private MoveExecutor getMoveExecutor() {
            return AbstractPlayzoneFragment.this.getGameAdapter().getMoveExecutor();
        }
        
        private boolean hasDelayedMove() {
            return this._currentlyDelayedSEP != null;
        }
        
        @Override
        public boolean advanceOneMoveInCurrentLine() {
            return this.getMoveExecutor() != null && this.getMoveExecutor().advanceOneMoveInCurrentLine();
        }
        
        @Override
        public void cancelMove(final Move move) {
            AbstractPlayzoneFragment.this.hideMoveConfirmationView();
            if (AbstractPlayzoneFragment.this._boardView != null && AbstractPlayzoneFragment.this.getGameAdapter() != null) {
                AbstractPlayzoneFragment.this._boardView.setPosition(AbstractPlayzoneFragment.this.getGameAdapter().getPosition());
                this._currentlyDelayedSEP = null;
            }
        }
        
        @Override
        public void confirmMove(final Move move) {
            AbstractPlayzoneFragment.this.hideMoveConfirmationView();
            if (move != null && this.getMoveExecutor() != null) {
                this.getMoveExecutor().doMoveInCurrentPosition(move.getSEP());
                this._currentlyDelayedSEP = null;
            }
        }
        
        @Override
        public Move doMoveInCurrentPosition(final SEP currentlyDelayedSEP) {
            if (ServiceProvider.getInstance().getSettingsService().confirmMove()) {
                this._currentlyDelayedSEP = currentlyDelayedSEP;
                if (AbstractPlayzoneFragment.this.getGameAdapter() != null) {
                    final Position position = AbstractPlayzoneFragment.this.getGameAdapter().getPosition();
                    if (position != null) {
                        final Move move = position.createMoveFrom(currentlyDelayedSEP);
                        if (move != null) {
                            if (AbstractPlayzoneFragment.this._boardView != null) {
                                AbstractPlayzoneFragment.this._boardView.positionChanged(move.getResultingPosition(), move);
                            }
                            AbstractPlayzoneFragment.this.showMoveConfirmationView(move);
                        }
                        return move;
                    }
                }
            }
            else if (this.getMoveExecutor() != null) {
                return this.getMoveExecutor().doMoveInCurrentPosition(currentlyDelayedSEP);
            }
            return null;
        }
        
        @Override
        public boolean goBackOneMove() {
            return this.getMoveExecutor() != null && this.getMoveExecutor().goBackOneMove();
        }
        
        @Override
        public void gotoEndingPosition() {
            if (this.getMoveExecutor() != null) {
                this.getMoveExecutor().gotoEndingPosition();
            }
        }
        
        @Override
        public boolean gotoStartingPosition() {
            return this.getMoveExecutor() != null && this.getMoveExecutor().gotoStartingPosition();
        }
        
        @Override
        public void registerPremove(final Square square, final Square square2) {
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
        public void restoreState(final Bundle bundle) {
            if (bundle != null && bundle.get("DELAYED_SEP_TO_CONFIRM") != null) {
                this.doMoveInCurrentPosition(new SEP(bundle.getShort("DELAYED_SEP_TO_CONFIRM")));
            }
        }
        
        @Override
        public void saveState(final Bundle bundle) {
            if (this._currentlyDelayedSEP != null) {
                bundle.putShort("DELAYED_SEP_TO_CONFIRM", this._currentlyDelayedSEP.getSEPNumber());
                return;
            }
            bundle.remove("DELAYED_SEP_TO_CONFIRM");
        }
        
        @Override
        public boolean selectMove(final Move move) {
            return this.getMoveExecutor() != null && this.getMoveExecutor().selectMove(move);
        }
    }
    
    public enum PlayzoneState
    {
        AFTER, 
        CHOOSING_BEFORE, 
        PLAYING;
    }
}
