/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text;

import android.support.v4.text.BidiFormatter;
import android.support.v4.text.TextDirectionHeuristicCompat;
import java.util.Locale;

public static final class BidiFormatter.Builder {
    private int mFlags;
    private boolean mIsRtlContext;
    private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

    public BidiFormatter.Builder() {
        this.initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
    }

    public BidiFormatter.Builder(Locale locale) {
        this.initialize(BidiFormatter.isRtlLocale(locale));
    }

    public BidiFormatter.Builder(boolean bl) {
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
            return BidiFormatter.Builder.getDefaultInstanceFromContext(this.mIsRtlContext);
        }
        return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat, null);
    }

    public BidiFormatter.Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
        return this;
    }

    public BidiFormatter.Builder stereoReset(boolean bl) {
        if (bl) {
            this.mFlags |= 2;
            return this;
        }
        this.mFlags &= -3;
        return this;
    }
}
