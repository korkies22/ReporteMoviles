/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.internal.VideoUploader;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

private static abstract class VideoUploader.UploadWorkItemBase
implements Runnable {
    protected int completedRetries;
    protected VideoUploader.UploadContext uploadContext;

    protected VideoUploader.UploadWorkItemBase(VideoUploader.UploadContext uploadContext, int n) {
        this.uploadContext = uploadContext;
        this.completedRetries = n;
    }

    private boolean attemptRetry(int n) {
        if (this.completedRetries < 2 && this.getTransientErrorCodes().contains(n)) {
            n = (int)Math.pow(3.0, this.completedRetries);
            VideoUploader.getHandler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    UploadWorkItemBase.this.enqueueRetry(UploadWorkItemBase.this.completedRetries + 1);
                }
            }, (long)(5000 * n));
            return true;
        }
        return false;
    }

    protected void endUploadWithFailure(FacebookException facebookException) {
        this.issueResponseOnMainThread(facebookException, null);
    }

    protected abstract void enqueueRetry(int var1);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void executeGraphRequestSynchronously(Bundle object) {
        object = new GraphRequest(this.uploadContext.accessToken, String.format(Locale.ROOT, "%s/videos", this.uploadContext.graphNode), (Bundle)object, HttpMethod.POST, null).executeAndWait();
        if (object != null) {
            FacebookRequestError facebookRequestError = object.getError();
            JSONObject jSONObject = object.getJSONObject();
            if (facebookRequestError != null) {
                if (this.attemptRetry(facebookRequestError.getSubErrorCode())) return;
                this.handleError(new FacebookGraphResponseException((GraphResponse)object, "Video upload failed"));
                return;
            }
            if (jSONObject != null) {
                try {
                    this.handleSuccess(jSONObject);
                    return;
                }
                catch (JSONException jSONException) {
                    this.endUploadWithFailure(new FacebookException("Unexpected error in server response", (Throwable)jSONException));
                    return;
                }
            }
            this.handleError(new FacebookException("Unexpected error in server response"));
            return;
        }
        this.handleError(new FacebookException("Unexpected error in server response"));
    }

    protected abstract Bundle getParameters() throws Exception;

    protected abstract Set<Integer> getTransientErrorCodes();

    protected abstract void handleError(FacebookException var1);

    protected abstract void handleSuccess(JSONObject var1) throws JSONException;

    protected void issueResponseOnMainThread(final FacebookException facebookException, final String string) {
        VideoUploader.getHandler().post(new Runnable(){

            @Override
            public void run() {
                VideoUploader.issueResponse(UploadWorkItemBase.this.uploadContext, facebookException, string);
            }
        });
    }

    @Override
    public void run() {
        if (!this.uploadContext.isCanceled) {
            try {
                this.executeGraphRequestSynchronously(this.getParameters());
                return;
            }
            catch (Exception exception) {
                this.endUploadWithFailure(new FacebookException("Video upload failed", exception));
                return;
            }
            catch (FacebookException facebookException) {
                this.endUploadWithFailure(facebookException);
                return;
            }
        }
        this.endUploadWithFailure(null);
    }

}
