/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

public interface IErrorPresenter {
    public void showViewForErrorCode(APIStatusCode var1, IReloadAction var2);

    public void showViewForErrorCode(APIStatusCode var1, IReloadAction var2, ICancelAction var3);

    public void showViewForErrorCode(APIStatusCode var1, IReloadAction var2, boolean var3);

    public static interface ICancelAction {
        public void cancelPressed();
    }

    public static interface IReloadAction {
        public void performReload();
    }

}
