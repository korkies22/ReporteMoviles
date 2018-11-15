/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 */
package de.cisha.chess.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BitmapHelper {
    public static Bitmap blurImage(Bitmap arrn, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int[] arrn2;
        int n13 = n;
        Bitmap bitmap = arrn.copy(arrn.getConfig(), true);
        if (n13 < 1) {
            return null;
        }
        int n14 = bitmap.getWidth();
        int n15 = bitmap.getHeight();
        int n16 = n14 * n15;
        int[] arrn3 = new int[n16];
        bitmap.getPixels(arrn3, 0, n14, 0, 0, n14, n15);
        int n17 = n14 - 1;
        int n18 = n15 - 1;
        int n19 = n13 + n13 + 1;
        int[] arrn4 = new int[n16];
        int[] arrn5 = new int[n16];
        int[] arrn6 = new int[n16];
        arrn = new int[Math.max(n14, n15)];
        n16 = n19 + 1 >> 1;
        int n20 = n16 * n16;
        int n21 = 256 * n20;
        int[] arrn7 = new int[n21];
        for (n16 = 0; n16 < n21; ++n16) {
            arrn7[n16] = n16 / n20;
        }
        int[][] arrn8 = (int[][])Array.newInstance(Integer.TYPE, n19, 3);
        int n22 = n13 + 1;
        int n23 = 0;
        int n24 = 0;
        n16 = n18;
        for (n8 = 0; n8 < n15; ++n8) {
            n5 = 0;
            n11 = 0;
            n2 = 0;
            n21 = 0;
            n12 = 0;
            n7 = 0;
            n3 = 0;
            n20 = 0;
            n18 = 0;
            for (n9 = - n13; n9 <= n13; ++n9) {
                n6 = arrn3[n23 + Math.min(n17, Math.max(n9, 0))];
                arrn2 = arrn8[n9 + n13];
                arrn2[0] = (n6 & 16711680) >> 16;
                arrn2[1] = (n6 & 65280) >> 8;
                arrn2[2] = n6 & 255;
                n6 = n22 - Math.abs(n9);
                n5 += arrn2[0] * n6;
                n11 += arrn2[1] * n6;
                n2 += arrn2[2] * n6;
                if (n9 > 0) {
                    n21 += arrn2[0];
                    n12 += arrn2[1];
                    n7 += arrn2[2];
                    continue;
                }
                n3 += arrn2[0];
                n20 += arrn2[1];
                n18 += arrn2[2];
            }
            n4 = n13;
            n10 = 0;
            n9 = n12;
            n6 = n21;
            n12 = n4;
            for (n21 = n10; n21 < n14; ++n21) {
                arrn4[n23] = arrn7[n5];
                arrn5[n23] = arrn7[n11];
                arrn6[n23] = arrn7[n2];
                arrn2 = arrn8[(n12 - n13 + n19) % n19];
                int n25 = arrn2[0];
                n10 = arrn2[1];
                n4 = arrn2[2];
                if (n8 == 0) {
                    arrn[n21] = Math.min(n21 + n13 + 1, n17);
                }
                int n26 = arrn3[n24 + arrn[n21]];
                arrn2[0] = (n26 & 16711680) >> 16;
                arrn2[1] = (n26 & 65280) >> 8;
                arrn2[2] = n26 & 255;
                n5 = n5 - n3 + (n6 += arrn2[0]);
                n11 = n11 - n20 + (n9 += arrn2[1]);
                n2 = n2 - n18 + (n7 += arrn2[2]);
                n12 = (n12 + 1) % n19;
                arrn2 = arrn8[n12 % n19];
                n3 = n3 - n25 + arrn2[0];
                n20 = n20 - n10 + arrn2[1];
                n18 = n18 - n4 + arrn2[2];
                n6 -= arrn2[0];
                n9 -= arrn2[1];
                n7 -= arrn2[2];
                ++n23;
            }
            n24 += n14;
        }
        n13 = n15;
        n18 = 0;
        n15 = n16;
        n16 = n18;
        do {
            n24 = n;
            if (n16 >= n14) break;
            n23 = n8 * n14;
            n5 = 0;
            n11 = 0;
            n2 = 0;
            n7 = 0;
            n3 = 0;
            n12 = 0;
            n18 = 0;
            n20 = 0;
            n21 = 0;
            for (n8 = - n24; n8 <= n24; ++n8) {
                n6 = Math.max(0, n23) + n16;
                arrn2 = arrn8[n8 + n24];
                arrn2[0] = arrn4[n6];
                arrn2[1] = arrn5[n6];
                arrn2[2] = arrn6[n6];
                n4 = n22 - Math.abs(n8);
                n9 = n5 + arrn4[n6] * n4;
                n11 += arrn5[n6] * n4;
                n2 += arrn6[n6] * n4;
                if (n8 > 0) {
                    n7 += arrn2[0];
                    n3 += arrn2[1];
                    n12 += arrn2[2];
                } else {
                    n18 += arrn2[0];
                    n20 += arrn2[1];
                    n21 += arrn2[2];
                }
                n5 = n23;
                if (n8 < n15) {
                    n5 = n23 + n14;
                }
                n23 = n5;
                n5 = n9;
            }
            n8 = n16;
            n23 = n3;
            n6 = n12;
            n4 = 0;
            n12 = n24;
            n9 = n7;
            n7 = n2;
            n3 = n11;
            n24 = n6;
            n2 = n5;
            n11 = n8;
            for (n5 = n4; n5 < n13; ++n5) {
                arrn3[n11] = -16777216 & arrn3[n11] | arrn7[n2] << 16 | arrn7[n3] << 8 | arrn7[n7];
                arrn2 = arrn8[(n12 - n + n19) % n19];
                n4 = arrn2[0];
                n6 = arrn2[1];
                n8 = arrn2[2];
                if (n16 == 0) {
                    arrn[n5] = Math.min(n5 + n22, n15) * n14;
                }
                n10 = arrn[n5] + n16;
                arrn2[0] = arrn4[n10];
                arrn2[1] = arrn5[n10];
                arrn2[2] = arrn6[n10];
                n2 = n2 - n18 + (n9 += arrn2[0]);
                n3 = n3 - n20 + (n23 += arrn2[1]);
                n7 = n7 - n21 + (n24 += arrn2[2]);
                n12 = (n12 + 1) % n19;
                arrn2 = arrn8[n12];
                n18 = n18 - n4 + arrn2[0];
                n20 = n20 - n6 + arrn2[1];
                n21 = n21 - n8 + arrn2[2];
                n9 -= arrn2[0];
                n23 -= arrn2[1];
                n24 -= arrn2[2];
                n11 += n14;
            }
            ++n16;
        } while (true);
        bitmap.setPixels(arrn3, 0, n14, 0, 0, n14, n13);
        return bitmap;
    }

    public static InputStream createJPEG(Bitmap bitmap, int n) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, n, (OutputStream)byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static InputStream createPNG(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int n, int n2, int n3) {
        Bitmap bitmap2 = Bitmap.createBitmap((int)n2, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader((Shader)new BitmapShader(Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n2, (int)n3, (boolean)true), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        bitmap = new RectF(0.0f, 0.0f, (float)n2, (float)n3);
        float f = n;
        canvas.drawRoundRect((RectF)bitmap, f, f, paint);
        return bitmap2;
    }

    public static Bitmap loadImageFromWeb(URL object) throws IOException {
        int n = (object = (HttpURLConnection)object.openConnection()).getResponseCode();
        boolean bl = n >= 400;
        if (bl) {
            object = new StringBuilder();
            object.append("http error code:");
            object.append(n);
            throw new IOException(object.toString());
        }
        InputStream inputStream = object.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)inputStream);
        inputStream.close();
        object.disconnect();
        return bitmap;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int n) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float)n, (float)(bitmap.getWidth() / 2), (float)(bitmap.getHeight() / 2));
        return Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)bitmap.getWidth(), (int)bitmap.getHeight(), (Matrix)matrix, (boolean)true);
    }

    public static Bitmap scaleBitmapToSize(Bitmap bitmap, int n) {
        float f = n;
        n = (int)((float)bitmap.getHeight() * (f / (float)bitmap.getWidth()));
        return Bitmap.createScaledBitmap((Bitmap)bitmap, (int)((int)f), (int)n, (boolean)true);
    }
}
