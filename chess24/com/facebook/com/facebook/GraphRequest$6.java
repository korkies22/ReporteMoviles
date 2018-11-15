/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.GraphRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

class GraphRequest
implements GraphRequest.KeyValueSerializer {
    final /* synthetic */ ArrayList val$keysAndValues;

    GraphRequest(ArrayList arrayList) {
        this.val$keysAndValues = arrayList;
    }

    @Override
    public void writeString(String string, String string2) throws IOException {
        this.val$keysAndValues.add(String.format(Locale.US, "%s=%s", string, URLEncoder.encode(string2, "UTF-8")));
    }
}
