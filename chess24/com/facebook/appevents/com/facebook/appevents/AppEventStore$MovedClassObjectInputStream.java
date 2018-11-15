/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventStore;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

private static class AppEventStore.MovedClassObjectInputStream
extends ObjectInputStream {
    private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
    private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";

    public AppEventStore.MovedClassObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass objectStreamClass = super.readClassDescriptor();
        if (objectStreamClass.getName().equals(ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
            return ObjectStreamClass.lookup(AccessTokenAppIdPair.SerializationProxyV1.class);
        }
        ObjectStreamClass objectStreamClass2 = objectStreamClass;
        if (objectStreamClass.getName().equals(APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
            objectStreamClass2 = ObjectStreamClass.lookup(AppEvent.SerializationProxyV1.class);
        }
        return objectStreamClass2;
    }
}
