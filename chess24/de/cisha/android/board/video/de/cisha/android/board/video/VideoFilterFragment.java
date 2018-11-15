/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CompoundButton
 *  android.widget.TextView
 */
package de.cisha.android.board.video;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.video.VideoFilterSelectorFragment;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.filter.LanguageSettingItem;
import de.cisha.android.board.video.model.filter.PriceSingleSelectionSetting;
import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import de.cisha.android.board.video.model.filter.VideoLanguageMultipleSelectionSetting;
import de.cisha.android.board.video.view.EloRangeSeekBar;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VideoFilterFragment
extends BaseFragment {
    private static final int MAX_ELO = 2200;
    private static final int MIN_ELO = 1200;
    private View.OnClickListener _cancelClickListener;
    private EloRangeSeekBar _eloRangeSeekbar;
    private boolean _flageIsPurchasedToggleOn = false;
    private int _maxEloNumber = 2200;
    private int _minEloNumber = 1200;
    private CompoundButton _purchaseToggle;
    private View.OnClickListener _resetClickListener;
    private View.OnClickListener _saveClickListener;
    private VideoLanguageMultipleSelectionSetting _settingLanguage = new VideoLanguageMultipleSelectionSetting();
    private PriceSingleSelectionSetting _settingPrice = new PriceSingleSelectionSetting();

    private void commitSettings() {
        this._settingLanguage.commit();
        this._settingPrice.commit();
        this._minEloNumber = this._eloRangeSeekbar.getMinEloNumber();
        this._maxEloNumber = this._eloRangeSeekbar.getMaxEloNumber();
        this._flageIsPurchasedToggleOn = this._purchaseToggle.isChecked();
    }

    private void createSettingItem(LayoutInflater layoutInflater, ViewGroup viewGroup, final Setting<? extends SettingItem> setting) {
        layoutInflater = layoutInflater.inflate(2131427575, viewGroup, false);
        ((TextView)layoutInflater.findViewById(2131297212)).setText((CharSequence)setting.getName(this.getResources()));
        final TextView textView = (TextView)layoutInflater.findViewById(2131297211);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(setting.getSelectedText(this.getResources()));
        stringBuilder.append(" >");
        textView.setText((CharSequence)stringBuilder.toString());
        layoutInflater.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoFilterFragment.this.loadSettingSelectionFragment(setting);
            }
        });
        setting.registerOnSettingChangedListener(new Setting.OnSettingChangeListener(){

            @Override
            public void onSettingChanged() {
                TextView textView2 = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(setting.getSelectedText(VideoFilterFragment.this.getResources()));
                stringBuilder.append(" >");
                textView2.setText((CharSequence)stringBuilder.toString());
            }
        });
        viewGroup.addView((View)layoutInflater);
    }

    private void loadSettingSelectionFragment(Setting<? extends SettingItem> setting) {
        VideoFilterSelectorFragment videoFilterSelectorFragment = (VideoFilterSelectorFragment)this.getChildFragmentManager().findFragmentByTag(VideoFilterSelectorFragment.class.getName());
        if (videoFilterSelectorFragment == null) {
            videoFilterSelectorFragment = new VideoFilterSelectorFragment();
            this.getChildFragmentManager().beginTransaction().add(2131297203, videoFilterSelectorFragment, VideoFilterSelectorFragment.class.getName()).setTransition(4097).commit();
        } else {
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
        LinkedList<VideoLanguage> linkedList = new LinkedList<VideoLanguage>();
        Iterator iterator = this._settingLanguage.getSelectedItems().iterator();
        while (iterator.hasNext()) {
            linkedList.add(((LanguageSettingItem)iterator.next()).getVideoLanguage());
        }
        return linkedList;
    }

    public boolean isFilterSelected() {
        if (this._minEloNumber == 1200 && this._maxEloNumber == 2200 && this._settingLanguage.getSelectedItems().isEmpty() && this._settingPrice.getSelectedItem() == null && !this._flageIsPurchasedToggleOn) {
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        viewGroup = (ViewGroup)layoutInflater.inflate(2131427572, viewGroup, false);
        bundle = (ViewGroup)viewGroup.findViewById(2131297218);
        layoutInflater.inflate(2131427574, (ViewGroup)bundle);
        this._purchaseToggle = (CompoundButton)viewGroup.findViewById(2131297208);
        this._purchaseToggle.setChecked(this._flageIsPurchasedToggleOn);
        this.createSettingItem(layoutInflater, (ViewGroup)bundle, this._settingLanguage);
        layoutInflater.inflate(2131427573, (ViewGroup)bundle);
        this._eloRangeSeekbar = (EloRangeSeekBar)viewGroup.findViewById(2131297205);
        this._eloRangeSeekbar.setPossibleValueList(Arrays.asList(1200, 1400, 1600, 1800, 2000, 2200));
        this._eloRangeSeekbar.setMinMaxEloNumber(this._minEloNumber, this._maxEloNumber);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        view.findViewById(2131297204).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoFilterFragment.this.rollbackSettings();
                if (VideoFilterFragment.this._cancelClickListener != null) {
                    VideoFilterFragment.this._cancelClickListener.onClick(view);
                }
            }
        });
        view.findViewById(2131297210).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoFilterFragment.this.commitSettings();
                if (VideoFilterFragment.this._saveClickListener != null) {
                    VideoFilterFragment.this._saveClickListener.onClick(view);
                }
            }
        });
        view.findViewById(2131297209).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
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

    public void setFilterOnCancelButtonClickListener(View.OnClickListener onClickListener) {
        this._cancelClickListener = onClickListener;
    }

    public void setFilterOnResetButtonClickListener(View.OnClickListener onClickListener) {
        this._resetClickListener = onClickListener;
    }

    public void setFilterOnSaveButtonClickListener(View.OnClickListener onClickListener) {
        this._saveClickListener = onClickListener;
    }

}
