/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;

public class MenuBarItem
extends LinearLayout {
    private ImageView _imageView;
    private boolean _necessaryInMainBar;
    private boolean _selectable;
    private int _selectionGroup;
    private TextView _textView;

    public MenuBarItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._necessaryInMainBar = false;
        this._selectionGroup = 0;
        this._selectable = true;
        this.initLayout(context);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.MenuBarItem);
        try {
            this.setTitle(context.getString(R.styleable.MenuBarItem_text));
            attributeSet = context.getDrawable(R.styleable.MenuBarItem_mIcon);
            if (attributeSet != null) {
                this._imageView.setImageDrawable((Drawable)attributeSet);
            } else {
                this.setIconDrawablesForStates(context.getDrawable(R.styleable.MenuBarItem_icon_drawable_usual), context.getDrawable(R.styleable.MenuBarItem_icon_drawable_selected), context.getDrawable(R.styleable.MenuBarItem_icon_drawable_disabled));
            }
            this._selectionGroup = context.getInt(R.styleable.MenuBarItem_selection_group, 0);
            this._selectable = context.getBoolean(R.styleable.MenuBarItem_selectable, true);
            return;
        }
        finally {
            context.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MenuBarItem(Context context, String string, int n, int n2, int n3) {
        Drawable drawable;
        block5 : {
            block4 : {
                super(context);
                this._necessaryInMainBar = false;
                this._selectionGroup = 0;
                this._selectable = true;
                this.initLayout(context);
                this.setTitle(string);
                drawable = context.getResources().getDrawable(n);
                try {
                    string = context.getResources().getDrawable(n2);
                    break block4;
                }
                catch (Resources.NotFoundException notFoundException) {}
                string = null;
            }
            try {
                context = context.getResources().getDrawable(n3);
                break block5;
            }
            catch (Resources.NotFoundException notFoundException) {}
            context = null;
        }
        this.setIconDrawablesForStates(drawable, (Drawable)string, (Drawable)context);
    }

    private void initLayout(Context context) {
        this.setOrientation(1);
        this.setGravity(17);
        MenuBarItem.inflate((Context)context, (int)R.layout.menubar_item, (ViewGroup)this);
        this.setClickable(true);
        this._imageView = (ImageView)this.findViewById(R.id.playzone_after_game_menubar_item_image);
        this._textView = (TextView)this.findViewById(R.id.playzone_after_game_menubar_item_text);
        this.setTitle(null);
    }

    public int getSelectionGroup() {
        return this._selectionGroup;
    }

    public boolean isNecessaryInMainBar() {
        return this._necessaryInMainBar;
    }

    public boolean isSelectable() {
        return this._selectable;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this._imageView.setEnabled(bl);
        this._textView.setEnabled(bl);
    }

    public void setGlowing(boolean bl) {
        if (bl) {
            GlowAnimation glowAnimation = new GlowAnimation(this._imageView);
            this._imageView.startAnimation((Animation)glowAnimation);
            return;
        }
        Animation animation = this._imageView.getAnimation();
        if (animation != null) {
            animation.cancel();
            this._imageView.setAnimation(null);
            this._imageView.setAlpha(255);
        }
    }

    public void setIconDrawablesForStates(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        if (drawable3 != null) {
            stateListDrawable.addState(new int[]{-16842910}, drawable3);
        }
        if (drawable2 != null) {
            stateListDrawable.addState(SELECTED_STATE_SET, drawable2);
            stateListDrawable.addState(PRESSED_ENABLED_STATE_SET, drawable2);
        }
        stateListDrawable.addState(EMPTY_STATE_SET, drawable);
        this.setMIcon((Drawable)stateListDrawable);
    }

    public void setMIcon(Drawable drawable) {
        this._imageView.setImageDrawable(drawable);
    }

    public void setNecessaryInMainBar(boolean bl) {
        this._necessaryInMainBar = bl;
    }

    public void setSelectable(boolean bl) {
        this._selectable = bl;
    }

    public void setSelectionGroup(int n) {
        this._selectionGroup = n;
    }

    public void setTitle(String string) {
        if (string != null && !string.isEmpty()) {
            this._textView.setText((CharSequence)string);
        }
        TextView textView = this._textView;
        int n = string != null ? 0 : 8;
        textView.setVisibility(n);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        CharSequence charSequence = this._textView != null ? this._textView.getText() : "no text set yet";
        stringBuilder.append((Object)charSequence);
        return stringBuilder.toString();
    }

    public class GlowAnimation
    extends Animation {
        private ImageView _blinkingImage;

        public GlowAnimation(ImageView imageView) {
            this._blinkingImage = imageView;
            this.setRepeatMode(2);
            this.setRepeatCount(-1);
            this.setDuration(1000L);
        }

        protected void applyTransformation(float f, Transformation transformation) {
            int n = (int)(200.0f * f);
            this._blinkingImage.setAlpha(n + 55);
        }
    }

}
