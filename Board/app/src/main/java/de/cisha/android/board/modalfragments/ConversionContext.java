// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import de.cisha.chess.util.Logger;
import android.content.res.Resources;

public enum ConversionContext
{
    ACCOUNT_TYPE(new ConversionContextParams().setResIdStringRegister(2131689676)), 
    ANALYZE_ENGINE(new ConversionContextParams().setResIdStringRegister(2131689668).setLimitationRegister(8).setResIdStringPremium(2131689657).setLimitationPremium(12)), 
    ANALYZE_ENGINE_VARIATION(new ConversionContextParams().setResIdStringRegister(2131689669).setResIdStringPremium(2131689658)), 
    ANALYZE_TREE(new ConversionContextParams().setResIdStringRegister(2131689672).setLimitationRegister(3).setResIdStringPremium(2131689659).setLimitationPremium(6)), 
    PLAYZONE(new ConversionContextParams().setResIdStringRegister(2131689665)), 
    PROFILE_PICTURE(new ConversionContextParams().setResIdStringRegister(2131689667)), 
    SAVED_ANALYZES(new ConversionContextParams().setResIdStringRegister(2131689671).setResIdStringPremium(2131689660).setLimitationPremium(10)), 
    TACTICS(new ConversionContextParams().setResIdStringRegister(2131689670).setLimitationRegister(3).setResIdStringPremium(2131689656).setLimitationPremium(5)), 
    VIDEO_SERIES_OVERVIEW(new ConversionContextParams().setResIdStringRegister(2131689666).setResIdStringPremium(2131689661));
    
    private ConversionContextParams _params;
    
    private ConversionContext(final ConversionContextParams params) {
        this._params = params;
    }
    
    public String getMessagePremiumResId(final Resources resources) {
        if (this._params._resStringIdPremium < 0) {
            Logger.getInstance().error(ConversionContext.class.getName(), "resources not declared");
            return "";
        }
        if (this._params._limitationToReplacePremium != null) {
            return resources.getString(this._params._resStringIdPremium, new Object[] { this._params._limitationToReplacePremium });
        }
        return resources.getString(this._params._resStringIdPremium);
    }
    
    public String getMessageRegisterResId(final Resources resources) {
        if (this._params._resStringIdRegister < 0) {
            Logger.getInstance().error(ConversionContext.class.getName(), "resources not declared");
            return "";
        }
        if (this._params._limitationToReplaceRegister != null) {
            return resources.getString(this._params._resStringIdRegister, new Object[] { this._params._limitationToReplaceRegister });
        }
        return resources.getString(this._params._resStringIdRegister);
    }
    
    private static class ConversionContextParams
    {
        private String _limitationToReplacePremium;
        private String _limitationToReplaceRegister;
        private int _resStringIdPremium;
        private int _resStringIdRegister;
        
        ConversionContextParams() {
            this._resStringIdRegister = -1;
            this._resStringIdPremium = -1;
        }
        
        ConversionContextParams setLimitationPremium(final int n) {
            final StringBuilder sb = new StringBuilder();
            sb.append(n);
            sb.append("");
            this._limitationToReplacePremium = sb.toString();
            return this;
        }
        
        ConversionContextParams setLimitationRegister(final int n) {
            final StringBuilder sb = new StringBuilder();
            sb.append(n);
            sb.append("");
            this._limitationToReplaceRegister = sb.toString();
            return this;
        }
        
        ConversionContextParams setResIdStringPremium(final int resStringIdPremium) {
            this._resStringIdPremium = resStringIdPremium;
            return this;
        }
        
        ConversionContextParams setResIdStringRegister(final int resStringIdRegister) {
            this._resStringIdRegister = resStringIdRegister;
            return this;
        }
    }
}
