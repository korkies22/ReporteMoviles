/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.res.Resources
 */
package com.neovisionaries.i18n;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import de.cisha.android.board.R;
import de.cisha.chess.model.Country;
import java.lang.reflect.Field;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CountryCode
extends Enum<CountryCode>
implements Country {
    private static final /* synthetic */ CountryCode[] $VALUES;
    public static final /* enum */ CountryCode AD = new CountryCode("Andorra", "AND", 20, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AE = new CountryCode("United Arab Emirates", "ARE", 784, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AF = new CountryCode("Afghanistan", "AFG", 4, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AG = new CountryCode("Antigua and Barbuda", "ATG", 28, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AI = new CountryCode("Anguilla", "AIA", 660, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AL = new CountryCode("Albania", "ALB", 8, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AM = new CountryCode("Armenia", "ARM", 51, Assignment.OFFICIALLY_ASSIGNED);
    public static final /* enum */ CountryCode AN;
    public static final /* enum */ CountryCode AO;
    public static final /* enum */ CountryCode AQ;
    public static final /* enum */ CountryCode AR;
    public static final /* enum */ CountryCode AS;
    public static final /* enum */ CountryCode AT;
    public static final /* enum */ CountryCode AU;
    public static final /* enum */ CountryCode AW;
    public static final /* enum */ CountryCode AX;
    public static final /* enum */ CountryCode AZ;
    public static final /* enum */ CountryCode BA;
    public static final /* enum */ CountryCode BB;
    public static final /* enum */ CountryCode BD;
    public static final /* enum */ CountryCode BE;
    public static final /* enum */ CountryCode BF;
    public static final /* enum */ CountryCode BG;
    public static final /* enum */ CountryCode BH;
    public static final /* enum */ CountryCode BI;
    public static final /* enum */ CountryCode BJ;
    public static final /* enum */ CountryCode BL;
    public static final /* enum */ CountryCode BM;
    public static final /* enum */ CountryCode BN;
    public static final /* enum */ CountryCode BO;
    public static final /* enum */ CountryCode BQ;
    public static final /* enum */ CountryCode BR;
    public static final /* enum */ CountryCode BS;
    public static final /* enum */ CountryCode BT;
    public static final /* enum */ CountryCode BV;
    public static final /* enum */ CountryCode BW;
    public static final /* enum */ CountryCode BY;
    public static final /* enum */ CountryCode BZ;
    public static final /* enum */ CountryCode CA;
    public static final /* enum */ CountryCode CC;
    public static final /* enum */ CountryCode CD;
    public static final /* enum */ CountryCode CF;
    public static final /* enum */ CountryCode CG;
    public static final /* enum */ CountryCode CH;
    public static final /* enum */ CountryCode CI;
    public static final /* enum */ CountryCode CK;
    public static final /* enum */ CountryCode CL;
    public static final /* enum */ CountryCode CM;
    public static final /* enum */ CountryCode CN;
    public static final /* enum */ CountryCode CO;
    public static final /* enum */ CountryCode CR;
    public static final /* enum */ CountryCode CU;
    public static final /* enum */ CountryCode CV;
    public static final /* enum */ CountryCode CW;
    public static final /* enum */ CountryCode CX;
    public static final /* enum */ CountryCode CY;
    public static final /* enum */ CountryCode CZ;
    public static final /* enum */ CountryCode DE;
    public static final /* enum */ CountryCode DJ;
    public static final /* enum */ CountryCode DK;
    public static final /* enum */ CountryCode DM;
    public static final /* enum */ CountryCode DO;
    public static final /* enum */ CountryCode DZ;
    public static final /* enum */ CountryCode EC;
    public static final /* enum */ CountryCode EE;
    public static final /* enum */ CountryCode EG;
    public static final /* enum */ CountryCode EH;
    public static final /* enum */ CountryCode ER;
    public static final /* enum */ CountryCode ES;
    public static final /* enum */ CountryCode ET;
    public static final /* enum */ CountryCode FI;
    public static final /* enum */ CountryCode FJ;
    public static final /* enum */ CountryCode FK;
    public static final /* enum */ CountryCode FM;
    public static final /* enum */ CountryCode FO;
    public static final /* enum */ CountryCode FR;
    public static final /* enum */ CountryCode GA;
    public static final /* enum */ CountryCode GB;
    public static final /* enum */ CountryCode GD;
    public static final /* enum */ CountryCode GE;
    public static final /* enum */ CountryCode GF;
    public static final /* enum */ CountryCode GG;
    public static final /* enum */ CountryCode GH;
    public static final /* enum */ CountryCode GI;
    public static final /* enum */ CountryCode GL;
    public static final /* enum */ CountryCode GM;
    public static final /* enum */ CountryCode GN;
    public static final /* enum */ CountryCode GP;
    public static final /* enum */ CountryCode GQ;
    public static final /* enum */ CountryCode GR;
    public static final /* enum */ CountryCode GS;
    public static final /* enum */ CountryCode GT;
    public static final /* enum */ CountryCode GU;
    public static final /* enum */ CountryCode GW;
    public static final /* enum */ CountryCode GY;
    public static final /* enum */ CountryCode HK;
    public static final /* enum */ CountryCode HM;
    public static final /* enum */ CountryCode HN;
    public static final /* enum */ CountryCode HR;
    public static final /* enum */ CountryCode HT;
    public static final /* enum */ CountryCode HU;
    public static final /* enum */ CountryCode ID;
    public static final /* enum */ CountryCode IE;
    public static final /* enum */ CountryCode IL;
    public static final /* enum */ CountryCode IM;
    public static final /* enum */ CountryCode IN;
    public static final /* enum */ CountryCode IO;
    public static final /* enum */ CountryCode IQ;
    public static final /* enum */ CountryCode IR;
    public static final /* enum */ CountryCode IS;
    public static final /* enum */ CountryCode IT;
    public static final /* enum */ CountryCode JE;
    public static final /* enum */ CountryCode JM;
    public static final /* enum */ CountryCode JO;
    public static final /* enum */ CountryCode JP;
    public static final /* enum */ CountryCode KE;
    public static final /* enum */ CountryCode KG;
    public static final /* enum */ CountryCode KH;
    public static final /* enum */ CountryCode KI;
    public static final /* enum */ CountryCode KM;
    public static final /* enum */ CountryCode KN;
    public static final /* enum */ CountryCode KP;
    public static final /* enum */ CountryCode KR;
    public static final /* enum */ CountryCode KW;
    public static final /* enum */ CountryCode KY;
    public static final /* enum */ CountryCode KZ;
    public static final /* enum */ CountryCode LA;
    public static final /* enum */ CountryCode LB;
    public static final /* enum */ CountryCode LC;
    public static final /* enum */ CountryCode LI;
    public static final /* enum */ CountryCode LK;
    public static final /* enum */ CountryCode LR;
    public static final /* enum */ CountryCode LS;
    public static final /* enum */ CountryCode LT;
    public static final /* enum */ CountryCode LU;
    public static final /* enum */ CountryCode LV;
    public static final /* enum */ CountryCode LY;
    public static final /* enum */ CountryCode MA;
    public static final /* enum */ CountryCode MC;
    public static final /* enum */ CountryCode MD;
    public static final /* enum */ CountryCode ME;
    public static final /* enum */ CountryCode MF;
    public static final /* enum */ CountryCode MG;
    public static final /* enum */ CountryCode MH;
    public static final /* enum */ CountryCode MK;
    public static final /* enum */ CountryCode ML;
    public static final /* enum */ CountryCode MM;
    public static final /* enum */ CountryCode MN;
    public static final /* enum */ CountryCode MO;
    public static final /* enum */ CountryCode MP;
    public static final /* enum */ CountryCode MQ;
    public static final /* enum */ CountryCode MR;
    public static final /* enum */ CountryCode MS;
    public static final /* enum */ CountryCode MT;
    public static final /* enum */ CountryCode MU;
    public static final /* enum */ CountryCode MV;
    public static final /* enum */ CountryCode MW;
    public static final /* enum */ CountryCode MX;
    public static final /* enum */ CountryCode MY;
    public static final /* enum */ CountryCode MZ;
    public static final /* enum */ CountryCode NA;
    public static final /* enum */ CountryCode NC;
    public static final /* enum */ CountryCode NE;
    public static final /* enum */ CountryCode NF;
    public static final /* enum */ CountryCode NG;
    public static final /* enum */ CountryCode NI;
    public static final /* enum */ CountryCode NL;
    public static final /* enum */ CountryCode NO;
    public static final /* enum */ CountryCode NP;
    public static final /* enum */ CountryCode NR;
    public static final /* enum */ CountryCode NU;
    public static final /* enum */ CountryCode NZ;
    public static final /* enum */ CountryCode OM;
    public static final /* enum */ CountryCode PA;
    public static final /* enum */ CountryCode PE;
    public static final /* enum */ CountryCode PF;
    public static final /* enum */ CountryCode PG;
    public static final /* enum */ CountryCode PH;
    public static final /* enum */ CountryCode PK;
    public static final /* enum */ CountryCode PL;
    public static final /* enum */ CountryCode PM;
    public static final /* enum */ CountryCode PN;
    public static final /* enum */ CountryCode PR;
    public static final /* enum */ CountryCode PS;
    public static final /* enum */ CountryCode PT;
    public static final /* enum */ CountryCode PW;
    public static final /* enum */ CountryCode PY;
    public static final /* enum */ CountryCode QA;
    public static final /* enum */ CountryCode RE;
    public static final /* enum */ CountryCode RO;
    public static final /* enum */ CountryCode RS;
    public static final /* enum */ CountryCode RU;
    public static final /* enum */ CountryCode RW;
    public static final /* enum */ CountryCode SA;
    public static final /* enum */ CountryCode SB;
    public static final /* enum */ CountryCode SC;
    public static final /* enum */ CountryCode SD;
    public static final /* enum */ CountryCode SE;
    public static final /* enum */ CountryCode SG;
    public static final /* enum */ CountryCode SH;
    public static final /* enum */ CountryCode SI;
    public static final /* enum */ CountryCode SJ;
    public static final /* enum */ CountryCode SK;
    public static final /* enum */ CountryCode SL;
    public static final /* enum */ CountryCode SM;
    public static final /* enum */ CountryCode SN;
    public static final /* enum */ CountryCode SO;
    public static final /* enum */ CountryCode SR;
    public static final /* enum */ CountryCode SS;
    public static final /* enum */ CountryCode ST;
    public static final /* enum */ CountryCode SV;
    public static final /* enum */ CountryCode SX;
    public static final /* enum */ CountryCode SY;
    public static final /* enum */ CountryCode SZ;
    public static final /* enum */ CountryCode TC;
    public static final /* enum */ CountryCode TD;
    public static final /* enum */ CountryCode TF;
    public static final /* enum */ CountryCode TG;
    public static final /* enum */ CountryCode TH;
    public static final /* enum */ CountryCode TJ;
    public static final /* enum */ CountryCode TK;
    public static final /* enum */ CountryCode TL;
    public static final /* enum */ CountryCode TM;
    public static final /* enum */ CountryCode TN;
    public static final /* enum */ CountryCode TO;
    public static final /* enum */ CountryCode TR;
    public static final /* enum */ CountryCode TT;
    public static final /* enum */ CountryCode TV;
    public static final /* enum */ CountryCode TW;
    public static final /* enum */ CountryCode TZ;
    public static final /* enum */ CountryCode UA;
    public static final /* enum */ CountryCode UG;
    public static final /* enum */ CountryCode UM;
    public static final /* enum */ CountryCode US;
    public static final /* enum */ CountryCode UY;
    public static final /* enum */ CountryCode UZ;
    public static final /* enum */ CountryCode VA;
    public static final /* enum */ CountryCode VC;
    public static final /* enum */ CountryCode VE;
    public static final /* enum */ CountryCode VG;
    public static final /* enum */ CountryCode VI;
    public static final /* enum */ CountryCode VN;
    public static final /* enum */ CountryCode VU;
    public static final /* enum */ CountryCode WF;
    public static final /* enum */ CountryCode WS;
    public static final /* enum */ CountryCode XB;
    public static final /* enum */ CountryCode XD;
    public static final /* enum */ CountryCode XE;
    public static final /* enum */ CountryCode XN;
    public static final /* enum */ CountryCode XP;
    public static final /* enum */ CountryCode XS;
    public static final /* enum */ CountryCode XW;
    public static final /* enum */ CountryCode YE;
    public static final /* enum */ CountryCode YT;
    public static final /* enum */ CountryCode ZA;
    public static final /* enum */ CountryCode ZM;
    public static final /* enum */ CountryCode ZW;
    private static final Map<String, CountryCode> alpha3Map;
    private static final Map<String, CountryCode> iocMap;
    private static final Map<Integer, CountryCode> numericMap;
    private final String alpha3;
    private final Assignment assignment;
    private final String name;
    private final int numeric;

    static {
        AO = new CountryCode("Angola", "AGO", 24, Assignment.OFFICIALLY_ASSIGNED);
        AQ = new CountryCode("Antarctica", "ATA", 10, Assignment.OFFICIALLY_ASSIGNED);
        AR = new CountryCode("Argentina", "ARG", 32, Assignment.OFFICIALLY_ASSIGNED);
        AS = new CountryCode("American Samoa", "ASM", 16, Assignment.OFFICIALLY_ASSIGNED);
        AT = new CountryCode("Austria", "AUT", 40, Assignment.OFFICIALLY_ASSIGNED);
        AU = new CountryCode("Australia", "AUS", 36, Assignment.OFFICIALLY_ASSIGNED);
        AW = new CountryCode("Aruba", "ABW", 533, Assignment.OFFICIALLY_ASSIGNED);
        AX = new CountryCode("\u212bland Islands", "ALA", 248, Assignment.OFFICIALLY_ASSIGNED);
        AZ = new CountryCode("Azerbaijan", "AZE", 31, Assignment.OFFICIALLY_ASSIGNED);
        BA = new CountryCode("Bosnia and Herzegovina", "BIH", 70, Assignment.OFFICIALLY_ASSIGNED);
        BB = new CountryCode("Barbados", "BRB", 52, Assignment.OFFICIALLY_ASSIGNED);
        BD = new CountryCode("Bangladesh", "BGD", 50, Assignment.OFFICIALLY_ASSIGNED);
        BE = new CountryCode("Belgium", "BEL", 56, Assignment.OFFICIALLY_ASSIGNED);
        BF = new CountryCode("Burkina Faso", "BFA", 854, Assignment.OFFICIALLY_ASSIGNED);
        BG = new CountryCode("Bulgaria", "BGR", 100, Assignment.OFFICIALLY_ASSIGNED);
        BH = new CountryCode("Bahrain", "BHR", 48, Assignment.OFFICIALLY_ASSIGNED);
        BI = new CountryCode("Burundi", "BDI", 108, Assignment.OFFICIALLY_ASSIGNED);
        BJ = new CountryCode("Benin", "BEN", 204, Assignment.OFFICIALLY_ASSIGNED);
        BL = new CountryCode("Saint Barth\u00e9lemy", "BLM", 652, Assignment.OFFICIALLY_ASSIGNED);
        BM = new CountryCode("Bermuda", "BMU", 60, Assignment.OFFICIALLY_ASSIGNED);
        BN = new CountryCode("Brunei Darussalam", "BRN", 96, Assignment.OFFICIALLY_ASSIGNED);
        BO = new CountryCode("Plurinational State of Bolivia", "BOL", 68, Assignment.OFFICIALLY_ASSIGNED);
        BQ = new CountryCode("Bonaire, Sint Eustatius and Saba", "BES", 535, Assignment.OFFICIALLY_ASSIGNED);
        BR = new CountryCode("Brazil", "BRA", 76, Assignment.OFFICIALLY_ASSIGNED);
        BS = new CountryCode("Bahamas", "BHS", 44, Assignment.OFFICIALLY_ASSIGNED);
        BT = new CountryCode("Bhutan", "BTN", 64, Assignment.OFFICIALLY_ASSIGNED);
        BV = new CountryCode("Bouvet Island", "BVT", 74, Assignment.OFFICIALLY_ASSIGNED);
        BW = new CountryCode("Botswana", "BWA", 72, Assignment.OFFICIALLY_ASSIGNED);
        BY = new CountryCode("Belarus", "BLR", 112, Assignment.OFFICIALLY_ASSIGNED);
        BZ = new CountryCode("Belize", "BLZ", 84, Assignment.OFFICIALLY_ASSIGNED);
        CA = new CountryCode("CA", 37, "Canada", "CAN", 124, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.CANADA;
            }
        };
        CC = new CountryCode("Cocos Islands", "CCK", 166, Assignment.OFFICIALLY_ASSIGNED);
        CD = new CountryCode("The Democratic Republic of the Congo", "COD", 180, Assignment.OFFICIALLY_ASSIGNED);
        CF = new CountryCode("Central African Republic", "CAF", 140, Assignment.OFFICIALLY_ASSIGNED);
        CG = new CountryCode("Congo", "COG", 178, Assignment.OFFICIALLY_ASSIGNED);
        CH = new CountryCode("Switzerland", "CHE", 756, Assignment.OFFICIALLY_ASSIGNED);
        CI = new CountryCode("C\u00f4te d'Ivoire", "CIV", 384, Assignment.OFFICIALLY_ASSIGNED);
        CK = new CountryCode("Cook Islands", "COK", 184, Assignment.OFFICIALLY_ASSIGNED);
        CL = new CountryCode("Chile", "CHL", 152, Assignment.OFFICIALLY_ASSIGNED);
        CM = new CountryCode("Cameroon", "CMR", 120, Assignment.OFFICIALLY_ASSIGNED);
        CN = new CountryCode("CN", 47, "China", "CHN", 156, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.CHINA;
            }
        };
        CO = new CountryCode("Colombia", "COL", 170, Assignment.OFFICIALLY_ASSIGNED);
        CR = new CountryCode("Costa Rica", "CRI", 188, Assignment.OFFICIALLY_ASSIGNED);
        CU = new CountryCode("Cuba", "CUB", 192, Assignment.OFFICIALLY_ASSIGNED);
        CV = new CountryCode("Cape Verde", "CPV", 132, Assignment.OFFICIALLY_ASSIGNED);
        CW = new CountryCode("Cura\u00e7ao", "CUW", 531, Assignment.OFFICIALLY_ASSIGNED);
        CX = new CountryCode("Christmas Island", "CXR", 162, Assignment.OFFICIALLY_ASSIGNED);
        CY = new CountryCode("Cyprus", "CYP", 196, Assignment.OFFICIALLY_ASSIGNED);
        CZ = new CountryCode("Czech Republic", "CZE", 203, Assignment.OFFICIALLY_ASSIGNED);
        DE = new CountryCode("DE", 56, "Germany", "DEU", 276, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.GERMANY;
            }
        };
        DJ = new CountryCode("Djibouti", "DJI", 262, Assignment.OFFICIALLY_ASSIGNED);
        DK = new CountryCode("Denmark", "DNK", 208, Assignment.OFFICIALLY_ASSIGNED);
        DM = new CountryCode("Dominica", "DMA", 212, Assignment.OFFICIALLY_ASSIGNED);
        DO = new CountryCode("Dominican Republic", "DOM", 214, Assignment.OFFICIALLY_ASSIGNED);
        DZ = new CountryCode("Algeria", "DZA", 12, Assignment.OFFICIALLY_ASSIGNED);
        EC = new CountryCode("Ecuador", "ECU", 218, Assignment.OFFICIALLY_ASSIGNED);
        EE = new CountryCode("Estonia", "EST", 233, Assignment.OFFICIALLY_ASSIGNED);
        EG = new CountryCode("Egypt", "EGY", 818, Assignment.OFFICIALLY_ASSIGNED);
        EH = new CountryCode("Western Sahara", "ESH", 732, Assignment.OFFICIALLY_ASSIGNED);
        ER = new CountryCode("Eritrea", "ERI", 232, Assignment.OFFICIALLY_ASSIGNED);
        ES = new CountryCode("Spain", "ESP", 724, Assignment.OFFICIALLY_ASSIGNED);
        ET = new CountryCode("Ethiopia", "ETH", 231, Assignment.OFFICIALLY_ASSIGNED);
        FI = new CountryCode("Finland", "FIN", 246, Assignment.OFFICIALLY_ASSIGNED);
        FJ = new CountryCode("Fiji", "FJI", 242, Assignment.OFFICIALLY_ASSIGNED);
        FK = new CountryCode("Falkland Islands", "FLK", 238, Assignment.OFFICIALLY_ASSIGNED);
        FM = new CountryCode("Federated States of Micronesia", "FSM", 583, Assignment.OFFICIALLY_ASSIGNED);
        FO = new CountryCode("Faroe Islands", "FRO", 234, Assignment.OFFICIALLY_ASSIGNED);
        FR = new CountryCode("FR", 74, "France", "FRA", 250, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.FRANCE;
            }
        };
        GA = new CountryCode("Gabon", "GAB", 266, Assignment.OFFICIALLY_ASSIGNED);
        GB = new CountryCode("GB", 76, "United Kingdom", "GBR", 826, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.UK;
            }
        };
        GD = new CountryCode("Grenada", "GRD", 308, Assignment.OFFICIALLY_ASSIGNED);
        GE = new CountryCode("Georgia", "GEO", 268, Assignment.OFFICIALLY_ASSIGNED);
        GF = new CountryCode("French Guiana", "GUF", 254, Assignment.OFFICIALLY_ASSIGNED);
        GG = new CountryCode("Guemsey", "GGY", 831, Assignment.OFFICIALLY_ASSIGNED);
        GH = new CountryCode("Ghana", "GHA", 288, Assignment.OFFICIALLY_ASSIGNED);
        GI = new CountryCode("Gibraltar", "GIB", 292, Assignment.OFFICIALLY_ASSIGNED);
        GL = new CountryCode("Greenland", "GRL", 304, Assignment.OFFICIALLY_ASSIGNED);
        GM = new CountryCode("Gambia", "GMB", 270, Assignment.OFFICIALLY_ASSIGNED);
        GN = new CountryCode("Guinea", "GIN", 324, Assignment.OFFICIALLY_ASSIGNED);
        GP = new CountryCode("Guadeloupe", "GLP", 312, Assignment.OFFICIALLY_ASSIGNED);
        GQ = new CountryCode("GQ", 87, "Equatorial Guinea", "GNQ", 226, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public String getIOC() {
                return "GEQ";
            }
        };
        GR = new CountryCode("Greece", "GRC", 300, Assignment.OFFICIALLY_ASSIGNED);
        GS = new CountryCode("South Georgia and the South Sandwich Islands", "SGS", 239, Assignment.OFFICIALLY_ASSIGNED);
        GT = new CountryCode("Guatemala", "GTM", 320, Assignment.OFFICIALLY_ASSIGNED);
        GU = new CountryCode("Guam", "GUM", 316, Assignment.OFFICIALLY_ASSIGNED);
        GW = new CountryCode("Guinea-Bissau", "GNB", 624, Assignment.OFFICIALLY_ASSIGNED);
        GY = new CountryCode("Guyana", "GUY", 328, Assignment.OFFICIALLY_ASSIGNED);
        HK = new CountryCode("Hong Kong", "HKG", 344, Assignment.OFFICIALLY_ASSIGNED);
        HM = new CountryCode("Heard Island and McDonald Islands", "HMD", 334, Assignment.OFFICIALLY_ASSIGNED);
        HN = new CountryCode("Honduras", "HND", 340, Assignment.OFFICIALLY_ASSIGNED);
        HR = new CountryCode("Croatia", "HRV", 191, Assignment.OFFICIALLY_ASSIGNED);
        HT = new CountryCode("Haiti", "HTI", 332, Assignment.OFFICIALLY_ASSIGNED);
        HU = new CountryCode("Hungary", "HUN", 348, Assignment.OFFICIALLY_ASSIGNED);
        ID = new CountryCode("Indonesia", "IDN", 360, Assignment.OFFICIALLY_ASSIGNED);
        IE = new CountryCode("Ireland", "IRL", 372, Assignment.OFFICIALLY_ASSIGNED);
        IL = new CountryCode("Israel", "ISR", 376, Assignment.OFFICIALLY_ASSIGNED);
        IM = new CountryCode("Isle of Man", "IMN", 833, Assignment.OFFICIALLY_ASSIGNED);
        IN = new CountryCode("India", "IND", 356, Assignment.OFFICIALLY_ASSIGNED);
        IO = new CountryCode("British Indian Ocean Territory", "IOT", 86, Assignment.OFFICIALLY_ASSIGNED);
        IQ = new CountryCode("Iraq", "IRQ", 368, Assignment.OFFICIALLY_ASSIGNED);
        IR = new CountryCode("Islamic Republic of Iran", "IRN", 364, Assignment.OFFICIALLY_ASSIGNED);
        IS = new CountryCode("Iceland", "ISL", 352, Assignment.OFFICIALLY_ASSIGNED);
        IT = new CountryCode("IT", 109, "Italy", "ITA", 380, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.ITALY;
            }
        };
        JE = new CountryCode("Jersey", "JEY", 832, Assignment.OFFICIALLY_ASSIGNED);
        JM = new CountryCode("Jamaica", "JAM", 388, Assignment.OFFICIALLY_ASSIGNED);
        JO = new CountryCode("Jordan", "JOR", 400, Assignment.OFFICIALLY_ASSIGNED);
        JP = new CountryCode("JP", 113, "Japan", "JPN", 392, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.JAPAN;
            }
        };
        KE = new CountryCode("Kenya", "KEN", 404, Assignment.OFFICIALLY_ASSIGNED);
        KG = new CountryCode("Kyrgyzstan", "KGZ", 417, Assignment.OFFICIALLY_ASSIGNED);
        KH = new CountryCode("Cambodia", "KHM", 116, Assignment.OFFICIALLY_ASSIGNED);
        KI = new CountryCode("Kiribati", "KIR", 296, Assignment.OFFICIALLY_ASSIGNED);
        KM = new CountryCode("Comoros", "COM", 174, Assignment.OFFICIALLY_ASSIGNED);
        KN = new CountryCode("Saint Kitts and Nevis", "KNA", 659, Assignment.OFFICIALLY_ASSIGNED);
        KP = new CountryCode("Democratic People's Republic of Korea", "PRK", 408, Assignment.OFFICIALLY_ASSIGNED);
        KR = new CountryCode("KR", 121, "Republic of Korea", "KOR", 410, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.KOREA;
            }
        };
        KW = new CountryCode("Kuwait", "KWT", 414, Assignment.OFFICIALLY_ASSIGNED);
        KY = new CountryCode("Cayman Islands", "CYM", 136, Assignment.OFFICIALLY_ASSIGNED);
        KZ = new CountryCode("Kazakhstan", "KAZ", 398, Assignment.OFFICIALLY_ASSIGNED);
        LA = new CountryCode("Lao People's Democratic Republic", "LAO", 418, Assignment.OFFICIALLY_ASSIGNED);
        LB = new CountryCode("Lebanon", "LBN", 422, Assignment.OFFICIALLY_ASSIGNED);
        LC = new CountryCode("Saint Lucia", "LCA", 662, Assignment.OFFICIALLY_ASSIGNED);
        LI = new CountryCode("Liechtenstein", "LIE", 438, Assignment.OFFICIALLY_ASSIGNED);
        LK = new CountryCode("Sri Lanka", "LKA", 144, Assignment.OFFICIALLY_ASSIGNED);
        LR = new CountryCode("Liberia", "LBR", 430, Assignment.OFFICIALLY_ASSIGNED);
        LS = new CountryCode("Lesotho", "LSO", 426, Assignment.OFFICIALLY_ASSIGNED);
        LT = new CountryCode("Lithuania", "LTU", 440, Assignment.OFFICIALLY_ASSIGNED);
        LU = new CountryCode("Luxembourg", "LUX", 442, Assignment.OFFICIALLY_ASSIGNED);
        LV = new CountryCode("Latvia", "LVA", 428, Assignment.OFFICIALLY_ASSIGNED);
        LY = new CountryCode("Libya", "LBY", 434, Assignment.OFFICIALLY_ASSIGNED);
        MA = new CountryCode("Morocco", "MAR", 504, Assignment.OFFICIALLY_ASSIGNED);
        MC = new CountryCode("Monaco", "MCO", 492, Assignment.OFFICIALLY_ASSIGNED);
        MD = new CountryCode("Republic of Moldova", "MDA", 498, Assignment.OFFICIALLY_ASSIGNED);
        ME = new CountryCode("Montenegro", "MNE", 499, Assignment.OFFICIALLY_ASSIGNED);
        MF = new CountryCode("Saint Martin", "MAF", 663, Assignment.OFFICIALLY_ASSIGNED);
        MG = new CountryCode("Madagascar", "MDG", 450, Assignment.OFFICIALLY_ASSIGNED);
        MH = new CountryCode("Marshall Islands", "MHL", 584, Assignment.OFFICIALLY_ASSIGNED);
        MK = new CountryCode("The former Yugoslav Republic of Macedonia", "MKD", 807, Assignment.OFFICIALLY_ASSIGNED);
        ML = new CountryCode("Mali", "MLI", 466, Assignment.OFFICIALLY_ASSIGNED);
        MM = new CountryCode("Myanmar", "MMR", 104, Assignment.OFFICIALLY_ASSIGNED);
        MN = new CountryCode("Mongolia", "MNG", 496, Assignment.OFFICIALLY_ASSIGNED);
        MO = new CountryCode("Macao", "MAC", 446, Assignment.OFFICIALLY_ASSIGNED);
        MP = new CountryCode("Northern Mariana Islands", "MNP", 580, Assignment.OFFICIALLY_ASSIGNED);
        MQ = new CountryCode("Martinique", "MTQ", 474, Assignment.OFFICIALLY_ASSIGNED);
        MR = new CountryCode("Mauritania", "MRT", 478, Assignment.OFFICIALLY_ASSIGNED);
        MS = new CountryCode("Montserrat", "MSR", 500, Assignment.OFFICIALLY_ASSIGNED);
        MT = new CountryCode("Malta", "MLT", 470, Assignment.OFFICIALLY_ASSIGNED);
        MU = new CountryCode("Mauritius", "MUS", 480, Assignment.OFFICIALLY_ASSIGNED);
        MV = new CountryCode("Maldives", "MDV", 462, Assignment.OFFICIALLY_ASSIGNED);
        MW = new CountryCode("Malawi", "MWI", 454, Assignment.OFFICIALLY_ASSIGNED);
        MX = new CountryCode("Mexico", "MEX", 484, Assignment.OFFICIALLY_ASSIGNED);
        MY = new CountryCode("Malaysia", "MYS", 458, Assignment.OFFICIALLY_ASSIGNED);
        MZ = new CountryCode("Mozambique", "MOZ", 508, Assignment.OFFICIALLY_ASSIGNED);
        NA = new CountryCode("Namibia", "NAM", 516, Assignment.OFFICIALLY_ASSIGNED);
        NC = new CountryCode("New Caledonia", "NCL", 540, Assignment.OFFICIALLY_ASSIGNED);
        NE = new CountryCode("Niger", "NER", 562, Assignment.OFFICIALLY_ASSIGNED);
        NF = new CountryCode("Norfolk Island", "NFK", 574, Assignment.OFFICIALLY_ASSIGNED);
        NG = new CountryCode("Nigeria", "NGA", 566, Assignment.OFFICIALLY_ASSIGNED);
        NI = new CountryCode("Nicaragua", "NIC", 558, Assignment.OFFICIALLY_ASSIGNED);
        NL = new CountryCode("Netherlands", "NLD", 528, Assignment.OFFICIALLY_ASSIGNED);
        NO = new CountryCode("Norway", "NOR", 578, Assignment.OFFICIALLY_ASSIGNED);
        NP = new CountryCode("Nepal", "NPL", 524, Assignment.OFFICIALLY_ASSIGNED);
        NR = new CountryCode("Nauru", "NRU", 520, Assignment.OFFICIALLY_ASSIGNED);
        NU = new CountryCode("Niue", "NIU", 570, Assignment.OFFICIALLY_ASSIGNED);
        NZ = new CountryCode("New Zealand", "NZL", 554, Assignment.OFFICIALLY_ASSIGNED);
        OM = new CountryCode("Oman", "OMN", 512, Assignment.OFFICIALLY_ASSIGNED);
        PA = new CountryCode("Panama", "PAN", 591, Assignment.OFFICIALLY_ASSIGNED);
        PE = new CountryCode("Peru", "PER", 604, Assignment.OFFICIALLY_ASSIGNED);
        PF = new CountryCode("PF", 174, "French Polynesia", "PYF", 258, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public String getIOC() {
                return "PYF";
            }
        };
        PG = new CountryCode("Papua New Guinea", "PNG", 598, Assignment.OFFICIALLY_ASSIGNED);
        PH = new CountryCode("Philippines", "PHL", 608, Assignment.OFFICIALLY_ASSIGNED);
        PK = new CountryCode("Pakistan", "PAK", 586, Assignment.OFFICIALLY_ASSIGNED);
        PL = new CountryCode("Poland", "POL", 616, Assignment.OFFICIALLY_ASSIGNED);
        PM = new CountryCode("Saint Pierre and Miquelon", "SPM", 666, Assignment.OFFICIALLY_ASSIGNED);
        PN = new CountryCode("Pitcairn", "PCN", 612, Assignment.OFFICIALLY_ASSIGNED);
        PR = new CountryCode("Puerto Rico", "PRI", 630, Assignment.OFFICIALLY_ASSIGNED);
        PS = new CountryCode("Occupied Palestinian Territory", "PSE", 275, Assignment.OFFICIALLY_ASSIGNED);
        PT = new CountryCode("Portugal", "PRT", 620, Assignment.OFFICIALLY_ASSIGNED);
        PW = new CountryCode("Palau", "PLW", 585, Assignment.OFFICIALLY_ASSIGNED);
        PY = new CountryCode("Paraguay", "PRY", 600, Assignment.OFFICIALLY_ASSIGNED);
        QA = new CountryCode("Qatar", "QAT", 634, Assignment.OFFICIALLY_ASSIGNED);
        RE = new CountryCode("R\u00e9union", "REU", 638, Assignment.OFFICIALLY_ASSIGNED);
        RO = new CountryCode("Romania", "ROU", 642, Assignment.OFFICIALLY_ASSIGNED);
        RS = new CountryCode("Serbia", "SRB", 688, Assignment.OFFICIALLY_ASSIGNED);
        RU = new CountryCode("Russian Federation", "RUS", 643, Assignment.OFFICIALLY_ASSIGNED);
        RW = new CountryCode("Rwanda", "RWA", 646, Assignment.OFFICIALLY_ASSIGNED);
        SA = new CountryCode("Saudi Arabia", "SAU", 682, Assignment.OFFICIALLY_ASSIGNED);
        SB = new CountryCode("Solomon Islands", "SLB", 90, Assignment.OFFICIALLY_ASSIGNED);
        SC = new CountryCode("Seychelles", "SYC", 690, Assignment.OFFICIALLY_ASSIGNED);
        SD = new CountryCode("Sudan", "SDN", 729, Assignment.OFFICIALLY_ASSIGNED);
        SE = new CountryCode("Sweden", "SWE", 752, Assignment.OFFICIALLY_ASSIGNED);
        SG = new CountryCode("Singapore", "SGP", 702, Assignment.OFFICIALLY_ASSIGNED);
        SH = new CountryCode("Saint Helena, Ascension and Tristan da Cunha", "SHN", 654, Assignment.OFFICIALLY_ASSIGNED);
        SI = new CountryCode("Slovenia", "SVN", 705, Assignment.OFFICIALLY_ASSIGNED);
        SJ = new CountryCode("Svalbard and Jan Mayen", "SJM", 744, Assignment.OFFICIALLY_ASSIGNED);
        SK = new CountryCode("Slovakia", "SVK", 703, Assignment.OFFICIALLY_ASSIGNED);
        SL = new CountryCode("Sierra Leone", "SLE", 694, Assignment.OFFICIALLY_ASSIGNED);
        SM = new CountryCode("San Marino", "SMR", 674, Assignment.OFFICIALLY_ASSIGNED);
        SN = new CountryCode("Senegal", "SEN", 686, Assignment.OFFICIALLY_ASSIGNED);
        SO = new CountryCode("Somalia", "SOM", 706, Assignment.OFFICIALLY_ASSIGNED);
        SR = new CountryCode("Suriname", "SUR", 740, Assignment.OFFICIALLY_ASSIGNED);
        SS = new CountryCode("South Sudan", "SSD", 728, Assignment.OFFICIALLY_ASSIGNED);
        ST = new CountryCode("Sao Tome and Principe", "STP", 678, Assignment.OFFICIALLY_ASSIGNED);
        SV = new CountryCode("El Salvador", "SLV", 222, Assignment.OFFICIALLY_ASSIGNED);
        SX = new CountryCode("Sint Maarten", "SXM", 534, Assignment.OFFICIALLY_ASSIGNED);
        SY = new CountryCode("Syrian Arab Republic", "SYR", 760, Assignment.OFFICIALLY_ASSIGNED);
        SZ = new CountryCode("Swaziland", "SWZ", 748, Assignment.OFFICIALLY_ASSIGNED);
        TC = new CountryCode("Turks and Caicos Islands", "TCA", 796, Assignment.OFFICIALLY_ASSIGNED);
        TD = new CountryCode("Chad", "TCD", 148, Assignment.OFFICIALLY_ASSIGNED);
        TF = new CountryCode("French Southern Territories", "ATF", 260, Assignment.OFFICIALLY_ASSIGNED);
        TG = new CountryCode("Togo", "TGO", 768, Assignment.OFFICIALLY_ASSIGNED);
        TH = new CountryCode("Thailand", "THA", 764, Assignment.OFFICIALLY_ASSIGNED);
        TJ = new CountryCode("Tajikistan", "TJK", 762, Assignment.OFFICIALLY_ASSIGNED);
        TK = new CountryCode("Tokelau", "TKL", 772, Assignment.OFFICIALLY_ASSIGNED);
        TL = new CountryCode("Timor-Leste", "TLS", 626, Assignment.OFFICIALLY_ASSIGNED);
        TM = new CountryCode("Turkmenistan", "TKM", 795, Assignment.OFFICIALLY_ASSIGNED);
        TN = new CountryCode("Tunisia", "TUN", 788, Assignment.OFFICIALLY_ASSIGNED);
        TO = new CountryCode("Tonga", "TON", 776, Assignment.OFFICIALLY_ASSIGNED);
        TR = new CountryCode("Turkey", "TUR", 792, Assignment.OFFICIALLY_ASSIGNED);
        TT = new CountryCode("Trinidad and Tobago", "TTO", 780, Assignment.OFFICIALLY_ASSIGNED);
        TV = new CountryCode("Tuvalu", "TUV", 798, Assignment.OFFICIALLY_ASSIGNED);
        TW = new CountryCode("TW", 227, "Taiwan, Province of China", "TWN", 158, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.TAIWAN;
            }
        };
        TZ = new CountryCode("United Republic of Tanzania", "TZA", 834, Assignment.OFFICIALLY_ASSIGNED);
        UA = new CountryCode("Ukraine", "UKR", 804, Assignment.OFFICIALLY_ASSIGNED);
        UG = new CountryCode("Uganda", "UGA", 800, Assignment.OFFICIALLY_ASSIGNED);
        UM = new CountryCode("United States Minor Outlying Islands", "UMI", 581, Assignment.OFFICIALLY_ASSIGNED);
        US = new CountryCode("US", 232, "United States", "USA", 840, Assignment.OFFICIALLY_ASSIGNED){

            @Override
            public Locale toLocale() {
                return Locale.US;
            }
        };
        UY = new CountryCode("Uruguay", "URY", 858, Assignment.OFFICIALLY_ASSIGNED);
        UZ = new CountryCode("Uzbekistan", "UZB", 860, Assignment.OFFICIALLY_ASSIGNED);
        VA = new CountryCode("Vatican", "VAT", 336, Assignment.OFFICIALLY_ASSIGNED);
        VC = new CountryCode("Saint Vincent and the Grenadines", "VCT", 670, Assignment.OFFICIALLY_ASSIGNED);
        VE = new CountryCode("Bolivarian Republic of Venezuela", "VEN", 862, Assignment.OFFICIALLY_ASSIGNED);
        VG = new CountryCode("British Virgin Islands", "VGB", 92, Assignment.OFFICIALLY_ASSIGNED);
        VI = new CountryCode("Virgin Islands, U.S.", "VIR", 850, Assignment.OFFICIALLY_ASSIGNED);
        VN = new CountryCode("Viet Nam", "VNM", 704, Assignment.OFFICIALLY_ASSIGNED);
        VU = new CountryCode("Vanuatu", "VUT", 548, Assignment.OFFICIALLY_ASSIGNED);
        WF = new CountryCode("Wallis and Futuna", "WLF", 876, Assignment.OFFICIALLY_ASSIGNED);
        WS = new CountryCode("Samoa", "WSM", 882, Assignment.OFFICIALLY_ASSIGNED);
        YE = new CountryCode("Yemen", "YEM", 887, Assignment.OFFICIALLY_ASSIGNED);
        YT = new CountryCode("Mayotte", "MYT", 175, Assignment.OFFICIALLY_ASSIGNED);
        ZA = new CountryCode("South Africa", "ZAF", 710, Assignment.OFFICIALLY_ASSIGNED);
        ZM = new CountryCode("Zambia", "ZMB", 894, Assignment.OFFICIALLY_ASSIGNED);
        ZW = new CountryCode("Zimbabwe", "ZWE", 716, Assignment.OFFICIALLY_ASSIGNED);
        XE = new CountryCode("XE", 249, "England", "ENG", -1, Assignment.USER_ASSIGNED){

            @Override
            public String getDisplayName(Resources resources) {
                return resources.getString(2131689940);
            }

            @Override
            public int getImageId() {
                return 2131231200;
            }
        };
        XS = new CountryCode("XS", 250, "Scotland", "SCO", -2, Assignment.USER_ASSIGNED){

            @Override
            public String getDisplayName(Resources resources) {
                return resources.getString(2131689942);
            }

            @Override
            public int getImageId() {
                return 2131231330;
            }
        };
        XW = new CountryCode("XW", 251, "Wales", "WLS", -3, Assignment.USER_ASSIGNED){

            @Override
            public String getDisplayName(Resources resources) {
                return resources.getString(2131689943);
            }

            @Override
            public int getImageId() {
                return 2131231378;
            }
        };
        XN = new CountryCode("XN", 252, "Northern Ireland", "NIR", -4, Assignment.USER_ASSIGNED){

            @Override
            public String getDisplayName(Resources resources) {
                return resources.getString(2131689941);
            }

            @Override
            public int getImageId() {
                return 2131231211;
            }
        };
        XP = new CountryCode("XP", 253, "INTERNATIONAL PHYSICALLY DISABLED CHESS ASSOCIATION", "XPD", -5, Assignment.USER_ASSIGNED_NOT_USED){

            @Override
            public String getDisplayName(Resources resources) {
                return "INTERNATIONAL PHYSICALLY DISABLED CHESS ASSOCIATION";
            }

            @Override
            public int getImageId() {
                return 2131231242;
            }
        };
        XD = new CountryCode("XD", 254, "INTERNATIONAL CHESS COMMITTEE OF THE DEAF", "XDC", -6, Assignment.USER_ASSIGNED_NOT_USED){

            @Override
            public String getDisplayName(Resources resources) {
                return "INTERNATIONAL CHESS COMMITTEE OF THE DEAF";
            }

            @Override
            public int getImageId() {
                return 2131231233;
            }
        };
        XB = new CountryCode("XB", 255, "International Braille Chess Association", "XBC", -7, Assignment.USER_ASSIGNED_NOT_USED){

            @Override
            public String getDisplayName(Resources resources) {
                return "International Braille Chess Association";
            }

            @Override
            public int getImageId() {
                return 2131231232;
            }
        };
        AN = new CountryCode("Netherlands Antilles", "ANT", 530, Assignment.TRANSITIONALLY_RESERVED);
        CountryCode[] arrcountryCode = AD;
        $VALUES = new CountryCode[]{arrcountryCode, AE, AF, AG, AI, AL, AM, AO, AQ, AR, AS, AT, AU, AW, AX, AZ, BA, BB, BD, BE, BF, BG, BH, BI, BJ, BL, BM, BN, BO, BQ, BR, BS, BT, BV, BW, BY, BZ, CA, CC, CD, CF, CG, CH, CI, CK, CL, CM, CN, CO, CR, CU, CV, CW, CX, CY, CZ, DE, DJ, DK, DM, DO, DZ, EC, EE, EG, EH, ER, ES, ET, FI, FJ, FK, FM, FO, FR, GA, GB, GD, GE, GF, GG, GH, GI, GL, GM, GN, GP, GQ, GR, GS, GT, GU, GW, GY, HK, HM, HN, HR, HT, HU, ID, IE, IL, IM, IN, IO, IQ, IR, IS, IT, JE, JM, JO, JP, KE, KG, KH, KI, KM, KN, KP, KR, KW, KY, KZ, LA, LB, LC, LI, LK, LR, LS, LT, LU, LV, LY, MA, MC, MD, ME, MF, MG, MH, MK, ML, MM, MN, MO, MP, MQ, MR, MS, MT, MU, MV, MW, MX, MY, MZ, NA, NC, NE, NF, NG, NI, NL, NO, NP, NR, NU, NZ, OM, PA, PE, PF, PG, PH, PK, PL, PM, PN, PR, PS, PT, PW, PY, QA, RE, RO, RS, RU, RW, SA, SB, SC, SD, SE, SG, SH, SI, SJ, SK, SL, SM, SN, SO, SR, SS, ST, SV, SX, SY, SZ, TC, TD, TF, TG, TH, TJ, TK, TL, TM, TN, TO, TR, TT, TV, TW, TZ, UA, UG, UM, US, UY, UZ, VA, VC, VE, VG, VI, VN, VU, WF, WS, YE, YT, ZA, ZM, ZW, XE, XS, XW, XN, XP, XD, XB, AN};
        alpha3Map = new HashMap<String, CountryCode>();
        numericMap = new HashMap<Integer, CountryCode>();
        iocMap = new HashMap<String, CountryCode>();
        for (CountryCode countryCode : CountryCode.values()) {
            alpha3Map.put(countryCode.getAlpha3(), countryCode);
            numericMap.put(countryCode.getNumeric(), countryCode);
        }
        CountryCode.fillIOCCodeMap();
    }

    private CountryCode(String string2, String string3, int n2, Assignment assignment) {
        this.name = string2;
        this.alpha3 = string3;
        this.numeric = n2;
        this.assignment = assignment;
    }

    static String canonicalize(String string, boolean bl) {
        if (string != null && string.length() != 0) {
            if (bl) {
                return string;
            }
            return string.toUpperCase();
        }
        return null;
    }

    private static void fillIOCCodeMap() {
        iocMap.put("AHO", AN);
        iocMap.put("ALG", DZ);
        iocMap.put("ASA", AS);
        iocMap.put("ANG", AO);
        iocMap.put("ANT", AG);
        iocMap.put("ARU", AW);
        iocMap.put("BAH", BS);
        iocMap.put("BRN", BH);
        iocMap.put("BAN", BD);
        iocMap.put("BAR", BB);
        iocMap.put("BIZ", BZ);
        iocMap.put("BER", BM);
        iocMap.put("BHU", BT);
        iocMap.put("BOT", BW);
        iocMap.put("IVB", VG);
        iocMap.put("BRU", BN);
        iocMap.put("BUL", BG);
        iocMap.put("BUR", BF);
        iocMap.put("CAM", KH);
        iocMap.put("CAY", KY);
        iocMap.put("CHA", TD);
        iocMap.put("CHI", CL);
        iocMap.put("CGO", CG);
        iocMap.put("CRI", CR);
        iocMap.put("CRC", CR);
        iocMap.put("CRO", HR);
        iocMap.put("DEN", DK);
        iocMap.put("ESA", SV);
        iocMap.put("GEQ", GQ);
        iocMap.put("EQG", GQ);
        iocMap.put("FAI", FO);
        iocMap.put("FIJ", FJ);
        iocMap.put("FYROM", MK);
        iocMap.put("TAH", PF);
        iocMap.put("GAM", GM);
        iocMap.put("GCI", GG);
        iocMap.put("GER", DE);
        iocMap.put("GRE", GR);
        iocMap.put("GRN", GD);
        iocMap.put("GUA", GT);
        iocMap.put("GUI", GN);
        iocMap.put("GBS", GW);
        iocMap.put("HAI", HT);
        iocMap.put("HON", HK);
        iocMap.put("INA", ID);
        iocMap.put("IRI", IR);
        iocMap.put("JCI", JE);
        iocMap.put("KUW", KW);
        iocMap.put("LAT", LV);
        iocMap.put("LIB", LB);
        iocMap.put("LES", LS);
        iocMap.put("LBA", LY);
        iocMap.put("MAD", MG);
        iocMap.put("MAW", MW);
        iocMap.put("MAS", MY);
        iocMap.put("MNC", MC);
        iocMap.put("MTN", MR);
        iocMap.put("MRI", MU);
        iocMap.put("MON", MC);
        iocMap.put("MGL", MN);
        iocMap.put("MYA", MM);
        iocMap.put("NEP", NP);
        iocMap.put("NED", NL);
        iocMap.put("NCA", NI);
        iocMap.put("NIG", NE);
        iocMap.put("NGR", NG);
        iocMap.put("OMA", OM);
        iocMap.put("PLE", PS);
        iocMap.put("PAR", PY);
        iocMap.put("PHI", PH);
        iocMap.put("POR", PT);
        iocMap.put("PUR", PR);
        iocMap.put("SKN", KN);
        iocMap.put("VIN", VC);
        iocMap.put("SAM", WS);
        iocMap.put("KSA", SA);
        iocMap.put("SEY", SC);
        iocMap.put("SIN", SG);
        iocMap.put("SLO", SI);
        iocMap.put("SOL", SB);
        iocMap.put("RSA", ZA);
        iocMap.put("SRI", LK);
        iocMap.put("SUD", SD);
        iocMap.put("SUI", CH);
        iocMap.put("TPE", TW);
        iocMap.put("TAN", TZ);
        iocMap.put("TGA", TO);
        iocMap.put("TNZ", TZ);
        iocMap.put("TOG", TG);
        iocMap.put("TRI", TT);
        iocMap.put("UAE", AE);
        iocMap.put("ISV", VI);
        iocMap.put("URU", UY);
        iocMap.put("VAN", VU);
        iocMap.put("VIE", VN);
        iocMap.put("ZAM", ZM);
        iocMap.put("ZIM", ZW);
        iocMap.put("IPCA", XP);
        iocMap.put("ICCD", XD);
        iocMap.put("IBCA", XB);
        iocMap.put("IPC", XP);
        iocMap.put("ICS", XD);
        iocMap.put("IBC", XB);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static CountryCode getByAlpha2Code(String object) {
        try {
            return Enum.valueOf(CountryCode.class, (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    private static CountryCode getByAlpha3Code(String string) {
        return alpha3Map.get(string);
    }

    public static CountryCode getByCode(int n) {
        return numericMap.get(n);
    }

    public static CountryCode getByCode(String string) {
        return CountryCode.getByCode(string, false);
    }

    public static CountryCode getByCode(String object, boolean bl) {
        String string = CountryCode.canonicalize((String)object, bl);
        if (string == null) {
            return null;
        }
        object = string.length() == 2 ? CountryCode.getByAlpha2Code(string) : CountryCode.getByAlpha3Code(string);
        Object object2 = object;
        if (object == null) {
            object2 = CountryCode.getByIOCCode(string);
        }
        return object2;
    }

    private static CountryCode getByIOCCode(String string) {
        return iocMap.get(string);
    }

    public static CountryCode getByLocale(Locale locale) {
        if (locale == null) {
            return null;
        }
        return CountryCode.getByCode(locale.getCountry(), true);
    }

    public static CountryCode valueOf(String string) {
        return Enum.valueOf(CountryCode.class, string);
    }

    public static CountryCode[] values() {
        return (CountryCode[])$VALUES.clone();
    }

    @Override
    public String getAlpha2() {
        return this.name();
    }

    @Override
    public String getAlpha3() {
        return this.alpha3;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Currency getCurrency() {
        try {
            return Currency.getInstance(this.toLocale());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SuppressLint(value={"DefaultLocale"})
    @Override
    public String getDisplayName(Resources resources) {
        int n;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("country_");
            stringBuilder.append(this.getAlpha2().toUpperCase());
            n = R.string.class.getDeclaredField(stringBuilder.toString()).getInt(new R.drawable());
        }
        catch (Exception exception) {
            return this.getAlpha3();
        }
        return resources.getString(n);
    }

    @Override
    public String getIOC() {
        String string22;
        block2 : {
            for (String string22 : iocMap.keySet()) {
                if (iocMap.get(string22) != this) continue;
                break block2;
            }
            string22 = null;
        }
        Object object = string22;
        if (string22 == null) {
            object = this.getAlpha3();
        }
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SuppressLint(value={"DefaultLocale"})
    @Override
    public int getImageId() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("flag_");
            stringBuilder.append(this.getAlpha2().toLowerCase());
            return R.drawable.class.getDeclaredField(stringBuilder.toString()).getInt(new R.drawable());
        }
        catch (Exception exception) {
            return 2131231366;
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getNumeric() {
        return this.numeric;
    }

    public boolean isSelectableByUser() {
        if (this.assignment != Assignment.NOT_USED && this.assignment != Assignment.USER_ASSIGNED_NOT_USED && this.assignment != Assignment.TRANSITIONALLY_RESERVED) {
            return true;
        }
        return false;
    }

    public boolean isUserAssigned() {
        if (this.assignment != Assignment.USER_ASSIGNED && this.assignment != Assignment.USER_ASSIGNED_NOT_USED) {
            return false;
        }
        return true;
    }

    @Override
    public Locale toLocale() {
        return new Locale("", this.name());
    }

    public static enum Assignment {
        OFFICIALLY_ASSIGNED,
        USER_ASSIGNED,
        EXCEPTIONALLY_RESERVED,
        TRANSITIONALLY_RESERVED,
        INDETERMINATELY_RESERVED,
        NOT_USED,
        USER_ASSIGNED_NOT_USED;
        

        private Assignment() {
        }
    }

}
