/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.share.model;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Collections;
import java.util.List;

public static abstract class ShareContent.Builder<P extends ShareContent, E extends ShareContent.Builder>
implements ShareModelBuilder<P, E> {
    private Uri contentUrl;
    private ShareHashtag hashtag;
    private String pageId;
    private List<String> peopleIds;
    private String placeId;
    private String ref;

    static /* synthetic */ Uri access$000(ShareContent.Builder builder) {
        return builder.contentUrl;
    }

    static /* synthetic */ List access$100(ShareContent.Builder builder) {
        return builder.peopleIds;
    }

    static /* synthetic */ String access$200(ShareContent.Builder builder) {
        return builder.placeId;
    }

    static /* synthetic */ String access$300(ShareContent.Builder builder) {
        return builder.pageId;
    }

    static /* synthetic */ String access$400(ShareContent.Builder builder) {
        return builder.ref;
    }

    static /* synthetic */ ShareHashtag access$500(ShareContent.Builder builder) {
        return builder.hashtag;
    }

    @Override
    public E readFrom(P p) {
        if (p == null) {
            return (E)this;
        }
        return this.setContentUrl(p.getContentUrl()).setPeopleIds(p.getPeopleIds()).setPlaceId(p.getPlaceId()).setPageId(p.getPageId()).setRef(p.getRef());
    }

    public E setContentUrl(@Nullable Uri uri) {
        this.contentUrl = uri;
        return (E)this;
    }

    public E setPageId(@Nullable String string) {
        this.pageId = string;
        return (E)this;
    }

    public E setPeopleIds(@Nullable List<String> list) {
        list = list == null ? null : Collections.unmodifiableList(list);
        this.peopleIds = list;
        return (E)this;
    }

    public E setPlaceId(@Nullable String string) {
        this.placeId = string;
        return (E)this;
    }

    public E setRef(@Nullable String string) {
        this.ref = string;
        return (E)this;
    }

    public E setShareHashtag(@Nullable ShareHashtag shareHashtag) {
        this.hashtag = shareHashtag;
        return (E)this;
    }
}
