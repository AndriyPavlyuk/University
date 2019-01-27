package com.foxminded.university.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.java.domain.ScheduleRecord;

public class JdbcScheduleRecordDao extends AbstractJdbcDao implements ScheduleRecordDao{
	private SubjectDao subjectDao;
	private GroupDao groupDao;
	private RoomDao roomDao;
	
	public JdbcScheduleRecordDao() {
	}
	public JdbcScheduleRecordDao(GroupDao groupDao,SubjectDao subjectDao, RoomDao roomDao) {
		this.groupDao = groupDao;
		this.subjectDao = subjectDao;
		this.roomDao = roomDao;
	}

	@Override
	public Collection<ScheduleRecord> findAll() throws SQLException {
		Collection<ScheduleRecord> scheduleRecordList = new ArrayList<>();
		String sql = "SELECT SCHEDULERECORDS_ID, date_time, SUBJECT_ID, GROUP_ID, ROOM_ID FROM SCHEDULERECORDS";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				ScheduleRecord scheduleRecord = new ScheduleRecord();
				scheduleRecord.setScheduleRecordID(resultSet.getLong("SCHEDULERECORDS_ID"));
				scheduleRecord.setTime(resultSet.getObject(2, LocalDateTime.class));
				scheduleRecord.setGroup(groupDao.findById(resultSet.getLong("GROUP_ID")));
				scheduleRecord.setSubject(subjectDao.findById(resultSet.getLong("SUBJECT_ID")));
				scheduleRecord.setRoom(roomDao.findById(resultSet.getLong("ROOM_ID")));
				scheduleRecordList.add(scheduleRecord);
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
		return scheduleRecordList;
	}

	@Override
	public ScheduleRecord findById(long id) throws SQLException {
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		String sql = "SELECT SCHEDULERECORDS_ID, DATE_TIME, SUBJECT_ID, GROUP_ID, ROOM_ID  FROM SCHEDULERECORDS WHERE SCHEDULERECORDS_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				scheduleRecord.setScheduleRecordID(resultSet.getLong("SCHEDULERECORDS_ID"));
				scheduleRecord.setTime(resultSet.getObject(2, LocalDateTime.class));
				scheduleRecord.setGroup(groupDao.findById(resultSet.getLong("GROUP_ID")));
				scheduleRecord.setSubject(subjectDao.findById(resultSet.getLong("SUBJECT_ID")));
				scheduleRecord.setRoom(roomDao.findById(resultSet.getLong("ROOM_ID")));
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
		return scheduleRecord;
	}

	@Override
	public void addScheduleRecord(ScheduleRecord scheduleRecord) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO SCHEDULERECORDS (SCHEDULERECORDS_ID, DATE_TIME, SUBJECT_ID, GROUP_ID, ROOM_ID) VALUES(?, ?, ?,?,?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, scheduleRecord.getScheduleRecordID());
			preparedStatement.setObject(2, scheduleRecord.getTime());
			preparedStatement.setLong(3, scheduleRecord.getSubject().getSubjectID());
			preparedStatement.setLong(4, scheduleRecord.getGroup().getGroupID());
			preparedStatement.setLong(5, scheduleRecord.getRoom().getRoomID());
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
	public void updateScheduleRecord(ScheduleRecord scheduleRecord) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE SCHEDULERECORDS SET DATE_TIME=?, SUBJECT_ID=?, GROUP_ID=?, ROOM_ID=? WHERE SCHEDULERECORDS_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, scheduleRecord.getTime());
			preparedStatement.setLong(2, scheduleRecord.getSubject().getSubjectID());
			preparedStatement.setLong(3, scheduleRecord.getGroup().getGroupID());
			preparedStatement.setLong(4, scheduleRecord.getRoom().getRoomID());
			preparedStatement.setLong(5, scheduleRecord.getScheduleRecordID());
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
	public void removeScheduleRecord(ScheduleRecord scheduleRecord) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM SCHEDULERECORDS WHERE SCHEDULERECORDS_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, scheduleRecord.getScheduleRecordID());
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
