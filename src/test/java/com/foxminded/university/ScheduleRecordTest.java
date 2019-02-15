package com.foxminded.university;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.foxminded.university.domain.Room;
import com.foxminded.university.domain.ScheduleRecord;

public class ScheduleRecordTest {

	@Test
	public void testEqualsTrue() {
		/* Given */
		ScheduleRecord scheduleRecord_1 = new ScheduleRecord(LocalDateTime.of(1999, 1, 1, 12, 00), new Room(1));
		ScheduleRecord scheduleRecord_2 = new ScheduleRecord(LocalDateTime.of(1999, 1, 1, 12, 00), new Room(1));
		/* When */
		boolean equals = scheduleRecord_1.equals(scheduleRecord_2);
		/* Then */
		assertTrue(equals);
	}

	@Test
	public void testEqualsFalse() {
		/* Given */
		ScheduleRecord scheduleRecord_1 = new ScheduleRecord(LocalDateTime.of(1999, 1, 1, 12, 00), new Room(1));
		ScheduleRecord scheduleRecord_2 = new ScheduleRecord(LocalDateTime.of(1999, 1, 1, 12, 01), new Room(1));
		/* When */
		boolean equals = scheduleRecord_1.equals(scheduleRecord_2);
		/* Then */
		assertFalse(equals);
	}
}