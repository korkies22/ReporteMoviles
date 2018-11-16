// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote;

import android.widget.TextView;
import de.cisha.android.board.playzone.view.TimeControlView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.playzone.remote.model.EloRange;
import android.view.View.OnClickListener;
import de.cisha.chess.model.ClockSetting;
import android.content.Intent;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.view.RegisterButtonOverlayLayout;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.user.User;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.playzone.remote.view.PairingRangeSetupView;
import de.cisha.android.view.PremiumButtonOverlayLayout;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.List;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.BaseFragment;

public class PairingSetupFragment extends BaseFragment implements IUserDataChangedListener
{
    private static final String SAVE_INSTANCE_STATE_PAIRING_SETUP_RANGE_MAX_VALUE = "pairing_setup_range_max_value";
    private static final String SAVE_INSTANCE_STATE_PAIRING_SETUP_RANGE_MIN_VALUE = "pairing_setup_range_min_value";
    private List<TimeControl> _availableClocks;
    private IContentPresenter _contentPresenter;
    private PremiumButtonOverlayLayout _eloRangeOverlay;
    private PairingRangeSetupView _eloRangeView;
    private RecyclerView _recyclerView;
    private View _timeControlLoadingView;
    private TimeControlsAdapter _timeControlsAdapter;
    
    private void loadAvailableClocks() {
        if (this._availableClocks == null) {
            ServiceProvider.getInstance().getPlayzoneService().getAvailableClocks(new LoadCommandCallbackWithTimeout<List<TimeControl>>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                }
                
                @Override
                protected void succeded(final List<TimeControl> list) {
                    PairingSetupFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                        @Override
                        public void run() {
                            PairingSetupFragment.this._availableClocks = list;
                            PairingSetupFragment.this._recyclerView.setVisibility(0);
                            PairingSetupFragment.this._timeControlLoadingView.setVisibility(8);
                            ((RecyclerView.Adapter)PairingSetupFragment.this._timeControlsAdapter).notifyDataSetChanged();
                        }
                    });
                }
            });
            return;
        }
        this._recyclerView.setVisibility(0);
        this._timeControlLoadingView.setVisibility(8);
    }
    
    private void setPremiumWallForUser(final User user) {
        if (user != null) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                final /* synthetic */ boolean val.enableRegisterEntryPoint = user.isGuest();
                
                @Override
                public void run() {
                    if (PairingSetupFragment.this._eloRangeOverlay != null) {
                        PairingSetupFragment.this._eloRangeOverlay.setPremiumOverlayEnabled(this.val.enableRegisterEntryPoint);
                    }
                }
            });
        }
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        ServiceProvider.getInstance().getProfileDataService().addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        return layoutInflater.inflate(2131427481, viewGroup, false);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        this.loadAvailableClocks();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        final SharedPreferences preferences = this.getActivity().getPreferences(0);
        if (preferences != null) {
            final String userPrefix = ServiceProvider.getInstance().getLoginService().getUserPrefix();
            final SharedPreferences.Editor edit = preferences.edit();
            final StringBuilder sb = new StringBuilder();
            sb.append(userPrefix);
            sb.append("pairing_setup_range_min_value");
            final SharedPreferences.Editor putInt = edit.putInt(sb.toString(), this._eloRangeView.getSelectedMinRating());
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(userPrefix);
            sb2.append("pairing_setup_range_max_value");
            putInt.putInt(sb2.toString(), this._eloRangeView.getSelectedMaxRating()).commit();
        }
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        (this._recyclerView = (RecyclerView)view.findViewById(2131296654)).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this.getActivity(), this.getResources().getInteger(2131361806)));
        this._timeControlsAdapter = new TimeControlsAdapter();
        this._recyclerView.setAdapter((RecyclerView.Adapter)this._timeControlsAdapter);
        this._timeControlLoadingView = view.findViewById(2131296649);
        this._eloRangeView = (PairingRangeSetupView)view.findViewById(2131296650);
        (this._eloRangeOverlay = (RegisterButtonOverlayLayout)view.findViewById(2131296651)).setHideBeforeClickedEnabled(false);
        this._eloRangeOverlay.setOverlayButtonAction(new BoardAction() {
            @Override
            public void perform() {
                final Intent intent = new Intent(PairingSetupFragment.this.getActivity().getApplicationContext(), (Class)LoginActivity.class);
                intent.setFlags(131072);
                PairingSetupFragment.this.startActivity(intent);
            }
        });
        this.setPremiumWallForUser(ServiceProvider.getInstance().getProfileDataService().getCurrentUserData());
        final SharedPreferences preferences = this.getActivity().getPreferences(0);
        if (preferences != null) {
            final String userPrefix = ServiceProvider.getInstance().getLoginService().getUserPrefix();
            final StringBuilder sb = new StringBuilder();
            sb.append(userPrefix);
            sb.append("pairing_setup_range_min_value");
            final int int1 = preferences.getInt(sb.toString(), -500);
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(userPrefix);
            sb2.append("pairing_setup_range_max_value");
            this._eloRangeView.setEloRange(int1, preferences.getInt(sb2.toString(), 500));
        }
    }
    
    public void setContentPresenter(final IContentPresenter contentPresenter) {
        this._contentPresenter = contentPresenter;
    }
    
    @Override
    public void userDataChanged(final User premiumWallForUser) {
        this.setPremiumWallForUser(premiumWallForUser);
    }
    
    private class TimeControlsAdapter extends Adapter<TimeControlViewHolder>
    {
        @Override
        public int getItemCount() {
            if (PairingSetupFragment.this._availableClocks != null) {
                return PairingSetupFragment.this._availableClocks.size();
            }
            return 0;
        }
        
        public void onBindViewHolder(final TimeControlViewHolder timeControlViewHolder, final int n) {
            if (PairingSetupFragment.this._availableClocks != null) {
                final TimeControl timeControl = PairingSetupFragment.this._availableClocks.get(n);
                timeControlViewHolder._timeControl.setTimeControlValue(timeControl.getMinutes(), timeControl.getIncrement());
                timeControlViewHolder._timeControlType.setText((CharSequence)new ClockSetting(timeControl.getMinutes() * 60, timeControl.getMinutes()).getClockType().getString(PairingSetupFragment.this.getResources()));
                timeControlViewHolder.itemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        PairingSetupFragment.this._contentPresenter.showFragment(PlayzoneRemoteFragment.createFragmentWithTimeControl(timeControl, new EloRange(PairingSetupFragment.this._eloRangeView.getMinValue(), PairingSetupFragment.this._eloRangeView.getMaxValue())), true, true);
                    }
                });
            }
        }
        
        public TimeControlViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new TimeControlViewHolder(LayoutInflater.from((Context)PairingSetupFragment.this.getActivity()).inflate(2131427483, viewGroup, false));
        }
        
        public class TimeControlViewHolder extends ViewHolder
        {
            private TimeControlView _timeControl;
            private TextView _timeControlType;
            
            public TimeControlViewHolder(final View view) {
                super(view);
                this._timeControl = (TimeControlView)view.findViewById(2131296658);
                this._timeControlType = (TextView)view.findViewById(2131296657);
            }
        }
    }
}
