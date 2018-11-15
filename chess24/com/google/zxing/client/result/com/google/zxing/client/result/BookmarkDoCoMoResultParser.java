/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.google.zxing.client.result.URIResultParser;

public final class BookmarkDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    @Override
    public URIParsedResult parse(Result object) {
        Object object2 = object.getText();
        boolean bl = object2.startsWith("MEBKM:");
        object = null;
        if (!bl) {
            return null;
        }
        String string = BookmarkDoCoMoResultParser.matchSingleDoCoMoPrefixedField("TITLE:", (String)object2, true);
        if ((object2 = BookmarkDoCoMoResultParser.matchDoCoMoPrefixedField("URL:", (String)object2, true)) == null) {
            return null;
        }
        if (URIResultParser.isBasicallyValidURI((String)(object2 = object2[0]))) {
            object = new URIParsedResult((String)object2, string);
        }
        return object;
    }
}
