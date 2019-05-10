package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
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
import org.mockito.Mockito;

import com.foxminded.university.dao.ConnectionProperties;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.JdbcStudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

public class JdbcStudentDaoTest extends DBTestCase {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcStudentDao jdbcStudentDao;
	private GroupDao groupDao;

	public JdbcStudentDaoTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER_NAME);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
	}

	protected IDataSet getDataSet() throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream("inputStudentTestTable.xml");
		return new FlatXmlDataSetBuilder().build(inputFile);
	}

	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	private ITable getActualTable() throws Exception {
		IDataSet actualDataSet = getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("STUDENTS");
		return actualTable;
	}

	private ITable getExpextedTable(String fileName) throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream(fileName);
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
		ITable expectedTable = expectedDataSet.getTable("STUDENTS");
		return expectedTable;
	}

	@Before
	public void setUp() throws Exception {
		long groupID=3;
		groupDao = Mockito.mock(GroupDao.class);
		jdbcStudentDao = new JdbcStudentDao(groupDao);
		Mockito.when(groupDao.findById(groupID)).thenReturn(new Group(groupID));
	}
	
	@Test
	public void testFindAll() throws Exception {	
		/* Given */
		Collection<Student> studentsList = jdbcStudentDao.findAll();
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("findAllStudentTestTable.xml");
		/* Then */
		Assertion.assertEquals(expectedTable, actualTable);
		Assert.assertEquals(expectedTable.getRowCount(), studentsList.size());
	}

	@Test
	public void testAddStudent() throws Exception {
		/* Given */
		long studentID = 3;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Ivan");
		student.setLastName("Gorbyn");
		student.setGroup(groupDao.findById(groupID));
		jdbcStudentDao.addStudent(student);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("addStudentTestTable.xml");
		/* Then */
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testRemoveStudent() throws Exception {
		/* Given */
		long studentID = 1;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Mikko");
		student.setLastName("Hirvonen");
		student.setGroup(groupDao.findById(groupID));
		jdbcStudentDao.removeStudent(student);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("deleteStudentTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testUpdateStudent() throws Exception {
		/* Given */
		long studentID = 1;
		long groupID = 3;
		Student student = new Student();
		student.setPersonID(studentID);
		student.setFirstName("Sebastien");
		student.setLastName("Loeb");
		student.setGroup(groupDao.findById(groupID));
		jdbcStudentDao.updateStudent(student);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("updateStudentTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testFindById() throws Exception {
		/* Given */
		long studentID = 1;
		long groupID = 3;
		/* When */
		Student student = jdbcStudentDao.findById(studentID);
		/* Then */
		assertThat(student.getPersonID(), is(studentID));
		assertThat(student.getGroup().getGroupID(), is(groupID));
		assertThat(student.getFirstName(), is("Mikko"));
		assertThat(student.getLastName(), is("Hirvonen"));
	}
}