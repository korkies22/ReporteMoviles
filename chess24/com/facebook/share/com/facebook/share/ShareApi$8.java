/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Utility;
import java.util.Iterator;
import java.util.Set;

class ShareApi
implements CollectionMapper.Collection<String> {
    final /* synthetic */ Bundle val$parameters;

    ShareApi(Bundle bundle) {
        this.val$parameters = bundle;
    }

    @Override
    public Object get(String string) {
        return this.val$parameters.get(string);
    }

    @Override
    public Iterator<String> keyIterator() {
        return this.val$parameters.keySet().iterator();
    }

    @Override
    public void set(String charSequence, Object object, CollectionMapper.OnErrorListener onErrorListener) {
        if (!Utility.putJSONValueInBundle(this.val$parameters, (String)charSequence, object)) {
            charSequence = new StringBuilder();
            charSequence.append("Unexpected value: ");
            charSequence.append(object.toString());
            onErrorListener.onError(new FacebookException(charSequence.toString()));
        }
    }
}
