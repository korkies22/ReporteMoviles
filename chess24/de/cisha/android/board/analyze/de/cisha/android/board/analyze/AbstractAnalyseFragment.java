/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.analyze.AnalyzeNavigationBarController;
import de.cisha.android.board.analyze.AutoPlayDelegate;
import de.cisha.android.board.analyze.EngineAnalyzeViewController;
import de.cisha.android.board.analyze.IAnalyzeDelegate;
import de.cisha.android.board.analyze.MoveListViewController;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.AutoPlayNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.OpeningTreeViewController;
import de.cisha.android.board.analyze.navigationbaritems.SwitchBoardNavigationItem;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.setup.SetupBoardActivity;
import de.cisha.android.board.video.model.UserActionObservable;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesEditor;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractAnalyseFragment
extends AbstractContentFragment
implements IAnalyzeDelegate,
AutoPlayDelegate,
GameInfoDialogFragment.GameInfoEditDelegate,
ScaleGestureDetector.OnScaleGestureListener,
UserActionObservable.UserActionObserver {
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
    private Handler _mainThreadHandler = new Handler(Looper.getMainLooper());
    private int _maxSizeBoard;
    private MoveListViewController _moveListController;
    private AnalyzeNavigationBarController _navigationBarController;
    private ScaleGestureDetector _scaleGestureDetector;
    private int _startBoardScaleWidth;
    private ViewGroup _submenuView;
    private UserActionObservable _userActionObservable;

    private void addAnalyseBarItem(AnalyzeNavigationBarItem analyzeNavigationBarItem) {
        this._navigationBarController.addAnalyseBarItem(analyzeNavigationBarItem);
    }

    private boolean advanceOneMove() {
        GameHolder gameHolder = this.getGameHolder();
        if (gameHolder != null) {
            return gameHolder.advanceOneMoveInCurrentLine();
        }
        return false;
    }

    private void setFieldSize(int n) {
        this._fieldView.getLayoutParams().width = n = Math.max(Math.min(this._maxSizeBoard, n), 100);
        this._fieldView.getLayoutParams().height = n;
    }

    @Override
    public void autoReplayGame() {
        this._autoplayItem.setPlaying(true);
        this._navigationBarController.setCloseSubmenuOnMoveChanges(false);
        if (this._autoPlayTimer == null) {
            this._autoPlayTimer = new Timer();
            TimerTask timerTask = new TimerTask(){

                @Override
                public void run() {
                    AbstractAnalyseFragment.this._mainThreadHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            if (!AbstractAnalyseFragment.this.advanceOneMove()) {
                                AbstractAnalyseFragment.this.stopAutoReplay();
                            }
                        }
                    });
                }

            };
            this._autoPlayTimer.scheduleAtFixedRate(timerTask, 0L, (long)ServiceProvider.getInstance().getSettingsService().getAutoReplaySpeedMillis());
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

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690382);
    }

    @Override
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

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        HashSet<SettingsMenuCategory> hashSet = new HashSet<SettingsMenuCategory>();
        hashSet.add(SettingsMenuCategory.BOARD);
        return hashSet;
    }

    protected UserActionObservable getUserActionObservable() {
        if (this._userActionObservable == null) {
            this._userActionObservable = new UserActionObservable(this.getGameHolder(), this.getGameHolder());
        }
        return this._userActionObservable;
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }

    @Override
    public void onActivityResult(int n, int n2, Intent object) {
        if (n == 23454 && n2 == -1 && object.hasExtra("de.cisha.android.board.setup.FEN_STRING") && (object = object.getStringExtra("de.cisha.android.board.setup.FEN_STRING")) != null) {
            object = new Position(new FEN((String)object));
            Game game = new Game();
            game.setStartingPosition((Position)object);
            game.setStartDate(new Date());
            this.getGameHolder().setNewGame(game);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setRetainInstance(true);
        bundle = this.getArguments();
        this._gotoend = true;
        if (bundle != null) {
            try {
                this._gotoend = bundle.getBoolean("gotoend", true);
            }
            catch (Exception exception) {
                Logger.getInstance().error(this.getClass().getName(), "Error - args key gotoend did not contain a boolean", exception);
            }
        }
        this.getGameHolder().setNewGame(new Game());
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427358, object, false);
        this._contentView = (ViewGroup)layoutInflater.findViewById(2131296311);
        this._submenuView = (ViewGroup)layoutInflater.findViewById(2131296314);
        object = this.getGameHolder();
        this._navigationBarController = new AnalyzeNavigationBarController((MenuBar)layoutInflater.findViewById(2131296309), (MenuBar)layoutInflater.findViewById(2131296308), this._contentView, this._submenuView, (MovesObservable)object, this.getMoveExecutor(), this.getMoveSelector());
        this.setupMenuBar(this._navigationBarController);
        this.addStateHolder(this._navigationBarController);
        layoutInflater.findViewById(2131296309).setOnTouchListener(new View.OnTouchListener(){
            private float _startDownY;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    AbstractAnalyseFragment.this._startBoardScaleWidth = AbstractAnalyseFragment.this._fieldView.getWidth();
                    this._startDownY = motionEvent.getRawY();
                } else if (motionEvent.getAction() == 2) {
                    AbstractAnalyseFragment.this.setFieldSize((int)((float)AbstractAnalyseFragment.this._startBoardScaleWidth + motionEvent.getRawY() - this._startDownY));
                    AbstractAnalyseFragment.this._fieldView.requestLayout();
                }
                return true;
            }
        });
        this._fieldView = (FieldView)layoutInflater.findViewById(2131296305);
        this._boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, false, false);
        this.addStateHolder(this._fieldView);
        this.addStateHolder(this._boardView);
        this._fieldView.setBoardView(this._boardView);
        this._scaleGestureDetector = new ScaleGestureDetector((Context)this.getActivity(), (ScaleGestureDetector.OnScaleGestureListener)this);
        this._boardView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() == 2) {
                    AbstractAnalyseFragment.this._scaleGestureDetector.onTouchEvent(motionEvent);
                    return true;
                }
                return false;
            }
        });
        if (this.getResources().getConfiguration().orientation != 1) {
            this._fieldView.post(new Runnable(){

                @Override
                public void run() {
                    if (AbstractAnalyseFragment.this._fieldView != null) {
                        AbstractAnalyseFragment.this._maxSizeBoard = AbstractAnalyseFragment.this._fieldView.getMeasuredHeight();
                        AbstractAnalyseFragment.this.setFieldSize(AbstractAnalyseFragment.this._maxSizeBoard);
                    }
                }
            });
        } else {
            this._maxSizeBoard = this.getResources().getDisplayMetrics().widthPixels;
        }
        this.getGameHolder().addPositionObserver(this._boardView);
        this._boardView.setMoveExecutor(this.getMoveExecutor());
        this._boardView.setPosition(this.getGameHolder().getPosition());
        return layoutInflater;
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
        this._fieldView.postDelayed(new Runnable(){

            @Override
            public void run() {
                if (!AbstractAnalyseFragment.this.isRemoving() && AbstractAnalyseFragment.this.isAdded()) {
                    float f = AbstractAnalyseFragment.this.getResources().getDisplayMetrics().density;
                    float f2 = AbstractAnalyseFragment.this._contentView.getHeight();
                    if (f2 / f < 60.0f) {
                        int n = (int)(f * 60.0f - f2);
                        AbstractAnalyseFragment.this.setFieldSize(AbstractAnalyseFragment.this._boardView.getHeight() - n);
                        AbstractAnalyseFragment.this._fieldView.requestLayout();
                    }
                }
            }
        }, 50L);
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        this._boardView.setIsZooming(true);
        this.setFieldSize((int)(scaleGestureDetector.getScaleFactor() * (float)this._startBoardScaleWidth));
        this._fieldView.requestLayout();
        return false;
    }

    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        this._startBoardScaleWidth = this._boardView.getHeight();
        this._boardView.setIsZooming(true);
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        this._boardView.setIsZooming(false);
    }

    @Override
    public void onStop() {
        this._boardView.clearCachedBitmaps();
        super.onStop();
    }

    protected void setGame(Game game) {
        this.getGameHolder().setNewGame(game);
        if (this._gotoend) {
            this.getGameHolder().gotoEndingPosition();
        }
    }

    protected void setupMenuBar(AnalyzeNavigationBarController analyzeNavigationBarController) {
        FragmentActivity fragmentActivity = this.getActivity();
        List<EvaluationInfo> list = this.getMoveSelector();
        MoveExecutor moveExecutor = this.getMoveExecutor();
        GameHolder gameHolder = this.getGameHolder();
        GameHolder gameHolder2 = this.getGameHolder();
        GameHolder gameHolder3 = this.getGameHolder();
        IContentPresenter iContentPresenter = this.getContentPresenter();
        this._moveListController = new MoveListViewController((Context)fragmentActivity, gameHolder2, (MoveSelector)((Object)list), gameHolder3);
        this.addAnalyseBarItem(this._moveListController);
        list = this._currentEvaluations == null ? new ArrayList<EvaluationInfo>(10) : this._currentEvaluations;
        this._currentEvaluations = list;
        this._engineViewController = new EngineAnalyzeViewController((Context)fragmentActivity, iContentPresenter, moveExecutor, this._currentEvaluations);
        gameHolder.addPositionObserver(this._engineViewController);
        this._engineViewController.setupEngine(gameHolder);
        this.addAnalyseBarItem(this._engineViewController);
        this.addAnalyseBarItem(new OpeningTreeViewController(iContentPresenter, gameHolder, moveExecutor));
        this.addAnalyseBarItem(new SwitchBoardNavigationItem(this));
        this._autoplayItem = new AutoPlayNavigationBarItem(this);
        this.addAnalyseBarItem(this._autoplayItem);
        analyzeNavigationBarController.selectNavigationBarItem(this._moveListController);
        this.getUserActionObservable().addUserActionObserver(this);
    }

    @Override
    public void setupPosition() {
        Intent intent = new Intent(this.getActivity().getApplicationContext(), SetupBoardActivity.class);
        intent.putExtra("de.cisha.android.board.setup.FEN_STRING", this.getGameHolder().getPosition().getFEN().getFENString());
        this.startActivityForResult(intent, 23454);
    }

    @Override
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

    @Override
    public void userDidAction() {
        this.stopAutoReplay();
    }

}
