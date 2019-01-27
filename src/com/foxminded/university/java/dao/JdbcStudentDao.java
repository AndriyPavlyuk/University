package com.foxminded.university.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.java.domain.Student;

public class JdbcStudentDao extends AbstractJdbcDao implements StudentDao {

	private GroupDao groupDao;
	
	public JdbcStudentDao() {
	}
	public JdbcStudentDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public Collection<Student> findAll() throws SQLException {
		Collection<Student> studentsList = new ArrayList<>();
		String sql = "SELECT STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME FROM STUDENTS";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
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
		return studentsList;
	}

	@Override
	public Student findById(long id) throws SQLException {
		Student student = new Student();
		String sql = "SELECT STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME FROM STUDENTS WHERE STUDENT_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				student.setPersonID(resultSet.getLong("STUDENT_ID"));
				student.setGroup(groupDao.findById(resultSet.getLong("GROUP_ID")));
				student.setFirstName(resultSet.getString("FIRST_NAME"));
				student.setLastName(resultSet.getString("LAST_NAME"));
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
		return student;
	}

	@Override
	public void addStudent(Student student) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO STUDENTS (STUDENT_ID, GROUP_ID, FIRST_NAME,LAST_NAME) VALUES(?, ?, ?,?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, student.getPersonID());
			preparedStatement.setLong(2, student.getGroup().getGroupID());
			preparedStatement.setString(3, student.getFirstName());
			preparedStatement.setString(4, student.getLastName());
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
	public void updateStudent(Student student) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE STUDENTS SET GROUP_ID=?, FIRST_NAME=?, LAST_NAME=? WHERE STUDENT_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, student.getGroup().getGroupID());
			preparedStatement.setString(2, student.getFirstName());
			preparedStatement.setString(3, student.getLastName());
			preparedStatement.setLong(4, student.getPersonID());
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
	public void removeStudent(Student student) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM STUDENTS WHERE STUDENT_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, student.getPersonID());
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