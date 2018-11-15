/*
 * Decompiled with CFR 0_134.
 */
package fi.harism.curl;

import fi.harism.curl.CurlView;

public static interface CurlView.PageChangeObserver {
    public static final int PAGE_CHANGE_NEXT_PAGE = 1;
    public static final int PAGE_CHANGE_NO_CHANGE = 0;
    public static final int PAGE_CHANGE_PREVIOUS_PAGE = -1;

    public void curlAnimationFinished();

    public void curlAnimationStarted(int var1);
}
