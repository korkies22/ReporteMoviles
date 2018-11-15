/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AggregateException
extends Exception {
    private static final String DEFAULT_MESSAGE = "There were multiple errors.";
    private static final long serialVersionUID = 1L;
    private List<Throwable> innerThrowables;

    public AggregateException(String string, List<? extends Throwable> list) {
        Throwable throwable = list != null && list.size() > 0 ? list.get(0) : null;
        super(string, throwable);
        this.innerThrowables = Collections.unmodifiableList(list);
    }

    public AggregateException(String string, Throwable[] arrthrowable) {
        this(string, Arrays.asList(arrthrowable));
    }

    public AggregateException(List<? extends Throwable> list) {
        this(DEFAULT_MESSAGE, list);
    }

    @Deprecated
    public Throwable[] getCauses() {
        return this.innerThrowables.toArray(new Throwable[this.innerThrowables.size()]);
    }

    @Deprecated
    public List<Exception> getErrors() {
        ArrayList<Exception> arrayList = new ArrayList<Exception>();
        if (this.innerThrowables == null) {
            return arrayList;
        }
        for (Throwable throwable : this.innerThrowables) {
            if (throwable instanceof Exception) {
                arrayList.add((Exception)throwable);
                continue;
            }
            arrayList.add(new Exception(throwable));
        }
        return arrayList;
    }

    public List<Throwable> getInnerThrowables() {
        return this.innerThrowables;
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        Iterator<Throwable> iterator = this.innerThrowables.iterator();
        int n = -1;
        while (iterator.hasNext()) {
            Throwable throwable = iterator.next();
            printStream.append("\n");
            printStream.append("  Inner throwable #");
            printStream.append(Integer.toString(++n));
            printStream.append(": ");
            throwable.printStackTrace(printStream);
            printStream.append("\n");
        }
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        Iterator<Throwable> iterator = this.innerThrowables.iterator();
        int n = -1;
        while (iterator.hasNext()) {
            Throwable throwable = iterator.next();
            printWriter.append("\n");
            printWriter.append("  Inner throwable #");
            printWriter.append(Integer.toString(++n));
            printWriter.append(": ");
            throwable.printStackTrace(printWriter);
            printWriter.append("\n");
        }
    }
}
