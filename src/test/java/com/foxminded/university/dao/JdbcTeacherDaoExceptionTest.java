package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcTeacherDao;
import com.foxminded.university.domain.Teacher;

public class JdbcTeacherDaoExceptionTest {
	private JdbcTeacherDao jdbcTeacherDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		jdbcTeacherDao = new JdbcTeacherDao();
		thrown.expect(DaoException.class);
	}

	@Test
	public void testFindByIdNegativeValue() throws DaoException {
		thrown.expectMessage(is("Can not find teacher"));
		jdbcTeacherDao.findById(-1);
	}

	@Test
	public void testFindByIdTeacherNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find teacher"));
		jdbcTeacherDao.findById(15);
	}
	
	@Test
	public void testAddGroupWithSameID() throws DaoException {
		Teacher teacher = new Teacher();
		Teacher teacherSameID = new Teacher();
		long teacherID = 3;
		teacher.setPersonID(teacherID);
		teacher.setFirstName("Andriy");
		teacher.setLastName("Gugynov");
		teacherSameID.setPersonID(teacherID);
		teacherSameID.setFirstName("Andriy");
		teacherSameID.setLastName("Gugynov");
		thrown.expectMessage(is("Can not add teacher"));	
		jdbcTeacherDao.addTeacher(teacher);
		jdbcTeacherDao.addTeacher(teacherSameID);
	}

	@Test
	public void testRemoveGroup() throws DaoException {
		long teacherID = -2;
		Teacher teacher = new Teacher();
		teacher.setPersonID(teacherID);
		teacher.setFirstName("Volodymur");
		teacher.setLastName("Petrenko");
		thrown.expectMessage(is("Can not remove teacher"));
		jdbcTeacherDao.removeTeacher(teacher);	
	}

	@Test
	public void testUpdateGroup() throws DaoException {
		/* Given */
		long teacherID = -1;
		Teacher teacher = new Teacher();
		teacher.setPersonID(teacherID);
		teacher.setFirstName("Sebastien");
		teacher.setLastName("Loeb");
		thrown.expectMessage(is("Can not update teacher"));	
		jdbcTeacherDao.updateTeacher(teacher);
	}
	
}
