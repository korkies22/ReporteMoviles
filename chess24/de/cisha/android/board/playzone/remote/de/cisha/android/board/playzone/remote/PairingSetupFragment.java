/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.view.PairingRangeSetupView;
import de.cisha.android.board.playzone.view.TimeControlView;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.android.view.PremiumButtonOverlayLayout;
import de.cisha.android.view.RegisterButtonOverlayLayout;
import de.cisha.chess.model.ClockSetting;
import java.util.List;
import org.json.JSONObject;

public class PairingSetupFragment
extends BaseFragment
implements IProfileDataService.IUserDataChangedListener {
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
            ServiceProvider.getInstance().getPlayzoneService().getAvailableClocks((LoadCommandCallback<List<TimeControl>>)new LoadCommandCallbackWithTimeout<List<TimeControl>>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                }

                @Override
                protected void succeded(final List<TimeControl> list) {
                    PairingSetupFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                        @Override
                        public void run() {
                            PairingSetupFragment.this._availableClocks = list;
                            PairingSetupFragment.this._recyclerView.setVisibility(0);
                            PairingSetupFragment.this._timeControlLoadingView.setVisibility(8);
                            PairingSetupFragment.this._timeControlsAdapter.notifyDataSetChanged();
                        }
                    });
                }

            });
            return;
        }
        this._recyclerView.setVisibility(0);
        this._timeControlLoadingView.setVisibility(8);
    }

    private void setPremiumWallForUser(User user) {
        if (user != null) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable(user.isGuest()){
                final /* synthetic */ boolean val$enableRegisterEntryPoint;
                {
                    this.val$enableRegisterEntryPoint = bl;
                }

                @Override
                public void run() {
                    if (PairingSetupFragment.this._eloRangeOverlay != null) {
                        PairingSetupFragment.this._eloRangeOverlay.setPremiumOverlayEnabled(this.val$enableRegisterEntryPoint);
                    }
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ServiceProvider.getInstance().getProfileDataService().addUserDataChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
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
        SharedPreferences sharedPreferences = this.getActivity().getPreferences(0);
        if (sharedPreferences != null) {
            String string = ServiceProvider.getInstance().getLoginService().getUserPrefix();
            sharedPreferences = sharedPreferences.edit();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(SAVE_INSTANCE_STATE_PAIRING_SETUP_RANGE_MIN_VALUE);
            sharedPreferences = sharedPreferences.putInt(stringBuilder.toString(), this._eloRangeView.getSelectedMinRating());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(SAVE_INSTANCE_STATE_PAIRING_SETUP_RANGE_MAX_VALUE);
            sharedPreferences.putInt(stringBuilder.toString(), this._eloRangeView.getSelectedMaxRating()).commit();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle object) {
        super.onViewCreated(view, (Bundle)object);
        this._recyclerView = (RecyclerView)view.findViewById(2131296654);
        int n = this.getResources().getInteger(2131361806);
        this._recyclerView.setLayoutManager(new GridLayoutManager((Context)this.getActivity(), n));
        this._timeControlsAdapter = new TimeControlsAdapter();
        this._recyclerView.setAdapter(this._timeControlsAdapter);
        this._timeControlLoadingView = view.findViewById(2131296649);
        this._eloRangeView = (PairingRangeSetupView)view.findViewById(2131296650);
        this._eloRangeOverlay = (RegisterButtonOverlayLayout)view.findViewById(2131296651);
        this._eloRangeOverlay.setHideBeforeClickedEnabled(false);
        this._eloRangeOverlay.setOverlayButtonAction(new BoardAction(){

            @Override
            public void perform() {
                Intent intent = new Intent(PairingSetupFragment.this.getActivity().getApplicationContext(), LoginActivity.class);
                intent.setFlags(131072);
                PairingSetupFragment.this.startActivity(intent);
            }
        });
        this.setPremiumWallForUser(ServiceProvider.getInstance().getProfileDataService().getCurrentUserData());
        view = this.getActivity().getPreferences(0);
        if (view != null) {
            object = ServiceProvider.getInstance().getLoginService().getUserPrefix();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(SAVE_INSTANCE_STATE_PAIRING_SETUP_RANGE_MIN_VALUE);
            n = view.getInt(stringBuilder.toString(), -500);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(SAVE_INSTANCE_STATE_PAIRING_SETUP_RANGE_MAX_VALUE);
            int n2 = view.getInt(stringBuilder.toString(), 500);
            this._eloRangeView.setEloRange(n, n2);
        }
    }

    public void setContentPresenter(IContentPresenter iContentPresenter) {
        this._contentPresenter = iContentPresenter;
    }

    @Override
    public void userDataChanged(User user) {
        this.setPremiumWallForUser(user);
    }

    private class TimeControlsAdapter
    extends RecyclerView.Adapter<TimeControlsAdapter$TimeControlViewHolder> {
        private TimeControlsAdapter() {
        }

        @Override
        public int getItemCount() {
            if (PairingSetupFragment.this._availableClocks != null) {
                return PairingSetupFragment.this._availableClocks.size();
            }
            return 0;
        }

        @Override
        public void onBindViewHolder(TimeControlsAdapter$TimeControlViewHolder timeControlsAdapter$TimeControlViewHolder, int n) {
            if (PairingSetupFragment.this._availableClocks != null) {
                final TimeControl timeControl = (TimeControl)PairingSetupFragment.this._availableClocks.get(n);
                timeControlsAdapter$TimeControlViewHolder._timeControl.setTimeControlValue(timeControl.getMinutes(), timeControl.getIncrement());
                ClockSetting.GameClockType gameClockType = new ClockSetting(timeControl.getMinutes() * 60, timeControl.getMinutes()).getClockType();
                timeControlsAdapter$TimeControlViewHolder._timeControlType.setText((CharSequence)gameClockType.getString(PairingSetupFragment.this.getResources()));
                timeControlsAdapter$TimeControlViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View object) {
                        object = new EloRange(PairingSetupFragment.this._eloRangeView.getMinValue(), PairingSetupFragment.this._eloRangeView.getMaxValue());
                        object = PlayzoneRemoteFragment.createFragmentWithTimeControl(timeControl, (EloRange)object);
                        PairingSetupFragment.this._contentPresenter.showFragment((AbstractContentFragment)object, true, true);
                    }
                });
            }
        }

        @Override
        public TimeControlsAdapter$TimeControlViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            return new TimeControlsAdapter$TimeControlViewHolder(LayoutInflater.from((Context)PairingSetupFragment.this.getActivity()).inflate(2131427483, viewGroup, false));
        }

    }

    public class TimeControlsAdapter$TimeControlViewHolder
    extends RecyclerView.ViewHolder {
        private TimeControlView _timeControl;
        private TextView _timeControlType;

        public TimeControlsAdapter$TimeControlViewHolder(View view) {
            super(view);
            this._timeControl = (TimeControlView)view.findViewById(2131296658);
            this._timeControlType = (TextView)view.findViewById(2131296657);
        }
    }

}
