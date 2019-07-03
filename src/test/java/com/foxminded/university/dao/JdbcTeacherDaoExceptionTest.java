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

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcTeacherDao;
import com.foxminded.university.domain.Teacher;

public class JdbcTeacherDaoExceptionTest {
	private JdbcTeacherDao jdbcTeacherDao;
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private DataSourceConnection dataSourceConnection;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		thrown.expect(DaoException.class);
		jdbcTeacherDao = spy(JdbcTeacherDao.class);
		dataSourceConnection=spy(DataSourceConnection.class);
		doReturn(dataSourceConnection).when(jdbcTeacherDao).getDataSourceConnection();
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
		long teacherID = 2;
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
