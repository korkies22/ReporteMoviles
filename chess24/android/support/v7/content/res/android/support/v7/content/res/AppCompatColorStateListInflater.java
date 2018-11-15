/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.util.StateSet
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.GrowingArrayUtils;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

final class AppCompatColorStateListInflater {
    private static final int DEFAULT_COLOR = -65536;

    private AppCompatColorStateListInflater() {
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return AppCompatColorStateListInflater.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
    }

    @NonNull
    private static ColorStateList createFromXmlInner(@NonNull Resources object, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        String string = xmlPullParser.getName();
        if (!string.equals("selector")) {
            object = new StringBuilder();
            object.append(xmlPullParser.getPositionDescription());
            object.append(": invalid color state list tag ");
            object.append(string);
            throw new XmlPullParserException(object.toString());
        }
        return AppCompatColorStateListInflater.inflate((Resources)object, xmlPullParser, attributeSet, theme);
    }

    private static ColorStateList inflate(@NonNull Resources arrn, @NonNull XmlPullParser arrarrn, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = arrarrn.getDepth() + 1;
        int[][] arrarrn2 = new int[20][];
        int[] arrn2 = new int[arrarrn2.length];
        int n4 = 0;
        while ((n2 = arrarrn.next()) != 1 && ((n = arrarrn.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !arrarrn.getName().equals("item")) continue;
            int[] arrn3 = AppCompatColorStateListInflater.obtainAttributes((Resources)arrn, theme, attributeSet, R.styleable.ColorStateListItem);
            int n5 = arrn3.getColor(R.styleable.ColorStateListItem_android_color, -65281);
            float f = 1.0f;
            if (arrn3.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                f = arrn3.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f);
            } else if (arrn3.hasValue(R.styleable.ColorStateListItem_alpha)) {
                f = arrn3.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f);
            }
            arrn3.recycle();
            int n6 = attributeSet.getAttributeCount();
            arrn3 = new int[n6];
            n = n2 = 0;
            while (n2 < n6) {
                int n7 = attributeSet.getAttributeNameResource(n2);
                int n8 = n;
                if (n7 != 16843173) {
                    n8 = n;
                    if (n7 != 16843551) {
                        n8 = n;
                        if (n7 != R.attr.alpha) {
                            n8 = attributeSet.getAttributeBooleanValue(n2, false) ? n7 : - n7;
                            arrn3[n] = n8;
                            n8 = n + 1;
                        }
                    }
                }
                ++n2;
                n = n8;
            }
            arrn3 = StateSet.trimStateSet((int[])arrn3, (int)n);
            n2 = AppCompatColorStateListInflater.modulateColorAlpha(n5, f);
            if (n4 != 0) {
                n = arrn3.length;
            }
            arrn2 = GrowingArrayUtils.append(arrn2, n4, n2);
            arrarrn2 = GrowingArrayUtils.append(arrarrn2, n4, arrn3);
            ++n4;
        }
        arrn = new int[n4];
        arrarrn = new int[n4][];
        System.arraycopy(arrn2, 0, arrn, 0, n4);
        System.arraycopy(arrarrn2, 0, arrarrn, 0, n4);
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private static int modulateColorAlpha(int n, float f) {
        return ColorUtils.setAlphaComponent(n, Math.round((float)Color.alpha((int)n) * f));
    }

    private static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, arrn);
        }
        return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }
}
