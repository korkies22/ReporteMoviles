/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIParsedResult
extends ParsedResult {
    private static final Pattern USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
    private final String title;
    private final String uri;

    public URIParsedResult(String string, String string2) {
        super(ParsedResultType.URI);
        this.uri = URIParsedResult.massageURI(string);
        this.title = string2;
    }

    private static boolean isColonFollowedByPortNumber(String string, int n) {
        int n2;
        int n3 = n + 1;
        n = n2 = string.indexOf(47, n3);
        if (n2 < 0) {
            n = string.length();
        }
        return ResultParser.isSubstringOfDigits(string, n3, n - n3);
    }

    private static String massageURI(String charSequence) {
        block3 : {
            String string;
            block2 : {
                string = charSequence.trim();
                int n = string.indexOf(58);
                if (n < 0) break block2;
                charSequence = string;
                if (!URIParsedResult.isColonFollowedByPortNumber(string, n)) break block3;
            }
            charSequence = new StringBuilder("http://");
            charSequence.append(string);
            charSequence = charSequence.toString();
        }
        return charSequence;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(30);
        URIParsedResult.maybeAppend(this.title, stringBuilder);
        URIParsedResult.maybeAppend(this.uri, stringBuilder);
        return stringBuilder.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getURI() {
        return this.uri;
    }

    public boolean isPossiblyMaliciousURI() {
        return USER_IN_HOST.matcher(this.uri).find();
    }
}
