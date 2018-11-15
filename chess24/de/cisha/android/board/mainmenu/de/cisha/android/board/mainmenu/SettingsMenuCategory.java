/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.mainmenu;

import de.cisha.android.board.settings.AnalyticsSettingsViewController;
import de.cisha.android.board.settings.BoardSettingsController;
import de.cisha.android.board.settings.PlayzoneSettingsController;
import de.cisha.android.board.settings.SettingsViewController;
import de.cisha.android.board.settings.TacticsSettingsController;

public enum SettingsMenuCategory {
    TACTICS(TacticsSettingsController.class, 2131690367, 2131231684),
    PLAYZONE(PlayzoneSettingsController.class, 2131690172, 2131231675),
    BOARD(BoardSettingsController.class, 2131689577, 2131231667),
    ANALYTICS(AnalyticsSettingsViewController.class, 2131689544, 2131231615);
    
    private int _iconResId;
    private int _titleResId;
    private Class<? extends SettingsViewController> _viewClass;

    private SettingsMenuCategory(Class<? extends SettingsViewController> class_, int n2, int n3) {
        this._viewClass = class_;
        this._titleResId = n2;
        this._iconResId = n3;
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
