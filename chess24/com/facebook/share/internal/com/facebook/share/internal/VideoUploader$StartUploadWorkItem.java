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
import com.facebook.share.internal.VideoUploader;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

private static class VideoUploader.StartUploadWorkItem
extends VideoUploader.UploadWorkItemBase {
    static final Set<Integer> transientErrorCodes = new HashSet<Integer>(){
        {
            this.add(6000);
        }
    };

    public VideoUploader.StartUploadWorkItem(VideoUploader.UploadContext uploadContext, int n) {
        super(uploadContext, n);
    }

    @Override
    protected void enqueueRetry(int n) {
        VideoUploader.enqueueUploadStart(this.uploadContext, n);
    }

    @Override
    public Bundle getParameters() {
        Bundle bundle = new Bundle();
        bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_START_PHASE);
        bundle.putLong(VideoUploader.PARAM_FILE_SIZE, this.uploadContext.videoSize);
        return bundle;
    }

    @Override
    protected Set<Integer> getTransientErrorCodes() {
        return transientErrorCodes;
    }

    @Override
    protected void handleError(FacebookException facebookException) {
        VideoUploader.logError(facebookException, "Error starting video upload", new Object[0]);
        this.endUploadWithFailure(facebookException);
    }

    @Override
    protected void handleSuccess(JSONObject object) throws JSONException {
        this.uploadContext.sessionId = object.getString(VideoUploader.PARAM_SESSION_ID);
        this.uploadContext.videoId = object.getString(VideoUploader.PARAM_VIDEO_ID);
        String string = object.getString(VideoUploader.PARAM_START_OFFSET);
        object = object.getString(VideoUploader.PARAM_END_OFFSET);
        VideoUploader.enqueueUploadChunk(this.uploadContext, string, (String)object, 0);
    }

}
