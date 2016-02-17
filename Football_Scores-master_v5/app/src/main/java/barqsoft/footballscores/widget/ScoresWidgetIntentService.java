package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by User on 2016/2/16.
 */

public class ScoresWidgetIntentService extends IntentService {
    public static final String LOG_TAG = ScoresWidgetIntentService.class.getSimpleName();

    private static final String[] SCORES_COLUMNS = {
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
    };

    // these indices must match the projection
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private static final int INDEX_HOME = 2;
    private static final int INDEX_AWAY = 3;
    private static final int INDEX_HOME_GOALS = 4;
    private static final int INDEX_AWAY_GOALS = 5;

    public ScoresWidgetIntentService() {
        super("ScoresWidgetIntentService");
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve all of the widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                ScoresWidget.class));

        Cursor data = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, SCORES_COLUMNS,
                null, null, DatabaseContract.scores_table.DATE_COL + " ASC");

        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        int i_current_position = 0;
        while (data.moveToNext()) {
            if(data.getInt(INDEX_HOME_GOALS) >= 0 && data.getInt(INDEX_AWAY_GOALS) >= 0){
                i_current_position = data.getPosition();
            } else {
                break;
            }
        }

        if(i_current_position >= 0){
            data.moveToPosition(i_current_position);
        } else {
            return;
        }

        // Perform this loop procedure for each Current widget
        for (int appWidgetId : appWidgetIds) {
            int layoutId = R.layout.football_scores_widget;
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);
            //RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);
            String homeTeam = data.getString(INDEX_HOME);
            String awayTeam = data.getString(INDEX_AWAY);
            String scoredatetime = data.getString(INDEX_DATE);
            // Add the data to the RemoteViews
            views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(homeTeam));
            views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(awayTeam));

            // Content Descriptions for RemoteViews were only added in ICS MR1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                views.setContentDescription(R.id.home_crest, getString(R.string.crest_name, homeTeam));
                views.setContentDescription(R.id.away_crest, getString(R.string.crest_name, awayTeam));
            }

            views.setTextViewText(R.id.home_name, homeTeam);
            views.setTextViewText(R.id.away_name, awayTeam);
            views.setTextViewText(R.id.score_textview, Utilies.getScores(
                    data.getInt(INDEX_HOME_GOALS), data.getInt(INDEX_AWAY_GOALS)));
            views.setTextViewText(R.id.data_textview, scoredatetime);

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

}