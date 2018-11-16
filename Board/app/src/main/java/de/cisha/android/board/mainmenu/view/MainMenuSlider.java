// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.PremiumIndicatorManager;
import android.graphics.Color;
import android.view.View;
import android.graphics.drawable.Drawable;
import de.cisha.android.board.user.User;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.view.CouchImageView;
import de.cisha.android.board.service.IProfileDataService;

public class MainMenuSlider extends MenuSlider implements IUserDataChangedListener
{
    private static final boolean SHOW_VERSION_NUMBER = false;
    private CouchImageView _headerImage;
    private TextView _headerUserName;
    private ImageView _menuButton;
    
    public MainMenuSlider(final Context context) {
        super(context);
    }
    
    public MainMenuSlider(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    private void setProfileImageFor(final User user) {
        if (user != null) {
            this._headerImage.setCouchId(user.getProfileImageCouchId());
            return;
        }
        this._headerImage.setImageDrawable((Drawable)null);
    }
    
    private void setUsername(final User user) {
        String nickname;
        if (user == null) {
            nickname = "";
        }
        else {
            nickname = user.getNickname();
        }
        this._headerUserName.setText((CharSequence)nickname);
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
        (this._headerUserName = (TextView)this.findViewById(2131296597)).setShadowLayer(1.0f, 0.0f, 1.0f, Color.argb(255, 255, 255, 255));
        PremiumIndicatorManager.getInstance().addTextViewToIndicators(this._headerUserName);
        final IProfileDataService profileDataService = ServiceProvider.getInstance().getProfileDataService();
        profileDataService.addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
        final User currentUserData = profileDataService.getCurrentUserData();
        this._headerImage = (CouchImageView)this.findViewById(2131296596);
        this._menuButton = (ImageView)this.findViewById(2131296591);
        this.setUsername(currentUserData);
        this.setProfileImageFor(currentUserData);
    }
    
    @Override
    public void userDataChanged(final User user) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                MainMenuSlider.this.setUsername(user);
                MainMenuSlider.this.setProfileImageFor(user);
                final MenuItem signout = MenuItem.SIGNOUT;
                int title;
                if (user != null && user.isGuest()) {
                    title = 2131690066;
                }
                else {
                    title = 2131690065;
                }
                signout.setTitle(title);
            }
        });
    }
}
