/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RectF
 */
package fi.harism.curl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class CurlPage {
    public static final int SIDE_BACK = 2;
    public static final int SIDE_BOTH = 3;
    public static final int SIDE_FRONT = 1;
    private int mColorBack;
    private int mColorFront;
    private Bitmap mTextureBack;
    private Bitmap mTextureFront;
    private boolean mTexturesChanged;

    public CurlPage() {
        this.reset();
    }

    private int getNextHighestPO2(int n) {
        --n;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return (n | n >> 32) + 1;
    }

    private Bitmap getTexture(Bitmap bitmap, RectF rectF) {
        int n = bitmap.getWidth();
        int n2 = bitmap.getHeight();
        int n3 = this.getNextHighestPO2(n);
        int n4 = this.getNextHighestPO2(n2);
        Bitmap bitmap2 = Bitmap.createBitmap((int)n3, (int)n4, (Bitmap.Config)bitmap.getConfig());
        new Canvas(bitmap2).drawBitmap(bitmap, 0.0f, 0.0f, null);
        rectF.set(0.0f, 0.0f, (float)n / (float)n3, (float)n2 / (float)n4);
        return bitmap2;
    }

    public int getColor(int n) {
        if (n != 1) {
            return this.mColorBack;
        }
        return this.mColorFront;
    }

    public Bitmap getTexture(RectF rectF, int n) {
        if (n != 1) {
            return this.getTexture(this.mTextureBack, rectF);
        }
        return this.getTexture(this.mTextureFront, rectF);
    }

    public boolean getTexturesChanged() {
        return this.mTexturesChanged;
    }

    public boolean hasBackTexture() {
        return this.mTextureFront.equals((Object)this.mTextureBack) ^ true;
    }

    public void recycle() {
        if (this.mTextureFront != null) {
            this.mTextureFront.recycle();
        }
        this.mTextureFront = Bitmap.createBitmap((int)1, (int)1, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        this.mTextureFront.eraseColor(this.mColorFront);
        if (this.mTextureBack != null) {
            this.mTextureBack.recycle();
        }
        this.mTextureBack = Bitmap.createBitmap((int)1, (int)1, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        this.mTextureBack.eraseColor(this.mColorBack);
        this.mTexturesChanged = false;
    }

    public void reset() {
        this.mColorBack = -1;
        this.mColorFront = -1;
        this.recycle();
    }

    public void setColor(int n, int n2) {
        switch (n2) {
            default: {
                this.mColorBack = n;
                this.mColorFront = n;
                return;
            }
            case 2: {
                this.mColorBack = n;
                return;
            }
            case 1: 
        }
        this.mColorFront = n;
    }

    public void setTexture(Bitmap bitmap, int n) {
        Bitmap bitmap2 = bitmap;
        if (bitmap == null) {
            bitmap2 = Bitmap.createBitmap((int)1, (int)1, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            if (n == 2) {
                bitmap2.eraseColor(this.mColorBack);
            } else {
                bitmap2.eraseColor(this.mColorFront);
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 3: {
                if (this.mTextureFront != null) {
                    this.mTextureFront.recycle();
                }
                if (this.mTextureBack != null) {
                    this.mTextureBack.recycle();
                }
                this.mTextureBack = bitmap2;
                this.mTextureFront = bitmap2;
                break;
            }
            case 2: {
                if (this.mTextureBack != null) {
                    this.mTextureBack.recycle();
                }
                this.mTextureBack = bitmap2;
                break;
            }
            case 1: {
                if (this.mTextureFront != null) {
                    this.mTextureFront.recycle();
                }
                this.mTextureFront = bitmap2;
            }
        }
        this.mTexturesChanged = true;
    }
}
