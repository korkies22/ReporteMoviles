/*
 * Decompiled with CFR 0_134.
 */
package com.neovisionaries.i18n;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum LocaleCode {
    ar(LanguageCode.ar, null),
    ar_AE(LanguageCode.ar, CountryCode.AE),
    ar_BH(LanguageCode.ar, CountryCode.BH),
    ar_DZ(LanguageCode.ar, CountryCode.DZ),
    ar_EG(LanguageCode.ar, CountryCode.EG),
    ar_IQ(LanguageCode.ar, CountryCode.IQ),
    ar_JO(LanguageCode.ar, CountryCode.JO),
    ar_KW(LanguageCode.ar, CountryCode.KW),
    ar_LB(LanguageCode.ar, CountryCode.LB),
    ar_LY(LanguageCode.ar, CountryCode.LY),
    ar_MA(LanguageCode.ar, CountryCode.MA),
    ar_OM(LanguageCode.ar, CountryCode.OM),
    ar_QA(LanguageCode.ar, CountryCode.QA),
    ar_SA(LanguageCode.ar, CountryCode.SA),
    ar_SD(LanguageCode.ar, CountryCode.SD),
    ar_SY(LanguageCode.ar, CountryCode.SY),
    ar_TN(LanguageCode.ar, CountryCode.TN),
    ar_YE(LanguageCode.ar, CountryCode.YE),
    be(LanguageCode.be, null),
    be_BY(LanguageCode.be, CountryCode.BY),
    bg(LanguageCode.bg, null),
    bg_BG(LanguageCode.bg, CountryCode.BG),
    ca(LanguageCode.ca, null),
    ca_ES(LanguageCode.ca, CountryCode.ES),
    cs(LanguageCode.cs, null),
    cs_CZ(LanguageCode.cs, CountryCode.CZ),
    da(LanguageCode.da, null),
    da_DK(LanguageCode.da, CountryCode.DK),
    de(LanguageCode.de, null){

        @Override
        public Locale toLocale() {
            return Locale.GERMAN;
        }
    }
    ,
    de_AT(LanguageCode.de, CountryCode.AT),
    de_CH(LanguageCode.de, CountryCode.CH),
    de_DE(LanguageCode.de, CountryCode.DE),
    de_LU(LanguageCode.de, CountryCode.LU),
    el(LanguageCode.el, null),
    el_CY(LanguageCode.el, CountryCode.CY),
    el_GR(LanguageCode.el, CountryCode.GR),
    en(LanguageCode.en, null){

        @Override
        public Locale toLocale() {
            return Locale.ENGLISH;
        }
    }
    ,
    en_AU(LanguageCode.en, CountryCode.AU),
    en_CA(LanguageCode.en, CountryCode.CA),
    en_GB(LanguageCode.en, CountryCode.GB),
    en_IE(LanguageCode.en, CountryCode.IE),
    en_IN(LanguageCode.en, CountryCode.IN),
    en_MT(LanguageCode.en, CountryCode.MT),
    en_NZ(LanguageCode.en, CountryCode.NZ),
    en_PH(LanguageCode.en, CountryCode.PH),
    en_SG(LanguageCode.en, CountryCode.SG),
    en_US(LanguageCode.en, CountryCode.US),
    en_ZA(LanguageCode.en, CountryCode.ZA),
    es(LanguageCode.es, null),
    es_AR(LanguageCode.es, CountryCode.AR),
    es_BO(LanguageCode.es, CountryCode.BO),
    es_CL(LanguageCode.es, CountryCode.CL),
    es_CO(LanguageCode.es, CountryCode.CO),
    es_CR(LanguageCode.es, CountryCode.CR),
    es_DO(LanguageCode.es, CountryCode.DO),
    es_EC(LanguageCode.es, CountryCode.EC),
    es_ES(LanguageCode.es, CountryCode.ES),
    es_GT(LanguageCode.es, CountryCode.GT),
    es_HN(LanguageCode.es, CountryCode.HN),
    es_MX(LanguageCode.es, CountryCode.MX),
    es_NI(LanguageCode.es, CountryCode.NI),
    es_PA(LanguageCode.es, CountryCode.PA),
    es_PE(LanguageCode.es, CountryCode.PE),
    es_PR(LanguageCode.es, CountryCode.PR),
    es_PY(LanguageCode.es, CountryCode.PY),
    es_SV(LanguageCode.es, CountryCode.SV),
    es_US(LanguageCode.es, CountryCode.US),
    es_UY(LanguageCode.es, CountryCode.UY),
    es_VE(LanguageCode.es, CountryCode.VE),
    et(LanguageCode.et, null),
    et_EE(LanguageCode.et, CountryCode.EE),
    fi(LanguageCode.fi, null),
    fi_FI(LanguageCode.fi, CountryCode.FI),
    fr(LanguageCode.fr, null){

        @Override
        public Locale toLocale() {
            return Locale.FRENCH;
        }
    }
    ,
    fr_BE(LanguageCode.fr, CountryCode.BE),
    fr_CA(LanguageCode.fr, CountryCode.CA){

        @Override
        public Locale toLocale() {
            return Locale.CANADA_FRENCH;
        }
    }
    ,
    fr_CH(LanguageCode.fr, CountryCode.CH),
    fr_FR(LanguageCode.fr, CountryCode.FR),
    fr_LU(LanguageCode.fr, CountryCode.LU),
    ga(LanguageCode.ga, null),
    ga_IE(LanguageCode.ga, CountryCode.IE),
    he(LanguageCode.he, null),
    he_IL(LanguageCode.he, CountryCode.IL),
    hi_IN(LanguageCode.hi, CountryCode.IN),
    hr(LanguageCode.hr, null),
    hr_HR(LanguageCode.hr, CountryCode.HR),
    hu(LanguageCode.hu, null),
    hu_HU(LanguageCode.hu, CountryCode.HU),
    id(LanguageCode.id, null),
    id_ID(LanguageCode.id, CountryCode.ID),
    is(LanguageCode.is, null),
    is_IS(LanguageCode.is, CountryCode.IS),
    it(LanguageCode.it, null){

        @Override
        public Locale toLocale() {
            return Locale.ITALIAN;
        }
    }
    ,
    it_CH(LanguageCode.it, CountryCode.CH),
    it_IT(LanguageCode.it, CountryCode.IT),
    ja(LanguageCode.ja, null){

        @Override
        public Locale toLocale() {
            return Locale.JAPANESE;
        }
    }
    ,
    ja_JP(LanguageCode.ja, CountryCode.JP),
    ko(LanguageCode.ko, null){

        @Override
        public Locale toLocale() {
            return Locale.KOREAN;
        }
    }
    ,
    ko_KR(LanguageCode.ko, CountryCode.KR),
    lt(LanguageCode.lt, null),
    lt_LT(LanguageCode.lt, CountryCode.LT),
    lv(LanguageCode.lv, null),
    lv_LV(LanguageCode.lv, CountryCode.LV),
    mk(LanguageCode.mk, null),
    mk_MK(LanguageCode.mk, CountryCode.MK),
    ms(LanguageCode.ms, null),
    ms_MY(LanguageCode.ms, CountryCode.MY),
    mt(LanguageCode.mt, null),
    mt_MT(LanguageCode.mt, CountryCode.MT),
    nl(LanguageCode.nl, null),
    nl_BE(LanguageCode.nl, CountryCode.BE),
    nl_NL(LanguageCode.nl, CountryCode.NL),
    nn_NO(LanguageCode.nn, CountryCode.NO),
    no(LanguageCode.no, null),
    no_NO(LanguageCode.no, CountryCode.NO),
    pl(LanguageCode.pl, null),
    pl_PL(LanguageCode.pl, CountryCode.PL),
    pt(LanguageCode.pt, null),
    pt_BR(LanguageCode.pt, CountryCode.BR),
    pt_PT(LanguageCode.pt, CountryCode.PT),
    ro(LanguageCode.ro, null),
    ro_RO(LanguageCode.ro, CountryCode.RO),
    ru(LanguageCode.ru, null),
    ru_RU(LanguageCode.ru, CountryCode.RU),
    sk(LanguageCode.sk, null),
    sk_SK(LanguageCode.sk, CountryCode.SK),
    sl(LanguageCode.sl, null),
    sl_SI(LanguageCode.sl, CountryCode.SI),
    sq(LanguageCode.sq, null),
    sq_AL(LanguageCode.sq, CountryCode.AL),
    sr(LanguageCode.sr, null),
    sr_BA(LanguageCode.sr, CountryCode.BA),
    sr_CS(LanguageCode.sr, CountryCode.RS),
    sr_ME(LanguageCode.sr, CountryCode.ME),
    sr_RS(LanguageCode.sr, CountryCode.RS),
    sv(LanguageCode.sv, null),
    sv_SE(LanguageCode.sv, CountryCode.SE),
    th(LanguageCode.th, null),
    th_TH(LanguageCode.th, CountryCode.TH),
    tr(LanguageCode.tr, null),
    tr_TR(LanguageCode.tr, CountryCode.TR),
    uk(LanguageCode.uk, null),
    uk_UA(LanguageCode.uk, CountryCode.UA),
    vi(LanguageCode.vi, null),
    vi_VN(LanguageCode.vi, CountryCode.VN),
    zh(LanguageCode.zh, null){

        @Override
        public Locale toLocale() {
            return Locale.CHINESE;
        }
    }
    ,
    zh_CN(LanguageCode.zh, CountryCode.CN){

        @Override
        public Locale toLocale() {
            return Locale.SIMPLIFIED_CHINESE;
        }
    }
    ,
    zh_HK(LanguageCode.zh, CountryCode.HK),
    zh_SG(LanguageCode.zh, CountryCode.SG),
    zh_TW(LanguageCode.zh, CountryCode.TW){

        @Override
        public Locale toLocale() {
            return Locale.TRADITIONAL_CHINESE;
        }
    };
    
    private final CountryCode country;
    private final LanguageCode language;
    private final String string;

    private LocaleCode(LanguageCode languageCode, CountryCode countryCode) {
        this.language = languageCode;
        this.country = countryCode;
        if (countryCode == null) {
            this.string = languageCode.name();
            return;
        }
        charSequence = new StringBuilder();
        charSequence.append(languageCode.name());
        charSequence.append("-");
        charSequence.append(countryCode.name());
        this.string = charSequence.toString();
    }

    public static LocaleCode getByCode(String string) {
        return LocaleCode.getByCode(string, false);
    }

    public static LocaleCode getByCode(String string, String string2) {
        return LocaleCode.getByCode(string, string2, false);
    }

    public static LocaleCode getByCode(String string, String string2, boolean bl) {
        if ((string = LanguageCode.canonicalize(string, bl)) == null) {
            return null;
        }
        if ((string2 = CountryCode.canonicalize(string2, bl)) == null) {
            return LocaleCode.getByEnumName(string);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("_");
        stringBuilder.append(string2);
        return LocaleCode.getByEnumName(stringBuilder.toString());
    }

    public static LocaleCode getByCode(String string, boolean bl) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        if (n != 2) {
            if (n != 5) {
                return null;
            }
            return LocaleCode.getByCode5(string, bl);
        }
        return LocaleCode.getByCode(string, null, bl);
    }

    private static LocaleCode getByCode5(String string, boolean bl) {
        char c = string.charAt(2);
        if (c == '_') {
            if (bl) {
                return LocaleCode.getByEnumName(string);
            }
        } else if (c != '-') {
            return null;
        }
        return LocaleCode.getByCode(string.substring(0, 2), string.substring(3), bl);
    }

    public static List<LocaleCode> getByCountry(CountryCode countryCode) {
        ArrayList<LocaleCode> arrayList = new ArrayList<LocaleCode>();
        if (countryCode == null) {
            return arrayList;
        }
        for (LocaleCode localeCode : LocaleCode.values()) {
            if (localeCode.getCountry() != countryCode) continue;
            arrayList.add(localeCode);
        }
        return arrayList;
    }

    public static List<LocaleCode> getByCountry(String string) {
        return LocaleCode.getByCountry(string, false);
    }

    public static List<LocaleCode> getByCountry(String string, boolean bl) {
        return LocaleCode.getByCountry(CountryCode.getByCode(string, bl));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static LocaleCode getByEnumName(String object) {
        try {
            return Enum.valueOf(LocaleCode.class, object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public static List<LocaleCode> getByLanguage(LanguageCode languageCode) {
        ArrayList<LocaleCode> arrayList = new ArrayList<LocaleCode>();
        if (languageCode == null) {
            return arrayList;
        }
        for (LocaleCode localeCode : LocaleCode.values()) {
            if (localeCode.getLanguage() != languageCode) continue;
            arrayList.add(localeCode);
        }
        return arrayList;
    }

    public static List<LocaleCode> getByLanguage(String string) {
        return LocaleCode.getByLanguage(string, false);
    }

    public static List<LocaleCode> getByLanguage(String string, boolean bl) {
        return LocaleCode.getByLanguage(LanguageCode.getByCode(string, bl));
    }

    public static LocaleCode getByLocale(Locale locale) {
        if (locale == null) {
            return null;
        }
        return LocaleCode.getByCode(locale.getLanguage(), locale.getCountry(), true);
    }

    public CountryCode getCountry() {
        return this.country;
    }

    public LanguageCode getLanguage() {
        return this.language;
    }

    public Locale toLocale() {
        if (this.country != null) {
            return new Locale(this.language.name(), this.country.name());
        }
        return new Locale(this.language.name());
    }

    public String toString() {
        return this.string;
    }

}
