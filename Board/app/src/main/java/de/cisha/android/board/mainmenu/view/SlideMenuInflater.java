// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.view.View.OnClickListener;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.IContentPresenter;
import android.view.LayoutInflater;
import android.app.Activity;

public class SlideMenuInflater
{
    private Activity _activity;
    private LayoutInflater _inflater;
    private IContentPresenter _presenter;
    private MainMenuSlider _slider;
    
    public SlideMenuInflater(final Activity activity, final IContentPresenter presenter) {
        this._activity = activity;
        this._presenter = presenter;
        this._inflater = this._activity.getLayoutInflater();
    }
    
    private MenuGroupView addMenuGroup(final ViewGroup viewGroup, final String text) {
        final MenuGroupView menuGroupView = (MenuGroupView)this._inflater.inflate(2131427451, viewGroup, false);
        menuGroupView.setText(text);
        menuGroupView.setDrawingCacheEnabled(true);
        viewGroup.addView((View)menuGroupView);
        return menuGroupView;
    }
    
    private void addMenuItem(final MenuGroupView menuGroupView, final MenuItem menuItem) {
        final MenuItemView menuItemView = (MenuItemView)this._inflater.inflate(2131427452, (ViewGroup)menuGroupView, false);
        if (!menuItem.hasAction()) {
            menuItemView.setBackgroundColor(Color.argb(255, 211, 211, 211));
        }
        menuItemView.setMenuItem(menuItem);
        if (menuItem.hasAction()) {
            menuItemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (!menuItem.isHighlighted()) {
                        menuItem.highlight(true);
                        menuItem.performAction(SlideMenuInflater.this._activity, SlideMenuInflater.this._presenter);
                        return;
                    }
                    SlideMenuInflater.this._slider.closeWithAnimation();
                }
            });
        }
        menuGroupView.addMenuItem(menuItemView);
    }
    
    private MainMenuSlider inflate() {
        this._activity.getLayoutInflater().inflate(2131427453, (ViewGroup)this._activity.findViewById(2131296588));
        this._slider = (MainMenuSlider)this._activity.findViewById(2131296604);
        final ViewGroup viewGroup = (ViewGroup)this._activity.findViewById(2131296612);
        final MenuGroupView addMenuGroup = this.addMenuGroup(viewGroup, this._activity.getString(2131690043));
        this.addMenuItem(addMenuGroup, MenuItem.PLAYNOW);
        this.addMenuItem(addMenuGroup, MenuItem.MY_PLAY_GAMES);
        final MenuGroupView addMenuGroup2 = this.addMenuGroup(viewGroup, this._activity.getString(2131690040));
        this.addMenuItem(addMenuGroup2, MenuItem.ANALYZE);
        this.addMenuItem(addMenuGroup2, MenuItem.MY_ANALYZE_GAMES);
        final MenuGroupView addMenuGroup3 = this.addMenuGroup(viewGroup, this._activity.getString(2131690041));
        this.addMenuItem(addMenuGroup3, MenuItem.VIDEO_SERIES);
        this.addMenuItem(addMenuGroup3, MenuItem.NEWS);
        this.addMenuItem(addMenuGroup3, MenuItem.TACTIC);
        this.addMenuItem(this.addMenuGroup(viewGroup, this._activity.getString(2131690046)), MenuItem.CHESSTV);
        final MenuGroupView addMenuGroup4 = this.addMenuGroup(viewGroup, this._activity.getString(2131690044));
        this.addMenuItem(addMenuGroup4, MenuItem.SUBSCRIPTIONS);
        this.addMenuItem(addMenuGroup4, MenuItem.FEEDBACK);
        this.addMenuItem(addMenuGroup4, MenuItem.SIGNOUT);
        return this._slider;
    }
    
    public MainMenuSlider addMainMenu() {
        final MainMenuSlider inflate = this.inflate();
        inflate.setMenuGrabBound(50);
        return inflate;
    }
}
