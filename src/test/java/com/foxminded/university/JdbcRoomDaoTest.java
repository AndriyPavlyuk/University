package com.foxminded.university;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.university.dao.ConnectionProperties;
import com.foxminded.university.dao.JdbcRoomDao;
import com.foxminded.university.domain.Room;

public class JdbcRoomDaoTest extends DBTestCase {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcRoomDao jdbcRoomDao;

	public JdbcRoomDaoTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER_NAME);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
	}

	protected IDataSet getDataSet() throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream("inputRoomTestTable.xml");
		return new FlatXmlDataSetBuilder().build(inputFile);
	}

	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	private ITable getActualTable() throws Exception {
		IDataSet actualDataSet = getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("ROOMS");
		return actualTable;
	}

	private ITable getExpextedTable(String fileName) throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream(fileName);
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
		ITable expectedTable = expectedDataSet.getTable("ROOMS");
		return expectedTable;
	}

	@Before
	public void setUp() throws SQLException {
		jdbcRoomDao = new JdbcRoomDao();
	}
	
	@Test
	public void testFindAll() throws Exception {	
		/* Given */
		Collection<Room> roomsList = jdbcRoomDao.findAll();
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("findAllRoomTestTable.xml");
		/* Then */
		Assertion.assertEquals(expectedTable, actualTable);
		Assert.assertEquals(expectedTable.getRowCount(), roomsList.size());
	}

	@Test
	public void testAddRoom() throws Exception {
		/* Given */
		long roomID = 3;
		int number=5;
		Room room = new Room();
		room.setRoomID(roomID);
		room.setNumber(number);
		jdbcRoomDao.addRoom(room);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("addRoomTestTable.xml");
		/* Then */
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testRemoveRoom() throws Exception {
		/* Given */
		long roomID = 2;
		int number=2;
		Room room = new Room();
		room.setRoomID(roomID);
		room.setNumber(number);
		jdbcRoomDao.removeRoom(room);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("deleteRoomTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testUpdateRoom() throws Exception {
		/* Given */
		long roomID = 2;
		int number=6;
		Room room = new Room();
		room.setRoomID(roomID);
		room.setNumber(number);
		jdbcRoomDao.updateRoom(room);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("updateRoomTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testFindById() throws Exception {
		/* Given */
		long roomID = 2;
		int number=2;
		/* When */
		Room room = jdbcRoomDao.findById(roomID);
		/* Then */
		assertThat(room.getRoomID(), is(roomID));
		assertThat(room.getNumber(), is(number));
	}
}
