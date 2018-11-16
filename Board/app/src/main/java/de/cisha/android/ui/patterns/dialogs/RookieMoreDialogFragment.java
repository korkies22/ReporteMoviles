// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.List;

public class RookieMoreDialogFragment extends RookieInfoDialogFragment
{
    private boolean _flagDialogIsDismissed;
    private List<ListOption> _options;
    
    public RookieMoreDialogFragment() {
        this._flagDialogIsDismissed = false;
        this._options = new LinkedList<ListOption>();
    }
    
    private void addNewOption(final LinearLayout linearLayout, final ListOption listOption) {
        final float density = this.getResources().getDisplayMetrics().density;
        final CustomTextView customTextView = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.SIMPLE_LIST_ITEM, CustomTextViewTextStyle.SIMPLE_LIST_ITEM_SELECTED);
        customTextView.setText((CharSequence)listOption.getString());
        customTextView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (!RookieMoreDialogFragment.this._flagDialogIsDismissed) {
                    RookieMoreDialogFragment.this._flagDialogIsDismissed = true;
                    RookieMoreDialogFragment.this.dismiss();
                    listOption.executeAction();
                }
            }
        });
        customTextView.setBackgroundResource(R.drawable.rookie_more_list_item_bg);
        customTextView.setGravity(17);
        linearLayout.addView((View)customTextView, -1, (int)(40.0f * density));
    }
    
    @Override
    public void addButton(final CustomButton customButton) {
    }
    
    @Override
    protected RookieType getRookieType() {
        return RookieType.MORE;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setCancelable(true);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.setTitle(this.getActivity().getString(R.string.rookie_more_dialog_title));
        final LinearLayout linearLayout = new LinearLayout((Context)this.getActivity());
        linearLayout.setOrientation(1);
        final float density = this.getResources().getDisplayMetrics().density;
        for (int i = 0; i < this._options.size(); ++i) {
            this.addNewOption(linearLayout, this._options.get(i));
            final LinearLayout linearLayout2 = new LinearLayout((Context)this.getActivity());
            linearLayout2.setBackgroundColor(-7829368);
            linearLayout.addView((View)linearLayout2, -1, (int)(1.0f * density));
        }
        this.addNewOption(linearLayout, (ListOption)new ListOption() {
            @Override
            public void executeAction() {
            }
            
            @Override
            public String getString() {
                return RookieMoreDialogFragment.this.getActivity().getString(R.string.rookie_more_dialog_option_cancel);
            }
        });
        this.getContentContainerView().addView((View)linearLayout, -1, -1);
        return onCreateView;
    }
    
    @Override
    public void onPause() {
        this.dismiss();
        super.onPause();
    }
    
    public void setListOptions(final List<ListOption> options) {
        this._options = options;
    }
    
    public interface ListOption
    {
        void executeAction();
        
        String getString();
    }
}
