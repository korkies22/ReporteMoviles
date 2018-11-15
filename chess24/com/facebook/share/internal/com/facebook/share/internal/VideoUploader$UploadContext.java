/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.text.TextUtils
 */
package com.facebook.share.internal;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import com.facebook.share.Sharer;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

private static class VideoUploader.UploadContext {
    public final AccessToken accessToken = AccessToken.getCurrentAccessToken();
    public final FacebookCallback<Sharer.Result> callback;
    public String chunkStart = "0";
    public final String description;
    public final String graphNode;
    public boolean isCanceled;
    public Bundle params;
    public final String ref;
    public String sessionId;
    public final String title;
    public String videoId;
    public long videoSize;
    public InputStream videoStream;
    public final Uri videoUri;
    public WorkQueue.WorkItem workItem;

    private VideoUploader.UploadContext(ShareVideoContent shareVideoContent, String string, FacebookCallback<Sharer.Result> facebookCallback) {
        this.videoUri = shareVideoContent.getVideo().getLocalUrl();
        this.title = shareVideoContent.getContentTitle();
        this.description = shareVideoContent.getContentDescription();
        this.ref = shareVideoContent.getRef();
        this.graphNode = string;
        this.callback = facebookCallback;
        this.params = shareVideoContent.getVideo().getParameters();
        if (!Utility.isNullOrEmpty(shareVideoContent.getPeopleIds())) {
            this.params.putString("tags", TextUtils.join((CharSequence)", ", shareVideoContent.getPeopleIds()));
        }
        if (!Utility.isNullOrEmpty(shareVideoContent.getPlaceId())) {
            this.params.putString("place", shareVideoContent.getPlaceId());
        }
        if (!Utility.isNullOrEmpty(shareVideoContent.getRef())) {
            this.params.putString(VideoUploader.PARAM_REF, shareVideoContent.getRef());
        }
    }

    static /* synthetic */ void access$100(VideoUploader.UploadContext uploadContext) throws FileNotFoundException {
        uploadContext.initialize();
    }

    private void initialize() throws FileNotFoundException {
        try {
            if (Utility.isFileUri(this.videoUri)) {
                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open((File)new File(this.videoUri.getPath()), (int)268435456);
                this.videoSize = parcelFileDescriptor.getStatSize();
                this.videoStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                return;
            }
            if (Utility.isContentUri(this.videoUri)) {
                this.videoSize = Utility.getContentSize(this.videoUri);
                this.videoStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(this.videoUri);
                return;
            }
            throw new FacebookException("Uri must be a content:// or file:// uri");
        }
        catch (FileNotFoundException fileNotFoundException) {
            Utility.closeQuietly(this.videoStream);
            throw fileNotFoundException;
        }
    }
}
