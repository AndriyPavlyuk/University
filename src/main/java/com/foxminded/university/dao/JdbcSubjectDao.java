package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.domain.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class JdbcSubjectDao extends AbstractJdbcDao implements SubjectDao {
	private static final Logger logger = LoggerFactory.getLogger(JdbcSubjectDao.class);
	
	@Override
	public Collection<Subject> findAll() throws DaoException {
		logger.debug("Searching students");
		Collection<Subject> subjectsList = new ArrayList<>();
		String sql = "SELECT SUBJECT_ID, NAME FROM SUBJECTS";
		Statement statement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setSubjectID(resultSet.getLong("SUBJECT_ID"));
				subject.setName(resultSet.getString("NAME"));
				subjectsList.add(subject);
			}
			logger.trace("Number of subject : {}", subjectsList.size());
		} catch (SQLException e) {
			logger.warn("Can not find subjects : " + e);
			throw new DaoException("Can not find subjects",e);
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
		logger.info("Subjects were founded");
		return subjectsList;
	}

	@Override
	public Subject findById(long id) throws DaoException {
		logger.debug("Searching subject");
		if (id < 0) {
			logger.warn("Can not find subject : " + id);
			throw new DaoException("Can not find subject");
		}
		Subject subject = new Subject();
		String sql = "SELECT SUBJECT_ID, NAME FROM SUBJECTS WHERE SUBJECT_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				subject.setSubjectID(resultSet.getLong("SUBJECT_ID"));
				subject.setName(resultSet.getString("NAME"));
			}
			if (subject.getSubjectID()==null) {
				logger.warn("Can not find subject : " + subject.getSubjectID());
				throw new DaoException("Can not find subject");
			}
		} catch (SQLException e) {
			logger.warn("Can not find groups : " + e);
			throw new DaoException("Can not find subject",e);
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
		logger.info("Subject was founded");
		return subject;
	}

	@Override
	public void addSubject(Subject subject) throws DaoException {
		logger.debug("Adding subject");
		if (subject.getSubjectID() < 0) {
			logger.warn("Can not find subject : " + subject.getSubjectID());
			throw new DaoException("Can not add subject");
		}
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO SUBJECTS (SUBJECT_ID, NAME) VALUES(?,?)";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, subject.getSubjectID());
			preparedStatement.setString(2, subject.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not find groups : " + e);
			throw new DaoException("Can not add subject",e);
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
		logger.info("Subject was added");
	}

	@Override
	public void updateSubject(Subject subject) throws DaoException {
		logger.debug("Updating subject");
		if (subject.getSubjectID() < 0) {
			logger.warn("Can not update subject : " + subject.getSubjectID());
			throw new DaoException("Can not update subject");
		}
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE SUBJECTS SET NAME=? WHERE SUBJECT_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, subject.getName());
			preparedStatement.setLong(2, subject.getSubjectID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not update subject : " + e);
			throw new DaoException("Can not update subject",e);
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
		logger.info("Subject was updated");
	}

	@Override
	public void removeSubject(Subject subject) throws DaoException {
		logger.debug("Removing subject");
		if (subject.getSubjectID() < 0) {
			logger.warn("Can not remove subject : " + subject.getSubjectID());
			throw new DaoException("Can not remove subject");
		}
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM SUBJECTS WHERE SUBJECT_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, subject.getSubjectID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not remove subject : " + e);
			throw new DaoException("Can not remove subject",e);
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
		logger.info("Subject was removed");
	}	
}