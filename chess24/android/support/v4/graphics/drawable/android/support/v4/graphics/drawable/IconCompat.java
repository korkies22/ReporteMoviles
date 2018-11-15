/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.Intent$ShortcutIconResource
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Icon
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 */
package android.support.v4.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;

public class IconCompat {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
    private static final int TYPE_ADAPTIVE_BITMAP = 5;
    private static final int TYPE_BITMAP = 1;
    private static final int TYPE_DATA = 3;
    private static final int TYPE_RESOURCE = 2;
    private static final int TYPE_URI = 4;
    private int mInt1;
    private int mInt2;
    private Object mObj1;
    private final int mType;

    private IconCompat(int n) {
        this.mType = n;
    }

    @VisibleForTesting
    static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap bitmap, boolean bl) {
        int n = (int)(0.6666667f * (float)Math.min(bitmap.getWidth(), bitmap.getHeight()));
        Bitmap bitmap2 = Bitmap.createBitmap((int)n, (int)n, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint(3);
        float f = n;
        float f2 = 0.5f * f;
        float f3 = 0.9166667f * f2;
        if (bl) {
            float f4 = 0.010416667f * f;
            paint.setColor(0);
            paint.setShadowLayer(f4, 0.0f, 0.020833334f * f, 1023410176);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.setShadowLayer(f4, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(-16777216);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate((float)((- bitmap.getWidth() - n) / 2), (float)((- bitmap.getHeight() - n) / 2));
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader((Shader)bitmapShader);
        canvas.drawCircle(f2, f2, f3, paint);
        canvas.setBitmap(null);
        return bitmap2;
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat iconCompat = new IconCompat(5);
        iconCompat.mObj1 = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat iconCompat = new IconCompat(1);
        iconCompat.mObj1 = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        return IconCompat.createWithContentUri(uri.toString());
    }

    public static IconCompat createWithContentUri(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat iconCompat = new IconCompat(4);
        iconCompat.mObj1 = string;
        return iconCompat;
    }

    public static IconCompat createWithData(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        IconCompat iconCompat = new IconCompat(3);
        iconCompat.mObj1 = arrby;
        iconCompat.mInt1 = n;
        iconCompat.mInt2 = n2;
        return iconCompat;
    }

    public static IconCompat createWithResource(Context context, @DrawableRes int n) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        IconCompat iconCompat = new IconCompat(2);
        iconCompat.mInt1 = n;
        iconCompat.mObj1 = context;
        return iconCompat;
    }

    @Deprecated
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void addToShortcutIntent(@NonNull Intent intent) {
        this.addToShortcutIntent(intent, null);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void addToShortcutIntent(@NonNull Intent intent, @Nullable Drawable drawable) {
        Bitmap bitmap;
        int n = this.mType;
        if (n != 5) {
            switch (n) {
                default: {
                    throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
                }
                case 2: {
                    if (drawable == null) {
                        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", (Parcelable)Intent.ShortcutIconResource.fromContext((Context)((Context)this.mObj1), (int)this.mInt1));
                        return;
                    }
                    bitmap = (Context)this.mObj1;
                    Drawable drawable2 = ContextCompat.getDrawable((Context)bitmap, this.mInt1);
                    if (drawable2.getIntrinsicWidth() > 0 && drawable2.getIntrinsicHeight() > 0) {
                        bitmap = Bitmap.createBitmap((int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    } else {
                        n = ((ActivityManager)bitmap.getSystemService("activity")).getLauncherLargeIconSize();
                        bitmap = Bitmap.createBitmap((int)n, (int)n, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    }
                    drawable2.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    drawable2.draw(new Canvas(bitmap));
                    break;
                }
                case 1: {
                    Bitmap bitmap2;
                    bitmap = bitmap2 = (Bitmap)this.mObj1;
                    if (drawable != null) {
                        bitmap = bitmap2.copy(bitmap2.getConfig(), true);
                        break;
                    } else {
                        break;
                    }
                }
            }
        } else {
            bitmap = IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
        }
        if (drawable != null) {
            n = bitmap.getWidth();
            int n2 = bitmap.getHeight();
            drawable.setBounds(n / 2, n2 / 2, n, n2);
            drawable.draw(new Canvas(bitmap));
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", (Parcelable)bitmap);
    }

    @RequiresApi(value=23)
    public Icon toIcon() {
        switch (this.mType) {
            default: {
                throw new IllegalArgumentException("Unknown type");
            }
            case 5: {
                if (Build.VERSION.SDK_INT >= 26) {
                    return Icon.createWithAdaptiveBitmap((Bitmap)((Bitmap)this.mObj1));
                }
                return Icon.createWithBitmap((Bitmap)IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
            }
            case 4: {
                return Icon.createWithContentUri((String)((String)this.mObj1));
            }
            case 3: {
                return Icon.createWithData((byte[])((byte[])this.mObj1), (int)this.mInt1, (int)this.mInt2);
            }
            case 2: {
                return Icon.createWithResource((Context)((Context)this.mObj1), (int)this.mInt1);
            }
            case 1: 
        }
        return Icon.createWithBitmap((Bitmap)((Bitmap)this.mObj1));
    }
}
