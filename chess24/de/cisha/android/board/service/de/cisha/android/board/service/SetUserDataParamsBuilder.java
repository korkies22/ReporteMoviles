/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.user.User;
import de.cisha.chess.model.Country;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

public class SetUserDataParamsBuilder {
    private Map<String, String> _result = new TreeMap<String, String>();

    public Map<String, String> createParams() {
        return this._result;
    }

    public SetUserDataParamsBuilder setBirthdate(Date map) {
        if (map != null) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime((Date)((Object)map));
            map = this._result;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(gregorianCalendar.get(5));
            map.put("day", stringBuilder.toString());
            map = this._result;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(gregorianCalendar.get(2) + 1);
            map.put("month", stringBuilder.toString());
            map = this._result;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(gregorianCalendar.get(1));
            map.put("year", stringBuilder.toString());
        }
        return this;
    }

    public SetUserDataParamsBuilder setCountry(Country object) {
        Map<String, String> map = this._result;
        object = object != null ? object.getAlpha2() : "";
        map.put("country", (String)object);
        return this;
    }

    public SetUserDataParamsBuilder setFirstname(String string) {
        Map<String, String> map = this._result;
        if (string == null) {
            string = "";
        }
        map.put("firstname", string);
        return this;
    }

    public SetUserDataParamsBuilder setGender(User.Gender object) {
        Map<String, String> map = this._result;
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(object.toInt());
            object = stringBuilder.toString();
        } else {
            object = "0";
        }
        map.put("gender", (String)object);
        return this;
    }

    public SetUserDataParamsBuilder setSurname(String string) {
        Map<String, String> map = this._result;
        if (string == null) {
            string = "";
        }
        map.put("surname", string);
        return this;
    }

    public SetUserDataParamsBuilder setWebsite(String string) {
        Map<String, String> map = this._result;
        if (string == null) {
            string = "";
        }
        map.put("website", string);
        return this;
    }
}
