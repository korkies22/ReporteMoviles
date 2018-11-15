/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.modalfragments;

import android.content.res.Resources;
import de.cisha.chess.util.Logger;

public enum ConversionContext {
    TACTICS(new ConversionContextParams().setResIdStringRegister(2131689670).setLimitationRegister(3).setResIdStringPremium(2131689656).setLimitationPremium(5)),
    PLAYZONE(new ConversionContextParams().setResIdStringRegister(2131689665)),
    ANALYZE_ENGINE(new ConversionContextParams().setResIdStringRegister(2131689668).setLimitationRegister(8).setResIdStringPremium(2131689657).setLimitationPremium(12)),
    ANALYZE_ENGINE_VARIATION(new ConversionContextParams().setResIdStringRegister(2131689669).setResIdStringPremium(2131689658)),
    ANALYZE_TREE(new ConversionContextParams().setResIdStringRegister(2131689672).setLimitationRegister(3).setResIdStringPremium(2131689659).setLimitationPremium(6)),
    SAVED_ANALYZES(new ConversionContextParams().setResIdStringRegister(2131689671).setResIdStringPremium(2131689660).setLimitationPremium(10)),
    ACCOUNT_TYPE(new ConversionContextParams().setResIdStringRegister(2131689676)),
    PROFILE_PICTURE(new ConversionContextParams().setResIdStringRegister(2131689667)),
    VIDEO_SERIES_OVERVIEW(new ConversionContextParams().setResIdStringRegister(2131689666).setResIdStringPremium(2131689661));
    
    private ConversionContextParams _params;

    private ConversionContext(ConversionContextParams conversionContextParams) {
        this._params = conversionContextParams;
    }

    public String getMessagePremiumResId(Resources resources) {
        if (this._params._resStringIdPremium < 0) {
            Logger.getInstance().error(ConversionContext.class.getName(), "resources not declared");
            return "";
        }
        if (this._params._limitationToReplacePremium != null) {
            return resources.getString(this._params._resStringIdPremium, new Object[]{this._params._limitationToReplacePremium});
        }
        return resources.getString(this._params._resStringIdPremium);
    }

    public String getMessageRegisterResId(Resources resources) {
        if (this._params._resStringIdRegister < 0) {
            Logger.getInstance().error(ConversionContext.class.getName(), "resources not declared");
            return "";
        }
        if (this._params._limitationToReplaceRegister != null) {
            return resources.getString(this._params._resStringIdRegister, new Object[]{this._params._limitationToReplaceRegister});
        }
        return resources.getString(this._params._resStringIdRegister);
    }

    private static class ConversionContextParams {
        private String _limitationToReplacePremium;
        private String _limitationToReplaceRegister;
        private int _resStringIdPremium = -1;
        private int _resStringIdRegister = -1;

        ConversionContextParams() {
        }

        ConversionContextParams setLimitationPremium(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append("");
            this._limitationToReplacePremium = stringBuilder.toString();
            return this;
        }

        ConversionContextParams setLimitationRegister(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append("");
            this._limitationToReplaceRegister = stringBuilder.toString();
            return this;
        }

        ConversionContextParams setResIdStringPremium(int n) {
            this._resStringIdPremium = n;
            return this;
        }

        ConversionContextParams setResIdStringRegister(int n) {
            this._resStringIdRegister = n;
            return this;
        }
    }

}
