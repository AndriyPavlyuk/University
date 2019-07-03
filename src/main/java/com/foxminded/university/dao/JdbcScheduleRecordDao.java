package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.university.domain.ScheduleRecord;
@Component
public class JdbcScheduleRecordDao implements ScheduleRecordDao{
	private SubjectDao subjectDao;
	private GroupDao groupDao;
	private RoomDao roomDao;
	private static final Logger logger = LoggerFactory.getLogger(JdbcScheduleRecordDao.class);
	@Autowired
	private DataSourceConnection dataSourceConnection;
	
	public JdbcScheduleRecordDao() {
	}
	public JdbcScheduleRecordDao(GroupDao groupDao,SubjectDao subjectDao, RoomDao roomDao) {
		this.groupDao = groupDao;
		this.subjectDao = subjectDao;
		this.roomDao = roomDao;
	}

	@Override
	public Collection<ScheduleRecord> findAll() throws DaoException {
		logger.debug("Searching schedule records");
		Collection<ScheduleRecord> scheduleRecordList = new ArrayList<>();
		String sql = "SELECT SCHEDULERECORDS_ID, date_time, SUBJECT_ID, GROUP_ID, ROOM_ID FROM SCHEDULERECORDS";
		Statement statement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
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
			logger.trace("Number of schedule records : {}", scheduleRecordList.size());
		} catch (SQLException e) {
			logger.warn("Can not find schedule records : " + e);
			throw new DaoException("Can not find schedule records",e);
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
		logger.info("Schedule records were founded");
		return scheduleRecordList;
	}

	@Override
	public ScheduleRecord findById(long id) throws DaoException {
		logger.debug("Searching schedule record");
		if (id < 0) {
			logger.error("Can not find schedule record : " + id);
			throw new DaoException("Can not find schedule record");
		}
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		String sql = "SELECT SCHEDULERECORDS_ID, DATE_TIME, GROUP_ID, SUBJECT_ID, ROOM_ID  FROM SCHEDULERECORDS WHERE SCHEDULERECORDS_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
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
			if (scheduleRecord.getScheduleRecordID()==null) {
				logger.warn("Can not find schedule records : " + scheduleRecord.getScheduleRecordID());
				throw new DaoException("Can not find schedule record");
			}
		} catch (SQLException e) {
			logger.warn("Can not find schedule records : " + e);
			throw new DaoException("Can not find schedule record",e);
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
		logger.info("Schedule record was founded");
		return scheduleRecord;
	}

	@Override
	public void addScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException {
		logger.debug("Adding schedule record");
		if (scheduleRecord.getScheduleRecordID() < 0) {
			logger.warn("Can not add schedule records : " + scheduleRecord.getScheduleRecordID());
			throw new DaoException("Can not add schedule record");
		}
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO SCHEDULERECORDS (SCHEDULERECORDS_ID, DATE_TIME, SUBJECT_ID, GROUP_ID, ROOM_ID) VALUES(?, ?, ?,?,?)";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection =dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, scheduleRecord.getScheduleRecordID());
			preparedStatement.setObject(2, scheduleRecord.getTime());
			preparedStatement.setLong(3, scheduleRecord.getSubject().getSubjectID());
			preparedStatement.setLong(4, scheduleRecord.getGroup().getGroupID());
			preparedStatement.setLong(5, scheduleRecord.getRoom().getRoomID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not add schedule records : " + e);
			throw new DaoException("Can not add schedule record",e);
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
		logger.info("Schedule record was added");
	}

	@Override
	public void updateScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException {
		logger.debug("Updating schedule record");
		if (scheduleRecord.getScheduleRecordID() < 0) {
			logger.warn("Can not update schedule records : " + scheduleRecord.getScheduleRecordID());
			throw new DaoException("Can not update schedule record");
		}
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE SCHEDULERECORDS SET DATE_TIME=?, GROUP_ID=?, SUBJECT_ID=?, ROOM_ID=? WHERE SCHEDULERECORDS_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, scheduleRecord.getTime());
			preparedStatement.setLong(2, scheduleRecord.getGroup().getGroupID());
			preparedStatement.setLong(3, scheduleRecord.getSubject().getSubjectID());
			preparedStatement.setLong(4, scheduleRecord.getRoom().getRoomID());
			preparedStatement.setLong(5, scheduleRecord.getScheduleRecordID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not update schedule records : " + e);
			throw new DaoException("Can not update schedule",e);
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
		logger.info("Schedule record was updated");
	}

	@Override
	public void removeScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException {
		logger.debug("Removing schedule record");
		if (scheduleRecord.getScheduleRecordID() < 0) {
			logger.error("Can not remove schedule records : " + scheduleRecord.getScheduleRecordID());
			throw new DaoException("Can not remove schedule record");
		}
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM SCHEDULERECORDS WHERE SCHEDULERECORDS_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection =dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, scheduleRecord.getScheduleRecordID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not remove schedule record",e);
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
		logger.info("Schedule record was removed");
	}
	public DataSourceConnection getDataSourceConnection() {
		return dataSourceConnection;
	}
}
