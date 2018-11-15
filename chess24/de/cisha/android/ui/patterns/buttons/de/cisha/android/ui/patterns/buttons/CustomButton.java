/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.buttons;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.text.FontName;

public class CustomButton
extends TextView {
    private int _bgResIdDefault;
    private boolean _enabled;

    public CustomButton(Context context) {
        super(context);
        this._enabled = true;
        this.init();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public CustomButton(Context context, AttributeSet attributeSet) {
        block4 : {
            super(context, attributeSet);
            this._enabled = true;
            context = context.obtainStyledAttributes(attributeSet, R.styleable.CustomButton);
            try {
                this._bgResIdDefault = context.getResourceId(R.styleable.CustomButton_bg_activ_drawable, 0);
                this._enabled = context.getBoolean(R.styleable.CustomButton_disabled, false) ^ true;
                break block4;
            }
            catch (Throwable throwable) {}
            context.recycle();
            throw throwable;
            catch (Exception exception) {}
            Log.e((String)"CustomButton", (String)"Error reading attributes");
        }
        context.recycle();
        this.init();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void init() {
        StringBuilder stringBuilder;
        block4 : {
            this.setClickable(this._enabled);
            this.setEnabled(this._enabled);
            if (this.getBackgroundResourceId() > 0) {
                this.setBackgroundResource(this.getBackgroundResourceId());
            }
            try {
                this.setTypeface(Typeface.createFromAsset((AssetManager)this.getContext().getAssets(), (String)this.getFont().getAssetsFileName()));
                break block4;
            }
            catch (Exception exception) {}
            stringBuilder = new StringBuilder();
            stringBuilder.append("Font not Found: ");
            stringBuilder.append(this.getFont().getAssetsFileName());
            Log.e((String)"CustomButton", (String)stringBuilder.toString());
        }
        int n = this.getTextSizeResDimenId();
        stringBuilder = this.getResources();
        if (n <= 0) {
            n = R.dimen.custom_button_text_size;
        }
        this.setTextSize(0, stringBuilder.getDimension(n));
    }

    protected int getBackgroundResourceId() {
        return this._bgResIdDefault;
    }

    protected FontName getFont() {
        return FontName.TREBUCHET;
    }

    protected int getTextColorActive() {
        return -1;
    }

    protected int getTextColorDefault() {
        return -1;
    }

    protected int getTextColorInactive() {
        return -1;
    }

    protected int getTextSizeResDimenId() {
        return 0;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this._enabled) {
            if (motionEvent.getAction() == 0) {
                this.setTextColor(this.getTextColorActive());
            } else if (1 == motionEvent.getAction() || 3 == motionEvent.getAction()) {
                this.setTextColor(this.getTextColorActive());
            }
        } else {
            this.setTextColor(this.getTextColorInactive());
        }
        return super.onTouchEvent(motionEvent);
    }

    protected void setButtonEnabled(boolean bl) {
        this._enabled = bl;
        if (bl) {
            this.setTextColor(this.getTextColorDefault());
            return;
        }
        this.setTextColor(this.getTextColorInactive());
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.setButtonEnabled(bl);
    }
}
