// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.content.res.Resources;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternativeSmall;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public class RegisterConversionDialogFragment extends GoPremiumWithListDialogFragment
{
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
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(2131689675, 2131689679, 2131689664, 2131689674, 2131689663));
        this.removeRandomItemsFromList(list, 2);
        return list;
    }
    
    @Override
    protected String getStringForConversionContext(final Resources resources, final ConversionContext conversionContext) {
        return conversionContext.getMessageRegisterResId(resources);
    }
    
    @Override
    protected int getTitleResId() {
        return 2131689677;
    }
}
