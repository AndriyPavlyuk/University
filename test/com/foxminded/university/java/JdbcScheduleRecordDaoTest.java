package com.foxminded.university.java;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
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
import org.powermock.api.mockito.PowerMockito;

import com.foxminded.university.java.dao.ConnectionProperties;
import com.foxminded.university.java.dao.GroupDao;
import com.foxminded.university.java.dao.JdbcScheduleRecordDao;
import com.foxminded.university.java.dao.RoomDao;
import com.foxminded.university.java.dao.SubjectDao;
import com.foxminded.university.java.domain.Group;
import com.foxminded.university.java.domain.Room;
import com.foxminded.university.java.domain.ScheduleRecord;
import com.foxminded.university.java.domain.Subject;

public class JdbcScheduleRecordDaoTest extends DBTestCase {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcScheduleRecordDao jdbcScheduleRecordDao;
	private GroupDao groupDao;
	private SubjectDao subjectDao;
	private RoomDao roomDao;

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
	public void setUp() throws SQLException {
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		groupDao = PowerMockito.mock(GroupDao.class);
		subjectDao = PowerMockito.mock(SubjectDao.class);
		roomDao=PowerMockito.mock(RoomDao.class);
		jdbcScheduleRecordDao = new JdbcScheduleRecordDao(groupDao, subjectDao, roomDao);
		PowerMockito.when(groupDao.findById(groupID)).thenReturn(new Group(groupID));
		PowerMockito.when(subjectDao.findById(subjectID)).thenReturn(new Subject(subjectID));
		PowerMockito.when(roomDao.findById(roomID)).thenReturn(new Room(roomID));
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
		Group group = new Group();
		group.setGroupID(groupID);
		Subject subject=new Subject();
		subject.setSubjectID(subjectID);
		Room room=new Room();
		room.setRoomID(roomID);
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(group);
		scheduleRecord.setSubject(subject);
		scheduleRecord.setRoom(room);
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
	public void testRemoveScheduleRecord() throws Exception {
		/* Given */
		long scheduleRecordID=2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		Group group = new Group();
		group.setGroupID(groupID);
		Subject subject=new Subject();
		subject.setSubjectID(subjectID);
		Room room=new Room();
		room.setRoomID(roomID);
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(group);
		scheduleRecord.setSubject(subject);
		scheduleRecord.setRoom(room);
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
	public void testUpdateScheduleRecord() throws Exception {
		/* Given */
		long scheduleRecordID=2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=2;
		long subjectID=3;
		long roomID=2;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		Group group = new Group();
		group.setGroupID(groupID);
		Subject subject=new Subject();
		subject.setSubjectID(subjectID);
		Room room=new Room();
		room.setRoomID(roomID);
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(group);
		scheduleRecord.setSubject(subject);
		scheduleRecord.setRoom(room);
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
