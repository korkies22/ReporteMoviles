/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorSet
 *  android.animation.Keyframe
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TimeInterpolator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.util.Xml
 *  android.view.InflateException
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.AnimatorRes;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.AndroidResources;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.graphics.drawable.ArgbEvaluator;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class AnimatorInflaterCompat {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int MAX_NUM_POINTS = 100;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;

    private static Animator createAnimatorFromXml(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, float f) throws XmlPullParserException, IOException {
        return AnimatorInflaterCompat.createAnimatorFromXml(context, resources, theme, xmlPullParser, Xml.asAttributeSet((XmlPullParser)xmlPullParser), null, 0, f);
    }

    private static Animator createAnimatorFromXml(Context object, Resources object2, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int n, float f) throws XmlPullParserException, IOException {
        int n2;
        ArrayList<PropertyValuesHolder[]> arrayList;
        PropertyValuesHolder[] arrpropertyValuesHolder;
        block12 : {
            int n3 = xmlPullParser.getDepth();
            arrpropertyValuesHolder = null;
            arrayList = null;
            do {
                int n4 = xmlPullParser.next();
                n2 = 0;
                if (n4 == 3 && xmlPullParser.getDepth() <= n3 || n4 == 1) break block12;
                if (n4 != 2) continue;
                PropertyValuesHolder[] arrpropertyValuesHolder2 = xmlPullParser.getName();
                if (arrpropertyValuesHolder2.equals("objectAnimator")) {
                    arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadObjectAnimator((Context)object, (Resources)object2, theme, attributeSet, f, xmlPullParser);
                } else if (arrpropertyValuesHolder2.equals("animator")) {
                    arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadAnimator((Context)object, (Resources)object2, theme, attributeSet, null, f, xmlPullParser);
                } else if (arrpropertyValuesHolder2.equals("set")) {
                    arrpropertyValuesHolder2 = new AnimatorSet();
                    arrpropertyValuesHolder = TypedArrayUtils.obtainAttributes((Resources)object2, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR_SET);
                    n2 = TypedArrayUtils.getNamedInt((TypedArray)arrpropertyValuesHolder, xmlPullParser, "ordering", 0, 0);
                    AnimatorInflaterCompat.createAnimatorFromXml((Context)object, (Resources)object2, theme, xmlPullParser, attributeSet, (AnimatorSet)arrpropertyValuesHolder2, n2, f);
                    arrpropertyValuesHolder.recycle();
                    n2 = 0;
                } else {
                    if (!arrpropertyValuesHolder2.equals("propertyValuesHolder")) break;
                    arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadValues((Context)object, (Resources)object2, theme, xmlPullParser, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
                    if (arrpropertyValuesHolder2 != null && arrpropertyValuesHolder != null && arrpropertyValuesHolder instanceof ValueAnimator) {
                        ((ValueAnimator)arrpropertyValuesHolder).setValues(arrpropertyValuesHolder2);
                    }
                    n2 = 1;
                    arrpropertyValuesHolder2 = arrpropertyValuesHolder;
                }
                arrpropertyValuesHolder = arrpropertyValuesHolder2;
                if (animatorSet == null) continue;
                arrpropertyValuesHolder = arrpropertyValuesHolder2;
                if (n2 != 0) continue;
                ArrayList<PropertyValuesHolder[]> arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<PropertyValuesHolder[]>();
                }
                arrayList2.add(arrpropertyValuesHolder2);
                arrpropertyValuesHolder = arrpropertyValuesHolder2;
                arrayList = arrayList2;
            } while (true);
            object = new StringBuilder();
            object.append("Unknown animator name: ");
            object.append(xmlPullParser.getName());
            throw new RuntimeException(object.toString());
        }
        if (animatorSet != null && arrayList != null) {
            object = new Animator[arrayList.size()];
            object2 = arrayList.iterator();
            n2 = 0;
            while (object2.hasNext()) {
                object[n2] = (Animator)object2.next();
                ++n2;
            }
            if (n == 0) {
                animatorSet.playTogether((Animator[])object);
                return arrpropertyValuesHolder;
            }
            animatorSet.playSequentially((Animator[])object);
        }
        return arrpropertyValuesHolder;
    }

    private static Keyframe createNewKeyframe(Keyframe keyframe, float f) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat((float)f);
        }
        if (keyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt((float)f);
        }
        return Keyframe.ofObject((float)f);
    }

    private static void distributeKeyframes(Keyframe[] arrkeyframe, float f, int n, int n2) {
        f /= (float)(n2 - n + 2);
        while (n <= n2) {
            arrkeyframe[n].setFraction(arrkeyframe[n - 1].getFraction() + f);
            ++n;
        }
    }

    private static void dumpKeyframes(Object[] arrobject, String object) {
        if (arrobject != null) {
            if (arrobject.length == 0) {
                return;
            }
            Log.d((String)TAG, (String)object);
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                Keyframe keyframe = (Keyframe)arrobject[i];
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Keyframe ");
                stringBuilder.append(i);
                stringBuilder.append(": fraction ");
                object = keyframe.getFraction() < 0.0f ? "null" : Float.valueOf(keyframe.getFraction());
                stringBuilder.append(object);
                stringBuilder.append(", ");
                stringBuilder.append(", value : ");
                object = keyframe.hasValue() ? keyframe.getValue() : "null";
                stringBuilder.append(object);
                Log.d((String)TAG, (String)stringBuilder.toString());
            }
            return;
        }
    }

    private static PropertyValuesHolder getPVH(TypedArray object, int n, int n2, int n3, String string) {
        block23 : {
            boolean bl;
            String string2;
            int n4;
            boolean bl2;
            int n5;
            Object object2;
            int n6;
            block21 : {
                PathParser.PathDataNode[] arrpathDataNode;
                PathParser.PathDataNode[] arrpathDataNode2;
                String string3;
                block22 : {
                    object2 = object.peekValue(n2);
                    bl2 = object2 != null;
                    n5 = bl2 ? object2.type : 0;
                    object2 = object.peekValue(n3);
                    bl = object2 != null;
                    n6 = bl ? object2.type : 0;
                    n4 = n;
                    if (n == 4) {
                        n4 = bl2 && AnimatorInflaterCompat.isColorType(n5) || bl && AnimatorInflaterCompat.isColorType(n6) ? 3 : 0;
                    }
                    n = n4 == 0 ? 1 : 0;
                    object2 = null;
                    string2 = null;
                    if (n4 != 2) break block21;
                    string3 = object.getString(n2);
                    string2 = object.getString(n3);
                    arrpathDataNode2 = PathParser.createNodesFromPathData(string3);
                    arrpathDataNode = PathParser.createNodesFromPathData(string2);
                    if (arrpathDataNode2 != null) break block22;
                    object = object2;
                    if (arrpathDataNode == null) break block23;
                }
                if (arrpathDataNode2 != null) {
                    object = new PathDataEvaluator();
                    if (arrpathDataNode != null) {
                        if (!PathParser.canMorph(arrpathDataNode2, arrpathDataNode)) {
                            object = new StringBuilder();
                            object.append(" Can't morph from ");
                            object.append(string3);
                            object.append(" to ");
                            object.append(string2);
                            throw new InflateException(object.toString());
                        }
                        object = PropertyValuesHolder.ofObject((String)string, (TypeEvaluator)object, (Object[])new Object[]{arrpathDataNode2, arrpathDataNode});
                    } else {
                        object = PropertyValuesHolder.ofObject((String)string, (TypeEvaluator)object, (Object[])new Object[]{arrpathDataNode2});
                    }
                    return object;
                }
                object = object2;
                if (arrpathDataNode != null) {
                    return PropertyValuesHolder.ofObject((String)string, (TypeEvaluator)new PathDataEvaluator(), (Object[])new Object[]{arrpathDataNode});
                }
                break block23;
            }
            ArgbEvaluator argbEvaluator = n4 == 3 ? ArgbEvaluator.getInstance() : null;
            if (n != 0) {
                if (bl2) {
                    float f = n5 == 5 ? object.getDimension(n2, 0.0f) : object.getFloat(n2, 0.0f);
                    if (bl) {
                        float f2 = n6 == 5 ? object.getDimension(n3, 0.0f) : object.getFloat(n3, 0.0f);
                        object = PropertyValuesHolder.ofFloat((String)string, (float[])new float[]{f, f2});
                    } else {
                        object = PropertyValuesHolder.ofFloat((String)string, (float[])new float[]{f});
                    }
                } else {
                    float f = n6 == 5 ? object.getDimension(n3, 0.0f) : object.getFloat(n3, 0.0f);
                    object = PropertyValuesHolder.ofFloat((String)string, (float[])new float[]{f});
                }
                object2 = object;
            } else if (bl2) {
                n = n5 == 5 ? (int)object.getDimension(n2, 0.0f) : (AnimatorInflaterCompat.isColorType(n5) ? object.getColor(n2, 0) : object.getInt(n2, 0));
                if (bl) {
                    n2 = n6 == 5 ? (int)object.getDimension(n3, 0.0f) : (AnimatorInflaterCompat.isColorType(n6) ? object.getColor(n3, 0) : object.getInt(n3, 0));
                    object2 = PropertyValuesHolder.ofInt((String)string, (int[])new int[]{n, n2});
                } else {
                    object2 = PropertyValuesHolder.ofInt((String)string, (int[])new int[]{n});
                }
            } else {
                object2 = string2;
                if (bl) {
                    n = n6 == 5 ? (int)object.getDimension(n3, 0.0f) : (AnimatorInflaterCompat.isColorType(n6) ? object.getColor(n3, 0) : object.getInt(n3, 0));
                    object2 = PropertyValuesHolder.ofInt((String)string, (int[])new int[]{n});
                }
            }
            object = object2;
            if (object2 != null) {
                object = object2;
                if (argbEvaluator != null) {
                    object2.setEvaluator((TypeEvaluator)argbEvaluator);
                    object = object2;
                }
            }
        }
        return object;
    }

    private static int inferValueTypeFromValues(TypedArray typedArray, int n, int n2) {
        block3 : {
            block2 : {
                TypedValue typedValue = typedArray.peekValue(n);
                int n3 = 1;
                int n4 = 0;
                n = typedValue != null ? 1 : 0;
                int n5 = n != 0 ? typedValue.type : 0;
                typedArray = typedArray.peekValue(n2);
                n2 = typedArray != null ? n3 : 0;
                n3 = n2 != 0 ? typedArray.type : 0;
                if (n != 0 && AnimatorInflaterCompat.isColorType(n5)) break block2;
                n = n4;
                if (n2 == 0) break block3;
                n = n4;
                if (!AnimatorInflaterCompat.isColorType(n3)) break block3;
            }
            n = 3;
        }
        return n;
    }

    private static int inferValueTypeOfKeyframe(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_KEYFRAME);
        int n = 0;
        theme = TypedArrayUtils.peekNamedValue((TypedArray)resources, xmlPullParser, "value", 0);
        boolean bl = theme != null;
        int n2 = n;
        if (bl) {
            n2 = n;
            if (AnimatorInflaterCompat.isColorType(theme.type)) {
                n2 = 3;
            }
        }
        resources.recycle();
        return n2;
    }

    private static boolean isColorType(int n) {
        if (n >= 28 && n <= 31) {
            return true;
        }
        return false;
    }

    public static Animator loadAnimator(Context context, @AnimatorRes int n) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 24) {
            return AnimatorInflater.loadAnimator((Context)context, (int)n);
        }
        return AnimatorInflaterCompat.loadAnimator(context, context.getResources(), context.getTheme(), n);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes int n) throws Resources.NotFoundException {
        return AnimatorInflaterCompat.loadAnimator(context, resources, theme, n, 1.0f);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Animator loadAnimator(Context var0, Resources var1_4, Resources.Theme var2_11, @AnimatorRes int var3_12, float var4_13) throws Resources.NotFoundException {
        block9 : {
            var7_14 = null;
            var8_15 = null;
            var5_16 = null;
            var6_17 = var1_4.getAnimation(var3_12);
            try {
                var0 = AnimatorInflaterCompat.createAnimatorFromXml(var0, var1_4, (Resources.Theme)var2_11, (XmlPullParser)var6_17, var4_13);
                if (var6_17 == null) return var0;
            }
            catch (Throwable var0_1) {
                var5_16 = var6_17;
                ** GOTO lbl37
            }
            catch (IOException var1_5) {
                var0 = var6_17;
                break block9;
            }
            catch (XmlPullParserException var1_6) {
                var0 = var6_17;
                ** GOTO lbl42
            }
            var6_17.close();
            return var0;
            catch (Throwable var0_2) {
                ** GOTO lbl37
            }
            catch (IOException var1_7) {
                var0 = var7_14;
            }
        }
        var5_16 = var0;
        {
            var2_11 = new StringBuilder();
            var5_16 = var0;
            var2_11.append("Can't load animation resource ID #0x");
            var5_16 = var0;
            var2_11.append(Integer.toHexString(var3_12));
            var5_16 = var0;
            var2_11 = new Resources.NotFoundException(var2_11.toString());
            var5_16 = var0;
            var2_11.initCause((Throwable)var1_8);
            var5_16 = var0;
            throw var2_11;
lbl37: // 2 sources:
            if (var5_16 == null) throw var0_3;
            var5_16.close();
            throw var0_3;
            catch (XmlPullParserException var1_10) {
                var0 = var8_15;
            }
lbl42: // 2 sources:
            var5_16 = var0;
            var2_11 = new StringBuilder();
            var5_16 = var0;
            var2_11.append("Can't load animation resource ID #0x");
            var5_16 = var0;
            var2_11.append(Integer.toHexString(var3_12));
            var5_16 = var0;
            var2_11 = new Resources.NotFoundException(var2_11.toString());
            var5_16 = var0;
            var2_11.initCause((Throwable)var1_9);
            var5_16 = var0;
            throw var2_11;
        }
    }

    private static ValueAnimator loadAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR);
        theme = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        resources = valueAnimator;
        if (valueAnimator == null) {
            resources = new ValueAnimator();
        }
        AnimatorInflaterCompat.parseAnimatorFromTypeArray((ValueAnimator)resources, typedArray, (TypedArray)theme, f, xmlPullParser);
        int n = TypedArrayUtils.getNamedResourceId(typedArray, xmlPullParser, "interpolator", 0, 0);
        if (n > 0) {
            resources.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(context, n));
        }
        typedArray.recycle();
        if (theme != null) {
            theme.recycle();
        }
        return resources;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Keyframe loadKeyframe(Context var0, Resources var1_1, Resources.Theme var2_2, AttributeSet var3_3, int var4_4, XmlPullParser var5_5) throws XmlPullParserException, IOException {
        var2_2 = TypedArrayUtils.obtainAttributes(var1_1, var2_2, var3_3, AndroidResources.STYLEABLE_KEYFRAME);
        var6_6 = TypedArrayUtils.getNamedFloat((TypedArray)var2_2, var5_5, "fraction", 3, -1.0f);
        var1_1 = TypedArrayUtils.peekNamedValue((TypedArray)var2_2, var5_5, "value", 0);
        var8_7 = var1_1 != null;
        var7_8 = var4_4;
        if (var4_4 == 4) {
            var7_8 = var8_7 != false && AnimatorInflaterCompat.isColorType(var1_1.type) != false ? 3 : 0;
        }
        if (var8_7) {
            if (var7_8 != 3) {
                switch (var7_8) {
                    default: {
                        var1_1 = null;
                        ** break;
                    }
                    case 0: {
                        var1_1 = Keyframe.ofFloat((float)var6_6, (float)TypedArrayUtils.getNamedFloat((TypedArray)var2_2, var5_5, "value", 0, 0.0f));
                        ** break;
                    }
                    case 1: 
                }
            }
            var1_1 = Keyframe.ofInt((float)var6_6, (int)TypedArrayUtils.getNamedInt((TypedArray)var2_2, var5_5, "value", 0, 0));
            ** break;
lbl20: // 3 sources:
        } else {
            var1_1 = var7_8 == 0 ? Keyframe.ofFloat((float)var6_6) : Keyframe.ofInt((float)var6_6);
        }
        var4_4 = TypedArrayUtils.getNamedResourceId((TypedArray)var2_2, var5_5, "interpolator", 1, 0);
        if (var4_4 > 0) {
            var1_1.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(var0, var4_4));
        }
        var2_2.recycle();
        return var1_1;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        AnimatorInflaterCompat.loadAnimator(context, resources, theme, attributeSet, (ValueAnimator)objectAnimator, f, xmlPullParser);
        return objectAnimator;
    }

    private static PropertyValuesHolder loadPvh(Context resources, Resources resources2, Resources.Theme theme, XmlPullParser xmlPullParser, String string, int n) throws XmlPullParserException, IOException {
        Object var14_6 = null;
        ArrayList<Keyframe> arrayList = null;
        int n2 = n;
        while ((n = xmlPullParser.next()) != 3 && n != 1) {
            if (!xmlPullParser.getName().equals("keyframe")) continue;
            n = n2;
            if (n2 == 4) {
                n = AnimatorInflaterCompat.inferValueTypeOfKeyframe(resources2, theme, Xml.asAttributeSet((XmlPullParser)xmlPullParser), xmlPullParser);
            }
            Keyframe keyframe = AnimatorInflaterCompat.loadKeyframe((Context)resources, resources2, theme, Xml.asAttributeSet((XmlPullParser)xmlPullParser), n, xmlPullParser);
            ArrayList<Keyframe> arrayList2 = arrayList;
            if (keyframe != null) {
                arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<Keyframe>();
                }
                arrayList2.add(keyframe);
            }
            xmlPullParser.next();
            n2 = n;
            arrayList = arrayList2;
        }
        resources = var14_6;
        if (arrayList != null) {
            int n3 = arrayList.size();
            resources = var14_6;
            if (n3 > 0) {
                int n4 = 0;
                resources = (Keyframe)arrayList.get(0);
                resources2 = (Keyframe)arrayList.get(n3 - 1);
                float f = resources2.getFraction();
                n = n3;
                if (f < 1.0f) {
                    if (f < 0.0f) {
                        resources2.setFraction(1.0f);
                        n = n3;
                    } else {
                        arrayList.add(arrayList.size(), AnimatorInflaterCompat.createNewKeyframe((Keyframe)resources2, 1.0f));
                        n = n3 + 1;
                    }
                }
                f = resources.getFraction();
                n3 = n;
                if (f != 0.0f) {
                    if (f < 0.0f) {
                        resources.setFraction(0.0f);
                        n3 = n;
                    } else {
                        arrayList.add(0, AnimatorInflaterCompat.createNewKeyframe((Keyframe)resources, 0.0f));
                        n3 = n + 1;
                    }
                }
                resources = new Keyframe[n3];
                arrayList.toArray((T[])resources);
                for (n = n4; n < n3; ++n) {
                    resources2 = resources[n];
                    if (resources2.getFraction() >= 0.0f) continue;
                    if (n == 0) {
                        resources2.setFraction(0.0f);
                        continue;
                    }
                    int n5 = n3 - 1;
                    if (n == n5) {
                        resources2.setFraction(1.0f);
                        continue;
                    }
                    n4 = n + 1;
                    int n6 = n;
                    while (n4 < n5 && resources[n4].getFraction() < 0.0f) {
                        n6 = n4++;
                    }
                    AnimatorInflaterCompat.distributeKeyframes((Keyframe[])resources, resources[n6 + 1].getFraction() - resources[n - 1].getFraction(), n, n6);
                }
                resources = resources2 = PropertyValuesHolder.ofKeyframe((String)string, (Keyframe[])resources);
                if (n2 == 3) {
                    resources2.setEvaluator((TypeEvaluator)ArgbEvaluator.getInstance());
                    resources = resources2;
                }
            }
        }
        return resources;
    }

    private static PropertyValuesHolder[] loadValues(Context arrpropertyValuesHolder, Resources arrpropertyValuesHolder2, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        int n2;
        Object var10_5 = null;
        ArrayList arrayList = null;
        do {
            n2 = xmlPullParser.getEventType();
            n = 0;
            if (n2 == 3 || n2 == 1) break;
            if (n2 != 2) {
                xmlPullParser.next();
                continue;
            }
            if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                TypedArray typedArray = TypedArrayUtils.obtainAttributes((Resources)arrpropertyValuesHolder2, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                String string = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyName", 3);
                n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 2, 4);
                Object object = AnimatorInflaterCompat.loadPvh((Context)arrpropertyValuesHolder, (Resources)arrpropertyValuesHolder2, theme, xmlPullParser, string, n);
                PropertyValuesHolder propertyValuesHolder = object;
                if (object == null) {
                    propertyValuesHolder = AnimatorInflaterCompat.getPVH(typedArray, n, 0, 1, string);
                }
                object = arrayList;
                if (propertyValuesHolder != null) {
                    object = arrayList;
                    if (arrayList == null) {
                        object = new ArrayList();
                    }
                    object.add(propertyValuesHolder);
                }
                typedArray.recycle();
                arrayList = object;
            }
            xmlPullParser.next();
        } while (true);
        arrpropertyValuesHolder = var10_5;
        if (arrayList != null) {
            n2 = arrayList.size();
            arrpropertyValuesHolder2 = new PropertyValuesHolder[n2];
            do {
                arrpropertyValuesHolder = arrpropertyValuesHolder2;
                if (n >= n2) break;
                arrpropertyValuesHolder2[n] = (PropertyValuesHolder)arrayList.get(n);
                ++n;
            } while (true);
        }
        return arrpropertyValuesHolder;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, float f, XmlPullParser xmlPullParser) {
        int n;
        long l = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "duration", 1, 300);
        long l2 = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "startOffset", 2, 0);
        int n2 = n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueFrom")) {
            n2 = n;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueTo")) {
                int n3 = n;
                if (n == 4) {
                    n3 = AnimatorInflaterCompat.inferValueTypeFromValues(typedArray, 5, 6);
                }
                PropertyValuesHolder propertyValuesHolder = AnimatorInflaterCompat.getPVH(typedArray, n3, 5, 6, "");
                n2 = n3;
                if (propertyValuesHolder != null) {
                    valueAnimator.setValues(new PropertyValuesHolder[]{propertyValuesHolder});
                    n2 = n3;
                }
            }
        }
        valueAnimator.setDuration(l);
        valueAnimator.setStartDelay(l2);
        valueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatCount", 3, 0));
        valueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatMode", 4, 1));
        if (typedArray2 != null) {
            AnimatorInflaterCompat.setupObjectAnimator(valueAnimator, typedArray2, n2, f, xmlPullParser);
        }
    }

    private static void setupObjectAnimator(ValueAnimator object, TypedArray typedArray, int n, float f, XmlPullParser object2) {
        object = (ObjectAnimator)object;
        String string = TypedArrayUtils.getNamedString(typedArray, object2, "pathData", 1);
        if (string != null) {
            String string2 = TypedArrayUtils.getNamedString(typedArray, object2, "propertyXName", 2);
            object2 = TypedArrayUtils.getNamedString(typedArray, object2, "propertyYName", 3);
            if (n != 2) {
                // empty if block
            }
            if (string2 == null && object2 == null) {
                object = new StringBuilder();
                object.append(typedArray.getPositionDescription());
                object.append(" propertyXName or propertyYName is needed for PathData");
                throw new InflateException(object.toString());
            }
            AnimatorInflaterCompat.setupPathMotion(PathParser.createPathFromPathData(string), (ObjectAnimator)object, 0.5f * f, string2, (String)object2);
            return;
        }
        object.setPropertyName(TypedArrayUtils.getNamedString(typedArray, object2, "propertyName", 0));
    }

    private static void setupPathMotion(Path object, ObjectAnimator objectAnimator, float f, String string, String string2) {
        float f2;
        PathMeasure pathMeasure = new PathMeasure(object, false);
        ArrayList<Float> arrayList = new ArrayList<Float>();
        arrayList.add(Float.valueOf(0.0f));
        float f3 = 0.0f;
        do {
            f2 = f3 + pathMeasure.getLength();
            arrayList.add(Float.valueOf(f2));
            f3 = f2;
        } while (pathMeasure.nextContour());
        object = new PathMeasure(object, false);
        int n = Math.min(100, (int)(f2 / f) + 1);
        float[] arrf = new float[n];
        float[] arrf2 = new float[n];
        float[] arrf3 = new float[2];
        f2 /= (float)(n - 1);
        int n2 = 0;
        f = 0.0f;
        int n3 = n2;
        do {
            pathMeasure = null;
            if (n3 >= n) break;
            object.getPosTan(f, arrf3, null);
            arrf[n3] = arrf3[0];
            arrf2[n3] = arrf3[1];
            f3 = f + f2;
            int n4 = n2 + 1;
            f = f3;
            int n5 = n2;
            if (n4 < arrayList.size()) {
                f = f3;
                n5 = n2;
                if (f3 > ((Float)arrayList.get(n4)).floatValue()) {
                    f = f3 - ((Float)arrayList.get(n4)).floatValue();
                    object.nextContour();
                    n5 = n4;
                }
            }
            ++n3;
            n2 = n5;
        } while (true);
        object = string != null ? PropertyValuesHolder.ofFloat((String)string, (float[])arrf) : null;
        string = pathMeasure;
        if (string2 != null) {
            string = PropertyValuesHolder.ofFloat((String)string2, (float[])arrf2);
        }
        if (object == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{string});
            return;
        }
        if (string == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{object});
            return;
        }
        objectAnimator.setValues(new PropertyValuesHolder[]{object, string});
    }

    private static class PathDataEvaluator
    implements TypeEvaluator<PathParser.PathDataNode[]> {
        private PathParser.PathDataNode[] mNodeArray;

        private PathDataEvaluator() {
        }

        PathDataEvaluator(PathParser.PathDataNode[] arrpathDataNode) {
            this.mNodeArray = arrpathDataNode;
        }

        public PathParser.PathDataNode[] evaluate(float f, PathParser.PathDataNode[] arrpathDataNode, PathParser.PathDataNode[] arrpathDataNode2) {
            if (!PathParser.canMorph(arrpathDataNode, arrpathDataNode2)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            if (this.mNodeArray == null || !PathParser.canMorph(this.mNodeArray, arrpathDataNode)) {
                this.mNodeArray = PathParser.deepCopyNodes(arrpathDataNode);
            }
            for (int i = 0; i < arrpathDataNode.length; ++i) {
                this.mNodeArray[i].interpolatePathDataNode(arrpathDataNode[i], arrpathDataNode2[i], f);
            }
            return this.mNodeArray;
        }
    }

}
