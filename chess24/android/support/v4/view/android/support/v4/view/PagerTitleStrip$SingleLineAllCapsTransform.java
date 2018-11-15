/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.text.method.SingleLineTransformationMethod
 *  android.view.View
 */
package android.support.v4.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.view.PagerTitleStrip;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import java.util.Locale;

private static class PagerTitleStrip.SingleLineAllCapsTransform
extends SingleLineTransformationMethod {
    private Locale mLocale;

    PagerTitleStrip.SingleLineAllCapsTransform(Context context) {
        this.mLocale = context.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if ((charSequence = super.getTransformation(charSequence, view)) != null) {
            return charSequence.toString().toUpperCase(this.mLocale);
        }
        return null;
    }
}
