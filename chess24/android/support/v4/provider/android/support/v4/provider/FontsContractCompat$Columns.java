/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.provider.BaseColumns
 */
package android.support.v4.provider;

import android.provider.BaseColumns;
import android.support.v4.provider.FontsContractCompat;

public static final class FontsContractCompat.Columns
implements BaseColumns {
    public static final String FILE_ID = "file_id";
    public static final String ITALIC = "font_italic";
    public static final String RESULT_CODE = "result_code";
    public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
    public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
    public static final int RESULT_CODE_MALFORMED_QUERY = 3;
    public static final int RESULT_CODE_OK = 0;
    public static final String TTC_INDEX = "font_ttc_index";
    public static final String VARIATION_SETTINGS = "font_variation_settings";
    public static final String WEIGHT = "font_weight";
}
