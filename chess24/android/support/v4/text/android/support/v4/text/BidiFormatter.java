/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.SpannableStringBuilder
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.text.TextUtilsCompat;
import android.text.SpannableStringBuilder;
import java.util.Locale;

public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    private static final BidiFormatter DEFAULT_LTR_INSTANCE;
    private static final BidiFormatter DEFAULT_RTL_INSTANCE;
    private static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '\u202a';
    private static final char LRM = '\u200e';
    private static final String LRM_STRING;
    private static final char PDF = '\u202c';
    private static final char RLE = '\u202b';
    private static final char RLM = '\u200f';
    private static final String RLM_STRING;
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    static {
        DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        LRM_STRING = Character.toString('\u200e');
        RLM_STRING = Character.toString('\u200f');
        DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
        DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    }

    private BidiFormatter(boolean bl, int n, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = bl;
        this.mFlags = n;
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
    }

    private static int getEntryDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getEntryDir();
    }

    private static int getExitDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getExitDir();
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    public static BidiFormatter getInstance(boolean bl) {
        return new Builder(bl).build();
    }

    private static boolean isRtlLocale(Locale locale) {
        if (TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1) {
            return true;
        }
        return false;
    }

    private String markAfter(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean bl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (bl || BidiFormatter.getExitDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!bl || BidiFormatter.getExitDir(charSequence) == -1)) {
            return RLM_STRING;
        }
        return EMPTY_STRING;
    }

    private String markBefore(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean bl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (bl || BidiFormatter.getEntryDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!bl || BidiFormatter.getEntryDir(charSequence) == -1)) {
            return RLM_STRING;
        }
        return EMPTY_STRING;
    }

    public boolean getStereoReset() {
        if ((this.mFlags & 2) != 0) {
            return true;
        }
        return false;
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.mDefaultTextDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    public boolean isRtl(String string) {
        return this.isRtl((CharSequence)string);
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return this.unicodeWrap(charSequence, textDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean bl) {
        if (charSequence == null) {
            return null;
        }
        boolean bl2 = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.getStereoReset() && bl) {
            textDirectionHeuristicCompat = bl2 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR;
            spannableStringBuilder.append((CharSequence)this.markBefore(charSequence, textDirectionHeuristicCompat));
        }
        if (bl2 != this.mIsRtlContext) {
            char c = bl2 ? (char)'\u202b' : '\u202a';
            spannableStringBuilder.append(c);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append('\u202c');
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (bl) {
            textDirectionHeuristicCompat = bl2 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR;
            spannableStringBuilder.append((CharSequence)this.markAfter(charSequence, textDirectionHeuristicCompat));
        }
        return spannableStringBuilder;
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean bl) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, bl);
    }

    public String unicodeWrap(String string) {
        return this.unicodeWrap(string, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String string, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return this.unicodeWrap(string, textDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String string, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean bl) {
        if (string == null) {
            return null;
        }
        return this.unicodeWrap((CharSequence)string, textDirectionHeuristicCompat, bl).toString();
    }

    public String unicodeWrap(String string, boolean bl) {
        return this.unicodeWrap(string, this.mDefaultTextDirectionHeuristicCompat, bl);
    }

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() {
            this.initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        public Builder(Locale locale) {
            this.initialize(BidiFormatter.isRtlLocale(locale));
        }

        public Builder(boolean bl) {
            this.initialize(bl);
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean bl) {
            if (bl) {
                return DEFAULT_RTL_INSTANCE;
            }
            return DEFAULT_LTR_INSTANCE;
        }

        private void initialize(boolean bl) {
            this.mIsRtlContext = bl;
            this.mTextDirectionHeuristicCompat = DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return Builder.getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.mTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
            return this;
        }

        public Builder stereoReset(boolean bl) {
            if (bl) {
                this.mFlags |= 2;
                return this;
            }
            this.mFlags &= -3;
            return this;
        }
    }

    private static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[1792];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final CharSequence text;

        static {
            for (int i = 0; i < 1792; ++i) {
                DirectionalityEstimator.DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean bl) {
            this.text = charSequence;
            this.isHtml = bl;
            this.length = charSequence.length();
        }

        private static byte getCachedDirectionality(char c) {
            if (c < '\u0700') {
                return DIR_TYPE_CACHE[c];
            }
            return Character.getDirectionality(c);
        }

        private byte skipEntityBackward() {
            int n = this.charIndex;
            while (this.charIndex > 0) {
                int n2;
                CharSequence charSequence = this.text;
                this.charIndex = n2 = this.charIndex - 1;
                this.lastChar = charSequence.charAt(n2);
                if (this.lastChar == '&') {
                    return 12;
                }
                if (this.lastChar != ';') continue;
            }
            this.charIndex = n;
            this.lastChar = (char)59;
            return 13;
        }

        private byte skipEntityForward() {
            while (this.charIndex < this.length) {
                char c;
                CharSequence charSequence = this.text;
                int n = this.charIndex;
                this.charIndex = n + 1;
                this.lastChar = c = charSequence.charAt(n);
                if (c != ';') continue;
            }
            return 12;
        }

        private byte skipTagBackward() {
            int n = this.charIndex;
            block0 : while (this.charIndex > 0) {
                int n2;
                CharSequence charSequence = this.text;
                this.charIndex = n2 = this.charIndex - 1;
                this.lastChar = charSequence.charAt(n2);
                if (this.lastChar == '<') {
                    return 12;
                }
                if (this.lastChar == '>') break;
                if (this.lastChar != '\"' && this.lastChar != '\'') continue;
                n2 = this.lastChar;
                while (this.charIndex > 0) {
                    int n3;
                    char c;
                    charSequence = this.text;
                    this.charIndex = n3 = this.charIndex - 1;
                    this.lastChar = c = charSequence.charAt(n3);
                    if (c == n2) continue block0;
                }
            }
            this.charIndex = n;
            this.lastChar = (char)62;
            return 13;
        }

        private byte skipTagForward() {
            int n = this.charIndex;
            block0 : while (this.charIndex < this.length) {
                CharSequence charSequence = this.text;
                int n2 = this.charIndex;
                this.charIndex = n2 + 1;
                this.lastChar = charSequence.charAt(n2);
                if (this.lastChar == '>') {
                    return 12;
                }
                if (this.lastChar != '\"' && this.lastChar != '\'') continue;
                n2 = this.lastChar;
                while (this.charIndex < this.length) {
                    char c;
                    charSequence = this.text;
                    int n3 = this.charIndex;
                    this.charIndex = n3 + 1;
                    this.lastChar = c = charSequence.charAt(n3);
                    if (c == n2) continue block0;
                }
            }
            this.charIndex = n;
            this.lastChar = (char)60;
            return 13;
        }

        byte dirTypeBackward() {
            byte by;
            this.lastChar = this.text.charAt(this.charIndex - 1);
            if (Character.isLowSurrogate(this.lastChar)) {
                int n = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(n);
                return Character.getDirectionality(n);
            }
            --this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (this.isHtml) {
                if (this.lastChar == '>') {
                    return this.skipTagBackward();
                }
                by2 = by;
                if (this.lastChar == ';') {
                    by2 = this.skipEntityBackward();
                }
            }
            return by2;
        }

        byte dirTypeForward() {
            byte by;
            this.lastChar = this.text.charAt(this.charIndex);
            if (Character.isHighSurrogate(this.lastChar)) {
                int n = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount(n);
                return Character.getDirectionality(n);
            }
            ++this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (this.isHtml) {
                if (this.lastChar == '<') {
                    return this.skipTagForward();
                }
                by2 = by;
                if (this.lastChar == '&') {
                    by2 = this.skipEntityForward();
                }
            }
            return by2;
        }

        int getEntryDir() {
            int n;
            int n2;
            this.charIndex = 0;
            int n3 = n2 = (n = 0);
            block14 : while (this.charIndex < this.length && n == 0) {
                byte by = this.dirTypeForward();
                if (by == 9) continue;
                block0 : switch (by) {
                    default: {
                        switch (by) {
                            default: {
                                break block0;
                            }
                            case 18: {
                                --n3;
                                n2 = 0;
                                continue block14;
                            }
                            case 16: 
                            case 17: {
                                ++n3;
                                n2 = 1;
                                continue block14;
                            }
                            case 14: 
                            case 15: 
                        }
                        ++n3;
                        n2 = -1;
                        continue block14;
                    }
                    case 1: 
                    case 2: {
                        if (n3 != 0) break;
                        return 1;
                    }
                    case 0: {
                        if (n3 != 0) break;
                        return -1;
                    }
                }
                n = n3;
            }
            if (n == 0) {
                return 0;
            }
            if (n2 != 0) {
                return n2;
            }
            block15 : while (this.charIndex > 0) {
                switch (this.dirTypeBackward()) {
                    default: {
                        continue block15;
                    }
                    case 18: {
                        ++n3;
                        continue block15;
                    }
                    case 16: 
                    case 17: {
                        if (n == n3) {
                            return 1;
                        }
                        --n3;
                        continue block15;
                    }
                    case 14: 
                    case 15: 
                }
                if (n == n3) {
                    return -1;
                }
                --n3;
            }
            return 0;
        }

        int getExitDir() {
            int n;
            this.charIndex = this.length;
            int n2 = n = 0;
            block9 : while (this.charIndex > 0) {
                byte by = this.dirTypeBackward();
                if (by == 9) continue;
                block0 : switch (by) {
                    default: {
                        switch (by) {
                            default: {
                                if (n != 0) continue block9;
                                break block0;
                            }
                            case 18: {
                                ++n2;
                                break;
                            }
                            case 16: 
                            case 17: {
                                if (n == n2) {
                                    return 1;
                                }
                                --n2;
                                break;
                            }
                            case 14: 
                            case 15: {
                                if (n == n2) {
                                    return -1;
                                }
                                --n2;
                                break;
                            }
                        }
                        continue block9;
                    }
                    case 1: 
                    case 2: {
                        if (n2 == 0) {
                            return 1;
                        }
                        if (n != 0) continue block9;
                        break;
                    }
                    case 0: {
                        if (n2 == 0) {
                            return -1;
                        }
                        if (n != 0) continue block9;
                    }
                }
                n = n2;
            }
            return 0;
        }
    }

}
