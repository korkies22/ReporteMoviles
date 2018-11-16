// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video;

import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import de.cisha.android.board.video.view.SelectionSettingView;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.model.filter.Setting;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.BaseFragment;

public class VideoFilterSelectorFragment extends BaseFragment
{
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
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427576, viewGroup, false);
        inflate.findViewById(2131297213).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoFilterSelectorFragment.this.getFragmentManager().beginTransaction().hide(VideoFilterSelectorFragment.this).commit();
            }
        });
        this._itemContainer = (ViewGroup)inflate.findViewById(2131297214);
        this._filterTitleTextView = (TextView)inflate.findViewById(2131297217);
        return inflate;
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.updateView();
    }
    
    public void setSetting(final Setting<? extends SettingItem> setting) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoFilterSelectorFragment.this._setting = setting;
                VideoFilterSelectorFragment.this.updateView();
            }
        });
    }
}
