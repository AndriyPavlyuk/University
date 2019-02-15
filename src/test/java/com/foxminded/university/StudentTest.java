package com.foxminded.university;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.foxminded.university.domain.DomainException;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Student;

public class StudentTest extends ScheduleTest {

	@Test
	public void testGetScheduleForDay() throws DomainException {
		/* Given */
		Student student = givenStudentWithSomeSchedule(super.givenScheduleRecordsForDay);
		/* When */
		List<ScheduleRecord> scheduleForDayStudent = student.getScheduleForDay(LocalDate.of(1999, 1, 2));
		/* Then */
		assertThatContains(scheduleForDayStudent, super.expectedScheduleRecordsForDay);
	}

	@Test
	public void testGetScheduleForMonthOf() throws DomainException {
		/* Given */
		Student student = givenStudentWithSomeSchedule(super.givenScheduleRecordsForMonth);
		/* When */
		List<ScheduleRecord> scheduleForMonthOfStudent = student.getScheduleForMonthOf(LocalDate.of(1999, 2, 2));
		/* Then */
		assertThatContains(scheduleForMonthOfStudent, super.exptectedScheduleRecordsForMonth);
	}

	public Student givenStudentWithSomeSchedule(ScheduleRecord[] scheduleRecords) throws DomainException {
		Student student = new Student();
		student.setFirstName("Igor");
		student.setLastName("Grobenko");
		Group group = new Group();
		Stream.of(scheduleRecords).forEach(t -> {
			try {
				group.addLesson(t);
			} catch (DomainException e) {
				e.printStackTrace();
			}
		});
		group.addStudent(student);
		return student;
	}
}
