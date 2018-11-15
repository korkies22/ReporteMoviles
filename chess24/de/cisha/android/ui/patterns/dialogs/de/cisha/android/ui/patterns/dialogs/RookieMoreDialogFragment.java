/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package de.cisha.android.ui.patterns.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import java.util.LinkedList;
import java.util.List;

public class RookieMoreDialogFragment
extends RookieInfoDialogFragment {
    private boolean _flagDialogIsDismissed = false;
    private List<ListOption> _options = new LinkedList<ListOption>();

    private void addNewOption(LinearLayout linearLayout, final ListOption listOption) {
        float f = this.getResources().getDisplayMetrics().density;
        CustomTextView customTextView = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.SIMPLE_LIST_ITEM, CustomTextViewTextStyle.SIMPLE_LIST_ITEM_SELECTED);
        customTextView.setText((CharSequence)listOption.getString());
        customTextView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!RookieMoreDialogFragment.this._flagDialogIsDismissed) {
                    RookieMoreDialogFragment.this._flagDialogIsDismissed = true;
                    RookieMoreDialogFragment.this.dismiss();
                    listOption.executeAction();
                }
            }
        });
        customTextView.setBackgroundResource(R.drawable.rookie_more_list_item_bg);
        customTextView.setGravity(17);
        linearLayout.addView((View)customTextView, -1, (int)(40.0f * f));
    }

    @Override
    public void addButton(CustomButton customButton) {
    }

    @Override
    protected RookieInfoDialogFragment.RookieType getRookieType() {
        return RookieInfoDialogFragment.RookieType.MORE;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.setTitle(this.getActivity().getString(R.string.rookie_more_dialog_title));
        viewGroup = new LinearLayout((Context)this.getActivity());
        viewGroup.setOrientation(1);
        float f = this.getResources().getDisplayMetrics().density;
        for (int i = 0; i < this._options.size(); ++i) {
            this.addNewOption((LinearLayout)viewGroup, this._options.get(i));
            bundle = new LinearLayout((Context)this.getActivity());
            bundle.setBackgroundColor(-7829368);
            viewGroup.addView((View)bundle, -1, (int)(1.0f * f));
        }
        this.addNewOption((LinearLayout)viewGroup, new ListOption(){

            @Override
            public void executeAction() {
            }

            @Override
            public String getString() {
                return RookieMoreDialogFragment.this.getActivity().getString(R.string.rookie_more_dialog_option_cancel);
            }
        });
        this.getContentContainerView().addView((View)viewGroup, -1, -1);
        return layoutInflater;
    }

    @Override
    public void onPause() {
        this.dismiss();
        super.onPause();
    }

    public void setListOptions(List<ListOption> list) {
        this._options = list;
    }

    public static interface ListOption {
        public void executeAction();

        public String getString();
    }

}
