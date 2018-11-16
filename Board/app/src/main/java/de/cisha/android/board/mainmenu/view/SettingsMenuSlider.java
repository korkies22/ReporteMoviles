// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.view.ViewGroup;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsMenuSlider extends MenuSlider
{
    private TextView _headerText;
    private ImageView _menuButton;
    
    public SettingsMenuSlider(final Context context) {
        super(context);
    }
    
    public SettingsMenuSlider(final Context context, final AttributeSet set) {
        super(context, set);
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
    
    public void setHeaderText(final String text) {
        this._headerText.setText((CharSequence)text);
    }
}
