package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
import com.foxminded.university.dao.JdbcSubjectDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Subject;

public class JdbcSubjectDaoTest extends DBTestCase {
	private final String DRIVER = ConnectionProperties.DRIVER;
	private final String URL = ConnectionProperties.URL;
	private final String USER_NAME = ConnectionProperties.USER_NAME;
	private final String PASSWORD = ConnectionProperties.PASSWORD;
	private JdbcSubjectDao jdbcSubjectDao;
	private DataSourceConnection dataSourceConnection;

	public JdbcSubjectDaoTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER_NAME);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
	}

	protected IDataSet getDataSet() throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream("inputSubjectTestTable.xml");
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
		ITable actualTable = actualDataSet.getTable("SUBJECTS");
		return actualTable;
	}

	private ITable getExpextedTable(String fileName) throws Exception {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputFile = classLoader.getResourceAsStream(fileName);
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
		ITable expectedTable = expectedDataSet.getTable("SUBJECTS");
		return expectedTable;
	}

	@Before
	public void setUp() throws SQLException, DaoException {
		 jdbcSubjectDao = spy(JdbcSubjectDao.class);
		 dataSourceConnection=spy(DataSourceConnection.class);
		// doReturn(dataSourceConnection).when(jdbcSubjectDao).getDataSourceConnection();
		// when(jdbcSubjectDao).thenReturn(dataSourceConnection.getDataSourceConnection());
		// when(dataSourceConnection.getDataSourceConnection()).thenReturn(createConnection());
	 doReturn(createConnection()).when(jdbcSubjectDao).getDataSourceConnection();
		 
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
		Collection<Subject> subjectsList = jdbcSubjectDao.findAll();
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("findAllSubjectTestTable.xml");
		/* Then */
		Assertion.assertEquals(expectedTable, actualTable);
		Assert.assertEquals(expectedTable.getRowCount(), subjectsList.size());
	}

	@Test
	public void testAddSubject() throws Exception {
		/* Given */
		long subjectID = 3;
		String name="Physics";
		Subject subject = new Subject();
		subject.setSubjectID(subjectID);
		subject.setName(name);
		jdbcSubjectDao.addSubject(subject);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("addSubjectTestTable.xml");
		/* Then */
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testRemoveSubject() throws Exception {
		/* Given */
		long subjectID = 2;
		String name="Mathematics";
		Subject subject = new Subject();
		subject.setSubjectID(subjectID);
		subject.setName(name);
		jdbcSubjectDao.removeSubject(subject);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("deleteSubjectTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testUpdateSubject() throws Exception {
		/* Given */
		long subjectID = 2;
		String name="Geography";
		Subject subject = new Subject();
		subject.setSubjectID(subjectID);
		subject.setName(name);
		jdbcSubjectDao.updateSubject(subject);
		ITable actualTable = getActualTable();
		/* When */
		ITable expectedTable = getExpextedTable("updateSubjectTestTable.xml");
		ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
				expectedTable.getTableMetaData().getColumns());
		/* Then */
		Assertion.assertEquals(expectedTable, filteredActualTable);
	}

	@Test
	public void testFindById() throws Exception {
		/* Given */
		long subjectID = 1;
		String name="Biology";
		/* When */
		Subject subject = jdbcSubjectDao.findById(subjectID);
		/* Then */
		assertThat(subject.getSubjectID(), is(subjectID));
		assertThat(subject.getName(), is(name));
	}
}
