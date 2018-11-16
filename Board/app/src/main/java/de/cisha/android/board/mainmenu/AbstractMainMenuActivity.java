// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu;

import de.cisha.android.board.mainmenu.view.ObservableMenu;
import de.cisha.android.board.IContentPresenter;
import android.app.Activity;
import de.cisha.android.board.mainmenu.view.SlideMenuInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.mainmenu.view.MenuSlider;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.widget.ImageView;
import de.cisha.android.board.mainmenu.view.SettingsMenuSlider;
import de.cisha.android.board.mainmenu.view.MainMenuSlider;
import android.widget.TextView;
import de.cisha.android.board.registration.SingleScreenFragmentActivity;

public abstract class AbstractMainMenuActivity extends SingleScreenFragmentActivity
{
    private boolean _grabMenu;
    private TextView _headerText;
    private MainMenuSlider _menuSlider;
    private SettingsMenuSlider _settingsMenuSlider;
    private ButtonType _topLeftButton;
    private ImageView _topLeftButtonView;
    private ButtonType _topRightButton;
    private ImageView _topRightButtonView;
    
    public AbstractMainMenuActivity() {
        this._grabMenu = true;
    }
    
    public boolean dispatchTouchEvent(MotionEvent dispatchSlideMotion) {
        MotionEvent dispatchSlideMotion2 = dispatchSlideMotion;
        if (this._menuSlider != null) {
            dispatchSlideMotion2 = dispatchSlideMotion;
            if (this.showMenu()) {
                dispatchSlideMotion2 = this._menuSlider.dispatchSlideMotion(dispatchSlideMotion);
            }
        }
        dispatchSlideMotion = dispatchSlideMotion2;
        if (this._settingsMenuSlider != null) {
            dispatchSlideMotion = this._settingsMenuSlider.dispatchSlideMotion(dispatchSlideMotion2);
        }
        return super.dispatchTouchEvent(dispatchSlideMotion);
    }
    
    protected ViewGroup getContentView() {
        return (ViewGroup)this.findViewById(2131296260);
    }
    
    protected int getContentViewHeight() {
        int contentViewHeight;
        if ((contentViewHeight = MenuSlider.getContentViewHeight()) <= 0) {
            contentViewHeight = (int)(this.getResources().getDisplayMetrics().widthPixels - 75.0f * this.getResources().getDisplayMetrics().density);
        }
        return contentViewHeight;
    }
    
    protected int getContentViewWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }
    
    protected abstract MenuItem getHiglightedMenuItem();
    
    protected MenuSlider getMainMenuSlider() {
        return this._menuSlider;
    }
    
    protected SettingsMenuSlider getSettingsMenuSlider() {
        return this._settingsMenuSlider;
    }
    
    @Override
    public void onBackPressed() {
        final MenuSlider mainMenuSlider = this.getMainMenuSlider();
        if (mainMenuSlider != null && !this.getMainMenuSlider().isClosed()) {
            mainMenuSlider.closeWithAnimation();
            return;
        }
        if (this._settingsMenuSlider != null && !this._settingsMenuSlider.isClosed()) {
            this._settingsMenuSlider.closeWithAnimation();
            return;
        }
        super.onBackPressed();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        super.setContentView(2131427357);
        (this._headerText = (TextView)this.findViewById(2131296543)).setText((CharSequence)"chess24");
        if (this.showSettingsMenu()) {
            this._settingsMenuSlider = (SettingsMenuSlider)((ViewGroup)this.getLayoutInflater().inflate(2131427537, (ViewGroup)this.findViewById(2131296588))).findViewById(2131296956);
        }
        this.getContentView().setDrawingCacheEnabled(true);
        final ViewGroup viewGroup = (ViewGroup)this.findViewById(2131296609);
        final ViewGroup viewGroup2 = (ViewGroup)this.findViewById(2131296610);
        (this._topLeftButtonView = (ImageView)this.getLayoutInflater().inflate(2131427462, viewGroup, false)).setImageResource(2131230867);
        this._topRightButtonView = (ImageView)this.getLayoutInflater().inflate(2131427462, viewGroup2, false);
        viewGroup.addView((View)this._topLeftButtonView);
        viewGroup2.addView((View)this._topRightButtonView);
        ButtonType buttonType;
        if (this.showMenu()) {
            buttonType = ButtonType.NO;
        }
        else {
            buttonType = ButtonType.BACK;
        }
        this.setTopButton(buttonType, true);
        this.setTopButton(ButtonType.NO, false);
        this._topLeftButtonView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractMainMenuActivity.this.onBackPressed();
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getContentView().destroyDrawingCache();
        this._topLeftButtonView = null;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        if (this.showMenu()) {
            (this._menuSlider = new SlideMenuInflater(this, null).addMainMenu()).setMenuGrabBound(35);
            this._menuSlider.setGrabMenuEnabled(this._grabMenu);
            this._menuSlider.setClosed();
            if (this._settingsMenuSlider != null) {
                this._menuSlider.observeOtherMenu(this._settingsMenuSlider);
                this._settingsMenuSlider.observeOtherMenu(this._menuSlider);
                this._settingsMenuSlider.setGrabMenuEnabled(this._grabMenu);
            }
        }
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.bringToFront();
        }
        final MenuItem higlightedMenuItem = this.getHiglightedMenuItem();
        if (higlightedMenuItem != null) {
            higlightedMenuItem.highlight(true);
            return;
        }
        MenuItem.removeHighlightFromAll();
    }
    
    public void setContentView(final int n) {
        this.getLayoutInflater().inflate(n, this.getContentView());
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.bringToFront();
        }
        if (this._menuSlider != null) {
            this._menuSlider.bringToFront();
        }
    }
    
    protected void setGrabMenuEnabled(final boolean grabMenuEnabled) {
        this._grabMenu = grabMenuEnabled;
        if (this._menuSlider != null) {
            this._menuSlider.setGrabMenuEnabled(grabMenuEnabled);
        }
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.setGrabMenuEnabled(grabMenuEnabled);
        }
    }
    
    protected void setHeaderText(final String text) {
        this._headerText.setText((CharSequence)text);
    }
    
    protected void setSettingsMenu(final View view) {
        if (this._settingsMenuSlider != null) {
            this.getSettingsMenuSlider().getSettingsContentContainer().addView(view);
            this._settingsMenuSlider.setGrabMenuEnabled(this._grabMenu);
        }
    }
    
    protected void setSettingsTitle(final String headerText) {
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.setHeaderText(headerText);
        }
    }
    
    protected void setTopButton(final ButtonType buttonType, final boolean b) {
        if (b) {
            this._topLeftButton = buttonType;
        }
        else {
            this._topRightButton = buttonType;
        }
        ImageView imageView;
        if (b) {
            imageView = this._topLeftButtonView;
        }
        else {
            imageView = this._topRightButtonView;
        }
        if (buttonType != ButtonType.NO) {
            imageView.setVisibility(0);
            imageView.setImageDrawable(this.getResources().getDrawable(buttonType._resId));
            return;
        }
        imageView.setVisibility(8);
    }
    
    protected void setTopButtonClickListener(final View.OnClickListener onClickListener, final boolean b) {
        ImageView imageView;
        if (b) {
            imageView = this._topLeftButtonView;
        }
        else {
            imageView = this._topRightButtonView;
        }
        imageView.setOnClickListener(onClickListener);
    }
    
    protected boolean showMenu() {
        return true;
    }
    
    protected boolean showSettingsMenu() {
        return false;
    }
    
    protected enum ButtonType
    {
        BACK(2131230867), 
        CLOSE(2131231037), 
        NO(0), 
        SAVE(2131231653), 
        SAVE_INVALID(2131231033), 
        SAVE_VALID(2131231653);
        
        private int _resId;
        
        private ButtonType(final int resId) {
            this._resId = resId;
        }
    }
}
