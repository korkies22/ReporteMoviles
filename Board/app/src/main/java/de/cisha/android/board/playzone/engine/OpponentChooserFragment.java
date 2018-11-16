// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.os.Bundle;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Arrays;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.AbstractContentFragment;

public class OpponentChooserFragment extends AbstractContentFragment implements EngineOpponentChooserListener, OnlineEngineChooserListener
{
    private void resumeGame(final PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.getContentPresenter().showFragment(PlayzoneRemoteFragment.createFragmentWithSessionToken(playzoneGameSessionInfo, true), false, true);
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690112);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.PLAYVSCOMPUTER;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return new TreeSet<SettingsMenuCategory>(Arrays.asList(SettingsMenuCategory.BOARD, SettingsMenuCategory.PLAYZONE));
    }
    
    @Override
    public String getTrackingName() {
        return "PlayzoneEngine";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void onChoosen(final TimeControl timeControl, final int n, final String s, final boolean b) {
        this.getContentPresenter().showFragment(PlayzoneEngineFragment.createFragment(timeControl, n, s, b), true, true);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        ServiceProvider.getInstance().getPlayzoneService().getOpenGames(new LoadCommandCallbackWithTimeout<List<PlayzoneGameSessionInfo>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            }
            
            @Override
            protected void succeded(final List<PlayzoneGameSessionInfo> list) {
                if (!list.isEmpty()) {
                    OpponentChooserFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                        @Override
                        public void run() {
                            OpponentChooserFragment.this.resumeGame(list.get(0));
                        }
                    });
                }
            }
        });
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427500, viewGroup, false);
        PlayzoneOpponentChooserTabsFragment playzoneOpponentChooserTabsFragment = (PlayzoneOpponentChooserTabsFragment)this.getChildFragmentManager().findFragmentByTag(PlayzoneOpponentChooserTabsFragment.class.getName());
        final FragmentTransaction beginTransaction = this.getChildFragmentManager().beginTransaction();
        if (playzoneOpponentChooserTabsFragment != null) {
            beginTransaction.show(playzoneOpponentChooserTabsFragment);
        }
        else {
            playzoneOpponentChooserTabsFragment = new PlayzoneOpponentChooserTabsFragment();
            beginTransaction.add(2131296753, playzoneOpponentChooserTabsFragment, PlayzoneOpponentChooserTabsFragment.class.getName());
        }
        playzoneOpponentChooserTabsFragment.setEngineOpponentChooserListener(this);
        playzoneOpponentChooserTabsFragment.setOnlineEngineChooserListener(this);
        playzoneOpponentChooserTabsFragment.setContentPresenter(this.getContentPresenter());
        beginTransaction.commit();
        return inflate;
    }
    
    @Override
    public void onOnlineEngineChosen(final String s, final TimeControl timeControl, final boolean b) {
        if (s != null) {
            this.getContentPresenter().showFragment(PlayzoneRemoteFragment.createFragmentWithOnlineEngine(s, timeControl, b), true, true);
        }
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
    }
    
    @Override
    public void resumeEngineOnlineGame(final PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.resumeGame(playzoneGameSessionInfo);
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
}
