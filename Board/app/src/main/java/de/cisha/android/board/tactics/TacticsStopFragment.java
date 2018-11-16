// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics;

import de.cisha.android.board.view.KeyValueInfoDialogFragment;
import android.view.animation.Transformation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.position.PositionObserver;
import android.content.Context;
import de.cisha.android.board.view.BoardViewFactory;
import android.view.LayoutInflater;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.AbstractContentFragment;
import java.util.HashSet;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.chess.model.MoveContainer;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.os.Handler;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import java.util.Iterator;
import java.util.List;
import android.view.View.MeasureSpec;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.view.VariationSelectionView;
import android.widget.TextView;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import android.view.ViewGroup;
import de.cisha.android.board.tactics.view.ScrollViewWithSizeListener;
import de.cisha.android.board.view.notation.NotationListView;
import android.view.View;
import de.cisha.chess.model.GameHolder;
import de.cisha.android.board.view.FieldView;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.board.tactics.view.ExerciseHistoryView;
import de.cisha.chess.model.MovesObserver;
import de.cisha.android.board.AbstractNetworkContentFragment;

public class TacticsStopFragment extends AbstractNetworkContentFragment implements MovesObserver, HistorySelectionListener, MenuBarObserver
{
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
    
    private void animateFieldViewHeight(final int n) {
        final FieldViewHeightAnimation fieldViewHeightAnimation = new FieldViewHeightAnimation(this._fieldView.getMeasuredHeight(), n);
        fieldViewHeightAnimation.setDuration(1000L);
        fieldViewHeightAnimation.setFillAfter(true);
        fieldViewHeightAnimation.setFillEnabled(true);
        fieldViewHeightAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                if (TacticsStopFragment.this._boardView != null) {
                    TacticsStopFragment.this._boardView.setIsZooming(false);
                }
            }
            
            public void onAnimationRepeat(final Animation animation) {
            }
            
            public void onAnimationStart(final Animation animation) {
                if (TacticsStopFragment.this._boardView != null) {
                    TacticsStopFragment.this._boardView.setIsZooming(true);
                }
            }
        });
        this._fieldView.startAnimation((Animation)fieldViewHeightAnimation);
    }
    
    private void checkForViewInitalization(@Nullable final Bundle bundle) {
        if (this._session != null && this._viewsAdded) {
            final List<TacticsExerciseUserSolution> exercises = this._session.getExercises();
            final Iterator<TacticsExerciseUserSolution> iterator = exercises.iterator();
            int n = -1;
            int n2 = -1;
            while (iterator.hasNext()) {
                final TacticsExerciseUserSolution tacticsExerciseUserSolution = iterator.next();
                if (tacticsExerciseUserSolution.isCompleted()) {
                    final ExerciseHistoryView exerciseHistoryBar = this._exerciseHistoryBar;
                    SolvedIndicatorView.SolveType solveType;
                    if (tacticsExerciseUserSolution.isCorrect()) {
                        solveType = SolvedIndicatorView.SolveType.CORRECT;
                    }
                    else {
                        solveType = SolvedIndicatorView.SolveType.INCORRECT;
                    }
                    exerciseHistoryBar.addSolvedExcercise(solveType);
                    final int n3 = ++n2;
                    if (tacticsExerciseUserSolution.isCorrect()) {
                        continue;
                    }
                    n = n3;
                    n2 = n3;
                }
            }
            if (ServiceProvider.getInstance().getSettingsService().getTacticsStopMode() == SettingsService.TacticsStopMode.STOP_NEVER && n >= 0) {
                n2 = n;
            }
            if (exercises.size() > n2 && n2 >= 0) {
                TacticsExerciseUserSolution currentExercise;
                final TacticsExerciseUserSolution tacticsExerciseUserSolution2 = currentExercise = exercises.get(n2);
                if (bundle != null) {
                    currentExercise = tacticsExerciseUserSolution2;
                    if (bundle.get("TacticsStopFragmentExerciseId") != null) {
                        final int int1 = bundle.getInt("TacticsStopFragmentExerciseId");
                        final Iterator<TacticsExerciseUserSolution> iterator2 = exercises.iterator();
                        do {
                            currentExercise = tacticsExerciseUserSolution2;
                            if (!iterator2.hasNext()) {
                                break;
                            }
                            currentExercise = iterator2.next();
                        } while (currentExercise.getExercise().getExerciseId() != int1);
                    }
                }
                this.setCurrentExercise(currentExercise);
                this._exerciseHistoryBar.postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (TacticsStopFragment.this._exerciseHistoryBar != null) {
                            TacticsStopFragment.this._exerciseHistoryBar.scrollToSelectedExercise();
                        }
                    }
                }, 500L);
            }
            final TextView tacticsNumberView = this._tacticsNumberView;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(exercises.size());
            tacticsNumberView.setText((CharSequence)sb.toString());
            this._tacticsNumberView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            this._exerciseHistoryBar.setPlaceHolderWidth(this._tacticsNumberView.getMeasuredWidth());
            if (bundle != null && bundle.get("TacticStopFragmentMoveId") != null) {
                this._gameHolder.selectMoveWithId(bundle.getInt("TacticStopFragmentMoveId"));
            }
        }
    }
    
    private void hideVariationsView() {
        synchronized (this) {
            if (this._variationShown) {
                this._menuBar.hidePopup();
                this._variationShown = false;
            }
        }
    }
    
    private void loadSession() {
        this.showDialogWaiting(false, false, "loading", null);
        this._tacticsService.getCurrentSession(new LoadCommandCallbackWithTimeout<TacticsExerciseSession>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsStopFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsStopFragment.this.hideDialogWaiting();
                        final IContentPresenter access.300 = TacticsStopFragment.this.getContentPresenter();
                        final IErrorPresenter access.301 = TacticsStopFragment.this.getErrorHandler();
                        if (apiStatusCode == APIStatusCode.API_ERROR_NOT_SET && access.300 != null) {
                            new Handler().post((Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    access.300.popCurrentFragment();
                                }
                            });
                            return;
                        }
                        if (access.301 != null) {
                            access.301.showViewForErrorCode(apiStatusCode, (IErrorPresenter.IReloadAction)new IErrorPresenter.IReloadAction() {
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
                TacticsStopFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsStopFragment.this.sessionLoaded(tacticsExerciseSession);
                        TacticsStopFragment.this.hideDialogWaiting();
                    }
                });
            }
        });
    }
    
    private void sessionLoaded(final TacticsExerciseSession session) {
        this._session = session;
        this.checkForViewInitalization(null);
    }
    
    private void setCurrentExercise(final TacticsExerciseUserSolution currentSolution) {
        this._menuBar.hidePopup();
        this._currentSolution = currentSolution;
        final Game exerciseGameIncludingUserMoves = currentSolution.getExerciseGameIncludingUserMoves();
        this._gameHolder.setNewGame(exerciseGameIncludingUserMoves);
        this._gameHolder.gotoStartingPosition();
        this._fieldView.flip(currentSolution.getExercise().getUserColor());
        final Iterator<TacticsExerciseUserSolution> iterator = this._session.getExercises().iterator();
        int n = 0;
        while (iterator.hasNext() && !iterator.next().equals(currentSolution)) {
            ++n;
        }
        this._exerciseHistoryBar.selectElementNumber(n);
        if (!currentSolution.isCorrect()) {
            Move move = null;
            for (final Move move2 : exerciseGameIncludingUserMoves.getChildrenOfAllLevels()) {
                if (move2.isUserMove()) {
                    move = move2;
                }
            }
            if (move != null) {
                this._notationList.postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (TacticsStopFragment.this._notationList != null) {
                            TacticsStopFragment.this._notationList.selectedMoveChanged(move);
                            TacticsStopFragment.this._notationList.markMoveWithColor(move, -65536);
                        }
                    }
                }, 50L);
            }
        }
    }
    
    private void showVariationsInCurrentPosition() {
        MoveContainer moveContainer;
        if (this._gameHolder.getCurrentMove() != null) {
            moveContainer = this._gameHolder.getCurrentMove();
        }
        else {
            moveContainer = this._gameHolder.getRootMoveContainer();
        }
        this._variationView.showVariations(moveContainer);
        this._menuBar.showPopup(this._variationView, (MenuBarItem)this.getActivity().findViewById(2131297101));
        this._variationShown = true;
    }
    
    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
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
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690385);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.TACTIC;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        final HashSet<SettingsMenuCategory> set = new HashSet<SettingsMenuCategory>();
        set.add(SettingsMenuCategory.BOARD);
        set.add(SettingsMenuCategory.TACTICS);
        return set;
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
    public void menuItemClicked(final MenuBarItem menuBarItem) {
        this._menuBar.hidePopup();
        if (this._session != null) {
            switch (menuBarItem.getId()) {
                default: {
                    this.hideVariationsView();
                }
                case 2131297104: {
                    final TacticsExerciseFragment tacticsExerciseFragment = new TacticsExerciseFragment();
                    final Bundle arguments = new Bundle();
                    arguments.putBoolean("continue", false);
                    tacticsExerciseFragment.setArguments(arguments);
                    this.getContentPresenter().showFragment(tacticsExerciseFragment, true, false);
                }
                case 2131297103: {
                    final TacticsInfoFragment tacticsInfoFragment = new TacticsInfoFragment();
                    tacticsInfoFragment.setCurrentExercise(this._currentSolution);
                    tacticsInfoFragment.setSession(this._session);
                    tacticsInfoFragment.show(this.getChildFragmentManager(), "");
                }
                case 2131297102: {
                    final TacticsExerciseFragment tacticsExerciseFragment2 = new TacticsExerciseFragment();
                    final Bundle arguments2 = new Bundle();
                    arguments2.putBoolean("continue", true);
                    tacticsExerciseFragment2.setArguments(arguments2);
                    this.getContentPresenter().showFragment(tacticsExerciseFragment2, true, false);
                }
                case 2131297101: {
                    if (this._currentMoveHasVariations && !this._variationShown) {
                        this.showVariationsInCurrentPosition();
                        return;
                    }
                    this._gameHolder.advanceOneMoveInCurrentLine();
                }
                case 2131297100: {
                    this._gameHolder.goBackOneMove();
                }
                case 2131297099: {
                    try {
                        final AnalyzeFragment analyzeFragment = new AnalyzeFragment();
                        final Bundle arguments3 = new Bundle();
                        final JSONMappingGame jsonMappingGame = new JSONMappingGame();
                        Game exerciseGameIncludingUserMoves;
                        if (this._currentSolution != null) {
                            exerciseGameIncludingUserMoves = this._currentSolution.getExerciseGameIncludingUserMoves();
                        }
                        else {
                            exerciseGameIncludingUserMoves = new Game();
                        }
                        arguments3.putString("jsonGame", jsonMappingGame.mapToJSON(exerciseGameIncludingUserMoves).toString());
                        arguments3.putBoolean("gotoend", false);
                        analyzeFragment.setArguments(arguments3);
                        this.getContentPresenter().showFragment(analyzeFragment, true, true);
                        return;
                    }
                    catch (JSONException ex) {
                        Logger.getInstance().debug(TacticsStopFragment.class.getName(), JSONException.class.getName(), (Throwable)ex);
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public void menuItemLongClicked(final MenuBarItem menuBarItem) {
        switch (menuBarItem.getId()) {
            default: {}
            case 2131297101: {
                this._gameHolder.gotoEndingPosition();
            }
            case 2131297100: {
                this._gameHolder.gotoStartingPosition();
            }
        }
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
    public void newMove(final Move move) {
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._tacticsService = ServiceProvider.getInstance().getTacticsExerciseService();
        this._gameHolder = new GameHolder(new Game());
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TacticsStopFragment.this.loadSession();
            }
        });
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427369, viewGroup, false);
        this._fieldView = (FieldView)inflate.findViewById(2131297096);
        (this._boardView = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, false, false)).setMovable(false);
        this._boardView.setPremovable(false);
        this._fieldView.setBoardView(this._boardView);
        (this._menuBar = (MenuBar)inflate.findViewById(2131297105)).setObserver((MenuBar.MenuBarObserver)this);
        this._popupViewGroup = (ViewGroup)inflate.findViewById(2131297108);
        this._menuBar.setPopupViewGroup(this._popupViewGroup);
        this._menuBarItemPrev = inflate.findViewById(2131297100);
        this._menuBarItemNext = inflate.findViewById(2131297101);
        (this._exerciseHistoryBar = (ExerciseHistoryView)inflate.findViewById(2131297098)).setHistorySelectionListener((ExerciseHistoryView.HistorySelectionListener)this);
        this._tacticsNumberView = (TextView)inflate.findViewById(2131297094);
        this._notationListScrollView = (ScrollViewWithSizeListener)inflate.findViewById(2131297107);
        if (this.getResources().getConfiguration().orientation == 1) {
            this._notationListScrollView.setSizeListener((ScrollViewWithSizeListener.SizeListener)new ScrollViewWithSizeListener.SizeListener() {
                @Override
                public void onSizeInit(final int n) {
                    TacticsStopFragment.this.animateFieldViewHeight(Math.min(TacticsStopFragment.this._fieldView.getMeasuredHeight() - ((int)(TacticsStopFragment.this.getResources().getDisplayMetrics().density * 100.0f) - n), (int)(0.75 * TacticsStopFragment.this._fieldView.getMeasuredHeight())));
                }
            });
        }
        this._notationList = (NotationListView)inflate.findViewById(2131297106);
        this._gameHolder.addPositionObserver(this._boardView);
        this._notationList.setMoveSelector(this._gameHolder);
        this._gameHolder.getPosition().getFEN().equals(FEN.STARTING_POSITION);
        this._boardView.setPosition(this._gameHolder.getPosition());
        this._notationList.allMovesChanged(this._gameHolder.getRootMoveContainer());
        this._gameHolder.addMovesObserver(this._notationList);
        this._gameHolder.addMovesObserver(this);
        (this._variationView = new VariationSelectionView((Context)this.getActivity(), this._gameHolder.getRootMoveContainer())).setMoveSelector(this._gameHolder);
        this._variationView.setStrokeColor(-2236963);
        return inflate;
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
    public void onHistoryNumberClicked(final int n) {
        final List<TacticsExerciseUserSolution> exercises = this._session.getExercises();
        if (exercises.size() > n) {
            this.setCurrentExercise(exercises.get(n));
        }
    }
    
    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        if (this._currentSolution != null) {
            bundle.putInt("TacticsStopFragmentExerciseId", this._currentSolution.getExercise().getExerciseId());
        }
        if (this._gameHolder != null && this._gameHolder.getCurrentMove() != null) {
            bundle.putInt("TacticStopFragmentMoveId", this._gameHolder.getCurrentMove().getMoveId());
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
    public void onViewCreated(final View view, final Bundle bundle) {
        this._viewsAdded = true;
        this.checkForViewInitalization(bundle);
        if (this.getResources().getConfiguration().orientation == 2) {
            this._fieldView.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    final int measuredHeight = TacticsStopFragment.this._fieldView.getMeasuredHeight();
                    TacticsStopFragment.this._fieldView.getLayoutParams().width = measuredHeight;
                    TacticsStopFragment.this._fieldView.getLayoutParams().height = measuredHeight;
                }
            });
        }
        super.onViewCreated(view, bundle);
    }
    
    @Override
    public void selectedMoveChanged(final Move move) {
        this.hideVariationsView();
        final View menuBarItemPrev = this._menuBarItemPrev;
        final boolean b = false;
        menuBarItemPrev.setEnabled(move != null);
        boolean currentMoveHasVariations;
        if (move == null) {
            currentMoveHasVariations = this._gameHolder.getRootMoveContainer().hasVariants();
        }
        else {
            currentMoveHasVariations = move.hasVariants();
        }
        this._currentMoveHasVariations = currentMoveHasVariations;
        final View menuBarItemNext = this._menuBarItemNext;
        boolean enabled = false;
        Label_0080: {
            if (move != null) {
                enabled = b;
                if (!move.hasChildren()) {
                    break Label_0080;
                }
            }
            enabled = true;
        }
        menuBarItemNext.setEnabled(enabled);
        this._notationListScrollView.postDelayed((Runnable)new Runnable() {
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
    
    private class FieldViewHeightAnimation extends Animation
    {
        private float _end;
        private float _start;
        
        public FieldViewHeightAnimation(final int n, final int n2) {
            this._start = n;
            this._end = n2;
        }
        
        protected void applyTransformation(final float n, final Transformation transformation) {
            super.applyTransformation(n, transformation);
            TacticsStopFragment.this._fieldView.getLayoutParams().height = (int)(this._start + (this._end - this._start) * n);
            TacticsStopFragment.this._fieldView.requestLayout();
        }
    }
    
    public static class TacticsInfoFragment extends KeyValueInfoDialogFragment
    {
        private TacticsExerciseUserSolution _currentExercise;
        private TacticsExerciseSession _currentSession;
        
        @Override
        public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
            final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
            if (this._currentExercise != null) {
                final String string = this.getActivity().getString(2131690351);
                final StringBuilder sb = new StringBuilder();
                sb.append("#");
                sb.append(this._currentExercise.getExercise().getExerciseId());
                this.addRowView(string, sb.toString());
                final String string2 = this.getActivity().getString(2131690352);
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(this._currentExercise.getExercise().getExerciseRating().getRating());
                this.addRowView(string2, sb2.toString());
                final String string3 = this.getActivity().getString(2131690350);
                final StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(this._currentExercise.getExercise().getAverageTimeInMillis() / 1000.0f);
                this.addRowView(string3, sb3.toString());
                final String string4 = this.getActivity().getString(2131690356);
                final StringBuilder sb4 = new StringBuilder();
                sb4.append("");
                sb4.append(this._currentExercise.getTimeUsed() / 1000.0f);
                this.addRowView(string4, sb4.toString());
            }
            if (this._currentSession != null) {
                this.addRowView(this.getActivity().getString(2131690353), "");
                final String string5 = this.getActivity().getString(2131690346);
                final StringBuilder sb5 = new StringBuilder();
                sb5.append("");
                sb5.append(this._currentSession.getNumberOfExercises());
                this.addRowView(string5, sb5.toString());
                final String string6 = this.getActivity().getString(2131690347);
                final StringBuilder sb6 = new StringBuilder();
                sb6.append("");
                sb6.append(this._currentSession.getNumberOfCorrectExercises());
                this.addRowView(string6, sb6.toString());
                final String string7 = this.getActivity().getString(2131690344);
                final StringBuilder sb7 = new StringBuilder();
                sb7.append("");
                sb7.append(this._currentSession.getNumberOfWrongExercises());
                this.addRowView(string7, sb7.toString());
                final String string8 = this.getActivity().getString(2131690354);
                String string9;
                if (this._currentSession.getUserStartRating() == null) {
                    string9 = "";
                }
                else {
                    final StringBuilder sb8 = new StringBuilder();
                    sb8.append("");
                    sb8.append(this._currentSession.getUserStartRating().getRating());
                    string9 = sb8.toString();
                }
                this.addRowView(string8, string9);
                final String string10 = this.getActivity().getString(2131690345);
                String string11;
                if (this._currentSession.getUserEndRating() == null) {
                    string11 = "";
                }
                else {
                    final StringBuilder sb9 = new StringBuilder();
                    sb9.append("");
                    sb9.append(this._currentSession.getUserEndRating().getRating());
                    string11 = sb9.toString();
                }
                this.addRowView(string10, string11);
                final String string12 = this.getActivity().getString(2131690349);
                String string13;
                if (this._currentSession.getPerformance() == null) {
                    string13 = "";
                }
                else {
                    final StringBuilder sb10 = new StringBuilder();
                    sb10.append("");
                    sb10.append(this._currentSession.getPerformance().getRating());
                    string13 = sb10.toString();
                }
                this.addRowView(string12, string13);
                final String string14 = this.getActivity().getString(2131690355);
                final StringBuilder sb11 = new StringBuilder();
                sb11.append("");
                sb11.append(this._currentSession.getAverageTimeMillis() / 1000.0f);
                sb11.append(this.getActivity().getString(2131690348));
                this.addRowView(string14, sb11.toString());
            }
            return onCreateView;
        }
        
        public void setCurrentExercise(final TacticsExerciseUserSolution currentExercise) {
            this._currentExercise = currentExercise;
        }
        
        public void setSession(final TacticsExerciseSession currentSession) {
            this._currentSession = currentSession;
        }
    }
}
