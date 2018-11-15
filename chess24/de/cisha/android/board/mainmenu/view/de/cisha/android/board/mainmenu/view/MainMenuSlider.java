/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.mainmenu.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.PremiumIndicatorManager;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.mainmenu.view.MenuSlider;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.user.User;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;

public class MainMenuSlider
extends MenuSlider
implements IProfileDataService.IUserDataChangedListener {
    private static final boolean SHOW_VERSION_NUMBER = false;
    private CouchImageView _headerImage;
    private TextView _headerUserName;
    private ImageView _menuButton;

    public MainMenuSlider(Context context) {
        super(context);
    }

    public MainMenuSlider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void setProfileImageFor(User user) {
        if (user != null) {
            this._headerImage.setCouchId(user.getProfileImageCouchId());
            return;
        }
        this._headerImage.setImageDrawable(null);
    }

    private void setUsername(User object) {
        object = object == null ? "" : object.getNickname();
        this._headerUserName.setText((CharSequence)object);
    }

    @Override
    protected View getImageFrame() {
        return this.findViewById(2131296551);
    }

    @Override
    protected ImageView getMenuButton() {
        return this._menuButton;
    }

    @Override
    protected ImageView getMenuShadow() {
        return (ImageView)this.findViewById(2131296608);
    }

    @Override
    protected View getMenuSlideContainer() {
        return this.findViewById(2131296605);
    }

    @Override
    public boolean leftSideMenu() {
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this._headerUserName = (TextView)this.findViewById(2131296597);
        this._headerUserName.setShadowLayer(1.0f, 0.0f, 1.0f, Color.argb((int)255, (int)255, (int)255, (int)255));
        PremiumIndicatorManager.getInstance().addTextViewToIndicators(this._headerUserName);
        Object object = ServiceProvider.getInstance().getProfileDataService();
        object.addUserDataChangedListener(this);
        object = object.getCurrentUserData();
        this._headerImage = (CouchImageView)this.findViewById(2131296596);
        this._menuButton = (ImageView)this.findViewById(2131296591);
        this.setUsername((User)object);
        this.setProfileImageFor((User)object);
    }

    @Override
    public void userDataChanged(final User user) {
        this.post(new Runnable(){

            @Override
            public void run() {
                MainMenuSlider.this.setUsername(user);
                MainMenuSlider.this.setProfileImageFor(user);
                MenuItem menuItem = MenuItem.SIGNOUT;
                int n = user != null && user.isGuest() ? 2131690066 : 2131690065;
                menuItem.setTitle(n);
            }
        });
    }

}
