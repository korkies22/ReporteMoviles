/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  android.util.AttributeSet
 *  android.widget.ImageView
 *  org.json.JSONObject
 */
package de.cisha.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import org.json.JSONObject;

public class CouchImageView
extends ImageView {
    private CishaUUID _couchId;
    private boolean _imageRequested = false;
    private int _oldHeight;
    private int _oldwidth;
    private boolean _sizeDidInitialChange = false;
    private CouchUpdateCallbackWithTimeout _updateCallback;

    public CouchImageView(Context context) {
        super(context);
    }

    public CouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void requestCouchImage() {
        if (this._couchId != null && this._sizeDidInitialChange && !this._imageRequested) {
            this._imageRequested = true;
            AsyncTaskCompatHelper.executeOnExecutorPool(new CouchImageLoader(), this._couchId);
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (!(this.isInEditMode() || n == this._oldwidth && n2 == this._oldHeight)) {
            this._oldwidth = n;
            this._oldHeight = n2;
            this._sizeDidInitialChange = true;
            this.requestCouchImage();
        }
    }

    public void setCouchId(CishaUUID cishaUUID) {
        if (cishaUUID != null) {
            cishaUUID.equals(this._couchId);
        }
        this._couchId = cishaUUID;
        this._imageRequested = false;
        this.requestCouchImage();
    }

    private class CouchImageLoader
    extends AsyncTask<CishaUUID, Void, Bitmap> {
        private CouchImageLoader() {
        }

        protected /* varargs */ Bitmap doInBackground(CishaUUID ... object) {
            if (CouchImageView.this._updateCallback != null) {
                CouchImageView.this._updateCallback.cancel();
            }
            object = new CouchUpdateCallbackWithTimeout();
            CouchImageView.this._updateCallback = (CouchUpdateCallbackWithTimeout)object;
            return ServiceProvider.getInstance().getCouchImageService().getCouchImage(CouchImageView.this._couchId, CouchImageView.this.getWidth(), CouchImageView.this.getHeight(), (ICouchImageService.CouchImageLoadCommandCallback)object);
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                CouchImageView.this.setImageBitmap(bitmap);
            }
        }
    }

    private class CouchUpdateCallbackWithTimeout
    extends LoadCommandCallbackWithTimeout<Bitmap>
    implements ICouchImageService.CouchImageLoadCommandCallback {
        private CouchUpdateCallbackWithTimeout() {
        }

        @Override
        protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            CouchImageView.this._updateCallback = null;
        }

        @Override
        public void noUpdateNeeded() {
            if (!this.isCanceled()) {
                CouchImageView.this._updateCallback = null;
            }
        }

        @Override
        protected void succeded(Bitmap bitmap) {
            CouchImageView.this.setImageBitmap(bitmap);
            CouchImageView.this._updateCallback = null;
        }
    }

}
