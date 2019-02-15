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

import com.foxminded.university.dao.JdbcTeacherDao;
import com.foxminded.university.domain.Teacher;

public class JdbcTeacherDaoTest extends DBTestCase {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcTeacherDao jdbcTeacherDao;

	public JdbcTeacherDaoTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER_NAME);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
	}

	protected IDataSet getDataSet() throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream("inputTeacherTestTable.xml");
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
		ITable actualTable = actualDataSet.getTable("TEACHERS");
		return actualTable;
	}

	private ITable getExpextedTable(String fileName) throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream(fileName);
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
		ITable expectedTable = expectedDataSet.getTable("TEACHERS");
		return expectedTable;
	}

	@Before
	public void setUp() throws SQLException {
		jdbcTeacherDao = new JdbcTeacherDao();
	}

	@Test
	public void testFindAll() throws Exception {
		/* Given */
		Collection<Teacher> teachersList = jdbcTeacherDao.findAll();
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("findAllTeacherTestTable.xml");
		/* Then */
		Assertion.assertEquals(expectedTable, actualTable);
		Assert.assertEquals(expectedTable.getRowCount(), teachersList.size());
	}

	@Test
	public void testAddTeacher() throws Exception {
		/* Given */
		long teacherID = 3;
		Teacher teacher = new Teacher();
		teacher.setPersonID(teacherID);
		teacher.setFirstName("Andriy");
		teacher.setLastName("Gugynov");
		jdbcTeacherDao.addTeacher(teacher);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("addTeacherTestTable.xml");
		/* Then */
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testRemoveTeacher() throws Exception {
		/* Given */
		long teacherID = 2;
		Teacher teacher = new Teacher();
		teacher.setPersonID(teacherID);
		teacher.setFirstName("Volodymur");
		teacher.setLastName("Petrenko");
		jdbcTeacherDao.removeTeacher(teacher);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("deleteTeacherTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testUpdateTeacher() throws Exception {
		/* Given */
		long teacherID = 1;
		Teacher teacher = new Teacher();
		teacher.setPersonID(teacherID);
		teacher.setFirstName("Sebastien");
		teacher.setLastName("Loeb");
		jdbcTeacherDao.updateTeacher(teacher);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("updateTeacherTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testFindById() throws Exception {
		/* Given */
		long teacherID = 1;
		/* When */
		Teacher teacher = jdbcTeacherDao.findById(teacherID);
		/* Then */
		assertThat(teacher.getPersonID(), is(teacherID));
		assertThat(teacher.getFirstName(), is("Aleksandr"));
		assertThat(teacher.getLastName(), is("Saliuk"));
	}
}