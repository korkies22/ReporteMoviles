// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.content.Context;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;

public class KeyValueInfoDialogFragment extends SimpleOkDialogFragment
{
    private LinearLayout _rows;
    
    private void initRowsView() {
        final ScrollView scrollView = new ScrollView((Context)this.getActivity());
        this._rows = new LinearLayout((Context)this.getActivity());
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)this.getActivity().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        final float density = displayMetrics.density;
        final LinearLayout rows = this._rows;
        final int n = (int)(10.0f * density);
        rows.setPadding(n, 0, n, 0);
        this._rows.setOrientation(1);
        scrollView.addView((View)this._rows);
        this.getContentContainerView().addView((View)scrollView);
    }
    
    protected void addRowView(final View view) {
        this._rows.addView(view, -2, -2);
    }
    
    protected void addRowView(final String s, final String s2) {
        this.addRowView(this.getRow(s, s2));
    }
    
    protected View getRow(final View view, final View view2) {
        final LinearLayout linearLayout = new LinearLayout((Context)this.getActivity());
        linearLayout.setOrientation(0);
        linearLayout.addView(view);
        linearLayout.addView(new View((Context)this.getActivity()), (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout.addView(view2, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
        return (View)linearLayout;
    }
    
    protected View getRow(String text, final String s) {
        final CustomTextView customTextView = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST_BOLD, null);
        final StringBuilder sb = new StringBuilder();
        sb.append(text);
        sb.append(":");
        customTextView.setText((CharSequence)sb.toString());
        final CustomTextView customTextView2 = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST, null);
        text = s;
        if (s == null) {
            text = "";
        }
        customTextView2.setText((CharSequence)text);
        return this.getRow((View)customTextView, (View)customTextView2);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.initRowsView();
        return onCreateView;
    }
}
