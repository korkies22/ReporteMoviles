// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import de.cisha.android.board.setup.SetupBoardActivity;
import de.cisha.android.board.IContentPresenter;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.analyze.navigationbaritems.SwitchBoardNavigationItem;
import de.cisha.android.board.analyze.navigationbaritems.OpeningTreeViewController;
import de.cisha.chess.model.position.PositionObservable;
import java.util.ArrayList;
import de.cisha.chess.model.MovesEditor;
import de.cisha.chess.model.position.PositionObserver;
import android.content.Context;
import de.cisha.android.board.view.BoardViewFactory;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import de.cisha.android.board.StateHolder;
import de.cisha.chess.model.MovesObservable;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import android.view.View;
import android.view.LayoutInflater;
import de.cisha.chess.util.Logger;
import android.os.Bundle;
import java.util.Date;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.FEN;
import android.content.Intent;
import java.util.HashSet;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.chess.model.Game;
import de.cisha.android.board.service.ServiceProvider;
import java.util.TimerTask;
import de.cisha.chess.model.GameHolder;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import android.os.Looper;
import android.view.ScaleGestureDetector;
import android.os.Handler;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.board.engine.EvaluationInfo;
import java.util.List;
import android.view.ViewGroup;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.analyze.navigationbaritems.AutoPlayNavigationBarItem;
import java.util.Timer;
import de.cisha.android.board.video.model.UserActionObservable;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.AbstractContentFragment;

public abstract class AbstractAnalyseFragment extends AbstractContentFragment implements IAnalyzeDelegate, AutoPlayDelegate, GameInfoEditDelegate, ScaleGestureDetector.OnScaleGestureListener, UserActionObserver
{
    public static final String GOTO_END = "gotoend";
    private static final int MIN_BOARD_HEIGHT = 100;
    public static final int SET_POSITION_REQUEST_CODE = 23454;
    private Timer _autoPlayTimer;
    private AutoPlayNavigationBarItem _autoplayItem;
    private BoardView _boardView;
    private ViewGroup _contentView;
    private List<EvaluationInfo> _currentEvaluations;
    private EngineAnalyzeViewController _engineViewController;
    private FieldView _fieldView;
    private boolean _gotoend;
    private Handler _mainThreadHandler;
    private int _maxSizeBoard;
    private MoveListViewController _moveListController;
    private AnalyzeNavigationBarController _navigationBarController;
    private ScaleGestureDetector _scaleGestureDetector;
    private int _startBoardScaleWidth;
    private ViewGroup _submenuView;
    private UserActionObservable _userActionObservable;
    
    public AbstractAnalyseFragment() {
        this._mainThreadHandler = new Handler(Looper.getMainLooper());
    }
    
    private void addAnalyseBarItem(final AnalyzeNavigationBarItem analyzeNavigationBarItem) {
        this._navigationBarController.addAnalyseBarItem(analyzeNavigationBarItem);
    }
    
    private boolean advanceOneMove() {
        final GameHolder gameHolder = this.getGameHolder();
        return gameHolder != null && gameHolder.advanceOneMoveInCurrentLine();
    }
    
    private void setFieldSize(int max) {
        max = Math.max(Math.min(this._maxSizeBoard, max), 100);
        this._fieldView.getLayoutParams().width = max;
        this._fieldView.getLayoutParams().height = max;
    }
    
    @Override
    public void autoReplayGame() {
        this._autoplayItem.setPlaying(true);
        this._navigationBarController.setCloseSubmenuOnMoveChanges(false);
        if (this._autoPlayTimer == null) {
            (this._autoPlayTimer = new Timer()).scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    AbstractAnalyseFragment.this._mainThreadHandler.post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (!AbstractAnalyseFragment.this.advanceOneMove()) {
                                AbstractAnalyseFragment.this.stopAutoReplay();
                            }
                        }
                    });
                }
            }, 0L, ServiceProvider.getInstance().getSettingsService().getAutoReplaySpeedMillis());
        }
    }
    
    @Override
    public void editButtonClicked() {
    }
    
    protected BoardView getBoardView() {
        return this._boardView;
    }
    
    protected Game getGame() {
        return this.getGameHolder().getRootMoveContainer().getGame();
    }
    
    protected abstract GameHolder getGameHolder();
    
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690382);
    }
    
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.ANALYZE;
    }
    
    protected MoveExecutor getMoveExecutor() {
        return this.getUserActionObservable();
    }
    
    protected MoveListViewController getMoveListController() {
        return this._moveListController;
    }
    
    protected MoveSelector getMoveSelector() {
        return this.getUserActionObservable();
    }
    
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        final HashSet<SettingsMenuCategory> set = new HashSet<SettingsMenuCategory>();
        set.add(SettingsMenuCategory.BOARD);
        return set;
    }
    
    protected UserActionObservable getUserActionObservable() {
        if (this._userActionObservable == null) {
            this._userActionObservable = new UserActionObservable(this.getGameHolder(), this.getGameHolder());
        }
        return this._userActionObservable;
    }
    
    public boolean isGrabMenuEnabled() {
        return false;
    }
    
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n == 23454 && n2 == -1 && intent.hasExtra("de.cisha.android.board.setup.FEN_STRING")) {
            final String stringExtra = intent.getStringExtra("de.cisha.android.board.setup.FEN_STRING");
            if (stringExtra != null) {
                final Position startingPosition = new Position(new FEN(stringExtra));
                final Game newGame = new Game();
                newGame.setStartingPosition(startingPosition);
                newGame.setStartDate(new Date());
                this.getGameHolder().setNewGame(newGame);
            }
        }
    }
    
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        this.setRetainInstance(true);
        arguments = this.getArguments();
        this._gotoend = true;
        if (arguments != null) {
            try {
                this._gotoend = arguments.getBoolean("gotoend", true);
            }
            catch (Exception ex) {
                Logger.getInstance().error(this.getClass().getName(), "Error - args key gotoend did not contain a boolean", ex);
            }
        }
        this.getGameHolder().setNewGame(new Game());
    }
    
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427358, viewGroup, false);
        this._contentView = (ViewGroup)inflate.findViewById(2131296311);
        this._submenuView = (ViewGroup)inflate.findViewById(2131296314);
        this.setupMenuBar(this._navigationBarController = new AnalyzeNavigationBarController((MenuBar)inflate.findViewById(2131296309), (MenuBar)inflate.findViewById(2131296308), this._contentView, this._submenuView, this.getGameHolder(), this.getMoveExecutor(), this.getMoveSelector()));
        this.addStateHolder(this._navigationBarController);
        inflate.findViewById(2131296309).setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            private float _startDownY;
            
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    AbstractAnalyseFragment.this._startBoardScaleWidth = AbstractAnalyseFragment.this._fieldView.getWidth();
                    this._startDownY = motionEvent.getRawY();
                }
                else if (motionEvent.getAction() == 2) {
                    AbstractAnalyseFragment.this.setFieldSize((int)(AbstractAnalyseFragment.this._startBoardScaleWidth + motionEvent.getRawY() - this._startDownY));
                    AbstractAnalyseFragment.this._fieldView.requestLayout();
                }
                return true;
            }
        });
        this._fieldView = (FieldView)inflate.findViewById(2131296305);
        this._boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, false, false);
        this.addStateHolder(this._fieldView);
        this.addStateHolder(this._boardView);
        this._fieldView.setBoardView(this._boardView);
        this._scaleGestureDetector = new ScaleGestureDetector((Context)this.getActivity(), (ScaleGestureDetector.OnScaleGestureListener)this);
        this._boardView.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() == 2) {
                    AbstractAnalyseFragment.this._scaleGestureDetector.onTouchEvent(motionEvent);
                    return true;
                }
                return false;
            }
        });
        if (this.getResources().getConfiguration().orientation != 1) {
            this._fieldView.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (AbstractAnalyseFragment.this._fieldView != null) {
                        AbstractAnalyseFragment.this._maxSizeBoard = AbstractAnalyseFragment.this._fieldView.getMeasuredHeight();
                        AbstractAnalyseFragment.this.setFieldSize(AbstractAnalyseFragment.this._maxSizeBoard);
                    }
                }
            });
        }
        else {
            this._maxSizeBoard = this.getResources().getDisplayMetrics().widthPixels;
        }
        this.getGameHolder().addPositionObserver(this._boardView);
        this._boardView.setMoveExecutor(this.getMoveExecutor());
        this._boardView.setPosition(this.getGameHolder().getPosition());
        return inflate;
    }
    
    @Override
    public void onDestroyView() {
        this._currentEvaluations = this._engineViewController.getCurrentEvaluations();
        this._engineViewController.destroy();
        this._engineViewController = null;
        this._fieldView = null;
        this._boardView = null;
        this._contentView = null;
        this._submenuView = null;
        this._scaleGestureDetector = null;
        this.getGameHolder().clearObservers();
        super.onDestroyView();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        this._fieldView.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                if (!AbstractAnalyseFragment.this.isRemoving() && AbstractAnalyseFragment.this.isAdded()) {
                    final float density = AbstractAnalyseFragment.this.getResources().getDisplayMetrics().density;
                    final float n = AbstractAnalyseFragment.this._contentView.getHeight();
                    if (n / density < 60.0f) {
                        AbstractAnalyseFragment.this.setFieldSize(AbstractAnalyseFragment.this._boardView.getHeight() - (int)(density * 60.0f - n));
                        AbstractAnalyseFragment.this._fieldView.requestLayout();
                    }
                }
            }
        }, 50L);
    }
    
    public boolean onScale(final ScaleGestureDetector scaleGestureDetector) {
        this._boardView.setIsZooming(true);
        this.setFieldSize((int)(scaleGestureDetector.getScaleFactor() * this._startBoardScaleWidth));
        this._fieldView.requestLayout();
        return false;
    }
    
    public boolean onScaleBegin(final ScaleGestureDetector scaleGestureDetector) {
        this._startBoardScaleWidth = this._boardView.getHeight();
        this._boardView.setIsZooming(true);
        return true;
    }
    
    public void onScaleEnd(final ScaleGestureDetector scaleGestureDetector) {
        this._boardView.setIsZooming(false);
    }
    
    public void onStop() {
        this._boardView.clearCachedBitmaps();
        super.onStop();
    }
    
    protected void setGame(final Game newGame) {
        this.getGameHolder().setNewGame(newGame);
        if (this._gotoend) {
            this.getGameHolder().gotoEndingPosition();
        }
    }
    
    protected void setupMenuBar(final AnalyzeNavigationBarController analyzeNavigationBarController) {
        final FragmentActivity activity = this.getActivity();
        final MoveSelector moveSelector = this.getMoveSelector();
        final MoveExecutor moveExecutor = this.getMoveExecutor();
        final GameHolder gameHolder = this.getGameHolder();
        final GameHolder gameHolder2 = this.getGameHolder();
        final GameHolder gameHolder3 = this.getGameHolder();
        final IContentPresenter contentPresenter = this.getContentPresenter();
        this.addAnalyseBarItem(this._moveListController = new MoveListViewController((Context)activity, gameHolder2, moveSelector, gameHolder3));
        List<EvaluationInfo> currentEvaluations;
        if (this._currentEvaluations == null) {
            currentEvaluations = new ArrayList<EvaluationInfo>(10);
        }
        else {
            currentEvaluations = this._currentEvaluations;
        }
        this._currentEvaluations = currentEvaluations;
        gameHolder.addPositionObserver(this._engineViewController = new EngineAnalyzeViewController((Context)activity, contentPresenter, moveExecutor, this._currentEvaluations));
        this._engineViewController.setupEngine(gameHolder);
        this.addAnalyseBarItem(this._engineViewController);
        this.addAnalyseBarItem(new OpeningTreeViewController(contentPresenter, gameHolder, moveExecutor));
        this.addAnalyseBarItem(new SwitchBoardNavigationItem(this));
        this.addAnalyseBarItem(this._autoplayItem = new AutoPlayNavigationBarItem(this));
        analyzeNavigationBarController.selectNavigationBarItem(this._moveListController);
        this.getUserActionObservable().addUserActionObserver((UserActionObservable.UserActionObserver)this);
    }
    
    @Override
    public void setupPosition() {
        final Intent intent = new Intent(this.getActivity().getApplicationContext(), (Class)SetupBoardActivity.class);
        intent.putExtra("de.cisha.android.board.setup.FEN_STRING", this.getGameHolder().getPosition().getFEN().getFENString());
        this.startActivityForResult(intent, 23454);
    }
    
    public boolean showMenu() {
        return false;
    }
    
    @Override
    public void stopAutoReplay() {
        if (this._autoPlayTimer != null) {
            this._autoPlayTimer.cancel();
            this._autoPlayTimer.purge();
            this._autoPlayTimer = null;
        }
        this._autoplayItem.setPlaying(false);
        this._navigationBarController.setCloseSubmenuOnMoveChanges(true);
    }
    
    @Override
    public void switchBoard() {
        this._fieldView.flip();
    }
    
    public void userDidAction() {
        this.stopAutoReplay();
    }
}
