package com.blackstone.goldenquran.GetTimeClasses;

import java.util.Date;
import java.util.TimeZone;

public class TimeZoneUtil {
    //---------------------- Time-Zone Functions -----------------------


    // compute local time-zone for a specific date
    public static double getTimeZone(Date date)
    {
        Date localDate = new Date(date.getYear(), date.getMonth(), date.getDay());
        String GMTString = localDate.toGMTString();
        Date GMTDate = new Date(GMTString.substring(0, GMTString.lastIndexOf(' ')- 1));
        double hoursDiff = (localDate.getTime()- GMTDate.getTime()) / (1000* 60* 60);
        return hoursDiff;
    }


    // compute base time-zone of the system
    public static TimeZone getBaseTimeZone ()
    {
        return TimeZone.getDefault();
    }


    // detect daylight saving in a given date
    public static boolean detectDaylightSaving(Date date, TimeZone timeZone)
    {
        return timeZone.inDaylightTime(date);
    }


    // return effective timezone for a given date
    public static double effectiveTimeZone(int year,int month,int day,TimeZone timeZone)
    {
        if (timeZone == null)
            timeZone = TimeZone.getDefault();
        long time = (new Date(year,month,day)).getTime();
        if(!timeZone.inDaylightTime(new Date(year,month,day))){
            return (timeZone.getOffset(time))/(60d*60d*1000d);
        }else{
            return (timeZone.getOffset(time))/(60d*60d*1000d)-1;
        }

    }

}