/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.graphics.Color
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package de.cisha.android.board.mainmenu.view;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.mainmenu.view.MainMenuSlider;
import de.cisha.android.board.mainmenu.view.MenuGroupView;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.mainmenu.view.MenuItemView;

public class SlideMenuInflater {
    private Activity _activity;
    private LayoutInflater _inflater;
    private IContentPresenter _presenter;
    private MainMenuSlider _slider;

    public SlideMenuInflater(Activity activity, IContentPresenter iContentPresenter) {
        this._activity = activity;
        this._presenter = iContentPresenter;
        this._inflater = this._activity.getLayoutInflater();
    }

    private MenuGroupView addMenuGroup(ViewGroup viewGroup, String string) {
        MenuGroupView menuGroupView = (MenuGroupView)this._inflater.inflate(2131427451, viewGroup, false);
        menuGroupView.setText(string);
        menuGroupView.setDrawingCacheEnabled(true);
        viewGroup.addView((View)menuGroupView);
        return menuGroupView;
    }

    private void addMenuItem(MenuGroupView menuGroupView, final MenuItem menuItem) {
        MenuItemView menuItemView = (MenuItemView)this._inflater.inflate(2131427452, (ViewGroup)menuGroupView, false);
        if (!menuItem.hasAction()) {
            menuItemView.setBackgroundColor(Color.argb((int)255, (int)211, (int)211, (int)211));
        }
        menuItemView.setMenuItem(menuItem);
        if (menuItem.hasAction()) {
            menuItemView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
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
        Object object = (ViewGroup)this._activity.findViewById(2131296612);
        MenuGroupView menuGroupView = this.addMenuGroup((ViewGroup)object, this._activity.getString(2131690043));
        this.addMenuItem(menuGroupView, MenuItem.PLAYNOW);
        this.addMenuItem(menuGroupView, MenuItem.MY_PLAY_GAMES);
        menuGroupView = this.addMenuGroup((ViewGroup)object, this._activity.getString(2131690040));
        this.addMenuItem(menuGroupView, MenuItem.ANALYZE);
        this.addMenuItem(menuGroupView, MenuItem.MY_ANALYZE_GAMES);
        menuGroupView = this.addMenuGroup((ViewGroup)object, this._activity.getString(2131690041));
        this.addMenuItem(menuGroupView, MenuItem.VIDEO_SERIES);
        this.addMenuItem(menuGroupView, MenuItem.NEWS);
        this.addMenuItem(menuGroupView, MenuItem.TACTIC);
        this.addMenuItem(this.addMenuGroup((ViewGroup)object, this._activity.getString(2131690046)), MenuItem.CHESSTV);
        object = this.addMenuGroup((ViewGroup)object, this._activity.getString(2131690044));
        this.addMenuItem((MenuGroupView)((Object)object), MenuItem.SUBSCRIPTIONS);
        this.addMenuItem((MenuGroupView)((Object)object), MenuItem.FEEDBACK);
        this.addMenuItem((MenuGroupView)((Object)object), MenuItem.SIGNOUT);
        return this._slider;
    }

    public MainMenuSlider addMainMenu() {
        MainMenuSlider mainMenuSlider = this.inflate();
        mainMenuSlider.setMenuGrabBound(50);
        return mainMenuSlider;
    }

}
