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
import com.foxminded.university.dao.JdbcRoomDao;
import com.foxminded.university.domain.Room;

public class JdbcRoomDaoExceptionTest {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcRoomDao jdbcRoomDao;
	private DataSourceConnection dataSourceConnection;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		thrown.expect(DaoException.class);
		jdbcRoomDao = spy(JdbcRoomDao.class);
	    dataSourceConnection=spy(DataSourceConnection.class);
		doReturn(dataSourceConnection).when(jdbcRoomDao).getDataSourceConnection();
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
		thrown.expectMessage(is("Can not find room"));
		jdbcRoomDao.findById(-1);
	}

	@Test
	public void testFindByIdRoomNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find room"));
		jdbcRoomDao.findById(15);
	}
	
	@Test
	public void testAddRoomWithSameID() throws DaoException {
		Room room = new Room();
		Room roomSameID = new Room();
		long roomID = 2;
		int number=2;
		room.setRoomID(roomID);
		room.setNumber(number);
		roomSameID.setRoomID(roomID);
		roomSameID.setNumber(number);
		thrown.expectMessage(is("Can not add room"));	
		jdbcRoomDao.addRoom(room);
		jdbcRoomDao.addRoom(roomSameID);
	}

	@Test
	public void testRemoveRoom() throws DaoException {
		long roomID = -2;
		int number=2;
		Room room = new Room();
		room.setRoomID(roomID);
		room.setNumber(number);
		thrown.expectMessage(is("Can not remove room"));
		jdbcRoomDao.removeRoom(room);		
	}

	@Test
	public void testUpdateRoom() throws DaoException {
		/* Given */
		long roomID = -2;
		int number=6;
		Room room = new Room();
		room.setRoomID(roomID);
		room.setNumber(number);
		thrown.expectMessage(is("Can not update room"));	
		jdbcRoomDao.updateRoom(room);
	}
}
