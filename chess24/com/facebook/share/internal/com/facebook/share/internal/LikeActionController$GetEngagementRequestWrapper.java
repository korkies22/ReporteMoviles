/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;
import java.util.Locale;
import org.json.JSONObject;

private class LikeActionController.GetEngagementRequestWrapper
extends LikeActionController.AbstractRequestWrapper {
    String likeCountStringWithLike;
    String likeCountStringWithoutLike;
    String socialSentenceStringWithLike;
    String socialSentenceStringWithoutLike;

    LikeActionController.GetEngagementRequestWrapper(String string, LikeView.ObjectType objectType) {
        super(LikeActionController.this, string, objectType);
        this.likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
        this.likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
        this.socialSentenceStringWithLike = LikeActionController.this.socialSentenceWithLike;
        this.socialSentenceStringWithoutLike = LikeActionController.this.socialSentenceWithoutLike;
        LikeActionController.this = new Bundle();
        LikeActionController.this.putString("fields", "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
        LikeActionController.this.putString("locale", Locale.getDefault().toString());
        this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), string, (Bundle)LikeActionController.this, HttpMethod.GET));
    }

    @Override
    protected void processError(FacebookRequestError facebookRequestError) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error fetching engagement for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
        LikeActionController.this.logAppEventForError("get_engagement", facebookRequestError);
    }

    @Override
    protected void processSuccess(GraphResponse graphResponse) {
        if ((graphResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), "engagement")) != null) {
            this.likeCountStringWithLike = graphResponse.optString("count_string_with_like", this.likeCountStringWithLike);
            this.likeCountStringWithoutLike = graphResponse.optString("count_string_without_like", this.likeCountStringWithoutLike);
            this.socialSentenceStringWithLike = graphResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, this.socialSentenceStringWithLike);
            this.socialSentenceStringWithoutLike = graphResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, this.socialSentenceStringWithoutLike);
        }
    }
}
