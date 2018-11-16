// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MenuItemView extends RelativeLayout implements MenuItemListener
{
    private ImageView _image;
    private View _indicatorNew;
    private MenuItem _menuItem;
    private TextView _notification;
    private int _paddingBottom;
    private int _paddingLeft;
    private int _paddingRight;
    private int _paddingTop;
    private CustomTextView _text;
    
    public MenuItemView(final Context context, final AttributeSet set) {
        super(context, set);
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
    
    public void highlight(final boolean b) {
        if (this._menuItem.hasAction()) {
            this._text.setSelected(b);
            this.setSelected(b);
            if (b) {
                this.setImageResource(this._menuItem.getIconId());
                return;
            }
            this.setImageResource(this._menuItem.getIconIdInactive());
        }
    }
    
    public void menuTitleChanged() {
        this._text.setText((CharSequence)this._menuItem.getTitle(this.getResources()));
    }
    
    public void notificationUpdate(final String notificationText) {
        this.setNotificationText(notificationText);
    }
    
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.init();
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        final TextView notification = this._notification;
        int visibility;
        if (this._notification.getText() != null && this._notification.getText().length() != 0) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        notification.setVisibility(visibility);
        super.onLayout(b, n, n2, n3, n4);
    }
    
    public void setImageResource(final int imageResource) {
        this._image.setImageResource(imageResource);
        this.setPadding(this._paddingLeft, this._paddingTop, this._paddingRight, this._paddingBottom);
    }
    
    public void setMenuItem(final MenuItem menuItem) {
        this._menuItem = menuItem;
        this.setText(this._menuItem.getTitle(this.getResources()));
        this.setNotificationText(this._menuItem.getNotification());
        this.setImageResource(this._menuItem.getIconId());
        this.show(this._menuItem.isShown());
        final View indicatorNew = this._indicatorNew;
        int visibility;
        if (menuItem.isNew()) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        indicatorNew.setVisibility(visibility);
        this._menuItem.addNotificationListener((MenuItem.MenuItemListener)this);
    }
    
    public void setNotificationText(final String text) {
        this._notification.setText((CharSequence)text);
    }
    
    public void setText(final String text) {
        this._text.setText((CharSequence)text);
    }
    
    public void show(final boolean b) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                final MenuItemView this.0 = MenuItemView.this;
                int visibility;
                if (b) {
                    visibility = 0;
                }
                else {
                    visibility = 8;
                }
                this.0.setVisibility(visibility);
            }
        });
    }
}
