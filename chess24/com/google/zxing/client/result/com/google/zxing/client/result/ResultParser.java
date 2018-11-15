/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookAUResultParser;
import com.google.zxing.client.result.AddressBookDoCoMoResultParser;
import com.google.zxing.client.result.BizcardResultParser;
import com.google.zxing.client.result.BookmarkDoCoMoResultParser;
import com.google.zxing.client.result.EmailAddressResultParser;
import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.google.zxing.client.result.ExpandedProductResultParser;
import com.google.zxing.client.result.GeoResultParser;
import com.google.zxing.client.result.ISBNResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductResultParser;
import com.google.zxing.client.result.SMSMMSResultParser;
import com.google.zxing.client.result.SMSTOMMSTOResultParser;
import com.google.zxing.client.result.SMTPResultParser;
import com.google.zxing.client.result.TelResultParser;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIResultParser;
import com.google.zxing.client.result.URLTOResultParser;
import com.google.zxing.client.result.VCardResultParser;
import com.google.zxing.client.result.VEventResultParser;
import com.google.zxing.client.result.VINResultParser;
import com.google.zxing.client.result.WifiResultParser;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ResultParser {
    private static final Pattern AMPERSAND;
    private static final String BYTE_ORDER_MARK = "\ufeff";
    private static final Pattern DIGITS;
    private static final Pattern EQUALS;
    private static final ResultParser[] PARSERS;

    static {
        PARSERS = new ResultParser[]{new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser()};
        DIGITS = Pattern.compile("\\d+");
        AMPERSAND = Pattern.compile("&");
        EQUALS = Pattern.compile("=");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void appendKeyValue(CharSequence charSequence, Map<String, String> map) {
        String[] arrstring = EQUALS.split(charSequence, 2);
        if (arrstring.length != 2) return;
        charSequence = arrstring[0];
        String string = arrstring[1];
        try {
            map.put((String)charSequence, ResultParser.urlDecode(string));
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    private static int countPrecedingBackslashes(CharSequence charSequence, int n) {
        --n;
        int n2 = 0;
        while (n >= 0 && charSequence.charAt(n) == '\\') {
            ++n2;
            --n;
        }
        return n2;
    }

    protected static String getMassagedText(Result object) {
        String string = object.getText();
        object = string;
        if (string.startsWith(BYTE_ORDER_MARK)) {
            object = string.substring(1);
        }
        return object;
    }

    protected static boolean isStringOfDigits(CharSequence charSequence, int n) {
        if (charSequence != null && n > 0 && n == charSequence.length() && DIGITS.matcher(charSequence).matches()) {
            return true;
        }
        return false;
    }

    protected static boolean isSubstringOfDigits(CharSequence charSequence, int n, int n2) {
        if (charSequence != null) {
            if (n2 <= 0) {
                return false;
            }
            if (charSequence.length() >= (n2 += n) && DIGITS.matcher(charSequence.subSequence(n, n2)).matches()) {
                return true;
            }
            return false;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    static String[] matchPrefixedField(String string, String string2, char c, boolean bl) {
        int n = string2.length();
        ArrayList arrayList = null;
        int n2 = 0;
        block0 : do {
            if (n2 >= n || (n2 = string2.indexOf(string, n2)) < 0) {
                if (arrayList == null) {
                    return null;
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return arrayList.toArray(new String[arrayList.size()]);
            }
            int n3 = n2 + string.length();
            boolean bl2 = true;
            n2 = n3;
            do {
                if (!bl2) continue block0;
                if ((n2 = string2.indexOf(c, n2)) < 0) {
                    n2 = string2.length();
                } else {
                    if (ResultParser.countPrecedingBackslashes(string2, n2) % 2 != 0) {
                        ++n2;
                        continue;
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList(3);
                    }
                    String string3 = ResultParser.unescapeBackslash(string2.substring(n3, n2));
                    arrayList = string3;
                    if (bl) {
                        arrayList = string3.trim();
                    }
                    if (!((String)((Object)arrayList)).isEmpty()) {
                        arrayList2.add(arrayList);
                    }
                    ++n2;
                    arrayList = arrayList2;
                }
                bl2 = false;
            } while (true);
            break;
        } while (true);
    }

    static String matchSinglePrefixedField(String arrstring, String string, char c, boolean bl) {
        if ((arrstring = ResultParser.matchPrefixedField((String)arrstring, string, c, bl)) == null) {
            return null;
        }
        return arrstring[0];
    }

    protected static void maybeAppend(String string, StringBuilder stringBuilder) {
        if (string != null) {
            stringBuilder.append('\n');
            stringBuilder.append(string);
        }
    }

    protected static void maybeAppend(String[] arrstring, StringBuilder stringBuilder) {
        if (arrstring != null) {
            for (String string : arrstring) {
                stringBuilder.append('\n');
                stringBuilder.append(string);
            }
        }
    }

    protected static String[] maybeWrap(String string) {
        if (string == null) {
            return null;
        }
        return new String[]{string};
    }

    protected static int parseHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return 10 + (c - 97);
        }
        if (c >= 'A' && c <= 'F') {
            return 10 + (c - 65);
        }
        return -1;
    }

    static Map<String, String> parseNameValuePairs(String arrstring) {
        int n = arrstring.indexOf(63);
        if (n < 0) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>(3);
        arrstring = AMPERSAND.split(arrstring.substring(n + 1));
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            ResultParser.appendKeyValue(arrstring[n], hashMap);
        }
        return hashMap;
    }

    public static ParsedResult parseResult(Result result) {
        ResultParser[] arrresultParser = PARSERS;
        int n = arrresultParser.length;
        for (int i = 0; i < n; ++i) {
            ParsedResult parsedResult = arrresultParser[i].parse(result);
            if (parsedResult == null) continue;
            return parsedResult;
        }
        return new TextParsedResult(result.getText(), null);
    }

    protected static String unescapeBackslash(String string) {
        int n = string.indexOf(92);
        if (n < 0) {
            return string;
        }
        int n2 = string.length();
        StringBuilder stringBuilder = new StringBuilder(n2 - 1);
        stringBuilder.append(string.toCharArray(), 0, n);
        boolean bl = false;
        while (n < n2) {
            char c = string.charAt(n);
            if (!bl && c == '\\') {
                bl = true;
            } else {
                stringBuilder.append(c);
                bl = false;
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    static String urlDecode(String string) {
        try {
            string = URLDecoder.decode(string, "UTF-8");
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalStateException(unsupportedEncodingException);
        }
    }

    public abstract ParsedResult parse(Result var1);
}
