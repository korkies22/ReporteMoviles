/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VCardResultParser;
import java.util.List;

public final class VEventResultParser
extends ResultParser {
    private static String matchSingleVCardPrefixedField(CharSequence object, String string, boolean bl) {
        if ((object = VCardResultParser.matchSingleVCardPrefixedField((CharSequence)object, string, bl, false)) != null && !object.isEmpty()) {
            return (String)object.get(0);
        }
        return null;
    }

    private static String[] matchVCardPrefixedField(CharSequence object, String arrstring, boolean bl) {
        if ((object = VCardResultParser.matchVCardPrefixedField((CharSequence)object, (String)arrstring, bl, false)) != null && !object.isEmpty()) {
            int n = object.size();
            arrstring = new String[n];
            for (int i = 0; i < n; ++i) {
                arrstring[i] = (String)((List)object.get(i)).get(0);
            }
            return arrstring;
        }
        return null;
    }

    private static String stripMailto(String string) {
        String string2;
        block2 : {
            block3 : {
                string2 = string;
                if (string == null) break block2;
                if (string.startsWith("mailto:")) break block3;
                string2 = string;
                if (!string.startsWith("MAILTO:")) break block2;
            }
            string2 = string.substring(7);
        }
        return string2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public CalendarParsedResult parse(Result object) {
        int n;
        double d;
        String string = VEventResultParser.getMassagedText((Result)object);
        if (string.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        object = VEventResultParser.matchSingleVCardPrefixedField("SUMMARY", string, true);
        String string2 = VEventResultParser.matchSingleVCardPrefixedField("DTSTART", string, true);
        if (string2 == null) {
            return null;
        }
        String string3 = VEventResultParser.matchSingleVCardPrefixedField("DTEND", string, true);
        String string4 = VEventResultParser.matchSingleVCardPrefixedField("DURATION", string, true);
        String string5 = VEventResultParser.matchSingleVCardPrefixedField("LOCATION", string, true);
        String string6 = VEventResultParser.stripMailto(VEventResultParser.matchSingleVCardPrefixedField("ORGANIZER", string, true));
        String[] arrstring = VEventResultParser.matchVCardPrefixedField("ATTENDEE", string, true);
        if (arrstring != null) {
            for (n = 0; n < arrstring.length; ++n) {
                arrstring[n] = VEventResultParser.stripMailto(arrstring[n]);
            }
        }
        String string7 = VEventResultParser.matchSingleVCardPrefixedField("DESCRIPTION", string, true);
        string = VEventResultParser.matchSingleVCardPrefixedField("GEO", string, true);
        double d2 = Double.NaN;
        if (string == null) {
            d = Double.NaN;
        } else {
            n = string.indexOf(59);
            if (n < 0) {
                return null;
            }
            d2 = Double.parseDouble(string.substring(0, n));
            d = Double.parseDouble(string.substring(n + 1));
        }
        try {
            return new CalendarParsedResult((String)object, string2, string3, string4, string5, string6, arrstring, string7, d2, d);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }
}
