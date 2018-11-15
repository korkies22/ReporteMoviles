/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

class LikeActionController
implements PlatformServiceClient.CompletedListener {
    LikeActionController() {
    }

    @Override
    public void completed(Bundle object) {
        if (object != null) {
            if (!object.containsKey("com.facebook.platform.extra.OBJECT_IS_LIKED")) {
                return;
            }
            boolean bl = object.getBoolean("com.facebook.platform.extra.OBJECT_IS_LIKED");
            String string = object.containsKey("com.facebook.platform.extra.LIKE_COUNT_STRING_WITH_LIKE") ? object.getString("com.facebook.platform.extra.LIKE_COUNT_STRING_WITH_LIKE") : LikeActionController.this.likeCountStringWithLike;
            String string2 = object.containsKey("com.facebook.platform.extra.LIKE_COUNT_STRING_WITHOUT_LIKE") ? object.getString("com.facebook.platform.extra.LIKE_COUNT_STRING_WITHOUT_LIKE") : LikeActionController.this.likeCountStringWithoutLike;
            String string3 = object.containsKey("com.facebook.platform.extra.SOCIAL_SENTENCE_WITH_LIKE") ? object.getString("com.facebook.platform.extra.SOCIAL_SENTENCE_WITH_LIKE") : LikeActionController.this.socialSentenceWithLike;
            String string4 = object.containsKey("com.facebook.platform.extra.SOCIAL_SENTENCE_WITHOUT_LIKE") ? object.getString("com.facebook.platform.extra.SOCIAL_SENTENCE_WITHOUT_LIKE") : LikeActionController.this.socialSentenceWithoutLike;
            object = object.containsKey("com.facebook.platform.extra.UNLIKE_TOKEN") ? object.getString("com.facebook.platform.extra.UNLIKE_TOKEN") : LikeActionController.this.unlikeToken;
            LikeActionController.this.updateState(bl, string, string2, string3, string4, (String)object);
            return;
        }
    }
}
