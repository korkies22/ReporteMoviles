/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Xml
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnimationUtils
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.AnticipateOvershootInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.CycleInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.OvershootInterpolator
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class AnimationUtilsCompat {
    private static Interpolator createInterpolatorFromXml(Context object, Resources object2, Resources.Theme object3, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        object2 = null;
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            object2 = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
            object3 = xmlPullParser.getName();
            if (object3.equals("linearInterpolator")) {
                object2 = new LinearInterpolator();
                continue;
            }
            if (object3.equals("accelerateInterpolator")) {
                object2 = new AccelerateInterpolator((Context)object, (AttributeSet)object2);
                continue;
            }
            if (object3.equals("decelerateInterpolator")) {
                object2 = new DecelerateInterpolator((Context)object, (AttributeSet)object2);
                continue;
            }
            if (object3.equals("accelerateDecelerateInterpolator")) {
                object2 = new AccelerateDecelerateInterpolator();
                continue;
            }
            if (object3.equals("cycleInterpolator")) {
                object2 = new CycleInterpolator((Context)object, (AttributeSet)object2);
                continue;
            }
            if (object3.equals("anticipateInterpolator")) {
                object2 = new AnticipateInterpolator((Context)object, (AttributeSet)object2);
                continue;
            }
            if (object3.equals("overshootInterpolator")) {
                object2 = new OvershootInterpolator((Context)object, (AttributeSet)object2);
                continue;
            }
            if (object3.equals("anticipateOvershootInterpolator")) {
                object2 = new AnticipateOvershootInterpolator((Context)object, (AttributeSet)object2);
                continue;
            }
            if (object3.equals("bounceInterpolator")) {
                object2 = new BounceInterpolator();
                continue;
            }
            if (object3.equals("pathInterpolator")) {
                object2 = new PathInterpolatorCompat((Context)object, (AttributeSet)object2, xmlPullParser);
                continue;
            }
            object = new StringBuilder();
            object.append("Unknown interpolator name: ");
            object.append(xmlPullParser.getName());
            throw new RuntimeException(object.toString());
        }
        return object2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Interpolator loadInterpolator(Context var0, int var1_4) throws Resources.NotFoundException {
        block13 : {
            if (Build.VERSION.SDK_INT >= 21) {
                return AnimationUtils.loadInterpolator((Context)var0, (int)var1_4);
            }
            var5_5 = null;
            var2_6 = null;
            var4_9 = null;
            if (var1_4 != 17563663) ** GOTO lbl9
            return new FastOutLinearInInterpolator();
lbl9: // 1 sources:
            if (var1_4 == 17563661) {
                return new FastOutSlowInInterpolator();
            }
            if (var1_4 == 17563662) {
                return new LinearOutSlowInInterpolator();
            }
            var3_10 = var0.getResources().getAnimation(var1_4);
            try {
                var0 = AnimationUtilsCompat.createInterpolatorFromXml(var0, var0.getResources(), var0.getTheme(), (XmlPullParser)var3_10);
                if (var3_10 == null) return var0;
            }
            catch (Throwable var0_1) {
                var2_6 = var3_10;
                ** GOTO lbl45
            }
            catch (IOException var2_7) {
                var0 = var3_10;
                var3_11 = var2_7;
            }
            catch (XmlPullParserException var2_8) {
                var0 = var3_10;
                var3_12 = var2_8;
                break block13;
            }
            var3_10.close();
            return var0;
lbl30: // 2 sources:
            do {
                var2_6 = var0;
                try {
                    var4_9 = new StringBuilder();
                    var2_6 = var0;
                    var4_9.append("Can't load animation resource ID #0x");
                    var2_6 = var0;
                    var4_9.append(Integer.toHexString(var1_4));
                    var2_6 = var0;
                    var4_9 = new Resources.NotFoundException(var4_9.toString());
                    var2_6 = var0;
                    var4_9.initCause((Throwable)var3_13);
                    var2_6 = var0;
                    throw var4_9;
                }
                catch (Throwable var0_3) {}
lbl45: // 2 sources:
                if (var2_6 == null) throw var0_2;
                var2_6.close();
                throw var0_2;
                break;
            } while (true);
            catch (IOException var3_15) {
                var0 = var4_9;
                ** continue;
            }
            catch (XmlPullParserException var3_16) {
                var0 = var5_5;
            }
        }
        var2_6 = var0;
        var4_9 = new StringBuilder();
        var2_6 = var0;
        var4_9.append("Can't load animation resource ID #0x");
        var2_6 = var0;
        var4_9.append(Integer.toHexString(var1_4));
        var2_6 = var0;
        var4_9 = new Resources.NotFoundException(var4_9.toString());
        var2_6 = var0;
        var4_9.initCause((Throwable)var3_14);
        var2_6 = var0;
        throw var4_9;
    }
}
