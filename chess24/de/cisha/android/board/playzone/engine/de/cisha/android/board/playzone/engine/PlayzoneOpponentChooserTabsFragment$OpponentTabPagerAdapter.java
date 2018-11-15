/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.view.ViewGroup
 */
package de.cisha.android.board.playzone.engine;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.engine.EngineOfflineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.EngineOnlineOpponentChooserViewFragment;
import de.cisha.android.board.playzone.engine.PlayzoneOpponentChooserTabsFragment;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;

private class PlayzoneOpponentChooserTabsFragment.OpponentTabPagerAdapter
extends FragmentPagerAdapter {
    public PlayzoneOpponentChooserTabsFragment.OpponentTabPagerAdapter(FragmentManager fragmentManager) {
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
