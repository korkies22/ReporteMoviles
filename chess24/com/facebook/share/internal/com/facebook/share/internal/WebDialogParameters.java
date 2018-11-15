/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  org.json.JSONException
 */
package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;

public class WebDialogParameters {
    public static Bundle create(AppGroupCreationContent object) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "name", object.getName());
        Utility.putNonEmptyString(bundle, "description", object.getDescription());
        object = object.getAppGroupPrivacy();
        if (object != null) {
            Utility.putNonEmptyString(bundle, "privacy", object.toString().toLowerCase(Locale.ENGLISH));
        }
        return bundle;
    }

    public static Bundle create(GameRequestContent gameRequestContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "message", gameRequestContent.getMessage());
        Utility.putCommaSeparatedStringList(bundle, "to", gameRequestContent.getRecipients());
        Utility.putNonEmptyString(bundle, "title", gameRequestContent.getTitle());
        Utility.putNonEmptyString(bundle, "data", gameRequestContent.getData());
        if (gameRequestContent.getActionType() != null) {
            Utility.putNonEmptyString(bundle, "action_type", gameRequestContent.getActionType().toString().toLowerCase(Locale.ENGLISH));
        }
        Utility.putNonEmptyString(bundle, "object_id", gameRequestContent.getObjectId());
        if (gameRequestContent.getFilters() != null) {
            Utility.putNonEmptyString(bundle, "filters", gameRequestContent.getFilters().toString().toLowerCase(Locale.ENGLISH));
        }
        Utility.putCommaSeparatedStringList(bundle, "suggestions", gameRequestContent.getSuggestions());
        return bundle;
    }

    public static Bundle create(ShareLinkContent shareLinkContent) {
        Bundle bundle = WebDialogParameters.createBaseParameters(shareLinkContent);
        Utility.putUri(bundle, "href", shareLinkContent.getContentUrl());
        Utility.putNonEmptyString(bundle, "quote", shareLinkContent.getQuote());
        return bundle;
    }

    public static Bundle create(ShareOpenGraphContent shareOpenGraphContent) {
        Bundle bundle;
        block3 : {
            bundle = WebDialogParameters.createBaseParameters(shareOpenGraphContent);
            Utility.putNonEmptyString(bundle, "action_type", shareOpenGraphContent.getAction().getActionType());
            try {
                shareOpenGraphContent = ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForWeb(shareOpenGraphContent), false);
                if (shareOpenGraphContent == null) break block3;
            }
            catch (JSONException jSONException) {
                throw new FacebookException("Unable to serialize the ShareOpenGraphContent to JSON", (Throwable)jSONException);
            }
            Utility.putNonEmptyString(bundle, "action_properties", shareOpenGraphContent.toString());
        }
        return bundle;
    }

    public static Bundle create(SharePhotoContent sharePhotoContent) {
        Bundle bundle = WebDialogParameters.createBaseParameters(sharePhotoContent);
        String[] arrstring = new String[sharePhotoContent.getPhotos().size()];
        Utility.map(sharePhotoContent.getPhotos(), new Utility.Mapper<SharePhoto, String>(){

            @Override
            public String apply(SharePhoto sharePhoto) {
                return sharePhoto.getImageUrl().toString();
            }
        }).toArray(arrstring);
        bundle.putStringArray("media", arrstring);
        return bundle;
    }

    public static Bundle createBaseParameters(ShareContent shareModel) {
        Bundle bundle = new Bundle();
        if ((shareModel = shareModel.getShareHashtag()) != null) {
            Utility.putNonEmptyString(bundle, "hashtag", shareModel.getHashtag());
        }
        return bundle;
    }

    public static Bundle createForFeed(ShareFeedContent shareFeedContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "to", shareFeedContent.getToId());
        Utility.putNonEmptyString(bundle, "link", shareFeedContent.getLink());
        Utility.putNonEmptyString(bundle, "picture", shareFeedContent.getPicture());
        Utility.putNonEmptyString(bundle, "source", shareFeedContent.getMediaSource());
        Utility.putNonEmptyString(bundle, "name", shareFeedContent.getLinkName());
        Utility.putNonEmptyString(bundle, "caption", shareFeedContent.getLinkCaption());
        Utility.putNonEmptyString(bundle, "description", shareFeedContent.getLinkDescription());
        return bundle;
    }

    public static Bundle createForFeed(ShareLinkContent shareLinkContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "name", shareLinkContent.getContentTitle());
        Utility.putNonEmptyString(bundle, "description", shareLinkContent.getContentDescription());
        Utility.putNonEmptyString(bundle, "link", Utility.getUriString(shareLinkContent.getContentUrl()));
        Utility.putNonEmptyString(bundle, "picture", Utility.getUriString(shareLinkContent.getImageUrl()));
        Utility.putNonEmptyString(bundle, "quote", shareLinkContent.getQuote());
        if (shareLinkContent.getShareHashtag() != null) {
            Utility.putNonEmptyString(bundle, "hashtag", shareLinkContent.getShareHashtag().getHashtag());
        }
        return bundle;
    }

}
