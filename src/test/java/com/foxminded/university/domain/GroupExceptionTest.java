package com.foxminded.university.domain;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.domain.DomainException;
import com.foxminded.university.domain.Group;

public class GroupExceptionTest {
	private Group group;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		group = new Group();
	}

	@Test
	public void testAddLesson() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Lesson was not founded"));
		group.addLesson(null);
	}
	
	@Test
	public void testAddStudent() throws DomainException {
		thrown.expect(DomainException.class);
		thrown.expectMessage(is("Student was not founded"));
		group.addStudent(null);	
	}
}
