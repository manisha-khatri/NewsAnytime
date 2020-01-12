package manisha.khatri.newsanytime.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateCalculator {

    public Date getCurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime;
    }

    public Date convertDateIntoISTTimeZone(String publishedDate){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("IST"));
        Date date = null;
        try {
            date = df.parse(publishedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String calculateTotalTimeDifference(Date publishedDate, Date currentDate) {
        long difference = currentDate.getTime() - publishedDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long days = difference / daysInMilli;
        difference = difference % daysInMilli;

        long hours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long minutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long seconds = difference / secondsInMilli;

        return processDatesDifferenceResult(days, hours, minutes, seconds);
    }

    private String processDatesDifferenceResult(long days, long hours, long minutes, long seconds) {
        if(days > 0) {
            String sDays = Long.toString(days);
            if(days == 1)
                return (sDays+" day ago");
            else
                return (sDays+" days ago");
        }
        else if(checkHours(days,hours)!=null) {
           return checkHours(days,hours);
        }
        else if(checkMinutes(hours,minutes)!=null) {
            return checkMinutes(hours,minutes);
        }
        else if(checkSeconds(minutes,seconds)!=null){
            return checkSeconds(minutes,seconds);
        }
        return "";
    }
    String checkHours(long days, long hours){
        if(hours > 0) {
            String  sHours = Long.toString(hours);
            if(hours == 1)
                return (sHours+" hour ago");
            else
                return (sHours+" hours ago");
        }
        else if(hours < 0){
            String strDays = Long.toString(days+1);
            return (strDays+" days ago");
        }
        return null;
    }
    String checkMinutes(long hours, long minutes){
        if(minutes > 0) {
            String  sMinutes = Long.toString(minutes);
            return (sMinutes+" min ago");
        }
        else if(minutes < 0){
            String strHrs = Long.toString(hours+1);
            return (strHrs+" hour ago");
        }
        return null;
    }
    String checkSeconds(long minutes, long seconds){
        if(seconds > 0){
            String  sSeconds = Long.toString(seconds);
            return (sSeconds+" sec ago");
        }
        else if(seconds < 0){
            String strMin = Long.toString(minutes+1);
            return (strMin+" min ago");
        }
        return null;
    }

    public boolean validatePublishedDate(String dateToValidate){
        if(dateToValidate == null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}


