// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics;

import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.content.Context;
import de.cisha.android.board.view.BoardViewFactory;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Locale;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import java.util.HashSet;
import java.util.Iterator;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;
import de.cisha.chess.model.MoveExecutor;
import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.view.View.MeasureSpec;
import de.cisha.android.board.AbstractContentFragment;
import java.util.Date;
import de.cisha.chess.model.Square;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.android.board.tactics.view.TranslateViewGroup;
import java.util.Timer;
import android.view.View;
import de.cisha.android.board.tactics.view.SolvedToastView;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.Rating;
import de.cisha.android.board.tactics.view.NumberExerciseViewGroup;
import de.cisha.android.board.tactics.view.ExerciseHistoryView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.exercise.TacticsGameHolder;
import de.cisha.android.board.view.FieldView;
import android.widget.TextView;
import de.cisha.android.board.view.BoardView;
import android.widget.ImageView;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.chess.model.exercise.TacticsObserver;
import de.cisha.android.board.AbstractNetworkContentFragment;

public class TacticsExerciseFragment extends AbstractNetworkContentFragment implements TacticsObserver, MenuBarObserver, PositionObserver
{
    public static final String CONTINUE_SESSION = "continue";
    private static final int HINT_MILLISECONDS_PENALTY = 10000;
    private static final int MINIMUM_TOAST_SHOW_TIME = 1500;
    private static final int START_AFTER_MILLISECONDS = 10000;
    private ImageView _bestMoveImageView;
    private BoardView _board;
    private boolean _calledTranlateViewOnce;
    private TextView _exerciseNo;
    private FieldView _fieldView;
    protected int _fragmentHeight;
    protected int _fragmentWidth;
    private TacticsGameHolder _gameHolder;
    private boolean _hintActive;
    private MenuBarItem _hintItem;
    private ExerciseHistoryView _historyBar;
    private NumberExerciseViewGroup _numberExerciseViewGroup;
    private boolean _openTranslateView;
    private Rating _rating;
    private TextView _ratingMaxView;
    private TextView _ratingMinView;
    private TextView _ratingRunning;
    private TacticsExerciseSession _session;
    private boolean _smallDevice;
    private SolvedToastView _solvedToastView;
    private long _startExerciseTime;
    private boolean _stopFlag;
    private View _strapMenuActionHint;
    private ITacticsExerciseService _tacticsService;
    private TextView _textBestMoveForColor;
    private long _timeExerciseResultStartedToShow;
    private TextView _timeRunning;
    private Timer _timer;
    private TranslateViewGroup _translateViewForTimer;
    private TranslateViewGroup _translateViewRatingBeforeMarker;
    private TranslateViewGroup _translateViewTopMenu;
    
    public TacticsExerciseFragment() {
        this._calledTranlateViewOnce = false;
    }
    
    private void actionHint() {
        if (!this._hintActive && this._gameHolder != null && this._gameHolder.getCurrentTacticsPuzzle() != null) {
            final Square hint = this._gameHolder.getCurrentTacticsPuzzle().getHint();
            if (hint != null) {
                this._board.markSquare(hint, -16711936);
                this._startExerciseTime -= 10000L;
                this._translateViewForTimer.adjustAnimationTimeBy(10000);
                this._hintActive = true;
                this._hintItem.setSelected(true);
            }
        }
    }
    
    private void actionStop() {
        final long time = new Date().getTime();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final TacticsStopFragment tacticsStopFragment = new TacticsStopFragment();
                final IContentPresenter access.4400 = TacticsExerciseFragment.this.getContentPresenter();
                if (access.4400 != null) {
                    access.4400.showFragment(tacticsStopFragment, false, false);
                }
            }
        };
        final int n = (int)(1500L - (time - this._timeExerciseResultStartedToShow));
        if (n > 0) {
            this._board.postDelayed((Runnable)runnable, (long)n);
            return;
        }
        runnable.run();
    }
    
    private void adjustBoardForCurrentExercise() {
        final boolean userColor = this._gameHolder.getCurrentTacticsPuzzle().getExercise().getUserColor();
        this._fieldView.flip(userColor);
        this._board.setMovable(true);
        this._board.setBlackMoveable(userColor ^ true);
        this._board.setWhiteMoveable(userColor);
        this._board.setPremovable(false);
        final TextView textBestMoveForColor = this._textBestMoveForColor;
        int text;
        if (userColor) {
            text = 2131689569;
        }
        else {
            text = 2131689568;
        }
        textBestMoveForColor.setText(text);
        final ImageView bestMoveImageView = this._bestMoveImageView;
        int imageResource;
        if (userColor) {
            imageResource = 2131231838;
        }
        else {
            imageResource = 2131231805;
        }
        bestMoveImageView.setImageResource(imageResource);
    }
    
    private void checkForSmallDisplays(final View view) {
        final View viewById = view.findViewById(2131297120);
        viewById.measure(View.MeasureSpec.makeMeasureSpec(this._fragmentWidth, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
        final int measuredHeight = viewById.getMeasuredHeight();
        final MenuBar menuBar = (MenuBar)view.findViewById(2131297091);
        final CustomTextView customTextView = (CustomTextView)view.findViewById(2131297084);
        this._smallDevice = (viewById.getHeight() < measuredHeight);
        if (this._smallDevice) {
            this._strapMenuActionHint.setVisibility(0);
            customTextView.setVisibility(0);
            menuBar.setVisibility(8);
            this._exerciseNo.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    TacticsExerciseFragment.this.toggleSmallDevActionMenu();
                    TacticsExerciseFragment.this.resetHistoryBarWidth();
                }
            });
            this._strapMenuActionHint.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    TacticsExerciseFragment.this.actionHint();
                    TacticsExerciseFragment.this._strapMenuActionHint.setSelected(TacticsExerciseFragment.this._hintActive);
                }
            });
            customTextView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    TacticsExerciseFragment.this._stopFlag ^= true;
                    customTextView.setGlowing(TacticsExerciseFragment.this._stopFlag);
                    customTextView.setSelected(TacticsExerciseFragment.this._stopFlag);
                }
            });
            return;
        }
        this._exerciseNo.setCompoundDrawables((Drawable)null, (Drawable)null, (Drawable)null, (Drawable)null);
        this._exerciseNo.setOnClickListener((View.OnClickListener)null);
        this._strapMenuActionHint.setVisibility(8);
        customTextView.setVisibility(8);
        menuBar.setVisibility(0);
        menuBar.setObserver((MenuBar.MenuBarObserver)this);
    }
    
    private void finishedLoadingNewRating(final Rating rating) {
        final int n = 0;
        int rating2;
        if (rating == null) {
            rating2 = 0;
        }
        else {
            rating2 = rating.getRating();
        }
        int n2 = n;
        if (this._rating != null) {
            n2 = n;
            if (rating != null) {
                n2 = rating2 - this._rating.getRating();
            }
        }
        this._rating = rating;
        this.setExerciseRatingChange(rating2, n2);
    }
    
    private int getPositionForRatingNow(final int n, final int n2, final int n3) {
        return this._translateViewRatingBeforeMarker.getWidth() * (n3 - n2) / (n - n2);
    }
    
    private void hideExerciseResultAndStartNextExercise(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        final long time = new Date().getTime();
        this._solvedToastView.showLoadingAnimation(false);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TacticsExerciseFragment.this.hideResultToast();
                TacticsExerciseFragment.this._gameHolder.setNewTacticsPuzzle(tacticsExerciseUserSolution);
                TacticsExerciseFragment.this.adjustBoardForCurrentExercise();
                TacticsExerciseFragment.this.startExerciseTimer();
            }
        };
        final long timeExerciseResultStartedToShow = this._timeExerciseResultStartedToShow;
        final long n = 1500;
        if (time - timeExerciseResultStartedToShow < n) {
            this._solvedToastView.postDelayed((Runnable)runnable, n - (time - this._timeExerciseResultStartedToShow));
            return;
        }
        runnable.run();
    }
    
    private void hideResultToast() {
        this._solvedToastView.showLoadingAnimation(false);
        this._solvedToastView.setVisibility(8);
    }
    
    private void hideWaitingTimer() {
        this.hideDialogWaiting();
    }
    
    private void initGameHolderObservers() {
        this._gameHolder.addPositionObserver(this._board);
        this._gameHolder.addPositionObserver(this);
        this._board.setMoveExecutor(this._gameHolder);
        this._board.setPosition(this._gameHolder.getPosition());
        this._gameHolder.addTacticsObserver(this);
    }
    
    private void initHistoryBarWithSession() {
        this.setExerciceNo(this._session.getExercises().size());
        this._historyBar.reset();
        for (final TacticsExerciseUserSolution tacticsExerciseUserSolution : this._session.getExercises()) {
            if (tacticsExerciseUserSolution.isCompleted()) {
                final ExerciseHistoryView historyBar = this._historyBar;
                SolvedIndicatorView.SolveType solveType;
                if (tacticsExerciseUserSolution.isCorrect()) {
                    solveType = SolvedIndicatorView.SolveType.CORRECT;
                }
                else {
                    solveType = SolvedIndicatorView.SolveType.INCORRECT;
                }
                historyBar.addSolvedExcercise(solveType);
            }
        }
        this._historyBar.addSolvedExcercise(SolvedIndicatorView.SolveType.RUNNING);
    }
    
    private void initRatingIndicatorBar() {
        if (this._gameHolder != null) {
            if (this._smallDevice && this._openTranslateView) {
                this.toggleSmallDevActionMenu();
            }
            this.resetHistoryBarWidth();
            final int rating = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMax().getRating();
            final int rating2 = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMin().getRating();
            final int rating3 = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingNow().getRating();
            final TextView ratingMinView = this._ratingMinView;
            final StringBuilder sb = new StringBuilder();
            sb.append(rating2);
            sb.append("");
            ratingMinView.setText((CharSequence)sb.toString());
            final TextView ratingMaxView = this._ratingMaxView;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(rating);
            sb2.append("");
            ratingMaxView.setText((CharSequence)sb2.toString());
            final TextView ratingRunning = this._ratingRunning;
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(rating);
            sb3.append("");
            ratingRunning.setText((CharSequence)sb3.toString());
            this._calledTranlateViewOnce = false;
            this._translateViewRatingBeforeMarker.translateViewTo(this.getPositionForRatingNow(rating, rating2, rating3));
        }
    }
    
    private boolean isOwnErrorCode(final APIStatusCode apiStatusCode) {
        final HashSet<APIStatusCode> set = new HashSet<APIStatusCode>();
        set.add(APIStatusCode.INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED);
        set.add(APIStatusCode.INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED);
        set.add(APIStatusCode.INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES);
        return set.contains(apiStatusCode);
    }
    
    private void loadCurrentSession() {
        this.showWaitingTimer();
        this._tacticsService.getCurrentSession(new LoadCommandCallbackWithTimeout<TacticsExerciseSession>() {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideDialogWaiting();
                        TacticsExerciseFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.loadCurrentSession();
                            }
                        }, true);
                    }
                });
            }
            
            public void succeded(final TacticsExerciseSession tacticsExerciseSession) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideDialogWaiting();
                        TacticsExerciseFragment.this.loadFirstExercise();
                        TacticsExerciseFragment.this._session = tacticsExerciseSession;
                        TacticsExerciseFragment.this.initHistoryBarWithSession();
                    }
                });
            }
        });
    }
    
    private void loadExerciseSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this._tacticsService.exerciseSolved(tacticsExerciseUserSolution, new LoadCommandCallbackWithTimeout<Rating>() {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsExerciseFragment.this.hideResultToast();
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.showExerciseResult(tacticsExerciseUserSolution.isCorrect());
                                TacticsExerciseFragment.this.loadExerciseSolved(tacticsExerciseUserSolution);
                            }
                        }, new IErrorPresenter.ICancelAction() {
                            @Override
                            public void cancelPressed() {
                                TacticsExerciseFragment.this.actionStop();
                            }
                        });
                    }
                });
            }
            
            public void succeded(final Rating rating) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.finishedLoadingNewRating(rating);
                        TacticsExerciseFragment.this.loadNextExercise();
                    }
                });
            }
        }, this.shouldStopPuzzles() ^ true);
    }
    
    private void loadFirstExercise() {
        this.showWaitingTimer();
        this._tacticsService.getNextExercise(new LoadCommandCallbackWithTimeout<TacticsExerciseUserSolution>() {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideWaitingTimer();
                        TacticsExerciseFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.loadFirstExercise();
                            }
                        }, true);
                    }
                });
            }
            
            public void succeded(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideWaitingTimer();
                        TacticsExerciseFragment.this._gameHolder = new TacticsGameHolder(tacticsExerciseUserSolution);
                        TacticsExerciseFragment.this.initGameHolderObservers();
                        TacticsExerciseFragment.this.adjustBoardForCurrentExercise();
                        TacticsExerciseFragment.this.startExerciseTimer();
                    }
                });
            }
        });
    }
    
    private void loadNextExercise() {
        if (this.shouldStopPuzzles()) {
            this.actionStop();
            return;
        }
        this._tacticsService.getNextExercise(new LoadCommandCallbackWithTimeout<TacticsExerciseUserSolution>() {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.loadNextExercise();
                            }
                        });
                    }
                });
            }
            
            public void succeded(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideExerciseResultAndStartNextExercise(tacticsExerciseUserSolution);
                    }
                });
            }
        });
    }
    
    private void resetHistoryBarWidth() {
        final ExerciseHistoryView historyBar = this._historyBar;
        int placeHolderWidth;
        if (!this._openTranslateView) {
            placeHolderWidth = this._exerciseNo.getMeasuredWidth();
        }
        else {
            placeHolderWidth = this._numberExerciseViewGroup.getMeasuredWidth();
        }
        historyBar.setPlaceHolderWidth(placeHolderWidth);
        this._historyBar.requestLayout();
        this._historyBar.fullScroll(66);
    }
    
    private void setExerciceNo(final int n) {
        final TextView exerciseNo = this._exerciseNo;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(n);
        exerciseNo.setText((CharSequence)sb.toString());
    }
    
    private void setExerciseRatingChange(final int n, final int n2) {
        this._timeExerciseResultStartedToShow = new Date().getTime();
        final SolvedToastView solvedToastView = this._solvedToastView;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(n);
        sb.append("(");
        String s;
        if (n2 > 0) {
            s = "+";
        }
        else {
            s = "";
        }
        sb.append(s);
        sb.append(n2);
        sb.append(")");
        solvedToastView.setText(sb.toString());
    }
    
    private boolean shouldStopPuzzles() {
        final boolean stopFlag = this._stopFlag;
        final SettingsService.TacticsStopMode tacticsStopMode = ServiceProvider.getInstance().getSettingsService().getTacticsStopMode();
        final SettingsService.TacticsStopMode stop_AFTER_EVERY = SettingsService.TacticsStopMode.STOP_AFTER_EVERY;
        final boolean b = false;
        final boolean b2 = tacticsStopMode == stop_AFTER_EVERY;
        boolean b3 = b;
        if (tacticsStopMode == SettingsService.TacticsStopMode.STOP_ON_FAILURE) {
            b3 = b;
            if (this._gameHolder.getCurrentTacticsPuzzle() != null) {
                b3 = b;
                if (!this._gameHolder.getCurrentTacticsPuzzle().isCorrect()) {
                    b3 = true;
                }
            }
        }
        return stopFlag | b2 | b3;
    }
    
    private void showExerciseResult(final boolean b) {
        this._timeExerciseResultStartedToShow = new Date().getTime();
        this._solvedToastView.setSolved(this._gameHolder.getCurrentTacticsPuzzle().isCorrect());
        this._solvedToastView.setText("...");
        this._solvedToastView.setVisibility(0);
        this._solvedToastView.showLoadingAnimation(true);
    }
    
    private void showWaitingTimer() {
        this.showDialogWaiting(true, true, "loading...", null);
    }
    
    private void startExerciseTimer() {
        if (this._timer != null) {
            this._timer.cancel();
        }
        this._translateViewForTimer.clearAnimation();
        this.initRatingIndicatorBar();
        this._startExerciseTime = System.currentTimeMillis();
        final int rating = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingNow().getRating();
        final int rating2 = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMax().getRating();
        final int positionForRatingNow = this.getPositionForRatingNow(rating2, this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMin().getRating(), rating);
        final int solvingTimeMillis = this._gameHolder.getCurrentTacticsPuzzle().getExercise().getSolvingTimeMillis();
        (this._timer = new Timer()).scheduleAtFixedRate(new TimerTask() {
            final /* synthetic */ SimpleDateFormat val.dateFormat = new SimpleDateFormat("mm:ss", Locale.GERMAN);
            
            @Override
            public void run() {
                if (!TacticsExerciseFragment.this.isResumed()) {
                    return;
                }
                final int n = (int)(System.currentTimeMillis() - TacticsExerciseFragment.this._startExerciseTime);
                final int val.solvingTimeMillis = solvingTimeMillis;
                if (TacticsExerciseFragment.this.isAdded() && !TacticsExerciseFragment.this.isRemoving()) {
                    TacticsExerciseFragment.this.getActivity().runOnUiThread((Runnable)new Runnable() {
                        final /* synthetic */ int val.millisLeft = val.solvingTimeMillis - n;
                        
                        @Override
                        public void run() {
                            if (this.val.millisLeft >= 0) {
                                TacticsExerciseFragment.this._timeRunning.setText((CharSequence)TimerTask.this.val.dateFormat.format(new Date(this.val.millisLeft)));
                            }
                            else {
                                TacticsExerciseFragment.this._timeRunning.setText((CharSequence)TimerTask.this.val.dateFormat.format(new Date(0L)));
                            }
                            if (n >= 10000 && !TacticsExerciseFragment.this._calledTranlateViewOnce) {
                                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo((int)((TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth()) * (1.0f - (n - 10000) / (solvingTimeMillis - 10000))));
                                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(positionForRatingNow, solvingTimeMillis - n);
                                TacticsExerciseFragment.this._calledTranlateViewOnce = true;
                                return;
                            }
                            if (n >= 0 && !TacticsExerciseFragment.this._calledTranlateViewOnce) {
                                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth());
                            }
                        }
                    });
                }
            }
        }, 0L, 200L);
        this._timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!TacticsExerciseFragment.this.isResumed()) {
                    return;
                }
                final long n = System.currentTimeMillis() - (TacticsExerciseFragment.this._startExerciseTime + 10000L);
                if (n > 0L) {
                    double n2;
                    if ((n2 = n / (solvingTimeMillis - 10000)) > 1.0) {
                        n2 = 1.0;
                    }
                    final int val.maxRating = rating2;
                    final int n3 = (int)(n2 * (rating2 - rating));
                    if (TacticsExerciseFragment.this.isAdded() && !TacticsExerciseFragment.this.isRemoving()) {
                        TacticsExerciseFragment.this.getActivity().runOnUiThread((Runnable)new Runnable() {
                            final /* synthetic */ int val.rating = val.maxRating - n3;
                            
                            @Override
                            public void run() {
                                final TextView access.1700 = TacticsExerciseFragment.this._ratingRunning;
                                final StringBuilder sb = new StringBuilder();
                                sb.append(this.val.rating);
                                sb.append("");
                                access.1700.setText((CharSequence)sb.toString());
                            }
                        });
                    }
                }
            }
        }, 0L, 200L);
        this._translateViewForTimer.post((Runnable)new Runnable() {
            @Override
            public void run() {
                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth());
            }
        });
    }
    
    private void stopExerciseTimer() {
        if (this._timer != null) {
            this._timer.cancel();
        }
        this._translateViewForTimer.clearAnimation();
    }
    
    private void toggleSmallDevActionMenu() {
        this._numberExerciseViewGroup.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (TacticsExerciseFragment.this._openTranslateView) {
                    TacticsExerciseFragment.this._translateViewTopMenu.translateViewTo(TacticsExerciseFragment.this._numberExerciseViewGroup.getMeasuredWidth() - TacticsExerciseFragment.this._exerciseNo.getMeasuredWidth(), 500);
                }
                else {
                    TacticsExerciseFragment.this._translateViewTopMenu.translateViewTo(0, 500);
                }
                TacticsExerciseFragment.this._openTranslateView ^= true;
            }
        });
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
        return false;
    }
    
    @Override
    public void menuItemClicked(final MenuBarItem menuBarItem) {
        if (menuBarItem.getId() == 2131297089) {
            menuBarItem.setGlowing(this._stopFlag ^= true);
            if (!this._stopFlag) {
                menuBarItem.setSelected(false);
            }
        }
        else if (menuBarItem.getId() == 2131297088) {
            this.actionHint();
        }
        this._hintItem.setSelected(this._hintActive);
    }
    
    @Override
    public void menuItemLongClicked(final MenuBarItem menuBarItem) {
    }
    
    public boolean needAuthToken() {
        return true;
    }
    
    public boolean needNetwork() {
        return true;
    }
    
    public boolean needRegisteredUser() {
        return false;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this._tacticsService = ServiceProvider.getInstance().getTacticsExerciseService()).getUserExerciseRating(new LoadCommandCallbackWithTimeout<Rating>() {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            }
            
            public void succeded(final Rating rating) {
                TacticsExerciseFragment.this._rating = rating;
            }
        });
        if (this.getArguments() != null && this.getArguments().getBoolean("continue", false)) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                @Override
                public void run() {
                    TacticsExerciseFragment.this.loadCurrentSession();
                }
            });
            return;
        }
        this._session = this._tacticsService.startNewSession();
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TacticsExerciseFragment.this.setExerciceNo(0);
                TacticsExerciseFragment.this._historyBar.reset();
                TacticsExerciseFragment.this._historyBar.addSolvedExcercise(SolvedIndicatorView.SolveType.RUNNING);
                TacticsExerciseFragment.this.loadFirstExercise();
            }
        });
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427368, viewGroup, false);
        this._hintItem = (MenuBarItem)inflate.findViewById(2131297088);
        (this._textBestMoveForColor = (TextView)inflate.findViewById(2131297119)).setText((CharSequence)"");
        this._bestMoveImageView = (ImageView)inflate.findViewById(2131297085);
        this._ratingMinView = (TextView)inflate.findViewById(2131297128);
        this._ratingMaxView = (TextView)inflate.findViewById(2131297127);
        this._ratingRunning = (TextView)inflate.findViewById(2131297129);
        this._timeRunning = (TextView)inflate.findViewById(2131297130);
        this._translateViewTopMenu = (TranslateViewGroup)inflate.findViewById(2131297092);
        this._translateViewForTimer = (TranslateViewGroup)inflate.findViewById(2131297126);
        this._translateViewRatingBeforeMarker = (TranslateViewGroup)inflate.findViewById(2131297125);
        this._openTranslateView = true;
        this._exerciseNo = (TextView)inflate.findViewById(2131297086);
        this._numberExerciseViewGroup = (NumberExerciseViewGroup)inflate.findViewById(2131297093);
        (this._historyBar = (ExerciseHistoryView)inflate.findViewById(2131297087)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
            }
        });
        (this._solvedToastView = (SolvedToastView)inflate.findViewById(2131297121)).setVisibility(8);
        this._strapMenuActionHint = inflate.findViewById(2131297083);
        this._fieldView = (FieldView)inflate.findViewById(2131297090);
        (this._board = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, false, false)).setKeepScreenOn(true);
        this._fieldView.setBoardView(this._board);
        if (this._gameHolder != null) {
            this.initGameHolderObservers();
        }
        this._fragmentWidth = viewGroup.getWidth();
        this._fragmentHeight = viewGroup.getHeight();
        inflate.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                TacticsExerciseFragment.this._fragmentWidth = viewGroup.getWidth();
                TacticsExerciseFragment.this._fragmentHeight = viewGroup.getHeight();
                TacticsExerciseFragment.this.checkForSmallDisplays(inflate);
                inflate.getViewTreeObserver().removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                TacticsExerciseFragment.this.initRatingIndicatorBar();
            }
        });
        return inflate;
    }
    
    @Override
    public void onDestroy() {
        if (this._timer != null) {
            this._timer.cancel();
            this._timer = null;
        }
        super.onDestroy();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (this._rating != null && this._gameHolder != null) {
            this.adjustBoardForCurrentExercise();
        }
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.resetHistoryBarWidth();
        if (this._session != null) {
            this.initHistoryBarWithSession();
        }
        if (this._hintActive && this._gameHolder != null) {
            final Square hint = this._gameHolder.getCurrentTacticsPuzzle().getHint();
            if (hint != null) {
                this._board.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TacticsExerciseFragment.this._board.markSquare(hint, -16711936);
                    }
                });
            }
            this._hintItem.setSelected(true);
            this._strapMenuActionHint.setSelected(true);
        }
        if (this._stopFlag) {
            final MenuBarItem menuBarItem = (MenuBarItem)view.findViewById(2131297089);
            menuBarItem.setGlowing(true);
            menuBarItem.setSelected(true);
            final CustomTextView customTextView = (CustomTextView)view.findViewById(2131297084);
            customTextView.setGlowing(true);
            customTextView.setSelected(true);
        }
        if (this.getResources().getConfiguration().orientation == 2) {
            this._fieldView.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    final int measuredHeight = TacticsExerciseFragment.this._fieldView.getMeasuredHeight();
                    TacticsExerciseFragment.this._fieldView.getLayoutParams().width = measuredHeight;
                    TacticsExerciseFragment.this._fieldView.getLayoutParams().height = measuredHeight;
                }
            });
        }
    }
    
    @Override
    public void positionChanged(final Position position, final Move move) {
        this._hintActive = false;
        this._hintItem.setSelected(false);
        this._strapMenuActionHint.setSelected(false);
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    @Override
    protected void showViewForErrorCode(final APIStatusCode apiStatusCode, final IErrorPresenter.IReloadAction reloadAction, final IErrorPresenter.ICancelAction cancelAction) {
        if (this.isOwnErrorCode(apiStatusCode)) {
            this.hideResultToast();
            if (this.getActivity() != null) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.TACTICS, "premium screen shown", null);
                this.getContentPresenter().showConversionDialog(null, ConversionContext.TACTICS);
                this._timeExerciseResultStartedToShow = System.currentTimeMillis();
                this.actionStop();
            }
        }
        else {
            super.showViewForErrorCode(apiStatusCode, reloadAction, cancelAction);
        }
    }
    
    @Override
    protected void showViewForErrorCode(final APIStatusCode apiStatusCode, final IErrorPresenter.IReloadAction reloadAction, final boolean b) {
        if (this.isOwnErrorCode(apiStatusCode)) {
            this.hideResultToast();
            if (this.getActivity() != null) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.TACTICS, "premium screen shown", null);
                this.getContentPresenter().showConversionDialog(null, ConversionContext.TACTICS);
                this._timeExerciseResultStartedToShow = System.currentTimeMillis();
                this.actionStop();
            }
        }
        else {
            super.showViewForErrorCode(apiStatusCode, reloadAction, b);
        }
    }
    
    @Override
    public void tacticsSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        final boolean correct = tacticsExerciseUserSolution.isCorrect();
        tacticsExerciseUserSolution.setTimeUsed((int)(System.currentTimeMillis() - this._startExerciseTime));
        this._historyBar.post((Runnable)new Runnable() {
            @Override
            public void run() {
                TacticsExerciseFragment.this.stopExerciseTimer();
                final ExerciseHistoryView access.200 = TacticsExerciseFragment.this._historyBar;
                SolvedIndicatorView.SolveType solveType;
                if (correct) {
                    solveType = SolvedIndicatorView.SolveType.CORRECT;
                }
                else {
                    solveType = SolvedIndicatorView.SolveType.INCORRECT;
                }
                access.200.addSolvedExcercise(solveType);
                TacticsExerciseFragment.this.setExerciceNo(TacticsExerciseFragment.this._session.getExercises().size());
                TacticsExerciseFragment.this.showExerciseResult(tacticsExerciseUserSolution.isCorrect());
            }
        });
        this.loadExerciseSolved(tacticsExerciseUserSolution);
    }
}
