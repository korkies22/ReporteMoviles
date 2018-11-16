// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics;

import de.cisha.android.board.account.StoreFragment;
import android.view.View.OnClickListener;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import java.util.TreeSet;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import java.util.TimerTask;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.ServiceProvider;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.IContentPresenter;
import de.cisha.chess.model.Rating;
import java.util.Timer;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.LoadingHelperWithAPIStatusCode;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.AbstractContentFragment;

public class TacticsStartFragment extends AbstractContentFragment implements TacticsSettingObserver, LoadingHelperListener
{
    private ITacticsExerciseService.TacticsTrainerInfo _dailyPuzzlesInfo;
    private LoadingHelperWithAPIStatusCode _loadingHelper;
    private long _onStartTimeMillis;
    private TextView _ratingTextView;
    private View _startButton;
    private TextView _stopModeTextView;
    private TextView _timeOutTextView;
    private Timer _timer;
    private Rating _usersRating;
    private View _viewgroupGoPremium;
    private View _viewgroupStartButton;
    
    private void loadDailyPuzzlesInfo() {
        final LoadCommandCallbackWithTimeout<ITacticsExerciseService.TacticsTrainerInfo> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<ITacticsExerciseService.TacticsTrainerInfo>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsStartFragment.this._loadingHelper.loadingFailed(this, apiStatusCode);
            }
            
            @Override
            protected void succeded(final ITacticsExerciseService.TacticsTrainerInfo tacticsTrainerInfo) {
                TacticsStartFragment.this._dailyPuzzlesInfo = tacticsTrainerInfo;
                TacticsStartFragment.this._onStartTimeMillis = System.currentTimeMillis();
                TacticsStartFragment.this._loadingHelper.loadingCompleted(this);
            }
        };
        this._loadingHelper.addLoadable(loadCommandCallbackWithTimeout);
        ServiceProvider.getInstance().getTacticsExerciseService().getTacticsTrainerInfo(loadCommandCallbackWithTimeout);
    }
    
    private void loadData() {
        this.loadUsersRating();
        this.loadDailyPuzzlesInfo();
    }
    
    private void loadUsersRating() {
        final LoadCommandCallbackWithTimeout<Rating> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Rating>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                TacticsStartFragment.this._loadingHelper.loadingFailed(this, apiStatusCode);
            }
            
            @Override
            protected void succeded(final Rating rating) {
                TacticsStartFragment.this._usersRating = rating;
                TacticsStartFragment.this._ratingTextView.setText((CharSequence)rating.getRatingString());
                TacticsStartFragment.this._loadingHelper.loadingCompleted(this);
            }
        };
        this._loadingHelper.addLoadable(loadCommandCallbackWithTimeout);
        ServiceProvider.getInstance().getTacticsExerciseService().getUserExerciseRating(loadCommandCallbackWithTimeout);
    }
    
    private void updateViews() {
        if (this.getView() != null) {
            this._startButton.setEnabled(this._dailyPuzzlesInfo != null);
            if (this._dailyPuzzlesInfo != null) {
                if (this._dailyPuzzlesInfo.isHasLimit() && this._dailyPuzzlesInfo.getNumberOfPuzzlesLeft() == 0) {
                    this._viewgroupGoPremium.setVisibility(0);
                    this._viewgroupStartButton.setVisibility(8);
                    this._timeOutTextView.setText((CharSequence)ChessClock.formatTime(this._dailyPuzzlesInfo.getTimeLeft(), true));
                    if (this._timer != null) {
                        this._timer.cancel();
                    }
                    (this._timer = new Timer()).schedule(new TimerTask() {
                        @Override
                        public void run() {
                            TacticsStartFragment.this._timeOutTextView.post((Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    TacticsStartFragment.this._timeOutTextView.setText((CharSequence)ChessClock.formatTime(TacticsStartFragment.this._dailyPuzzlesInfo.getTimeLeft() - (System.currentTimeMillis() - TacticsStartFragment.this._onStartTimeMillis), true));
                                }
                            });
                        }
                    }, 1000L, 1000L);
                    this._timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            TacticsStartFragment.this._timer.cancel();
                            TacticsStartFragment.this.loadDailyPuzzlesInfo();
                        }
                    }, this._dailyPuzzlesInfo.getTimeLeft());
                    return;
                }
                this._viewgroupGoPremium.setVisibility(8);
                this._viewgroupStartButton.setVisibility(0);
            }
        }
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
        final TreeSet<SettingsMenuCategory> set = new TreeSet<SettingsMenuCategory>();
        set.add(SettingsMenuCategory.TACTICS);
        set.add(SettingsMenuCategory.BOARD);
        return set;
    }
    
    @Override
    public String getTrackingName() {
        return "TacticsStartScreen";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void loadingStart() {
        this.showDialogWaiting(true, true, "", null);
    }
    
    @Override
    public void loadingStop(final boolean b) {
        this.hideDialogWaiting();
        if (b) {
            this.updateViews();
            return;
        }
        this.showViewForErrorCode(this._loadingHelper.getAPIStatusCodeFromLoadingFailed(), new IErrorPresenter.IReloadAction() {
            @Override
            public void performReload() {
                TacticsStartFragment.this.loadData();
            }
        }, true);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._loadingHelper = new LoadingHelperWithAPIStatusCode(this);
        ServiceProvider.getInstance().getSettingsService().addTacticsObserver((SettingsService.TacticsSettingObserver)this);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TacticsStartFragment.this.loadData();
            }
        });
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427561, viewGroup, false);
        (this._timeOutTextView = (TextView)inflate.findViewById(2131297116)).setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/DS-DIGI.TTF"));
        this._viewgroupStartButton = inflate.findViewById(2131297112);
        (this._viewgroupGoPremium = inflate.findViewById(2131297109)).setVisibility(8);
        this._ratingTextView = (TextView)inflate.findViewById(2131297111);
        (this._stopModeTextView = (TextView)inflate.findViewById(2131297115)).setText(ServiceProvider.getInstance().getSettingsService().getTacticsStopMode().getTitleResId());
        inflate.findViewById(2131297117).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TacticsStartFragment.this.getContentPresenter().showFragment(new StoreFragment(), false, true);
            }
        });
        return inflate;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        ServiceProvider.getInstance().getSettingsService().removeTacticsObserver((SettingsService.TacticsSettingObserver)this);
        if (this._timer != null) {
            this._timer.cancel();
            this._timer = null;
        }
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        (this._startButton = this.getView().findViewById(2131297118)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TacticsStartFragment.this.getContentPresenter().showFragment(new TacticsExerciseFragment(), false, false);
            }
        });
        if (this._usersRating != null) {
            this._ratingTextView.setText((CharSequence)this._usersRating.getRatingString());
        }
        this.updateViews();
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    @Override
    public void tacticsSettingsChanged() {
        this._stopModeTextView.setText(ServiceProvider.getInstance().getSettingsService().getTacticsStopMode().getTitleResId());
    }
}
