package ch.juventus.schichtenschmiede.persistency.entity;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author: Alexandra
 * @since: 23.01.2019
 **/
public class DailyScheduleTest {

    @Test
    public void testSetDays() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = cal.getTime();

        DailySchedule dailySchedule = new DailySchedule();
        dailySchedule.setDate(new java.sql.Date(monday.getTime()));

        dailySchedule.setDays();

        assertTrue(dailySchedule.isMonday());
    }

    @Test
    public void testSetDaysFail() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = cal.getTime();

        DailySchedule dailySchedule = new DailySchedule();
        dailySchedule.setDate(new java.sql.Date(monday.getTime()));

        dailySchedule.setDays();

        assertFalse(dailySchedule.isWednesday());
    }
}