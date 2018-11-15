/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.URIParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIResultParser
extends ResultParser {
    private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN;
    private static final Pattern URL_WITH_PROTOCOL_PATTERN;

    static {
        URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");
        URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.){1,6}[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");
    }

    static boolean isBasicallyValidURI(String object) {
        if (object.contains(" ")) {
            return false;
        }
        Matcher matcher = URL_WITH_PROTOCOL_PATTERN.matcher((CharSequence)object);
        if (matcher.find() && matcher.start() == 0) {
            return true;
        }
        if ((object = URL_WITHOUT_PROTOCOL_PATTERN.matcher((CharSequence)object)).find() && object.start() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public URIParsedResult parse(Result object) {
        if (!(object = URIResultParser.getMassagedText((Result)object)).startsWith("URL:") && !object.startsWith("URI:")) {
            if (URIResultParser.isBasicallyValidURI((String)(object = object.trim()))) {
                return new URIParsedResult((String)object, null);
            }
            return null;
        }
        return new URIParsedResult(object.substring(4).trim(), null);
    }
}
