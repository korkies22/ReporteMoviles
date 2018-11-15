/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.os.AsyncTask
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package de.cisha.android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import de.cisha.android.board.service.WebImageService;
import de.cisha.chess.util.Logger;
import java.net.URL;

public class WebImageView
extends ImageView {
    private WebImageLoader _imageLoadAsyncTask;
    private String _imageSource;
    private ImageViewState _state = ImageViewState.WEB_IMAGE_VIEW_STATE_SHOWING_INITIAL_IMAGE;

    public WebImageView(Context context) {
        super(context);
    }

    public WebImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WebImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private void setWebImage(Bitmap bitmap) {
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_SHOWING_WEB_IMAGE;
        this.setImageBitmap(bitmap);
        this._imageLoadAsyncTask = null;
    }

    private void webImageLoadingFailed() {
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_LOADING_FAILED;
        this._imageLoadAsyncTask = null;
        this.setImageBitmap(BitmapFactory.decodeResource((Resources)this.getResources(), (int)2131231786));
        Logger logger = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to load Image from:");
        stringBuilder.append(this._imageSource);
        logger.debug("WebImageView", stringBuilder.toString());
    }

    public ImageViewState getState() {
        return this._state;
    }

    public String getURL() {
        return this._imageSource;
    }

    public void setImageWebUrl(URL object) {
        object = object != null ? object.toExternalForm() : "";
        this.setImageWebUrlString((String)object);
    }

    public void setImageWebUrlString(String string) {
        this.setWebImageFrom(string);
    }

    public void setWebImageFrom(String string) {
        if (string != null && !"".equals(string)) {
            this._imageSource = string;
            this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_LOADING_WEB_IMAGE;
            if (this._imageLoadAsyncTask != null) {
                this._imageLoadAsyncTask.cancel(true);
            }
            this._imageLoadAsyncTask = new WebImageLoader();
            this._imageLoadAsyncTask.execute((Object[])new String[]{string});
            return;
        }
        this.webImageLoadingFailed();
    }

    public static enum ImageViewState {
        WEB_IMAGE_VIEW_STATE_SHOWING_INITIAL_IMAGE,
        WEB_IMAGE_VIEW_STATE_LOADING_WEB_IMAGE,
        WEB_IMAGE_VIEW_STATE_LOADING_FAILED,
        WEB_IMAGE_VIEW_STATE_SHOWING_WEB_IMAGE;
        

        private ImageViewState() {
        }
    }

    protected class WebImageLoader
    extends AsyncTask<String, Void, Bitmap> {
        protected /* varargs */ Bitmap doInBackground(String ... bitmap) {
            try {
                bitmap = WebImageService.getInstance(WebImageView.this.getContext()).getWebImage(bitmap[0]);
                return bitmap;
            }
            catch (WebImageService.NoImageFoundAtURLException noImageFoundAtURLException) {
                Logger.getInstance().debug(WebImageLoader.class.getName(), WebImageService.NoImageFoundAtURLException.class.getName(), noImageFoundAtURLException);
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (!this.isCancelled()) {
                if (bitmap != null) {
                    WebImageView.this.setWebImage(bitmap);
                } else {
                    WebImageView.this.webImageLoadingFailed();
                }
            }
            super.onPostExecute((Object)bitmap);
        }
    }

}
