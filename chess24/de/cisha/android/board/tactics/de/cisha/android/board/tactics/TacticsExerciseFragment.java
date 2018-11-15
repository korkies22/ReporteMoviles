/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.AbstractNetworkContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.tactics.TacticsStopFragment;
import de.cisha.android.board.tactics.view.ExerciseHistoryView;
import de.cisha.android.board.tactics.view.NumberExerciseViewGroup;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;
import de.cisha.android.board.tactics.view.SolvedToastView;
import de.cisha.android.board.tactics.view.TranslateViewGroup;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.BoardViewFactory;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.exercise.TacticsGameHolder;
import de.cisha.chess.model.exercise.TacticsObserver;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class TacticsExerciseFragment
extends AbstractNetworkContentFragment
implements TacticsObserver,
MenuBar.MenuBarObserver,
PositionObserver {
    public static final String CONTINUE_SESSION = "continue";
    private static final int HINT_MILLISECONDS_PENALTY = 10000;
    private static final int MINIMUM_TOAST_SHOW_TIME = 1500;
    private static final int START_AFTER_MILLISECONDS = 10000;
    private ImageView _bestMoveImageView;
    private BoardView _board;
    private boolean _calledTranlateViewOnce = false;
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

    private void actionHint() {
        Square square;
        if (!this._hintActive && this._gameHolder != null && this._gameHolder.getCurrentTacticsPuzzle() != null && (square = this._gameHolder.getCurrentTacticsPuzzle().getHint()) != null) {
            this._board.markSquare(square, -16711936);
            this._startExerciseTime -= 10000L;
            this._translateViewForTimer.adjustAnimationTimeBy(10000);
            this._hintActive = true;
            this._hintItem.setSelected(true);
        }
    }

    private void actionStop() {
        long l = new Date().getTime();
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                TacticsStopFragment tacticsStopFragment = new TacticsStopFragment();
                IContentPresenter iContentPresenter = TacticsExerciseFragment.this.getContentPresenter();
                if (iContentPresenter != null) {
                    iContentPresenter.showFragment(tacticsStopFragment, false, false);
                }
            }
        };
        int n = (int)(1500L - (l - this._timeExerciseResultStartedToShow));
        if (n > 0) {
            this._board.postDelayed(runnable, (long)n);
            return;
        }
        runnable.run();
    }

    private void adjustBoardForCurrentExercise() {
        boolean bl = this._gameHolder.getCurrentTacticsPuzzle().getExercise().getUserColor();
        this._fieldView.flip(bl);
        this._board.setMovable(true);
        this._board.setBlackMoveable(bl ^ true);
        this._board.setWhiteMoveable(bl);
        this._board.setPremovable(false);
        TextView textView = this._textBestMoveForColor;
        int n = bl ? 2131689569 : 2131689568;
        textView.setText(n);
        textView = this._bestMoveImageView;
        n = bl ? 2131231838 : 2131231805;
        textView.setImageResource(n);
    }

    private void checkForSmallDisplays(View object) {
        View view = object.findViewById(2131297120);
        view.measure(View.MeasureSpec.makeMeasureSpec((int)this._fragmentWidth, (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
        int n = view.getMeasuredHeight();
        MenuBar menuBar = (MenuBar)object.findViewById(2131297091);
        object = (CustomTextView)object.findViewById(2131297084);
        boolean bl = view.getHeight() < n;
        this._smallDevice = bl;
        if (this._smallDevice) {
            this._strapMenuActionHint.setVisibility(0);
            object.setVisibility(0);
            menuBar.setVisibility(8);
            this._exerciseNo.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    TacticsExerciseFragment.this.toggleSmallDevActionMenu();
                    TacticsExerciseFragment.this.resetHistoryBarWidth();
                }
            });
            this._strapMenuActionHint.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    TacticsExerciseFragment.this.actionHint();
                    TacticsExerciseFragment.this._strapMenuActionHint.setSelected(TacticsExerciseFragment.this._hintActive);
                }
            });
            object.setOnClickListener(new View.OnClickListener((CustomTextView)((Object)object)){
                final /* synthetic */ CustomTextView val$viewActionStop;
                {
                    this.val$viewActionStop = customTextView;
                }

                public void onClick(View view) {
                    TacticsExerciseFragment.this._stopFlag = TacticsExerciseFragment.this._stopFlag ^ true;
                    this.val$viewActionStop.setGlowing(TacticsExerciseFragment.this._stopFlag);
                    this.val$viewActionStop.setSelected(TacticsExerciseFragment.this._stopFlag);
                }
            });
            return;
        }
        this._exerciseNo.setCompoundDrawables(null, null, null, null);
        this._exerciseNo.setOnClickListener(null);
        this._strapMenuActionHint.setVisibility(8);
        object.setVisibility(8);
        menuBar.setVisibility(0);
        menuBar.setObserver(this);
    }

    private void finishedLoadingNewRating(Rating rating) {
        int n = 0;
        int n2 = rating == null ? 0 : rating.getRating();
        int n3 = n;
        if (this._rating != null) {
            n3 = n;
            if (rating != null) {
                n3 = n2 - this._rating.getRating();
            }
        }
        this._rating = rating;
        this.setExerciseRatingChange(n2, n3);
    }

    private int getPositionForRatingNow(int n, int n2, int n3) {
        return (int)((float)this._translateViewRatingBeforeMarker.getWidth() * (float)(n3 - n2) / (float)(n - n2));
    }

    private void hideExerciseResultAndStartNextExercise(TacticsExerciseUserSolution object) {
        long l = new Date().getTime();
        this._solvedToastView.showLoadingAnimation(false);
        object = new Runnable((TacticsExerciseUserSolution)object){
            final /* synthetic */ TacticsExerciseUserSolution val$result;
            {
                this.val$result = tacticsExerciseUserSolution;
            }

            @Override
            public void run() {
                TacticsExerciseFragment.this.hideResultToast();
                TacticsExerciseFragment.this._gameHolder.setNewTacticsPuzzle(this.val$result);
                TacticsExerciseFragment.this.adjustBoardForCurrentExercise();
                TacticsExerciseFragment.this.startExerciseTimer();
            }
        };
        long l2 = this._timeExerciseResultStartedToShow;
        long l3 = 1500;
        if (l - l2 < l3) {
            this._solvedToastView.postDelayed((Runnable)object, l3 - (l - this._timeExerciseResultStartedToShow));
            return;
        }
        object.run();
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
        for (Object object : this._session.getExercises()) {
            if (!object.isCompleted()) continue;
            ExerciseHistoryView exerciseHistoryView = this._historyBar;
            object = object.isCorrect() ? SolvedIndicatorView.SolveType.CORRECT : SolvedIndicatorView.SolveType.INCORRECT;
            exerciseHistoryView.addSolvedExcercise((SolvedIndicatorView.SolveType)((Object)object));
        }
        this._historyBar.addSolvedExcercise(SolvedIndicatorView.SolveType.RUNNING);
    }

    private void initRatingIndicatorBar() {
        if (this._gameHolder != null) {
            if (this._smallDevice && this._openTranslateView) {
                this.toggleSmallDevActionMenu();
            }
            this.resetHistoryBarWidth();
            int n = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMax().getRating();
            int n2 = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMin().getRating();
            int n3 = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingNow().getRating();
            TextView textView = this._ratingMinView;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n2);
            stringBuilder.append("");
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._ratingMaxView;
            stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append("");
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._ratingRunning;
            stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append("");
            textView.setText((CharSequence)stringBuilder.toString());
            this._calledTranlateViewOnce = false;
            n = this.getPositionForRatingNow(n, n2, n3);
            this._translateViewRatingBeforeMarker.translateViewTo(n);
        }
    }

    private boolean isOwnErrorCode(APIStatusCode aPIStatusCode) {
        HashSet<APIStatusCode> hashSet = new HashSet<APIStatusCode>();
        hashSet.add(APIStatusCode.INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED);
        hashSet.add(APIStatusCode.INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED);
        hashSet.add(APIStatusCode.INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES);
        return hashSet.contains((Object)aPIStatusCode);
    }

    private void loadCurrentSession() {
        this.showWaitingTimer();
        this._tacticsService.getCurrentSession((LoadCommandCallback<TacticsExerciseSession>)new LoadCommandCallbackWithTimeout<TacticsExerciseSession>(){

            @Override
            public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideDialogWaiting();
                        TacticsExerciseFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.loadCurrentSession();
                            }
                        }, true);
                    }

                });
            }

            @Override
            public void succeded(final TacticsExerciseSession tacticsExerciseSession) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

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
        this._tacticsService.exerciseSolved(tacticsExerciseUserSolution, (LoadCommandCallback<Rating>)new LoadCommandCallbackWithTimeout<Rating>(){

            @Override
            public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsExerciseFragment.this.hideResultToast();
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.showExerciseResult(tacticsExerciseUserSolution.isCorrect());
                                TacticsExerciseFragment.this.loadExerciseSolved(tacticsExerciseUserSolution);
                            }
                        }, new IErrorPresenter.ICancelAction(){

                            @Override
                            public void cancelPressed() {
                                TacticsExerciseFragment.this.actionStop();
                            }
                        });
                    }

                });
            }

            @Override
            public void succeded(final Rating rating) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

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
        this._tacticsService.getNextExercise((LoadCommandCallback<TacticsExerciseUserSolution>)new LoadCommandCallbackWithTimeout<TacticsExerciseUserSolution>(){

            @Override
            public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideWaitingTimer();
                        TacticsExerciseFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.loadFirstExercise();
                            }
                        }, true);
                    }

                });
            }

            @Override
            public void succeded(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

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
        this._tacticsService.getNextExercise((LoadCommandCallback<TacticsExerciseUserSolution>)new LoadCommandCallbackWithTimeout<TacticsExerciseUserSolution>(){

            @Override
            public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                TacticsExerciseFragment.this.loadNextExercise();
                            }
                        });
                    }

                });
            }

            @Override
            public void succeded(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
                TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TacticsExerciseFragment.this.hideExerciseResultAndStartNextExercise(tacticsExerciseUserSolution);
                    }
                });
            }

        });
    }

    private void resetHistoryBarWidth() {
        ExerciseHistoryView exerciseHistoryView = this._historyBar;
        int n = !this._openTranslateView ? this._exerciseNo.getMeasuredWidth() : this._numberExerciseViewGroup.getMeasuredWidth();
        exerciseHistoryView.setPlaceHolderWidth(n);
        this._historyBar.requestLayout();
        this._historyBar.fullScroll(66);
    }

    private void setExerciceNo(int n) {
        TextView textView = this._exerciseNo;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        textView.setText((CharSequence)stringBuilder.toString());
    }

    private void setExerciseRatingChange(int n, int n2) {
        this._timeExerciseResultStartedToShow = new Date().getTime();
        SolvedToastView solvedToastView = this._solvedToastView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        stringBuilder.append("(");
        String string = n2 > 0 ? "+" : "";
        stringBuilder.append(string);
        stringBuilder.append(n2);
        stringBuilder.append(")");
        solvedToastView.setText(stringBuilder.toString());
    }

    private boolean shouldStopPuzzles() {
        boolean bl = this._stopFlag;
        SettingsService.TacticsStopMode tacticsStopMode = ServiceProvider.getInstance().getSettingsService().getTacticsStopMode();
        SettingsService.TacticsStopMode tacticsStopMode2 = SettingsService.TacticsStopMode.STOP_AFTER_EVERY;
        boolean bl2 = false;
        boolean bl3 = tacticsStopMode == tacticsStopMode2;
        boolean bl4 = bl2;
        if (tacticsStopMode == SettingsService.TacticsStopMode.STOP_ON_FAILURE) {
            bl4 = bl2;
            if (this._gameHolder.getCurrentTacticsPuzzle() != null) {
                bl4 = bl2;
                if (!this._gameHolder.getCurrentTacticsPuzzle().isCorrect()) {
                    bl4 = true;
                }
            }
        }
        return bl | bl3 | bl4;
    }

    private void showExerciseResult(boolean bl) {
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
        final int n = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingNow().getRating();
        final int n2 = this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMax().getRating();
        final int n3 = this.getPositionForRatingNow(n2, this._gameHolder.getCurrentTacticsPuzzle().getInfo().getUserRatingMin().getRating(), n);
        final int n4 = this._gameHolder.getCurrentTacticsPuzzle().getExercise().getSolvingTimeMillis();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.GERMAN);
        this._timer = new Timer();
        this._timer.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                if (!TacticsExerciseFragment.this.isResumed()) {
                    return;
                }
                int n = (int)(System.currentTimeMillis() - TacticsExerciseFragment.this._startExerciseTime);
                int n2 = n4;
                if (TacticsExerciseFragment.this.isAdded() && !TacticsExerciseFragment.this.isRemoving()) {
                    TacticsExerciseFragment.this.getActivity().runOnUiThread(new Runnable(n2 - n, n){
                        final /* synthetic */ int val$millisGone;
                        final /* synthetic */ int val$millisLeft;
                        {
                            this.val$millisLeft = n;
                            this.val$millisGone = n2;
                        }

                        @Override
                        public void run() {
                            if (this.val$millisLeft >= 0) {
                                TacticsExerciseFragment.this._timeRunning.setText((CharSequence)simpleDateFormat.format(new Date(this.val$millisLeft)));
                            } else {
                                TacticsExerciseFragment.this._timeRunning.setText((CharSequence)simpleDateFormat.format(new Date(0L)));
                            }
                            if (this.val$millisGone >= 10000 && !TacticsExerciseFragment.this._calledTranlateViewOnce) {
                                float f = (float)(this.val$millisGone - 10000) / (float)(n4 - 10000);
                                int n = (int)((float)(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth()) * (1.0f - f));
                                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(n);
                                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(n3, n4 - this.val$millisGone);
                                TacticsExerciseFragment.this._calledTranlateViewOnce = true;
                                return;
                            }
                            if (this.val$millisGone >= 0 && !TacticsExerciseFragment.this._calledTranlateViewOnce) {
                                TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth());
                            }
                        }
                    });
                }
            }

        }, 0L, 200L);
        this._timer.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                if (!TacticsExerciseFragment.this.isResumed()) {
                    return;
                }
                long l = TacticsExerciseFragment.this._startExerciseTime;
                l = System.currentTimeMillis() - (l + 10000L);
                if (l > 0L) {
                    double d;
                    double d2 = d = (double)l / (double)(n4 - 10000);
                    if (d > 1.0) {
                        d2 = 1.0;
                    }
                    int n3 = n2;
                    int n22 = (int)(d2 * (double)(n2 - n));
                    if (TacticsExerciseFragment.this.isAdded() && !TacticsExerciseFragment.this.isRemoving()) {
                        TacticsExerciseFragment.this.getActivity().runOnUiThread(new Runnable(n3 - n22){
                            final /* synthetic */ int val$rating;
                            {
                                this.val$rating = n;
                            }

                            @Override
                            public void run() {
                                TextView textView = TacticsExerciseFragment.this._ratingRunning;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(this.val$rating);
                                stringBuilder.append("");
                                textView.setText((CharSequence)stringBuilder.toString());
                            }
                        });
                    }
                }
            }

        }, 0L, 200L);
        this._translateViewForTimer.post(new Runnable(){

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
        this._numberExerciseViewGroup.post(new Runnable(){

            @Override
            public void run() {
                if (TacticsExerciseFragment.this._openTranslateView) {
                    int n = TacticsExerciseFragment.this._numberExerciseViewGroup.getMeasuredWidth();
                    int n2 = TacticsExerciseFragment.this._exerciseNo.getMeasuredWidth();
                    TacticsExerciseFragment.this._translateViewTopMenu.translateViewTo(n - n2, 500);
                } else {
                    TacticsExerciseFragment.this._translateViewTopMenu.translateViewTo(0, 500);
                }
                TacticsExerciseFragment.this._openTranslateView = TacticsExerciseFragment.this._openTranslateView ^ true;
            }
        });
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
        return false;
    }

    @Override
    public void menuItemClicked(MenuBarItem menuBarItem) {
        if (menuBarItem.getId() == 2131297089) {
            this._stopFlag ^= true;
            menuBarItem.setGlowing(this._stopFlag);
            if (!this._stopFlag) {
                menuBarItem.setSelected(false);
            }
        } else if (menuBarItem.getId() == 2131297088) {
            this.actionHint();
        }
        this._hintItem.setSelected(this._hintActive);
    }

    @Override
    public void menuItemLongClicked(MenuBarItem menuBarItem) {
    }

    @Override
    public boolean needAuthToken() {
        return true;
    }

    @Override
    public boolean needNetwork() {
        return true;
    }

    @Override
    public boolean needRegisteredUser() {
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._tacticsService = ServiceProvider.getInstance().getTacticsExerciseService();
        this._tacticsService.getUserExerciseRating((LoadCommandCallback<Rating>)new LoadCommandCallbackWithTimeout<Rating>(){

            @Override
            public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            }

            @Override
            public void succeded(Rating rating) {
                TacticsExerciseFragment.this._rating = rating;
            }
        });
        if (this.getArguments() != null && this.getArguments().getBoolean(CONTINUE_SESSION, false)) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    TacticsExerciseFragment.this.loadCurrentSession();
                }
            });
            return;
        }
        this._session = this._tacticsService.startNewSession();
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

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
    public View onCreateView(LayoutInflater layoutInflater, final ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427368, viewGroup, false);
        this._hintItem = (MenuBarItem)layoutInflater.findViewById(2131297088);
        this._textBestMoveForColor = (TextView)layoutInflater.findViewById(2131297119);
        this._textBestMoveForColor.setText((CharSequence)"");
        this._bestMoveImageView = (ImageView)layoutInflater.findViewById(2131297085);
        this._ratingMinView = (TextView)layoutInflater.findViewById(2131297128);
        this._ratingMaxView = (TextView)layoutInflater.findViewById(2131297127);
        this._ratingRunning = (TextView)layoutInflater.findViewById(2131297129);
        this._timeRunning = (TextView)layoutInflater.findViewById(2131297130);
        this._translateViewTopMenu = (TranslateViewGroup)layoutInflater.findViewById(2131297092);
        this._translateViewForTimer = (TranslateViewGroup)layoutInflater.findViewById(2131297126);
        this._translateViewRatingBeforeMarker = (TranslateViewGroup)layoutInflater.findViewById(2131297125);
        this._openTranslateView = true;
        this._exerciseNo = (TextView)layoutInflater.findViewById(2131297086);
        this._numberExerciseViewGroup = (NumberExerciseViewGroup)layoutInflater.findViewById(2131297093);
        this._historyBar = (ExerciseHistoryView)layoutInflater.findViewById(2131297087);
        this._historyBar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        this._solvedToastView = (SolvedToastView)layoutInflater.findViewById(2131297121);
        this._solvedToastView.setVisibility(8);
        this._strapMenuActionHint = layoutInflater.findViewById(2131297083);
        this._fieldView = (FieldView)layoutInflater.findViewById(2131297090);
        this._board = BoardViewFactory.getInstance().createBoardView((Context)this.getActivity(), this.getChildFragmentManager(), true, false, false);
        this._board.setKeepScreenOn(true);
        this._fieldView.setBoardView(this._board);
        if (this._gameHolder != null) {
            this.initGameHolderObservers();
        }
        this._fragmentWidth = viewGroup.getWidth();
        this._fragmentHeight = viewGroup.getHeight();
        layoutInflater.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener((View)layoutInflater){
            final /* synthetic */ View val$contentView;
            {
                this.val$contentView = view;
            }

            public void onGlobalLayout() {
                TacticsExerciseFragment.this._fragmentWidth = viewGroup.getWidth();
                TacticsExerciseFragment.this._fragmentHeight = viewGroup.getHeight();
                TacticsExerciseFragment.this.checkForSmallDisplays(this.val$contentView);
                this.val$contentView.getViewTreeObserver().removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                TacticsExerciseFragment.this.initRatingIndicatorBar();
            }
        });
        return layoutInflater;
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
    public void onViewCreated(View object, @Nullable Bundle object2) {
        super.onViewCreated((View)object, (Bundle)object2);
        this.resetHistoryBarWidth();
        if (this._session != null) {
            this.initHistoryBarWithSession();
        }
        if (this._hintActive && this._gameHolder != null) {
            object2 = this._gameHolder.getCurrentTacticsPuzzle().getHint();
            if (object2 != null) {
                this._board.post(new Runnable((Square)object2){
                    final /* synthetic */ Square val$hint;
                    {
                        this.val$hint = square;
                    }

                    @Override
                    public void run() {
                        TacticsExerciseFragment.this._board.markSquare(this.val$hint, -16711936);
                    }
                });
            }
            this._hintItem.setSelected(true);
            this._strapMenuActionHint.setSelected(true);
        }
        if (this._stopFlag) {
            object2 = (MenuBarItem)object.findViewById(2131297089);
            object2.setGlowing(true);
            object2.setSelected(true);
            object = (CustomTextView)object.findViewById(2131297084);
            object.setGlowing(true);
            object.setSelected(true);
        }
        if (this.getResources().getConfiguration().orientation == 2) {
            this._fieldView.post(new Runnable(){

                @Override
                public void run() {
                    int n;
                    TacticsExerciseFragment.access$1200((TacticsExerciseFragment)TacticsExerciseFragment.this).getLayoutParams().width = n = TacticsExerciseFragment.this._fieldView.getMeasuredHeight();
                    TacticsExerciseFragment.access$1200((TacticsExerciseFragment)TacticsExerciseFragment.this).getLayoutParams().height = n;
                }
            });
        }
    }

    @Override
    public void positionChanged(Position position, Move move) {
        this._hintActive = false;
        this._hintItem.setSelected(false);
        this._strapMenuActionHint.setSelected(false);
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    @Override
    protected void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction, IErrorPresenter.ICancelAction iCancelAction) {
        if (this.isOwnErrorCode(aPIStatusCode)) {
            this.hideResultToast();
            if (this.getActivity() != null) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.TACTICS, "premium screen shown", null);
                this.getContentPresenter().showConversionDialog(null, ConversionContext.TACTICS);
                this._timeExerciseResultStartedToShow = System.currentTimeMillis();
                this.actionStop();
                return;
            }
        } else {
            super.showViewForErrorCode(aPIStatusCode, iReloadAction, iCancelAction);
        }
    }

    @Override
    protected void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction, boolean bl) {
        if (this.isOwnErrorCode(aPIStatusCode)) {
            this.hideResultToast();
            if (this.getActivity() != null) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.TACTICS, "premium screen shown", null);
                this.getContentPresenter().showConversionDialog(null, ConversionContext.TACTICS);
                this._timeExerciseResultStartedToShow = System.currentTimeMillis();
                this.actionStop();
                return;
            }
        } else {
            super.showViewForErrorCode(aPIStatusCode, iReloadAction, bl);
        }
    }

    @Override
    public void tacticsSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        final boolean bl = tacticsExerciseUserSolution.isCorrect();
        tacticsExerciseUserSolution.setTimeUsed((int)(System.currentTimeMillis() - this._startExerciseTime));
        this._historyBar.post(new Runnable(){

            @Override
            public void run() {
                TacticsExerciseFragment.this.stopExerciseTimer();
                ExerciseHistoryView exerciseHistoryView = TacticsExerciseFragment.this._historyBar;
                SolvedIndicatorView.SolveType solveType = bl ? SolvedIndicatorView.SolveType.CORRECT : SolvedIndicatorView.SolveType.INCORRECT;
                exerciseHistoryView.addSolvedExcercise(solveType);
                TacticsExerciseFragment.this.setExerciceNo(TacticsExerciseFragment.this._session.getExercises().size());
                TacticsExerciseFragment.this.showExerciseResult(tacticsExerciseUserSolution.isCorrect());
            }
        });
        this.loadExerciseSolved(tacticsExerciseUserSolution);
    }

}
