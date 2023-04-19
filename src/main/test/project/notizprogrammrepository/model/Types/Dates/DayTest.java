package project.notizprogrammrepository.model.Types.Dates;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    @Test
    public void dayComparingTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Day d = new Day(sdf.parse("30-04-2023"));


    }

}