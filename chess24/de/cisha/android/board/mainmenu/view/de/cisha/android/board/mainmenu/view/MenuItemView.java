/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.mainmenu.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.ui.patterns.text.CustomTextView;

public class MenuItemView
extends RelativeLayout
implements MenuItem.MenuItemListener {
    private ImageView _image;
    private View _indicatorNew;
    private MenuItem _menuItem;
    private TextView _notification;
    private int _paddingBottom;
    private int _paddingLeft;
    private int _paddingRight;
    private int _paddingTop;
    private CustomTextView _text;

    public MenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void init() {
        this._paddingLeft = this.getPaddingLeft();
        this._paddingRight = this.getPaddingRight();
        this._paddingBottom = this.getPaddingBottom();
        this._paddingTop = this.getPaddingTop();
        this._menuItem = MenuItem.PLAYNOW;
        this._text = (CustomTextView)this.findViewById(2131296601);
        this._indicatorNew = this.findViewById(2131296600);
        this._image = (ImageView)this.findViewById(2131296599);
        this._notification = (TextView)this.findViewById(2131296613);
    }

    @Override
    public void highlight(boolean bl) {
        if (this._menuItem.hasAction()) {
            this._text.setSelected(bl);
            this.setSelected(bl);
            if (bl) {
                this.setImageResource(this._menuItem.getIconId());
                return;
            }
            this.setImageResource(this._menuItem.getIconIdInactive());
        }
    }

    @Override
    public void menuTitleChanged() {
        this._text.setText((CharSequence)this._menuItem.getTitle(this.getResources()));
    }

    @Override
    public void notificationUpdate(String string) {
        this.setNotificationText(string);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.init();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        TextView textView = this._notification;
        int n5 = this._notification.getText() != null && this._notification.getText().length() != 0 ? 0 : 8;
        textView.setVisibility(n5);
        super.onLayout(bl, n, n2, n3, n4);
    }

    public void setImageResource(int n) {
        this._image.setImageResource(n);
        this.setPadding(this._paddingLeft, this._paddingTop, this._paddingRight, this._paddingBottom);
    }

    public void setMenuItem(MenuItem menuItem) {
        this._menuItem = menuItem;
        this.setText(this._menuItem.getTitle(this.getResources()));
        this.setNotificationText(this._menuItem.getNotification());
        this.setImageResource(this._menuItem.getIconId());
        this.show(this._menuItem.isShown());
        View view = this._indicatorNew;
        int n = menuItem.isNew() ? 0 : 8;
        view.setVisibility(n);
        this._menuItem.addNotificationListener(this);
    }

    public void setNotificationText(String string) {
        this._notification.setText((CharSequence)string);
    }

    public void setText(String string) {
        this._text.setText((CharSequence)string);
    }

    @Override
    public void show(final boolean bl) {
        this.post(new Runnable(){

            @Override
            public void run() {
                MenuItemView menuItemView = MenuItemView.this;
                int n = bl ? 0 : 8;
                menuItemView.setVisibility(n);
            }
        });
    }

}
