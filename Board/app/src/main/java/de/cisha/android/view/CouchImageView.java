// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.ServiceProvider;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.CishaUUID;
import android.widget.ImageView;

public class CouchImageView extends ImageView
{
    private CishaUUID _couchId;
    private boolean _imageRequested;
    private int _oldHeight;
    private int _oldwidth;
    private boolean _sizeDidInitialChange;
    private CouchUpdateCallbackWithTimeout _updateCallback;
    
    public CouchImageView(final Context context) {
        super(context);
        this._sizeDidInitialChange = false;
        this._imageRequested = false;
    }
    
    public CouchImageView(final Context context, final AttributeSet set) {
        super(context, set);
        this._sizeDidInitialChange = false;
        this._imageRequested = false;
    }
    
    private void requestCouchImage() {
        if (this._couchId != null && this._sizeDidInitialChange && !this._imageRequested) {
            this._imageRequested = true;
            AsyncTaskCompatHelper.executeOnExecutorPool((android.os.AsyncTask<CishaUUID, Object, Object>)new CouchImageLoader(), this._couchId);
        }
    }
    
    protected void onSizeChanged(final int oldwidth, final int oldHeight, final int n, final int n2) {
        super.onSizeChanged(oldwidth, oldHeight, n, n2);
        if (!this.isInEditMode() && (oldwidth != this._oldwidth || oldHeight != this._oldHeight)) {
            this._oldwidth = oldwidth;
            this._oldHeight = oldHeight;
            this._sizeDidInitialChange = true;
            this.requestCouchImage();
        }
    }
    
    public void setCouchId(final CishaUUID couchId) {
        if (couchId != null) {
            couchId.equals(this._couchId);
        }
        this._couchId = couchId;
        this._imageRequested = false;
        this.requestCouchImage();
    }
    
    private class CouchImageLoader extends AsyncTask<CishaUUID, Void, Bitmap>
    {
        protected Bitmap doInBackground(final CishaUUID... array) {
            if (CouchImageView.this._updateCallback != null) {
                CouchImageView.this._updateCallback.cancel();
            }
            final CouchUpdateCallbackWithTimeout couchUpdateCallbackWithTimeout = new CouchUpdateCallbackWithTimeout();
            CouchImageView.this._updateCallback = couchUpdateCallbackWithTimeout;
            return ServiceProvider.getInstance().getCouchImageService().getCouchImage(CouchImageView.this._couchId, CouchImageView.this.getWidth(), CouchImageView.this.getHeight(), (ICouchImageService.CouchImageLoadCommandCallback)couchUpdateCallbackWithTimeout);
        }
        
        protected void onPostExecute(final Bitmap imageBitmap) {
            if (imageBitmap != null) {
                CouchImageView.this.setImageBitmap(imageBitmap);
            }
        }
    }
    
    private class CouchUpdateCallbackWithTimeout extends LoadCommandCallbackWithTimeout<Bitmap> implements CouchImageLoadCommandCallback
    {
        @Override
        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            CouchImageView.this._updateCallback = null;
        }
        
        @Override
        public void noUpdateNeeded() {
            if (!this.isCanceled()) {
                CouchImageView.this._updateCallback = null;
            }
        }
        
        @Override
        protected void succeded(final Bitmap imageBitmap) {
            CouchImageView.this.setImageBitmap(imageBitmap);
            CouchImageView.this._updateCallback = null;
        }
    }
}
