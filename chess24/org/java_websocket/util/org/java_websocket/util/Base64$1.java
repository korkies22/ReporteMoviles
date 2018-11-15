/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

static final class Base64
extends ObjectInputStream {
    final /* synthetic */ ClassLoader val$loader;

    Base64(InputStream inputStream, ClassLoader classLoader) {
        this.val$loader = classLoader;
        super(inputStream);
    }

    @Override
    public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        Class<?> class_ = Class.forName(objectStreamClass.getName(), false, this.val$loader);
        if (class_ == null) {
            return super.resolveClass(objectStreamClass);
        }
        return class_;
    }
}
