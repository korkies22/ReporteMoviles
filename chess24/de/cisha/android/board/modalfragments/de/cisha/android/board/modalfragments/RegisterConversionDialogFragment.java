/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package de.cisha.android.board.modalfragments;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.GoPremiumWithListDialogFragment;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternativeSmall;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RegisterConversionDialogFragment
extends GoPremiumWithListDialogFragment {
    @Override
    protected CustomButton createConvertButtonInstance() {
        return new CustomButtonAlternativeSmall((Context)this.getActivity());
    }

    @Override
    protected int getConversionButtonTitleResId() {
        return 2131689662;
    }

    @Override
    protected List<Integer> getListStringResIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(2131689675, 2131689679, 2131689664, 2131689674, 2131689663));
        this.removeRandomItemsFromList(arrayList, 2);
        return arrayList;
    }

    @Override
    protected String getStringForConversionContext(Resources resources, ConversionContext conversionContext) {
        return conversionContext.getMessageRegisterResId(resources);
    }

    @Override
    protected int getTitleResId() {
        return 2131689677;
    }
}
