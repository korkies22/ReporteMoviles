/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.text.style.URLSpan
 *  android.text.util.Linkify
 *  android.text.util.Linkify$MatchFilter
 *  android.text.util.Linkify$TransformFilter
 *  android.webkit.WebView
 *  android.widget.TextView
 */
package android.support.v4.text.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
    private static final Comparator<LinkSpec> COMPARATOR;
    private static final String[] EMPTY_STRING;

    static {
        EMPTY_STRING = new String[0];
        COMPARATOR = new Comparator<LinkSpec>(){

            @Override
            public int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
                if (linkSpec.start < linkSpec2.start) {
                    return -1;
                }
                if (linkSpec.start > linkSpec2.start) {
                    return 1;
                }
                if (linkSpec.end < linkSpec2.end) {
                    return 1;
                }
                if (linkSpec.end > linkSpec2.end) {
                    return -1;
                }
                return 0;
            }
        };
    }

    private LinkifyCompat() {
    }

    private static void addLinkMovementMethod(@NonNull TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String string) {
        if (Build.VERSION.SDK_INT >= 26) {
            Linkify.addLinks((TextView)textView, (Pattern)pattern, (String)string);
            return;
        }
        LinkifyCompat.addLinks(textView, pattern, string, null, null, null);
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String string, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            Linkify.addLinks((TextView)textView, (Pattern)pattern, (String)string, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
            return;
        }
        LinkifyCompat.addLinks(textView, pattern, string, null, matchFilter, transformFilter);
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String string, @Nullable String[] arrstring, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            Linkify.addLinks((TextView)textView, (Pattern)pattern, (String)string, (String[])arrstring, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
            return;
        }
        SpannableString spannableString = SpannableString.valueOf((CharSequence)textView.getText());
        if (LinkifyCompat.addLinks((Spannable)spannableString, pattern, string, arrstring, matchFilter, transformFilter)) {
            textView.setText((CharSequence)spannableString);
            LinkifyCompat.addLinkMovementMethod(textView);
        }
    }

    public static boolean addLinks(@NonNull Spannable spannable, int n) {
        Object object;
        if (Build.VERSION.SDK_INT >= 27) {
            return Linkify.addLinks((Spannable)spannable, (int)n);
        }
        if (n == 0) {
            return false;
        }
        Object object2 = (URLSpan[])spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i = ((URLSpan[])object2).length - 1; i >= 0; --i) {
            spannable.removeSpan((Object)object2[i]);
        }
        if ((n & 4) != 0) {
            Linkify.addLinks((Spannable)spannable, (int)4);
        }
        object2 = new ArrayList();
        if ((n & 1) != 0) {
            object = PatternsCompat.AUTOLINK_WEB_URL;
            Linkify.MatchFilter matchFilter = Linkify.sUrlMatchFilter;
            LinkifyCompat.gatherLinks((ArrayList<LinkSpec>)object2, spannable, (Pattern)object, new String[]{"http://", "https://", "rtsp://"}, matchFilter, null);
        }
        if ((n & 2) != 0) {
            LinkifyCompat.gatherLinks((ArrayList<LinkSpec>)object2, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((n & 8) != 0) {
            LinkifyCompat.gatherMapLinks((ArrayList<LinkSpec>)object2, spannable);
        }
        LinkifyCompat.pruneOverlaps((ArrayList<LinkSpec>)object2, spannable);
        if (object2.size() == 0) {
            return false;
        }
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object = (LinkSpec)object2.next();
            if (object.frameworkAddedSpan != null) continue;
            LinkifyCompat.applyLink(object.url, object.start, object.end, spannable);
        }
        return true;
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String string) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks((Spannable)spannable, (Pattern)pattern, (String)string);
        }
        return LinkifyCompat.addLinks(spannable, pattern, string, null, null, null);
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String string, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks((Spannable)spannable, (Pattern)pattern, (String)string, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
        }
        return LinkifyCompat.addLinks(spannable, pattern, string, null, matchFilter, transformFilter);
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern object, @Nullable String arrstring, @Nullable String[] object2, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        String[] arrstring2;
        block8 : {
            block7 : {
                if (Build.VERSION.SDK_INT >= 26) {
                    return Linkify.addLinks((Spannable)spannable, (Pattern)object, (String)arrstring, (String[])object2, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
                }
                arrstring2 = arrstring;
                if (arrstring == null) {
                    arrstring2 = "";
                }
                if (object2 == null) break block7;
                arrstring = object2;
                if (((String[])object2).length >= 1) break block8;
            }
            arrstring = EMPTY_STRING;
        }
        String[] arrstring3 = new String[arrstring.length + 1];
        arrstring3[0] = arrstring2.toLowerCase(Locale.ROOT);
        int n = 0;
        while (n < arrstring.length) {
            object2 = arrstring[n];
            object2 = object2 == null ? "" : object2.toLowerCase(Locale.ROOT);
            arrstring3[++n] = object2;
        }
        object = object.matcher((CharSequence)spannable);
        boolean bl = false;
        while (object.find()) {
            n = object.start();
            int n2 = object.end();
            boolean bl2 = matchFilter != null ? matchFilter.acceptMatch((CharSequence)spannable, n, n2) : true;
            if (!bl2) continue;
            LinkifyCompat.applyLink(LinkifyCompat.makeUrl(object.group(0), arrstring3, (Matcher)object, transformFilter), n, n2, spannable);
            bl = true;
        }
        return bl;
    }

    public static boolean addLinks(@NonNull TextView textView, int n) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks((TextView)textView, (int)n);
        }
        if (n == 0) {
            return false;
        }
        CharSequence charSequence = textView.getText();
        if (charSequence instanceof Spannable) {
            if (LinkifyCompat.addLinks((Spannable)charSequence, n)) {
                LinkifyCompat.addLinkMovementMethod(textView);
                return true;
            }
            return false;
        }
        if (LinkifyCompat.addLinks((Spannable)(charSequence = SpannableString.valueOf((CharSequence)charSequence)), n)) {
            LinkifyCompat.addLinkMovementMethod(textView);
            textView.setText(charSequence);
            return true;
        }
        return false;
    }

    private static void applyLink(String string, int n, int n2, Spannable spannable) {
        spannable.setSpan((Object)new URLSpan(string), n, n2, 33);
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern object, String[] arrstring, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        object = object.matcher((CharSequence)spannable);
        while (object.find()) {
            int n = object.start();
            int n2 = object.end();
            if (matchFilter != null && !matchFilter.acceptMatch((CharSequence)spannable, n, n2)) continue;
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.url = LinkifyCompat.makeUrl(object.group(0), arrstring, (Matcher)object, transformFilter);
            linkSpec.start = n;
            linkSpec.end = n2;
            arrayList.add(linkSpec);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable object) {
        object = object.toString();
        int n = 0;
        do {
            int n2;
            String string;
            block8 : {
                try {
                    string = WebView.findAddress((String)object);
                    if (string == null) return;
                }
                catch (UnsupportedOperationException unsupportedOperationException) {
                    return;
                }
                n2 = object.indexOf(string);
                if (n2 >= 0) break block8;
                return;
            }
            LinkSpec linkSpec = new LinkSpec();
            int n3 = string.length() + n2;
            linkSpec.start = n2 + n;
            n += n3;
            linkSpec.end = n;
            object = object.substring(n3);
            try {
                string = URLEncoder.encode(string, "UTF-8");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("geo:0,0?q=");
                stringBuilder.append(string);
                linkSpec.url = stringBuilder.toString();
                arrayList.add(linkSpec);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
            }
            continue;
            break;
        } while (true);
    }

    private static String makeUrl(@NonNull String charSequence, @NonNull String[] arrstring, Matcher object, @Nullable Linkify.TransformFilter transformFilter) {
        boolean bl;
        block6 : {
            String string = charSequence;
            if (transformFilter != null) {
                string = transformFilter.transformUrl((Matcher)object, (String)charSequence);
            }
            int n = 0;
            do {
                boolean bl2 = true;
                if (n >= arrstring.length) break;
                if (string.regionMatches(true, 0, arrstring[n], 0, arrstring[n].length())) {
                    bl = bl2;
                    charSequence = string;
                    if (!string.regionMatches(false, 0, arrstring[n], 0, arrstring[n].length())) {
                        charSequence = new StringBuilder();
                        charSequence.append(arrstring[n]);
                        charSequence.append(string.substring(arrstring[n].length()));
                        charSequence = charSequence.toString();
                        bl = bl2;
                    }
                    break block6;
                }
                ++n;
            } while (true);
            bl = false;
            charSequence = string;
        }
        object = charSequence;
        if (!bl) {
            object = charSequence;
            if (arrstring.length > 0) {
                object = new StringBuilder();
                object.append(arrstring[0]);
                object.append((String)charSequence);
                object = object.toString();
            }
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        LinkSpec linkSpec;
        int n = spannable.length();
        int n2 = 0;
        URLSpan uRLSpan = (URLSpan[])spannable.getSpans(0, n, URLSpan.class);
        for (n = 0; n < ((URLSpan[])uRLSpan).length; ++n) {
            linkSpec = new LinkSpec();
            linkSpec.frameworkAddedSpan = uRLSpan[n];
            linkSpec.start = spannable.getSpanStart((Object)uRLSpan[n]);
            linkSpec.end = spannable.getSpanEnd((Object)uRLSpan[n]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, COMPARATOR);
        int n3 = arrayList.size();
        n = n2;
        while (n < n3 - 1) {
            uRLSpan = arrayList.get(n);
            int n4 = n + 1;
            linkSpec = arrayList.get(n4);
            if (uRLSpan.start <= linkSpec.start && uRLSpan.end > linkSpec.start) {
                n2 = linkSpec.end <= uRLSpan.end || uRLSpan.end - uRLSpan.start > linkSpec.end - linkSpec.start ? n4 : (uRLSpan.end - uRLSpan.start < linkSpec.end - linkSpec.start ? n : -1);
                if (n2 != -1) {
                    uRLSpan = arrayList.get((int)n2).frameworkAddedSpan;
                    if (uRLSpan != null) {
                        spannable.removeSpan((Object)uRLSpan);
                    }
                    arrayList.remove(n2);
                    --n3;
                    continue;
                }
            }
            n = n4;
        }
        return;
    }

    private static class LinkSpec {
        int end;
        URLSpan frameworkAddedSpan;
        int start;
        String url;

        LinkSpec() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface LinkifyMask {
    }

}
