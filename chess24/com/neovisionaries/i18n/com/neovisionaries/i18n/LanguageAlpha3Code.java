/*
 * Decompiled with CFR 0_134.
 */
package com.neovisionaries.i18n;

import com.neovisionaries.i18n.LanguageCode;

public enum LanguageAlpha3Code {
    aar("Afar"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.aa;
        }
    }
    ,
    abk("Abkhaz"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ab;
        }
    }
    ,
    afr("Afrikaans"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.af;
        }
    }
    ,
    aka("Akan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ak;
        }
    }
    ,
    alb("Albanian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sq;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return sqi;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    amh("Amharic"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.am;
        }
    }
    ,
    ara("Arabic"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ar;
        }
    }
    ,
    arg("Aragonese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.an;
        }
    }
    ,
    arm("Armenian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hy;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return hye;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    asm("Assamese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.as;
        }
    }
    ,
    ava("Avaric"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.av;
        }
    }
    ,
    ave("Avestan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ae;
        }
    }
    ,
    aym("Aymara"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ay;
        }
    }
    ,
    aze("Azerbaijani"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.az;
        }
    }
    ,
    bak("Bashkir"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ba;
        }
    }
    ,
    bam("Bambara"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bm;
        }
    }
    ,
    baq("Basque"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.eu;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return eus;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    bel("Belarusian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.be;
        }
    }
    ,
    ben("Bengali"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bn;
        }
    }
    ,
    bih("Bihari languages"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bh;
        }
    }
    ,
    bis("Bislama"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bi;
        }
    }
    ,
    bod("Tibetan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bo;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return tib;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    bos("Bosnian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bs;
        }
    }
    ,
    bre("Breton"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.br;
        }
    }
    ,
    bul("Bulgarian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bg;
        }
    }
    ,
    bur("Burmese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.my;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return mya;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    cat("Catalan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ca;
        }
    }
    ,
    ces("Czech"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cs;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return cze;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    cha("Chamorro"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ch;
        }
    }
    ,
    che("Chechen"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ce;
        }
    }
    ,
    chi("Chinese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.zh;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return zho;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    chu("Church Slavic"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cu;
        }
    }
    ,
    chv("Chuvash"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cv;
        }
    }
    ,
    cor("Comish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kw;
        }
    }
    ,
    cos("Corsican"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.co;
        }
    }
    ,
    cre("Cree"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cr;
        }
    }
    ,
    cym("Welsh"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cy;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return wel;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    cze("Czech"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cs;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return ces;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    dan("Danish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.da;
        }
    }
    ,
    deu("German"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.de;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return ger;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    div("Dhivehi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.dv;
        }
    }
    ,
    dut("Dutch"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nl;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return nld;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    dzo("Dzongkha"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.dz;
        }
    }
    ,
    ell("Greek"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.el;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return gre;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    eng("English"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.en;
        }
    }
    ,
    epo("Esperanto"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.eo;
        }
    }
    ,
    est("Estonian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.et;
        }
    }
    ,
    eus("Basque"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.eu;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return baq;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    ewe("Ewe"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ee;
        }
    }
    ,
    fao("Faroese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fo;
        }
    }
    ,
    fas("Persian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fa;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return per;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    fij("Fijian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fj;
        }
    }
    ,
    fin("Finnish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fi;
        }
    }
    ,
    fra("French"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fr;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return fre;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    fre("French"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fr;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return fra;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    fry("West Frisian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fy;
        }
    }
    ,
    ful("Fula"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ff;
        }
    }
    ,
    geo("Georgian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ka;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return kat;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    ger("German"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.de;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return deu;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    gla("Scottish Gaelic"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gd;
        }
    }
    ,
    gle("Irish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ga;
        }
    }
    ,
    glg("Galician"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gl;
        }
    }
    ,
    glv("Manx"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gv;
        }
    }
    ,
    gre("Greek"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.el;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return ell;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    grn("Guaran\u00ed"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gn;
        }
    }
    ,
    guj("Gujarati"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gu;
        }
    }
    ,
    hat("Haitian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ht;
        }
    }
    ,
    hau("Hausa"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ha;
        }
    }
    ,
    heb("Hebrew"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.he;
        }
    }
    ,
    her("Herero"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hz;
        }
    }
    ,
    hin("Hindi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hi;
        }
    }
    ,
    hmo("Hiri Motu"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ho;
        }
    }
    ,
    hrv("Croatian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hr;
        }
    }
    ,
    hun("Hungarian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hu;
        }
    }
    ,
    hye("Armenian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hy;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return arm;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    ibo("Igbo"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ig;
        }
    }
    ,
    ice("Icelandic"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.is;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return isl;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    ido("Ido"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.io;
        }
    }
    ,
    iii("Nuosu"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ii;
        }
    }
    ,
    iku("Inuktitut"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.iu;
        }
    }
    ,
    ile("Interlingue"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ie;
        }
    }
    ,
    ina("Interlingua"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ia;
        }
    }
    ,
    ind("Indonesian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.id;
        }
    }
    ,
    ipk("Inupiaq"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ik;
        }
    }
    ,
    isl("Icelandic"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.is;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return ice;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    ita("Italian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.it;
        }
    }
    ,
    jav("Javanese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.jv;
        }
    }
    ,
    jpn("Japanese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ja;
        }
    }
    ,
    kal("Kalaallisut"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kl;
        }
    }
    ,
    kan("Kannada"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kn;
        }
    }
    ,
    kas("Kashmiri"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ks;
        }
    }
    ,
    kat("Georgian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ka;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return geo;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    kau("Kanuri"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kr;
        }
    }
    ,
    kaz("Kazakh"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kk;
        }
    }
    ,
    khm("Central Khmer"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.km;
        }
    }
    ,
    kik("Kikuyu"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ki;
        }
    }
    ,
    kin("Kinyarwanda"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.rw;
        }
    }
    ,
    kir("Kirghiz"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ky;
        }
    }
    ,
    kom("Komi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kv;
        }
    }
    ,
    kon("Kongo"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kg;
        }
    }
    ,
    kor("Korean"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ko;
        }
    }
    ,
    kua("Kuanyama"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kj;
        }
    }
    ,
    kur("Kurdish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ku;
        }
    }
    ,
    lao("Lao"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lo;
        }
    }
    ,
    lat("Latin"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.la;
        }
    }
    ,
    lav("Latvian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lv;
        }
    }
    ,
    lim("Limburgan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.li;
        }
    }
    ,
    lin("Lingala"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ln;
        }
    }
    ,
    lit("Lithuanian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lt;
        }
    }
    ,
    ltz("Luxembourgish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lb;
        }
    }
    ,
    lub("Luba-Katanga"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lu;
        }
    }
    ,
    lug("Ganda"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lg;
        }
    }
    ,
    mac("Macedonian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mk;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return mkd;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    mah("Marshallese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mh;
        }
    }
    ,
    mal("Malayalam"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ml;
        }
    }
    ,
    mao("M\u0101ori"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mi;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return mri;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    mar("Marathi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mr;
        }
    }
    ,
    may("Malay"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ms;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return msa;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    mkd("Macedonian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mk;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return mac;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    mlg("Malagasy"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mg;
        }
    }
    ,
    mlt("Maltese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mt;
        }
    }
    ,
    mon("Mongolian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mn;
        }
    }
    ,
    mri("M\u0101ori"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mi;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return mao;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    msa("Malay"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ms;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return may;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    mya("Burmese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.my;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return bur;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    nau("Nauru"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.na;
        }
    }
    ,
    nav("Navajo"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nv;
        }
    }
    ,
    nbl("South Ndebele"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nr;
        }
    }
    ,
    nde("North Ndebele"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nd;
        }
    }
    ,
    ndo("Ndonga"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ng;
        }
    }
    ,
    nep("Nepali"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ne;
        }
    }
    ,
    nld("Dutch"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nl;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return dut;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    nno("Norwegian Nynorsk"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nn;
        }
    }
    ,
    nob("Norwegian Bokm\u00e5l"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nb;
        }
    }
    ,
    nor("Norwegian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.no;
        }
    }
    ,
    nya("Nyanja"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ny;
        }
    }
    ,
    oci("Occitan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.oc;
        }
    }
    ,
    oji("Ojibwa"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.oj;
        }
    }
    ,
    ori("Oriya"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.or;
        }
    }
    ,
    orm("Oromo"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.om;
        }
    }
    ,
    oss("Ossetian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.os;
        }
    }
    ,
    pan("Panjabi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pa;
        }
    }
    ,
    per("Persian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fa;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return fas;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    pli("P\u0101li"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pi;
        }
    }
    ,
    pol("Polish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pl;
        }
    }
    ,
    por("Portuguese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pt;
        }
    }
    ,
    pus("Pushto"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ps;
        }
    }
    ,
    que("Quechua"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.qu;
        }
    }
    ,
    roh("Romansh"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.rm;
        }
    }
    ,
    ron("Romansh"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ro;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return rum;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    rum("Romansh"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ro;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return ron;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    run("Kirundi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.rn;
        }
    }
    ,
    rus("Russian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ru;
        }
    }
    ,
    sag("Sango"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sg;
        }
    }
    ,
    san("Sanskrit"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sa;
        }
    }
    ,
    sin("Sinhala"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.si;
        }
    }
    ,
    slk("Slovak"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sk;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return slo;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    slo("Slovak"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sk;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return slk;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    slv("Slovene"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sl;
        }
    }
    ,
    sme("Northern Sami"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.se;
        }
    }
    ,
    smo("Samoan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sm;
        }
    }
    ,
    sna("Shona"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sn;
        }
    }
    ,
    snd("Sindhi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sd;
        }
    }
    ,
    som("Somali"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.so;
        }
    }
    ,
    sot("Southern Sotho"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.st;
        }
    }
    ,
    spa("Spanish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.es;
        }
    }
    ,
    sqi("Albanian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sq;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return alb;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    srd("Sardinian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sc;
        }
    }
    ,
    srp("Serbian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sr;
        }
    }
    ,
    ssw("Swati"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ss;
        }
    }
    ,
    sun("Sundanese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.su;
        }
    }
    ,
    swa("Swahili"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sw;
        }
    }
    ,
    swe("Swedish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sv;
        }
    }
    ,
    tah("Tahitian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ty;
        }
    }
    ,
    tam("Tamil"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ta;
        }
    }
    ,
    tat("Tatar"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tt;
        }
    }
    ,
    tel("Telugu"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.te;
        }
    }
    ,
    tgk("Tajik"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tg;
        }
    }
    ,
    tgl("Tagalog"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tl;
        }
    }
    ,
    tha("Thai"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.th;
        }
    }
    ,
    tib("Tibetan"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bo;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return bod;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    tir("Tigrinya"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ti;
        }
    }
    ,
    ton("Tonga"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.to;
        }
    }
    ,
    tsn("Tswana"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tn;
        }
    }
    ,
    tso("Tsonga"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ts;
        }
    }
    ,
    tuk("Turkmen"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tk;
        }
    }
    ,
    tur("Turkish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tr;
        }
    }
    ,
    twi("Twi"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tw;
        }
    }
    ,
    uig("Uighur"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ug;
        }
    }
    ,
    ukr("Ukrainian"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.uk;
        }
    }
    ,
    urd("Urdu"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ur;
        }
    }
    ,
    uzb("Uzbek"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.uz;
        }
    }
    ,
    ven("Venda"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ve;
        }
    }
    ,
    vie("Vietnamese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.vi;
        }
    }
    ,
    vol("Volap\u00fck"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.vo;
        }
    }
    ,
    wel("Welsh"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cy;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return cym;
        }

        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }
    }
    ,
    wln("Walloon"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.wa;
        }
    }
    ,
    wol("Wolof"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.wo;
        }
    }
    ,
    xho("Xhosa"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.xh;
        }
    }
    ,
    yid("Yiddish"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.yi;
        }
    }
    ,
    yor("Yoruba"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.yo;
        }
    }
    ,
    zha("Zhuang"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.za;
        }
    }
    ,
    zho("Chinese"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.zh;
        }

        @Override
        public LanguageAlpha3Code getSynonym() {
            return chi;
        }

        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }
    }
    ,
    zul("Zulu"){

        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.zu;
        }
    };
    
    private final String name;

    private LanguageAlpha3Code(String string2) {
        this.name = string2;
    }

    private static String canonicalize(String string, boolean bl) {
        if (string != null && string.length() != 0) {
            if (bl) {
                return string;
            }
            return string.toLowerCase();
        }
        return null;
    }

    public static LanguageAlpha3Code getByCode(String string) {
        return LanguageAlpha3Code.getByCode(string, false);
    }

    public static LanguageAlpha3Code getByCode(String object, boolean bl) {
        if ((object = LanguageAlpha3Code.canonicalize((String)object, bl)) == null) {
            return null;
        }
        if (object.length() == 3) {
            return LanguageAlpha3Code.getByEnumName((String)object);
        }
        if (object.length() != 2) {
            return null;
        }
        if ((object = LanguageCode.getByEnumName(LanguageCode.canonicalize((String)object, bl))) == null) {
            return null;
        }
        return object.getAlpha3();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static LanguageAlpha3Code getByEnumName(String object) {
        try {
            return Enum.valueOf(LanguageAlpha3Code.class, object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public LanguageCode getAlpha2() {
        return null;
    }

    public LanguageAlpha3Code getAlpha3B() {
        if (this.getUsage() == Usage.BIBLIOGRAPHY) {
            return this;
        }
        return this.getSynonym();
    }

    public LanguageAlpha3Code getAlpha3T() {
        if (this.getUsage() == Usage.TERMINOLOGY) {
            return this;
        }
        return this.getSynonym();
    }

    public String getName() {
        return this.name;
    }

    public LanguageAlpha3Code getSynonym() {
        return this;
    }

    public Usage getUsage() {
        return Usage.COMMON;
    }

    public static enum Usage {
        TERMINOLOGY,
        BIBLIOGRAPHY,
        COMMON;
        

        private Usage() {
        }
    }

}
