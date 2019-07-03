package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.JdbcStudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

public class JdbcStudentDaoExceptionTest {
	private JdbcStudentDao jdbcStudentDao;
	private GroupDao groupDao;
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private DataSourceConnection dataSourceConnection;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		long groupID = 3;
		groupDao = mock(GroupDao.class);
		when(groupDao.findById(groupID)).thenReturn(new Group(groupID));
		thrown.expect(DaoException.class);
		jdbcStudentDao= spy(new JdbcStudentDao(groupDao));
	    dataSourceConnection=spy(DataSourceConnection.class);
		doReturn(dataSourceConnection).when(jdbcStudentDao).getDataSourceConnection();
		doReturn(createConnection()).when(dataSourceConnection).getConnection();
	}
	
	private Connection createConnection() throws DaoException {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DaoException("Db properties could not be founded",e);
		}
		return connection;
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
		long studentID = 2;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Peter");
		student.setLastName("Solberg");
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
