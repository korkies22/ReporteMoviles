/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.service.InternalDirWebImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONObject;

public abstract class WebImageService {
    public static WebImageService getInstance(Context object) {
        synchronized (WebImageService.class) {
            object = InternalDirWebImageService.getInstance(object);
            return object;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Bitmap loadImageFromWeb(String string) throws IOException {
        Object object2;
        block4 : {
            Object object = object2 = BitmapHelper.loadImageFromWeb(new URL(string));
            if (object2 == null) return object;
            try {
                this.addToImageCache(string, (Bitmap)object2);
                return object2;
            }
            catch (MalformedURLException malformedURLException) {
                string = object2;
                object2 = malformedURLException;
            }
            break block4;
            catch (MalformedURLException malformedURLException) {
                string = null;
            }
        }
        Logger.getInstance().debug(WebImageService.class.getName(), MalformedURLException.class.getName(), (Throwable)object2);
        return string;
    }

    protected abstract void addToImageCache(String var1, Bitmap var2);

    protected abstract Bitmap getImageFromCache(String var1);

    public Bitmap getWebImage(String string) throws NoImageFoundAtURLException {
        Object object;
        block5 : {
            if (this.isImageCashed(string)) {
                return this.getImageFromCache(string);
            }
            try {
                object = this.loadImageFromWeb(string);
                if (object == null) break block5;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No image Found at URL: ");
                stringBuilder.append(string);
                throw new NoImageFoundAtURLException(stringBuilder.toString(), iOException);
            }
            this.addToImageCache(string, (Bitmap)object);
            return object;
        }
        object = new StringBuilder();
        object.append("No image Found at URL: ");
        object.append(string);
        throw new NoImageFoundAtURLException(object.toString());
    }

    public Bitmap getWebImageCached(String string, LoadCommandCallback<Bitmap> loadCommandCallback) {
        if (this.isImageCashed(string)) {
            string = this.getImageFromCache(string);
            loadCommandCallback.loadingCancelled();
            return string;
        }
        new WebImageAsyncLoader(loadCommandCallback).execute((Object[])new String[]{string});
        return null;
    }

    protected abstract boolean isImageCashed(String var1);

    public static class NoImageFoundAtURLException
    extends Exception {
        private static final long serialVersionUID = 2181692219867494897L;

        public NoImageFoundAtURLException(String string) {
            super(string);
        }

        public NoImageFoundAtURLException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    protected class WebImageAsyncLoader
    extends AsyncTask<String, Void, Bitmap> {
        private LoadCommandCallback<Bitmap> _callback;

        public WebImageAsyncLoader(LoadCommandCallback<Bitmap> loadCommandCallback) {
            this._callback = loadCommandCallback;
        }

        protected /* varargs */ Bitmap doInBackground(String ... bitmap) {
            try {
                bitmap = WebImageService.this.getWebImage(bitmap[0]);
                return bitmap;
            }
            catch (NoImageFoundAtURLException noImageFoundAtURLException) {
                Logger.getInstance().debug(this.getClass().getName(), NoImageFoundAtURLException.class.getName(), noImageFoundAtURLException);
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (this.isCancelled()) {
                if (bitmap != null) {
                    this._callback.loadingSucceded(bitmap);
                    return;
                }
                this._callback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "image didn't contain image", null, null);
                return;
            }
            this._callback.loadingCancelled();
        }
    }

}
