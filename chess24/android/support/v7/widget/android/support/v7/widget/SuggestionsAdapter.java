/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.SearchManager
 *  android.app.SearchableInfo
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.text.SpannableString
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter
extends ResourceCursorAdapter
implements View.OnClickListener {
    private static final boolean DBG = false;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed = false;
    private final int mCommitIconResId;
    private int mFlagsCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement = 1;
    private final SearchManager mSearchManager = (SearchManager)this.mContext.getSystemService("search");
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private ColorStateList mUrlColor;

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId();
        this.mProviderContext = context;
        this.mOutsideDrawablesCache = weakHashMap;
    }

    private Drawable checkIconCache(String string) {
        if ((string = this.mOutsideDrawablesCache.get(string)) == null) {
            return null;
        }
        return string.newDrawable();
    }

    private CharSequence formatUrl(CharSequence charSequence) {
        TypedValue typedValue;
        if (this.mUrlColor == null) {
            typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(R.attr.textColorSearchUrl, typedValue, true);
            this.mUrlColor = this.mContext.getResources().getColorStateList(typedValue.resourceId);
        }
        typedValue = new SpannableString(charSequence);
        typedValue.setSpan((Object)new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33);
        return typedValue;
    }

    private Drawable getActivityIcon(ComponentName componentName) {
        ActivityInfo activityInfo;
        Object object = this.mContext.getPackageManager();
        try {
            activityInfo = object.getActivityInfo(componentName, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)LOG_TAG, (String)nameNotFoundException.toString());
            return null;
        }
        int n = activityInfo.getIconResource();
        if (n == 0) {
            return null;
        }
        if ((object = object.getDrawable(componentName.getPackageName(), n, activityInfo.applicationInfo)) == null) {
            object = new StringBuilder();
            object.append("Invalid icon resource ");
            object.append(n);
            object.append(" for ");
            object.append(componentName.flattenToShortString());
            Log.w((String)LOG_TAG, (String)object.toString());
            return null;
        }
        return object;
    }

    private Drawable getActivityIconWithCache(ComponentName object) {
        String string = object.flattenToShortString();
        boolean bl = this.mOutsideDrawablesCache.containsKey(string);
        Object var3_4 = null;
        if (bl) {
            object = this.mOutsideDrawablesCache.get(string);
            if (object == null) {
                return null;
            }
            return object.newDrawable(this.mProviderContext.getResources());
        }
        Drawable drawable = this.getActivityIcon((ComponentName)object);
        object = drawable == null ? var3_4 : drawable.getConstantState();
        this.mOutsideDrawablesCache.put(string, (Drawable.ConstantState)object);
        return drawable;
    }

    public static String getColumnString(Cursor cursor, String string) {
        return SuggestionsAdapter.getStringOrNull(cursor, cursor.getColumnIndex(string));
    }

    private Drawable getDefaultIcon1(Cursor cursor) {
        cursor = this.getActivityIconWithCache(this.mSearchable.getSearchActivity());
        if (cursor != null) {
            return cursor;
        }
        return this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Drawable getDrawable(Uri uri) {
        Object object;
        block12 : {
            try {
                boolean bl = "android.resource".equals(uri.getScheme());
                if (bl) {
                    return this.getDrawableFromResourceUri(uri);
                }
                object = this.mProviderContext.getContentResolver().openInputStream(uri);
                if (object != null) break block12;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to open ");
                stringBuilder.append((Object)uri);
                throw new FileNotFoundException(stringBuilder.toString());
            }
            catch (FileNotFoundException fileNotFoundException) {
                object = new StringBuilder();
                object.append("Icon not found: ");
                object.append((Object)uri);
                object.append(", ");
                object.append(fileNotFoundException.getMessage());
                Log.w((String)LOG_TAG, (String)object.toString());
                return null;
            }
        }
        Drawable drawable = Drawable.createFromStream((InputStream)object, null);
        {
            catch (Throwable throwable) {
                try {
                    object.close();
                    throw throwable;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error closing icon stream for ");
                    stringBuilder.append((Object)uri);
                    Log.e((String)LOG_TAG, (String)stringBuilder.toString(), (Throwable)iOException);
                }
                throw throwable;
            }
        }
        try {
            object.close();
            return drawable;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error closing icon stream for ");
            stringBuilder.append((Object)uri);
            Log.e((String)LOG_TAG, (String)stringBuilder.toString(), (Throwable)iOException);
            return drawable;
        }
        catch (Resources.NotFoundException notFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Resource does not exist: ");
        stringBuilder.append((Object)uri);
        throw new FileNotFoundException(stringBuilder.toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Drawable getDrawableFromResourceValue(String string) {
        if (string == null) return null;
        if (string.isEmpty()) return null;
        if ("0".equals(string)) {
            return null;
        }
        try {
            int n = Integer.parseInt(string);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("android.resource://");
            stringBuilder.append(this.mProviderContext.getPackageName());
            stringBuilder.append("/");
            stringBuilder.append(n);
            String string2 = stringBuilder.toString();
            Drawable drawable = this.checkIconCache(string2);
            if (drawable != null) {
                return drawable;
            }
            drawable = ContextCompat.getDrawable(this.mProviderContext, n);
            this.storeInIconCache(string2, drawable);
            return drawable;
        }
        catch (NumberFormatException numberFormatException) {}
        Drawable drawable = this.checkIconCache(string);
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = this.getDrawable(Uri.parse((String)string));
        this.storeInIconCache(string, drawable2);
        return drawable2;
        catch (Resources.NotFoundException notFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Icon resource not found: ");
        stringBuilder.append(string);
        Log.w((String)LOG_TAG, (String)stringBuilder.toString());
        return null;
    }

    private Drawable getIcon1(Cursor cursor) {
        if (this.mIconName1Col == -1) {
            return null;
        }
        Drawable drawable = this.getDrawableFromResourceValue(cursor.getString(this.mIconName1Col));
        if (drawable != null) {
            return drawable;
        }
        return this.getDefaultIcon1(cursor);
    }

    private Drawable getIcon2(Cursor cursor) {
        if (this.mIconName2Col == -1) {
            return null;
        }
        return this.getDrawableFromResourceValue(cursor.getString(this.mIconName2Col));
    }

    private static String getStringOrNull(Cursor object, int n) {
        if (n == -1) {
            return null;
        }
        try {
            object = object.getString(n);
            return object;
        }
        catch (Exception exception) {
            Log.e((String)LOG_TAG, (String)"unexpected error retrieving valid column from cursor, did the remote process die?", (Throwable)exception);
            return null;
        }
    }

    private void setViewDrawable(ImageView imageView, Drawable drawable, int n) {
        imageView.setImageDrawable(drawable);
        if (drawable == null) {
            imageView.setVisibility(n);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    private void setViewText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
    }

    private void storeInIconCache(String string, Drawable drawable) {
        if (drawable != null) {
            this.mOutsideDrawablesCache.put(string, drawable.getConstantState());
        }
    }

    private void updateSpinnerState(Cursor object) {
        object = object != null ? object.getExtras() : null;
        if (object != null && object.getBoolean("in_progress")) {
            return;
        }
    }

    @Override
    public void bindView(View object, Context object2, Cursor cursor) {
        object2 = (ChildViewCache)object.getTag();
        int n = this.mFlagsCol != -1 ? cursor.getInt(this.mFlagsCol) : 0;
        if (object2.mText1 != null) {
            object = SuggestionsAdapter.getStringOrNull(cursor, this.mText1Col);
            this.setViewText(object2.mText1, (CharSequence)object);
        }
        if (object2.mText2 != null) {
            object = SuggestionsAdapter.getStringOrNull(cursor, this.mText2UrlCol);
            object = object != null ? this.formatUrl((CharSequence)object) : SuggestionsAdapter.getStringOrNull(cursor, this.mText2Col);
            if (TextUtils.isEmpty((CharSequence)object)) {
                if (object2.mText1 != null) {
                    object2.mText1.setSingleLine(false);
                    object2.mText1.setMaxLines(2);
                }
            } else if (object2.mText1 != null) {
                object2.mText1.setSingleLine(true);
                object2.mText1.setMaxLines(1);
            }
            this.setViewText(object2.mText2, (CharSequence)object);
        }
        if (object2.mIcon1 != null) {
            this.setViewDrawable(object2.mIcon1, this.getIcon1(cursor), 4);
        }
        if (object2.mIcon2 != null) {
            this.setViewDrawable(object2.mIcon2, this.getIcon2(cursor), 8);
        }
        if (this.mQueryRefinement != 2 && (this.mQueryRefinement != 1 || (n & 1) == 0)) {
            object2.mIconRefine.setVisibility(8);
            return;
        }
        object2.mIconRefine.setVisibility(0);
        object2.mIconRefine.setTag((Object)object2.mText1.getText());
        object2.mIconRefine.setOnClickListener((View.OnClickListener)this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void changeCursor(Cursor cursor) {
        if (this.mClosed) {
            Log.w((String)LOG_TAG, (String)"Tried to change cursor after adapter was closed.");
            if (cursor == null) return;
            cursor.close();
            return;
        }
        super.changeCursor(cursor);
        if (cursor == null) return;
        try {
            this.mText1Col = cursor.getColumnIndex("suggest_text_1");
            this.mText2Col = cursor.getColumnIndex("suggest_text_2");
            this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
            this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
            this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
            this.mFlagsCol = cursor.getColumnIndex("suggest_flags");
            return;
        }
        catch (Exception exception) {
            Log.e((String)LOG_TAG, (String)"error changing cursor and caching columns", (Throwable)exception);
        }
    }

    public void close() {
        this.changeCursor(null);
        this.mClosed = true;
    }

    @Override
    public CharSequence convertToString(Cursor object) {
        if (object == null) {
            return null;
        }
        String string = SuggestionsAdapter.getColumnString(object, "suggest_intent_query");
        if (string != null) {
            return string;
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (string = SuggestionsAdapter.getColumnString(object, "suggest_intent_data")) != null) {
            return string;
        }
        if (this.mSearchable.shouldRewriteQueryFromText() && (object = SuggestionsAdapter.getColumnString(object, "suggest_text_1")) != null) {
            return object;
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
        Resources resources;
        CharSequence charSequence = uri.getAuthority();
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            charSequence = new StringBuilder();
            charSequence.append("No authority: ");
            charSequence.append((Object)uri);
            throw new FileNotFoundException(charSequence.toString());
        }
        try {
            resources = this.mContext.getPackageManager().getResourcesForApplication((String)charSequence);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        List list = uri.getPathSegments();
        if (list == null) {
            charSequence = new StringBuilder();
            charSequence.append("No path: ");
            charSequence.append((Object)uri);
            throw new FileNotFoundException(charSequence.toString());
        }
        int n = list.size();
        if (n == 1) {
            n = Integer.parseInt((String)list.get(0));
        } else {
            if (n != 2) {
                charSequence = new StringBuilder();
                charSequence.append("More than two path segments: ");
                charSequence.append((Object)uri);
                throw new FileNotFoundException(charSequence.toString());
            }
            n = resources.getIdentifier((String)list.get(1), (String)list.get(0), (String)charSequence);
        }
        if (n != 0) return resources.getDrawable(n);
        charSequence = new StringBuilder();
        charSequence.append("No resource found for: ");
        charSequence.append((Object)uri);
        throw new FileNotFoundException(charSequence.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No package found for authority: ");
        stringBuilder.append((Object)uri);
        throw new FileNotFoundException(stringBuilder.toString());
        catch (NumberFormatException numberFormatException) {}
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Single path segment is not a resource ID: ");
        stringBuilder2.append((Object)uri);
        throw new FileNotFoundException(stringBuilder2.toString());
    }

    @Override
    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        try {
            view = super.getDropDownView(n, view, viewGroup);
            return view;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.newDropDownView(this.mContext, this.mCursor, viewGroup);
            if (viewGroup != null) {
                ((ChildViewCache)viewGroup.getTag()).mText1.setText((CharSequence)runtimeException.toString());
            }
            return viewGroup;
        }
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    Cursor getSearchManagerSuggestions(SearchableInfo arrstring, String string, int n) {
        Object var4_4 = null;
        if (arrstring == null) {
            return null;
        }
        String string2 = arrstring.getSuggestAuthority();
        if (string2 == null) {
            return null;
        }
        string2 = new Uri.Builder().scheme("content").authority(string2).query("").fragment("");
        String string3 = arrstring.getSuggestPath();
        if (string3 != null) {
            string2.appendEncodedPath(string3);
        }
        string2.appendPath("search_suggest_query");
        string3 = arrstring.getSuggestSelection();
        if (string3 != null) {
            arrstring = new String[]{string};
        } else {
            string2.appendPath(string);
            arrstring = var4_4;
        }
        if (n > 0) {
            string2.appendQueryParameter("limit", String.valueOf(n));
        }
        string = string2.build();
        return this.mContext.getContentResolver().query((Uri)string, null, string3, arrstring, null);
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        try {
            view = super.getView(n, view, viewGroup);
            return view;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.newView(this.mContext, this.mCursor, viewGroup);
            if (viewGroup != null) {
                ((ChildViewCache)viewGroup.getTag()).mText1.setText((CharSequence)runtimeException.toString());
            }
            return viewGroup;
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        context = super.newView(context, cursor, viewGroup);
        context.setTag((Object)new ChildViewCache((View)context));
        ((ImageView)context.findViewById(R.id.edit_query)).setImageResource(this.mCommitIconResId);
        return context;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.updateSpinnerState(this.getCursor());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        this.updateSpinnerState(this.getCursor());
    }

    public void onClick(View object) {
        if ((object = object.getTag()) instanceof CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence)object);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        charSequence = charSequence == null ? "" : charSequence.toString();
        if (this.mSearchView.getVisibility() != 0) return null;
        if (this.mSearchView.getWindowVisibility() != 0) {
            return null;
        }
        charSequence = this.getSearchManagerSuggestions(this.mSearchable, (String)charSequence, 50);
        if (charSequence == null) return null;
        try {
            charSequence.getCount();
            return charSequence;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions query threw an exception.", (Throwable)runtimeException);
        }
        return null;
    }

    public void setQueryRefinement(int n) {
        this.mQueryRefinement = n;
    }

    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view) {
            this.mText1 = (TextView)view.findViewById(16908308);
            this.mText2 = (TextView)view.findViewById(16908309);
            this.mIcon1 = (ImageView)view.findViewById(16908295);
            this.mIcon2 = (ImageView)view.findViewById(16908296);
            this.mIconRefine = (ImageView)view.findViewById(R.id.edit_query);
        }
    }

}
