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
import com.foxminded.university.dao.JdbcGroupDao;
import com.foxminded.university.domain.Group;

public class JdbcGroupDaoExceptionTest {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcGroupDao jdbcGroupDao;
	private DataSourceConnection dataSourceConnection;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		thrown.expect(DaoException.class);
		jdbcGroupDao = spy(JdbcGroupDao.class);
		dataSourceConnection=spy(DataSourceConnection.class);
		//doReturn(dataSourceConnection).when(jdbcGroupDao).getDataSourceConnection();
		doReturn(createConnection()).when(dataSourceConnection).getDataSourceConnection();
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
		thrown.expectMessage(is("Can not find group"));
		jdbcGroupDao.findById(-1);
	}

	@Test
	public void testFindByIdGroupNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find group"));
		jdbcGroupDao.findById(15);
	}
	
	@Test
	public void testAddGroupWithSameID() throws DaoException {
		Group group = new Group();
		Group groupSameID = new Group();
		long groupID = 2;
		String name = "SR-02";
		group.setGroupID(groupID);
		group.setName(name);
		groupSameID.setGroupID(groupID);
		groupSameID.setName(name);
		thrown.expectMessage(is("Can not add group"));	
		jdbcGroupDao.addGroup(group);
		jdbcGroupDao.addGroup(groupSameID);
	}

	@Test
	public void testRemoveGroup() throws DaoException {
		long groupID = -2;
		String name = "SR-02";
		Group group = new Group();
		group.setGroupID(groupID);
		group.setName(name);
		thrown.expectMessage(is("Can not remove group"));
		jdbcGroupDao.removeGroup(group);		
	}

	@Test
	public void testUpdateGroup() throws DaoException {
		/* Given */
		long groupID = -5;
		String name = "SR-03";
		Group group = new Group();
		group.setGroupID(groupID);
		group.setName(name);
		thrown.expectMessage(is("Can not update group"));	
		jdbcGroupDao.updateGroup(group);
	}
}
