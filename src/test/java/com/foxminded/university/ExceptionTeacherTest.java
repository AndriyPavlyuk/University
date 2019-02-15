package com.foxminded.university;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.domain.DomainException;
import com.foxminded.university.domain.Teacher;

public class ExceptionTeacherTest {
	private Teacher teacher;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		teacher = new Teacher();
	}

	@Test
	public void testGetScheduleForDay() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Schedule for day was not founded"));
		teacher.getScheduleForDay(null);
	}

	@Test
	public void testGetScheduleForMonthOfTest() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Schedule for month was not founded"));
		teacher.getScheduleForMonthOf(null);
	}

	@Test
	public void testAddLesson() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Lesson was not founded"));
		teacher.addLesson(null);
	}

	@Test
	public void testAddSubject() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Subject was not founded"));
		teacher.addSubject(null);
	}
}
