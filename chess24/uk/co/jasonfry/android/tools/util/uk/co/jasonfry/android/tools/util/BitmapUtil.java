/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Matrix
 *  android.media.ExifInterface
 *  android.net.Uri
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Images$Thumbnails
 */
package uk.co.jasonfry.android.tools.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class BitmapUtil {
    public static void createScaledImage(String string, String object, int n, int n2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int n3 = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string, (BitmapFactory.Options)options);
        int n4 = options.outWidth;
        int n5 = options.outHeight;
        int n6 = n3;
        int n7 = n4;
        int n8 = n5;
        n2 = n;
        if (n > n4) {
            n2 = n4;
            n8 = n5;
            n7 = n4;
            n6 = n3;
        }
        while ((n = n7 / 2) > n2) {
            n8 /= 2;
            n6 *= 2;
            n7 = n;
        }
        float f = (float)n2 / (float)n7;
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = n6;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        string = BitmapFactory.decodeFile((String)string, (BitmapFactory.Options)options);
        options = new Matrix();
        options.postScale(f, f);
        string = Bitmap.createBitmap((Bitmap)string, (int)0, (int)0, (int)string.getWidth(), (int)string.getHeight(), (Matrix)options, (boolean)true);
        try {
            object = new FileOutputStream((String)object);
            string.compress(Bitmap.CompressFormat.JPEG, 85, (OutputStream)object);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    public static Bitmap cropToSquare(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getWidth() > bitmap.getHeight()) {
                return Bitmap.createBitmap((Bitmap)bitmap, (int)((bitmap.getWidth() - bitmap.getHeight()) / 2), (int)0, (int)bitmap.getHeight(), (int)bitmap.getHeight());
            }
            if (bitmap.getWidth() < bitmap.getHeight()) {
                return Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)((bitmap.getHeight() - bitmap.getWidth()) / 2), (int)bitmap.getWidth(), (int)bitmap.getWidth());
            }
        }
        return bitmap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Bitmap decodeFile(File file, int n, boolean bl) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream((InputStream)new FileInputStream(file), null, (BitmapFactory.Options)options);
        if (n <= 0) return null;
        int n2 = options.outWidth;
        int n3 = options.outHeight;
        int n4 = 1;
        do {
            if (n2 / 2 < n || n3 / 2 < n) break;
            n2 /= 2;
            n3 /= 2;
            ++n4;
            continue;
            break;
        } while (true);
        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = n4;
            options.inScaled = true;
            if (!bl) return BitmapFactory.decodeFile((String)file.getAbsolutePath(), (BitmapFactory.Options)options);
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
        return BitmapUtil.cropToSquare(BitmapFactory.decodeFile((String)file.getAbsolutePath(), (BitmapFactory.Options)options));
    }

    public static Bitmap decodeFile(String string, int n, boolean bl) {
        return BitmapUtil.decodeFile(new File(string), n, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bitmap getThumbnail(ContentResolver contentResolver, long l) {
        int n;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        int n2 = 0;
        if ((uri = contentResolver.query(uri, new String[]{"_data"}, "_id=?", new String[]{String.valueOf(l)}, null)) == null) return null;
        if (uri.getCount() <= 0) return null;
        uri.moveToFirst();
        String string = uri.getString(0);
        uri.close();
        try {
            int n3 = new ExifInterface(string).getAttributeInt("Orientation", 0);
            n = n2;
            if (n3 != 0) {
                n = n3 != 3 ? (n3 != 6 ? (n3 != 8 ? n2 : 270) : 90) : 180;
            }
        }
        catch (IOException iOException) {
            n = n2;
        }
        uri = MediaStore.Images.Thumbnails.getThumbnail((ContentResolver)contentResolver, (long)l, (int)1, null);
        contentResolver = uri;
        if (n == 0) return contentResolver;
        contentResolver = new Matrix();
        contentResolver.setRotate((float)n);
        return Bitmap.createBitmap((Bitmap)uri, (int)0, (int)0, (int)uri.getWidth(), (int)uri.getHeight(), (Matrix)contentResolver, (boolean)true);
    }
}
