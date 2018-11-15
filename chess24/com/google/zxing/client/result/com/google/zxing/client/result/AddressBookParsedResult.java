/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class AddressBookParsedResult
extends ParsedResult {
    private final String[] addressTypes;
    private final String[] addresses;
    private final String birthday;
    private final String[] emailTypes;
    private final String[] emails;
    private final String[] geo;
    private final String instantMessenger;
    private final String[] names;
    private final String[] nicknames;
    private final String note;
    private final String org;
    private final String[] phoneNumbers;
    private final String[] phoneTypes;
    private final String pronunciation;
    private final String title;
    private final String[] urls;

    public AddressBookParsedResult(String[] arrstring, String[] arrstring2, String string, String[] arrstring3, String[] arrstring4, String[] arrstring5, String[] arrstring6, String string2, String string3, String[] arrstring7, String[] arrstring8, String string4, String string5, String string6, String[] arrstring9, String[] arrstring10) {
        super(ParsedResultType.ADDRESSBOOK);
        this.names = arrstring;
        this.nicknames = arrstring2;
        this.pronunciation = string;
        this.phoneNumbers = arrstring3;
        this.phoneTypes = arrstring4;
        this.emails = arrstring5;
        this.emailTypes = arrstring6;
        this.instantMessenger = string2;
        this.note = string3;
        this.addresses = arrstring7;
        this.addressTypes = arrstring8;
        this.org = string4;
        this.birthday = string5;
        this.title = string6;
        this.urls = arrstring9;
        this.geo = arrstring10;
    }

    public AddressBookParsedResult(String[] arrstring, String[] arrstring2, String[] arrstring3, String[] arrstring4, String[] arrstring5, String[] arrstring6, String[] arrstring7) {
        this(arrstring, null, null, arrstring2, arrstring3, arrstring4, arrstring5, null, null, arrstring6, arrstring7, null, null, null, null, null);
    }

    public String[] getAddressTypes() {
        return this.addressTypes;
    }

    public String[] getAddresses() {
        return this.addresses;
    }

    public String getBirthday() {
        return this.birthday;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(100);
        AddressBookParsedResult.maybeAppend(this.names, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.nicknames, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.pronunciation, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.title, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.org, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.addresses, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.phoneNumbers, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.emails, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.instantMessenger, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.urls, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.birthday, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.geo, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.note, stringBuilder);
        return stringBuilder.toString();
    }

    public String[] getEmailTypes() {
        return this.emailTypes;
    }

    public String[] getEmails() {
        return this.emails;
    }

    public String[] getGeo() {
        return this.geo;
    }

    public String getInstantMessenger() {
        return this.instantMessenger;
    }

    public String[] getNames() {
        return this.names;
    }

    public String[] getNicknames() {
        return this.nicknames;
    }

    public String getNote() {
        return this.note;
    }

    public String getOrg() {
        return this.org;
    }

    public String[] getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public String[] getPhoneTypes() {
        return this.phoneTypes;
    }

    public String getPronunciation() {
        return this.pronunciation;
    }

    public String getTitle() {
        return this.title;
    }

    public String[] getURLs() {
        return this.urls;
    }
}
