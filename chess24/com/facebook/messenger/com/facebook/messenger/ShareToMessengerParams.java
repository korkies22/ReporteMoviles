/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.messenger;

import android.net.Uri;
import com.facebook.messenger.ShareToMessengerParamsBuilder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ShareToMessengerParams {
    public static final Set<String> VALID_EXTERNAL_URI_SCHEMES;
    public static final Set<String> VALID_MIME_TYPES;
    public static final Set<String> VALID_URI_SCHEMES;
    public final Uri externalUri;
    public final String metaData;
    public final String mimeType;
    public final Uri uri;

    static {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("image/*");
        hashSet.add("image/jpeg");
        hashSet.add("image/png");
        hashSet.add("image/gif");
        hashSet.add("image/webp");
        hashSet.add("video/*");
        hashSet.add("video/mp4");
        hashSet.add("audio/*");
        hashSet.add("audio/mpeg");
        VALID_MIME_TYPES = Collections.unmodifiableSet(hashSet);
        hashSet = new HashSet();
        hashSet.add("content");
        hashSet.add("android.resource");
        hashSet.add("file");
        VALID_URI_SCHEMES = Collections.unmodifiableSet(hashSet);
        hashSet = new HashSet();
        hashSet.add("http");
        hashSet.add("https");
        VALID_EXTERNAL_URI_SCHEMES = Collections.unmodifiableSet(hashSet);
    }

    ShareToMessengerParams(ShareToMessengerParamsBuilder object) {
        this.uri = object.getUri();
        this.mimeType = object.getMimeType();
        this.metaData = object.getMetaData();
        this.externalUri = object.getExternalUri();
        if (this.uri == null) {
            throw new NullPointerException("Must provide non-null uri");
        }
        if (this.mimeType == null) {
            throw new NullPointerException("Must provide mimeType");
        }
        if (!VALID_URI_SCHEMES.contains(this.uri.getScheme())) {
            object = new StringBuilder();
            object.append("Unsupported URI scheme: ");
            object.append(this.uri.getScheme());
            throw new IllegalArgumentException(object.toString());
        }
        if (!VALID_MIME_TYPES.contains(this.mimeType)) {
            object = new StringBuilder();
            object.append("Unsupported mime-type: ");
            object.append(this.mimeType);
            throw new IllegalArgumentException(object.toString());
        }
        if (this.externalUri != null && !VALID_EXTERNAL_URI_SCHEMES.contains(this.externalUri.getScheme())) {
            object = new StringBuilder();
            object.append("Unsupported external uri scheme: ");
            object.append(this.externalUri.getScheme());
            throw new IllegalArgumentException(object.toString());
        }
    }

    public static ShareToMessengerParamsBuilder newBuilder(Uri uri, String string) {
        return new ShareToMessengerParamsBuilder(uri, string);
    }
}
