/*
 * Decompiled with CFR 0_134.
 */
package fi.harism.curl;

import fi.harism.curl.CurlPage;
import fi.harism.curl.CurlView;

public static interface CurlView.PageProvider {
    public void getCurrentPage(CurlPage var1, int var2, int var3);

    public void getNextPage(CurlPage var1, int var2, int var3);

    public void getPreviousPage(CurlPage var1, int var2, int var3);

    public void getSecondPreviousPage(CurlPage var1, int var2, int var3);

    public boolean isFirstPage();

    public boolean isLastPage();

    public boolean isSecondLastPage();

    public boolean isSecondPage();
}
