// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.widget;

import android.net.Uri;
import android.widget.RemoteViews;
import de.cisha.chess.util.Logger;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import de.cisha.android.board.MainActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.annotation.TargetApi;
import android.appwidget.AppWidgetProvider;

@TargetApi(14)
public class TournamentsWidgetProvider extends AppWidgetProvider
{
    private static final int REQUEST_CODE_START_MAIN_ACTIVITY = 44;
    private static final int REQUEST_CODE_START_TOURNAMENT_ACTIVITY = 3456;
    
    public PendingIntent getImagePendingIntent(final Context context) {
        return PendingIntent.getActivity(context, 44, new Intent(context, (Class)MainActivity.class), 134217728);
    }
    
    public void onDisabled(final Context context) {
        super.onDisabled(context);
    }
    
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] array) {
        super.onUpdate(context, appWidgetManager, array);
        for (int i = 0; i < array.length; ++i) {
            final int n = array[i];
            final Logger instance = Logger.getInstance();
            final String name = this.getClass().getName();
            final StringBuilder sb = new StringBuilder();
            sb.append("Updateing widget ");
            sb.append(n);
            instance.debug(name, sb.toString());
            final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), 2131427570);
            remoteViews.setOnClickPendingIntent(2131297181, this.getImagePendingIntent(context));
            final Intent intent = new Intent(context, (Class)TournamentWidgetService.class);
            intent.putExtra("appWidgetId", n);
            intent.setData(Uri.parse(intent.toUri(1)));
            remoteViews.setRemoteAdapter(2131297182, intent);
            remoteViews.setPendingIntentTemplate(2131297182, PendingIntent.getActivity(context, 3456, new Intent(context.getApplicationContext(), (Class)MainActivity.class), 134217728));
            appWidgetManager.updateAppWidget(n, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(n, 2131297182);
        }
    }
}
