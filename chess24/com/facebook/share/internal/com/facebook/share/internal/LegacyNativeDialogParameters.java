/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class LegacyNativeDialogParameters {
    private static Bundle create(ShareLinkContent shareLinkContent, boolean bl) {
        Bundle bundle = LegacyNativeDialogParameters.createBaseParameters(shareLinkContent, bl);
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.TITLE", shareLinkContent.getContentTitle());
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.DESCRIPTION", shareLinkContent.getContentDescription());
        Utility.putUri(bundle, "com.facebook.platform.extra.IMAGE", shareLinkContent.getImageUrl());
        return bundle;
    }

    private static Bundle create(ShareOpenGraphContent shareOpenGraphContent, JSONObject jSONObject, boolean bl) {
        Bundle bundle = LegacyNativeDialogParameters.createBaseParameters(shareOpenGraphContent, bl);
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.PREVIEW_PROPERTY_NAME", shareOpenGraphContent.getPreviewPropertyName());
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.ACTION_TYPE", shareOpenGraphContent.getAction().getActionType());
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.ACTION", jSONObject.toString());
        return bundle;
    }

    private static Bundle create(SharePhotoContent sharePhotoContent, List<String> list, boolean bl) {
        sharePhotoContent = LegacyNativeDialogParameters.createBaseParameters(sharePhotoContent, bl);
        sharePhotoContent.putStringArrayList("com.facebook.platform.extra.PHOTOS", new ArrayList<String>(list));
        return sharePhotoContent;
    }

    private static Bundle create(ShareVideoContent shareVideoContent, boolean bl) {
        return null;
    }

    public static Bundle create(UUID uUID, ShareContent object, boolean bl) {
        Validate.notNull(object, "shareContent");
        Validate.notNull(uUID, "callId");
        if (object instanceof ShareLinkContent) {
            return LegacyNativeDialogParameters.create((ShareLinkContent)object, bl);
        }
        if (object instanceof SharePhotoContent) {
            object = (SharePhotoContent)object;
            return LegacyNativeDialogParameters.create((SharePhotoContent)object, ShareInternalUtility.getPhotoUrls((SharePhotoContent)object, uUID), bl);
        }
        if (object instanceof ShareVideoContent) {
            return LegacyNativeDialogParameters.create((ShareVideoContent)object, bl);
        }
        if (object instanceof ShareOpenGraphContent) {
            object = (ShareOpenGraphContent)object;
            try {
                uUID = LegacyNativeDialogParameters.create((ShareOpenGraphContent)object, ShareInternalUtility.toJSONObjectForCall(uUID, (ShareOpenGraphContent)object), bl);
                return uUID;
            }
            catch (JSONException jSONException) {
                object = new StringBuilder();
                object.append("Unable to create a JSON Object from the provided ShareOpenGraphContent: ");
                object.append(jSONException.getMessage());
                throw new FacebookException(object.toString());
            }
        }
        return null;
    }

    private static Bundle createBaseParameters(ShareContent object, boolean bl) {
        Bundle bundle = new Bundle();
        Utility.putUri(bundle, "com.facebook.platform.extra.LINK", object.getContentUrl());
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.PLACE", object.getPlaceId());
        Utility.putNonEmptyString(bundle, "com.facebook.platform.extra.REF", object.getRef());
        bundle.putBoolean("com.facebook.platform.extra.DATA_FAILURES_FATAL", bl);
        object = object.getPeopleIds();
        if (!Utility.isNullOrEmpty(object)) {
            bundle.putStringArrayList("com.facebook.platform.extra.FRIENDS", new ArrayList(object));
        }
        return bundle;
    }
}
