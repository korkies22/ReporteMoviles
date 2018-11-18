// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine;

import java.util.concurrent.Executors;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.playzone.model.TimeControl;
import android.content.Context;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.BaseFragment;

public class EngineOnlineOpponentChooserViewFragment extends BaseFragment implements OnlineEngineChooserListener, OnRefreshListener
{
    private EngineOnlineOpponentChooserView _engineOnlineOpponentView;
    private OnlineEngineChooserListener _listener;
    private SwipeRefreshLayout _refreshLayout;
    
    private void loadOpponents() {
        ServiceProvider.getInstance().getPlayzoneService().getOnlineEngineOpponents(new LoadCommandCallbackWithTimeout<List<OnlineEngineOpponent>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.clearAnimation(); // IMPLEMENT THIS
                        EngineOnlineOpponentChooserViewFragment.this._refreshLayout.setRefreshing(false);
                    }
                });
            }
            
            @Override
            protected void succeded(final List<OnlineEngineOpponent> list) {
                EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        (this._engineOnlineOpponentView = new EngineOnlineOpponentChooserView((Context)this.getActivity())).setOnlineEngineChooserListener((EngineOnlineOpponentChooserView.OnlineEngineChooserListener)this);
        (this._refreshLayout = this._engineOnlineOpponentView.getViewSwipeRefreshLayout()).setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)this);
        return (View)this._engineOnlineOpponentView;
    }
    
    @Override
    public void onOnlineEngineChosen(final String s, final TimeControl timeControl, final boolean b) {
        if (this._listener != null) {
            this._listener.onOnlineEngineChosen(s, timeControl, b);
        }
    }
    
    @Override
    public void onRefresh() {
        this.loadOpponents();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        ServiceProvider.getInstance().getPlayzoneService().getBackgroundGameHolderService((Context)this.getActivity(), (PlayzoneService.BinderCallback)new PlayzoneService.BinderCallback() {
            @Override
            public void onServiceBinded(final GameBackgroundHolderService gameBackgroundHolderService) {
                final RemoteGameAdapter runningGameAdapter = gameBackgroundHolderService.getRunningGameAdapter(PlayzoneRemoteFragment.class.getName());
                if (runningGameAdapter != null && runningGameAdapter.isGameActive()) {
                    final PlayzoneGameSessionInfo gameSessionInfo = runningGameAdapter.getGameSessionInfo();
                    runningGameAdapter.getChessClock().addOnClockListener(EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView);
                    EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setResumeGameEnabled(runningGameAdapter.getOpponent().getName(), new BoardAction() {
                        @Override
                        public void perform() {
                            if (EngineOnlineOpponentChooserViewFragment.this._listener != null) {
                                EngineOnlineOpponentChooserViewFragment.this._listener.resumeEngineOnlineGame(gameSessionInfo);
                            }
                        }
                    });
                    return;
                }
                gameBackgroundHolderService.stopServiceIfNoGameActive();
            }
        });
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    final /* synthetic */ List val.onlineEngineOpponents = ServiceProvider.getInstance().getPlayzoneService().getOnlineEngineOpponents();
                    
                    @Override
                    public void run() {
                        EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setOnlineOpponents(this.val.onlineEngineOpponents);
                    }
                });
                EngineOnlineOpponentChooserViewFragment.this.loadOpponents();
            }
        });
    }
    
    @Override
    public void resumeEngineOnlineGame(final PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        if (this._listener != null) {
            this._listener.resumeEngineOnlineGame(playzoneGameSessionInfo);
        }
    }
    
    public void setOnlineEngineChooserListener(final OnlineEngineChooserListener listener) {
        this._listener = listener;
    }
}
