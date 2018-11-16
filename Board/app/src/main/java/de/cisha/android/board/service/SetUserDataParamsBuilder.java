// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.user.User;
import de.cisha.chess.model.Country;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;

public class SetUserDataParamsBuilder
{
    private Map<String, String> _result;
    
    public SetUserDataParamsBuilder() {
        this._result = new TreeMap<String, String>();
    }
    
    public Map<String, String> createParams() {
        return this._result;
    }
    
    public SetUserDataParamsBuilder setBirthdate(final Date time) {
        if (time != null) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(time);
            final Map<String, String> result = this._result;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(gregorianCalendar.get(5));
            result.put("day", sb.toString());
            final Map<String, String> result2 = this._result;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(gregorianCalendar.get(2) + 1);
            result2.put("month", sb2.toString());
            final Map<String, String> result3 = this._result;
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(gregorianCalendar.get(1));
            result3.put("year", sb3.toString());
        }
        return this;
    }
    
    public SetUserDataParamsBuilder setCountry(final Country country) {
        final Map<String, String> result = this._result;
        String alpha2;
        if (country != null) {
            alpha2 = country.getAlpha2();
        }
        else {
            alpha2 = "";
        }
        result.put("country", alpha2);
        return this;
    }
    
    public SetUserDataParamsBuilder setFirstname(String s) {
        final Map<String, String> result = this._result;
        if (s == null) {
            s = "";
        }
        result.put("firstname", s);
        return this;
    }
    
    public SetUserDataParamsBuilder setGender(final User.Gender gender) {
        final Map<String, String> result = this._result;
        String string;
        if (gender != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(gender.toInt());
            string = sb.toString();
        }
        else {
            string = "0";
        }
        result.put("gender", string);
        return this;
    }
    
    public SetUserDataParamsBuilder setSurname(String s) {
        final Map<String, String> result = this._result;
        if (s == null) {
            s = "";
        }
        result.put("surname", s);
        return this;
    }
    
    public SetUserDataParamsBuilder setWebsite(String s) {
        final Map<String, String> result = this._result;
        if (s == null) {
            s = "";
        }
        result.put("website", s);
        return this;
    }
}
