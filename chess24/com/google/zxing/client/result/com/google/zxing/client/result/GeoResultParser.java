/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.GeoParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeoResultParser
extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override
    public GeoParsedResult parse(Result object) {
        block6 : {
            block8 : {
                String string;
                double d;
                double d2;
                double d3;
                block10 : {
                    block9 : {
                        block7 : {
                            object = GeoResultParser.getMassagedText((Result)object);
                            if (!(object = GEO_URL_PATTERN.matcher((CharSequence)object)).matches()) {
                                return null;
                            }
                            string = object.group(4);
                            try {
                                d3 = Double.parseDouble(object.group(1));
                                if (d3 > 90.0) break block6;
                                if (d3 >= -90.0) break block7;
                                return null;
                            }
                            catch (NumberFormatException numberFormatException) {
                                return null;
                            }
                        }
                        d = Double.parseDouble(object.group(2));
                        if (d > 180.0) break block8;
                        if (d >= -180.0) break block9;
                        return null;
                    }
                    String string2 = object.group(3);
                    d2 = 0.0;
                    if (string2 == null) break block10;
                    d2 = Double.parseDouble(object.group(3));
                    if (d2 >= 0.0) break block10;
                    return null;
                }
                return new GeoParsedResult(d3, d, d2, string);
            }
            return null;
        }
        return null;
    }
}
