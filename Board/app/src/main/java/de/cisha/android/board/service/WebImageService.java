// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.io.IOException;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import de.cisha.chess.util.BitmapHelper;
import java.net.URL;
import android.graphics.Bitmap;
import android.content.Context;

public abstract class WebImageService
{
    public static WebImageService getInstance(final Context context) {
        synchronized (WebImageService.class) {
            return InternalDirWebImageService.getInstance(context);
        }
    }
    
    private Bitmap loadImageFromWeb(final String s) throws IOException {
        Bitmap bitmap2;
        MalformedURLException ex = null;
        try {
            final Bitmap loadImageFromWeb;
            final Bitmap bitmap = loadImageFromWeb = BitmapHelper.loadImageFromWeb(new URL(s));
            if (bitmap == null) {
                return loadImageFromWeb;
            }
            try {
                this.addToImageCache(s, bitmap);
                return bitmap;
            }
            catch (MalformedURLException ex2) {
                bitmap2 = bitmap;
                ex = ex2;
            }
        }
        catch (MalformedURLException ex) {
            bitmap2 = null;
        }
        Logger.getInstance().debug(WebImageService.class.getName(), MalformedURLException.class.getName(), ex);
        return bitmap2;
    }
    
    protected abstract void addToImageCache(final String p0, final Bitmap p1);
    
    protected abstract Bitmap getImageFromCache(final String p0);
    
    public Bitmap getWebImage(final String s) throws NoImageFoundAtURLException {
        if (this.isImageCashed(s)) {
            return this.getImageFromCache(s);
        }
        try {
            final Bitmap loadImageFromWeb = this.loadImageFromWeb(s);
            if (loadImageFromWeb != null) {
                this.addToImageCache(s, loadImageFromWeb);
                return loadImageFromWeb;
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("No image Found at URL: ");
            sb.append(s);
            throw new NoImageFoundAtURLException(sb.toString());
        }
        catch (IOException ex) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("No image Found at URL: ");
            sb2.append(s);
            throw new NoImageFoundAtURLException(sb2.toString(), ex);
        }
    }
    
    public Bitmap getWebImageCached(final String s, final LoadCommandCallback<Bitmap> loadCommandCallback) {
        if (this.isImageCashed(s)) {
            final Bitmap imageFromCache = this.getImageFromCache(s);
            loadCommandCallback.loadingCancelled();
            return imageFromCache;
        }
        new WebImageAsyncLoader(loadCommandCallback).execute((Object[])new String[] { s });
        return null;
    }
    
    protected abstract boolean isImageCashed(final String p0);
    
    public static class NoImageFoundAtURLException extends Exception
    {
        private static final long serialVersionUID = 2181692219867494897L;
        
        public NoImageFoundAtURLException(final String s) {
            super(s);
        }
        
        public NoImageFoundAtURLException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    protected class WebImageAsyncLoader extends AsyncTask<String, Void, Bitmap>
    {
        private LoadCommandCallback<Bitmap> _callback;
        
        public WebImageAsyncLoader(final LoadCommandCallback<Bitmap> callback) {
            this._callback = callback;
        }
        
        protected Bitmap doInBackground(final String... array) {
            try {
                return WebImageService.this.getWebImage(array[0]);
            }
            catch (NoImageFoundAtURLException ex) {
                Logger.getInstance().debug(this.getClass().getName(), NoImageFoundAtURLException.class.getName(), ex);
                return null;
            }
        }
        
        protected void onPostExecute(final Bitmap bitmap) {
            if (!this.isCancelled()) {
                this._callback.loadingCancelled();
                return;
            }
            if (bitmap != null) {
                this._callback.loadingSucceded(bitmap);
                return;
            }
            this._callback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "image didn't contain image", null, null);
        }
    }
}
