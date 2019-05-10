package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.JdbcStudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

public class JdbcStudentDaoExceptionTest {
	private JdbcStudentDao jdbcStudentDao;
	private GroupDao groupDao;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		long groupID = 3;
		groupDao = Mockito.mock(GroupDao.class);
		jdbcStudentDao = new JdbcStudentDao(groupDao);
		Mockito.when(groupDao.findById(groupID)).thenReturn(new Group(groupID));
		thrown.expect(DaoException.class);
	}

	@Test
	public void testFindByIdNegativeValue() throws DaoException {
		thrown.expectMessage(is("Can not find student"));
		jdbcStudentDao.findById(-1);
	}

	@Test
	public void testFindByIdStudentNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find student"));
		jdbcStudentDao.findById(150);
	}

	@Test
	public void testAddStudentsWithSameID() throws DaoException {
		long studentID = 3;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Ivan");
		student.setLastName("Gorbyn");
		student.setGroup(groupDao.findById(groupID));
		Student studentSameID = new Student();
		studentSameID.setPersonID(studentID);
		studentSameID.setFirstName("Gregor");
		studentSameID.setLastName("Pazl");
		studentSameID.setGroup(groupDao.findById(groupID));
		thrown.expectMessage(is("Can not add student"));
		jdbcStudentDao.addStudent(student);
		jdbcStudentDao.addStudent(studentSameID);
	}

	@Test
	public void testRemoveStudent() throws DaoException {
		long studentID = -1;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Mikko");
		student.setLastName("Hirvonen");
		student.setGroup(groupDao.findById(groupID));
		thrown.expectMessage(is("Can not remove student"));
		jdbcStudentDao.removeStudent(student);
	}

	@Test
	public void testUpdateGroup() throws DaoException {
		/* Given */
		long studentID = -1;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Sebastien");
		student.setLastName("Loeb");
		student.setGroup(groupDao.findById(groupID));
		thrown.expectMessage(is("Can not update student"));
		jdbcStudentDao.updateStudent(student);;
	}
}
