/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.mainmenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.mainmenu.view.MenuSlider;

public class SettingsMenuSlider
extends MenuSlider {
    private TextView _headerText;
    private ImageView _menuButton;

    public SettingsMenuSlider(Context context) {
        super(context);
    }

    public SettingsMenuSlider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected View getImageFrame() {
        return this.findViewById(2131296953);
    }

    @Override
    protected ImageView getMenuButton() {
        return this._menuButton;
    }

    @Override
    protected ImageView getMenuShadow() {
        return (ImageView)this.findViewById(2131296974);
    }

    @Override
    protected View getMenuSlideContainer() {
        return this.findViewById(2131296605);
    }

    public ViewGroup getSettingsContentContainer() {
        return (ViewGroup)this.findViewById(2131296952);
    }

    @Override
    public boolean leftSideMenu() {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this._menuButton = (ImageView)this.findViewById(2131296954);
        this._headerText = (TextView)this.findViewById(2131296951);
    }

    public void setHeaderText(String string) {
        this._headerText.setText((CharSequence)string);
    }
}
