// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import android.content.res.TypedArray;
import android.view.MotionEvent;
import de.cisha.android.ui.patterns.text.FontName;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;

public class CustomButton extends TextView
{
    private int _bgResIdDefault;
    private boolean _enabled;
    
    public CustomButton(final Context context) {
        super(context);
        this._enabled = true;
        this.init();
    }
    
    public CustomButton(Context obtainStyledAttributes, final AttributeSet set) {
        super(obtainStyledAttributes, set);
        this._enabled = true;
        obtainStyledAttributes = (Context)obtainStyledAttributes.obtainStyledAttributes(set, R.styleable.CustomButton);
        while (true) {
            try {
                Label_0070: {
                    Label_0061: {
                        try {
                            this._bgResIdDefault = ((TypedArray)obtainStyledAttributes).getResourceId(R.styleable.CustomButton_bg_activ_drawable, 0);
                            this._enabled = (((TypedArray)obtainStyledAttributes).getBoolean(R.styleable.CustomButton_disabled, false) ^ true);
                            break Label_0061;
                        }
                        finally {
                            break Label_0070;
                        }
                        Log.e("CustomButton", "Error reading attributes");
                    }
                    ((TypedArray)obtainStyledAttributes).recycle();
                    this.init();
                    return;
                }
                ((TypedArray)obtainStyledAttributes).recycle();
                throw;
            }
            catch (Exception ex) {}
            continue;
        }
    }
    
    private void init() {
        this.setClickable(this._enabled);
        this.setEnabled(this._enabled);
        if (this.getBackgroundResourceId() > 0) {
            this.setBackgroundResource(this.getBackgroundResourceId());
        }
    Label_0055_Outer:
        while (true) {
            while (true) {
                try {
                    this.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), this.getFont().getAssetsFileName()));
                    while (true) {
                        int n = this.getTextSizeResDimenId();
                        final Resources resources = this.getResources();
                        if (n <= 0) {
                            n = R.dimen.custom_button_text_size;
                        }
                        this.setTextSize(0, resources.getDimension(n));
                        return;
                        final StringBuilder sb = new StringBuilder();
                        sb.append("Font not Found: ");
                        sb.append(this.getFont().getAssetsFileName());
                        Log.e("CustomButton", sb.toString());
                        continue Label_0055_Outer;
                    }
                }
                catch (Exception ex) {}
                continue;
            }
        }
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
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (this._enabled) {
            if (motionEvent.getAction() == 0) {
                this.setTextColor(this.getTextColorActive());
            }
            else if (1 == motionEvent.getAction() || 3 == motionEvent.getAction()) {
                this.setTextColor(this.getTextColorActive());
            }
        }
        else {
            this.setTextColor(this.getTextColorInactive());
        }
        return super.onTouchEvent(motionEvent);
    }
    
    protected void setButtonEnabled(final boolean enabled) {
        this._enabled = enabled;
        if (enabled) {
            this.setTextColor(this.getTextColorDefault());
            return;
        }
        this.setTextColor(this.getTextColorInactive());
    }
    
    public void setEnabled(final boolean b) {
        super.setEnabled(b);
        this.setButtonEnabled(b);
    }
}
