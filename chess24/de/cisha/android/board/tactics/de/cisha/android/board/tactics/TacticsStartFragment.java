/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.LoadingHelperWithAPIStatusCode;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.chess.model.Rating;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import org.json.JSONObject;

public class TacticsStartFragment
extends AbstractContentFragment
implements SettingsService.TacticsSettingObserver,
LoadingHelper.LoadingHelperListener {
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
        LoadCommandCallbackWithTimeout<ITacticsExerciseService.TacticsTrainerInfo> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<ITacticsExerciseService.TacticsTrainerInfo>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsStartFragment.this._loadingHelper.loadingFailed(this, aPIStatusCode);
            }

            @Override
            protected void succeded(ITacticsExerciseService.TacticsTrainerInfo tacticsTrainerInfo) {
                TacticsStartFragment.this._dailyPuzzlesInfo = tacticsTrainerInfo;
                TacticsStartFragment.this._onStartTimeMillis = System.currentTimeMillis();
                TacticsStartFragment.this._loadingHelper.loadingCompleted(this);
            }
        };
        this._loadingHelper.addLoadable(loadCommandCallbackWithTimeout);
        ServiceProvider.getInstance().getTacticsExerciseService().getTacticsTrainerInfo((LoadCommandCallback<ITacticsExerciseService.TacticsTrainerInfo>)loadCommandCallbackWithTimeout);
    }

    private void loadData() {
        this.loadUsersRating();
        this.loadDailyPuzzlesInfo();
    }

    private void loadUsersRating() {
        LoadCommandCallbackWithTimeout<Rating> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Rating>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TacticsStartFragment.this._loadingHelper.loadingFailed(this, aPIStatusCode);
            }

            @Override
            protected void succeded(Rating rating) {
                TacticsStartFragment.this._usersRating = rating;
                TacticsStartFragment.this._ratingTextView.setText((CharSequence)rating.getRatingString());
                TacticsStartFragment.this._loadingHelper.loadingCompleted(this);
            }
        };
        this._loadingHelper.addLoadable(loadCommandCallbackWithTimeout);
        ServiceProvider.getInstance().getTacticsExerciseService().getUserExerciseRating((LoadCommandCallback<Rating>)loadCommandCallbackWithTimeout);
    }

    private void updateViews() {
        if (this.getView() != null) {
            View view = this._startButton;
            boolean bl = this._dailyPuzzlesInfo != null;
            view.setEnabled(bl);
            if (this._dailyPuzzlesInfo != null) {
                if (this._dailyPuzzlesInfo.isHasLimit() && this._dailyPuzzlesInfo.getNumberOfPuzzlesLeft() == 0) {
                    this._viewgroupGoPremium.setVisibility(0);
                    this._viewgroupStartButton.setVisibility(8);
                    this._timeOutTextView.setText((CharSequence)ChessClock.formatTime(this._dailyPuzzlesInfo.getTimeLeft(), true));
                    if (this._timer != null) {
                        this._timer.cancel();
                    }
                    this._timer = new Timer();
                    this._timer.schedule(new TimerTask(){

                        @Override
                        public void run() {
                            TacticsStartFragment.this._timeOutTextView.post(new Runnable(){

                                @Override
                                public void run() {
                                    TacticsStartFragment.this._timeOutTextView.setText((CharSequence)ChessClock.formatTime(TacticsStartFragment.this._dailyPuzzlesInfo.getTimeLeft() - (System.currentTimeMillis() - TacticsStartFragment.this._onStartTimeMillis), true));
                                }
                            });
                        }

                    }, 1000L, 1000L);
                    this._timer.schedule(new TimerTask(){

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
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690385);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.TACTIC;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        TreeSet<SettingsMenuCategory> treeSet = new TreeSet<SettingsMenuCategory>();
        treeSet.add(SettingsMenuCategory.TACTICS);
        treeSet.add(SettingsMenuCategory.BOARD);
        return treeSet;
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
    public void loadingStop(boolean bl) {
        this.hideDialogWaiting();
        if (bl) {
            this.updateViews();
            return;
        }
        this.showViewForErrorCode(this._loadingHelper.getAPIStatusCodeFromLoadingFailed(), new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                TacticsStartFragment.this.loadData();
            }
        }, true);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._loadingHelper = new LoadingHelperWithAPIStatusCode(this);
        ServiceProvider.getInstance().getSettingsService().addTacticsObserver(this);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsStartFragment.this.loadData();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427561, (ViewGroup)object, false);
        this._timeOutTextView = (TextView)layoutInflater.findViewById(2131297116);
        this._timeOutTextView.setTypeface(Typeface.createFromAsset((AssetManager)this.getActivity().getAssets(), (String)"fonts/DS-DIGI.TTF"));
        this._viewgroupStartButton = layoutInflater.findViewById(2131297112);
        this._viewgroupGoPremium = layoutInflater.findViewById(2131297109);
        this._viewgroupGoPremium.setVisibility(8);
        this._ratingTextView = (TextView)layoutInflater.findViewById(2131297111);
        object = ServiceProvider.getInstance().getSettingsService().getTacticsStopMode();
        this._stopModeTextView = (TextView)layoutInflater.findViewById(2131297115);
        this._stopModeTextView.setText(object.getTitleResId());
        layoutInflater.findViewById(2131297117).setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = new StoreFragment();
                TacticsStartFragment.this.getContentPresenter().showFragment((AbstractContentFragment)object, false, true);
            }
        });
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServiceProvider.getInstance().getSettingsService().removeTacticsObserver(this);
        if (this._timer != null) {
            this._timer.cancel();
            this._timer = null;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._startButton = this.getView().findViewById(2131297118);
        this._startButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
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
