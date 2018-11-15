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
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

private static class VideoUploader.TransferChunkWorkItem
extends VideoUploader.UploadWorkItemBase {
    static final Set<Integer> transientErrorCodes = new HashSet<Integer>(){
        {
            this.add(1363019);
            this.add(1363021);
            this.add(1363030);
            this.add(1363033);
            this.add(1363041);
        }
    };
    private String chunkEnd;
    private String chunkStart;

    public VideoUploader.TransferChunkWorkItem(VideoUploader.UploadContext uploadContext, String string, String string2, int n) {
        super(uploadContext, n);
        this.chunkStart = string;
        this.chunkEnd = string2;
    }

    @Override
    protected void enqueueRetry(int n) {
        VideoUploader.enqueueUploadChunk(this.uploadContext, this.chunkStart, this.chunkEnd, n);
    }

    @Override
    public Bundle getParameters() throws IOException {
        Bundle bundle = new Bundle();
        bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_TRANSFER_PHASE);
        bundle.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
        bundle.putString(VideoUploader.PARAM_START_OFFSET, this.chunkStart);
        byte[] arrby = VideoUploader.getChunk(this.uploadContext, this.chunkStart, this.chunkEnd);
        if (arrby != null) {
            bundle.putByteArray(VideoUploader.PARAM_VIDEO_FILE_CHUNK, arrby);
            return bundle;
        }
        throw new FacebookException("Error reading video");
    }

    @Override
    protected Set<Integer> getTransientErrorCodes() {
        return transientErrorCodes;
    }

    @Override
    protected void handleError(FacebookException facebookException) {
        VideoUploader.logError(facebookException, "Error uploading video '%s'", new Object[]{this.uploadContext.videoId});
        this.endUploadWithFailure(facebookException);
    }

    @Override
    protected void handleSuccess(JSONObject object) throws JSONException {
        String string = object.getString(VideoUploader.PARAM_START_OFFSET);
        if (Utility.areObjectsEqual(string, object = object.getString(VideoUploader.PARAM_END_OFFSET))) {
            VideoUploader.enqueueUploadFinish(this.uploadContext, 0);
            return;
        }
        VideoUploader.enqueueUploadChunk(this.uploadContext, string, (String)object, 0);
    }

}
