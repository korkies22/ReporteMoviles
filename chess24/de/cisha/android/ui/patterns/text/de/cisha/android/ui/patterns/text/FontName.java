/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.text;

public enum FontName {
    TREBUCHET("fonts/TREBUC.TTF"),
    TREBUCHET_BOLD("fonts/TREBUCBD.TTF"),
    TREBUCHET_ITALIC("fonts/TREBUCIT.TTF"),
    TREBUCHET_BOLD_ITALIC("fonts/TREBUCBI.TTF"),
    DS_DIGIT("fonts/DS-DIGI.TTF"),
    SOMMETSLAB("fonts/SommetSlabRndBlack.otf"),
    SOMMETSLAB_LIGHT("fonts/SommetSlabRndLight.otf");
    
    private String _assetsFile;

    private FontName(String string2) {
        this._assetsFile = string2;
    }

    public String getAssetsFileName() {
        return this._assetsFile;
    }

    public String toString() {
        return this._assetsFile;
    }
}
