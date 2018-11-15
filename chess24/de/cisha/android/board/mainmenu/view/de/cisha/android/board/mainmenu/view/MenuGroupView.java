/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.RotateAnimation
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.mainmenu.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.mainmenu.view.DropDownAnimation;
import de.cisha.android.board.mainmenu.view.MenuItemView;

public class MenuGroupView
extends LinearLayout {
    private boolean _animating = false;
    private ImageView _arrowImageView;
    private int _durationTime = 500;
    private ViewGroup _itemContainer;
    private TextView _nameTextView;
    private boolean _open = true;

    public MenuGroupView(Context context) {
        super(context);
    }

    public MenuGroupView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void close() {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -90.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration((long)this._durationTime);
        this._animating = true;
        DropDownAnimation dropDownAnimation = new DropDownAnimation((View)this._itemContainer, this._itemContainer.getHeight(), false);
        dropDownAnimation.setFillAfter(true);
        dropDownAnimation.setDuration((long)this._durationTime);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                MenuGroupView.this._animating = false;
                MenuGroupView.this._open = false;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        this._itemContainer.startAnimation((Animation)dropDownAnimation);
        this._arrowImageView.startAnimation((Animation)rotateAnimation);
    }

    private void init() {
        this._itemContainer = (ViewGroup)this.findViewById(2131296593);
        this._nameTextView = (TextView)this.findViewById(2131296594);
        this._arrowImageView = (ImageView)this.findViewById(2131296339);
        this._nameTextView.setShadowLayer(1.0f, 0.0f, -1.0f, Color.argb((int)255, (int)64, (int)64, (int)64));
        ((ViewGroup)this.findViewById(2131296565)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuGroupView.this.toggle();
            }
        });
        this._nameTextView.bringToFront();
    }

    private void open() {
        RotateAnimation rotateAnimation = new RotateAnimation(-90.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration((long)this._durationTime);
        this._animating = true;
        this._itemContainer.measure(View.MeasureSpec.makeMeasureSpec((int)1000000, (int)0), View.MeasureSpec.makeMeasureSpec((int)1000000, (int)0));
        int n = this._itemContainer.getMeasuredHeight();
        DropDownAnimation dropDownAnimation = new DropDownAnimation((View)this._itemContainer, n, true);
        dropDownAnimation.setFillAfter(true);
        dropDownAnimation.setDuration((long)this._durationTime);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                MenuGroupView.this._animating = false;
                MenuGroupView.this._open = true;
                MenuGroupView.this._itemContainer.clearAnimation();
                MenuGroupView.this._arrowImageView.clearAnimation();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        this._itemContainer.startAnimation((Animation)dropDownAnimation);
        this._arrowImageView.startAnimation((Animation)rotateAnimation);
    }

    public void addMenuItem(MenuItemView menuItemView) {
        if (this._itemContainer.getChildCount() > 0) {
            View view = new View(this.getContext());
            view.setBackgroundResource(2131231439);
            this._itemContainer.addView(view, -1, 4);
        }
        this._itemContainer.addView((View)menuItemView);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.init();
    }

    public void setText(String string) {
        this._nameTextView.setText((CharSequence)string);
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
