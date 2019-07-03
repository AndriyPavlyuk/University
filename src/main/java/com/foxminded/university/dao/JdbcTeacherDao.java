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
import org.springframework.stereotype.Component;

import com.foxminded.university.domain.Teacher;
@Component
public class JdbcTeacherDao implements TeacherDao {
	private static final Logger logger = LoggerFactory.getLogger(JdbcTeacherDao.class);
	@Autowired
	private DataSourceConnection dataSourceConnection;
	
	public JdbcTeacherDao() {
	}

	@Override
	public Collection<Teacher> findAll() throws DaoException {
		logger.debug("Searching teacher");
		Collection<Teacher> teachersList = new ArrayList<>();
		String sql = "SELECT TEACHER_ID, FIRST_NAME, LAST_NAME FROM TEACHERS";
		Statement statement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Teacher teacher = new Teacher();
				teacher.setPersonID(resultSet.getLong("TEACHER_ID"));
				teacher.setFirstName(resultSet.getString("FIRST_NAME"));
				teacher.setLastName(resultSet.getString("LAST_NAME"));
				teachersList.add(teacher);
			}
			logger.trace("Number of teacher : {}", teachersList.size());
		} catch (SQLException e) {
			logger.warn("Can not find teacher : " + e);
			throw new DaoException("Can not find teacher",e);
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
		logger.info("Teachers were founded");
		return teachersList;
	}

	@Override
	public Teacher findById(long id) throws DaoException {
		logger.debug("Searching teacher");
		if (id < 0) {
			logger.warn("Can not find teacher : " + id);
			throw new DaoException("Can not find teacher");
		}
		Teacher teacher = new Teacher();
		String sql = "SELECT TEACHER_ID, FIRST_NAME, LAST_NAME FROM TEACHERS WHERE TEACHER_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				teacher.setPersonID(resultSet.getLong("TEACHER_ID"));
				teacher.setFirstName(resultSet.getString("FIRST_NAME"));
				teacher.setLastName(resultSet.getString("LAST_NAME"));
			}
			if (teacher.getPersonID()==null) {
				logger.warn("Can not find teacher : " + teacher.getPersonID());
					throw new DaoException("Can not find teacher");
			}
		} catch (SQLException e) {
			logger.warn("Can not find teacher : " + e);
			throw new DaoException("Can not find teacher",e);
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
		logger.info("Teacher was founded");
		return teacher;
	}

	@Override
	public void addTeacher(Teacher teacher) throws DaoException {
		logger.debug("Adding teacher");
		if (teacher.getPersonID()< 0) {
			logger.warn("Can not add teacher : " + teacher.getPersonID());
			throw new DaoException("Can not add teacher");
		}
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO TEACHERS (TEACHER_ID, FIRST_NAME, LAST_NAME) VALUES(?, ?, ?)";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, teacher.getPersonID());
			preparedStatement.setString(2, teacher.getFirstName());
			preparedStatement.setString(3, teacher.getLastName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not add teacher : " + e);
			throw new DaoException("Can not add teacher", e);
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
		logger.info("Teacher was added");
	}

	@Override
	public void updateTeacher(Teacher teacher) throws DaoException {
		logger.debug("Updating teacher");
		if (teacher.getPersonID()< 0) {
			logger.warn("Can not update teacher : " + teacher.getPersonID());
			throw new DaoException("Can not update teacher");
		}
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE TEACHERS SET FIRST_NAME=?, LAST_NAME=? WHERE TEACHER_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teacher.getFirstName());
			preparedStatement.setString(2, teacher.getLastName());
			preparedStatement.setLong(3, teacher.getPersonID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not update teacher : " + e);
			throw new DaoException("Can not update teacher",e);
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
		logger.info("Teacher was updated");
	}

	@Override
	public void removeTeacher(Teacher teacher) throws DaoException {
		logger.debug("Removing teacher");
		if (teacher.getPersonID()< 0) {
			logger.warn("Can not remove teacher : " + teacher.getPersonID());
			throw new DaoException("Can not remove teacher");
		}
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM TEACHERS WHERE TEACHER_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, teacher.getPersonID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not remove teacher : " + e);
			throw new DaoException("Can not remove teacher",e);
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
		logger.info("Teacher was removed");
	}
	public DataSourceConnection getDataSourceConnection() {
		return dataSourceConnection;
	}
}