package project.notizprogrammrepository.model.Types.Dates;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MonthTest {

    Month month = new Month();
    Date date = new Date();

    @Test
    void DayOfWeekFinderTest(){
        assertEquals(1, month.DayOfWeekFinder(date));
    }
}