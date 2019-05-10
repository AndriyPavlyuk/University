package com.foxminded.university.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import com.foxminded.university.domain.Room;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Subject;

public class ScheduleTest {

	ScheduleRecord[] givenScheduleRecordsForDay = new ScheduleRecord[] {
			new ScheduleRecord(LocalDateTime.of(1999, 1, 1, 12, 00), new Subject("Math"), new Room(1)),
			new ScheduleRecord(LocalDateTime.of(1999, 1, 9, 12, 00), new Subject("Music"), new Room(2)),
			new ScheduleRecord(LocalDateTime.of(1998, 1, 10, 12, 00), new Subject("Music"), new Room(2)),
			new ScheduleRecord(LocalDateTime.of(1999, 1, 2, 12, 00), new Subject("Music"), new Room(2)),
			new ScheduleRecord(LocalDateTime.of(1999, 1, 2, 12, 00), new Subject("Biology"), new Room(3)),
			new ScheduleRecord(LocalDateTime.of(1999, 1, 3, 12, 00), new Subject("Math"), new Room(1)) };

	ScheduleRecord[] expectedScheduleRecordsForDay = new ScheduleRecord[] {
			new ScheduleRecord(LocalDateTime.of(1999, 1, 2, 12, 00), new Subject("Music"), new Room(2)),
			new ScheduleRecord(LocalDateTime.of(1999, 1, 2, 12, 00), new Subject("Biology"), new Room(3)) };

	ScheduleRecord[] givenScheduleRecordsForMonth = new ScheduleRecord[] {
			new ScheduleRecord(LocalDateTime.of(1999, 1, 1, 12, 00), new Subject("Math"), new Room(1)),
			new ScheduleRecord(LocalDateTime.of(1999, 2, 2, 12, 00), new Subject("Music"), new Room(2)),
			new ScheduleRecord(LocalDateTime.of(1999, 2, 3, 12, 00), new Subject("Biology"), new Room(3)),
			new ScheduleRecord(LocalDateTime.of(1998, 2, 3, 12, 00), new Subject("Biology"), new Room(3)),
			new ScheduleRecord(LocalDateTime.of(1999, 3, 4, 12, 00), new Subject("Math"), new Room(1)) };

	ScheduleRecord[] exptectedScheduleRecordsForMonth = new ScheduleRecord[] {
			new ScheduleRecord(LocalDateTime.of(1999, 2, 2, 12, 00), new Subject("Music"), new Room(2)),
			new ScheduleRecord(LocalDateTime.of(1999, 2, 3, 12, 00), new Subject("Biology"), new Room(3)) };

	protected void assertThatContains(List<ScheduleRecord> schedule, ScheduleRecord[] scheduleRecords) {
		assertNotNull(schedule);
		assertEquals(scheduleRecords.length, schedule.size());
		assertTrue(schedule.contains(scheduleRecords[0]));
		assertTrue(schedule.contains(scheduleRecords[1]));
	}
}