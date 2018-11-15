/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.modalfragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GoPremiumWithListDialogFragment
extends AbstractConversionDialogFragment {
    protected List<Integer> getListStringResIds() {
        return Arrays.asList(2131689679, 2131689664, 2131689663);
    }

    protected String getStringForConversionContext(Resources resources, ConversionContext conversionContext) {
        return conversionContext.getMessagePremiumResId(resources);
    }

    @Override
    protected void inflateContentViewTo(LayoutInflater layoutInflater, LinearLayout linearLayout) {
        Object object = this.getConversionContext();
        if (object != null && (object = this.getStringForConversionContext(layoutInflater.getContext().getResources(), (ConversionContext)((Object)object))) != null && !object.isEmpty()) {
            Integer n = (TextView)layoutInflater.inflate(2131427407, (ViewGroup)linearLayout, false);
            n.setText((CharSequence)object);
            linearLayout.addView((View)n);
        }
        linearLayout.setGravity(3);
        for (Integer n : this.getListStringResIds()) {
            TextView textView = (TextView)layoutInflater.inflate(2131427442, (ViewGroup)linearLayout, false);
            textView.setText(n.intValue());
            linearLayout.addView((View)textView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int n = (int)(this.getResources().getDisplayMetrics().density * 20.0f);
        int n2 = this.getResources().getDisplayMetrics().widthPixels;
        int n3 = this.getDialog().getWindow().getAttributes().height;
        this.getDialog().getWindow().setLayout(n2 - n, n3);
    }

    protected void removeRandomItemsFromList(List<Integer> list, int n) {
        int n2 = list.size();
        if (n > 0 && n <= n2) {
            if (n == 1) {
                list.remove(new Random().nextInt(n2));
                return;
            }
            this.removeRandomItemsFromList(list, 1);
            this.removeRandomItemsFromList(list, n - 1);
        }
    }
}
