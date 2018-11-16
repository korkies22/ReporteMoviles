// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video;

import de.cisha.android.board.video.model.filter.PriceSettingItem;
import de.cisha.android.board.video.model.filter.AbstractSingleSelectionSetting;
import android.content.res.Resources;
import de.cisha.android.board.video.model.filter.AbstractMultipleSelectionSetting;
import java.util.Arrays;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.Iterator;
import de.cisha.android.board.video.model.filter.LanguageSettingItem;
import java.util.LinkedList;
import de.cisha.android.board.video.model.VideoLanguage;
import java.util.List;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.PriceSingleSelectionSetting;
import de.cisha.android.board.video.model.filter.VideoLanguageMultipleSelectionSetting;
import android.widget.CompoundButton;
import de.cisha.android.board.video.view.EloRangeSeekBar;
import android.view.View.OnClickListener;
import de.cisha.android.board.BaseFragment;

public class VideoFilterFragment extends BaseFragment
{
    private static final int MAX_ELO = 2200;
    private static final int MIN_ELO = 1200;
    private View.OnClickListener _cancelClickListener;
    private EloRangeSeekBar _eloRangeSeekbar;
    private boolean _flageIsPurchasedToggleOn;
    private int _maxEloNumber;
    private int _minEloNumber;
    private CompoundButton _purchaseToggle;
    private View.OnClickListener _resetClickListener;
    private View.OnClickListener _saveClickListener;
    private VideoLanguageMultipleSelectionSetting _settingLanguage;
    private PriceSingleSelectionSetting _settingPrice;
    
    public VideoFilterFragment() {
        this._settingLanguage = new VideoLanguageMultipleSelectionSetting();
        this._settingPrice = new PriceSingleSelectionSetting();
        this._minEloNumber = 1200;
        this._maxEloNumber = 2200;
        this._flageIsPurchasedToggleOn = false;
    }
    
    private void commitSettings() {
        this._settingLanguage.commit();
        this._settingPrice.commit();
        this._minEloNumber = this._eloRangeSeekbar.getMinEloNumber();
        this._maxEloNumber = this._eloRangeSeekbar.getMaxEloNumber();
        this._flageIsPurchasedToggleOn = this._purchaseToggle.isChecked();
    }
    
    private void createSettingItem(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Setting<? extends SettingItem> setting) {
        final View inflate = layoutInflater.inflate(2131427575, viewGroup, false);
        ((TextView)inflate.findViewById(2131297212)).setText((CharSequence)setting.getName(this.getResources()));
        final TextView textView = (TextView)inflate.findViewById(2131297211);
        final StringBuilder sb = new StringBuilder();
        sb.append(setting.getSelectedText(this.getResources()));
        sb.append(" >");
        textView.setText((CharSequence)sb.toString());
        inflate.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoFilterFragment.this.loadSettingSelectionFragment(setting);
            }
        });
        setting.registerOnSettingChangedListener((Setting.OnSettingChangeListener)new Setting.OnSettingChangeListener() {
            @Override
            public void onSettingChanged() {
                final TextView val.detailText = textView;
                final StringBuilder sb = new StringBuilder();
                sb.append(setting.getSelectedText(VideoFilterFragment.this.getResources()));
                sb.append(" >");
                val.detailText.setText((CharSequence)sb.toString());
            }
        });
        viewGroup.addView(inflate);
    }
    
    private void loadSettingSelectionFragment(final Setting<? extends SettingItem> setting) {
        VideoFilterSelectorFragment videoFilterSelectorFragment = (VideoFilterSelectorFragment)this.getChildFragmentManager().findFragmentByTag(VideoFilterSelectorFragment.class.getName());
        if (videoFilterSelectorFragment == null) {
            videoFilterSelectorFragment = new VideoFilterSelectorFragment();
            this.getChildFragmentManager().beginTransaction().add(2131297203, videoFilterSelectorFragment, VideoFilterSelectorFragment.class.getName()).setTransition(4097).commit();
        }
        else {
            this.getChildFragmentManager().beginTransaction().show(videoFilterSelectorFragment).commit();
        }
        videoFilterSelectorFragment.setSetting(setting);
    }
    
    private void rollbackSettings() {
        this._settingLanguage.rollback();
        this._settingPrice.rollback();
        this._eloRangeSeekbar.setMinMaxEloNumber(this._minEloNumber, this._maxEloNumber);
        this._purchaseToggle.setChecked(this._flageIsPurchasedToggleOn);
    }
    
    public boolean getFilterIsPurchased() {
        return this._flageIsPurchasedToggleOn;
    }
    
    public int getFilterMaxEloNumber() {
        return this._maxEloNumber;
    }
    
    public int getFilterMinEloNumber() {
        return this._minEloNumber;
    }
    
    public List<VideoLanguage> getFilterVideoLanguages() {
        final LinkedList<VideoLanguage> list = new LinkedList<VideoLanguage>();
        final Iterator<LanguageSettingItem> iterator = this._settingLanguage.getSelectedItems().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getVideoLanguage());
        }
        return list;
    }
    
    public boolean isFilterSelected() {
        return this._minEloNumber != 1200 || this._maxEloNumber != 2200 || !this._settingLanguage.getSelectedItems().isEmpty() || this._settingPrice.getSelectedItem() != null || this._flageIsPurchasedToggleOn;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable final Bundle bundle) {
        viewGroup = (ViewGroup)layoutInflater.inflate(2131427572, viewGroup, false);
        final ViewGroup viewGroup2 = (ViewGroup)viewGroup.findViewById(2131297218);
        layoutInflater.inflate(2131427574, viewGroup2);
        (this._purchaseToggle = (CompoundButton)viewGroup.findViewById(2131297208)).setChecked(this._flageIsPurchasedToggleOn);
        this.createSettingItem(layoutInflater, viewGroup2, this._settingLanguage);
        layoutInflater.inflate(2131427573, viewGroup2);
        (this._eloRangeSeekbar = (EloRangeSeekBar)viewGroup.findViewById(2131297205)).setPossibleValueList(Arrays.asList(1200, 1400, 1600, 1800, 2000, 2200));
        this._eloRangeSeekbar.setMinMaxEloNumber(this._minEloNumber, this._maxEloNumber);
        return (View)viewGroup;
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        view.findViewById(2131297204).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoFilterFragment.this.rollbackSettings();
                if (VideoFilterFragment.this._cancelClickListener != null) {
                    VideoFilterFragment.this._cancelClickListener.onClick(view);
                }
            }
        });
        view.findViewById(2131297210).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoFilterFragment.this.commitSettings();
                if (VideoFilterFragment.this._saveClickListener != null) {
                    VideoFilterFragment.this._saveClickListener.onClick(view);
                }
            }
        });
        view.findViewById(2131297209).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoFilterFragment.this.resetFilterSelection();
                if (VideoFilterFragment.this._resetClickListener != null) {
                    VideoFilterFragment.this._resetClickListener.onClick(view);
                }
            }
        });
    }
    
    public void resetFilterSelection() {
        this._minEloNumber = 1200;
        this._maxEloNumber = 2200;
        this._eloRangeSeekbar.setMinMaxEloNumber(this._minEloNumber, this._maxEloNumber);
        this._settingLanguage.setSelected(null);
        this._settingPrice.setSelected(null);
        this._flageIsPurchasedToggleOn = false;
        this._purchaseToggle.setChecked(this._flageIsPurchasedToggleOn);
    }
    
    public void setFilterOnCancelButtonClickListener(final View.OnClickListener cancelClickListener) {
        this._cancelClickListener = cancelClickListener;
    }
    
    public void setFilterOnResetButtonClickListener(final View.OnClickListener resetClickListener) {
        this._resetClickListener = resetClickListener;
    }
    
    public void setFilterOnSaveButtonClickListener(final View.OnClickListener saveClickListener) {
        this._saveClickListener = saveClickListener;
    }
}
