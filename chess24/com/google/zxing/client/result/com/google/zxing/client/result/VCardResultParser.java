/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VCardResultParser
extends ResultParser {
    private static final Pattern BEGIN_VCARD = Pattern.compile("BEGIN:VCARD", 2);
    private static final Pattern COMMA;
    private static final Pattern CR_LF_SPACE_TAB;
    private static final Pattern EQUALS;
    private static final Pattern NEWLINE_ESCAPE;
    private static final Pattern SEMICOLON;
    private static final Pattern SEMICOLON_OR_COMMA;
    private static final Pattern UNESCAPED_SEMICOLONS;
    private static final Pattern VCARD_ESCAPES;
    private static final Pattern VCARD_LIKE_DATE;

    static {
        VCARD_LIKE_DATE = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");
        CR_LF_SPACE_TAB = Pattern.compile("\r\n[ \t]");
        NEWLINE_ESCAPE = Pattern.compile("\\\\[nN]");
        VCARD_ESCAPES = Pattern.compile("\\\\([,;\\\\])");
        EQUALS = Pattern.compile("=");
        SEMICOLON = Pattern.compile(";");
        UNESCAPED_SEMICOLONS = Pattern.compile("(?<!\\\\);+");
        COMMA = Pattern.compile(",");
        SEMICOLON_OR_COMMA = Pattern.compile("[;,]");
    }

    private static String decodeQuotedPrintable(CharSequence charSequence, String string) {
        int n = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = 0;
        while (n2 < n) {
            char c = charSequence.charAt(n2);
            int n3 = n2;
            if (c != '\n') {
                n3 = n2;
                if (c != '\r') {
                    if (c != '=') {
                        VCardResultParser.maybeAppendFragment(byteArrayOutputStream, string, stringBuilder);
                        stringBuilder.append(c);
                        n3 = n2;
                    } else {
                        n3 = n2;
                        if (n2 < n - 2) {
                            c = charSequence.charAt(n2 + 1);
                            n3 = n2;
                            if (c != '\r') {
                                n3 = n2;
                                if (c != '\n') {
                                    char c2 = charSequence.charAt(n2 += 2);
                                    int n4 = VCardResultParser.parseHexDigit(c);
                                    int n5 = VCardResultParser.parseHexDigit(c2);
                                    n3 = n2;
                                    if (n4 >= 0) {
                                        n3 = n2;
                                        if (n5 >= 0) {
                                            byteArrayOutputStream.write((n4 << 4) + n5);
                                            n3 = n2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            n2 = n3 + 1;
        }
        VCardResultParser.maybeAppendFragment(byteArrayOutputStream, string, stringBuilder);
        return stringBuilder.toString();
    }

    private static void formatNames(Iterable<List<String>> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                int n;
                int n2;
                List list = (List)object.next();
                CharSequence charSequence = (String)list.get(0);
                String[] arrstring = new String[5];
                int n3 = n2 = 0;
                while (n2 < 4 && (n = charSequence.indexOf(59, n3)) >= 0) {
                    arrstring[n2] = charSequence.substring(n3, n);
                    ++n2;
                    n3 = n + 1;
                }
                arrstring[n2] = charSequence.substring(n3);
                charSequence = new StringBuilder(100);
                VCardResultParser.maybeAppendComponent(arrstring, 3, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(arrstring, 1, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(arrstring, 2, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(arrstring, 0, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(arrstring, 4, (StringBuilder)charSequence);
                list.set(0, charSequence.toString().trim());
            }
        }
    }

    private static boolean isLikeVCardDate(CharSequence charSequence) {
        if (charSequence != null && !VCARD_LIKE_DATE.matcher(charSequence).matches()) {
            return false;
        }
        return true;
    }

    static List<String> matchSingleVCardPrefixedField(CharSequence object, String string, boolean bl, boolean bl2) {
        if ((object = VCardResultParser.matchVCardPrefixedField((CharSequence)object, string, bl, bl2)) != null && !object.isEmpty()) {
            return (List)object.get(0);
        }
        return null;
    }

    static List<List<String>> matchVCardPrefixedField(CharSequence charSequence, String string, boolean bl, boolean bl2) {
        int n = string.length();
        int n2 = 0;
        int n3 = 0;
        Object object = null;
        while (n3 < n) {
            Object object2;
            Object object3;
            Object object4;
            Object object5 = new StringBuilder("(?:^|\n)");
            object5.append((Object)charSequence);
            object5.append("(?:;([^:]*))?:");
            object5 = Pattern.compile(object5.toString(), 2).matcher(string);
            int n4 = n3;
            if (n3 > 0) {
                n4 = n3 - 1;
            }
            if (!object5.find(n4)) break;
            int n5 = object5.end(n2);
            if ((object5 = object5.group(1)) != null) {
                String[] arrstring = SEMICOLON.split((CharSequence)object5);
                int n6 = arrstring.length;
                n2 = n3 = n2;
                object2 = null;
                object5 = null;
                n4 = n3;
                do {
                    object3 = object2;
                    n3 = n2;
                    object4 = object5;
                    if (n4 < n6) {
                        object4 = arrstring[n4];
                        object3 = object2;
                        if (object2 == null) {
                            object3 = new ArrayList(1);
                        }
                        object3.add(object4);
                        object2 = EQUALS.split((CharSequence)object4, 2);
                        n3 = n2;
                        object4 = object5;
                        if (((String[])object2).length > 1) {
                            String string2 = object2[0];
                            object2 = object2[1];
                            if ("ENCODING".equalsIgnoreCase(string2) && "QUOTED-PRINTABLE".equalsIgnoreCase((String)object2)) {
                                n3 = 1;
                                object4 = object5;
                            } else {
                                n3 = n2;
                                object4 = object5;
                                if ("CHARSET".equalsIgnoreCase(string2)) {
                                    object4 = object2;
                                    n3 = n2;
                                }
                            }
                        }
                        ++n4;
                        object2 = object3;
                        n2 = n3;
                        object5 = object4;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                object3 = null;
                n3 = 0;
                object4 = null;
            }
            n2 = n5;
            while ((n4 = string.indexOf(10, n2)) >= 0) {
                if (n4 < string.length() - 1 && (string.charAt(n2 = n4 + 1) == ' ' || string.charAt(n2) == '\t')) {
                    n2 = n4 + 2;
                    continue;
                }
                if (n3 == 0) break;
                if (n4 <= 0 || string.charAt(n4 - 1) != '=') {
                    if (n4 < 2 || string.charAt(n4 - 2) != '=') break;
                }
                n2 = n4 + 1;
            }
            if (n4 < 0) {
                n3 = n;
                n2 = 0;
                continue;
            }
            if (n4 > n5) {
                object5 = object;
                if (object == null) {
                    object5 = new Object(1);
                }
                n2 = n4;
                if (n4 > 0) {
                    n2 = n4;
                    if (string.charAt(n4 - 1) == '\r') {
                        n2 = n4 - 1;
                    }
                }
                object = object2 = string.substring(n5, n2);
                if (bl) {
                    object = object2.trim();
                }
                if (n3 != 0) {
                    object = object2 = VCardResultParser.decodeQuotedPrintable((CharSequence)object, (String)object4);
                    if (bl2) {
                        object = UNESCAPED_SEMICOLONS.matcher((CharSequence)object2).replaceAll("\n").trim();
                    }
                } else {
                    object2 = object;
                    if (bl2) {
                        object2 = UNESCAPED_SEMICOLONS.matcher((CharSequence)object).replaceAll("\n").trim();
                    }
                    object = CR_LF_SPACE_TAB.matcher((CharSequence)object2).replaceAll("");
                    object = NEWLINE_ESCAPE.matcher((CharSequence)object).replaceAll("\n");
                    object = VCARD_ESCAPES.matcher((CharSequence)object).replaceAll("$1");
                }
                if (object3 == null) {
                    object2 = new ArrayList(1);
                    object2.add(object);
                    object5.add(object2);
                } else {
                    object3.add(0, object);
                    object5.add(object3);
                }
                ++n2;
                object = object5;
            } else {
                n2 = n4 + 1;
            }
            n3 = n2;
            n2 = 0;
        }
        return object;
    }

    private static void maybeAppendComponent(String[] arrstring, int n, StringBuilder stringBuilder) {
        if (arrstring[n] != null && !arrstring[n].isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(arrstring[n]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void maybeAppendFragment(ByteArrayOutputStream byteArrayOutputStream, String string, StringBuilder stringBuilder) {
        if (byteArrayOutputStream.size() > 0) {
            block5 : {
                byte[] arrby = byteArrayOutputStream.toByteArray();
                if (string == null) {
                    string = new String(arrby, Charset.forName("UTF-8"));
                } else {
                    try {
                        string = new String(arrby, string);
                        break block5;
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {}
                    string = new String(arrby, Charset.forName("UTF-8"));
                }
            }
            byteArrayOutputStream.reset();
            stringBuilder.append(string);
        }
    }

    private static String toPrimaryValue(List<String> list) {
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    private static String[] toPrimaryValues(Collection<List<String>> collection) {
        if (collection != null && !collection.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<String>(collection.size());
            Iterator<List<String>> iterator = collection.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next().get(0);
                if (string == null || string.isEmpty()) continue;
                arrayList.add(string);
            }
            return arrayList.toArray(new String[collection.size()]);
        }
        return null;
    }

    private static String[] toTypes(Collection<List<String>> collection) {
        if (collection != null) {
            if (collection.isEmpty()) {
                return null;
            }
            ArrayList<String> arrayList = new ArrayList<String>(collection.size());
            for (List<String> list : collection) {
                String string;
                block5 : {
                    for (int i = 1; i < list.size(); ++i) {
                        string = list.get(i);
                        int n = string.indexOf(61);
                        if (n >= 0) {
                            if (!"TYPE".equalsIgnoreCase(string.substring(0, n))) continue;
                            string = string.substring(n + 1);
                        }
                        break block5;
                    }
                    string = null;
                }
                arrayList.add(string);
            }
            return arrayList.toArray(new String[collection.size()]);
        }
        return null;
    }

    @Override
    public AddressBookParsedResult parse(Result list) {
        String string = VCardResultParser.getMassagedText((Result)((Object)list));
        list = BEGIN_VCARD.matcher(string);
        if (list.find()) {
            if (list.start() != 0) {
                return null;
            }
            list = VCardResultParser.matchVCardPrefixedField("FN", string, true, false);
            List<List<String>> list2 = list;
            if (list == null) {
                list2 = VCardResultParser.matchVCardPrefixedField("N", string, true, false);
                VCardResultParser.formatNames(list2);
            }
            String[] arrstring = (list = VCardResultParser.matchSingleVCardPrefixedField("NICKNAME", string, true, false)) == null ? null : COMMA.split((CharSequence)list.get(0));
            List<List<String>> list3 = VCardResultParser.matchVCardPrefixedField("TEL", string, true, false);
            List<List<String>> list4 = VCardResultParser.matchVCardPrefixedField("EMAIL", string, true, false);
            List<String> list5 = VCardResultParser.matchSingleVCardPrefixedField("NOTE", string, false, false);
            List<List<String>> list6 = VCardResultParser.matchVCardPrefixedField("ADR", string, true, true);
            List<String> list7 = VCardResultParser.matchSingleVCardPrefixedField("ORG", string, true, true);
            List<Object> list8 = list = VCardResultParser.matchSingleVCardPrefixedField("BDAY", string, true, false);
            if (list != null) {
                list8 = list;
                if (!VCardResultParser.isLikeVCardDate((CharSequence)list.get(0))) {
                    list8 = null;
                }
            }
            List<String> list9 = VCardResultParser.matchSingleVCardPrefixedField("TITLE", string, true, false);
            List<List<String>> list10 = VCardResultParser.matchVCardPrefixedField("URL", string, true, false);
            List<String> list11 = VCardResultParser.matchSingleVCardPrefixedField("IMPP", string, true, false);
            list = VCardResultParser.matchSingleVCardPrefixedField("GEO", string, true, false);
            list = list == null ? null : SEMICOLON_OR_COMMA.split((CharSequence)list.get(0));
            if (list != null && ((List<Object>)list).length != 2) {
                list = null;
            }
            return new AddressBookParsedResult(VCardResultParser.toPrimaryValues(list2), arrstring, null, VCardResultParser.toPrimaryValues(list3), VCardResultParser.toTypes(list3), VCardResultParser.toPrimaryValues(list4), VCardResultParser.toTypes(list4), VCardResultParser.toPrimaryValue(list11), VCardResultParser.toPrimaryValue(list5), VCardResultParser.toPrimaryValues(list6), VCardResultParser.toTypes(list6), VCardResultParser.toPrimaryValue(list7), VCardResultParser.toPrimaryValue(list8), VCardResultParser.toPrimaryValue(list9), VCardResultParser.toPrimaryValues(list10), (String[])list);
        }
        return null;
    }
}
