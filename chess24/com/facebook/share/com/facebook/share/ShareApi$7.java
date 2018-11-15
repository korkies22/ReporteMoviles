/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share;

import com.facebook.internal.CollectionMapper;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;

class ShareApi
implements CollectionMapper.ValueMapper {
    ShareApi() {
    }

    @Override
    public void mapValue(Object object, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        if (object instanceof ArrayList) {
            ShareApi.this.stageArrayList((ArrayList)object, onMapValueCompleteListener);
            return;
        }
        if (object instanceof ShareOpenGraphObject) {
            ShareApi.this.stageOpenGraphObject((ShareOpenGraphObject)object, onMapValueCompleteListener);
            return;
        }
        if (object instanceof SharePhoto) {
            ShareApi.this.stagePhoto((SharePhoto)object, onMapValueCompleteListener);
            return;
        }
        onMapValueCompleteListener.onComplete(object);
    }
}
