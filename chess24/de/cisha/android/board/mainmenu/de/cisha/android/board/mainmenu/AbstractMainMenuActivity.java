/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.mainmenu;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.mainmenu.view.MainMenuSlider;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.mainmenu.view.MenuSlider;
import de.cisha.android.board.mainmenu.view.ObservableMenu;
import de.cisha.android.board.mainmenu.view.SettingsMenuSlider;
import de.cisha.android.board.mainmenu.view.SlideMenuInflater;
import de.cisha.android.board.registration.SingleScreenFragmentActivity;

public abstract class AbstractMainMenuActivity
extends SingleScreenFragmentActivity {
    private boolean _grabMenu = true;
    private TextView _headerText;
    private MainMenuSlider _menuSlider;
    private SettingsMenuSlider _settingsMenuSlider;
    private ButtonType _topLeftButton;
    private ImageView _topLeftButtonView;
    private ButtonType _topRightButton;
    private ImageView _topRightButtonView;

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        if (this._menuSlider != null) {
            motionEvent2 = motionEvent;
            if (this.showMenu()) {
                motionEvent2 = this._menuSlider.dispatchSlideMotion(motionEvent);
            }
        }
        motionEvent = motionEvent2;
        if (this._settingsMenuSlider != null) {
            motionEvent = this._settingsMenuSlider.dispatchSlideMotion(motionEvent2);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    protected ViewGroup getContentView() {
        return (ViewGroup)this.findViewById(2131296260);
    }

    protected int getContentViewHeight() {
        int n;
        int n2 = n = MenuSlider.getContentViewHeight();
        if (n <= 0) {
            n2 = (int)((float)this.getResources().getDisplayMetrics().widthPixels - 75.0f * this.getResources().getDisplayMetrics().density);
        }
        return n2;
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
        MenuSlider menuSlider = this.getMainMenuSlider();
        if (menuSlider != null && !this.getMainMenuSlider().isClosed()) {
            menuSlider.closeWithAnimation();
            return;
        }
        if (this._settingsMenuSlider != null && !this._settingsMenuSlider.isClosed()) {
            this._settingsMenuSlider.closeWithAnimation();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        super.setContentView(2131427357);
        this._headerText = (TextView)this.findViewById(2131296543);
        this._headerText.setText((CharSequence)"chess24");
        if (this.showSettingsMenu()) {
            this._settingsMenuSlider = (SettingsMenuSlider)((ViewGroup)this.getLayoutInflater().inflate(2131427537, (ViewGroup)this.findViewById(2131296588))).findViewById(2131296956);
        }
        this.getContentView().setDrawingCacheEnabled(true);
        object = (ViewGroup)this.findViewById(2131296609);
        ViewGroup viewGroup = (ViewGroup)this.findViewById(2131296610);
        this._topLeftButtonView = (ImageView)this.getLayoutInflater().inflate(2131427462, (ViewGroup)object, false);
        this._topLeftButtonView.setImageResource(2131230867);
        this._topRightButtonView = (ImageView)this.getLayoutInflater().inflate(2131427462, viewGroup, false);
        object.addView((View)this._topLeftButtonView);
        viewGroup.addView((View)this._topRightButtonView);
        object = this.showMenu() ? ButtonType.NO : ButtonType.BACK;
        this.setTopButton((ButtonType)((Object)object), true);
        this.setTopButton(ButtonType.NO, false);
        this._topLeftButtonView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
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
        MenuItem menuItem;
        super.onStart();
        if (this.showMenu()) {
            this._menuSlider = new SlideMenuInflater(this, null).addMainMenu();
            this._menuSlider.setMenuGrabBound(35);
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
        if ((menuItem = this.getHiglightedMenuItem()) != null) {
            menuItem.highlight(true);
            return;
        }
        MenuItem.removeHighlightFromAll();
    }

    public void setContentView(int n) {
        this.getLayoutInflater().inflate(n, this.getContentView());
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.bringToFront();
        }
        if (this._menuSlider != null) {
            this._menuSlider.bringToFront();
        }
    }

    protected void setGrabMenuEnabled(boolean bl) {
        this._grabMenu = bl;
        if (this._menuSlider != null) {
            this._menuSlider.setGrabMenuEnabled(bl);
        }
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.setGrabMenuEnabled(bl);
        }
    }

    protected void setHeaderText(String string) {
        this._headerText.setText((CharSequence)string);
    }

    protected void setSettingsMenu(View view) {
        if (this._settingsMenuSlider != null) {
            this.getSettingsMenuSlider().getSettingsContentContainer().addView(view);
            this._settingsMenuSlider.setGrabMenuEnabled(this._grabMenu);
        }
    }

    protected void setSettingsTitle(String string) {
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.setHeaderText(string);
        }
    }

    protected void setTopButton(ButtonType buttonType, boolean bl) {
        if (bl) {
            this._topLeftButton = buttonType;
        } else {
            this._topRightButton = buttonType;
        }
        ImageView imageView = bl ? this._topLeftButtonView : this._topRightButtonView;
        if (buttonType != ButtonType.NO) {
            imageView.setVisibility(0);
            imageView.setImageDrawable(this.getResources().getDrawable(buttonType._resId));
            return;
        }
        imageView.setVisibility(8);
    }

    protected void setTopButtonClickListener(View.OnClickListener onClickListener, boolean bl) {
        ImageView imageView = bl ? this._topLeftButtonView : this._topRightButtonView;
        imageView.setOnClickListener(onClickListener);
    }

    protected boolean showMenu() {
        return true;
    }

    protected boolean showSettingsMenu() {
        return false;
    }

    protected static enum ButtonType {
        NO(0),
        BACK(2131230867),
        SAVE(2131231653),
        SAVE_VALID(2131231653),
        SAVE_INVALID(2131231033),
        CLOSE(2131231037);
        
        private int _resId;

        private ButtonType(int n2) {
            this._resId = n2;
        }
    }

}
