package mrteddy.gui.types;

public class t_Date {
    public int Year;
    public int Month;
    public int Day;
    public t_Time Time;

    /**
     * yy/mm/dd
     *
     * @param year <code>int </code>Year of date
     * @param month <code>int </code>month of date
     * @param day <code>int </code>day of date
     * @param time {@code t_Time} of date
     */
    public t_Date(int year, int month, int day, t_Time time) {

        Year = year;
        Month = month;
        Day = day;
        Time = time;
    }

    public int daysAway(t_Date currentTime) {
        int _daysAway = 0;

        //year conversion
        int yearsToDays = (Year - currentTime.Year) * 365;

        //month
        int monthsToDays = 0;
        for(int i = currentTime.Month; i < Month; i++)
            monthsToDays += monthToDays(i);

        //days
        int days = Day - currentTime.Day;

        _daysAway = yearsToDays + monthsToDays + days;
        return _daysAway;
    }

    private int monthToDays(int month) {
        int out = 0;
        //giant switch with all the months
        switch (month) {
            //January
            case 1: out = 31; break;
            //February
            case 2: out = 28; break;
            //March
            case 3: out = 31; break;
            //April
            case 4: out = 30; break;
            //May
            case 5: out = 31; break;
            //June
            case 6: out = 30; break;
            //July
            case 7: out = 31; break;
            //August
            case 8: out = 31; break;
            //September
            case 9: out = 30; break;
            //October
            case 10: out = 31; break;
            //November
            case 11: out = 30; break;
            //December
            case 12: out = 31; break;
        }
        return out;
    }

    @Override
    public String toString() {
        return Year + "." + Month + "." + Day + "," + Time;
    }

    /**
     * Class to store time, 24hr format
     */
    public static class t_Time {
        public int Hour;
        public int Minute;

        public t_Time(int hour, int minute) {
            if (hour > 24) hour = 24; //checks hour not greater then 24
            if (minute > 60) minute = 60;
            Hour = hour;
            Minute = minute;
        }

        @Override
        public String toString() {
            return Hour + ":" + Minute;
        }
    }
}

