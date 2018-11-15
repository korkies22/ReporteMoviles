/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList;
    private static final Map<String, Constructor<? extends View>> sConstructorMap;
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs = new Object[2];

    static {
        sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sOnClickAttrs = new int[]{16843375};
        sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
        sConstructorMap = new ArrayMap<String, Constructor<? extends View>>();
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Object object = view.getContext();
        if (object instanceof ContextWrapper) {
            if (Build.VERSION.SDK_INT >= 15 && !ViewCompat.hasOnClickListeners(view)) {
                return;
            }
            attributeSet = object.obtainStyledAttributes(attributeSet, sOnClickAttrs);
            object = attributeSet.getString(0);
            if (object != null) {
                view.setOnClickListener((View.OnClickListener)new DeclaredOnClickListener(view, (String)object));
            }
            attributeSet.recycle();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private View createViewByPrefix(Context var1_1, String var2_3, String var3_4) throws ClassNotFoundException, InflateException {
        var5_5 = AppCompatViewInflater.sConstructorMap.get(var2_3);
        var4_6 = var5_5;
        if (var5_5 != null) ** GOTO lbl15
        try {
            var4_6 = var1_1.getClassLoader();
            if (var3_4 != null) {
                var1_1 = new StringBuilder();
                var1_1.append(var3_4);
                var1_1.append(var2_3);
                var1_1 = var1_1.toString();
            } else {
                var1_1 = var2_3;
            }
            var4_6 = var4_6.loadClass((String)var1_1).asSubclass(View.class).getConstructor(AppCompatViewInflater.sConstructorSignature);
            AppCompatViewInflater.sConstructorMap.put(var2_3, var4_6);
lbl15: // 2 sources:
            var4_6.setAccessible(true);
            return (View)var4_6.newInstance(this.mConstructorArgs);
        }
        catch (Exception var1_2) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private View createViewFromTag(Context context, String string, AttributeSet attributeSet) {
        String string2;
        block7 : {
            string2 = string;
            if (string.equals("view")) {
                string2 = attributeSet.getAttributeValue(null, "class");
            }
            try {
                this.mConstructorArgs[0] = context;
                this.mConstructorArgs[1] = attributeSet;
                if (-1 != string2.indexOf(46)) {
                    context = this.createViewByPrefix(context, string2, null);
                    this.mConstructorArgs[0] = null;
                    this.mConstructorArgs[1] = null;
                    return context;
                }
                break block7;
            }
            catch (Throwable throwable) {
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                throw throwable;
            }
            catch (Exception exception) {}
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return null;
        }
        for (int i = 0; i < sClassPrefixList.length; ++i) {
            string = this.createViewByPrefix(context, string2, sClassPrefixList[i]);
            if (string == null) continue;
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return string;
        }
        this.mConstructorArgs[0] = null;
        this.mConstructorArgs[1] = null;
        return null;
    }

    private static Context themifyContext(Context context, AttributeSet object, boolean bl, boolean bl2) {
        block6 : {
            int n;
            block7 : {
                object = context.obtainStyledAttributes(object, R.styleable.View, 0, 0);
                int n2 = bl ? object.getResourceId(R.styleable.View_android_theme, 0) : 0;
                n = n2;
                if (bl2) {
                    n = n2;
                    if (n2 == 0) {
                        n = n2 = object.getResourceId(R.styleable.View_theme, 0);
                        if (n2 != 0) {
                            Log.i((String)LOG_TAG, (String)"app:theme is now deprecated. Please move to using android:theme instead.");
                            n = n2;
                        }
                    }
                }
                object.recycle();
                object = context;
                if (n == 0) break block6;
                if (!(context instanceof ContextThemeWrapper)) break block7;
                object = context;
                if (((ContextThemeWrapper)context).getThemeResId() == n) break block6;
            }
            object = new ContextThemeWrapper(context, n);
        }
        return object;
    }

    private void verifyNotNull(View object, String string) {
        if (object == null) {
            object = new StringBuilder();
            object.append(this.getClass().getName());
            object.append(" asked to inflate view for <");
            object.append(string);
            object.append(">, but returned null");
            throw new IllegalStateException(object.toString());
        }
    }

    @NonNull
    protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatButton createButton(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    @NonNull
    protected AppCompatCheckBox createCheckBox(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    @NonNull
    protected AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckedTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatEditText createEditText(Context context, AttributeSet attributeSet) {
        return new AppCompatEditText(context, attributeSet);
    }

    @NonNull
    protected AppCompatImageButton createImageButton(Context context, AttributeSet attributeSet) {
        return new AppCompatImageButton(context, attributeSet);
    }

    @NonNull
    protected AppCompatImageView createImageView(Context context, AttributeSet attributeSet) {
        return new AppCompatImageView(context, attributeSet);
    }

    @NonNull
    protected AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatMultiAutoCompleteTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatRadioButton createRadioButton(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    @NonNull
    protected AppCompatRatingBar createRatingBar(Context context, AttributeSet attributeSet) {
        return new AppCompatRatingBar(context, attributeSet);
    }

    @NonNull
    protected AppCompatSeekBar createSeekBar(Context context, AttributeSet attributeSet) {
        return new AppCompatSeekBar(context, attributeSet);
    }

    @NonNull
    protected AppCompatSpinner createSpinner(Context context, AttributeSet attributeSet) {
        return new AppCompatSpinner(context, attributeSet);
    }

    @NonNull
    protected AppCompatTextView createTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    @Nullable
    protected View createView(Context context, String string, AttributeSet attributeSet) {
        return null;
    }

    final View createView(View object, String string, @NonNull Context context, @NonNull AttributeSet attributeSet, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        Context context2;
        block38 : {
            block37 : {
                context2 = bl && object != null ? object.getContext() : context;
                if (bl2) break block37;
                object = context2;
                if (!bl3) break block38;
            }
            object = AppCompatViewInflater.themifyContext(context2, attributeSet, bl2, bl3);
        }
        context2 = object;
        if (bl4) {
            context2 = TintContextWrapper.wrap((Context)object);
        }
        int n = -1;
        switch (string.hashCode()) {
            default: {
                break;
            }
            case 2001146706: {
                if (!string.equals("Button")) break;
                n = 2;
                break;
            }
            case 1666676343: {
                if (!string.equals("EditText")) break;
                n = 3;
                break;
            }
            case 1601505219: {
                if (!string.equals("CheckBox")) break;
                n = 6;
                break;
            }
            case 1413872058: {
                if (!string.equals("AutoCompleteTextView")) break;
                n = 9;
                break;
            }
            case 1125864064: {
                if (!string.equals("ImageView")) break;
                n = 1;
                break;
            }
            case 776382189: {
                if (!string.equals("RadioButton")) break;
                n = 7;
                break;
            }
            case -339785223: {
                if (!string.equals("Spinner")) break;
                n = 4;
                break;
            }
            case -658531749: {
                if (!string.equals("SeekBar")) break;
                n = 12;
                break;
            }
            case -937446323: {
                if (!string.equals("ImageButton")) break;
                n = 5;
                break;
            }
            case -938935918: {
                if (!string.equals("TextView")) break;
                n = 0;
                break;
            }
            case -1346021293: {
                if (!string.equals("MultiAutoCompleteTextView")) break;
                n = 10;
                break;
            }
            case -1455429095: {
                if (!string.equals("CheckedTextView")) break;
                n = 8;
                break;
            }
            case -1946472170: {
                if (!string.equals("RatingBar")) break;
                n = 11;
            }
        }
        switch (n) {
            default: {
                object = this.createView(context2, string, attributeSet);
                break;
            }
            case 12: {
                object = this.createSeekBar(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 11: {
                object = this.createRatingBar(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 10: {
                object = this.createMultiAutoCompleteTextView(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 9: {
                object = this.createAutoCompleteTextView(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 8: {
                object = this.createCheckedTextView(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 7: {
                object = this.createRadioButton(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 6: {
                object = this.createCheckBox(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 5: {
                object = this.createImageButton(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 4: {
                object = this.createSpinner(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 3: {
                object = this.createEditText(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 2: {
                object = this.createButton(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 1: {
                object = this.createImageView(context2, attributeSet);
                this.verifyNotNull((View)object, string);
                break;
            }
            case 0: {
                object = this.createTextView(context2, attributeSet);
                this.verifyNotNull((View)object, string);
            }
        }
        Object object2 = object;
        if (object == null) {
            object2 = object;
            if (context != context2) {
                object2 = this.createViewFromTag(context2, string, attributeSet);
            }
        }
        if (object2 != null) {
            this.checkOnClickListener((View)object2, attributeSet);
        }
        return object2;
    }

    private static class DeclaredOnClickListener
    implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View view, @NonNull String string) {
            this.mHostView = view;
            this.mMethodName = string;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @NonNull
        private void resolveMethod(@Nullable Context object, @NonNull String object2) {
            do {
                if (object != null) {
                    if (!object.isRestricted() && (object2 = object.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = object2;
                        this.mResolvedContext = object;
                        return;
                    }
                } else {
                    int n = this.mHostView.getId();
                    if (n == -1) {
                        object = "";
                    } else {
                        object = new StringBuilder();
                        object.append(" with id '");
                        object.append(this.mHostView.getContext().getResources().getResourceEntryName(n));
                        object.append("'");
                        object = object.toString();
                    }
                    object2 = new StringBuilder();
                    object2.append("Could not find method ");
                    object2.append(this.mMethodName);
                    object2.append("(View) in a parent or ancestor Context for android:onClick ");
                    object2.append("attribute defined on view ");
                    object2.append(this.mHostView.getClass());
                    object2.append((String)object);
                    throw new IllegalStateException(object2.toString());
                    catch (NoSuchMethodException noSuchMethodException) {}
                }
                if (object instanceof ContextWrapper) {
                    object = ((ContextWrapper)object).getBaseContext();
                    continue;
                }
                object = null;
            } while (true);
        }

        public void onClick(@NonNull View view) {
            if (this.mResolvedMethod == null) {
                this.resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke((Object)this.mResolvedContext, new Object[]{view});
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
            }
        }
    }

}
