/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.LayerDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.ThemeUtils;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public final class AppCompatDrawableManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
    private static final ColorFilterLruCache COLOR_FILTER_CACHE;
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE;
    private static AppCompatDrawableManager INSTANCE;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "AppCompatDrawableManag";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST;
    private static final int[] TINT_COLOR_CONTROL_NORMAL;
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
    private ArrayMap<String, InflateDelegate> mDelegates;
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private SparseArrayCompat<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    static {
        DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
        COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
        COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
        TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
        COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
        COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
        TINT_COLOR_CONTROL_STATE_LIST = new int[]{R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
        TINT_CHECKABLE_BUTTON_LIST = new int[]{R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};
    }

    private void addDelegate(@NonNull String string, @NonNull InflateDelegate inflateDelegate) {
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap();
        }
        this.mDelegates.put(string, inflateDelegate);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean addDrawableToCache(@NonNull Context context, long l, @NonNull Drawable object) {
        Drawable.ConstantState constantState = object.getConstantState();
        if (constantState == null) {
            return false;
        }
        Object object2 = this.mDrawableCacheLock;
        synchronized (object2) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get((Object)context);
            object = longSparseArray;
            if (longSparseArray == null) {
                object = new LongSparseArray();
                this.mDrawableCaches.put(context, (LongSparseArray<WeakReference<Drawable.ConstantState>>)object);
            }
            object.put(l, new WeakReference<Drawable.ConstantState>(constantState));
            return true;
        }
    }

    private void addTintListToCache(@NonNull Context context, @DrawableRes int n, @NonNull ColorStateList colorStateList) {
        SparseArrayCompat<ColorStateList> sparseArrayCompat;
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap();
        }
        SparseArrayCompat<Object> sparseArrayCompat2 = sparseArrayCompat = this.mTintLists.get((Object)context);
        if (sparseArrayCompat == null) {
            sparseArrayCompat2 = new SparseArrayCompat();
            this.mTintLists.put(context, sparseArrayCompat2);
        }
        sparseArrayCompat2.append(n, (Object)colorStateList);
    }

    private static boolean arrayContains(int[] arrn, int n) {
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    private void checkVectorDrawableSetup(@NonNull Context context) {
        if (this.mHasCheckedVectorDrawableSetup) {
            return;
        }
        this.mHasCheckedVectorDrawableSetup = true;
        if ((context = this.getDrawable(context, R.drawable.abc_vector_test)) != null && AppCompatDrawableManager.isVectorDrawable((Drawable)context)) {
            return;
        }
        this.mHasCheckedVectorDrawableSetup = false;
        throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
    }

    private ColorStateList createBorderlessButtonColorStateList(@NonNull Context context) {
        return this.createButtonColorStateList(context, 0);
    }

    private ColorStateList createButtonColorStateList(@NonNull Context arrn, @ColorInt int n) {
        int n2 = ThemeUtils.getThemeAttrColor((Context)arrn, R.attr.colorControlHighlight);
        int n3 = ThemeUtils.getDisabledThemeAttrColor((Context)arrn, R.attr.colorButtonNormal);
        arrn = ThemeUtils.DISABLED_STATE_SET;
        int[] arrn2 = ThemeUtils.PRESSED_STATE_SET;
        int n4 = ColorUtils.compositeColors(n2, n);
        int[] arrn3 = ThemeUtils.FOCUSED_STATE_SET;
        n2 = ColorUtils.compositeColors(n2, n);
        return new ColorStateList((int[][])new int[][]{arrn, arrn2, arrn3, ThemeUtils.EMPTY_STATE_SET}, new int[]{n3, n4, n2, n});
    }

    private static long createCacheKey(TypedValue typedValue) {
        return (long)typedValue.assetCookie << 32 | (long)typedValue.data;
    }

    private ColorStateList createColoredButtonColorStateList(@NonNull Context context) {
        return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorAccent));
    }

    private ColorStateList createDefaultButtonColorStateList(@NonNull Context context) {
        return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal));
    }

    private Drawable createDrawableIfNeeded(@NonNull Context context, @DrawableRes int n) {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        context.getResources().getValue(n, typedValue, true);
        long l = AppCompatDrawableManager.createCacheKey(typedValue);
        Drawable drawable2 = this.getCachedDrawable(context, l);
        if (drawable2 != null) {
            return drawable2;
        }
        if (n == R.drawable.abc_cab_background_top_material) {
            drawable2 = new LayerDrawable(new Drawable[]{this.getDrawable(context, R.drawable.abc_cab_background_internal_bg), this.getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (drawable2 != null) {
            drawable2.setChangingConfigurations(typedValue.changingConfigurations);
            this.addDrawableToCache(context, l, drawable2);
        }
        return drawable2;
    }

    private ColorStateList createSwitchThumbColorStateList(Context context) {
        int[][] arrarrn = new int[3][];
        int[] arrn = new int[3];
        ColorStateList colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal);
        if (colorStateList != null && colorStateList.isStateful()) {
            arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
            arrn[0] = colorStateList.getColorForState(arrarrn[0], 0);
            arrarrn[1] = ThemeUtils.CHECKED_STATE_SET;
            arrn[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            arrarrn[2] = ThemeUtils.EMPTY_STATE_SET;
            arrn[2] = colorStateList.getDefaultColor();
        } else {
            arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
            arrn[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
            arrarrn[1] = ThemeUtils.CHECKED_STATE_SET;
            arrn[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            arrarrn[2] = ThemeUtils.EMPTY_STATE_SET;
            arrn[2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
        }
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, int[] arrn) {
        if (colorStateList != null && mode != null) {
            return AppCompatDrawableManager.getPorterDuffColorFilter(colorStateList.getColorForState(arrn, 0), mode);
        }
        return null;
    }

    public static AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            INSTANCE = new AppCompatDrawableManager();
            AppCompatDrawableManager.installDefaultInflateDelegates(INSTANCE);
        }
        return INSTANCE;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable getCachedDrawable(@NonNull Context context, long l) {
        Object object = this.mDrawableCacheLock;
        synchronized (object) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get((Object)context);
            if (longSparseArray == null) {
                return null;
            }
            Drawable.ConstantState constantState = longSparseArray.get(l);
            if (constantState == null) return null;
            if ((constantState = (Drawable.ConstantState)constantState.get()) != null) {
                return constantState.newDrawable(context.getResources());
            }
            longSparseArray.delete(l);
            return null;
        }
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int n, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = porterDuffColorFilter = COLOR_FILTER_CACHE.get(n, mode);
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter2 = new PorterDuffColorFilter(n, mode);
            COLOR_FILTER_CACHE.put(n, mode, porterDuffColorFilter2);
        }
        return porterDuffColorFilter2;
    }

    private ColorStateList getTintListFromCache(@NonNull Context context, @DrawableRes int n) {
        Object object = this.mTintLists;
        Object var3_4 = null;
        if (object != null) {
            object = this.mTintLists.get((Object)context);
            context = var3_4;
            if (object != null) {
                context = (ColorStateList)object.get(n);
            }
            return context;
        }
        return null;
    }

    static PorterDuff.Mode getTintMode(int n) {
        if (n == R.drawable.abc_switch_thumb_material) {
            return PorterDuff.Mode.MULTIPLY;
        }
        return null;
    }

    private static void installDefaultInflateDelegates(@NonNull AppCompatDrawableManager appCompatDrawableManager) {
        if (Build.VERSION.SDK_INT < 24) {
            appCompatDrawableManager.addDelegate("vector", new VdcInflateDelegate());
            appCompatDrawableManager.addDelegate("animated-vector", new AvdcInflateDelegate());
        }
    }

    private static boolean isVectorDrawable(@NonNull Drawable drawable2) {
        if (!(drawable2 instanceof VectorDrawableCompat) && !PLATFORM_VD_CLAZZ.equals(drawable2.getClass().getName())) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable loadDrawableFromDelegates(@NonNull Context context, @DrawableRes int n) {
        if (this.mDelegates != null && !this.mDelegates.isEmpty()) {
            String string;
            if (this.mKnownDrawableIdTags != null) {
                string = this.mKnownDrawableIdTags.get(n);
                if (SKIP_DRAWABLE_TAG.equals(string) || string != null && this.mDelegates.get(string) == null) {
                    return null;
                }
            } else {
                this.mKnownDrawableIdTags = new SparseArrayCompat();
            }
            if (this.mTypedValue == null) {
                this.mTypedValue = new TypedValue();
            }
            TypedValue typedValue = this.mTypedValue;
            string = context.getResources();
            string.getValue(n, typedValue, true);
            long l = AppCompatDrawableManager.createCacheKey(typedValue);
            Drawable drawable2 = this.getCachedDrawable(context, l);
            if (drawable2 != null) {
                return drawable2;
            }
            Object object = drawable2;
            if (typedValue.string != null) {
                object = drawable2;
                if (typedValue.string.toString().endsWith(".xml")) {
                    object = drawable2;
                    try {
                        int n2;
                        XmlResourceParser xmlResourceParser = string.getXml(n);
                        object = drawable2;
                        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
                        do {
                            object = drawable2;
                        } while ((n2 = xmlResourceParser.next()) != 2 && n2 != 1);
                        if (n2 != 2) {
                            object = drawable2;
                            throw new XmlPullParserException("No start tag found");
                        }
                        object = drawable2;
                        string = xmlResourceParser.getName();
                        object = drawable2;
                        this.mKnownDrawableIdTags.append(n, string);
                        object = drawable2;
                        InflateDelegate inflateDelegate = (InflateDelegate)this.mDelegates.get(string);
                        string = drawable2;
                        if (inflateDelegate != null) {
                            object = drawable2;
                            string = inflateDelegate.createFromXmlInner(context, (XmlPullParser)xmlResourceParser, attributeSet, context.getTheme());
                        }
                        object = string;
                        if (string != null) {
                            object = string;
                            string.setChangingConfigurations(typedValue.changingConfigurations);
                            object = string;
                            this.addDrawableToCache(context, l, (Drawable)string);
                            object = string;
                        }
                    }
                    catch (Exception exception) {
                        Log.e((String)TAG, (String)"Exception while inflating drawable", (Throwable)exception);
                    }
                }
            }
            if (object == null) {
                this.mKnownDrawableIdTags.append(n, SKIP_DRAWABLE_TAG);
            }
            return object;
        }
        return null;
    }

    private void removeDelegate(@NonNull String string, @NonNull InflateDelegate inflateDelegate) {
        if (this.mDelegates != null && this.mDelegates.get(string) == inflateDelegate) {
            this.mDelegates.remove(string);
        }
    }

    private static void setPorterDuffColorFilter(Drawable drawable2, int n, PorterDuff.Mode mode) {
        Drawable drawable3 = drawable2;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            drawable3 = drawable2.mutate();
        }
        drawable2 = mode;
        if (mode == null) {
            drawable2 = DEFAULT_MODE;
        }
        drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(n, (PorterDuff.Mode)drawable2));
    }

    private Drawable tintDrawable(@NonNull Context context, @DrawableRes int n, boolean bl, @NonNull Drawable drawable2) {
        ColorStateList colorStateList = this.getTintList(context, n);
        if (colorStateList != null) {
            context = drawable2;
            if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
                context = drawable2.mutate();
            }
            context = DrawableCompat.wrap((Drawable)context);
            DrawableCompat.setTintList((Drawable)context, colorStateList);
            drawable2 = AppCompatDrawableManager.getTintMode(n);
            colorStateList = context;
            if (drawable2 != null) {
                DrawableCompat.setTintMode((Drawable)context, (PorterDuff.Mode)drawable2);
                return context;
            }
        } else {
            if (n == R.drawable.abc_seekbar_track_material) {
                colorStateList = (LayerDrawable)drawable2;
                AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
                AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
                AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
                return drawable2;
            }
            if (n != R.drawable.abc_ratingbar_material && n != R.drawable.abc_ratingbar_indicator_material && n != R.drawable.abc_ratingbar_small_material) {
                colorStateList = drawable2;
                if (!AppCompatDrawableManager.tintDrawableUsingColorFilter(context, n, drawable2)) {
                    colorStateList = drawable2;
                    if (bl) {
                        return null;
                    }
                }
            } else {
                colorStateList = (LayerDrawable)drawable2;
                AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
                AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
                AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
                colorStateList = drawable2;
            }
        }
        return colorStateList;
    }

    static void tintDrawable(Drawable drawable2, TintInfo tintInfo, int[] arrn) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable2) && drawable2.mutate() != drawable2) {
            Log.d((String)TAG, (String)"Mutated drawable is not the same instance as the input.");
            return;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            drawable2.clearColorFilter();
        } else {
            ColorStateList colorStateList = tintInfo.mHasTintList ? tintInfo.mTintList : null;
            tintInfo = tintInfo.mHasTintMode ? tintInfo.mTintMode : DEFAULT_MODE;
            drawable2.setColorFilter((ColorFilter)AppCompatDrawableManager.createTintFilter(colorStateList, (PorterDuff.Mode)tintInfo, arrn));
        }
        if (Build.VERSION.SDK_INT <= 23) {
            drawable2.invalidateSelf();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static boolean tintDrawableUsingColorFilter(@NonNull Context var0, @DrawableRes int var1_1, @NonNull Drawable var2_2) {
        block6 : {
            block5 : {
                block4 : {
                    block3 : {
                        var6_3 = AppCompatDrawableManager.DEFAULT_MODE;
                        var5_4 = AppCompatDrawableManager.arrayContains(AppCompatDrawableManager.COLORFILTER_TINT_COLOR_CONTROL_NORMAL, var1_1);
                        var3_5 = 16842801;
                        if (!var5_4) break block3;
                        var1_1 = R.attr.colorControlNormal;
                        ** GOTO lbl24
                    }
                    if (!AppCompatDrawableManager.arrayContains(AppCompatDrawableManager.COLORFILTER_COLOR_CONTROL_ACTIVATED, var1_1)) break block4;
                    var1_1 = R.attr.colorControlActivated;
                    ** GOTO lbl24
                }
                if (!AppCompatDrawableManager.arrayContains(AppCompatDrawableManager.COLORFILTER_COLOR_BACKGROUND_MULTIPLY, var1_1)) break block5;
                var6_3 = PorterDuff.Mode.MULTIPLY;
                var1_1 = var3_5;
                ** GOTO lbl24
            }
            if (var1_1 != R.drawable.abc_list_divider_mtrl_alpha) break block6;
            var1_1 = 16842800;
            var3_5 = Math.round(40.8f);
            ** GOTO lbl25
        }
        if (var1_1 == R.drawable.abc_dialog_material_background) {
            var1_1 = var3_5;
lbl24: // 4 sources:
            var3_5 = -1;
lbl25: // 2 sources:
            var4_6 = 1;
        } else {
            var3_5 = -1;
            var1_1 = var4_6 = 0;
        }
        if (var4_6 == 0) return false;
        var7_7 = var2_2;
        if (DrawableUtils.canSafelyMutateDrawable(var2_2)) {
            var7_7 = var2_2.mutate();
        }
        var7_7.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(var0, var1_1), var6_3));
        if (var3_5 == -1) return true;
        var7_7.setAlpha(var3_5);
        return true;
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int n) {
        return this.getDrawable(context, n, false);
    }

    Drawable getDrawable(@NonNull Context context, @DrawableRes int n, boolean bl) {
        Drawable drawable2;
        this.checkVectorDrawableSetup(context);
        Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n);
        if (drawable2 == null) {
            drawable3 = this.createDrawableIfNeeded(context, n);
        }
        drawable2 = drawable3;
        if (drawable3 == null) {
            drawable2 = ContextCompat.getDrawable(context, n);
        }
        drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = this.tintDrawable(context, n, bl, drawable2);
        }
        if (drawable3 != null) {
            DrawableUtils.fixDrawable(drawable3);
        }
        return drawable3;
    }

    ColorStateList getTintList(@NonNull Context context, @DrawableRes int n) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = colorStateList = this.getTintListFromCache(context, n);
        if (colorStateList == null) {
            if (n == R.drawable.abc_edit_text_material) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext);
            } else if (n == R.drawable.abc_switch_track_mtrl_alpha) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track);
            } else if (n == R.drawable.abc_switch_thumb_material) {
                colorStateList = this.createSwitchThumbColorStateList(context);
            } else if (n == R.drawable.abc_btn_default_mtrl_shape) {
                colorStateList = this.createDefaultButtonColorStateList(context);
            } else if (n == R.drawable.abc_btn_borderless_material) {
                colorStateList = this.createBorderlessButtonColorStateList(context);
            } else if (n == R.drawable.abc_btn_colored_material) {
                colorStateList = this.createColoredButtonColorStateList(context);
            } else if (n != R.drawable.abc_spinner_mtrl_am_alpha && n != R.drawable.abc_spinner_textfield_background_material) {
                if (AppCompatDrawableManager.arrayContains(TINT_COLOR_CONTROL_NORMAL, n)) {
                    colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal);
                } else if (AppCompatDrawableManager.arrayContains(TINT_COLOR_CONTROL_STATE_LIST, n)) {
                    colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_default);
                } else if (AppCompatDrawableManager.arrayContains(TINT_CHECKABLE_BUTTON_LIST, n)) {
                    colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable);
                } else if (n == R.drawable.abc_seekbar_thumb_material) {
                    colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb);
                }
            } else {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
            }
            colorStateList2 = colorStateList;
            if (colorStateList != null) {
                this.addTintListToCache(context, n, colorStateList);
                colorStateList2 = colorStateList;
            }
        }
        return colorStateList2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onConfigurationChanged(@NonNull Context object) {
        Object object2 = this.mDrawableCacheLock;
        synchronized (object2) {
            object = this.mDrawableCaches.get(object);
            if (object != null) {
                object.clear();
            }
            return;
        }
    }

    Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources vectorEnabledTintResources, @DrawableRes int n) {
        Drawable drawable2;
        Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n);
        if (drawable2 == null) {
            drawable3 = vectorEnabledTintResources.superGetDrawable(n);
        }
        if (drawable3 != null) {
            return this.tintDrawable(context, n, false, drawable3);
        }
        return null;
    }

    @RequiresApi(value=11)
    private static class AvdcInflateDelegate
    implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(@NonNull Context object, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                object = AnimatedVectorDrawableCompat.createFromXmlInner(object, object.getResources(), xmlPullParser, attributeSet, theme);
                return object;
            }
            catch (Exception exception) {
                Log.e((String)"AvdcInflateDelegate", (String)"Exception while inflating <animated-vector>", (Throwable)exception);
                return null;
            }
        }
    }

    private static class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int n) {
            super(n);
        }

        private static int generateCacheKey(int n, PorterDuff.Mode mode) {
            return 31 * (n + 31) + mode.hashCode();
        }

        PorterDuffColorFilter get(int n, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter)this.get(ColorFilterLruCache.generateCacheKey(n, mode));
        }

        PorterDuffColorFilter put(int n, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return this.put(ColorFilterLruCache.generateCacheKey(n, mode), porterDuffColorFilter);
        }
    }

    private static interface InflateDelegate {
        public Drawable createFromXmlInner(@NonNull Context var1, @NonNull XmlPullParser var2, @NonNull AttributeSet var3, @Nullable Resources.Theme var4);
    }

    private static class VdcInflateDelegate
    implements InflateDelegate {
        VdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(@NonNull Context object, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                object = VectorDrawableCompat.createFromXmlInner(object.getResources(), xmlPullParser, attributeSet, theme);
                return object;
            }
            catch (Exception exception) {
                Log.e((String)"VdcInflateDelegate", (String)"Exception while inflating <vector>", (Throwable)exception);
                return null;
            }
        }
    }

}
