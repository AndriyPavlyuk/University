package com.foxminded.university.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.java.domain.Subject;

public class JdbcSubjectDao extends AbstractJdbcDao implements SubjectDao {

	@Override
	public Collection<Subject> findAll() throws SQLException {
		Collection<Subject> subjectsList = new ArrayList<>();
		String sql = "SELECT SUBJECT_ID, NAME FROM SUBJECTS";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setSubjectID(resultSet.getLong("SUBJECT_ID"));
				subject.setName(resultSet.getString("NAME"));
				subjectsList.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return subjectsList;
	}

	@Override
	public Subject findById(long id) throws SQLException {
		Subject subject = new Subject();
		String sql = "SELECT SUBJECT_ID, NAME FROM SUBJECTS WHERE SUBJECT_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				subject.setSubjectID(resultSet.getLong("SUBJECT_ID"));
				subject.setName(resultSet.getString("NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return subject;
	}

	@Override
	public void addSubject(Subject subject) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO SUBJECTS (SUBJECT_ID, NAME) VALUES(?,?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, subject.getSubjectID());
			preparedStatement.setString(2, subject.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
	}

	@Override
	public void updateSubject(Subject subject) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE SUBJECTS SET NAME=? WHERE SUBJECT_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, subject.getName());
			preparedStatement.setLong(2, subject.getSubjectID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
	}

	@Override
	public void removeSubject(Subject subject) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM SUBJECTS WHERE SUBJECT_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, subject.getSubjectID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}	
}
