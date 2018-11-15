/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text;

import android.support.v4.text.BidiFormatter;

private static class BidiFormatter.DirectionalityEstimator {
    private static final byte[] DIR_TYPE_CACHE = new byte[1792];
    private static final int DIR_TYPE_CACHE_SIZE = 1792;
    private int charIndex;
    private final boolean isHtml;
    private char lastChar;
    private final int length;
    private final CharSequence text;

    static {
        for (int i = 0; i < 1792; ++i) {
            BidiFormatter.DirectionalityEstimator.DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
        }
    }

    BidiFormatter.DirectionalityEstimator(CharSequence charSequence, boolean bl) {
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
        byte by2 = by = BidiFormatter.DirectionalityEstimator.getCachedDirectionality(this.lastChar);
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
        byte by2 = by = BidiFormatter.DirectionalityEstimator.getCachedDirectionality(this.lastChar);
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
