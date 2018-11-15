/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text.util;

import android.support.v4.text.util.LinkifyCompat;
import java.util.Comparator;

static final class LinkifyCompat
implements Comparator<LinkifyCompat.LinkSpec> {
    LinkifyCompat() {
    }

    @Override
    public int compare(LinkifyCompat.LinkSpec linkSpec, LinkifyCompat.LinkSpec linkSpec2) {
        if (linkSpec.start < linkSpec2.start) {
            return -1;
        }
        if (linkSpec.start > linkSpec2.start) {
            return 1;
        }
        if (linkSpec.end < linkSpec2.end) {
            return 1;
        }
        if (linkSpec.end > linkSpec2.end) {
            return -1;
        }
        return 0;
    }
}
