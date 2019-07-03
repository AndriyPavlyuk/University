package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foxminded.university.domain.Student;

@Repository
public class JdbcStudentDao implements StudentDao {
	@Autowired
	private GroupDao groupDao;
	private static final Logger logger = LoggerFactory.getLogger(JdbcStudentDao.class);
	@Autowired
	private DataSourceConnection dataSourceConnection;

	public JdbcStudentDao() {
	}

	@Autowired
	public JdbcStudentDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public Collection<Student> findAll() throws DaoException {
		logger.debug("Searching students");
		Collection<Student> studentsList = new ArrayList<>();
		String sql = "SELECT STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME FROM STUDENTS";
		Statement statement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Student student = new Student();
				student.setPersonID(resultSet.getLong("STUDENT_ID"));
				student.setGroup(groupDao.findById(resultSet.getLong("GROUP_ID")));
				student.setFirstName(resultSet.getString("FIRST_NAME"));
				student.setLastName(resultSet.getString("LAST_NAME"));
				studentsList.add(student);
			}
			logger.trace("Number of students : {}", studentsList.size());
		} catch (SQLException e) {
			logger.warn("Can not find students : " + e);
			throw new DaoException("Can not find students", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.warn("Can not close statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Students were founded:");
		return studentsList;
	}

	@Override
	public Student findById(long id) throws DaoException {
		logger.debug("Searching student");
		if (id < 0) {
			logger.warn("Can not find student : " + id);
			throw new DaoException("Can not find student");
		}
		Student student = new Student();
		String sql = "SELECT STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME FROM STUDENTS WHERE STUDENT_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				student.setPersonID(resultSet.getLong("STUDENT_ID"));
				student.setGroup(groupDao.findById(resultSet.getLong("GROUP_ID")));
				student.setFirstName(resultSet.getString("FIRST_NAME"));
				student.setLastName(resultSet.getString("LAST_NAME"));
			}
			if (student.getPersonID() == null) {
				logger.warn("Can not find student : " + student.getPersonID());
				throw new DaoException("Can not find student");
			}
		} catch (SQLException e) {
			logger.warn("Can not find students : " + e);
			throw new DaoException("Can not find student", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Student was founded");
		return student;
	}

	@Override
	public void addStudent(Student student) throws DaoException {
		logger.debug("Adding student");
		if (student.getPersonID() < 0) {
			logger.warn("Can not add student : " + student.getPersonID());
			throw new DaoException("Can not add student");
		}
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO STUDENTS (STUDENT_ID, GROUP_ID, FIRST_NAME,LAST_NAME) VALUES(?, ?, ?,?)";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, student.getPersonID());
			preparedStatement.setLong(2, student.getGroup().getGroupID());
			preparedStatement.setString(3, student.getFirstName());
			preparedStatement.setString(4, student.getLastName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not add students : " + e);
			throw new DaoException("Can not add student", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Student was added");
	}

	@Override
	public void updateStudent(Student student) throws DaoException {
		logger.debug("Updating student");
		if (student.getPersonID() < 0) {
			logger.warn("Can not update student : " + student.getPersonID());
			throw new DaoException("Can not update student");
		}
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE STUDENTS SET GROUP_ID=?, FIRST_NAME=?, LAST_NAME=? WHERE STUDENT_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, student.getGroup().getGroupID());
			preparedStatement.setString(2, student.getFirstName());
			preparedStatement.setString(3, student.getLastName());
			preparedStatement.setLong(4, student.getPersonID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not update student : " + e);
			throw new DaoException("Can not update student", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Student was updated");
	}

	@Override
	public void removeStudent(Student student) throws DaoException {
		logger.debug("Removing student");
		if (student.getPersonID() < 0) {
			logger.warn("Can not remove student : " + student.getPersonID());
			throw new DaoException("Can not remove student");
		}
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM STUDENTS WHERE STUDENT_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, student.getPersonID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not remove student : " + e);
			throw new DaoException("Can not remove student", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Student was removed");
	}
}