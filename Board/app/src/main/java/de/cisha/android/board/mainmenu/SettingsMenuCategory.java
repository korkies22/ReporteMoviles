// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu;

import de.cisha.android.board.settings.AnalyticsSettingsViewController;
import de.cisha.android.board.settings.BoardSettingsController;
import de.cisha.android.board.settings.PlayzoneSettingsController;
import de.cisha.android.board.settings.TacticsSettingsController;
import de.cisha.android.board.settings.SettingsViewController;

public enum SettingsMenuCategory
{
    ANALYTICS((Class<? extends SettingsViewController>)AnalyticsSettingsViewController.class, 2131689544, 2131231615), 
    BOARD((Class<? extends SettingsViewController>)BoardSettingsController.class, 2131689577, 2131231667), 
    PLAYZONE((Class<? extends SettingsViewController>)PlayzoneSettingsController.class, 2131690172, 2131231675), 
    TACTICS((Class<? extends SettingsViewController>)TacticsSettingsController.class, 2131690367, 2131231684);
    
    private int _iconResId;
    private int _titleResId;
    private Class<? extends SettingsViewController> _viewClass;
    
    private SettingsMenuCategory(final Class<? extends SettingsViewController> viewClass, final int titleResId, final int iconResId) {
        this._viewClass = viewClass;
        this._titleResId = titleResId;
        this._iconResId = iconResId;
    }
    
    public int getIconResId() {
        return this._iconResId;
    }
    
    public int getTitleResId() {
        return this._titleResId;
    }
    
    public Class<? extends SettingsViewController> getViewControllerClass() {
        return this._viewClass;
    }
}
