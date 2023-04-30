package project.notizprogrammrepository.model.Types.Dates;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils implements Serializable {

    // Gibt für das eingegebene Datum den Wochentag aus.
    public static int getDayOfWeek(Date d){
        SimpleDateFormat formatterDay = new SimpleDateFormat("dd");    //wandelt das datum in einen String um.
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");

        int w_tag;
        int tag = Integer.parseInt(formatterDay.format(d));
        int monat = Integer.parseInt(formatterMonth.format(d));
        int jahr = Integer.parseInt(formatterYear.format(d));
        int h = monat, k = jahr ;
        if (monat < 3)
        {
            h = monat + 12 ;
            k = jahr-1 ;
        }
        w_tag = (tag+2*h + (3*h+3)/5 + k + k/4 - k/100 + k/400 + 1)%7;
        switch (w_tag)
        {
            case 0 ->{return 7;} //Sonntag
            case 1 ->{return 1;} //Montag
            case 2 ->{return 2;} //Dienstag
            case 3 ->{return 3;} //Mittwoch
            case 4 ->{return 4;} //Donnerstag
            case 5 ->{return 5;} //Freitag
            case 6 ->{return 6;} //Samstag
            default ->{return -1;} //gibts nicht
        }
    }
}
