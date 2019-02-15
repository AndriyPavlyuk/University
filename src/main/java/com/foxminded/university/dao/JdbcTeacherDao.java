package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.domain.Teacher;

public class JdbcTeacherDao extends AbstractJdbcDao implements TeacherDao {

	public JdbcTeacherDao() {
	}

	@Override
	public Collection<Teacher> findAll() throws DaoException {
		Collection<Teacher> teachersList = new ArrayList<>();
		String sql = "SELECT TEACHER_ID, FIRST_NAME, LAST_NAME FROM TEACHERS";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Teacher teacher = new Teacher();
				teacher.setPersonID(resultSet.getLong("TEACHER_ID"));
				teacher.setFirstName(resultSet.getString("FIRST_NAME"));
				teacher.setLastName(resultSet.getString("LAST_NAME"));
				teachersList.add(teacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not find teacher",e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return teachersList;
	}

	@Override
	public Teacher findById(long id) throws DaoException {
		Teacher teacher = new Teacher();
		String sql = "SELECT TEACHER_ID, FIRST_NAME, LAST_NAME FROM TEACHERS WHERE TEACHER_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				teacher.setPersonID(resultSet.getLong("TEACHER_ID"));
				teacher.setFirstName(resultSet.getString("FIRST_NAME"));
				teacher.setLastName(resultSet.getString("LAST_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not find teachers",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return teacher;
	}

	@Override
	public void addTeacher(Teacher teacher) throws DaoException {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO TEACHERS (TEACHER_ID, FIRST_NAME, LAST_NAME) VALUES(?, ?, ?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, teacher.getPersonID());
			preparedStatement.setString(2, teacher.getFirstName());
			preparedStatement.setString(3, teacher.getLastName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not find teacher",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void updateTeacher(Teacher teacher) throws DaoException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE TEACHERS SET FIRST_NAME=?, LAST_NAME=? WHERE TEACHER_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teacher.getFirstName());
			preparedStatement.setString(2, teacher.getLastName());
			preparedStatement.setLong(3, teacher.getPersonID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not update teacher",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void removeTeacher(Teacher teacher) throws DaoException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM TEACHERS WHERE TEACHER_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, teacher.getPersonID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not remove teacher",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
