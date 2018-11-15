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
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.internal.VideoUploader;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

private static class VideoUploader.FinishUploadWorkItem
extends VideoUploader.UploadWorkItemBase {
    static final Set<Integer> transientErrorCodes = new HashSet<Integer>(){
        {
            this.add(1363011);
        }
    };

    public VideoUploader.FinishUploadWorkItem(VideoUploader.UploadContext uploadContext, int n) {
        super(uploadContext, n);
    }

    @Override
    protected void enqueueRetry(int n) {
        VideoUploader.enqueueUploadFinish(this.uploadContext, n);
    }

    @Override
    public Bundle getParameters() {
        Bundle bundle = new Bundle();
        if (this.uploadContext.params != null) {
            bundle.putAll(this.uploadContext.params);
        }
        bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_FINISH_PHASE);
        bundle.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
        Utility.putNonEmptyString(bundle, VideoUploader.PARAM_TITLE, this.uploadContext.title);
        Utility.putNonEmptyString(bundle, VideoUploader.PARAM_DESCRIPTION, this.uploadContext.description);
        Utility.putNonEmptyString(bundle, VideoUploader.PARAM_REF, this.uploadContext.ref);
        return bundle;
    }

    @Override
    protected Set<Integer> getTransientErrorCodes() {
        return transientErrorCodes;
    }

    @Override
    protected void handleError(FacebookException facebookException) {
        VideoUploader.logError(facebookException, "Video '%s' failed to finish uploading", new Object[]{this.uploadContext.videoId});
        this.endUploadWithFailure(facebookException);
    }

    @Override
    protected void handleSuccess(JSONObject jSONObject) throws JSONException {
        if (jSONObject.getBoolean("success")) {
            this.issueResponseOnMainThread(null, this.uploadContext.videoId);
            return;
        }
        this.handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
    }

}
