/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package de.cisha.android.ui.patterns.text;

import android.graphics.Color;
import de.cisha.android.ui.patterns.text.ColorConstants;
import de.cisha.android.ui.patterns.text.FontName;

public enum CustomTextViewTextStyle {
    TEXT(0, FontName.TREBUCHET, 16.0f, ColorConstants.COLOR_BLACK_FONT),
    HEADER(1, FontName.TREBUCHET_BOLD, 25.0f, Color.rgb((int)120, (int)120, (int)120)),
    MENUGROUP(2, FontName.TREBUCHET_BOLD, 16.0f, -1, 1.0f, 0.0f, -1.0f, Color.argb((int)255, (int)64, (int)64, (int)64)),
    MENUITEM(3, FontName.TREBUCHET_BOLD_ITALIC, 16.0f, ColorConstants.COLOR_BLACK_FONT, 1.0f, 0.0f, 1.0f, -1),
    MENUITEM_SELECTED(4, FontName.TREBUCHET_BOLD_ITALIC, 16.0f, Color.rgb((int)255, (int)255, (int)255), 1.0f, 0.0f, -1.0f, -16777216),
    MENUHEADER(5, FontName.SOMMETSLAB, 24.0f, Color.rgb((int)0, (int)74, (int)184), 2.0f, 0.0f, 0.0f, Color.rgb((int)255, (int)255, (int)255)),
    NAVIGATIONBARITEM(6, FontName.TREBUCHET_BOLD, 10.0f, ColorConstants.COLOR_BLACK_FONT),
    NAVIGATIONBARITEM_SELECTED(7, FontName.TREBUCHET_BOLD, 10.0f, Color.rgb((int)0, (int)74, (int)158)),
    SUB_NAVIGATIONBARITEM(8, FontName.TREBUCHET_BOLD, 15.0f, ColorConstants.COLOR_BLACK_FONT),
    SUB_NAVIGATIONBARITEM_SELECTED(9, FontName.TREBUCHET_BOLD, 15.0f, Color.rgb((int)0, (int)74, (int)158)),
    ANALYSE_MOVELIST(10, FontName.TREBUCHET, 18.0f, ColorConstants.COLOR_BLACK_FONT),
    ANALYSE_MOVELIST_BOLD(11, FontName.TREBUCHET_BOLD, 18.0f, ColorConstants.COLOR_BLACK_FONT),
    LIST_ITEM_TITLE(12, FontName.TREBUCHET, 18.0f, ColorConstants.COLOR_BLACK_FONT),
    LIST_ITEM_SUBTITLE(13, FontName.TREBUCHET, 10.0f, ColorConstants.COLOR_BLACK_FONT),
    LIST_ITEM_SUBTITLE_BOLD(14, FontName.TREBUCHET_BOLD, 10.0f, ColorConstants.COLOR_BLACK_FONT),
    LIST_ITEM_SECTION_HEADER(15, FontName.TREBUCHET_BOLD, 16.0f, Color.rgb((int)153, (int)151, (int)151)),
    REFRESH_HEADER(16, FontName.TREBUCHET_BOLD_ITALIC, 16.0f, Color.rgb((int)106, (int)106, (int)106)),
    ROOKIE_TITLE(17, FontName.SOMMETSLAB, 20.0f, Color.rgb((int)0, (int)74, (int)184), 2.0f, 0.0f, 0.0f, Color.rgb((int)255, (int)255, (int)255)),
    BROADCAST_LIVE_TEXT(18, FontName.TREBUCHET_BOLD_ITALIC, 22.0f, Color.rgb((int)255, (int)255, (int)255), 2.0f, 0.0f, -1.0f, Color.rgb((int)0, (int)0, (int)0)),
    BROADCAST_DETAILS_CLOCK(19, FontName.DS_DIGIT, 28.0f, Color.rgb((int)255, (int)255, (int)255)),
    BROADCAST_DETAILS_CLOCK_SUBTITLE(20, FontName.TREBUCHET, 13.0f, Color.rgb((int)237, (int)237, (int)237)),
    BROADCAST_USER_MODE_TEXT(21, FontName.TREBUCHET_BOLD, 14.0f, Color.rgb((int)243, (int)245, (int)248), 1.0f, 0.0f, -1.0f, Color.rgb((int)67, (int)101, (int)140)),
    BROADCAST_USER_MODE_TEXT_SELECTED(22, FontName.TREBUCHET_BOLD, 14.0f, Color.rgb((int)77, (int)108, (int)116)),
    BROADCAST_MATCH_POINTS(23, FontName.TREBUCHET_ITALIC, 20.0f, Color.rgb((int)0, (int)74, (int)158)),
    BUBBLE(24, FontName.TREBUCHET_BOLD, 10.0f, Color.rgb((int)237, (int)237, (int)237)),
    TOAST(25, FontName.TREBUCHET_BOLD, 12.0f, Color.rgb((int)244, (int)244, (int)244)),
    SIMPLE_LIST_ITEM(26, FontName.TREBUCHET, 16.0f, -16777216),
    SIMPLE_LIST_ITEM_SELECTED(27, FontName.TREBUCHET_BOLD, 16.0f, -1),
    TEXT_TALL(28, FontName.TREBUCHET, 20.0f, ColorConstants.COLOR_BLACK_FONT),
    TEXT_BOLD(29, FontName.TREBUCHET_BOLD, 16.0f, ColorConstants.COLOR_BLACK_FONT),
    TEXT_SMALLL(30, FontName.TREBUCHET, 12.0f, ColorConstants.COLOR_BLACK_FONT),
    TEXT_SMALLL_BOLD(31, FontName.TREBUCHET_BOLD, 12.0f, ColorConstants.COLOR_BLACK_FONT),
    RATING(32, FontName.TREBUCHET_BOLD, 20.0f, ColorConstants.COLOR_BLACK_FONT),
    WIDGET_TITLE(33, FontName.SOMMETSLAB, 20.0f, ColorConstants.COLOR_BLACK_FONT),
    USERNAME_TITLE(34, FontName.TREBUCHET_BOLD, 23.0f, ColorConstants.COLOR_BLACK_FONT),
    MOVELIST(35, FontName.TREBUCHET, 18.0f, ColorConstants.COLOR_BLACK_FONT),
    MOVELIST_SELECTED(36, FontName.TREBUCHET, 18.0f, ColorConstants.COLOR_BLUE_FONT),
    PREMIUM_DIALOG_TITLE(37, FontName.SOMMETSLAB, 20.0f, Color.rgb((int)255, (int)255, (int)255)),
    REGISTRATION_NAVIGATION_HEADER(38, FontName.SOMMETSLAB, 24.0f, Color.rgb((int)0, (int)74, (int)184)),
    TABLE(39, FontName.TREBUCHET, 14.0f, ColorConstants.COLOR_BLACK_FONT),
    TABLE_ITALIC(40, FontName.TREBUCHET_ITALIC, 14.0f, ColorConstants.COLOR_BLACK_FONT),
    LIST_ITEM_WHITE_SOMMETSLAB(41, FontName.SOMMETSLAB, 18.0f, Color.rgb((int)255, (int)255, (int)255)),
    TEASER_INTRO(42, FontName.SOMMETSLAB_LIGHT, 22.0f, ColorConstants.COLOR_BLACK_FONT),
    LIST_ITEM_TITLE_BLUE(43, FontName.TREBUCHET_BOLD, 16.0f, ColorConstants.COLOR_BLUE_FONT),
    PRICE(44, FontName.SOMMETSLAB_LIGHT, 18.0f, ColorConstants.COLOR_BLACK_FONT),
    ENGINE_TIMECONTROL(45, FontName.DS_DIGIT, 16.0f, ColorConstants.COLOR_BLACK_FONT),
    SOMMETSLAB_SMALL(46, FontName.SOMMETSLAB, 16.0f, ColorConstants.COLOR_BLACK_FONT),
    SOMMETSLAB_LIGHT_TITLE(47, FontName.SOMMETSLAB_LIGHT, 24.0f, ColorConstants.COLOR_BLACK_FONT),
    TEXT_TALL_BOLD(48, FontName.TREBUCHET_BOLD, 20.0f, ColorConstants.COLOR_BLACK_FONT);
    
    private int _attrsEnumNumber;
    private int _color;
    private FontName _font;
    private float _fontSize;
    private boolean _hasShadow;
    private int _shadowColor;
    private float _shadowWidth;
    private float _shadowX;
    private float _shadowY;

    private CustomTextViewTextStyle(int n2, FontName fontName, float f, int n3) {
        this._attrsEnumNumber = n2;
        this._font = fontName;
        this._fontSize = f;
        this._color = n3;
    }

    private CustomTextViewTextStyle(int n2, FontName fontName, float f, int n3, float f2, float f3, float f4, int n4) {
        this._shadowWidth = f2;
        this._shadowX = f3;
        this._shadowY = f4;
        this._shadowColor = n4;
        this._hasShadow = true;
        this._attrsEnumNumber = n2;
        this._font = fontName;
        this._fontSize = f;
        this._color = n3;
    }

    public static CustomTextViewTextStyle valueOf(String string) {
        return Enum.valueOf(CustomTextViewTextStyle.class, string);
    }

    public int getAttrEnumNumber() {
        return this._attrsEnumNumber;
    }

    public int getColor() {
        return this._color;
    }

    public FontName getFont() {
        return this._font;
    }

    public float getFontSize() {
        return this._fontSize;
    }

    public int getShadowColor() {
        return this._shadowColor;
    }

    public float getShadowWidth() {
        return this._shadowWidth;
    }

    public float getShadowX() {
        return this._shadowX;
    }

    public float getShadowY() {
        return this._shadowY;
    }

    public boolean hasShadow() {
        return this._hasShadow;
    }
}
