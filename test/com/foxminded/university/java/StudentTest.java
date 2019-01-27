package com.foxminded.university.java;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.foxminded.university.java.domain.Group;
import com.foxminded.university.java.domain.ScheduleRecord;
import com.foxminded.university.java.domain.Student;

public class StudentTest extends ScheduleTest {

	@Test
	public void testGetScheduleForDay() {
		/* Given */
		Student student = givenStudentWithSomeSchedule(super.givenScheduleRecordsForDay);
		/* When */
		List<ScheduleRecord> scheduleForDayStudent = student.getScheduleForDay(LocalDate.of(1999, 1, 2));
		/* Then */
		assertThatContains(scheduleForDayStudent, super.expectedScheduleRecordsForDay);
	}

	@Test
	public void testGetScheduleForMonthOf() {
		/* Given */
		Student student = givenStudentWithSomeSchedule(super.givenScheduleRecordsForMonth);
		/* When */
		List<ScheduleRecord> scheduleForMonthOfStudent = student.getScheduleForMonthOf(LocalDate.of(1999, 2, 2));
		/* Then */
		assertThatContains(scheduleForMonthOfStudent, super.exptectedScheduleRecordsForMonth);
	}

	public Student givenStudentWithSomeSchedule(ScheduleRecord[] scheduleRecords) {
		Student student = new Student();
		student.setFirstName("Igor");
		Group group = new Group();
		Stream.of(scheduleRecords).forEach(group::addLesson);
		group.addStudent(student);
		return student;
	}
}
