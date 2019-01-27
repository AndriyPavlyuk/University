package com.foxminded.university.java;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.foxminded.university.java.domain.ScheduleRecord;
import com.foxminded.university.java.domain.Subject;

import com.foxminded.university.java.domain.Teacher;

public class TeacherTest extends ScheduleTest {
	@Test
	public void testGetScheduleForDay() {
		/* Given */
		Teacher teacher = givenTeacherWithSomeSchedule(super.givenScheduleRecordsForDay);
		/* When */
		List<ScheduleRecord> scheduleForDayTeacher = teacher.getScheduleForDay(LocalDate.of(1999, 1, 2));
		/* Then */
		assertThatContains(scheduleForDayTeacher, super.expectedScheduleRecordsForDay);
	}

	@Test
	public void testGetScheduleForMonthOf() {
		/* Given */
		Teacher teacher = givenTeacherWithSomeSchedule(givenScheduleRecordsForMonth);
		/* When */
		List<ScheduleRecord> scheduleForMonthOfTeacher = teacher.getScheduleForMonthOf(LocalDate.of(1999, 2, 2));
		/* Then */
		assertThatContains(scheduleForMonthOfTeacher, super.exptectedScheduleRecordsForMonth);
	}

	private Teacher givenTeacherWithSomeSchedule(ScheduleRecord[] scheduleRecords) {
		Teacher teacher = new Teacher();
		teacher.setFirstName("Ivan");
		Subject subject = new Subject();
		Stream.of(scheduleRecords).forEach(teacher::addLesson);
		teacher.addSubject(subject);
		return teacher;
	}
}
