/*
 * Decompiled with CFR 0_134.
 */
package com.neovisionaries.i18n;

import java.util.HashMap;
import java.util.Map;

public enum ScriptCode {
    Afak(439, "Afaka"),
    Aghb(239, "Caucasian Albanian"),
    Arab(160, "Arabic"),
    Armi(124, "Imperial Aramaic"),
    Armn(230, "Armenian"),
    Avst(134, "Avestan"),
    Bali(360, "Balinese"),
    Bamu(435, "Bamum"),
    Bass(259, "Bassa Vah"),
    Batk(365, "Batak"),
    Beng(325, "Bengali"),
    Blis(550, "Blissymbols"),
    Bopo(285, "Bopomofo"),
    Brah(300, "Brahmi"),
    Brai(570, "Braille"),
    Bugi(367, "Buginese"),
    Buhd(372, "Buhid"),
    Cakm(349, "Chakma"),
    Cans(440, "Unified Canadian Aboriginal Syllabics"),
    Cari(201, "Carian"),
    Cham(358, "Cham"),
    Cher(445, "Cherokee"),
    Cirt(291, "Cirth"),
    Copt(204, "Coptic"),
    Cprt(403, "Cypriot"),
    Cyrl(220, "Cyrillic"),
    Cyrs(221, "Cyrillic"),
    Deva(315, "Devanagari"),
    Dsrt(250, "Deseret"),
    Dupl(755, "Duployan shorthand, Duployan stenography"),
    Egyd(56, "Egyptian demotic"),
    Egyh(48, "Egyptian hieratic"),
    Egyp(40, "Egyptian hieroglyphs"),
    Elba(226, "Elbasan"),
    Ethi(430, "Ethiopic"),
    Geor(240, "Georgian"),
    Geok(241, "Khutsuri"),
    Glag(225, "Glagolitic"),
    Goth(206, "Gothic"),
    Gran(343, "Grantha"),
    Grek(200, "Greek"),
    Gujr(320, "Gujarati"),
    Guru(310, "Gurmukhi"),
    Hang(286, "Hangul"),
    Hani(500, "Han"),
    Hano(371, "Hanunoo"),
    Hans(501, "Han"),
    Hant(502, "Han"),
    Hebr(125, "Hebrew"),
    Hira(410, "Hiragana"),
    Hluw(80, "Anatolian Hieroglyphs"),
    Hmng(450, "Pahawh Hmong"),
    Hrkt(412, "Japanese syllabaries"),
    Hung(176, "Old Hungarian"),
    Inds(610, "Indus"),
    Ital(210, "Old Italic"),
    Java(361, "Javanese"),
    Jpan(413, "Japanese"),
    Jurc(510, "Jurchen"),
    Kali(357, "Kayah Li"),
    Kana(411, "Katakana"),
    Khar(305, "Kharoshthi"),
    Khmr(355, "Khmer"),
    Khoj(322, "Khojki"),
    Knda(345, "Kannada"),
    Kore(287, "Korean"),
    Kpel(436, "Kpelle"),
    Kthi(317, "Kaithi"),
    Lana(351, "Tai Tham"),
    Laoo(356, "Lao"),
    Latf(217, "Latin"),
    Latg(216, "Latin"),
    Latn(215, "Latin"),
    Lepc(335, "Lepcha"),
    Limb(336, "Limbu"),
    Lina(400, "Linear A"),
    Linb(401, "Linear B"),
    Lisu(399, "Lisu"),
    Loma(437, "Loma"),
    Lyci(202, "Lycian"),
    Lydi(116, "Lydian"),
    Mahj(314, "Mahajani"),
    Mand(140, "Mandaic, Mandaean"),
    Mani(139, "Manichaean"),
    Maya(90, "Mayan hieroglyphs"),
    Mend(438, "Mende"),
    Merc(101, "Meroitic Cursive"),
    Mero(100, "Meroitic Hieroglyphs"),
    Mlym(347, "Malayalam"),
    Moon(218, "Moon"),
    Mong(145, "Mongolian"),
    Mroo(199, "Mro, Mru"),
    Mtei(337, "Meitei Mayek"),
    Mymr(350, "Myanmar"),
    Narb(106, "Old North Arabian"),
    Nbat(159, "Nabataean"),
    Nkgb(420, "Nakhi Geba"),
    Nkoo(165, "N\u2019Ko"),
    Nshu(499, "Nushu"),
    Ogam(212, "Ogham"),
    Olck(261, "Ol Chiki"),
    Orkh(175, "Old Turkic, Orkhon Runic"),
    Orya(327, "Oriya"),
    Osma(260, "Osmanya"),
    Palm(126, "Palmyrene"),
    Perm(227, "Old Permic"),
    Phag(331, "Phags-pa"),
    Phli(131, "Inscriptional Pahlavi"),
    Phlp(132, "Psalter Pahlavi"),
    Phlv(133, "Book Pahlavi"),
    Phnx(115, "Phoenician"),
    Plrd(282, "Miao"),
    Prti(130, "Inscriptional Parthian"),
    Qaaa(900, "Reserved for private use"),
    Qabx(949, "Reserved for private use"),
    Rjng(363, "Rejang"),
    Roro(620, "Rongorongo"),
    Runr(211, "Runic"),
    Samr(123, "Samaritan"),
    Sara(292, "Sarati"),
    Sarb(105, "Old South Arabian"),
    Saur(344, "Saurashtra"),
    Sgnw(95, "SignWriting"),
    Shaw(281, "Shavian"),
    Shrd(319, "Sharada"),
    Sind(318, "Khudawadi, Sindhi"),
    Sinh(348, "Sinhala"),
    Sora(398, "Sora Sompeng"),
    Sund(362, "Sundanese"),
    Sylo(316, "Syloti Nagri"),
    Syrc(135, "Syriac"),
    Syre(138, "Syriac"),
    Syrj(137, "Syriac"),
    Syrn(136, "Syriac"),
    Tagb(373, "Tagbanwa"),
    Takr(321, "Takri"),
    Tale(353, "Tai Le"),
    Talu(354, "New Tai Lue"),
    Taml(346, "Tamil"),
    Tang(520, "Tangut"),
    Tavt(359, "Tai Viet"),
    Telu(340, "Telugu"),
    Teng(290, "Tengwar"),
    Tfng(120, "Tifinagh"),
    Tglg(370, "Tagalog"),
    Thaa(170, "Thaana"),
    Thai(352, "Thai"),
    Tibt(330, "Tibetan"),
    Tirh(326, "Tirhuta"),
    Ugar(32, "Ugaritic"),
    Vaii(470, "Vai"),
    Visp(280, "Visible Speech"),
    Wara(262, "Warang Citi"),
    Wole(480, "Woleai"),
    Xpeo(24, "Old Persian"),
    Xsux(16, "Cuneiform, Sumero-Akkadian"),
    Yiii(460, "Yi"),
    Zinh(994, "Code for inherited script"),
    Zmth(995, "Mathematical notation"),
    Zsym(996, "Symbols"),
    Zxxx(997, "Code for unwritten documents"),
    Zyyy(998, "Code for undetermined script"),
    Zzzz(999, "Code for uncoded script");
    
    private static final Map<Integer, ScriptCode> numericMap;
    private final String name;
    private final int numeric;

    static {
        numericMap = new HashMap<Integer, ScriptCode>();
        for (ScriptCode scriptCode : ScriptCode.values()) {
            numericMap.put(scriptCode.getNumeric(), scriptCode);
        }
    }

    private ScriptCode(int n2, String string2) {
        this.numeric = n2;
        this.name = string2;
    }

    private static String canonicalize(String string, boolean bl) {
        if (string != null) {
            if (string.length() == 0) {
                return null;
            }
            if (bl) {
                return string;
            }
            StringBuilder stringBuilder = null;
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (i == 0) {
                    if (Character.isUpperCase(c)) continue;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(Character.toUpperCase(c));
                    continue;
                }
                if (stringBuilder == null) {
                    if (Character.isLowerCase(c)) continue;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string.substring(0, i));
                    stringBuilder.append(Character.toLowerCase(c));
                    continue;
                }
                stringBuilder.append(Character.toLowerCase(c));
            }
            if (stringBuilder == null) {
                return string;
            }
            return stringBuilder.toString();
        }
        return null;
    }

    public static ScriptCode getByCode(int n) {
        return numericMap.get(n);
    }

    public static ScriptCode getByCode(String string) {
        return ScriptCode.getByCode(string, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ScriptCode getByCode(String object, boolean bl) {
        if (object == null) return null;
        if (object.length() != 4) {
            return null;
        }
        object = ScriptCode.canonicalize(object, bl);
        try {
            return Enum.valueOf(ScriptCode.class, object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getNumeric() {
        return this.numeric;
    }
}
