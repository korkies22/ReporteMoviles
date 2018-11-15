/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ScrollView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;

public class KeyValueInfoDialogFragment
extends SimpleOkDialogFragment {
    private LinearLayout _rows;

    private void initRowsView() {
        ScrollView scrollView = new ScrollView((Context)this.getActivity());
        this._rows = new LinearLayout((Context)this.getActivity());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)this.getActivity().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        float f = displayMetrics.density;
        displayMetrics = this._rows;
        int n = (int)(10.0f * f);
        displayMetrics.setPadding(n, 0, n, 0);
        this._rows.setOrientation(1);
        scrollView.addView((View)this._rows);
        this.getContentContainerView().addView((View)scrollView);
    }

    protected void addRowView(View view) {
        this._rows.addView(view, -2, -2);
    }

    protected void addRowView(String string, String string2) {
        this.addRowView(this.getRow(string, string2));
    }

    protected View getRow(View view, View view2) {
        LinearLayout linearLayout = new LinearLayout((Context)this.getActivity());
        linearLayout.setOrientation(0);
        linearLayout.addView(view);
        linearLayout.addView(new View((Context)this.getActivity()), (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout.addView(view2, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
        return linearLayout;
    }

    protected View getRow(String string, String string2) {
        CustomTextView customTextView = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST_BOLD, null);
        Object object = new StringBuilder();
        object.append(string);
        object.append(":");
        customTextView.setText((CharSequence)object.toString());
        object = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST, null);
        string = string2;
        if (string2 == null) {
            string = "";
        }
        object.setText((CharSequence)string);
        return this.getRow((View)customTextView, (View)object);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.initRowsView();
        return layoutInflater;
    }
}
