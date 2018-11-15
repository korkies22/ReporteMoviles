/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.engine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.Opponent;
import java.util.List;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class EngineOnlineOpponentChooserViewFragment
extends BaseFragment
implements EngineOnlineOpponentChooserView.OnlineEngineChooserListener,
SwipeRefreshLayout.OnRefreshListener {
    private EngineOnlineOpponentChooserView _engineOnlineOpponentView;
    private EngineOnlineOpponentChooserView.OnlineEngineChooserListener _listener;
    private SwipeRefreshLayout _refreshLayout;

    private void loadOpponents() {
        ServiceProvider.getInstance().getPlayzoneService().getOnlineEngineOpponents((LoadCommandCallback<List<OnlineEngineOpponent>>)new LoadCommandCallbackWithTimeout<List<OnlineEngineOpponent>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        EngineOnlineOpponentChooserViewFragment.this._refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            protected void succeded(final List<OnlineEngineOpponent> list) {
                EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setOnlineOpponents(list);
                        EngineOnlineOpponentChooserViewFragment.this._refreshLayout.setRefreshing(false);
                    }
                });
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this._engineOnlineOpponentView = new EngineOnlineOpponentChooserView((Context)this.getActivity());
        this._engineOnlineOpponentView.setOnlineEngineChooserListener(this);
        this._refreshLayout = this._engineOnlineOpponentView.getViewSwipeRefreshLayout();
        this._refreshLayout.setOnRefreshListener(this);
        return this._engineOnlineOpponentView;
    }

    @Override
    public void onOnlineEngineChosen(String string, TimeControl timeControl, boolean bl) {
        if (this._listener != null) {
            this._listener.onOnlineEngineChosen(string, timeControl, bl);
        }
    }

    @Override
    public void onRefresh() {
        this.loadOpponents();
    }

    @Override
    public void onStart() {
        super.onStart();
        ServiceProvider.getInstance().getPlayzoneService().getBackgroundGameHolderService((Context)this.getActivity(), new PlayzoneService.BinderCallback(){

            @Override
            public void onServiceBinded(GameBackgroundHolderService object) {
                Object object2 = object.getRunningGameAdapter(PlayzoneRemoteFragment.class.getName());
                if (object2 != null && object2.isGameActive()) {
                    object = object2.getGameSessionInfo();
                    object2.getChessClock().addOnClockListener(EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView);
                    object2 = object2.getOpponent().getName();
                    EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setResumeGameEnabled((String)object2, new BoardAction((PlayzoneGameSessionInfo)object){
                        final /* synthetic */ PlayzoneGameSessionInfo val$sessionInfo;
                        {
                            this.val$sessionInfo = playzoneGameSessionInfo;
                        }

                        @Override
                        public void perform() {
                            if (EngineOnlineOpponentChooserViewFragment.this._listener != null) {
                                EngineOnlineOpponentChooserViewFragment.this._listener.resumeEngineOnlineGame(this.val$sessionInfo);
                            }
                        }
                    });
                    return;
                }
                object.stopServiceIfNoGameActive();
            }

        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Executors.newSingleThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                final List<OnlineEngineOpponent> list = ServiceProvider.getInstance().getPlayzoneService().getOnlineEngineOpponents();
                EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setOnlineOpponents(list);
                    }
                });
                EngineOnlineOpponentChooserViewFragment.this.loadOpponents();
            }

        });
    }

    @Override
    public void resumeEngineOnlineGame(PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        if (this._listener != null) {
            this._listener.resumeEngineOnlineGame(playzoneGameSessionInfo);
        }
    }

    public void setOnlineEngineChooserListener(EngineOnlineOpponentChooserView.OnlineEngineChooserListener onlineEngineChooserListener) {
        this._listener = onlineEngineChooserListener;
    }

}
