// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import java.util.Random;
import java.util.Iterator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.content.res.Resources;
import java.util.Arrays;
import java.util.List;

public class GoPremiumWithListDialogFragment extends AbstractConversionDialogFragment
{
    protected List<Integer> getListStringResIds() {
        return Arrays.asList(2131689679, 2131689664, 2131689663);
    }
    
    protected String getStringForConversionContext(final Resources resources, final ConversionContext conversionContext) {
        return conversionContext.getMessagePremiumResId(resources);
    }
    
    @Override
    protected void inflateContentViewTo(final LayoutInflater layoutInflater, final LinearLayout linearLayout) {
        final ConversionContext conversionContext = this.getConversionContext();
        if (conversionContext != null) {
            final String stringForConversionContext = this.getStringForConversionContext(layoutInflater.getContext().getResources(), conversionContext);
            if (stringForConversionContext != null && !stringForConversionContext.isEmpty()) {
                final TextView textView = (TextView)layoutInflater.inflate(2131427407, (ViewGroup)linearLayout, false);
                textView.setText((CharSequence)stringForConversionContext);
                linearLayout.addView((View)textView);
            }
        }
        linearLayout.setGravity(3);
        for (final Integer n : this.getListStringResIds()) {
            final TextView textView2 = (TextView)layoutInflater.inflate(2131427442, (ViewGroup)linearLayout, false);
            textView2.setText((int)n);
            linearLayout.addView((View)textView2);
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        this.getDialog().getWindow().setLayout(this.getResources().getDisplayMetrics().widthPixels - (int)(this.getResources().getDisplayMetrics().density * 20.0f), this.getDialog().getWindow().getAttributes().height);
    }
    
    protected void removeRandomItemsFromList(final List<Integer> list, final int n) {
        final int size = list.size();
        if (n > 0 && n <= size) {
            if (n == 1) {
                list.remove(new Random().nextInt(size));
                return;
            }
            this.removeRandomItemsFromList(list, 1);
            this.removeRandomItemsFromList(list, n - 1);
        }
    }
}
