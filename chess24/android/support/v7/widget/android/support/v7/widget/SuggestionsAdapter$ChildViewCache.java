/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.support.v7.appcompat.R;
import android.support.v7.widget.SuggestionsAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

private static final class SuggestionsAdapter.ChildViewCache {
    public final ImageView mIcon1;
    public final ImageView mIcon2;
    public final ImageView mIconRefine;
    public final TextView mText1;
    public final TextView mText2;

    public SuggestionsAdapter.ChildViewCache(View view) {
        this.mText1 = (TextView)view.findViewById(16908308);
        this.mText2 = (TextView)view.findViewById(16908309);
        this.mIcon1 = (ImageView)view.findViewById(16908295);
        this.mIcon2 = (ImageView)view.findViewById(16908296);
        this.mIconRefine = (ImageView)view.findViewById(R.id.edit_query);
    }
}
