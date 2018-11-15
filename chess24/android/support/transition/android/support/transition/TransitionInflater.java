/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.InflateException
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.transition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.transition.ArcMotion;
import android.support.transition.AutoTransition;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeClipBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeScroll;
import android.support.transition.ChangeTransform;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.PathMotion;
import android.support.transition.PatternPathMotion;
import android.support.transition.Scene;
import android.support.transition.Slide;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.ViewGroup;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final ArrayMap<String, Constructor> CONSTRUCTORS;
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE;
    private final Context mContext;

    static {
        CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
        CONSTRUCTORS = new ArrayMap();
    }

    private TransitionInflater(@NonNull Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Object createCustom(AttributeSet object, Class class_, String constructor) {
        String string = object.getAttributeValue(null, "class");
        if (string == null) {
            object = new StringBuilder();
            object.append((String)((Object)constructor));
            object.append(" tag must have a 'class' attribute");
            throw new InflateException(object.toString());
        }
        try {
            ArrayMap<String, Constructor> arrayMap = CONSTRUCTORS;
            // MONITORENTER : arrayMap
        }
        catch (Exception exception) {
            constructor = new StringBuilder();
            constructor.append("Could not instantiate ");
            constructor.append(class_);
            constructor.append(" class ");
            constructor.append(string);
            throw new InflateException(((StringBuilder)((Object)constructor)).toString(), (Throwable)exception);
        }
        Constructor constructor2 = (Constructor)CONSTRUCTORS.get(string);
        constructor = constructor2;
        if (constructor2 == null) {
            Class class_2 = this.mContext.getClassLoader().loadClass(string).asSubclass(class_);
            constructor = constructor2;
            if (class_2 != null) {
                constructor = class_2.getConstructor(CONSTRUCTOR_SIGNATURE);
                constructor.setAccessible(true);
                CONSTRUCTORS.put(string, constructor);
            }
        }
        object = constructor.newInstance(new Object[]{this.mContext, object});
        // MONITOREXIT : arrayMap
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Transition createTransitionFromXml(XmlPullParser var1_1, AttributeSet var2_2, Transition var3_3) throws XmlPullParserException, IOException {
        var4_4 = var1_1.getDepth();
        var7_5 = var3_3 instanceof TransitionSet != false ? (TransitionSet)var3_3 : null;
        block0 : do {
            var8_8 = null;
            do lbl-1000: // 4 sources:
            {
                if ((var5_6 = var1_1.next()) == 3) {
                    if (var1_1.getDepth() <= var4_4) return var8_8;
                }
                if (var5_6 == 1) return var8_8;
                if (var5_6 != 2) ** GOTO lbl-1000
                var6_7 = var1_1.getName();
                if ("fade".equals(var6_7)) {
                    var6_7 = new Fade(this.mContext, (AttributeSet)var2_2);
                } else if ("changeBounds".equals(var6_7)) {
                    var6_7 = new ChangeBounds(this.mContext, (AttributeSet)var2_2);
                } else if ("slide".equals(var6_7)) {
                    var6_7 = new Slide(this.mContext, (AttributeSet)var2_2);
                } else if ("explode".equals(var6_7)) {
                    var6_7 = new Explode(this.mContext, (AttributeSet)var2_2);
                } else if ("changeImageTransform".equals(var6_7)) {
                    var6_7 = new ChangeImageTransform(this.mContext, (AttributeSet)var2_2);
                } else if ("changeTransform".equals(var6_7)) {
                    var6_7 = new ChangeTransform(this.mContext, (AttributeSet)var2_2);
                } else if ("changeClipBounds".equals(var6_7)) {
                    var6_7 = new ChangeClipBounds(this.mContext, (AttributeSet)var2_2);
                } else if ("autoTransition".equals(var6_7)) {
                    var6_7 = new AutoTransition(this.mContext, (AttributeSet)var2_2);
                } else if ("changeScroll".equals(var6_7)) {
                    var6_7 = new ChangeScroll(this.mContext, (AttributeSet)var2_2);
                } else if ("transitionSet".equals(var6_7)) {
                    var6_7 = new TransitionSet(this.mContext, (AttributeSet)var2_2);
                } else if ("transition".equals(var6_7)) {
                    var6_7 = (Transition)this.createCustom((AttributeSet)var2_2, Transition.class, "transition");
                } else if ("targets".equals(var6_7)) {
                    this.getTargetIds(var1_1, (AttributeSet)var2_2, var3_3);
                    var6_7 = var8_8;
                } else if ("arcMotion".equals(var6_7)) {
                    if (var3_3 == null) {
                        throw new RuntimeException("Invalid use of arcMotion element");
                    }
                    var3_3.setPathMotion(new ArcMotion(this.mContext, (AttributeSet)var2_2));
                    var6_7 = var8_8;
                } else if ("pathMotion".equals(var6_7)) {
                    if (var3_3 == null) {
                        throw new RuntimeException("Invalid use of pathMotion element");
                    }
                    var3_3.setPathMotion((PathMotion)this.createCustom((AttributeSet)var2_2, PathMotion.class, "pathMotion"));
                    var6_7 = var8_8;
                } else {
                    if (!"patternPathMotion".equals(var6_7)) {
                        var2_2 = new StringBuilder();
                        var2_2.append("Unknown scene name: ");
                        var2_2.append(var1_1.getName());
                        throw new RuntimeException(var2_2.toString());
                    }
                    if (var3_3 == null) {
                        throw new RuntimeException("Invalid use of patternPathMotion element");
                    }
                    var3_3.setPathMotion(new PatternPathMotion(this.mContext, (AttributeSet)var2_2));
                    var6_7 = var8_8;
                }
                var8_8 = var6_7;
                if (var6_7 == null) ** GOTO lbl-1000
                if (!var1_1.isEmptyElementTag()) {
                    this.createTransitionFromXml(var1_1, (AttributeSet)var2_2, (Transition)var6_7);
                }
                if (var7_5 != null) {
                    var7_5.addTransition((Transition)var6_7);
                    continue block0;
                }
                var8_8 = var6_7;
            } while (var3_3 == null);
            break;
        } while (true);
        throw new InflateException("Could not add transition to another transition.");
    }

    private TransitionManager createTransitionManagerFromXml(XmlPullParser xmlPullParser, AttributeSet object, ViewGroup viewGroup) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        TransitionManager transitionManager = null;
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            String string = xmlPullParser.getName();
            if (string.equals("transitionManager")) {
                transitionManager = new TransitionManager();
                continue;
            }
            if (string.equals("transition") && transitionManager != null) {
                this.loadTransition((AttributeSet)object, xmlPullParser, viewGroup, transitionManager);
                continue;
            }
            object = new StringBuilder();
            object.append("Unknown scene name: ");
            object.append(xmlPullParser.getName());
            throw new RuntimeException(object.toString());
        }
        return transitionManager;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void getTargetIds(XmlPullParser var1_1, AttributeSet var2_2, Transition var3_6) throws XmlPullParserException, IOException {
        block8 : {
            var4_10 = var1_1 /* !! */ .getDepth();
            do {
                block7 : {
                    block12 : {
                        block11 : {
                            block10 : {
                                block9 : {
                                    if ((var5_11 = var1_1 /* !! */ .next()) == 3) {
                                        if (var1_1 /* !! */ .getDepth() <= var4_10) return;
                                    }
                                    if (var5_11 == 1) return;
                                    if (var5_11 != 2) continue;
                                    if (!var1_1 /* !! */ .getName().equals("target")) {
                                        var2_5 = new StringBuilder();
                                        var2_5.append("Unknown scene name: ");
                                        var2_5.append(var1_1 /* !! */ .getName());
                                        throw new RuntimeException(var2_5.toString());
                                    }
                                    var8_14 = this.mContext.obtainStyledAttributes((AttributeSet)var2_5, Styleable.TRANSITION_TARGET);
                                    var5_11 = TypedArrayUtils.getNamedResourceId(var8_14, var1_1 /* !! */ , "targetId", 1, 0);
                                    if (var5_11 == 0) break block9;
                                    var3_9.addTarget(var5_11);
                                    break block7;
                                }
                                var5_11 = TypedArrayUtils.getNamedResourceId(var8_14, var1_1 /* !! */ , "excludeId", 2, 0);
                                if (var5_11 == 0) break block10;
                                var3_9.excludeTarget(var5_11, true);
                                break block7;
                            }
                            var6_12 = TypedArrayUtils.getNamedString(var8_14, var1_1 /* !! */ , "targetName", 4);
                            if (var6_12 == null) break block11;
                            var3_9.addTarget(var6_12);
                            break block7;
                        }
                        var6_12 = TypedArrayUtils.getNamedString(var8_14, var1_1 /* !! */ , "excludeName", 5);
                        if (var6_12 == null) break block12;
                        var3_9.excludeTarget(var6_12, true);
                        break block7;
                    }
                    var6_12 = TypedArrayUtils.getNamedString(var8_14, var1_1 /* !! */ , "excludeClass", 3);
                    if (var6_12 == null) ** GOTO lbl38
                    var3_9.excludeTarget(Class.forName(var6_12), true);
                    break block7;
lbl38: // 1 sources:
                    var7_13 = TypedArrayUtils.getNamedString(var8_14, var1_1 /* !! */ , "targetClass", 0);
                    if (var7_13 == null) break block7;
                    try {
                        var3_9.addTarget(Class.forName(var7_13));
                    }
                    catch (ClassNotFoundException var2_6) {
                        var1_2 = var7_13;
                        break block8;
                    }
                }
                var8_14.recycle();
            } while (true);
            catch (ClassNotFoundException var2_8) {
                var1_4 = var6_12;
            }
        }
        var8_14.recycle();
        var3_9 = new StringBuilder();
        var3_9.append("Could not create ");
        var3_9.append((String)var1_3);
        throw new RuntimeException(var3_9.toString(), (Throwable)var2_7);
    }

    private void loadTransition(AttributeSet object, XmlPullParser object2, ViewGroup object3, TransitionManager transitionManager) throws Resources.NotFoundException {
        TypedArray typedArray = this.mContext.obtainStyledAttributes((AttributeSet)object, Styleable.TRANSITION_MANAGER);
        int n = TypedArrayUtils.getNamedResourceId(typedArray, object2, "transition", 2, -1);
        int n2 = TypedArrayUtils.getNamedResourceId(typedArray, object2, "fromScene", 0, -1);
        Object var7_8 = null;
        object = n2 < 0 ? null : Scene.getSceneForLayout(object3, n2, this.mContext);
        n2 = TypedArrayUtils.getNamedResourceId(typedArray, object2, "toScene", 1, -1);
        object2 = n2 < 0 ? var7_8 : Scene.getSceneForLayout(object3, n2, this.mContext);
        if (n >= 0 && (object3 = this.inflateTransition(n)) != null) {
            if (object2 == null) {
                object = new StringBuilder();
                object.append("No toScene for transition ID ");
                object.append(n);
                throw new RuntimeException(object.toString());
            }
            if (object == null) {
                transitionManager.setTransition((Scene)object2, (Transition)object3);
            } else {
                transitionManager.setTransition((Scene)object, (Scene)object2, (Transition)object3);
            }
        }
        typedArray.recycle();
    }

    /*
     * Exception decompiling
     */
    public Transition inflateTransition(int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public TransitionManager inflateTransitionManager(int var1_1, ViewGroup var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }
}
