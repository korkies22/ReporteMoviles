// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.net.MalformedURLException;
import de.cisha.chess.util.Logger;
import java.net.URL;
import de.cisha.android.board.Language;
import android.content.Context;

public class NewsServiceURLProvider implements ILocalizedURLProvider
{
    private Context _context;
    
    public NewsServiceURLProvider(final Context context) {
        this._context = context;
    }
    
    @Override
    public URL getUrlFor(final Language language) {
        while (true) {
            while (true) {
                Label_0117: {
                    try {
                        switch (NewsServiceURLProvider.1..SwitchMap.de.cisha.android.board.Language[language.ordinal()]) {
                            case 2: {
                                return new URL(this._context.getResources().getString(2131690091));
                            }
                            case 1: {
                                return new URL(this._context.getResources().getString(2131690089));
                            }
                            default: {
                                break Label_0117;
                            }
                        }
                        return new URL(this._context.getResources().getString(2131690090));
                    }
                    catch (MalformedURLException ex) {
                        Logger.getInstance().debug(NewsServiceURLProvider.class.getName(), MalformedURLException.class.getName(), ex);
                        return null;
                    }
                }
                continue;
            }
        }
    }
}
