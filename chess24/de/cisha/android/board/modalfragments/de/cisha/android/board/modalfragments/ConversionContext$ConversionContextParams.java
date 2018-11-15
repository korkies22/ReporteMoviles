/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.modalfragments;

import de.cisha.android.board.modalfragments.ConversionContext;

private static class ConversionContext.ConversionContextParams {
    private String _limitationToReplacePremium;
    private String _limitationToReplaceRegister;
    private int _resStringIdPremium = -1;
    private int _resStringIdRegister = -1;

    ConversionContext.ConversionContextParams() {
    }

    static /* synthetic */ int access$000(ConversionContext.ConversionContextParams conversionContextParams) {
        return conversionContextParams._resStringIdRegister;
    }

    static /* synthetic */ String access$100(ConversionContext.ConversionContextParams conversionContextParams) {
        return conversionContextParams._limitationToReplaceRegister;
    }

    static /* synthetic */ int access$200(ConversionContext.ConversionContextParams conversionContextParams) {
        return conversionContextParams._resStringIdPremium;
    }

    static /* synthetic */ String access$300(ConversionContext.ConversionContextParams conversionContextParams) {
        return conversionContextParams._limitationToReplacePremium;
    }

    ConversionContext.ConversionContextParams setLimitationPremium(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("");
        this._limitationToReplacePremium = stringBuilder.toString();
        return this;
    }

    ConversionContext.ConversionContextParams setLimitationRegister(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("");
        this._limitationToReplaceRegister = stringBuilder.toString();
        return this;
    }

    ConversionContext.ConversionContextParams setResIdStringPremium(int n) {
        this._resStringIdPremium = n;
        return this;
    }

    ConversionContext.ConversionContextParams setResIdStringRegister(int n) {
        this._resStringIdRegister = n;
        return this;
    }
}
