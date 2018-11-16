// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.remote.PairingSetupFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import android.support.v4.view.PagerAdapter;
import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.BaseFragment;

public class PlayzoneOpponentChooserTabsFragment extends BaseFragment implements EngineOpponentChooserListener, OnlineEngineChooserListener
{
    private IContentPresenter _contentPresenter;
    private EngineOpponentChooserListener _engineOpponentChooserListener;
    private OnlineEngineChooserListener _onlineEngineChooserListener;
    
    @Override
    public void onChoosen(final TimeControl timeControl, final int n, final String s, final boolean b) {
        if (this._engineOpponentChooserListener != null) {
            this._engineOpponentChooserListener.onChoosen(timeControl, n, s, b);
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427502, viewGroup, false);
        final ViewPager viewPager = (ViewPager)inflate.findViewById(2131296756);
        final PagerTabStrip pagerTabStrip = (PagerTabStrip)inflate.findViewById(2131296757);
        pagerTabStrip.setTabIndicatorColor(Color.rgb(215, 215, 215));
        pagerTabStrip.setTextColor(-1);
        viewPager.setAdapter(new OpponentTabPagerAdapter(this.getChildFragmentManager()));
        return inflate;
    }
    
    @Override
    public void onOnlineEngineChosen(final String s, final TimeControl timeControl, final boolean b) {
        if (this._onlineEngineChooserListener != null) {
            this._onlineEngineChooserListener.onOnlineEngineChosen(s, timeControl, b);
        }
    }
    
    @Override
    public void resumeEngineOnlineGame(final PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        if (this._onlineEngineChooserListener != null) {
            this._onlineEngineChooserListener.resumeEngineOnlineGame(playzoneGameSessionInfo);
        }
    }
    
    public void setContentPresenter(final IContentPresenter contentPresenter) {
        this._contentPresenter = contentPresenter;
    }
    
    public void setEngineOpponentChooserListener(final EngineOpponentChooserListener engineOpponentChooserListener) {
        this._engineOpponentChooserListener = engineOpponentChooserListener;
    }
    
    public void setOnlineEngineChooserListener(final OnlineEngineChooserListener onlineEngineChooserListener) {
        this._onlineEngineChooserListener = onlineEngineChooserListener;
    }
    
    private class OpponentTabPagerAdapter extends FragmentPagerAdapter
    {
        public OpponentTabPagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        
        @Override
        public int getCount() {
            return 3;
        }
        
        @Override
        public Fragment getItem(final int n) {
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
                case 0: {
                    return new PairingSetupFragment();
                }
            }
        }
        
        @Override
        public CharSequence getPageTitle(final int n) {
            switch (n) {
                default: {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Tab Number ");
                    sb.append(n);
                    return sb.toString();
                }
                case 2: {
                    return PlayzoneOpponentChooserTabsFragment.this.getResources().getString(2131690162);
                }
                case 1: {
                    return PlayzoneOpponentChooserTabsFragment.this.getResources().getString(2131690161);
                }
                case 0: {
                    return PlayzoneOpponentChooserTabsFragment.this.getResources().getString(2131690174);
                }
            }
        }
        
        @Override
        public Object instantiateItem(final ViewGroup viewGroup, final int n) {
            final Object instantiateItem = super.instantiateItem(viewGroup, n);
            if (instantiateItem instanceof PairingSetupFragment) {
                ((PairingSetupFragment)instantiateItem).setContentPresenter(PlayzoneOpponentChooserTabsFragment.this._contentPresenter);
                return instantiateItem;
            }
            if (instantiateItem instanceof EngineOfflineOpponentChooserViewFragment) {
                final EngineOfflineOpponentChooserViewFragment engineOfflineOpponentChooserViewFragment = (EngineOfflineOpponentChooserViewFragment)instantiateItem;
                engineOfflineOpponentChooserViewFragment.setContentPresenter(PlayzoneOpponentChooserTabsFragment.this._contentPresenter);
                engineOfflineOpponentChooserViewFragment.setEngineOpponentChooserListener(PlayzoneOpponentChooserTabsFragment.this);
                return instantiateItem;
            }
            if (instantiateItem instanceof EngineOnlineOpponentChooserViewFragment) {
                ((EngineOnlineOpponentChooserViewFragment)instantiateItem).setOnlineEngineChooserListener(PlayzoneOpponentChooserTabsFragment.this);
            }
            return instantiateItem;
        }
    }
}
