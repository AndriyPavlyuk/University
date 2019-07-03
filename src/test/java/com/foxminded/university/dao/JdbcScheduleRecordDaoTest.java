package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.JdbcScheduleRecordDao;
import com.foxminded.university.dao.RoomDao;
import com.foxminded.university.dao.SubjectDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Room;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Subject;

import static org.mockito.Mockito.*;

public class JdbcScheduleRecordDaoTest extends DBTestCase {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcScheduleRecordDao jdbcScheduleRecordDao;
	private GroupDao groupDao;
	private SubjectDao subjectDao;
	private RoomDao roomDao;
	private DataSourceConnection dataSourceConnection;

	public JdbcScheduleRecordDaoTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER_NAME);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
	}

	protected IDataSet getDataSet() throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream("inputScheduleRecordTestTable.xml");
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
		ITable actualTable = actualDataSet.getTable("SCHEDULERECORDS");
		return actualTable;
	}

	private ITable getExpextedTable(String fileName) throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream(fileName);
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
		ITable expectedTable = expectedDataSet.getTable("SCHEDULERECORDS");
		return expectedTable;
	}
	
	@Before
	public void setUp() throws Exception {
		long groupID=3;
		long groupID1=2;
		long subjectID=2;
		long roomID=1;
		groupDao = mock(GroupDao.class);
		subjectDao = mock(SubjectDao.class);
		roomDao=mock(RoomDao.class);
		when(groupDao.findById(groupID)).thenReturn(new Group(groupID));
		when(subjectDao.findById(subjectID)).thenReturn(new Subject(subjectID));
		when(roomDao.findById(roomID)).thenReturn(new Room(roomID));
		when(groupDao.findById(groupID1)).thenReturn(new Group(groupID1));
		jdbcScheduleRecordDao = spy(new JdbcScheduleRecordDao(groupDao, subjectDao, roomDao));
	    dataSourceConnection=spy(DataSourceConnection.class);
		doReturn(dataSourceConnection).when(jdbcScheduleRecordDao).getDataSourceConnection();
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
	public void testFindAll() throws Exception {	
		/* Given */
		Collection<ScheduleRecord> scheduleRecordsList = jdbcScheduleRecordDao.findAll();
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("findAllScheduleRecordTestTable.xml");
		/* Then */
		Assertion.assertEquals(expectedTable, actualTable);
		Assert.assertEquals(expectedTable.getRowCount(),scheduleRecordsList.size());
	}

	@Test
	public void testAddScheduleRecord() throws Exception {
		/* Given */
		long scheduleRecordID=3;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(groupDao.findById(groupID));
		scheduleRecord.setSubject(subjectDao.findById(subjectID));
		scheduleRecord.setRoom(roomDao.findById(roomID));
		jdbcScheduleRecordDao.addScheduleRecord(scheduleRecord);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("addScheduleRecordTestTable.xml");
		/* Then */
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testUpdateScheduleRecord() throws Exception {
		/* Given */
		long groupID= 2;
		long subjectID= 2;
		long roomID= 1;
		long scheduleRecordID= 2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(groupDao.findById(groupID));
		scheduleRecord.setSubject(subjectDao.findById(subjectID));
		scheduleRecord.setRoom(roomDao.findById(roomID));
		jdbcScheduleRecordDao.updateScheduleRecord(scheduleRecord);		
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("updateScheduleRecordTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	
	@Test
	public void testRemoveScheduleRecord() throws Exception {
		/* Given */
		long scheduleRecordID=2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(groupDao.findById(groupID));
		scheduleRecord.setSubject(subjectDao.findById(subjectID));
		scheduleRecord.setRoom(roomDao.findById(roomID));
		jdbcScheduleRecordDao.removeScheduleRecord(scheduleRecord);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("deleteScheduleRecordTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testFindById() throws Exception {
		/* Given */
		long scheduleRecordID=2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		/* When */
		ScheduleRecord scheduleRecord  = jdbcScheduleRecordDao.findById(scheduleRecordID);
		/* Then */
		assertThat(scheduleRecord.getScheduleRecordID(), is(scheduleRecordID));
		assertThat(scheduleRecord.getTime(), is(time));
		assertThat(scheduleRecord.getGroup().getGroupID(), is(groupID));
		assertThat(scheduleRecord.getSubject().getSubjectID(), is(subjectID));
		assertThat(scheduleRecord.getRoom().getRoomID(), is(roomID));
	}
}
