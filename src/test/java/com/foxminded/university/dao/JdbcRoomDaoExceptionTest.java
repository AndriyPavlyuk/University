package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcRoomDao;
import com.foxminded.university.domain.Room;

public class JdbcRoomDaoExceptionTest {
	private JdbcRoomDao jdbcRoomDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		jdbcRoomDao = new JdbcRoomDao();
		thrown.expect(DaoException.class);
	}

	@Test
	public void testFindByIdNegativeValue() throws DaoException {
		thrown.expectMessage(is("Can not find room"));
		jdbcRoomDao.findById(-1);
	}

	@Test
	public void testFindByIdGroupNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find room"));
		jdbcRoomDao.findById(15);
	}
	
	@Test
	public void testAddGroupWithSameID() throws DaoException {
		Room room = new Room();
		Room roomSameID = new Room();
		long roomID = 3;
		int number=5;
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
