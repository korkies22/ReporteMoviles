// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.content.Context;

public class RoundedCouchImageView extends CouchImageView
{
    private int _cornerRadius;
    
    public RoundedCouchImageView(final Context context) {
        super(context);
        this.init();
    }
    
    public RoundedCouchImageView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this._cornerRadius = (int)(this.getContext().getResources().getDisplayMetrics().density * 5.0f);
    }
    
    public int getCornerRadius() {
        return this._cornerRadius;
    }
    
    public void setCornerRadius(final int cornerRadius) {
        this._cornerRadius = cornerRadius;
    }
    
    public void setImageBitmap(final Bitmap bitmap) {
        if (bitmap != null) {
            this.setImageDrawable((Drawable)new RoundedImageDrawable(bitmap, this._cornerRadius, 5));
        }
    }
}
