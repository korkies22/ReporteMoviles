/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.engine;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.playzone.engine.PlayzoneEngineFragment;
import de.cisha.android.board.playzone.engine.PlayzoneOpponentChooserTabsFragment;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONObject;

public class OpponentChooserFragment
extends AbstractContentFragment
implements EngineOpponentChooserListener,
EngineOnlineOpponentChooserView.OnlineEngineChooserListener {
    private void resumeGame(PlayzoneGameSessionInfo object) {
        object = PlayzoneRemoteFragment.createFragmentWithSessionToken((PlayzoneGameSessionInfo)object, true);
        this.getContentPresenter().showFragment((AbstractContentFragment)object, false, true);
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690112);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.PLAYVSCOMPUTER;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return new TreeSet<SettingsMenuCategory>(Arrays.asList(new SettingsMenuCategory[]{SettingsMenuCategory.BOARD, SettingsMenuCategory.PLAYZONE}));
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
    public void onChoosen(TimeControl timeControl, int n, String string, boolean bl) {
        this.getContentPresenter().showFragment(PlayzoneEngineFragment.createFragment(timeControl, n, string, bl), true, true);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ServiceProvider.getInstance().getPlayzoneService().getOpenGames((LoadCommandCallback<List<PlayzoneGameSessionInfo>>)new LoadCommandCallbackWithTimeout<List<PlayzoneGameSessionInfo>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            }

            @Override
            protected void succeded(final List<PlayzoneGameSessionInfo> list) {
                if (!list.isEmpty()) {
                    OpponentChooserFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                        @Override
                        public void run() {
                            PlayzoneGameSessionInfo playzoneGameSessionInfo = (PlayzoneGameSessionInfo)list.get(0);
                            OpponentChooserFragment.this.resumeGame(playzoneGameSessionInfo);
                        }
                    });
                }
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater object, @Nullable ViewGroup viewGroup, @Nullable Bundle object2) {
        viewGroup = object.inflate(2131427500, viewGroup, false);
        object = (PlayzoneOpponentChooserTabsFragment)this.getChildFragmentManager().findFragmentByTag(PlayzoneOpponentChooserTabsFragment.class.getName());
        object2 = this.getChildFragmentManager().beginTransaction();
        if (object != null) {
            object2.show((Fragment)object);
        } else {
            object = new PlayzoneOpponentChooserTabsFragment();
            object2.add(2131296753, (Fragment)object, PlayzoneOpponentChooserTabsFragment.class.getName());
        }
        object.setEngineOpponentChooserListener(this);
        object.setOnlineEngineChooserListener(this);
        object.setContentPresenter(this.getContentPresenter());
        object2.commit();
        return viewGroup;
    }

    @Override
    public void onOnlineEngineChosen(String string, TimeControl timeControl, boolean bl) {
        if (string != null) {
            this.getContentPresenter().showFragment(PlayzoneRemoteFragment.createFragmentWithOnlineEngine(string, timeControl, bl), true, true);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override
    public void resumeEngineOnlineGame(PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.resumeGame(playzoneGameSessionInfo);
    }

    @Override
    public boolean showMenu() {
        return true;
    }

}
