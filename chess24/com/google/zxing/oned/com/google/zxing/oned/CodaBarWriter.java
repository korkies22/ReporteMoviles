/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.OneDimensionalCodeWriter;

public final class CodaBarWriter
extends OneDimensionalCodeWriter {
    private static final char[] ALT_START_END_CHARS;
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED;
    private static final char DEFAULT_GUARD;
    private static final char[] START_END_CHARS;

    static {
        START_END_CHARS = new char[]{'A', 'B', 'C', 'D'};
        ALT_START_END_CHARS = new char[]{'T', 'N', '*', 'E'};
        CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = new char[]{'/', ':', '+', '.'};
        DEFAULT_GUARD = START_END_CHARS[0];
    }

    @Override
    public boolean[] encode(String object) {
        block19 : {
            int n;
            CharSequence charSequence;
            int n2;
            boolean bl;
            block16 : {
                boolean bl2;
                boolean bl3;
                block18 : {
                    boolean bl4;
                    block17 : {
                        block15 : {
                            if (object.length() >= 2) break block15;
                            charSequence = new StringBuilder();
                            charSequence.append(DEFAULT_GUARD);
                            charSequence.append((String)object);
                            charSequence.append(DEFAULT_GUARD);
                            charSequence = ((StringBuilder)charSequence).toString();
                            break block16;
                        }
                        char c = Character.toUpperCase(object.charAt(0));
                        char c2 = Character.toUpperCase(object.charAt(object.length() - 1));
                        bl = CodaBarReader.arrayContains(START_END_CHARS, c);
                        bl2 = CodaBarReader.arrayContains(START_END_CHARS, c2);
                        bl4 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c);
                        bl3 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c2);
                        if (!bl) break block17;
                        charSequence = object;
                        if (!bl2) {
                            charSequence = new StringBuilder("Invalid start/end guards: ");
                            charSequence.append((String)object);
                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        }
                        break block16;
                    }
                    if (!bl4) break block18;
                    charSequence = object;
                    if (!bl3) {
                        charSequence = new StringBuilder("Invalid start/end guards: ");
                        charSequence.append((String)object);
                        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                    }
                    break block16;
                }
                if (bl2 || bl3) break block19;
                charSequence = new StringBuilder();
                charSequence.append(DEFAULT_GUARD);
                charSequence.append((String)object);
                charSequence.append(DEFAULT_GUARD);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            int n3 = 20;
            for (n = 1; n < ((String)charSequence).length() - 1; ++n) {
                if (!Character.isDigit(((String)charSequence).charAt(n)) && ((String)charSequence).charAt(n) != '-' && ((String)charSequence).charAt(n) != '$') {
                    if (CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, ((String)charSequence).charAt(n))) {
                        n3 += 10;
                        continue;
                    }
                    object = new StringBuilder("Cannot encode : '");
                    object.append(((String)charSequence).charAt(n));
                    object.append('\'');
                    throw new IllegalArgumentException(object.toString());
                }
                n3 += 9;
            }
            object = new boolean[n3 + (((String)charSequence).length() - 1)];
            n = n2 = 0;
            while (n2 < ((String)charSequence).length()) {
                int n4;
                block14 : {
                    block21 : {
                        block20 : {
                            n4 = Character.toUpperCase(((String)charSequence).charAt(n2));
                            if (n2 == 0) break block20;
                            n3 = n4;
                            if (n2 != ((String)charSequence).length() - 1) break block21;
                        }
                        n3 = n4 != 42 ? (n4 != 69 ? (n4 != 78 ? (n4 != 84 ? (int)n4 : 65) : 66) : 68) : 67;
                    }
                    for (n4 = 0; n4 < CodaBarReader.ALPHABET.length; ++n4) {
                        if (n3 != CodaBarReader.ALPHABET[n4]) continue;
                        n4 = CodaBarReader.CHARACTER_ENCODINGS[n4];
                        break block14;
                    }
                    n4 = 0;
                }
                n3 = 0;
                bl = true;
                int n5 = n;
                int n6 = n3;
                n = n3;
                n3 = n5;
                while (n6 < 7) {
                    object[n3] = bl;
                    ++n3;
                    if ((n4 >> 6 - n6 & 1) != 0 && n != 1) {
                        ++n;
                        continue;
                    }
                    bl ^= true;
                    ++n6;
                    n = 0;
                }
                n = n3;
                if (n2 < ((String)charSequence).length() - 1) {
                    object[n3] = false;
                    n = n3 + 1;
                }
                ++n2;
            }
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder("Invalid start/end guards: ");
        stringBuilder.append((String)object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
