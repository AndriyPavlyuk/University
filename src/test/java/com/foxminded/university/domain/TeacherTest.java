package com.foxminded.university.domain;

import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.domain.DomainException;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Subject;

import com.foxminded.university.domain.Teacher;

public class TeacherTest extends ScheduleTest {
	private Teacher teacher;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		teacher = new Teacher();
	}
	
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
	
	@Test
	public void testGetScheduleForDayException() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Schedule for day was not founded"));
		teacher.getScheduleForDay(null);
	}

	@Test
	public void testGetScheduleForMonthOfException() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Schedule for month was not founded"));
		teacher.getScheduleForMonthOf(null);
	}

	@Test
	public void testAddLessonException() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Lesson was not founded"));
		teacher.addLesson(null);
	}

	@Test
	public void testAddSubjectException() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Subject was not founded"));
		teacher.addSubject(null);
	}
}
