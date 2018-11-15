/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.Transformation
 *  android.widget.TextView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.AbstractNetworkContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.analyze.view.VariationSelectionView;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.android.board.tactics.view.ExerciseHistoryView;
import de.cisha.android.board.tactics.view.ScrollViewWithSizeListener;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.board.view.KeyValueInfoDialogFragment;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.chess.util.Logger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsStopFragment
extends AbstractNetworkContentFragment
implements MovesObserver,
ExerciseHistoryView.HistorySelectionListener,
MenuBar.MenuBarObserver {
    private static final String TACTICS_STOP_FRAGMENT_EXERCISE_ID = "TacticsStopFragmentExerciseId";
    private static final String TACTICS_STOP_FRAGMENT_MOVE_ID = "TacticStopFragmentMoveId";
    private BoardView _boardView;
    private boolean _currentMoveHasVariations;
    private TacticsExerciseUserSolution _currentSolution;
    private ExerciseHistoryView _exerciseHistoryBar;
    private FieldView _fieldView;
    private GameHolder _gameHolder;
    private MenuBar _menuBar;
    private View _menuBarItemNext;
    private View _menuBarItemPrev;
    private NotationListView _notationList;
    private ScrollViewWithSizeListener _notationListScrollView;
    private ViewGroup _popupViewGroup;
    private TacticsExerciseSession _session;
    private TextView _tacticsNumberView;
    private ITacticsExerciseService _tacticsService;
    private boolean _variationShown;
    private VariationSelectionView _variationView;
    private boolean _viewsAdded;

    private void animateFieldViewHeight(int n) {
        FieldViewHeightAnimation fieldViewHeightAnimation = new FieldViewHeightAnimation(this._fieldView.getMeasuredHeight(), n);
        fieldViewHeightAnimation.setDuration(1000L);
        fieldViewHeightAnimation.setFillAfter(true);
        fieldViewHeightAnimation.setFillEnabled(true);
        fieldViewHeightAnimation.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                if (TacticsStopFragment.this._boardView != null) {
                    TacticsStopFragment.this._boardView.setIsZooming(false);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                if (TacticsStopFragment.this._boardView != null) {
                    TacticsStopFragment.this._boardView.setIsZooming(true);
                }
            }
        });
        this._fieldView.startAnimation((Animation)fieldViewHeightAnimation);
    }

    private void checkForViewInitalization(@Nullable Bundle bundle) {
        if (this._session != null && this._viewsAdded) {
            Object object;
            Object object2;
            List<TacticsExerciseUserSolution> list = this._session.getExercises();
            Object object3 = list.iterator();
            int n = -1;
            int n2 = -1;
            while (object3.hasNext()) {
                int n3;
                object2 = object3.next();
                if (!object2.isCompleted()) continue;
                ExerciseHistoryView exerciseHistoryView = this._exerciseHistoryBar;
                object = object2.isCorrect() ? SolvedIndicatorView.SolveType.CORRECT : SolvedIndicatorView.SolveType.INCORRECT;
                exerciseHistoryView.addSolvedExcercise((SolvedIndicatorView.SolveType)((Object)object));
                n2 = n3 = n2 + 1;
                if (object2.isCorrect()) continue;
                n = n3;
                n2 = n3;
            }
            if (ServiceProvider.getInstance().getSettingsService().getTacticsStopMode() == SettingsService.TacticsStopMode.STOP_NEVER && n >= 0) {
                n2 = n;
            }
            if (list.size() > n2 && n2 >= 0) {
                object = object3 = list.get(n2);
                if (bundle != null) {
                    object = object3;
                    if (bundle.get(TACTICS_STOP_FRAGMENT_EXERCISE_ID) != null) {
                        n2 = bundle.getInt(TACTICS_STOP_FRAGMENT_EXERCISE_ID);
                        object2 = list.iterator();
                        do {
                            object = object3;
                        } while (object2.hasNext() && (object = (TacticsExerciseUserSolution)object2.next()).getExercise().getExerciseId() != n2);
                    }
                }
                this.setCurrentExercise((TacticsExerciseUserSolution)object);
                this._exerciseHistoryBar.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        if (TacticsStopFragment.this._exerciseHistoryBar != null) {
                            TacticsStopFragment.this._exerciseHistoryBar.scrollToSelectedExercise();
                        }
                    }
                }, 500L);
            }
            object = this._tacticsNumberView;
            object3 = new StringBuilder();
            object3.append("");
            object3.append(list.size());
            object.setText((CharSequence)object3.toString());
            this._tacticsNumberView.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
            this._exerciseHistoryBar.setPlaceHolderWidth(this._tacticsNumberView.getMeasuredWidth());
            if (bundle != null && bundle.get(TACTICS_STOP_FRAGMENT_MOVE_ID) != null) {
                n2 = bundle.getInt(TACTICS_STOP_FRAGMENT_MOVE_ID);
                this._gameHolder.selectMoveWithId(n2);
            }
        }
    }

    private void hideVariationsView() {
        synchronized (this) {
            if (this._variationShown) {
                this._menuBar.hidePopup();
                this._variationShown = false;
            }
            return;
        }
    }

    private void loadSession() {
        this.showDialogWaiting(false, false, "loading", null);
        this._tacticsService.getCurrentSession((LoadCommandCallback<TacticsExerciseSession>)new LoadCommandCallbackWithTimeout<TacticsExerciseSession>(){

            @Override
            protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsStopFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsStopFragment.this.hideDialogWaiting();
                        final IContentPresenter iContentPresenter = TacticsStopFragment.this.getContentPresenter();
                        IErrorPresenter iErrorPresenter = TacticsStopFragment.this.getErrorHandler();
                        if (aPIStatusCode == APIStatusCode.API_ERROR_NOT_SET && iContentPresenter != null) {
                            new Handler().post(new Runnable(){

                                @Override
                                public void run() {
                                    iContentPresenter.popCurrentFragment();
                                }
                            });
                            return;
                        }
                        if (iErrorPresenter != null) {
                            iErrorPresenter.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                                @Override
                                public void performReload() {
                                    TacticsStopFragment.this.loadSession();
                                }
                            }, true);
                        }
                    }

                });
            }

            @Override
            protected void succeded(final TacticsExerciseSession tacticsExerciseSession) {
                TacticsStopFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsStopFragment.this.sessionLoaded(tacticsExerciseSession);
                        TacticsStopFragment.this.hideDialogWaiting();
                    }
                });
            }

        });
    }

    private void sessionLoaded(TacticsExerciseSession tacticsExerciseSession) {
        this._session = tacticsExerciseSession;
        this.checkForViewInitalization(null);
    }

    private void setCurrentExercise(TacticsExerciseUserSolution object) {
        this._menuBar.hidePopup();
        this._currentSolution = object;
        Game abstractMoveContainer2 = object.getExerciseGameIncludingUserMoves();
        this._gameHolder.setNewGame(abstractMoveContainer2);
        this._gameHolder.gotoStartingPosition();
        this._fieldView.flip(object.getExercise().getUserColor());
        Iterator<Object> iterator = this._session.getExercises().iterator();
        int n = 0;
        while (iterator.hasNext() && !iterator.next().equals(object)) {
            ++n;
        }
        this._exerciseHistoryBar.selectElementNumber(n);
        if (!object.isCorrect()) {
            object = null;
            for (Move move : abstractMoveContainer2.getChildrenOfAllLevels()) {
                if (!move.isUserMove()) continue;
                object = move;
            }
            if (object != null) {
                this._notationList.postDelayed(new Runnable((Move)object){
                    final /* synthetic */ Move val$newMove;
                    {
                        this.val$newMove = move;
                    }

                    @Override
                    public void run() {
                        if (TacticsStopFragment.this._notationList != null) {
                            TacticsStopFragment.this._notationList.selectedMoveChanged(this.val$newMove);
                            TacticsStopFragment.this._notationList.markMoveWithColor(this.val$newMove, -65536);
                        }
                    }
                }, 50L);
            }
        }
    }

    private void showVariationsInCurrentPosition() {
        MoveContainer moveContainer = this._gameHolder.getCurrentMove() != null ? this._gameHolder.getCurrentMove() : this._gameHolder.getRootMoveContainer();
        this._variationView.showVariations(moveContainer);
        this._menuBar.showPopup(this._variationView, (MenuBarItem)this.getActivity().findViewById(2131297101));
        this._variationShown = true;
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this.hideVariationsView();
        this._menuBarItemPrev.setEnabled(false);
        this._currentMoveHasVariations = moveContainer.hasVariants();
        this._menuBarItemNext.setEnabled(moveContainer.hasChildren());
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690385);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.TACTIC;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        HashSet<SettingsMenuCategory> hashSet = new HashSet<SettingsMenuCategory>();
        hashSet.add(SettingsMenuCategory.BOARD);
        hashSet.add(SettingsMenuCategory.TACTICS);
        return hashSet;
    }

    @Override
    public String getTrackingName() {
        return "TacticsTrainer";
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    @Override
    public void menuItemClicked(MenuBarItem object) {
        this._menuBar.hidePopup();
        if (this._session != null) {
            switch (object.getId()) {
                default: {
                    this.hideVariationsView();
                    return;
                }
                case 2131297104: {
                    object = new TacticsExerciseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("continue", false);
                    object.setArguments(bundle);
                    this.getContentPresenter().showFragment((AbstractContentFragment)object, true, false);
                    return;
                }
                case 2131297103: {
                    object = new TacticsInfoFragment();
                    object.setCurrentExercise(this._currentSolution);
                    object.setSession(this._session);
                    object.show(this.getChildFragmentManager(), "");
                    return;
                }
                case 2131297102: {
                    object = new TacticsExerciseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("continue", true);
                    object.setArguments(bundle);
                    this.getContentPresenter().showFragment((AbstractContentFragment)object, true, false);
                    return;
                }
                case 2131297101: {
                    if (this._currentMoveHasVariations && !this._variationShown) {
                        this.showVariationsInCurrentPosition();
                        return;
                    }
                    this._gameHolder.advanceOneMoveInCurrentLine();
                    return;
                }
                case 2131297100: {
                    this._gameHolder.goBackOneMove();
                    return;
                }
                case 2131297099: 
            }
            try {
                AnalyzeFragment analyzeFragment = new AnalyzeFragment();
                Bundle bundle = new Bundle();
                JSONMappingGame jSONMappingGame = new JSONMappingGame();
                object = this._currentSolution != null ? this._currentSolution.getExerciseGameIncludingUserMoves() : new Game();
                bundle.putString("jsonGame", jSONMappingGame.mapToJSON((Game)object).toString());
                bundle.putBoolean("gotoend", false);
                analyzeFragment.setArguments(bundle);
                this.getContentPresenter().showFragment(analyzeFragment, true, true);
                return;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(TacticsStopFragment.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
        }
    }

    @Override
    public void menuItemLongClicked(MenuBarItem menuBarItem) {
        switch (menuBarItem.getId()) {
            default: {
                return;
            }
            case 2131297101: {
                this._gameHolder.gotoEndingPosition();
                return;
            }
            case 2131297100: 
        }
        this._gameHolder.gotoStartingPosition();
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }

    @Override
    protected boolean needNetwork() {
        return false;
    }

    @Override
    protected boolean needRegisteredUser() {
        return false;
    }

    @Override
    public void newMove(Move move) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._tacticsService = ServiceProvider.getInstance().getTacticsExerciseService();
        this._gameHolder = new GameHolder(new Game());
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsStopFragment.this.loadSession();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427369, viewGroup, false);
        this._fieldView = (FieldView)layoutInflater.findViewById(2131297096);
        this._boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, false, false);
        this._boardView.setMovable(false);
        this._boardView.setPremovable(false);
        this._fieldView.setBoardView(this._boardView);
        this._menuBar = (MenuBar)layoutInflater.findViewById(2131297105);
        this._menuBar.setObserver(this);
        this._popupViewGroup = (ViewGroup)layoutInflater.findViewById(2131297108);
        this._menuBar.setPopupViewGroup(this._popupViewGroup);
        this._menuBarItemPrev = layoutInflater.findViewById(2131297100);
        this._menuBarItemNext = layoutInflater.findViewById(2131297101);
        this._exerciseHistoryBar = (ExerciseHistoryView)layoutInflater.findViewById(2131297098);
        this._exerciseHistoryBar.setHistorySelectionListener(this);
        this._tacticsNumberView = (TextView)layoutInflater.findViewById(2131297094);
        this._notationListScrollView = (ScrollViewWithSizeListener)layoutInflater.findViewById(2131297107);
        if (this.getResources().getConfiguration().orientation == 1) {
            this._notationListScrollView.setSizeListener(new ScrollViewWithSizeListener.SizeListener(){

                @Override
                public void onSizeInit(int n) {
                    int n2 = (int)(TacticsStopFragment.this.getResources().getDisplayMetrics().density * 100.0f);
                    TacticsStopFragment.this.animateFieldViewHeight(Math.min(TacticsStopFragment.this._fieldView.getMeasuredHeight() - (n2 - n), (int)(0.75 * (double)TacticsStopFragment.this._fieldView.getMeasuredHeight())));
                }
            });
        }
        this._notationList = (NotationListView)layoutInflater.findViewById(2131297106);
        this._gameHolder.addPositionObserver(this._boardView);
        this._notationList.setMoveSelector(this._gameHolder);
        this._gameHolder.getPosition().getFEN().equals(FEN.STARTING_POSITION);
        this._boardView.setPosition(this._gameHolder.getPosition());
        this._notationList.allMovesChanged(this._gameHolder.getRootMoveContainer());
        this._gameHolder.addMovesObserver(this._notationList);
        this._gameHolder.addMovesObserver(this);
        this._variationView = new VariationSelectionView((Context)this.getActivity(), this._gameHolder.getRootMoveContainer());
        this._variationView.setMoveSelector(this._gameHolder);
        this._variationView.setStrokeColor(-2236963);
        return layoutInflater;
    }

    @Override
    public void onDestroyView() {
        this._viewsAdded = false;
        this._fieldView = null;
        this._boardView = null;
        this._notationList = null;
        this._exerciseHistoryBar = null;
        this._menuBar = null;
        this._menuBarItemPrev = null;
        this._menuBarItemNext = null;
        this._notationListScrollView = null;
        this._variationView = null;
        this._popupViewGroup = null;
        this._tacticsNumberView = null;
        super.onDestroyView();
    }

    @Override
    public void onHistoryNumberClicked(int n) {
        List<TacticsExerciseUserSolution> list = this._session.getExercises();
        if (list.size() > n) {
            this.setCurrentExercise(list.get(n));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (this._currentSolution != null) {
            bundle.putInt(TACTICS_STOP_FRAGMENT_EXERCISE_ID, this._currentSolution.getExercise().getExerciseId());
        }
        if (this._gameHolder != null && this._gameHolder.getCurrentMove() != null) {
            bundle.putInt(TACTICS_STOP_FRAGMENT_MOVE_ID, this._gameHolder.getCurrentMove().getMoveId());
        }
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        this._boardView.clearCachedBitmaps();
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        this._viewsAdded = true;
        this.checkForViewInitalization(bundle);
        if (this.getResources().getConfiguration().orientation == 2) {
            this._fieldView.post(new Runnable(){

                @Override
                public void run() {
                    int n;
                    TacticsStopFragment.access$600((TacticsStopFragment)TacticsStopFragment.this).getLayoutParams().width = n = TacticsStopFragment.this._fieldView.getMeasuredHeight();
                    TacticsStopFragment.access$600((TacticsStopFragment)TacticsStopFragment.this).getLayoutParams().height = n;
                }
            });
        }
        super.onViewCreated(view, bundle);
    }

    @Override
    public void selectedMoveChanged(final Move move) {
        View view;
        boolean bl;
        block3 : {
            block2 : {
                this.hideVariationsView();
                view = this._menuBarItemPrev;
                boolean bl2 = false;
                bl = move != null;
                view.setEnabled(bl);
                bl = move == null ? this._gameHolder.getRootMoveContainer().hasVariants() : move.hasVariants();
                this._currentMoveHasVariations = bl;
                view = this._menuBarItemNext;
                if (move == null) break block2;
                bl = bl2;
                if (!move.hasChildren()) break block3;
            }
            bl = true;
        }
        view.setEnabled(bl);
        this._notationListScrollView.postDelayed(new Runnable(){

            @Override
            public void run() {
                if (TacticsStopFragment.this._notationListScrollView != null) {
                    TacticsStopFragment.this._notationListScrollView.scrollTo(0, TacticsStopFragment.this._notationList.getTopPositionForMove(move));
                }
            }
        }, 50L);
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    private class FieldViewHeightAnimation
    extends Animation {
        private float _end;
        private float _start;

        public FieldViewHeightAnimation(int n, int n2) {
            this._start = n;
            this._end = n2;
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            TacticsStopFragment.access$600((TacticsStopFragment)TacticsStopFragment.this).getLayoutParams().height = (int)(this._start + (this._end - this._start) * f);
            TacticsStopFragment.this._fieldView.requestLayout();
        }
    }

    public static class TacticsInfoFragment
    extends KeyValueInfoDialogFragment {
        private TacticsExerciseUserSolution _currentExercise;
        private TacticsExerciseSession _currentSession;

        @Override
        public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle object2) {
            viewGroup = super.onCreateView((LayoutInflater)object, viewGroup, (Bundle)object2);
            if (this._currentExercise != null) {
                object = this.getActivity().getString(2131690351);
                object2 = new StringBuilder();
                object2.append("#");
                object2.append(this._currentExercise.getExercise().getExerciseId());
                this.addRowView((String)object, object2.toString());
                object = this.getActivity().getString(2131690352);
                object2 = new StringBuilder();
                object2.append("");
                object2.append(this._currentExercise.getExercise().getExerciseRating().getRating());
                this.addRowView((String)object, object2.toString());
                object = this.getActivity().getString(2131690350);
                object2 = new StringBuilder();
                object2.append("");
                object2.append((float)this._currentExercise.getExercise().getAverageTimeInMillis() / 1000.0f);
                this.addRowView((String)object, object2.toString());
                object = this.getActivity().getString(2131690356);
                object2 = new StringBuilder();
                object2.append("");
                object2.append((float)this._currentExercise.getTimeUsed() / 1000.0f);
                this.addRowView((String)object, object2.toString());
            }
            if (this._currentSession != null) {
                this.addRowView(this.getActivity().getString(2131690353), "");
                object = this.getActivity().getString(2131690346);
                object2 = new StringBuilder();
                object2.append("");
                object2.append(this._currentSession.getNumberOfExercises());
                this.addRowView((String)object, object2.toString());
                object = this.getActivity().getString(2131690347);
                object2 = new StringBuilder();
                object2.append("");
                object2.append(this._currentSession.getNumberOfCorrectExercises());
                this.addRowView((String)object, object2.toString());
                object = this.getActivity().getString(2131690344);
                object2 = new StringBuilder();
                object2.append("");
                object2.append(this._currentSession.getNumberOfWrongExercises());
                this.addRowView((String)object, object2.toString());
                object2 = this.getActivity().getString(2131690354);
                if (this._currentSession.getUserStartRating() == null) {
                    object = "";
                } else {
                    object = new StringBuilder();
                    object.append("");
                    object.append(this._currentSession.getUserStartRating().getRating());
                    object = object.toString();
                }
                this.addRowView((String)object2, (String)object);
                object2 = this.getActivity().getString(2131690345);
                if (this._currentSession.getUserEndRating() == null) {
                    object = "";
                } else {
                    object = new StringBuilder();
                    object.append("");
                    object.append(this._currentSession.getUserEndRating().getRating());
                    object = object.toString();
                }
                this.addRowView((String)object2, (String)object);
                object2 = this.getActivity().getString(2131690349);
                if (this._currentSession.getPerformance() == null) {
                    object = "";
                } else {
                    object = new StringBuilder();
                    object.append("");
                    object.append(this._currentSession.getPerformance().getRating());
                    object = object.toString();
                }
                this.addRowView((String)object2, (String)object);
                object = this.getActivity().getString(2131690355);
                object2 = new StringBuilder();
                object2.append("");
                object2.append((float)this._currentSession.getAverageTimeMillis() / 1000.0f);
                object2.append(this.getActivity().getString(2131690348));
                this.addRowView((String)object, object2.toString());
            }
            return viewGroup;
        }

        public void setCurrentExercise(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
            this._currentExercise = tacticsExerciseUserSolution;
        }

        public void setSession(TacticsExerciseSession tacticsExerciseSession) {
            this._currentSession = tacticsExerciseSession;
        }
    }

}
