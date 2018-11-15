/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.playzone.engine;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.engine.EngineOfflineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.EngineOnlineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;

public class PlayzoneOpponentChooserTabsFragment
extends BaseFragment
implements EngineOpponentChooserListener,
EngineOnlineOpponentChooserView.OnlineEngineChooserListener {
    private IContentPresenter _contentPresenter;
    private EngineOpponentChooserListener _engineOpponentChooserListener;
    private EngineOnlineOpponentChooserView.OnlineEngineChooserListener _onlineEngineChooserListener;

    @Override
    public void onChoosen(TimeControl timeControl, int n, String string, boolean bl) {
        if (this._engineOpponentChooserListener != null) {
            this._engineOpponentChooserListener.onChoosen(timeControl, n, string, bl);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle object) {
        layoutInflater = layoutInflater.inflate(2131427502, viewGroup, false);
        viewGroup = (ViewPager)layoutInflater.findViewById(2131296756);
        object = (PagerTabStrip)layoutInflater.findViewById(2131296757);
        object.setTabIndicatorColor(Color.rgb((int)215, (int)215, (int)215));
        object.setTextColor(-1);
        viewGroup.setAdapter(new OpponentTabPagerAdapter(this.getChildFragmentManager()));
        return layoutInflater;
    }

    @Override
    public void onOnlineEngineChosen(String string, TimeControl timeControl, boolean bl) {
        if (this._onlineEngineChooserListener != null) {
            this._onlineEngineChooserListener.onOnlineEngineChosen(string, timeControl, bl);
        }
    }

    @Override
    public void resumeEngineOnlineGame(PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        if (this._onlineEngineChooserListener != null) {
            this._onlineEngineChooserListener.resumeEngineOnlineGame(playzoneGameSessionInfo);
        }
    }

    public void setContentPresenter(IContentPresenter iContentPresenter) {
        this._contentPresenter = iContentPresenter;
    }

    public void setEngineOpponentChooserListener(EngineOpponentChooserListener engineOpponentChooserListener) {
        this._engineOpponentChooserListener = engineOpponentChooserListener;
    }

    public void setOnlineEngineChooserListener(EngineOnlineOpponentChooserView.OnlineEngineChooserListener onlineEngineChooserListener) {
        this._onlineEngineChooserListener = onlineEngineChooserListener;
    }

    private class OpponentTabPagerAdapter
    extends FragmentPagerAdapter {
        public OpponentTabPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int n) {
            switch (n) {
                default: {
                    return new Fragment();
                }
                case 2: {
                    return new EngineOnlineOpponentChooserViewFragment();
                }
                case 1: {
                    return new EngineOfflineOpponentChooserViewFragment();
                }
                case 0: 
            }
            return new PairingSetupFragment();
        }

        @Override
        public CharSequence getPageTitle(int n) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Tab Number ");
                    stringBuilder.append(n);
                    return stringBuilder.toString();
                }
                case 2: {
                    return PlayzoneOpponentChooserTabsFragment.this.getResources().getString(2131690162);
                }
                case 1: {
                    return PlayzoneOpponentChooserTabsFragment.this.getResources().getString(2131690161);
                }
                case 0: 
            }
            return PlayzoneOpponentChooserTabsFragment.this.getResources().getString(2131690174);
        }

        @Override
        public Object instantiateItem(ViewGroup object, int n) {
            if ((object = super.instantiateItem((ViewGroup)object, n)) instanceof PairingSetupFragment) {
                ((PairingSetupFragment)object).setContentPresenter(PlayzoneOpponentChooserTabsFragment.this._contentPresenter);
                return object;
            }
            if (object instanceof EngineOfflineOpponentChooserViewFragment) {
                EngineOfflineOpponentChooserViewFragment engineOfflineOpponentChooserViewFragment = (EngineOfflineOpponentChooserViewFragment)object;
                engineOfflineOpponentChooserViewFragment.setContentPresenter(PlayzoneOpponentChooserTabsFragment.this._contentPresenter);
                engineOfflineOpponentChooserViewFragment.setEngineOpponentChooserListener(PlayzoneOpponentChooserTabsFragment.this);
                return object;
            }
            if (object instanceof EngineOnlineOpponentChooserViewFragment) {
                ((EngineOnlineOpponentChooserViewFragment)object).setOnlineEngineChooserListener(PlayzoneOpponentChooserTabsFragment.this);
            }
            return object;
        }
    }

}
