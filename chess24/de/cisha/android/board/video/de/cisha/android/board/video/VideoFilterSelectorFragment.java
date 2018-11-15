/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.video;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.view.SelectionSettingView;

public class VideoFilterSelectorFragment
extends BaseFragment {
    private TextView _filterTitleTextView;
    private ViewGroup _itemContainer;
    private Setting<? extends SettingItem> _setting;

    private void updateView() {
        if (this._setting != null) {
            this._filterTitleTextView.setText((CharSequence)this._setting.getName(this.getResources()));
            this._itemContainer.removeAllViews();
            this._itemContainer.addView((View)new SelectionSettingView((Context)this.getActivity(), this._setting));
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, @Nullable Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427576, viewGroup, false);
        layoutInflater.findViewById(2131297213).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoFilterSelectorFragment.this.getFragmentManager().beginTransaction().hide(VideoFilterSelectorFragment.this).commit();
            }
        });
        this._itemContainer = (ViewGroup)layoutInflater.findViewById(2131297214);
        this._filterTitleTextView = (TextView)layoutInflater.findViewById(2131297217);
        return layoutInflater;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.updateView();
    }

    public void setSetting(final Setting<? extends SettingItem> setting) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoFilterSelectorFragment.this._setting = setting;
                VideoFilterSelectorFragment.this.updateView();
            }
        });
    }

}
