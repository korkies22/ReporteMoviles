/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VINParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VINResultParser
extends ResultParser {
    private static final Pattern AZ09;
    private static final Pattern IOQ;

    static {
        IOQ = Pattern.compile("[IOQ]");
        AZ09 = Pattern.compile("[A-Z0-9]{17}");
    }

    private static char checkChar(int n) {
        if (n < 10) {
            return (char)(n + 48);
        }
        if (n == 10) {
            return 'X';
        }
        throw new IllegalArgumentException();
    }

    private static boolean checkChecksum(CharSequence charSequence) {
        int n;
        int n2 = n = 0;
        while (n < charSequence.length()) {
            int n3 = n + 1;
            n2 += VINResultParser.vinPositionWeight(n3) * VINResultParser.vinCharValue(charSequence.charAt(n));
            n = n3;
        }
        if (charSequence.charAt(8) == VINResultParser.checkChar(n2 % 11)) {
            return true;
        }
        return false;
    }

    private static String countryCode(CharSequence charSequence) {
        block30 : {
            char c;
            block27 : {
                block28 : {
                    block29 : {
                        char c2 = charSequence.charAt(0);
                        c = charSequence.charAt(1);
                        if (c2 == '9') break block27;
                        if (c2 == 'S') break block28;
                        if (c2 == 'Z') break block29;
                        switch (c2) {
                            default: {
                                switch (c2) {
                                    default: {
                                        switch (c2) {
                                            default: {
                                                break block30;
                                            }
                                            case 'X': {
                                                if (c == '0' || c >= '3' && c <= '9') {
                                                    return "RU";
                                                }
                                                break block30;
                                            }
                                            case 'W': {
                                                return "DE";
                                            }
                                            case 'V': {
                                                if (c >= 'F' && c <= 'R') {
                                                    return "FR";
                                                }
                                                if (c >= 'S' && c <= 'W') {
                                                    return "ES";
                                                }
                                                break block30;
                                            }
                                        }
                                    }
                                    case 'M': {
                                        if (c >= 'A' && c <= 'E') {
                                            return "IN";
                                        }
                                        break block30;
                                    }
                                    case 'L': {
                                        return "CN";
                                    }
                                    case 'K': {
                                        if (c >= 'L' && c <= 'R') {
                                            return "KO";
                                        }
                                        break block30;
                                    }
                                    case 'J': {
                                        if (c >= 'A' && c <= 'T') {
                                            return "JP";
                                        }
                                        break block30;
                                    }
                                }
                            }
                            case '3': {
                                if (c >= 'A' && c <= 'W') {
                                    return "MX";
                                }
                                break block30;
                            }
                            case '2': {
                                return "CA";
                            }
                            case '1': 
                            case '4': 
                            case '5': {
                                return "US";
                            }
                        }
                    }
                    if (c >= 'A' && c <= 'R') {
                        return "IT";
                    }
                    break block30;
                }
                if (c >= 'A' && c <= 'M') {
                    return "UK";
                }
                if (c >= 'N' && c <= 'T') {
                    return "DE";
                }
                break block30;
            }
            if (c >= 'A' && c <= 'E' || c >= '3' && c <= '9') {
                return "BR";
            }
        }
        return null;
    }

    private static int modelYear(char c) {
        if (c >= 'E' && c <= 'H') {
            return c - 69 + 1984;
        }
        if (c >= 'J' && c <= 'N') {
            return c - 74 + 1988;
        }
        if (c == 'P') {
            return 1993;
        }
        if (c >= 'R' && c <= 'T') {
            return c - 82 + 1994;
        }
        if (c >= 'V' && c <= 'Y') {
            return c - 86 + 1997;
        }
        if (c >= '1' && c <= '9') {
            return c - 49 + 2001;
        }
        if (c >= 'A' && c <= 'D') {
            return c - 65 + 2010;
        }
        throw new IllegalArgumentException();
    }

    private static int vinCharValue(char c) {
        if (c >= 'A' && c <= 'I') {
            return c - 65 + 1;
        }
        if (c >= 'J' && c <= 'R') {
            return c - 74 + 1;
        }
        if (c >= 'S' && c <= 'Z') {
            return c - 83 + 2;
        }
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        throw new IllegalArgumentException();
    }

    private static int vinPositionWeight(int n) {
        if (n > 0 && n <= 7) {
            return 9 - n;
        }
        if (n == 8) {
            return 10;
        }
        if (n == 9) {
            return 0;
        }
        if (n >= 10 && n <= 17) {
            return 19 - n;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public VINParsedResult parse(Result object) {
        block5 : {
            if (object.getBarcodeFormat() != BarcodeFormat.CODE_39) {
                return null;
            }
            object = object.getText();
            if (!AZ09.matcher((CharSequence)(object = IOQ.matcher((CharSequence)object).replaceAll("").trim())).matches()) {
                return null;
            }
            try {
                if (VINResultParser.checkChecksum((CharSequence)object)) break block5;
                return null;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        String string = object.substring(0, 3);
        object = new VINParsedResult((String)object, string, object.substring(3, 9), object.substring(9, 17), VINResultParser.countryCode(string), object.substring(3, 8), VINResultParser.modelYear(object.charAt(9)), object.charAt(10), object.substring(11));
        return object;
    }
}
