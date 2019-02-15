package com.foxminded.university;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.is;
import com.foxminded.university.domain.DomainException;
import com.foxminded.university.domain.Student;

public class ExceptionStudentTest {
	private Student student;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		student = new Student();
	}

	@Test
	public void getScheduleForDayTest() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Schedule for day was not founded"));
		student.getScheduleForDay(null);
	}

	@Test
	public void getScheduleForMonthOfTest() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Schedule for month was not founded"));
		student.getScheduleForMonthOf(null);	
	}
}