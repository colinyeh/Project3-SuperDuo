package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;
    public static String getLeague(int league_num, Context context)
    {

        switch (league_num)
        {
            case SERIE_A: return context.getString(R.string.s_serie_a); // "Seria A";
            case PREMIER_LEGAUE : return context.getString(R.string.s_premier_league); //"Premier League";
            case CHAMPIONS_LEAGUE : return context.getString(R.string.s_uefa_cpampions_league); //"UEFA Champions League";
            case PRIMERA_DIVISION : return context.getString(R.string.s_primera_division); //"Primera Division";
            case BUNDESLIGA : return context.getString(R.string.s_bundeliga); //"Bundesliga";
            default: return context.getString(R.string.s_not_known_league_please_report); //"Not known League Please report";

            //case SERIE_A : return "Seria A";
            //case PREMIER_LEGAUE : return "Premier League";
            //case CHAMPIONS_LEAGUE : return "UEFA Champions League";
            //case PRIMERA_DIVISION : return "Primera Division";
            //case BUNDESLIGA : return "Bundesliga";
            //default: return "Not known League Please report";
        }
    }
    public static String getMatchDay(int match_day,int league_num, Context context)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return context.getString(R.string.s_group_stages); //"Group Stages, Matchday : 6";
                //return "Group Stages, Matchday : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                return context.getString(R.string.s_first_knockout_round); //"First Knockout round";
                //return "First Knockout round";
            }
            else if(match_day == 9 || match_day == 10)
            {
                return context.getString(R.string.s_quarterfinal); //"QuarterFinal";
                //return "QuarterFinal";
            }
            else if(match_day == 11 || match_day == 12)
            {
                return context.getString(R.string.s_semifinal); //"SemiFinal";
                //return "SemiFinal";
            }
            else
            {
                return context.getString(R.string.s_final); //"Final";
                //return "Final";
            }
        }
        else
        {
            return context.getString(R.string.s_matchday) + String.valueOf(match_day);
            //return "Matchday : " + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname, Context context)
    {
        if (teamname==null){return R.drawable.no_icon;}

        if( context.getString(R.string.s_arsenal_london_fc).equals(teamname)) return R.drawable.arsenal;
        else if (context.getString(R.string.s_manchester_united_fc).equals(teamname)) return R.drawable.manchester_united;

        else if ( context.getString(R.string.s_swansea_city).equals(teamname)) return R.drawable.swansea_city_afc;
        else if ( context.getString(R.string.s_leicester_city).equals(teamname)) return R.drawable.leicester_city_fc_hd_logo;
        else if ( context.getString(R.string.s_everton_fc).equals(teamname)) return R.drawable.everton_fc_logo1;
        else if ( context.getString(R.string.s_west_ham_united_fc).equals(teamname)) return R.drawable.west_ham;
        else if ( context.getString(R.string.s_tottenham_hotspur_fc).equals(teamname)) return R.drawable.tottenham_hotspur;
        else if ( context.getString(R.string.s_west_bromwich_albion).equals(teamname)) return R.drawable.west_bromwich_albion_hd_logo;
        else if ( context.getString(R.string.s_sunderland_afc).equals(teamname)) return R.drawable.sunderland;
        else if ( context.getString(R.string.s_stoke_city_fc).equals(teamname)) return R.drawable.stoke_city;
        else return R.drawable.no_icon;

    }



}
