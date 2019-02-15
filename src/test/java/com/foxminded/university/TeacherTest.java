package com.foxminded.university;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.foxminded.university.domain.DomainException;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Subject;

import com.foxminded.university.domain.Teacher;

public class TeacherTest extends ScheduleTest {
	@Test
	public void testGetScheduleForDay() throws DomainException {
		/* Given */
		Teacher teacher = givenTeacherWithSomeSchedule(super.givenScheduleRecordsForDay);
		/* When */
		List<ScheduleRecord> scheduleForDayTeacher = teacher.getScheduleForDay(LocalDate.of(1999, 1, 2));
		/* Then */
		assertThatContains(scheduleForDayTeacher, super.expectedScheduleRecordsForDay);
	}

	@Test
	public void testGetScheduleForMonthOf() throws DomainException {
		/* Given */
		Teacher teacher = givenTeacherWithSomeSchedule(givenScheduleRecordsForMonth);
		/* When */
		List<ScheduleRecord> scheduleForMonthOfTeacher = teacher.getScheduleForMonthOf(LocalDate.of(1999, 2, 2));
		/* Then */
		assertThatContains(scheduleForMonthOfTeacher, super.exptectedScheduleRecordsForMonth);
	}

	private Teacher givenTeacherWithSomeSchedule(ScheduleRecord[] scheduleRecords) throws DomainException  {
		Teacher teacher = new Teacher();
		teacher.setFirstName("Ivan");
		teacher.setLastName("Petrenkov");
		Subject subject = new Subject();
		Stream.of(scheduleRecords).forEach(t -> {
			try {
				teacher.addLesson(t);
			} catch (DomainException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		teacher.addSubject(subject);
		return teacher;
	}
}
