package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcSubjectDao;
import com.foxminded.university.domain.Subject;

public class JdbcSubjectDaoExceptionTest {
	private JdbcSubjectDao jdbcSubjectDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		jdbcSubjectDao = new JdbcSubjectDao();
		thrown.expect(DaoException.class);
	}

	@Test
	public void testFindByIdNegativeValue() throws DaoException {
		thrown.expectMessage(is("Can not find subject"));
		jdbcSubjectDao.findById(-1);
	}

	@Test
	public void testFindByIdSubjectNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find subject"));
		jdbcSubjectDao.findById(15);
	}
	
	@Test
	public void testAddSubjectWithSameID() throws DaoException {
		long subjectID = 3;
		String name="Physics";
		Subject subject = new Subject();
		Subject subjectSameID = new Subject();
		subject.setSubjectID(subjectID);
		subject.setName(name);
		subjectSameID.setSubjectID(subjectID);
		subjectSameID.setName(name);
		thrown.expectMessage(is("Can not add subject"));	
		jdbcSubjectDao.addSubject(subject);
		jdbcSubjectDao.addSubject(subjectSameID);
	}

	@Test
	public void testRemoveGroup() throws DaoException {
		long subjectID = -2;
		String name="Mathematics";
		Subject subject = new Subject();
		subject.setSubjectID(subjectID);
		subject.setName(name);
		thrown.expectMessage(is("Can not remove subject"));
		jdbcSubjectDao.removeSubject(subject);		
	}

	@Test
	public void testUpdateGroup() throws DaoException {
		/* Given */
		long subjectID = -2;
		String name="Geography";
		Subject subject = new Subject();
		subject.setSubjectID(subjectID);
		subject.setName(name);
		thrown.expectMessage(is("Can not update subject"));
		jdbcSubjectDao.updateSubject(subject);
	}
}
