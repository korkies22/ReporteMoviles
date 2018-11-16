// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import de.cisha.android.board.service.WebImageService;
import android.os.AsyncTask;
import java.net.URL;
import de.cisha.chess.util.Logger;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;

public class WebImageView extends ImageView
{
    private WebImageLoader _imageLoadAsyncTask;
    private String _imageSource;
    private ImageViewState _state;
    
    public WebImageView(final Context context) {
        super(context);
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_SHOWING_INITIAL_IMAGE;
    }
    
    public WebImageView(final Context context, final AttributeSet set) {
        super(context, set);
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_SHOWING_INITIAL_IMAGE;
    }
    
    public WebImageView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_SHOWING_INITIAL_IMAGE;
    }
    
    private void setWebImage(final Bitmap imageBitmap) {
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_SHOWING_WEB_IMAGE;
        this.setImageBitmap(imageBitmap);
        this._imageLoadAsyncTask = null;
    }
    
    private void webImageLoadingFailed() {
        this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_LOADING_FAILED;
        this._imageLoadAsyncTask = null;
        this.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), 2131231786));
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("Failed to load Image from:");
        sb.append(this._imageSource);
        instance.debug("WebImageView", sb.toString());
    }
    
    public ImageViewState getState() {
        return this._state;
    }
    
    public String getURL() {
        return this._imageSource;
    }
    
    public void setImageWebUrl(final URL url) {
        String externalForm;
        if (url != null) {
            externalForm = url.toExternalForm();
        }
        else {
            externalForm = "";
        }
        this.setImageWebUrlString(externalForm);
    }
    
    public void setImageWebUrlString(final String webImageFrom) {
        this.setWebImageFrom(webImageFrom);
    }
    
    public void setWebImageFrom(final String imageSource) {
        if (imageSource != null && !"".equals(imageSource)) {
            this._imageSource = imageSource;
            this._state = ImageViewState.WEB_IMAGE_VIEW_STATE_LOADING_WEB_IMAGE;
            if (this._imageLoadAsyncTask != null) {
                this._imageLoadAsyncTask.cancel(true);
            }
            (this._imageLoadAsyncTask = new WebImageLoader()).execute((Object[])new String[] { imageSource });
            return;
        }
        this.webImageLoadingFailed();
    }
    
    public enum ImageViewState
    {
        WEB_IMAGE_VIEW_STATE_LOADING_FAILED, 
        WEB_IMAGE_VIEW_STATE_LOADING_WEB_IMAGE, 
        WEB_IMAGE_VIEW_STATE_SHOWING_INITIAL_IMAGE, 
        WEB_IMAGE_VIEW_STATE_SHOWING_WEB_IMAGE;
    }
    
    protected class WebImageLoader extends AsyncTask<String, Void, Bitmap>
    {
        protected Bitmap doInBackground(final String... array) {
            try {
                return WebImageService.getInstance(WebImageView.this.getContext()).getWebImage(array[0]);
            }
            catch (WebImageService.NoImageFoundAtURLException ex) {
                Logger.getInstance().debug(WebImageLoader.class.getName(), WebImageService.NoImageFoundAtURLException.class.getName(), ex);
                return null;
            }
        }
        
        protected void onPostExecute(final Bitmap bitmap) {
            if (!this.isCancelled()) {
                if (bitmap != null) {
                    WebImageView.this.setWebImage(bitmap);
                }
                else {
                    WebImageView.this.webImageLoadingFailed();
                }
            }
            super.onPostExecute((Object)bitmap);
        }
    }
}
