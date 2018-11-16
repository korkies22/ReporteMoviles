// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MenuGroupView extends LinearLayout
{
    private boolean _animating;
    private ImageView _arrowImageView;
    private int _durationTime;
    private ViewGroup _itemContainer;
    private TextView _nameTextView;
    private boolean _open;
    
    public MenuGroupView(final Context context) {
        super(context);
        this._animating = false;
        this._open = true;
        this._durationTime = 500;
    }
    
    public MenuGroupView(final Context context, final AttributeSet set) {
        super(context, set);
        this._animating = false;
        this._open = true;
        this._durationTime = 500;
    }
    
    private void close() {
        final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -90.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration((long)this._durationTime);
        this._animating = true;
        final DropDownAnimation dropDownAnimation = new DropDownAnimation((View)this._itemContainer, this._itemContainer.getHeight(), false);
        dropDownAnimation.setFillAfter(true);
        dropDownAnimation.setDuration((long)this._durationTime);
        rotateAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                MenuGroupView.this._animating = false;
                MenuGroupView.this._open = false;
            }
            
            public void onAnimationRepeat(final Animation animation) {
            }
            
            public void onAnimationStart(final Animation animation) {
            }
        });
        this._itemContainer.startAnimation((Animation)dropDownAnimation);
        this._arrowImageView.startAnimation((Animation)rotateAnimation);
    }
    
    private void init() {
        this._itemContainer = (ViewGroup)this.findViewById(2131296593);
        this._nameTextView = (TextView)this.findViewById(2131296594);
        this._arrowImageView = (ImageView)this.findViewById(2131296339);
        this._nameTextView.setShadowLayer(1.0f, 0.0f, -1.0f, Color.argb(255, 64, 64, 64));
        ((ViewGroup)this.findViewById(2131296565)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MenuGroupView.this.toggle();
            }
        });
        this._nameTextView.bringToFront();
    }
    
    private void open() {
        final RotateAnimation rotateAnimation = new RotateAnimation(-90.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration((long)this._durationTime);
        this._animating = true;
        this._itemContainer.measure(View.MeasureSpec.makeMeasureSpec(1000000, 0), View.MeasureSpec.makeMeasureSpec(1000000, 0));
        final DropDownAnimation dropDownAnimation = new DropDownAnimation((View)this._itemContainer, this._itemContainer.getMeasuredHeight(), true);
        dropDownAnimation.setFillAfter(true);
        dropDownAnimation.setDuration((long)this._durationTime);
        rotateAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                MenuGroupView.this._animating = false;
                MenuGroupView.this._open = true;
                MenuGroupView.this._itemContainer.clearAnimation();
                MenuGroupView.this._arrowImageView.clearAnimation();
            }
            
            public void onAnimationRepeat(final Animation animation) {
            }
            
            public void onAnimationStart(final Animation animation) {
            }
        });
        this._itemContainer.startAnimation((Animation)dropDownAnimation);
        this._arrowImageView.startAnimation((Animation)rotateAnimation);
    }
    
    public void addMenuItem(final MenuItemView menuItemView) {
        if (this._itemContainer.getChildCount() > 0) {
            final View view = new View(this.getContext());
            view.setBackgroundResource(2131231439);
            this._itemContainer.addView(view, -1, 4);
        }
        this._itemContainer.addView((View)menuItemView);
    }
    
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.init();
    }
    
    public void setText(final String text) {
        this._nameTextView.setText((CharSequence)text);
    }
    
    public void toggle() {
        if (!this._animating) {
            if (this._open) {
                this.close();
                return;
            }
            this.open();
        }
    }
}
