// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.BitmapShader;
import android.graphics.Shader.TileMode;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import android.graphics.Bitmap.CompressFormat;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import android.graphics.Bitmap;

public class BitmapHelper
{
    public static Bitmap blurImage(final Bitmap bitmap, final int n) {
        final Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        if (n < 1) {
            return null;
        }
        final int width = copy.getWidth();
        final int height = copy.getHeight();
        final int n2 = width * height;
        final int[] array = new int[n2];
        copy.getPixels(array, 0, width, 0, 0, width, height);
        final int n3 = width - 1;
        final int n4 = height - 1;
        final int n5 = n + n + 1;
        final int[] array2 = new int[n2];
        final int[] array3 = new int[n2];
        final int[] array4 = new int[n2];
        final int[] array5 = new int[Math.max(width, height)];
        final int n6 = n5 + 1 >> 1;
        final int n7 = n6 * n6;
        final int n8 = 256 * n7;
        final int[] array6 = new int[n8];
        for (int i = 0; i < n8; ++i) {
            array6[i] = i / n7;
        }
        final int[][] array7 = (int[][])Array.newInstance(Integer.TYPE, n5, 3);
        final int n9 = n + 1;
        int j = 0;
        int n10 = 0;
        int n11 = 0;
        final int n12 = n4;
        while (j < height) {
            int k = -n;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int n16 = 0;
            int n17 = 0;
            int n18 = 0;
            int n19 = 0;
            int n20 = 0;
            int n21 = 0;
            while (k <= n) {
                final int n22 = array[n10 + Math.min(n3, Math.max(k, 0))];
                final int[] array8 = array7[k + n];
                array8[0] = (n22 & 0xFF0000) >> 16;
                array8[1] = (n22 & 0xFF00) >> 8;
                array8[2] = (n22 & 0xFF);
                final int n23 = n9 - Math.abs(k);
                n13 += array8[0] * n23;
                n14 += array8[1] * n23;
                n15 += array8[2] * n23;
                if (k > 0) {
                    n16 += array8[0];
                    n17 += array8[1];
                    n18 += array8[2];
                }
                else {
                    n19 += array8[0];
                    n20 += array8[1];
                    n21 += array8[2];
                }
                ++k;
            }
            final int n24 = 0;
            int n25 = n17;
            int n26 = n16;
            int n27 = n;
            for (int l = n24; l < width; ++l) {
                array2[n10] = array6[n13];
                array3[n10] = array6[n14];
                array4[n10] = array6[n15];
                final int[] array9 = array7[(n27 - n + n5) % n5];
                final int n28 = array9[0];
                final int n29 = array9[1];
                final int n30 = array9[2];
                if (j == 0) {
                    array5[l] = Math.min(l + n + 1, n3);
                }
                final int n31 = array[n11 + array5[l]];
                array9[0] = (n31 & 0xFF0000) >> 16;
                array9[1] = (n31 & 0xFF00) >> 8;
                array9[2] = (n31 & 0xFF);
                final int n32 = n26 + array9[0];
                final int n33 = n25 + array9[1];
                final int n34 = n18 + array9[2];
                n13 = n13 - n19 + n32;
                n14 = n14 - n20 + n33;
                n15 = n15 - n21 + n34;
                n27 = (n27 + 1) % n5;
                final int[] array10 = array7[n27 % n5];
                n19 = n19 - n28 + array10[0];
                n20 = n20 - n29 + array10[1];
                n21 = n21 - n30 + array10[2];
                n26 = n32 - array10[0];
                n25 = n33 - array10[1];
                n18 = n34 - array10[2];
                ++n10;
            }
            n11 += width;
            ++j;
        }
        final int n35 = height;
        final int n36 = 0;
        final int n37 = n12;
        for (int n38 = n36; n38 < width; ++n38) {
            int n39 = -n;
            int n40 = n39 * width;
            int n41 = 0;
            int n42 = 0;
            int n43 = 0;
            int n44 = 0;
            int n45 = 0;
            int n46 = 0;
            int n47 = 0;
            int n48 = 0;
            int n49 = 0;
            while (n39 <= n) {
                final int n50 = Math.max(0, n40) + n38;
                final int[] array11 = array7[n39 + n];
                array11[0] = array2[n50];
                array11[1] = array3[n50];
                array11[2] = array4[n50];
                final int n51 = n9 - Math.abs(n39);
                final int n52 = n41 + array2[n50] * n51;
                n42 += array3[n50] * n51;
                n43 += array4[n50] * n51;
                if (n39 > 0) {
                    n44 += array11[0];
                    n45 += array11[1];
                    n46 += array11[2];
                }
                else {
                    n47 += array11[0];
                    n48 += array11[1];
                    n49 += array11[2];
                }
                int n53 = n40;
                if (n39 < n37) {
                    n53 = n40 + width;
                }
                ++n39;
                n40 = n53;
                n41 = n52;
            }
            final int n54 = n38;
            int n55 = n45;
            final int n56 = n46;
            final int n57 = 0;
            int n58 = n;
            int n59 = n44;
            int n60 = n43;
            int n61 = n42;
            int n62 = n56;
            int n63 = n41;
            int n64 = n54;
            for (int n65 = n57; n65 < n35; ++n65) {
                array[n64] = ((0xFF000000 & array[n64]) | array6[n63] << 16 | array6[n61] << 8 | array6[n60]);
                final int[] array12 = array7[(n58 - n + n5) % n5];
                final int n66 = array12[0];
                final int n67 = array12[1];
                final int n68 = array12[2];
                if (n38 == 0) {
                    array5[n65] = Math.min(n65 + n9, n37) * width;
                }
                final int n69 = array5[n65] + n38;
                array12[0] = array2[n69];
                array12[1] = array3[n69];
                array12[2] = array4[n69];
                final int n70 = n59 + array12[0];
                final int n71 = n55 + array12[1];
                final int n72 = n62 + array12[2];
                n63 = n63 - n47 + n70;
                n61 = n61 - n48 + n71;
                n60 = n60 - n49 + n72;
                n58 = (n58 + 1) % n5;
                final int[] array13 = array7[n58];
                n47 = n47 - n66 + array13[0];
                n48 = n48 - n67 + array13[1];
                n49 = n49 - n68 + array13[2];
                n59 = n70 - array13[0];
                n55 = n71 - array13[1];
                n62 = n72 - array13[2];
                n64 += width;
            }
        }
        copy.setPixels(array, 0, width, 0, 0, width, n35);
        return copy;
    }
    
    public static InputStream createJPEG(final Bitmap bitmap, final int n) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, n, (OutputStream)byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    
    public static InputStream createPNG(final Bitmap bitmap) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    
    public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final int n, final int n2, final int n3) {
        final Bitmap bitmap2 = Bitmap.createBitmap(n2, n3, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap2);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader((Shader)new BitmapShader(Bitmap.createScaledBitmap(bitmap, n2, n3, true), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        final RectF rectF = new RectF(0.0f, 0.0f, (float)n2, (float)n3);
        final float n4 = n;
        canvas.drawRoundRect(rectF, n4, n4, paint);
        return bitmap2;
    }
    
    public static Bitmap loadImageFromWeb(final URL url) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        final int responseCode = httpURLConnection.getResponseCode();
        if (responseCode >= 400) {
            final StringBuilder sb = new StringBuilder();
            sb.append("http error code:");
            sb.append(responseCode);
            throw new IOException(sb.toString());
        }
        final InputStream inputStream = httpURLConnection.getInputStream();
        final Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        httpURLConnection.disconnect();
        return decodeStream;
    }
    
    public static Bitmap rotateBitmap(final Bitmap bitmap, final int n) {
        final Matrix matrix = new Matrix();
        matrix.setRotate((float)n, (float)(bitmap.getWidth() / 2), (float)(bitmap.getHeight() / 2));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    
    public static Bitmap scaleBitmapToSize(final Bitmap bitmap, int n) {
        final float n2 = n;
        n = (int)(bitmap.getHeight() * (n2 / bitmap.getWidth()));
        return Bitmap.createScaledBitmap(bitmap, (int)n2, n, true);
    }
}
