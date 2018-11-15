/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.LayoutTransition
 *  android.util.Log
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.R;
import android.support.transition.ViewGroupOverlayApi14;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtilsImpl;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(value=14)
class ViewGroupUtilsApi14
implements ViewGroupUtilsImpl {
    private static final int LAYOUT_TRANSITION_CHANGING = 4;
    private static final String TAG = "ViewGroupUtilsApi14";
    private static Method sCancelMethod;
    private static boolean sCancelMethodFetched;
    private static LayoutTransition sEmptyLayoutTransition;
    private static Field sLayoutSuppressedField;
    private static boolean sLayoutSuppressedFieldFetched;

    ViewGroupUtilsApi14() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void cancelLayoutTransition(LayoutTransition layoutTransition) {
        if (!sCancelMethodFetched) {
            block6 : {
                try {
                    sCancelMethod = LayoutTransition.class.getDeclaredMethod("cancel", new Class[0]);
                    sCancelMethod.setAccessible(true);
                    break block6;
                }
                catch (NoSuchMethodException noSuchMethodException) {}
                Log.i((String)TAG, (String)"Failed to access cancel method by reflection");
            }
            sCancelMethodFetched = true;
        }
        if (sCancelMethod == null) return;
        try {
            sCancelMethod.invoke((Object)layoutTransition, new Object[0]);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {}
        Log.i((String)TAG, (String)"Failed to access cancel method by reflection");
        return;
        catch (InvocationTargetException invocationTargetException) {}
        Log.i((String)TAG, (String)"Failed to invoke cancel method by reflection");
    }

    @Override
    public ViewGroupOverlayImpl getOverlay(@NonNull ViewGroup viewGroup) {
        return ViewGroupOverlayApi14.createFrom(viewGroup);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void suppressLayout(@NonNull ViewGroup var1_1, boolean var2_2) {
        block15 : {
            block16 : {
                var5_3 = ViewGroupUtilsApi14.sEmptyLayoutTransition;
                var4_7 = false;
                var3_8 = false;
                if (var5_3 == null) {
                    ViewGroupUtilsApi14.sEmptyLayoutTransition = new LayoutTransition(){

                        public boolean isChangingLayout() {
                            return true;
                        }
                    };
                    ViewGroupUtilsApi14.sEmptyLayoutTransition.setAnimator(2, null);
                    ViewGroupUtilsApi14.sEmptyLayoutTransition.setAnimator(0, null);
                    ViewGroupUtilsApi14.sEmptyLayoutTransition.setAnimator(1, null);
                    ViewGroupUtilsApi14.sEmptyLayoutTransition.setAnimator(3, null);
                    ViewGroupUtilsApi14.sEmptyLayoutTransition.setAnimator(4, null);
                }
                if (var2_2) {
                    var5_3 = var1_1.getLayoutTransition();
                    if (var5_3 != null) {
                        if (var5_3.isRunning()) {
                            ViewGroupUtilsApi14.cancelLayoutTransition(var5_3);
                        }
                        if (var5_3 != ViewGroupUtilsApi14.sEmptyLayoutTransition) {
                            var1_1.setTag(R.id.transition_layout_save, (Object)var5_3);
                        }
                    }
                    var1_1.setLayoutTransition(ViewGroupUtilsApi14.sEmptyLayoutTransition);
                    return;
                }
                var1_1.setLayoutTransition(null);
                if (!ViewGroupUtilsApi14.sLayoutSuppressedFieldFetched) {
                    block14 : {
                        try {
                            ViewGroupUtilsApi14.sLayoutSuppressedField = ViewGroup.class.getDeclaredField("mLayoutSuppressed");
                            ViewGroupUtilsApi14.sLayoutSuppressedField.setAccessible(true);
                            break block14;
                        }
                        catch (NoSuchFieldException var5_4) {}
                        Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to access mLayoutSuppressed field by reflection");
                    }
                    ViewGroupUtilsApi14.sLayoutSuppressedFieldFetched = true;
                }
                var2_2 = var4_7;
                if (ViewGroupUtilsApi14.sLayoutSuppressedField == null) break block15;
                try {
                    var2_2 = ViewGroupUtilsApi14.sLayoutSuppressedField.getBoolean((Object)var1_1);
                    ** if (!var2_2) goto lbl-1000
                }
                catch (IllegalAccessException var5_6) {
                    var2_2 = var3_8;
                }
lbl-1000: // 1 sources:
                {
                    ViewGroupUtilsApi14.sLayoutSuppressedField.setBoolean((Object)var1_1, false);
                }
lbl-1000: // 2 sources:
                {
                    break block15;
                }
                break block16;
                catch (IllegalAccessException var5_5) {}
            }
            Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to get mLayoutSuppressed field by reflection");
        }
        if (var2_2) {
            var1_1.requestLayout();
        }
        if ((var5_3 = (LayoutTransition)var1_1.getTag(R.id.transition_layout_save)) == null) return;
        var1_1.setTag(R.id.transition_layout_save, null);
        var1_1.setLayoutTransition(var5_3);
    }

}
