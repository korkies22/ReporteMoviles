// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.text;

public enum FontName
{
    DS_DIGIT("fonts/DS-DIGI.TTF"), 
    SOMMETSLAB("fonts/SommetSlabRndBlack.otf"), 
    SOMMETSLAB_LIGHT("fonts/SommetSlabRndLight.otf"), 
    TREBUCHET("fonts/TREBUC.TTF"), 
    TREBUCHET_BOLD("fonts/TREBUCBD.TTF"), 
    TREBUCHET_BOLD_ITALIC("fonts/TREBUCBI.TTF"), 
    TREBUCHET_ITALIC("fonts/TREBUCIT.TTF");
    
    private String _assetsFile;
    
    private FontName(final String assetsFile) {
        this._assetsFile = assetsFile;
    }
    
    public String getAssetsFileName() {
        return this._assetsFile;
    }
    
    @Override
    public String toString() {
        return this._assetsFile;
    }
}
